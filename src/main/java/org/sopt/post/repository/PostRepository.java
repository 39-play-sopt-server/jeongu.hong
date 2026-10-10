package org.sopt.post.repository;

import org.sopt.post.entity.Post;

import java.util.List;
import java.util.Optional;

public interface PostRepository {

    void save(Post post);

    List<Post> findAll();

    Optional<Post> findById(long id);

    void deleteById(long id);

    boolean isEmpty();

    boolean exists(long id);
}
