package org.shining.boot15.products.entity;


import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "categories")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Category {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "category_id")
  private Long id;
  
  @Column(name = "category_name")
  private String categoryName;
  
  private String description;
  
  // 양방향 매핑 추가
  @OneToMany(mappedBy = "category", fetch = FetchType.LAZY)
  private List<Product> products = new ArrayList<>();
  
  public static Category createCategory(String categoryName, String description) {
    Category categorie = new Category();
    categorie.categoryName = categoryName;
    categorie.description = description;
    return categorie;
  }
  public void addProduct(Product product) {
    products.add(product);
    product.setCategory(this);
  }
  public void removeProduct(Product product) {
    products.remove(product);
    product.setCategory(null);
  }
  @Override
  public String toString() {
    return "Categorie [categoryId=" + id + ", categoryName=" + categoryName + ", description=" + description
        + ", products=" + products.size() + "]";
  }
}
