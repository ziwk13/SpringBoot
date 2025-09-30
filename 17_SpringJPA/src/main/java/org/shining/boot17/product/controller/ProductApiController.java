package org.shining.boot17.product.controller;

import java.util.Map;

import org.shining.boot17.product.dto.ProductDTO;
import org.shining.boot17.product.dto.response.ApiResponseDTO;
import org.shining.boot17.product.service.ProductService;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RequestMapping("/api/products")
@RestController
@RequiredArgsConstructor
public class ProductApiController {

  private final ProductService productService;
  
  // 등록 요청
  @PostMapping
  public ResponseEntity<ApiResponseDTO> save(ProductDTO dto) {
    ApiResponseDTO apiResponseDTO = ApiResponseDTO.builder()
                                                  .code("201")
                                                  .message("제등성")
                                                  .results(Map.of("savedProduct", productService.saveProduct(dto)))
                                                  .build();
    return ResponseEntity.status(HttpStatus.CREATED).body(apiResponseDTO);
  }
  // 수정 요청
  @PostMapping("/{id}")
  public ResponseEntity<ApiResponseDTO> update(ProductDTO dto, @PathVariable(value = "id") Integer productId) {
    dto.setProductId(productId);
    ApiResponseDTO apiResponseDTO = ApiResponseDTO.builder()
                                                  .code("200")
                                                  .message("제수성")
                                                  .results(Map.of("updatedProduct", productService.updateProduct(dto)))
                                                  .build();
    return ResponseEntity.ok(apiResponseDTO);
  }
  // 삭제 요청
  @DeleteMapping("/{id}")
  public ResponseEntity<ApiResponseDTO> delete(ProductDTO dto, @PathVariable(value = "id") Integer productId) {
    productService.deleteProduct(productId);
    ApiResponseDTO apiResponseDTO = ApiResponseDTO.builder()
                                                  .code("200")
                                                  .message("제수성")
                                                  .build();
    return ResponseEntity.ok(apiResponseDTO);
  }
  // 조회 요청
  @GetMapping("/{id}")
  public ResponseEntity<ApiResponseDTO> find(ProductDTO dto, @PathVariable(value = "id") Integer productId) {
    productService.findProductById(productId);
    ApiResponseDTO apiResponseDTO = ApiResponseDTO.builder()
                                                  .code("200")
                                                  .message("제품 조회 성공")
                                                  .results(Map.of("product", productService.findProductById(productId)))
                                                  .build();
    return ResponseEntity.ok(apiResponseDTO);
  }
  // 목록 요청
  // 요청 예시
  // 1. 기본 GET /api/product?page=0&size=10
  // 2. 정렬 GET /api/product?page=0&size=10&sort=productId,desc
  // 카테고리 ID가 1인 제품 목록
  // GET /api/products/categories/1?page=0&size=10
  
  @GetMapping
  public ResponseEntity<ApiResponseDTO> list(@PageableDefault(page = 1, size = 5, sort = "productId", direction = Direction.DESC) Pageable pageable) {
    pageable = pageable.withPage(pageable.getPageNumber() - 1);
    ApiResponseDTO apiResponseDTO = ApiResponseDTO.builder()
                                                  .code("200")
                                                  .message("제품 목록 조회 성공")
                                                  .results(Map.of("productPage", productService.findProductList(pageable)))
                                                  .build();
    return ResponseEntity.ok(apiResponseDTO);
  }
  @GetMapping("/category/{id}")
  public ResponseEntity<ApiResponseDTO> findByCategoryId(@PathVariable(value = "id") Integer categoryId) {
    ApiResponseDTO apiResponseDTO = ApiResponseDTO.builder()
                                                  .code("200")
                                                  .message("제품 조회 성공")
                                                  .results(Map.of("category", productService.findProductsByCategory(categoryId)))
                                                  .build();
    return ResponseEntity.ok(apiResponseDTO);
  }
}
