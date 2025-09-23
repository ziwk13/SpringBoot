package org.shark.boot07.user.dto.request;

import java.time.LocalDateTime;

import org.shark.boot07.user.dto.UserDTO;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@ToString
/*
 * 사용자 등록 DTO
 */

@Schema(description = "사용자 정보 수정을 위한 DTO")
public class UserUpdateRequestDTO {

  @Schema(description = "사용자 번호(식별자)")
  private Long uid;
  @Schema(description = "아이디(이메일)"
        , nullable = false
        , example = "ia@example.com")
  private String username;
  @Schema(description = "비밀번호"
        , nullable = false)
  @NotBlank(message = "비밀번호는 필수 입니다.")
  @Size(max = 100, message = "비밀번호의 최대 길이는 100자 입니다")
  private String password;
  @Schema(description = "닉네임"
        , nullable = false)
  @NotBlank(message = "닉네임은 필수 입니다.")
  @Size(max = 100, message = "닉네임의 최대 길이는 100자 입니다")
  private String nickname;
  @Schema(description = "등록일시")
  private LocalDateTime createdAt;
  
  // UserUpdateRequestDTO to UserDTO
  public UserDTO toUserDTO() {
    return UserDTO.builder()
                  .uid(uid)
                  .username(username)
                  .nickname(nickname)
                  .password(password)
                  .createdAt(createdAt)
                  .build();
  }
}
