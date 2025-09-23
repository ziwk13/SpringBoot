package org.shining.practice.user.dto;

import java.time.LocalDateTime;

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
@ToString
@Builder
public class UserDTO {
  
  private long uid;
  private String username;
  private String password;
  private String nickname;
  private LocalDateTime createdAt;
  private LocalDateTime updatedAt;
  private int deleted;
  

}
