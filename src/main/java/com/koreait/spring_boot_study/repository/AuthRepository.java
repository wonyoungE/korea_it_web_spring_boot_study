package com.koreait.spring_boot_study.repository;

import com.koreait.spring_boot_study.dto.SignupReqDto;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository
public class AuthRepository {
    private final Map<String, String> userDb = new HashMap<>();

    public AuthRepository() {
        userDb.put("test@example.com", "홍길동");
    }

    public int findByEmail(String email) {
        if(userDb.containsKey(email)) {
            return 0;
        } else {
            return 1;
        }
    }

    public int addUser(SignupReqDto signUpReqDto) {
        userDb.put(signUpReqDto.getEmail(), signUpReqDto.getUsername());
        return 1;
    }
}
