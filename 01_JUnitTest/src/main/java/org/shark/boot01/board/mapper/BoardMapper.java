package org.shark.boot01.board.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.shark.boot01.board.dto.BoardDTO;

/*
 * MyBatis Interface 방식 (둘 중 한 가지 방법을 사용)
 * 
 * 1. 기존의 복잡한 DAO 구현 클래스 작성을 생략할 수 있는 편리한 방법이다.
 * 2. 인터페이스만으로 DAO를 구현하거나, XML 매퍼와 인터페이스를 조합하여 DAO를 구현할 수 있다.
 * 3. 인터페이스만으로 DAO를 구현할 대는 어노테이션 기반의 SQL 매핑 방식을 사용한다.
 * 		1) @Select: SELECT 쿼리 실행
 *		2) @Insert: INSERT 쿼리 실행
 *		3) @Update: UPDATE 쿼리 실행
 *		4) @Delete: DELETE 쿼리 실행
 */

/*
 * Mapper Interface 등록 방법
 * 
 * 1. @Mapper
 * 	1) 하나의 Mapper Interface를 등록 한다.
 * 	2) Mapper Interface마다 추가한다.
 * 2. @MapperScan
 * 	1) 여러 Mapper Interface를 등록 한다.
 * 	2) Configuration 클래스에 Mapper Interface가 저장된 패키지를 등록
 */

@Mapper
public interface BoardMapper {

	@Select(value = "SELECT NOW()")
	String now();


	@Select(value = "SELECT * FROM tbl_board ORDER BY bid DESC") 
	List<BoardDTO> selectBoardList();


	@Select(value = "SELECT * FROM tbl_board WHERE bid = #{bid} ORDER BY bid DESC")
	BoardDTO selectBoardById(@Param(value = "bid") Long bid);

	@Insert(value = "INSERT INTO tbl_board VALUES (null, #{title}, #{content}, NOW())")
	int insertBoard(BoardDTO board);

	@Update(value = "UPDATE tbl_board SET title = #{title}, content = #{content} WHERE bid = #{bid}")
	int updateBoard(BoardDTO board);

	@Delete(value = "DELETE FROM tbl_board WHERE bid = #{bid}")
	int deleteBoard(@Param(value = "bid") Long bid);
}
