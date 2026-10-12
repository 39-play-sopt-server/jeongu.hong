package org.sopt.post.repository;

import org.sopt.post.domain.Category;
import org.sopt.post.domain.Post;
import org.springframework.stereotype.Repository;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class HashMapPostRepository implements PostRepository {

    private final Map<Long, Post> posts = new LinkedHashMap<>();
    private long sequence = 0L;

    @Override
    public void save(Post post) {
        post.assignId(++sequence);
        posts.put(post.getId(), post);
    }

    @Override
    public List<Post> findAll(Category category, int page, int size) {
        long offset = (long) (page - 1) * size;
        return posts.values().stream()
                .filter(post -> category == null || post.getCategory() == category)
                .skip(offset)
                .limit(size)
                .toList();
    }

    @Override
    public Optional<Post> findById(long id) {
        return Optional.ofNullable(posts.get(id));
    }

    @Override
    public void deleteById(long id) {
        posts.remove(id);
    }
}
