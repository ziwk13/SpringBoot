package org.shark.boot07.user.controller;

import java.util.Map;

import org.shark.boot07.common.dto.PageDTO;
import org.shark.boot07.user.dto.enums.SortType;
import org.shark.boot07.user.dto.request.UserCreateRequestDTO;
import org.shark.boot07.user.dto.request.UserUpdateRequestDTO;
import org.shark.boot07.user.dto.response.ApiUserResponseDTO;
import org.shark.boot07.user.exception.ApiUserErrorResponseDTO;
import org.shark.boot07.user.exception.UserNotFoundException;
import org.shark.boot07.user.service.UserService;
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

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RequestMapping("/api/users")
@RequiredArgsConstructor
@RestController

/*
 * Swagger 설정 Annotation
 *  @Tag
 *  @Operation
 *  @ApiResponse
 *  @Parameter
 */

@Tag(name = "User API 목록", description = "사용자 관리 API")
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
  public ResponseEntity<ApiUserErrorResponseDTO> handleUserNotFoundException(UserNotFoundException e, Model model) {
    ApiUserErrorResponseDTO dto = ApiUserErrorResponseDTO.builder().errorCode("UF-100").errorMessage(e.getMessage()).build();
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
  @Operation(summary = "신규 사용자 등록"
           , description = "아이디(이메일), 비밀번호, 닉네임을 이용하는 신규 사용자 등록 API")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200"
                 , description = "사용자 등록 성공"
                 , content = @Content(schema = @Schema(implementation = ApiUserResponseDTO.class)))
  })
  public ResponseEntity<ApiUserResponseDTO> create(@Valid @RequestBody UserCreateRequestDTO user) {
    ApiUserResponseDTO createdUser = ApiUserResponseDTO.builder()
                                                 .status(200).message("회원 등록이 성공 했습니다")
                                                 .results(Map.of("createdUser", userService.createUser(user.toUserDTO())))
                                                 .build();
    return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
  }
  @PutMapping("/{uid}")
  @Operation(summary = "사용자 정보 수정"
           , description = "아이디(이메일), 비밀번호, 닉네임을 이용하는 기존 사용자 정보 수정 API")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200"
                 , description = "사용자 정보 수정 성공"
                 , content = @Content(schema = @Schema(implementation = ApiUserResponseDTO.class)))
})
  public ResponseEntity<ApiUserResponseDTO> update(@PathVariable(value = "uid") Long uid,@Valid @RequestBody UserUpdateRequestDTO user) {
    ApiUserResponseDTO updateUser = ApiUserResponseDTO.builder()
                                                .status(200).message("회원 정보가 수정 되었습니다")
                                                .results(Map.of("updatedUser", userService.updateUser(user.toUserDTO(), uid)))
                                                .build();
    
    return ResponseEntity.status(HttpStatus.OK).body(updateUser);
  }
  @DeleteMapping("/{uid}")
  @Operation(summary = "사용자 정보 삭제"
           , description = "아이디(이메일), 비밀번호, 닉네임을 이용하는 기존 사용자 정보 삭제 API")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200"
                 , description = "사용자 정보 삭제 성공"
                 , content = @Content(schema = @Schema(implementation = ApiUserResponseDTO.class)))
})
  public ResponseEntity<ApiUserResponseDTO> delete(@PathVariable(value = "uid") Long uid) {
    userService.deleteUser(uid);
    ApiUserResponseDTO dto = ApiUserResponseDTO.builder().status(200).message("회원 탈퇴가 완료 되었습니다").build();
    return ResponseEntity.ok(dto);
  }
  @GetMapping("/{uid}")  
  @Operation(summary = "사용자 정보 조회"
           , description = "아이디(이메일), 비밀번호, 닉네임을 이용하는 기존 사용자 정보 조회 API")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200"
                 , description = "사용자 정보 조회 성공"
                 , content = @Content(schema = @Schema(implementation = ApiUserResponseDTO.class)))
})
  public ResponseEntity<ApiUserResponseDTO> detail(@PathVariable(value = "uid") Long uid){
    ApiUserResponseDTO dto = ApiUserResponseDTO.builder()
                                         .status(200)
                                         .message("회원 조회 성공")
                                         .results(Map.of("foundUser", userService.getUserById(uid)))
                                         .build(); 
    return ResponseEntity.ok(dto);
  }
  @GetMapping
  @Operation(summary = "사용자 목록 조회"
           , description = "아이디(이메일), 비밀번호, 닉네임을 이용하는 기존 사용자들 정보 조회 API")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200"
                 , description = "사용자 정보 조회 성공"
                 , content = @Content(schema = @Schema(implementation = ApiUserResponseDTO.class)))
})
  @Parameters(value = {
      @Parameter(name = "page"
               , required = true
               , description = "조회할 페이지의 번호"
               , in = ParameterIn.QUERY
               , schema = @Schema(type = "integer", minimum = "1", example = "1"))
    , @Parameter(name = "size"
               , required = false
               , description = "한 페이지에 포함할 사용자 수"
               , in = ParameterIn.QUERY
               , schema = @Schema(type = "integer", defaultValue = "20", example = "20"))
    , @Parameter(name = "sort"
               , required = false
               ,description = "사용자 정렬 방식"
               , in = ParameterIn.QUERY
               , schema = @Schema(implementation = SortType.class))
  })
  public ResponseEntity<ApiUserResponseDTO> list(@RequestParam(value = "size") int size,
                                                 @RequestParam(value = "page") int page,
                                                 @RequestParam(value = "sort", defaultValue = "DESC") String sort) {
    PageDTO pageDTO = new PageDTO();
    pageDTO.setPage(page);
    pageDTO.setSize(size);
    ApiUserResponseDTO dto = ApiUserResponseDTO.builder()
                                         .status(200)
                                         .message("회원 목록 조회 성공")
                                         .results(Map.of("userList", userService.getUserList(pageDTO, sort)))
                                         .build();
    return ResponseEntity.ok(dto);
  }
}
