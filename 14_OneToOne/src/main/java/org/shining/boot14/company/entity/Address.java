package org.shining.boot14.company.entity;

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
@Table(name = "addresses")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Address {

  @Id
  @Column(name = "aid")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  
  private String postcode;
  private String city;
  private String streetAddr;
  
  public static Address createAddress(String postcode, String city, String streetAddr) {
    Address address = new Address();
    address.setPostcode(postcode);
    address.setCity(city);
    address.setStreetAddr(streetAddr);
    return address;
  }
  @Override
  public String toString() {
    return "Address [id=" + id + ", postcode=" + postcode + ", city=" + city + ", streetAddr=" + streetAddr + "]";
  }
}
