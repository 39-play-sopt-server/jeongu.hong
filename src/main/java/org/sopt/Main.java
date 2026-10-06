package org.sopt;

import org.sopt.config.PostConfig;
import org.sopt.global.error.BusinessException;
import org.sopt.global.response.ApiResponse;
import org.sopt.post.handler.CommandHandler;
import org.sopt.post.view.InputView;
import org.sopt.post.view.OutputView;

import java.util.Map;

public class Main {

    private static final int EXIT_COMMAND = 6;

    private static final Map<Integer, CommandHandler> handlers = PostConfig.getHandlers();
    private static final InputView inputView = PostConfig.getInputView();
    private static final OutputView outputView = PostConfig.getOutputView();

    public static void main(String[] args) {
        while (true) {
            int command = inputView.readCommand();

            if (command == EXIT_COMMAND) {
                outputView.printMessage("프로그램을 종료합니다.");
                return;
            }

            CommandHandler handler = handlers.get(command);

            if (handler == null) {
                outputView.printMessage("잘못된 입력입니다.");
                continue;
            }

            try {
                handler.handle();
            } catch (BusinessException e) {
                outputView.printResult(ApiResponse.fail(e.getErrorCode()));
            }
        }
    }
}
