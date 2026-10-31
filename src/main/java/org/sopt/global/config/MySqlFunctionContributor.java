package org.sopt.global.config;

import org.hibernate.boot.model.FunctionContributions;
import org.hibernate.boot.model.FunctionContributor;
import org.hibernate.type.StandardBasicTypes;

/**
 * JPQL/QueryDSL에서 MySQL FULLTEXT 검색(MATCH ... AGAINST)을 쓰기 위한 함수 등록.
 * Spring 빈이 아니라 META-INF/services 파일을 통해 Hibernate가 직접 로딩한다.
 */
public class MySqlFunctionContributor implements FunctionContributor {

    @Override
    public void contributeFunctions(FunctionContributions functionContributions) {
        functionContributions.getFunctionRegistry().registerPattern(
                "match_against",
                // boolean mode + ngram: 검색어가 ngram 구문 검색으로 변환되어 부분 일치처럼 동작
                "match(?1) against (?2 in boolean mode)",
                functionContributions.getTypeConfiguration()
                        .getBasicTypeRegistry()
                        .resolve(StandardBasicTypes.DOUBLE)
        );
    }
}
