package org.shining.boot13.commom.embedable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Embeddable
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Address {

  private String postcode;
  
  private String city;
  
  @Column(name = "street_addr")
  private String streetAddr;
  
  public static Address createAddress(String postcode, String city, String streetAddr) {
    Address address = new Address();
    address.postcode = postcode;
    address.city = city;
    address.streetAddr = streetAddr;
    return address;
  }
  @Override
  public String toString() {
    return "Address [postcode=" + postcode + ", city=" + city + ", streetAddr=" + streetAddr + "]";
  }
  
}
