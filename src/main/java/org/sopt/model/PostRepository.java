package org.sopt.model;

import java.util.ArrayList;
import java.util.List;

public interface PostRepository {

    void save(Post post);

    List<Post> findAll();

    Post findByIndex(int index);

    void deleteByIndex(int index);

    boolean isEmpty();

    boolean exists(int index);
}
