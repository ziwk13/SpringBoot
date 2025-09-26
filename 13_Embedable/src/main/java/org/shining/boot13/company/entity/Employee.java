package org.shining.boot13.company.entity;

import org.shining.boot13.commom.embedable.Address;
import org.shining.boot13.commom.embedable.Name;

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
@Table(name = "employees")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Employee {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "eid")
  private Long id;
  
  @Embedded
  private Name name;
  
  @Embedded
  private Address address;
  
  public static Employee createEmployee(Name name, Address address) {
    Employee employee = new Employee();
    employee.name = name;
    employee.address = address;
    return employee;
  }
  @Override
  public String toString() {
    return "Employee [id=" + id + ", name=" + name + ", address=" + address + "]";
  }
  
}
