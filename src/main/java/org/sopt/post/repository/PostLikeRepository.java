package org.sopt.post.repository;

import org.sopt.post.domain.PostLike;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PostLikeRepository extends JpaRepository<PostLike, Long> {

    boolean existsByMemberIdAndPostId(Long memberId, Long postId);

    @Modifying(clearAutomatically = true)
    @Query("DELETE FROM PostLike pl WHERE pl.member.id = :memberId AND pl.post.id = :postId")
    int deleteByMemberIdAndPostId(@Param("memberId") Long memberId, @Param("postId") Long postId);
}
