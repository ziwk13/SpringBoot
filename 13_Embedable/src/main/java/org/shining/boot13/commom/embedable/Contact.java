package org.shining.boot13.commom.embedable;

import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Embeddable
@Setter
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Contact {

  private String email;
  
  private String tel;
  
  private String fax;
  
  public static Contact createContact(String email, String tel, String fax) {
    Contact contact = new Contact();
    contact.email = email;
    contact.tel = tel;
    contact.fax = fax;
    return contact;
  }
  @Override
  public String toString() {
    return "Contact [email=" + email + ", tel=" + tel + ", fax=" + fax + "]";
  }
}
