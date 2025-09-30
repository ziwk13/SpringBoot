package org.shark.boot16.product.entity;

import java.io.Serializable;

import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Embeddable
@EqualsAndHashCode
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OrderProductId implements Serializable {

  private Integer orderId;
  
  private Integer productId;
  
  public static OrderProductId createOrderProductId(Integer orderId, Integer productId) {
    OrderProductId id = new OrderProductId();
    id.orderId = orderId;
    id.productId = productId;

    return id;
  }
}
