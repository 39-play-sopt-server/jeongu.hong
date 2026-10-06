package org.sopt.config;

import org.sopt.post.controller.PostController;
import org.sopt.post.handler.CommandHandler;
import org.sopt.post.handler.CreatePostHandler;
import org.sopt.post.handler.DeletePostHandler;
import org.sopt.post.handler.GetPostHandler;
import org.sopt.post.handler.GetPostsHandler;
import org.sopt.post.handler.UpdatePostHandler;
import org.sopt.post.repository.HashMapPostRepository;
import org.sopt.post.repository.PostRepository;
import org.sopt.post.service.PostService;
import org.sopt.post.view.InputView;
import org.sopt.post.view.OutputView;

import java.util.Map;

public class PostConfig {

    private static final PostRepository postRepository = new HashMapPostRepository();
    private static final PostService postService = new PostService(postRepository);
    private static final InputView inputView = new InputView();
    private static final OutputView outputView = new OutputView();
    private static final PostController postController = new PostController(postService);
    private static final Map<Integer, CommandHandler> handlers = Map.of(
            1, new CreatePostHandler(inputView, outputView, postController),
            2, new GetPostsHandler(outputView, postController),
            3, new GetPostHandler(inputView, outputView, postController),
            4, new UpdatePostHandler(inputView, outputView, postController),
            5, new DeletePostHandler(inputView, outputView, postController)
    );

    public static Map<Integer, CommandHandler> getHandlers() {
        return handlers;
    }

    public static InputView getInputView() {
        return inputView;
    }

    public static OutputView getOutputView() {
        return outputView;
    }
}
