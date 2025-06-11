package com.koreait.spring_boot_study.controller;

import com.koreait.spring_boot_study.service.PostService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

// Controller에는 두 가지 방식이 존재함 -> @RestController, @Controller
// 1. @Controller -> html 파일을 넘겨줌
// html(웹 페이지) 반환하는 서버 사이드 렌더링(SSR)
// 렌더링을 다 해서 요청에 응답하기 때문에 느리다.
// 2. @RestController -> 응답으로 데이터를 줌
// JSON, 문자열 등 다양한 데이터를 반환 -> REST API 서버(웹 서버), 클라이언트 사이드 렌더링(SCR)
// FRONT에게 데이터만 넘기기 때문에 빠르다.

// 서버 사이드 렌더링은 서버측에서 프론트의 웹페이지까지 통째로 반환해줘서 그걸 웹페이지에 띄운다.
// 해당 요청 경로에 따라서 웹페이지가 다 할당되어있어서 느리다.

// 프론트(리액트) + 백엔드(스프링부트 웹서버)
// 리액트는 클라이언트 사이드 렌더링(SCR)/ 즉, 프론트엔드 쪽에서 웹페이지를 로드
// 백엔드는 요청에 해당하는 데이터만 반환시켜준다.
// SCR이 구현되면 -> Single Page Application(SPA) 방식 이라고 한다.
@RestController     // IoC 컨트롤러에 객체 등록해줘. & 내가 Controller임을 알림
@RequestMapping("/post")    // 요청 주소가 자동으로 /post/** 형식으로 매핑됨.
public class PostController {
    // PostController -> 요청만 받는 클래스
    private final PostService postService;  // 싱글톤 패턴과 비슷

    // 제어의 역전(IoC: Inversion of Control)
    // 객체 생성과 제어의 주도권을 개발자가 아닌, 스프링부트가 갖는 것
    // IoC 컨테이너 => 스프링부트가 만든 객체들을 담아두고 관리하는 창고
    // IoC 컨테이너에서 해당 객체를 찾아서 자동으로 넣어주니까 new로 인스턴스 생성할 필요가 없음!

    // 의존성 주입(DI: Dependency Injection)
    // 필요한 객체(의존성)를 직접 만들지 않고, 외부(스프링부트)에서 대신 넣어주는 것
    // IoC 컨테이너가 있어서 DI가 가능한 것!


    // PostController 생성자
    public PostController(PostService postService) {
        // 스프링부트가 객체를 관리하기 때문에(=IoC) 매개변수로 PostService 객체를 받을 수 있음
        // 나는 new로 PostService 인스턴스 만든 적 없다..
        this.postService = postService;
    }

    // 메서드
    // 요청에는 get,post,delete,update 가 있다
    // get은 데이터가 필요해서 요청하는 것
    // post는 데이터 줄테니까 db에 넣어라!
    @GetMapping("/get") // /post/get
    public String getPost() {
        System.out.println("get으로 들어온 요청입니다.");
        return postService.getPost();
    }

    @GetMapping("/user") // /post/user
    public String getPostUser() {
        System.out.println("get/user로 들어온 요청입니다.");
        return "어떤 게시물의 유저 정보";
    }
}
