package org.sopt.post.repository;

import org.sopt.post.entity.Post;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ListPostRepository implements PostRepository {

    private final List<Post> posts = new ArrayList<>();
    private long sequence = 0L;

    @Override
    public void save(Post post) {
        post.assignId(++sequence);
        this.posts.add(post);
    }

    @Override
    public List<Post> findAll() {
        return this.posts;
    }

    @Override
    public Optional<Post> findById(long id) {
        return this.posts.stream()
                .filter(post -> post.getId() == id)
                .findFirst();
    }

    @Override
    public void deleteById(long id) {
        this.posts.removeIf(post -> post.getId() == id);
    }

    @Override
    public boolean isEmpty() {
        return this.posts.isEmpty();
    }

    @Override
    public boolean exists(long id) {
        return findById(id).isPresent();
    }
}
