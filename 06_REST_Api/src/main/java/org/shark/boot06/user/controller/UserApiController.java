package org.shark.boot06.user.controller;

import java.util.Map;

import org.shark.boot06.common.dto.PageDTO;
import org.shark.boot06.user.dto.UserDTO;
import org.shark.boot06.user.dto.response.ResponseUserDTO;
import org.shark.boot06.user.exception.ErrorResponseDTO;
import org.shark.boot06.user.exception.UserNotFoundException;
import org.shark.boot06.user.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RequestMapping("/api/users")
@RequiredArgsConstructor
@RestController
public class UserApiController {
  
  private final UserService userService;
  
  /*
  @ExceptionHandler(UserNotFoundException.class)
  @ResponseStatus(HttpStatus.NOT_FOUND)
  public ErrorResponseDTO handleUserNotFoundException(UserNotFoundException e, Model model) {
    return ErrorResponseDTO.builder().status(404).errorMessage(e.getMessage()).build();
  }
  */
  @ExceptionHandler(UserNotFoundException.class)
  @ResponseStatus(HttpStatus.NOT_FOUND)
  public ResponseEntity<ErrorResponseDTO> handleUserNotFoundException(UserNotFoundException e, Model model) {
    ErrorResponseDTO dto = ErrorResponseDTO.builder().errorCode("UF-100").errorMessage(e.getMessage()).build();
    return ResponseEntity.status(404).body(dto); 
  }
  /*
   * Postman 요청 시 주의 상항
   * 
   * 1. x-www-form-urlencoded 형식으로 데이터를 보내는 경우 (폼을 사용하는 경우)
   *    create(UserDTO user)
   * 2. raw 형식 중 JSON 데이터를 보내는 경우
   *    create(@RequestBody UserDTO user)
   */
  @PostMapping
  public ResponseEntity<ResponseUserDTO> create(@RequestBody UserDTO user) {
    ResponseUserDTO createdUser = ResponseUserDTO.builder()
                                                 .status(201).message("회원 등록이 성공 했습니다")
                                                 .results(Map.of("createdUser", userService.createUser(user)))
                                                 .build();
    return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
  }
  @PutMapping("/{uid}")
  public ResponseEntity<ResponseUserDTO> update(@PathVariable(value = "uid") Long uid, @RequestBody UserDTO user) {
    ResponseUserDTO updateUser = ResponseUserDTO.builder()
                                                .status(200).message("회원 정보가 수정 되었습니다")
                                                .results(Map.of("updatedUser", userService.updateUser(user, uid)))
                                                .build();
    
    return ResponseEntity.status(HttpStatus.OK).body(updateUser);
  }
  @DeleteMapping("/{uid}")
  public ResponseEntity<ResponseUserDTO> delete(@PathVariable(value = "uid") Long uid) {
    userService.deleteUser(uid);
    ResponseUserDTO dto = ResponseUserDTO.builder().status(200).message("회원 탈퇴가 완료 되었습니다").build();
    return ResponseEntity.ok(dto);
  }
  @GetMapping("/{uid}")
  public ResponseEntity<ResponseUserDTO> detail(@PathVariable(value = "uid") Long uid){
    ResponseUserDTO dto = ResponseUserDTO.builder()
                                         .status(200)
                                         .message("회원 조회 성공")
                                         .results(Map.of("foundUser", userService.getUserById(uid)))
                                         .build(); 
    return ResponseEntity.ok(dto);
  }
  @GetMapping
  public ResponseEntity<ResponseUserDTO> list(@RequestParam(value = "size") int size,
                                              @RequestParam(value = "page") int page,
                                              @RequestParam(value = "sort", defaultValue = "DESC") String sort,
                                              PageDTO pageDTO) {
    pageDTO.setPage(page);
    pageDTO.setSize(size);
    ResponseUserDTO dto = ResponseUserDTO.builder()
                                         .status(200)
                                         .message("회원 목록 조회 성공")
                                         .results(Map.of("userList", userService.getUserList(pageDTO, sort)))
                                         .build();
    return ResponseEntity.ok(dto);
  }
}
