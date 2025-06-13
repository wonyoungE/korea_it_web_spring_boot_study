package com.koreait.spring_boot_study.controller;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
class UserDto {
    private int userId;
    private String username;
    private int age;
}

// controller => 웹페이지 자체를 반환 (서버 사이드 렌더링, SSR)
// 즉, 서버쪽에서 웹페이지를 렌더링해서 반환하는 SSR
@Controller
public class MainController {
    private List<UserDto> users = new ArrayList<>();

    // 이러한 방식은 정적 웹페이지를 보여주는 것
    // 데이터 즉 동적인 요소가 없는 정적 웹페이지
    @GetMapping("/main")
    public String getMain() {

        // 정적 웹페이지 반환
        return "main.html";
    }
    
    // SSR에 동적을 추가하면 Thymeleaf를 적용하면 된다
    // html 파일은 templates 폴더에 있어야 한다.
    // Thymeleaf
    // 서버에서 HTML을 렌더링할 때, 자바 데이터를 끼워 넣을 수 있게 해주는 템플릿 엔진 => 결국 SSR
    
    @GetMapping("/profile")
    public String getProfile(Model model) {
        model.addAttribute("username", "<b>원영</b>");
        model.addAttribute("isAdult", true);
        model.addAttribute("age", 26);
        Map<String, String> userList = new HashMap<>();
        userList.put("손원영", "26");
        userList.put("이원영", "19");
        userList.put("최원영", "42");
        userList.put("장원영", "21");
        model.addAttribute("userList", userList);
        // model 안에 있는 데이터가 html 파일에 넣어서 보내짐
        return "profile";
    }

    @GetMapping("/search")
    public String getSearch(@RequestParam String keyword, Model model) {
        model.addAttribute("keyword", keyword);
        return "search";
    }

    // 메서드가 다르면(get/post) 요청 주소가 같아도 된다.
    // 회원가입 요청
    @GetMapping("/signup")
    public String signup() {
        return "signup";
    }

    // 회원가입 처리
    @PostMapping("/signup")
    public String signupSubmit(@RequestParam String name, @RequestParam int age, Model model) {
        // @RequestParam => key, value 형태
        UserDto userDto = new UserDto(users.size()+1, name, age);
        users.add(userDto);
        model.addAttribute("message", name + "님, 가입을 환영합니다.");
        model.addAttribute("users", users);
        return "signup-result";
    }
}
