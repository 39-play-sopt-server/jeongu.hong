package org.sopt.post.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.sopt.post.domain.Category;
import org.sopt.post.domain.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;

import java.util.List;

import static org.sopt.member.domain.QMember.member;
import static org.sopt.post.domain.QPost.post;

public class PostRepositoryImpl implements PostRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    public PostRepositoryImpl(JPAQueryFactory jpaQueryFactory) {
        this.jpaQueryFactory = jpaQueryFactory;
    }

    @Override
    public Page<Post> search(Category category, String keyword, Long memberId, Pageable pageable) {
        List<Post> content = jpaQueryFactory
                .selectFrom(post)
                .join(post.member, member).fetchJoin()
                .where(categoryEq(category), titleContains(keyword), memberIdEq(memberId))
                .orderBy(post.createdAt.desc(), post.id.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPAQuery<Long> countQuery = jpaQueryFactory
                .select(post.count())
                .from(post)
                .where(categoryEq(category), titleContains(keyword));

        // Count 쿼리가 필요할 떄만 countQuery를 실행시켜 성능 향상.
        return PageableExecutionUtils.getPage(content, pageable, countQuery::fetchOne);
    }

    private BooleanExpression categoryEq(Category category) {
        return category == null ? null : post.category.eq(category);
    }

    private BooleanExpression titleContains(String keyword) {
        return (keyword != null && !keyword.isBlank()) ? post.title.contains(keyword) : null;
    }

    private BooleanExpression memberIdEq(Long memberId) {
        return memberId == null ? null : post.member.id.eq(memberId);
    }
}
