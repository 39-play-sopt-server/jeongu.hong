package org.sopt.view;

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

    public String readNewTitle() {
        System.out.print("새로운 제목: ");
        return scanner.nextLine();
    }

    public String readNewContent() {
        System.out.print("새로운 내용: ");
        return scanner.nextLine();
    }

    public int readPostIndex(String action) {
        System.out.print(action + " 게시글 번호: ");
        return Integer.parseInt(scanner.nextLine()) - 1;
    }

    public void printPostList(List<String> titles) {
        System.out.println("\n=== 게시글 목록 ===");

        for (int i = 0; i < titles.size(); i++) {
            System.out.println((i + 1) + ". " + titles.get(i));
        }
    }

    public void printPost(String title, String content) {
        System.out.println("\n=== 게시글 ===");
        System.out.println("제목: " + title);
        System.out.println("내용: " + content);
    }

    public void printMessage(String message) {
        System.out.println(message);
    }
}
