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
@Table(name = "users")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
public class User {
  
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "user_id")
  private Integer userId;
  
  @Column(nullable = true, length = 50)
  private String username;
  
  @Column(nullable = false, length = 100)
  private String email;
  
  @Column(name = "phone_number", nullable = false, length = 20)
  private String phoneNumber;
  
  @Column(nullable = false, length = 200)
  private String address;
  
  @Column(name = "birth_date")
  private LocalDate birthDate;
  
  @Column(name = "join_date", nullable = false)
  private LocalDateTime joinDate;
  
  @Column(name = "member_grade", nullable = false)
  private String memberGrade = "GENERAL";

  @Column(name = "withdraw_yn", nullable = false, length = 1)
  private Boolean withdrawYn = false;

  /*
   * @OneToMany(mappedBy = "user") private List<Order> orders;
   * 
   * @OneToMany(mappedBy = "user") private List<Payment> payments;
   */
  
  public static User createUser(String username, String email, String phoneNumber, String address, LocalDate birthDate) {
    User user = new User();
    user.setUsername(username);
    user.setEmail(email);
    user.setPhoneNumber(phoneNumber);
    user.setAddress(address);
    user.setBirthDate(birthDate);
    user.setJoinDate(LocalDateTime.now());
    user.setMemberGrade("GENERAL");
    user.setWithdrawYn(false);
    return user;
  }

  @Override
  public String toString() {
    return "User [userId=" + userId + ", username=" + username + ", email=" + email + ", phoneNumber=" + phoneNumber
        + ", address=" + address + ", birthDate=" + birthDate + ", joinDate=" + joinDate + ", memberGrade="
        + memberGrade + ", withdrawYn=" + withdrawYn + "]";
  }
}
