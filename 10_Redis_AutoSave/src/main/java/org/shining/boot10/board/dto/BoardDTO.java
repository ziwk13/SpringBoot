package org.shining.boot10.board.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class BoardDTO {

  private long bid;
  private String title;
  private String content;
  private String createdAt;
}
