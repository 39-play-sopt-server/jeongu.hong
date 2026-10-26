package org.sopt.post.domain;

import jakarta.persistence.*;
import org.sopt.global.error.BusinessException;
import org.sopt.member.domain.Member;
import org.sopt.post.code.PostErrorCode;

@Entity
@Table(
        name = "post_like",
        uniqueConstraints = @UniqueConstraint(
                name = "uk_post_like_member_post",
                columnNames = {"member_id", "post_id"}
        )
)
public class PostLike {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id", nullable = false)
    private Post post;

    public PostLike(Member member, Post post) {
        validateMember(member);
        validatePost(post);
        this.member = member;
        this.post = post;
    }

    protected PostLike() {
    }

    public Long getId() {
        return id;
    }

    public Member getMember() {
        return member;
    }

    public Post getPost() {
        return post;
    }

    private void validatePost(Post post) {
        if (post == null) {
            throw new BusinessException(PostErrorCode.EMPTY_LIKE_POST);
        }
    }

    private void validateMember(Member member) {
        if (member == null) {
            throw new BusinessException(PostErrorCode.EMPTY_LIKE_MEMBER);
        }
    }
}
