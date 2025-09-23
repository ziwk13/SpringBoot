package org.shining_08.board.entity;

import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString

@RedisHash(value = "board", timeToLive = 30)  // board를 key의 prefix로 사용 (board:1, board:2, ... )한다.
public class Board {                       // timeToLive 데이터는 Redisp 30초 동안만 유지된다. 30초 이후 자동으로 삭제 된다.
  // Redis에 BoardDTO 저장 시 board:{bid} 형식의 key를 사용한다.
  @Id
  private Long bid;
  private String title;
  private String content;
  private LocalDateTime createdAt;

}
