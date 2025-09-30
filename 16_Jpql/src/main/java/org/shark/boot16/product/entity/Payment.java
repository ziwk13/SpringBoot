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
@Table(name = "payments")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Payment {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "payment_id")
  private Long paymentId;
  
  @Column(name = "payment_date", nullable = true)
  private LocalDate paymentDate;
  
  @Column(name = "payment_time", nullable = true)
  private LocalTime paymentTime;
  
  @Column(name = "payment_amount", nullable = true)
  private Integer paymentAmount;
  
  @Column(name = "payment_type")
  private String paymentType;
  
  @Column(name = "payment_status", length = 20)
  private String paymentStatus;
  
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id")
  private User user;
  
  @OneToMany(mappedBy = "payment")
  private List<OrderPayment> orderpayments;
  
  public static Payment createPayment(LocalDate date, LocalTime time, Integer amount,
                                      String type, String status, User user) {
    Payment payment = new Payment();
    payment.setPaymentDate(date);
    payment.setPaymentTime(time);
    payment.setPaymentAmount(amount);
    payment.setPaymentType(type);
    payment.setPaymentStatus(status);
    payment.setUser(user);
    return payment;
  }
  @Override
  public String toString() {
    return "Payment [paymentId=" + paymentId + ", paymentDate=" + paymentDate + ", paymentTime=" + paymentTime
        + ", paymentAmount=" + paymentAmount + ", paymentType=" + paymentType + ", paymentStatus=" + paymentStatus
        + ", user=" + user + ", orderpayments=" + orderpayments + "]";
  }
}
