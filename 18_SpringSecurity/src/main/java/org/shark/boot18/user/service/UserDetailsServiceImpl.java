package org.shark.boot18.user.service;

import lombok.RequiredArgsConstructor;
import org.shark.boot18.user.entity.LoginUser;
import org.shark.boot18.user.entity.User;
import org.shark.boot18.user.repository.LoginRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl  implements UserDetailsService {

    private final LoginRepository loginRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
     /*   // 테스트 (계정명이 goodee 이면 로그인)
        if(username.equals("goodee")){
            return new LoginUser("goodee", "{noop}1234", Collections.emptyList());
        } else {
            throw  new UsernameNotFoundException(username + "사용자 존재 안함");
        }*/
        // DB에서 사용자 정보를 불러오는 작업을 수행한다.
        User foundUser = loginRepository.findByUsername(username).orElse(null);
        System.out.println(foundUser);
        if(foundUser == null) {
            throw new UsernameNotFoundException(username + " 사용자가 존재하지 않습니다 ");
        }
        // DB에서 조회한 사용자 정보를 UserDetails 타입의 객체를 반환 한다.
        // 반환한 객체가 가진 값을 이용해 Spring Security는 내부에서 비교를 진행 한다.
        return new LoginUser(foundUser.getUsername(),foundUser.getPassword(), Collections.emptyList());
    }
}
