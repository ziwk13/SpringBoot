package org.shark.boot16.product.entity;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
  private Long categoryId;
  
  @Column(name = "category_name", nullable = true, length = 50)
  private String categoryName;
  
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "parent_category_id")
  private Category parent;
  
  @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL)
  private List<Category> children;
  
  @OneToMany(mappedBy = "category")
  private List<Product> products;
  
  public static Category createCategory(String categroyName, Category parent) {
    Category category = new Category();
    category.setCategoryName(categroyName);
    category.setParent(parent);
    return category;
  }
  // @ManyToOne은 포함, @OneToMany는 불포함
  @Override
  public String toString() {
    return "Category [categoryId=" + categoryId
        + ", categoryName=" + categoryName
        + ", parent=" + (parent != null ? parent : null) + "]";
  }
}
