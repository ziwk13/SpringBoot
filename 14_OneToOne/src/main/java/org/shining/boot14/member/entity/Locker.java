package org.shining.boot14.member.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "lockers")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Locker {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "locker_id")
  private Long id;
  
  private String location;
  // 1:1 연관 관계 양방향 설정을 위해 역방향(자식 -> 부모 방향) 설정이 필요
  @OneToOne(mappedBy = "locker",  // Member의 locker 필드 참조
            fetch = FetchType.LAZY
      )
  
  private Member member;
  
  public static Locker createLocker(String location) {
    Locker locker = new Locker();
    locker.setLocation(location);
    return locker;
  }
  @Override
  public String toString() {
    return "Locker [id=" + id + ", location=" + location + ", member=" + member + "]";
  }
}
