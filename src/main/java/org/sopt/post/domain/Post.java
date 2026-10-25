package org.sopt.post.domain;

import jakarta.persistence.*;
import org.sopt.global.domain.BaseEntity;
import org.sopt.global.error.BusinessException;
import org.sopt.post.code.PostErrorCode;

@Entity
public class Post extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String content;

    private Long viewCount;

    @Enumerated(EnumType.STRING)
    private Category category;

    public Post(String title, String content, Category category) {
        validateTitle(title);
        validateContent(content);
        validateCategory(category);
        this.title = title;
        this.content = content;
        this.category = category;
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

    public long getId() {
        return id;
    }

    public long getViewCount() {
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
}