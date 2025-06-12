package com.koreait.spring_boot_study.controller;

import com.koreait.spring_boot_study.dto.SignInReqDto;
import com.koreait.spring_boot_study.dto.SignUpReqDto;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/auth")
public class AuthController {
    // @RequestParam
    // 클라이언트가 URL 쿼리스트링으로 넘긴 값을 메서드 파라미터로 전달

    @GetMapping("/get")
    // localhost:8080/auth/get?userId=xx
    public String getUser(@RequestParam String userId) {
        System.out.println("유저: " + userId);
        return "유저: " + userId;
    }

    @GetMapping("/get/name")
    // localhost:8080/auth/get/name?name=xxx&age=xx
    // localhost:8080/auth/get/name?name=xxx
    // required = false -> 받아도, 안받아도 됨
    // int와 Integer의 차이? int는 null 허용x Integer는 null 허용o
    // 제일 좋은 방법 -> 모두 String으로 받고 서버에서 int로 변환하기
    public String getUserName(@RequestParam(value="name", defaultValue = "길동") String username, @RequestParam(required = false) Integer age) {
        System.out.println(username+age);   // required = false인데 age가 int면 npe 뜸
        return username + age;
    }
    // 안에서 사용하는 변수명과 쿼리스트링의 키 값이 다른 경우 괄호 안에 value=""로 표기
    // 기본값 설정 defaultValue=""
    // 다른 타입도 가능하며 여러개의 RequestParam도 받을 수 있다
    // 주의할 점: int는 null을 허용하지 않기 때문에 값이 없음의 상태가 될 수 있음
    // -> required = false를 했지만 에러가 뜸 -> Integer로 해야 null로 받을 수 있음
    // -> 그렇다고 defaultValue 설정해두면 required = false가 무의미해짐

    @GetMapping("/get/names")
    // http://localhost:8080/auth/get/names?names=xxx,xxx,xxx,xxx,..
    public String getNames(@RequestParam List<String> names) {
        return names.toString();
    }

    // RequestParam 주의사항
    // 파라미터가 없으면 500에러
    // 타입이 안맞을 때
    // 이름이 불일치
    // 민감한 정보 금지
    // get일 때 주로 사용

    // Q. 요청 주소는 /search => name, email
    // name -> 필수x, email -> 기본값으로 no-email
    // 요청 => /auth/search?name=lee
    @GetMapping("/search")
    public String searchUser(@RequestParam(required = false) String name,
                             @RequestParam(defaultValue = "no-email") String email) {
        return "검색 조건 - 이름: " + name + ", 이메일: " + email;
    }

    // @RequestBody
    // HTTP 요청의 바디에 들어있는 JSON 데이터를 자바 객체(DTO)로 변환해서 주입해주는 어노테이션
    // 클라이언트가 JSON 형식으로 데이터 보냄
    // 백엔드 서버는 그 JSON을 @RequestBody가 붙은 DTO로 자동 매핑
    // 일반적으로 POST, PUT, PATCH에서 사용

    // DTO(Data Transfer Object)
    // 데이터를 전달하기 위한 객체
    // 클라이언트 간에 데이터를 주고받을 때 사용하는 중간 객체
    // DTO가 Entity는 아님
    @PostMapping("/signUp")
    public String signUp(@RequestBody SignUpReqDto signUpReqDto) {
        // JSON의 키와 DTO의 키가 같아야 함.
        System.out.println(signUpReqDto);
        return signUpReqDto.getUsername() + "님 회원가입 완료되었습니다.";
    }

    @PostMapping("/signIn")
    public String signIn(@RequestBody SignInReqDto signInReqDto) {
        String email = "wonyoung713@gmail.com";
        String password = "1q2w3e";

        if(email.equals(signInReqDto.getEmail())) {
            if(password.equals(signInReqDto.getPassword())) {
                String userEmail = signInReqDto.getEmail();
                return "로그인 완료: " + userEmail.substring(0, userEmail.indexOf("@")) + "님 반갑습니다.";
            } else {
                return "비밀번호가 틀렸습니다.";
            }
        }
        return "존재하지 않는 회원입니다.";
    }
}