package org.sopt.post.repository;

import org.sopt.post.entity.Post;

import java.util.ArrayList;
import java.util.List;

public class InMemoryPostRepository implements PostRepository {

    private final List<Post> posts = new ArrayList<>();

    public void save(Post post) {
        this.posts.add(post);
    }

    public List<Post> findAll() {
        return this.posts;
    }

    public Post findByIndex(int index) {
        return this.posts.get(index);
    }

    public void deleteByIndex(int index) {
        this.posts.remove(index);
    }

    public boolean isEmpty() {
        return this.posts.isEmpty();
    }

    public boolean exists(int index) {
        return index >= 0 && index < this.posts.size();
    }
}
