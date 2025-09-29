package org.shark.boot16.product.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "payment")
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
  private LocalDateTime paymentTime;
  
  @Column(name = "payment_amount", nullable = true)
  private Integer payment_amount;
  
}
