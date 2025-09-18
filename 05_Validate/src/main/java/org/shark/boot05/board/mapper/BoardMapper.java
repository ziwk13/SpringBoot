package org.shark.boot05.board.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.shark.boot05.board.dto.BoardDTO;

/*
 * Mapper Interface와 XML Mapper 활용 규칙
 * 
 * 1. XML의 namespace는 반드시 Mapper Interface의 전체 클래스명과 일치해야 한다.
 * 2. XML의 SQL id는 반드시 Mapper Interface의 메소드명과 일치해야 한다.
 */

@Mapper
public interface BoardMapper {
	int selectBoardCount();
	List<BoardDTO> selectBoardList(Map<String, Object> map);
	BoardDTO selectBoardById(Long bid);
	int insertBoard(BoardDTO board);
	int updateBoard(BoardDTO board);
	int deleteBoard(Long bid);
}
