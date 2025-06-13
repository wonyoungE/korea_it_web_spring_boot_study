package com.koreait.spring_boot_study.service;

import com.koreait.spring_boot_study.dto.SignupReqDto;
import com.koreait.spring_boot_study.dto.SignupRespDto;
import com.koreait.spring_boot_study.repository.AuthRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    @Autowired
    private AuthRepository authRepository;

    public SignupRespDto signup(SignupReqDto signUpReqDto){
        // 유효성 검사
        if(signUpReqDto.getEmail() == null || signUpReqDto.getEmail().trim().isEmpty()) {
            SignupRespDto signupRespDto = new SignupRespDto("failed", "이메일을 입력해주세요.");
            return signupRespDto;
        } else if(signUpReqDto.getPassword() == null || signUpReqDto.getPassword().trim().isEmpty()) {
            SignupRespDto signupRespDto = new SignupRespDto("failed", "비밀번호를 입력해주세요.");
            return signupRespDto;
        } else if(signUpReqDto.getUsername() == null || signUpReqDto.getUsername().trim().isEmpty()) {
            SignupRespDto signupRespDto = new SignupRespDto("failed", "사용자 이름을 입력해주세요.");
            return signupRespDto;
        }

        int checkEmail = authRepository.findByEmail(signUpReqDto.getEmail());
        if(checkEmail == 1) {
            authRepository.addUser(signUpReqDto);
            return new SignupRespDto("success", signUpReqDto.getUsername() + "님 회원가입 되었습니다.");
        } else if(checkEmail == 0){
            return new SignupRespDto("failed", "이미 존재하는 이메일입니다.");
        }
        return new SignupRespDto("failed", "회원가입에 오류가 발생했습니다. 다시 시도해주세요.");
    }
}
