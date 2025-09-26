package org.shining.boot13.company.entity;

import org.shining.boot13.commom.embedable.Address;
import org.shining.boot13.commom.embedable.Contact;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.AttributeOverrides;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
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
@Table(name = "companies")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Company {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "cid")
  private Long id;
  
  @Column(name = "company_name")
  private String companyName;
  
  @Embedded
  private Contact contact;
  
  @Embedded
  @AttributeOverrides({
    @AttributeOverride(name = "postcode", column = @Column(name = "company_postcode")),
    @AttributeOverride(name = "city", column = @Column(name = "company_city")),
    @AttributeOverride(name = "streeAddr", column = @Column(name = "streeAddr"))
  })
  private Address address;
  
  public static Company createCompany(String companyName, Contact contact, Address address) {
    Company company = new Company();
    company.companyName = companyName;
    company.contact = contact;
    company.address = address;
    return company;
  }
  @Override
  public String toString() {
    return "Company [id=" + id + ", companyName=" + companyName + ", contact=" + contact + ", address=" + address + "]";
  }
}
