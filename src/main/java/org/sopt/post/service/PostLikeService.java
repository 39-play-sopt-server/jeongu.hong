package org.sopt.post.service;

import org.sopt.global.error.BusinessException;
import org.sopt.member.domain.Member;
import org.sopt.member.service.MemberService;
import org.sopt.post.code.PostErrorCode;
import org.sopt.post.domain.Post;
import org.sopt.post.domain.PostLike;
import org.sopt.post.repository.PostLikeRepository;
import org.sopt.post.repository.PostRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PostLikeService {

    private final PostLikeRepository postLikeRepository;
    private final PostRepository postRepository;
    private final MemberService memberService;

    public PostLikeService(PostLikeRepository postLikeRepository, PostRepository postRepository, MemberService memberService) {
        this.postLikeRepository = postLikeRepository;
        this.postRepository = postRepository;
        this.memberService = memberService;
    }

    @Transactional
    public void likePost(Long memberId, Long postId) {
        Post post  = findPost(postId);
        Member member = memberService.getMember(memberId);

        if (postLikeRepository.existsByMemberIdAndPostId(memberId, postId)) { // 중복 체크
            throw new BusinessException(PostErrorCode.ALREADY_LIKED);
        }

        postRepository.increaseLikeCount(postId); // X-Lock 획득

        try {
            postLikeRepository.save(new PostLike(member, post)); // 좋아요 저장
        } catch (DataIntegrityViolationException e) { // 동시 요청 제어
            throw new BusinessException(PostErrorCode.ALREADY_LIKED);
        }
    }

    private Post findPost(Long postId) {
        return postRepository.findById(postId)
                .orElseThrow(() -> new BusinessException(PostErrorCode.POST_NOT_FOUND));
    }

}
