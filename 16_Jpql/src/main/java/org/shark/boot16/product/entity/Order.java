package org.shark.boot16.product.entity;
import java.security.Timestamp;
import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "orders")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Order {

  @Id
  @Column(name = "order_id")
  private Long orderId;
  
  @Column(name = "order_date")
  private Date orderDate;
  
  @Column(name = "order_time")
  private Timestamp orderTime;
  
  @Column(name = "total_order_amount")
  private Integer totalOrderAmount;
  
  @Column(name = "order_status")
  private String orderStatus;
  
  @Column(name = "user_id")
  private Long id;
}