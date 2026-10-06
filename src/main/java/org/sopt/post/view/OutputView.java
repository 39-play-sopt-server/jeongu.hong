package org.sopt.post.view;

import org.sopt.global.response.ApiResponse;
import org.sopt.post.dto.PostResponse;

import java.util.List;

public class OutputView {

    public void printPostList(ApiResponse<List<PostResponse>> response) {
        printResult(response);
        System.out.println("\n=== 게시글 목록 ===");

        for (PostResponse post : response.data()) {
            System.out.println(post.id() + ". " + post.title());
        }
    }

    public void printPost(ApiResponse<PostResponse> response) {
        PostResponse post = response.data();

        printResult(response);
        System.out.println("\n=== 게시글 ===");
        System.out.println("카테고리: " + post.category());
        System.out.println("제목: " + post.title());
        System.out.println("내용: " + post.content());
    }

    public void printMessage(String message) {
        System.out.println(message);
    }

    public void printResult(ApiResponse<?> response) {
        System.out.printf("[%s] %s%n", response.code(), response.message());
    }
}
