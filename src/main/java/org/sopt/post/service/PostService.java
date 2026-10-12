package org.sopt.post.service;

import org.sopt.global.error.BusinessException;
import org.sopt.post.code.PostErrorCode;
import org.sopt.post.dto.PostCreateRequest;
import org.sopt.post.dto.PostResponse;
import org.sopt.post.dto.PostUpdateRequest;
import org.sopt.post.domain.Category;
import org.sopt.post.domain.Post;
import org.sopt.post.repository.PostRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostService {

    private final PostRepository postRepository;

    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    public void createPost(PostCreateRequest request) {
        postRepository.save(new Post(request.title(), request.content(), request.category()));
    }

    public List<PostResponse> getPosts(Category category, int page, int size) {
        validatePageAndSize(page, size);

        return postRepository.findAll(category, page, size).stream()
                .map(PostResponse::from)
                .toList();
    }

    public PostResponse getPost(long id) {
        Post post = findPost(id);

        post.increaseViewCount();

        return PostResponse.from(post);
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
        return postRepository.findById(id)
                .orElseThrow(() -> new BusinessException(PostErrorCode.POST_NOT_FOUND));
    }

    private void validatePageAndSize(int page, int size) {
        if (page < 1 || size < 1) {
            throw new BusinessException(PostErrorCode.INVALID_PAGE_OR_SIZE);
        }
    }
}
