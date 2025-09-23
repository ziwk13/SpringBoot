package org.shining._08;

import java.time.LocalDateTime;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.shining_08.board.entity.Board;
import org.shining_08.board.repository.BoardRedisRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@TestInstance(Lifecycle.PER_CLASS)  // 인스턴스(객체)를 한 클래스당 1개만 생성한다.
class ApplicationTests {
/*
  @Autowired
  private RedisConnectionFactory redisConnectionFactory;
	@Test
	void contextLoads() {
	  Assertions.assertNotNull(redisConnectionFactory);
	}
*/
  @Autowired
  private BoardRedisRepository boardRedisRepository;
  
  private Board board;
  @BeforeAll  // 모든 @Test 이전에 실행 한다.
              // @TestInstance 설정으로 모든 @Test 메소드가 같은 인스턴스를 공유 하므로 굳이 @BeforeAll 메소드를 static 처리 할 필요가 없다.
   void setup() {
    board = new Board();
    board.setBid(1L);
    board.setTitle("테스트 제목");
    board.setContent("테스트내용");
    board.setCreatedAt(LocalDateTime.now());
  }
  @Test
  @DisplayName("Board 엔티티 저장")
  public void saveBoardTest() {
    // save 메소드
    // 전달한- 엔티티를 저장 하고, 저장된 엔티티를 반환 한다.
    Board savedBoard = boardRedisRepository.save(board);
    Assertions.assertNotNull(savedBoard);
    Assertions.assertEquals(board.getBid(), savedBoard.getBid());
  }
  @Test
  @DisplayName("Redis에서 Board 엔티티 조회하기")
  public void findBoardTest() {
    boardRedisRepository.save(board);
    Board foundBoard = boardRedisRepository.findById(board.getBid()).orElse(null);
    Assertions.assertNotNull(foundBoard);
  }
  @Test
  @DisplayName("Redis에서 Board 엔티티 전체 조회하기")
  public void findAllTest() {
    boardRedisRepository.save(board);
    boardRedisRepository.findAll().forEach(b -> System.out.println(b));
  }
  @Test
  @DisplayName("Redis에서 Board 엔티티 수정하기")
  public void updateBoardTest() {
    boardRedisRepository.save(board);  // 최초 저장
    String newTitle = "수정제목";
    board.setTitle(newTitle);
    Board updatedBoard = boardRedisRepository.save(board);  // 수정
    Assertions.assertEquals(newTitle, updatedBoard.getTitle());
  }
  @Test
  @DisplayName("Redis에서 Board 엔티티 삭제하기")
  public void deleteBoardTest() {
    boardRedisRepository.save(board);
    Long bid = board.getBid();
    boardRedisRepository.deleteById(bid);
    Optional<Board> found = boardRedisRepository.findById(bid);
    Assertions.assertFalse(found.isPresent());
  }
}
