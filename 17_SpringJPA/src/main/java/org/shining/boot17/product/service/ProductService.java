package org.shining.boot17.product.service;

import java.util.List;

import org.shining.boot17.product.dto.ProductDTO;
import org.shining.boot17.product.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProductService {
  ProductDTO saveProduct(ProductDTO dto);
  ProductDTO updateProduct(ProductDTO dto);
  void deleteProduct(Integer productId);
  ProductDTO findProductById(Integer productId);
  Page<ProductDTO> findProductList(Pageable pageable);
  List<Product> findProductsByCategory(Integer categoryId);
}
