package org.sopt.post.service;

import org.sopt.global.error.BusinessException;
import org.sopt.member.service.MemberService;
import org.sopt.post.code.PostErrorCode;
import org.sopt.post.dto.PostCreateRequest;
import org.sopt.post.dto.PostResponse;
import org.sopt.post.dto.PostUpdateRequest;
import org.sopt.post.domain.Category;
import org.sopt.post.domain.Post;
import org.sopt.post.repository.PostRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PostService {

    private final PostRepository postRepository;
    private final MemberService memberService;

    public PostService(PostRepository postRepository, MemberService memberService) {
        this.postRepository = postRepository;
        this.memberService = memberService;
    }

    @Transactional
    public void createPost(Long memberId, PostCreateRequest request) {
        postRepository.save(new Post(memberService.getMember(memberId), request.title(), request.content(), request.category()));
    }

    // TODO : JPA로 전환하면서 Category별 조회, 페이지 기능 빠짐
    @Transactional(readOnly = true)
    public List<PostResponse> getPosts(Category category, Pageable pageable) {
        return postRepository.findAll().stream()
                .map(PostResponse::from)
                .toList();
    }

    @Transactional
    public PostResponse getPost(Long id) {
        postRepository.updateViewCount(id);

        Post post = findPost(id);

        return PostResponse.from(post);
    }

    @Transactional
    public void updatePost(Long id, PostUpdateRequest request) {
        Post post = findPost(id);

        post.update(request.title(), request.content());
    }

    @Transactional
    public void deletePost(Long id) {
        findPost(id);

        postRepository.deleteById(id);
    }

    private Post findPost(Long id) {
        return postRepository.findById(id)
                .orElseThrow(() -> new BusinessException(PostErrorCode.POST_NOT_FOUND));
    }
}
