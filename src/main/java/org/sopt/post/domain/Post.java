package org.sopt.post.domain;

import jakarta.persistence.*;
import org.sopt.global.domain.BaseEntity;
import org.sopt.global.error.BusinessException;
import org.sopt.member.domain.Member;
import org.sopt.post.code.PostErrorCode;

@Entity
public class Post extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Category category;

    @Column(nullable = false)
    private Long viewCount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    public Post(Member member, String title, String content, Category category) {
        validateTitle(title);
        validateContent(content);
        validateCategory(category);
        validateMember(member);
        this.title = title;
        this.content = content;
        this.category = category;
        this.member = member;
        this.viewCount = 0L;
    }

    protected Post() {
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public Category getCategory() {
        return category;
    }

    public Long getId() {
        return id;
    }

    public Long getViewCount() {
        return viewCount;
    }

    public void update(String title, String content) {
        validateTitle(title);
        validateContent(content);
        this.title = title;
        this.content = content;
    }

    private void validateTitle(String title) {
        if (title == null || title.isBlank()) {
            throw new BusinessException(PostErrorCode.EMPTY_TITLE);
        }
    }

    private void validateContent(String content) {
        if (content == null || content.isBlank()) {
            throw new BusinessException(PostErrorCode.EMPTY_CONTENT);
        }
    }

    private void validateCategory(Category category) {
        if (category == null) {
            throw new BusinessException(PostErrorCode.EMPTY_CATEGORY);
        }
    }

    private void validateMember(Member member) {
        if (member == null) {
            throw new BusinessException(PostErrorCode.EMPTY_MEMBER);
        }
    }
}