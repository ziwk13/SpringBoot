package org.shark.boot16.product.entity;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/*
 * 복합키로 사용할 클래스
 *  1. 기본 생성자
 *  2. equals, hashCode 오버라이드
 *  3. 직렬화(Serializable 인터페이스 구현)
 *  4. 복합키를 사용할 엔티티에 포함되도록 @Embeddable 처리
 */
@Embeddable
@EqualsAndHashCode
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OrderPaymentId implements Serializable {

  @Column(name = "order_id")
  private Integer orderId;
  @Column(name = "payment_id")
  private Integer paymentId;
  
  public static OrderPaymentId createOrderPaymentId(Integer orderId, Integer paymentId) {
    OrderPaymentId id = new OrderPaymentId();
    id.orderId = orderId;
    id.paymentId = paymentId;
    return id;
  }
}
