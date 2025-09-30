package org.shining.boot17.product.entity;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicUpdate;
import org.shining.boot17.product.dto.ProductDTO;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "products")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@DynamicUpdate
public class Product {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "product_id")
  private Integer productId;
  
  @Column(name = "product_name", length = 100, nullable = false)
  private String productName;
  
  @Column(name = "product_price", nullable = false)
  private Integer productPrice;
  
  @Column(name = "stock_quantity", nullable = false)
  private Integer stockQuantity;
  
  @Column(name = "sale_status_yn", nullable = false)
  private Boolean saleStatusYn;
  
  @Column(name = "product_description", columnDefinition = "TEXT")
  private String productDescription;
  
  @CreationTimestamp
  @Column(name = "register_date", nullable = false, updatable = false)
  private LocalDateTime registerDate;
  
  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "category_id", nullable = false)
  private Category category;
  
  public static Product createProduct() {
    return new Product();
  }
  
  public void update(ProductDTO productDTO){
      this.setProductName(productDTO.getProductName());
      this.setProductPrice(productDTO.getProductPrice());
      this.setStockQuantity(productDTO.getStockQuantity());
      this.setSaleStatusYn(productDTO.getSaleStatusYn());
      this.setProductDescription(productDTO.getProductDescription());
     if(productDTO.getCategoryName() != null){
         Category category = new Category();
         category.setCategoryName(productDTO.getCategoryName());
         this.setCategory(category);
     }
  }

  public static Product createProduct(String productName, Integer productPrice, Integer stockQuantity, Boolean saleStatusYn, String productDescription, LocalDateTime registerDate, Category category) {
    Product product = new Product();
    product.setProductName(productName);
    product.setProductPrice(productPrice);
    product.setStockQuantity(stockQuantity);
    product.setSaleStatusYn(saleStatusYn);
    product.setProductDescription(productDescription);
    product.setRegisterDate(registerDate);
    product.setCategory(category);
    return product;
  }
  @Override
  public String toString() {
    return "Product [productId=" + productId + ", productName=" + productName + ", productPrice=" + productPrice
        + ", stockQuantity=" + stockQuantity + ", saleStatusYn=" + saleStatusYn + ", productDescription="
        + productDescription + ", registerDate=" + registerDate + ", category=" + category + "]";
  }
}
