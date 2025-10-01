package org.shark.boot18.user.controller;

import org.shark.boot18.user.dto.LoginDTO;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/login")
public class LoginController {

    @GetMapping
    public String login(LoginDTO dto) {  // Model에 저장한 이름은 loginDTO
        return "login";  // templates/login.html
    }
}