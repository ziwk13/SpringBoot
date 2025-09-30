package org.shark.boot16.product.entity;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

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
@Table(name = "orders")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Order {

  @Id
  @Column(name = "order_id")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long orderId;
  
  @Column(name = "order_date", nullable = false)
  private LocalDate orderDate;
  
  @Column(name = "order_time", nullable = false)
  private LocalTime orderTime;
  
  @Column(name = "total_order_amount", nullable = false)
  private Integer totalOrderAmount;
  
  @Column(name = "order_status", length = 20, nullable = false)
  private String orderStatus;
  
  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "user_id")
  private User user;
  
  @OneToMany(mappedBy = "order")
  private List<OrderPayment> order;
  
  public static Order createOrder(User user, LocalDate date, LocalTime time, int total, String status) {
    Order o = new Order();
    o.setUser(user);
    o.setOrderDate(date);
    o.setOrderTime(time);
    o.setTotalOrderAmount(total);
    o.setOrderStatus(status);
    return o;
  }
  @Override
  public String toString() {
    return "Order [orderId=" + orderId + ", orderDate=" + orderDate + ", orderTime=" + orderTime + ", totalOrderAmount="
        + totalOrderAmount + ", orderStatus=" + orderStatus + ", user=" + user + ", order=" + order + "]";
  }
}