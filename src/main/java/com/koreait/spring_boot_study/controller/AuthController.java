package com.koreait.spring_boot_study.controller;

import com.koreait.spring_boot_study.dto.SigninReqDto;
import com.koreait.spring_boot_study.dto.SignupReqDto;
import com.koreait.spring_boot_study.dto.SigninRespDto;
import com.koreait.spring_boot_study.dto.SignupRespDto;
import com.koreait.spring_boot_study.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/auth")
public class AuthController {
    // @RequestParam
    // 클라이언트가 URL 쿼리스트링으로 넘긴 값을 메서드 파라미터로 전달
    @Autowired
    private AuthService authService;

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
//    @PostMapping("/signup")
//    public String signup(@RequestBody SignUpReqDto signupReqDto) {
//        // JSON의 키와 DTO의 키가 같아야 함.
//        System.out.println(signupReqDto);
//        return signupReqDto.getUsername() + "님 회원가입 완료되었습니다.";
//    }
    @PostMapping("/signup")
    public ResponseEntity<SignupRespDto> signup(@RequestBody SignupReqDto signupReqDto) {
        return ResponseEntity.ok().body(authService.signup(signupReqDto));
    }
    // 중복 체크같은 API는 대부분 200 OK로 응답하고
    // 응답 본문(JSON)에 "중복 여부"를 표시합니다.
    // 중복 체크는 정상적인 요청에 대한 정상적인 응답이기 때문에 200 OK이다.
    // 이메일이 중복이든 아니든 요청 자체는 정상적으로 처리됐기 때문에 400/409같은 에러코드를 주지 않는다.
    // 중복인거는 뒤에서 해결할 일
    // 대신 JSON 응답 내부에서 중복됨/가능함 을 구분한다
    // 그럼 언제 에러 코드(409 Conflict)를 쓰느냐?
    // 그거는 진짜 예외 상황일 때
    // 중복된 이메일로 회원가입을 실제로 시도했을 때(유효성 검사를 거치지 않고 DB에 넣으려고 시도했을 때) => 409

//    @PostMapping("/signin")
//    public String signin(@RequestBody SigninReqDto signinReqDto) {
//        String email = "wonyoung713@gmail.com";
//        String password = "1q2w3e";
//
//        if(email.equals(signinReqDto.getEmail())) {
//            if(password.equals(signinReqDto.getPassword())) {
//                String userEmail = signinReqDto.getEmail();
//                return "로그인 완료: " + userEmail.substring(0, userEmail.indexOf("@")) + "님 반갑습니다.";
//            } else {
//                return "비밀번호가 틀렸습니다.";
//            }
//        }
//        return "존재하지 않는 회원입니다.";
//    }

    // ResponseEntity
    // HTTP 응답 전체를 커스터마이징을 해서 보낼 수 있는 스프링 클래스
    // HTTP 상태 코드, 응답 바디, 응답 헤더 모두 포함
    @PostMapping("signin")
    public ResponseEntity<SigninRespDto> signin(@RequestBody SigninReqDto signinReqDto) {
        if(signinReqDto.getEmail() == null || signinReqDto.getEmail().trim().isEmpty()) {
            SigninRespDto signinRespDto = new SigninRespDto("failed", "이메일을 다시 입력해주세요.");
            return ResponseEntity.badRequest().body(signinRespDto);
        } else if(signinReqDto.getPassword() == null || signinReqDto.getPassword().trim().isEmpty()) {
            SigninRespDto signinRespDto = new SigninRespDto("failed", "비밀번호를 다시 입력해주세요.");
            return ResponseEntity.badRequest().body(signinRespDto);
        }
        // 이메일, 비밀번호 다 있는 경우
        SigninRespDto signinRespDto = new SigninRespDto("success", "로그인 성공!");
        // Json형식으로 반환
        return ResponseEntity.ok(signinRespDto);
//        return ResponseEntity.ok().body(signinRespDto);
//        return ResponseEntity.status(HttpStatus.OK).body(signinRespDto);
        // 다 똑같은 방법
    }
    // 200 OK => 요청 성공
    // 400 Bad Request => 잘못된 요청(ex. 유효성 실패, JSON 파싱 오류)
    // 401 Unauthorized => 인증 실패(ex. 로그인 안 됨, 토큰 없음)
    // 403 Forbidden => 접근 권한 없음 (ex. 관리자만 접근 가능)
    // 404 Not Found => 리소스 없음
    // 409 Conflict => 중복 등으로 인한 충돌 (ex. 이미 존재하는 이메일)
    // 500 Internal Server Error => 서버 내부 오류 (ex. 코드 문제, 예외 등)

    // 200은 정상적으로 됐다, 400은 니가 잘못함;, 500은 서버가 터졌다
}