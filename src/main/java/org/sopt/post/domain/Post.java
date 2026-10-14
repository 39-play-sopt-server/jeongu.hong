package org.sopt.post.domain;

import org.sopt.global.error.BusinessException;
import org.sopt.post.code.PostErrorCode;

import java.util.concurrent.atomic.AtomicLong;

public class Post {

    private Long id;
    private String title;
    private String content;
    private final AtomicLong viewCount = new AtomicLong();
    private final Category category;

    public Post(String title, String content, Category category) {
        validateTitle(title);
        validateContent(content);
        validateCategory(category);
        this.title = title;
        this.content = content;
        this.category = category;
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
        return viewCount.get();
    }

    public void update(String title, String content) {
        validateTitle(title);
        validateContent(content);
        this.title = title;
        this.content = content;
    }

    public void assignId(Long id) {
        if (this.id != null) {
            throw new BusinessException(PostErrorCode.ASSIGNED_POST);
        }
        this.id = id;
    }

    public void increaseViewCount() {
        viewCount.incrementAndGet();
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