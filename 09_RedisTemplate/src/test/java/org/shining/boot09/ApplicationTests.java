package org.shining.boot09;

import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.shining.boot09.board.dto.BoardDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import lombok.extern.slf4j.Slf4j;

@SpringBootTest
@Slf4j
class ApplicationTests {
  
  @Autowired
  private RedisTemplate<String, String> redisTemplateString; 
  
  @Autowired
  private RedisTemplate<String, BoardDTO> redisTemplateBoard;

  private final String key = "test:redis";
  private final String value = "In Memory NoSQL Database";
	@Test
	@DisplayName("Redis에 값 저장하기")
	void saveTest() {
	  ValueOperations<String, String> ops = redisTemplateString.opsForValue();
	  ops.set(key, value);  // 저장
	}

	@Test
	@DisplayName("Redis에 저장된 값 조회하기")
	void findTest() {
	  ValueOperations<String, String> ops = redisTemplateString.opsForValue();
	  String value = ops.get(key);
	  Assertions.assertEquals(this.value, value);
	}
	@Test
	@DisplayName("Redis에 저장된 값 삭제하기")
	void deleteTest() {
	  redisTemplateString.delete(key);
	  ValueOperations<String, String> ops = redisTemplateString.opsForValue();
	  String value = ops.get(key);
	  Assertions.assertNull(value);
	}
	@Test
	@DisplayName("TTL 테스트 하기")
	void ttlTest() {
	  ValueOperations<String, String> ops = redisTemplateString.opsForValue();
	  ops.set(key, value, 30, TimeUnit.SECONDS);
	  try {
      Thread.sleep(5000);  // 5초 멈추기
    } catch (Exception e) {
      e.printStackTrace();
    }
	  long remainTtl = redisTemplateString.getExpire(key);
	  log.info("남은 TTL:{}", remainTtl);
	}
	@Test
	void boardSaveTest() {
	  BoardDTO value = new BoardDTO();
	  value.setBid(1L);
	  value.setTitle("테스트제목");
	  value.setContent("테스트내용");
	  value.setCreatedAt(LocalDateTime.now().toString());
	  ValueOperations<String, BoardDTO> ops = redisTemplateBoard.opsForValue();
	  ops.set("board:1", value, 10, TimeUnit.SECONDS);
	}
}
