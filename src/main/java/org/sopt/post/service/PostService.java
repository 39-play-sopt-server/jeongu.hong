package org.sopt.post.service;

import org.sopt.global.error.BusinessException;
import org.sopt.member.domain.Member;
import org.sopt.member.service.MemberService;
import org.sopt.post.code.PostErrorCode;
import org.sopt.post.dto.request.PostCreateRequest;
import org.sopt.post.dto.response.PostResponse;
import org.sopt.post.dto.request.PostUpdateRequest;
import org.sopt.post.domain.Category;
import org.sopt.post.domain.Post;
import org.sopt.post.repository.PostRepository;
import org.springframework.data.domain.Page;
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
        Member member = memberService.getMember(memberId);
        postRepository.save(new Post(member, request.title(), request.content(), request.category()));
    }

    @Transactional(readOnly = true)
    public Page<PostResponse> getPosts(Category category, String keyword, Pageable pageable) {
        return postRepository.search(category, keyword, pageable)
                .map(PostResponse::from);
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
