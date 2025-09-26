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
public class Name {

  @Column(name = "first_name")
  private String firstName;
  @Column(name = "last_name")
  private String lastName;
  
  public static Name createName(String firstName, String lastName) {
    Name name = new Name();
    name.firstName = firstName;
    name.lastName = lastName;
    return name;
  }
  @Override
  public String toString() {
    return "Name [firstName=" + firstName + ", lastName=" + lastName + "]";
  }
}
