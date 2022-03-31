package com.hanghae99.springweek01.service;

import com.hanghae99.springweek01.domain.User;
import com.hanghae99.springweek01.domain.UserRoleEnum;
import com.hanghae99.springweek01.dto.SignupRequestDto;
import com.hanghae99.springweek01.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Objects;
import java.util.Optional;
import java.util.regex.Pattern;

import static org.junit.jupiter.api.Assertions.*;


@Nested
@ExtendWith(MockitoExtension.class)
class UserServiceTest {
    @Mock
    UserRepository userRepository;
    private String username;
    private String passwordch;
    private String repassword;

    @BeforeEach
    void setup(){
         username = "langho";
         passwordch = "1234";
        repassword = "1234";
    }

    @Test
    @DisplayName("정상 케이스")
    void checkUsername() {

// 회원 ID 중복 확인

        Optional<User> found = userRepository.findByUsername(username);
        if (!Objects.equals(repassword, passwordch)) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다");
        }
        if (found.isPresent()) {
            throw new IllegalArgumentException("중복된 사용자 ID 가 존재합니다");
        }
        if (username.length() < 3) {
            throw new IllegalArgumentException("ID는 최소 3자 이상이여야 합니다");
        }
        if (!Pattern.matches("^[a-zA-Z0-9]*$", username)) {
            throw new IllegalArgumentException("ID는 알파벳과 숫자만 입력이 가능합니다");
        }
        if (passwordch.length() < 4) {
            throw new IllegalArgumentException("PW는 최소 4자 이상이여야 합니다");
        }
        if (passwordch.contains(username)) {
            throw new IllegalArgumentException("ID가 포함되지 않은 비밀번호를 사용해주세요");
        }
    }

    @Nested
    @DisplayName("실패 케이스")
    class FailCase{
        @Nested
        @DisplayName("닉네임 체크")
        class userName{
            @Test
            @DisplayName("ID 2자")
            void checkUsername1() {
                username = "as";
                try {
                    Optional<User> found = userRepository.findByUsername(username);
                    if (username.length() < 3) {
                        throw new IllegalArgumentException("ID는 최소 3자 이상이여야 합니다");
                    }
                    if (!Pattern.matches("^[a-zA-Z0-9]*$", username)) {
                        throw new IllegalArgumentException("ID는 알파벳과 숫자만 입력이 가능합니다");
                    }
                }catch (IllegalArgumentException e){
                    assertEquals("ID는 최소 3자 이상이여야 합니다", e.getMessage());
                }

        }
        @Test
        @DisplayName("알파벳과 숫자 이외로 구성")
        void checkUsername2(){
            username = "테스트유저네임";
            try {
                Optional<User> found = userRepository.findByUsername(username);
                if (username.length() < 3) {
                    throw new IllegalArgumentException("ID는 최소 3자 이상이여야 합니다");
                }
                if (!Pattern.matches("^[a-zA-Z0-9]*$", username)) {
                    throw new IllegalArgumentException("ID는 알파벳과 숫자만 입력이 가능합니다");
                }
            }catch (IllegalArgumentException e){
                assertEquals("ID는 알파벳과 숫자만 입력이 가능합니다", e.getMessage());
            }
        }
        @Nested
        @DisplayName("비밀번호 체크")
            class password{
                @Test
            @DisplayName("3자")
            void checkPassword1(){
                    passwordch = "123";
                    try{
                        if (passwordch.length() < 4) {
                            throw new IllegalArgumentException("PW는 최소 4자 이상이여야 합니다");
                        }
                        if (passwordch.contains(username)) {
                            throw new IllegalArgumentException("ID가 포함되지 않은 비밀번호를 사용해주세요");
                        }
                    }catch (IllegalArgumentException e){
                        assertEquals("PW는 최소 4자 이상이여야 합니다", e.getMessage());
                    }
                }
                @Test
            @DisplayName("ID와 중복된 비밀번호")
            void checkPassword2(){
                    username="user";
                    passwordch="user1234";
                    try {
                        if (passwordch.length() < 4) {
                            throw new IllegalArgumentException("PW는 최소 4자 이상이여야 합니다");
                        }
                        if (passwordch.contains(username)) {
                            throw new IllegalArgumentException("ID가 포함되지 않은 비밀번호를 사용해주세요");
                        }
                    }catch (IllegalArgumentException e){
                        assertEquals("ID가 포함되지 않은 비밀번호를 사용해주세요", e.getMessage());
                    }
                }
                @Nested
            @DisplayName("비밀번호 확인")
            class password2{
                    @Test
                    @DisplayName("비밀번호 확인과 비밀번호 일치")
                    void recheckPassword(){
                        passwordch="1234";
                        repassword="12345";
                        try{
                            if (!Objects.equals(repassword, passwordch)) {
                                throw new IllegalArgumentException("비밀번호가 일치하지 않습니다");
                            }
                        }catch (IllegalArgumentException e){
                            assertEquals("비밀번호가 일치하지 않습니다", e.getMessage());
                        }
                    }
                }
                @Nested
            @DisplayName("닉네임 중복")
            class checkUsername{
                    @Test
                    @DisplayName("닉네임 중복 확인")
                    void checkUsername1(){
                        try {
                            Optional<User> found = userRepository.findByUsername(username);
                            if (found.isPresent()) {
                                throw new IllegalArgumentException("중복된 사용자 ID 가 존재합니다");
                            }
                        }catch (IllegalArgumentException e){
                            assertEquals("중복된 사용자 ID가 존재합니다", e.getMessage());
                        }
                    }
                }
        }
        }
    }

}