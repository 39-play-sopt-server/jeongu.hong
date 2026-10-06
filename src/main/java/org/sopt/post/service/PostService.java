package org.sopt.post.service;

import org.sopt.global.error.ErrorCode;
import org.sopt.global.error.BusinessException;
import org.sopt.post.dto.PostCreateRequest;
import org.sopt.post.dto.PostResponse;
import org.sopt.post.dto.PostUpdateRequest;
import org.sopt.post.entity.Category;
import org.sopt.post.entity.Post;
import org.sopt.post.repository.PostRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class PostService {

    private final PostRepository postRepository;

    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    public void createPost(PostCreateRequest request) {
        if (request.title().isEmpty() || request.content().isEmpty()) {
            throw new BusinessException(ErrorCode.EMPTY_TITLE_OR_CONTENT);
        }

        if (!Category.exists(request.categoryNumber())) {
            throw new BusinessException(ErrorCode.CATEGORY_NOT_FOUND);
        }

        postRepository.save(new Post(request.title(), request.content(), Category.from(request.categoryNumber())));
    }

    public List<String> getCategoryNames() {
        return Stream.of(Category.values())
                .map(Enum::name)
                .toList();
    }

    public List<PostResponse> getPosts() {
        validateNotEmpty();

        List<PostResponse> responses = new ArrayList<>();

        for (Post post : postRepository.findAll()) {
            responses.add(PostResponse.from(post));
        }

        return responses;
    }

    public PostResponse getPost(int index) {
        return PostResponse.from(findPost(index));
    }

    public void updatePost(int index, PostUpdateRequest request) {
        Post post = findPost(index);

        post.updateTitle(request.title());
        post.updateContent(request.content());
    }

    public void deletePost(int index) {
        findPost(index);

        postRepository.deleteByIndex(index);
    }

    private Post findPost(int index) {
        validateNotEmpty();

        if (!postRepository.exists(index)) {
            throw new BusinessException(ErrorCode.POST_NOT_FOUND);
        }

        return postRepository.findByIndex(index);
    }

    private void validateNotEmpty() {
        if (postRepository.isEmpty()) {
            throw new BusinessException(ErrorCode.POST_EMPTY);
        }
    }
}
