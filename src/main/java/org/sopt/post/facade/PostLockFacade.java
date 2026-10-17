package org.sopt.post.facade;

import org.sopt.post.dto.PostCreateRequest;
import org.sopt.post.dto.PostResponse;
import org.sopt.post.dto.PostUpdateRequest;
import org.sopt.post.domain.Category;
import org.sopt.post.service.PostService;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

@Component
public class PostLockFacade {

    private final PostService postService;
    private final ReadWriteLock lock = new ReentrantReadWriteLock();

    public PostLockFacade(PostService postService) {
        this.postService = postService;
    }

    public void createPost(PostCreateRequest request) {
        lock.writeLock().lock();
        try {
            postService.createPost(request);
        } finally {
            lock.writeLock().unlock();
        }
    }

    public List<PostResponse> getPosts(Category category, Pageable pageable) {
        lock.readLock().lock();
        try {
            return postService.getPosts(category, pageable);
        } finally {
            lock.readLock().unlock();
        }
    }

    public PostResponse getPost(long id) {
        lock.readLock().lock();
        try {
            return postService.getPost(id);
        } finally {
            lock.readLock().unlock();
        }
    }

    public void updatePost(long id, PostUpdateRequest request) {
        lock.writeLock().lock();
        try {
            postService.updatePost(id, request);
        } finally {
            lock.writeLock().unlock();
        }
    }

    public void deletePost(long id) {
        lock.writeLock().lock();
        try {
            postService.deletePost(id);
        } finally {
            lock.writeLock().unlock();
        }
    }
}
