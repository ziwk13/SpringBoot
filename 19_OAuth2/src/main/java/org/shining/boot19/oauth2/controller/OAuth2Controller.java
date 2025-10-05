package org.shining.boot19.oauth2.controller;

import org.shining.boot19.oauth2.entity.CustomOAuth2User;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class OAuth2Controller {

    @GetMapping("/")
    public String index() { return "index"; }// 반환이 void이면 메소드 이름이 뷰 이름(templates/index.html) 이다.

    @GetMapping("/login")
    public String login() { return "login"; }

    @GetMapping("/profile")
    public String profile(@AuthenticationPrincipal CustomOAuth2User customOAuth2User, Model model) {  // SecurityContext에 저장된 CostomOAuth2User 타입의 사용자 정보를 가져온 뒤, Model에 저장
        if(customOAuth2User != null) {
            model.addAttribute("user", customOAuth2User.getFoundUser());
        }
        return "profile";  // templates/profile.html
    }
}
