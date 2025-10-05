package org.shining.boot19.oauth2.entity; // 패키지명 오타남 entity

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.shining.boot19.oauth2.enums.Role;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "kakao_id", unique = true)
    private String kakaoId;

    @Column(name = "profile_nickname")
    private String profileNickName;

    @Column(name = "profile_image")
    private String profileImage;

    @Enumerated(EnumType.STRING)
    private Role role;

    public static User createUser(String kakaoId, String profileNickName, String profileImage, Role role) {
        User user = new User();
        user.kakaoId = kakaoId;
        user.profileNickName = profileNickName;
        user.profileImage = profileImage;
        user.role = Role.USER;
        return user;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", kakaoId='" + kakaoId + '\'' +
                ", profileNickName='" + profileNickName + '\'' +
                ", profileImage='" + profileImage + '\'' +
                ", role=" + role +
                '}';
    }

}