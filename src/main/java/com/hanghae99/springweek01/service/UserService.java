package com.hanghae99.springweek01.service;

import com.hanghae99.springweek01.dto.SignupRequestDto;
import com.hanghae99.springweek01.domain.User;
import com.hanghae99.springweek01.domain.UserRoleEnum;
import com.hanghae99.springweek01.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;
import java.util.regex.Pattern;

@Service
public class UserService {
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private static final String ADMIN_TOKEN = "AAABnv/xRVklrnYxKZ0aHgTBcXukeZygoC";

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void registerUser(SignupRequestDto requestDto) {
// 회원 ID 중복 확인
        String username = requestDto.getUsername();
        String passwordch = requestDto.getPassword();
        String repassword = requestDto.getRepassword();
        Optional<User> found = userRepository.findByUsername(username);
        if(!Objects.equals(repassword, passwordch)){
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다");
        }
        if (found.isPresent()) {
            throw new IllegalArgumentException("중복된 사용자 ID 가 존재합니다");
        }
        if(username.length()<3){
            throw new IllegalArgumentException("ID는 최소 3자 이상이여야 합니다");
        }
        if(passwordch.length()<4){
            throw new IllegalArgumentException("PW는 최소 4자 이상이여야 합니다");
        }
        if(!Pattern.matches("^[a-zA-Z0-9]*$", username)){
            throw new IllegalArgumentException("ID는 알파벳과 숫자만 입력이 가능합니다");
        }
        if(passwordch.contains(username)){
            throw new IllegalArgumentException("ID가 포함되지 않은 비밀번호를 사용해주세요");
        }

// 패스워드 암호화
        String password = passwordEncoder.encode(requestDto.getPassword());
        String email = requestDto.getEmail();

// 사용자 ROLE 확인
        UserRoleEnum role = UserRoleEnum.USER;
        if (requestDto.isAdmin()) {
            if (!requestDto.getAdminToken().equals(ADMIN_TOKEN)) {
                throw new IllegalArgumentException("관리자 암호가 틀려 등록이 불가능합니다.");
            }
            role = UserRoleEnum.ADMIN;
        }

        User user = new User(username, password, email, role);
        userRepository.save(user);
    }
}