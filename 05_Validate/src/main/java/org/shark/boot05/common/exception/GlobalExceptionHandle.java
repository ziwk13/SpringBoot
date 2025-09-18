package org.shark.boot05.common.exception;

import java.util.NoSuchElementException;

import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class GlobalExceptionHandle {
  
  @ExceptionHandler(IllegalArgumentException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public String handleIllegalArgumentException(IllegalArgumentException e, Model model) {
    model.addAttribute("error", "잘못된 접근 입니다. (" + e.getMessage() + ")");
    return "error/400";
  }
  @ExceptionHandler(NoSuchElementException.class)
  public String NoSuchElementException(NoSuchElementException e, Model model) {
    model.addAttribute("error", "잘못된 접근 입니다. (" + e.getMessage() + ")");
    return "error/400";
  }

}
