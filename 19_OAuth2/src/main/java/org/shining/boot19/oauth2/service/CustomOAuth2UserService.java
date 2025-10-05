package org.shining.boot19.oauth2.service;

import lombok.RequiredArgsConstructor;
import org.shining.boot19.oauth2.entity.CustomOAuth2User;
import org.shining.boot19.oauth2.entity.User;
import org.shining.boot19.oauth2.enums.Role;
import org.shining.boot19.oauth2.repository.UserRepository;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Map;

@RequiredArgsConstructor
@Service
public class CustomOAuth2UserService extends DefaultOAuth2UserService {
    private final UserRepository userRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        // 요청 정보 확인
        String accessToken = userRequest.getAccessToken().getTokenValue();
        System.out.println("accessToken: " + accessToken);
        ClientRegistration clientRegistration = userRequest.getClientRegistration();
        System.out.println("clientRegistration: " + clientRegistration);
        // 프로필 정보 처리
        OAuth2User oAuth2User = super.loadUser(userRequest);
        String registrationId = clientRegistration.getRegistrationId();
        if(registrationId.equals("kakao")) {
            return kakaoUser(oAuth2User);
        }
        return oAuth2User;
    }
    private OAuth2User kakaoUser(OAuth2User oAuth2User) {
        // 카카오가 보낸 모든 응답 정로률
        Map<String, Object> attributes = oAuth2User.getAttributes();
        // 카카오가 보낸 ID
        String kakaoId = attributes.get("id").toString();
        // 카카오가 보낸 properties
        Map<String, Object> properties = (Map<String, Object>) attributes.get("properties");
        //propertertoes에서 nickname, profile_image 꺼내기
        String nickname = (String)properties.get("profile_nickname");
        String profileImage =  (String)properties.get("profile_image");
        // 카카오가 보낸 프로필을 저장하거나, 그냥 사용하거나 선택
        User foundUser = userRepository.findByKakaoId(kakaoId).orElse(null);
        if(foundUser == null) {
            User user = User.createUser(kakaoId, nickname, profileImage, Role.USER);
            foundUser = userRepository.save(user);
        }
        // foundUser (카카오로부터 받은 프로필 정보)를 OAuth2User 타입으로 변경한 뒤 반환
        // 반환한 OAuth2User는 Authentication 객체에 포함되어 SecurityContext에 저장 된다.
        return new CustomOAuth2User(attributes, foundUser);
    }
}

