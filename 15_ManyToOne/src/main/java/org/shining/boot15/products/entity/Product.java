package org.shining.boot15.products.entity;

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
public class Product {
  
  @Id
  @Column(name = "product_id")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  
  @Column(name = "product_name")
  private String productName;
  
  private Double price;
  
  private Integer stock;
  
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "category_id")
  private Category category;
  
  public static Product createProduct(String productName, Double price, Integer stock, Category category) {
    Product product = new Product();
    product.productName = productName;
    product.price = price;
    product.stock = stock;
    product.category = category;
    return product;
  }
  @Override
  public String toString() {
    return "Product [productId=" + id + ", productName=" + productName + ", price=" + price + ", stock=" + stock
        + ", categoryId=" + category + "]";
  }
}
