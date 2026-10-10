package org.sopt.post.service;

import org.sopt.global.code.ErrorCode;
import org.sopt.global.error.BusinessException;
import org.sopt.post.dto.PostCreateRequest;
import org.sopt.post.dto.PostResponse;
import org.sopt.post.dto.PostUpdateRequest;
import org.sopt.post.entity.Category;
import org.sopt.post.entity.Post;
import org.sopt.post.repository.PostRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

@Service
public class PostService {

    private final PostRepository postRepository;

    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    public void createPost(PostCreateRequest request) {
        if (!Category.exists(request.categoryNumber())) {
            throw new BusinessException(ErrorCode.CATEGORY_NOT_FOUND);
        }

        postRepository.save(new Post(request.title(), request.content(), Category.from(request.categoryNumber())));
    }

    public List<PostResponse> getPosts() {
        validateNotEmpty();

        List<PostResponse> responses = new ArrayList<>();

        for (Post post : postRepository.findAll()) {
            responses.add(PostResponse.from(post));
        }

        return responses;
    }

    public PostResponse getPost(long id) {
        return PostResponse.from(findPost(id));
    }

    public void updatePost(long id, PostUpdateRequest request) {
        Post post = findPost(id);

        post.update(request.title(), request.content());
    }

    public void deletePost(long id) {
        findPost(id);

        postRepository.deleteById(id);
    }

    private Post findPost(long id) {
        validateNotEmpty();
        return postRepository.findById(id)
                .orElseThrow(() -> new BusinessException(ErrorCode.POST_NOT_FOUND));
    }

    private void validateNotEmpty() {
        if (postRepository.isEmpty()) {
            throw new BusinessException(ErrorCode.POST_EMPTY);
        }
    }
}
