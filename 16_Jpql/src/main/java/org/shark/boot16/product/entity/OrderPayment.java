package org.shark.boot16.product.entity;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

// Order   : 각 주문은 여러 결제 수단을 가질 수 있다.
// Payment : 각 결제 수단은 여러 주문에서 사용될 수 있다.

@Entity
@Table(name = "order_Paymeny")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OrderPayment {

  @EmbeddedId   // 복합키 클래스(@Embeddable 객체)를 ID로 지정
  private OrderPaymentId id;
  
  // 복합키의 orderId 필드와 연관관계 필드 Order order 엔티티를 매핑하는 작업
  // 연관관계를 맺는 Order order 엔티티의 ID 값이 복합키의 orderId 필드에 자동으로 동기화가 된다.
  @MapsId("orderId")  // 복합키의 orderId 빌드를 지정
  @ManyToOne
  @JoinColumn(name = "order_id")
  private Order order;
  
  @MapsId("paymentId")
  @ManyToOne
  @JoinColumn(name = "payment_id")
  private Payment payment;
  
  public static OrderPayment createOrderPayment(Order order, Payment payment) {
    OrderPayment op = new OrderPayment();
    op.order = order;
    op.payment = payment;
    return op;
  }
  @Override
  public String toString() {
    return "OrderPayment [id=" + id + ", order=" + order + ", payment=" + payment + "]";
  }
}
