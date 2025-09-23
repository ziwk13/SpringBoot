package org.shark.boot07.user.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class ApiUserErrorResponseDTO {

  private String errorCode;
  private String errorMessage;  // 필수 값 누락
  private String errorDetailMessage;  // 아이디는 필수 입니다.
}
