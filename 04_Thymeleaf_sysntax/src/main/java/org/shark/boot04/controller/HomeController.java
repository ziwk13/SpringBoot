package org.shark.boot04.controller;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.shark.boot04.dto.BoardDTO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import jakarta.servlet.http.HttpSession;


@Controller
public class HomeController {

	@GetMapping("/")
	public String index() {
		return "index";  // templates/index.html을 의미 한다.
	}
	
	@GetMapping("/value/expr")
	public String value(Model model, HttpSession session) {
		model.addAttribute("name", "jackson");
		model.addAttribute("nickname", "<h3>Hulk</h3>");
		model.addAttribute("board", new BoardDTO(1L, "제목", "내용", LocalDateTime.now()));
		
		session.setAttribute("forecast", Map.of("weather", "rainy", "temperature", 27));
		return "value";
	}
	
	@GetMapping("/utility")
	public String utility(Model model) {
		model.addAttribute("title", "Spring Boot");
		model.addAttribute("content", "");
		model.addAttribute("hit", 1234);
		model.addAttribute("authors", Arrays.asList("김철수", "이형이"));
		model.addAttribute("pubdate", LocalDateTime.now());
		return "utility";
	}
	@GetMapping("/message/expr")
	public String message() {
		return "message";
	}
	@GetMapping("/javascript")
	public String javascript(Model model) {
		model.addAttribute("msg", Math.random() < 0.5 ? "성공" : "실패");
		if(Math.random() < 0.5) {
			model.addAttribute("result", "실행 결과 값");
		}
		return "javascript";
	}
	@GetMapping("/frag/expr")
	public String frag() {
		return "frag";
	}
	@GetMapping("/control")
	public String control(Model model) {
		List<BoardDTO> boardList = new ArrayList<>();
		if(Math.random() < 0.5) {
			boardList.add(new BoardDTO(1L, "제목입니다", "내용 이구요", LocalDateTime.now()));
			boardList.add(new BoardDTO(2L, "제목입니까", "내용 입니까", LocalDateTime.now()));
			boardList.add(new BoardDTO(3L, "제목이다", "내용 이요", LocalDateTime.now()));
		}
		model.addAttribute("boardList", boardList);
		return "control";
	}
	
	
}
