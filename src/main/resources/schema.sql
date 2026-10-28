-- 제목 검색용 FULLTEXT 인덱스
-- JPA @Index로는 FULLTEXT를 만들 수 없어 직접 생성한다.
-- 한글은 공백 기준 토큰 분리가 안 되므로 ngram parser(기본 2글자 단위)를 사용한다.
-- MySQL은 CREATE INDEX IF NOT EXISTS를 지원하지 않으므로, 인덱스가 없을 때만 생성해
-- ddl-auto가 create가 아니어도(update/validate) 재시작 시 실패하지 않도록 한다.
SET @idx_exists := (
    SELECT COUNT(*)
    FROM information_schema.statistics
    WHERE table_schema = DATABASE()
      AND table_name = 'post'
      AND index_name = 'idx_post_title'
);
SET @ddl := IF(@idx_exists = 0,
    'CREATE FULLTEXT INDEX idx_post_title ON post (title) WITH PARSER ngram',
    'SELECT 1');
PREPARE stmt FROM @ddl;
EXECUTE stmt;
DEALLOCATE PREPARE stmt;
