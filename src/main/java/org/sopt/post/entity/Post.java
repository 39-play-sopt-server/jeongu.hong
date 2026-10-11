package org.sopt.post.entity;

import org.sopt.global.error.BusinessException;
import org.sopt.global.code.ErrorCode;

import java.util.concurrent.atomic.AtomicLong;

public class Post {

    private Long id;
    private String title;
    private String content;
    private final AtomicLong viewCount = new AtomicLong();
    private final Category category;

    public Post(String title, String content, Category category) {
        validateNotBlank(title, content);
        this.title = title;
        this.content = content;
        this.category = category;
    }

    public String getTitle() {
        return this.title;
    }

    public String getContent() {
        return this.content;
    }

    public Category getCategory() {
        return this.category;
    }

    public long getId() {
        return this.id;
    }

    public long getViewCount() {
        return this.viewCount.get();
    }

    public void update(String title, String content) {
        validateNotBlank(title, content);
        this.title = title;
        this.content = content;
    }

    public void assignId(Long id) {
        if (this.id != null) {
            throw new BusinessException(ErrorCode.ASSIGNED_POST);
        }
        this.id = id;
    }

    public void increaseViewCount() {
        viewCount.incrementAndGet();
    }

    private void validateNotBlank(String title, String content) {
        if (title.isBlank() || content.isBlank()) {
            throw new BusinessException(ErrorCode.EMPTY_TITLE_OR_CONTENT);
        }
    }
}