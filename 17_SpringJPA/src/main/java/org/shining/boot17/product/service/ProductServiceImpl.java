package org.shining.boot17.product.service;

import java.util.List;
import java.util.NoSuchElementException;

import org.shining.boot17.product.dto.ProductDTO;
import org.shining.boot17.product.entity.Category;
import org.shining.boot17.product.entity.Product;
import org.shining.boot17.product.respository.CategoryRepository;
import org.shining.boot17.product.respository.ProductRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
@Transactional
public class ProductServiceImpl implements ProductService {
  
  private final ProductRepository productRepository;
  private final CategoryRepository categoryRepository;

  @Override
  public ProductDTO saveProduct(ProductDTO dto) {
    // categoryId에 해당하는 Category 정보가 필요
    Category foundCategory = categoryRepository.findById(dto.getCategoryId())
                                               .orElseThrow(() -> new NoSuchElementException("존재하지 않는 카테고리"));
    // ProductDTO dto -> Product product
    Product product = dto.toEntity(foundCategory);
    Product savedProduct = productRepository.save(product);
    // Product savedProduct -> ProductDTO
    return ProductDTO.toDTO(savedProduct);
  }

  @Override
  public ProductDTO updateProduct(ProductDTO productDTO) {
      Product entity = productRepository.findById(productDTO.getProductId()).orElse(null);
      if(entity == null){
          return null;
      }
      entity.update(productDTO);

      return ProductDTO.toDTO(entity);
  }

  @Override
  public void deleteProduct(Integer productId) {
    // findById 메소드 호출 결과는 영속 컨텍스트에 저장된다.
    Product foundProduct = productRepository.findById(productId)
                                            .orElseThrow(() -> new NoSuchElementException("존재 안함 ㅇㅇ"));
    productRepository.delete(foundProduct);
  }

  @Transactional(readOnly = true)
  @Override
  public ProductDTO findProductById(Integer productId) {
    Product foundProduct = productRepository.findById(productId)
                                            .orElseThrow(() -> new NoSuchElementException("그런 아이디는 없어"));
    return ProductDTO.toDTO(foundProduct);
  }

  @Transactional(readOnly = true)
  @Override
  public Page<ProductDTO> findProductList(Pageable pageable) {
    Page<Product> productPage = productRepository.findAll(pageable);
    return productPage.map(p -> ProductDTO.toDTO(p));
  }
  @Transactional(readOnly = true)
  @Override
  public List<Product> findProductsByCategory(Integer categoryId) {
    return productRepository.findByCategoryId(categoryId);
  }
}
