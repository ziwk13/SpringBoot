package org.shark.boot03;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.shark.boot03.board.dto.BoardDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

/*
 * MockMvc
 * 
 *  1. Spring 프레임워크에서 제공하는 웹 애플리케이션 테스트 라이브러리
 *  2. 서버 구동 없이 Spring MVC 동작을 재현할 수 있다
 *  3. Mock Object(가짜 객체)를 만들어서 서버에 배포하지 않고도 동작을 확인 할 수 있다.
 *  4. @AutoConfigureMockMvc 어노테이션을 클래스 레벨에 등록하면 자동으로 MockMvc 객체가 생성된다.
 */

@AutoConfigureMockMvc
@SpringBootTest
class ApplicationTests {

	@Autowired
	private MockMvc mockMvc;
	
	@Test
	@DisplayName("MockMvc 객체 생성 테스트")
	void contextLoads() {
		assertNotNull(mockMvc);
	}
	@Test
	@DisplayName("신규 게시글 등록 테스트")
	public void createBoardTest() throws Exception {
		BoardDTO board = new BoardDTO(null, "테스트 제목", "테스트 내용", null);
		
		mockMvc.perform(post("/board/write")
				.contentType(MediaType.APPLICATION_FORM_URLENCODED)
				.flashAttr("boardDTO", board)  // 컨트롤러에서 이름을 명시하지 않으면, 클래스명을 camelCase로 변환한 이름을 사용한다.
										       // @ModelAttribute BoardDTO board 또는 BoardDTO board
				// .param("title", "테스트 제목")
				// .param("content", "테스트 내용"));
				).andDo(MockMvcResultHandlers.print())
				.andExpect(MockMvcResultMatchers.status().is3xxRedirection())
				.andExpect(MockMvcResultMatchers.redirectedUrl("/board/list"))
				.andExpect(MockMvcResultMatchers.flash().attribute("msg", "등록 성공"));
	}
	
	@Test
	@DisplayName("게시글 상세 보기 테스트")
	public void boardDetailTest() throws Exception {
		mockMvc.perform(get("/board/detail")
				.param("bid", "1"))
				.andDo(MockMvcResultHandlers.print())
				.andExpect(MockMvcResultMatchers.status().isOk());
		
	}
}
