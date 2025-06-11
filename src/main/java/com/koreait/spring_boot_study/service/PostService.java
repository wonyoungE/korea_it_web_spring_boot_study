package com.koreait.spring_boot_study.service;

import com.koreait.spring_boot_study.repository.PostRepository;
import org.springframework.stereotype.Service;

@Service    // IoC 컨테이너에게 나는 service니까 등록해줘.
public class PostService {
    // 전체적인 흐름
    // 서버 실행 -> IoC 컨테이너가 둘러 봄 -> @RestController 붙어있는 애 보고 Controller 객체 등록하려고 함
    // -> 근데 보니까 Service 필요함 -> Service 클래스 와봤더니 @Service 있음 -> Service도 객체 등록
    // 이러한 과정에서 의존성이 생김

    private final PostRepository postRepository;

    // 의존성 주입
    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    public String getPost() {
        System.out.println("PostService에 왔다감");
        String result = postRepository.getPost();
        return result;
    }

}
