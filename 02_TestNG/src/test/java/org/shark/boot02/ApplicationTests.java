package org.shark.boot02;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.shark.boot02.board.dto.BoardDTO;
import org.shark.boot02.board.mapper.BoardMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.transaction.annotation.Transactional;
import org.testng.annotations.Test;

@Transactional
@SpringBootTest
class ApplicationTests extends AbstractTestNGSpringContextTests {
	
	@Autowired
	private BoardMapper boardMapper;
	private final static Logger log = LoggerFactory.getLogger(ApplicationTests.class);
	

	@Test(description = "컨텍스트 로드 테스트")
	void contextLoads() {
		assertNotNull(boardMapper);
		log.info("컨텍스트 로드 테스트 종료");
	}
	@Test(description = "현재 시간 가져오기 테스트")
	void nowTest() {
		log.info("NOW: {}", boardMapper.now());
	}
	@Test(description = "게시판 목록 가져오기 테스트")
	void boardListTest() {
		assertEquals(1, boardMapper.selectBoardList().size());
	}
	@Test(description = "게시판 상세 테스트")
	void boardDetailTest() {
		Long bid = 1L;
		assertEquals("테스트 제목", boardMapper.selectBoardById(bid).getTitle());
	}
	@Test(description = "신규 게시글 등록 테스트")
	void boardInsertTest() {
		BoardDTO board = new BoardDTO(null, "신규 제목", "신규 내용", null);
		assertEquals(1, boardMapper.insertBoard(board));
		assertEquals("신규 제목", boardMapper.selectBoardById(2L).getTitle());
		log.info("등록된 created_at: {}", boardMapper.selectBoardById(2L).getCreatedAt());
	}
	@Test(description = "게시글 삭제 테스트")
	void bardDeleteTest() {
		Long bid = 1L;
		boardMapper.deleteBoard(bid);
		assertNull(boardMapper.selectBoardById(bid));
	}
}
