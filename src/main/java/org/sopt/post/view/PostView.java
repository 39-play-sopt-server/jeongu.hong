package org.sopt.post.view;

import org.sopt.post.dto.PostResponse;

import java.util.List;
import java.util.Scanner;

public class PostView {
    private final Scanner scanner = new Scanner(System.in);

    public int readCommand() {
        System.out.println("\n=== 게시판 ===");
        System.out.println("1. 게시글 작성");
        System.out.println("2. 게시글 목록 조회");
        System.out.println("3. 게시글 단건 조회");
        System.out.println("4. 게시글 수정");
        System.out.println("5. 게시글 삭제");
        System.out.println("6. 종료");
        System.out.print("선택: ");

        return Integer.parseInt(scanner.nextLine());
    }

    public String readTitle() {
        System.out.print("제목: ");
        return scanner.nextLine();
    }

    public String readContent() {
        System.out.print("내용: ");
        return scanner.nextLine();
    }

    public int readCategoryNumber(List<String> categoryNames) {
        System.out.println("카테고리를 선택하세요");

        for (int i = 0; i < categoryNames.size(); i++) {
            System.out.println((i + 1) + ". " + categoryNames.get(i));
        }
        System.out.print("선택: ");

        return Integer.parseInt(scanner.nextLine());
    }

    public String readNewTitle() {
        System.out.print("새로운 제목: ");
        return scanner.nextLine();
    }

    public String readNewContent() {
        System.out.print("새로운 내용: ");
        return scanner.nextLine();
    }

    public long readPostId(String action) {
        System.out.print(action + " 게시글 번호: ");
        return Long.parseLong(scanner.nextLine());
    }

    public void printPostList(List<PostResponse> posts) {
        System.out.println("\n=== 게시글 목록 ===");

        for (PostResponse post : posts) {
            System.out.println(post.id() + ". " + post.title());
        }
    }

    public void printPost(PostResponse post) {
        System.out.println("\n=== 게시글 ===");
        System.out.println("카테고리: " + post.category());
        System.out.println("제목: " + post.title());
        System.out.println("내용: " + post.content());
    }

    public void printMessage(String message) {
        System.out.println(message);
    }

    public void printError(String errorCode, String message) {
        System.out.printf("[%s] %s%n", errorCode, message);
    }
}
