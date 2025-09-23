package org.shining.boot09.board.service;

import org.shining.boot09.board.dto.BoardDTO;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BoardRedisService {

  private final RedisTemplate<String, BoardDTO> redisTemplate;
  
  private static final String PREFIX = "board:"; 
  
  /*
   * key 형식
   *  board:1
   *  board:2
   *  ...
   *  -------
   *  board:{bid}
   */
  /*
   * Redis에 저장되는 형식의 예시
   * 
   * board:1 -> {
   *  "title": "제목",
   *  "content": "내용",
   *  "createdAT": "2025-09-23T10:00:00"
   */
  // 저장
  public void save(BoardDTO boardDTO) {
    ValueOperations<String, BoardDTO> ops = redisTemplate.opsForValue();
    ops.set(PREFIX + boardDTO.getBid(), boardDTO);
  }
  // 조회 (bid를 받아서 해당하는 BoardDTO 객체 반환)
  public BoardDTO findById(Long bid) {
    ValueOperations<String, BoardDTO> ops = redisTemplate.opsForValue();
    return ops.get(PREFIX + bid);
  }
  // 삭제
  public void deleteById(Long bid) {
    redisTemplate.delete(PREFIX + bid);
  }
}
