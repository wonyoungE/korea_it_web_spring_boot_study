package com.koreait.spring_boot_study;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringBootStudyApplication {
	// 어플리케이션의 시작점
	public static void main(String[] args) {
		SpringApplication.run(SpringBootStudyApplication.class, args);
		// 스프링 기본 포트 : 8080 -> http://localhost:8080/ = http://127.0.0.1:8080/
		// 리액트 기본 포트 : 3000
		// => 어플리케이션 돌리기 위해서 포트 두 개 사용. 서버가 두 개 돌아간다는 것

		// MVC 패턴(Model-View-Controller)
		// M(Model) -> 요청을 받아서 처리하는 곳
		// 데이터 및 비즈니스 로직 처리 - Entity, Service, Repository
		// V(View) -> 요청이 완료되었을 때 반환하는 곳
		// 사용자에게 보여지는 화면 - html, Json, 응답 등등
		// C(Controller) -> 요청을 받는 곳
		// 요청을 받아서 모델에 전달하고, 처리 결과를 View로 반환 - RestController, Controller

	}

}
