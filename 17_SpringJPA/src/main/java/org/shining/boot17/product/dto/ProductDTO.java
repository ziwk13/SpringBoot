package org.shining.boot17.product.dto;

import java.time.LocalDateTime;

import org.shining.boot17.product.entity.Category;
import org.shining.boot17.product.entity.Product;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class ProductDTO {

  private Integer productId;
  private String productName;
  private Integer productPrice;
  private Integer stockQuantity;
  private Boolean saleStatusYn;
  private String productDescription;
  private LocalDateTime registerDate;
  private Integer categoryId;
  private String categoryName;
  
  
  
  // DTO -> Entity
  public Product toEntity(Category category) {  // 연관관계에 있는 Category 가져오기
    Product p = Product.createProduct();
    p.setProductId(this.productId);
    p.setProductName(productName);
    p.setProductPrice(productPrice);
    p.setStockQuantity(stockQuantity);
    p.setSaleStatusYn(saleStatusYn = true);
    p.setProductDescription(productDescription);
    p.setRegisterDate(LocalDateTime.now());
    p.setCategory(category);
    return p;
  }
  
  // Entity -> DTO
  public static ProductDTO toDTO(Product entity) {
    ProductDTO dto = new ProductDTO();
    dto.setProductId(entity.getProductId());
    dto.setProductName(entity.getProductName());
    dto.setProductPrice(entity.getProductPrice());
    dto.setStockQuantity(entity.getStockQuantity());
    dto.setSaleStatusYn(entity.getSaleStatusYn());
    dto.setProductDescription(entity.getProductDescription());
    dto.setRegisterDate(entity.getRegisterDate());
    if(entity.getCategory() != null) {
      dto.setCategoryId(entity.getCategory().getCategoryId());
      dto.setCategoryName(entity.getCategory().getCategoryName());
    }
    return dto;
  }
}

