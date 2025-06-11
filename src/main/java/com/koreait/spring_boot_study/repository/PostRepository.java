package com.koreait.spring_boot_study.repository;

import org.springframework.stereotype.Repository;

@Repository
public class PostRepository {

    public String getPost() {
        System.out.println("PostRepository에 왔다감");
        return "레포지토리에서 보낸 어떠한 게시물의 데이터";
    }
}