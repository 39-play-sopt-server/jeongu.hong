package org.sopt.post.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.sopt.post.domain.Category;
import org.sopt.post.domain.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;

import java.util.List;
import java.util.regex.Pattern;

import static org.sopt.member.domain.QMember.member;
import static org.sopt.post.domain.QPost.post;

public class PostRepositoryImpl implements PostRepositoryCustom {

    private static final Pattern BOOLEAN_MODE_OPERATORS = Pattern.compile("[+\\-*\"()<>~@]");

    private final JPAQueryFactory jpaQueryFactory;

    public PostRepositoryImpl(JPAQueryFactory jpaQueryFactory) {
        this.jpaQueryFactory = jpaQueryFactory;
    }

    @Override
    public Page<Post> search(Category category, String keyword, Long memberId, Pageable pageable) {
        List<Post> content = jpaQueryFactory
                .selectFrom(post)
                .join(post.member, member).fetchJoin()
                .where(categoryEq(category), titleMatches(keyword), memberIdEq(memberId))
                .orderBy(post.createdAt.desc(), post.id.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPAQuery<Long> countQuery = jpaQueryFactory
                .select(post.count())
                .from(post)
                .where(categoryEq(category), titleMatches(keyword), memberIdEq(memberId));

        // Count 쿼리가 필요할 떄만 countQuery를 실행시켜 성능 향상.
        return PageableExecutionUtils.getPage(content, pageable, countQuery::fetchOne);
    }

    private BooleanExpression categoryEq(Category category) {
        return category == null ? null : post.category.eq(category);
    }

    // LIKE '%keyword%'는 인덱스를 못 타므로 FULLTEXT(ngram) 인덱스를 쓰는 MATCH ... AGAINST로 검색
    private BooleanExpression titleMatches(String keyword) {
        if (keyword == null) {
            return null;
        }
        // boolean mode 연산자로 해석되는 특수문자 제거
        String sanitized = BOOLEAN_MODE_OPERATORS.matcher(keyword).replaceAll(" ").trim();
        if (sanitized.isEmpty()) {
            return null;
        }
        return Expressions.numberTemplate(Double.class,
                "function('match_against', {0}, {1})", post.title, sanitized).gt(0);
    }

    private BooleanExpression memberIdEq(Long memberId) {
        return memberId == null ? null : post.member.id.eq(memberId);
    }
}
