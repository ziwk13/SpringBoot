package org.shining.boot14.company.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "employees")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Employee {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "eid")
  private Long id;
  
  private String name;
  
  @OneToOne(cascade = CascadeType.PERSIST)
  @JoinColumn(name = "address_id")
  private Address address;
  
  public static Employee createEmployee(String name) {
    Employee employee = new Employee();
    employee.name = name;
    return employee;
  }
  public void assignAddress(Address address) {
    this.address = address;
  }
  public void removeAddress(Address address) { 
    if(this.address != null) {
      this.address = null;
    }
  }
  @Override
  public String toString() {
    return "Employee [id=" + id + ", name=" + name + ", address=" + address + "]";
  }
}
