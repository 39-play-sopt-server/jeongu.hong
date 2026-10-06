package org.sopt.post.view;

import java.util.List;
import java.util.Scanner;

public class InputView {

    private static final String INVALID_INPUT_MESSAGE = "잘못된 입력입니다. 다시 입력해주세요.";

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

        return readNumber();
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

        return readNumber();
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
        return readLongNumber();
    }

    private int readNumber() {
        String input = scanner.nextLine();

        while (!validateIntegerType(input)) {
            input = scanner.nextLine();
        }

        return Integer.parseInt(input);
    }

    private boolean validateIntegerType(String input) {
        try {
            Integer.parseInt(input);
            return true;
        } catch (NumberFormatException e) {
            System.out.println(INVALID_INPUT_MESSAGE);
            return false;
        }
    }

    private long readLongNumber() {
        String input = scanner.nextLine();

        while (!validateLongType(input)) {
            input = scanner.nextLine();
        }

        return Long.parseLong(input);
    }

    private boolean validateLongType(String input) {
        try {
            Long.parseLong(input);
            return true;
        } catch (NumberFormatException e) {
            System.out.println(INVALID_INPUT_MESSAGE);
            return false;
        }
    }
}
