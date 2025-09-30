package org.shark.boot16.product.entity;

import jakarta.persistence.Column;
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

@Entity
@Table(name = "order_Product")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OrderProduct {

  @EmbeddedId
  private OrderProductId id;
  
  @MapsId("orderId")
  @ManyToOne
  @JoinColumn(name = "order_id")
  private Order order;
  
  @MapsId("producetId")
  @ManyToOne
  @JoinColumn(name = "product_id")
  private Product product;
  
  @Column(name = "order_quantity", nullable = false)
  private Integer orderQuantity;
  
  @Column(name = "unit_price", nullable = false)
  private Integer unitPrice;
  
  public static OrderProduct createOrderProduct(Order order, Product product, Integer orderQuantity, Integer unitPrice) {
    OrderProduct op = new OrderProduct();
    op.order = order;
    op.product = product;
    op.orderQuantity = orderQuantity;
    op.unitPrice = unitPrice;
    return op;
  }
}
