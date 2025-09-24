package org.shining.boot11.product.entity;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

// 엔티티로 등록된 클래스는 부트 앱이 실행 될 때 자동으로 인식되어 테이블(Relational)과 매핑(Mapping)된다.
@Entity
@Getter
@Setter
public class Product {
  // 엔티티 설정 시 필수 사항
  // 식별자 설정 1 : 식별값을 직접 생성 하는 방식
  // - 엔티티명 == 테이블명
  // - 필드명   == 칼럼명
  @Id
  private String code;
  
  // String -> varchar(255)
  private String name;
  
  // Long -> bigint
  private Long stock;
  
  // Integer -> integer
  private Integer price;
  
  // LocalDate -> Date
  private LocalDate expiredAt;

  public Product() {
    // TODO Auto-generated constructor stub
  }
  public Product(String code, String name, Long stock, Integer price, LocalDate expiredAt) {
    super();
    this.code = code;
    this.name = name;
    this.stock = stock;
    this.price = price;
    this.expiredAt = expiredAt;
  }
  @Override
  public String toString() {
    return "Product [code=" + code + ", name=" + name + ", stock=" + stock + ", price=" + price + ", expiredAt="
        + expiredAt + "]";
  }
}
