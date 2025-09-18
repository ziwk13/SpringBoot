package org.shark.boot05.board.dto;

import java.time.LocalDateTime;

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
public class BoardDTO {

	private Long bid;
	
	@NotBlank(message = "제목은 필수 입니다")
	@Size(max = 100, message = "제목의 최대 글자 수는 100자 입니다")
	private String title;
	private String content;
	private LocalDateTime createdAt;
	
}