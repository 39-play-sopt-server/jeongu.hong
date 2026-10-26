-- 로컬 테스트용 초기 데이터
-- created_at / updated_at은 JPA Auditing을 거치지 않으므로 직접 넣는다.

INSERT INTO member (name, email, deleted_at, created_at, updated_at) VALUES
    ('홍길동', 'hong@sopt.org', NULL, '2026-09-01 10:00:00', '2026-09-01 10:00:00'),
    ('김솝트', 'kim@sopt.org',  NULL, '2026-09-02 10:00:00', '2026-09-02 10:00:00');

-- 총 25개 (SOPT 9 / PLAY 8 / DANCE 8), 제목에 '스프링' 포함 7개 (SOPT 6 / PLAY 1)
INSERT INTO post (title, content, category, view_count, member_id, created_at, updated_at) VALUES
    ('스프링 부트 시작하기',        '스프링 부트 프로젝트 세팅 방법', 'SOPT',  0, (SELECT id FROM member WHERE email = 'hong@sopt.org'), '2026-10-01 09:00:00', '2026-10-01 09:00:00'),
    ('주말 보드게임 모임',          '이번 주 토요일에 모여요',        'PLAY',  0, (SELECT id FROM member WHERE email = 'kim@sopt.org'),  '2026-10-02 09:00:00', '2026-10-02 09:00:00'),
    ('이번주 안무',         '춤이 좋네요',           'DANCE', 0, (SELECT id FROM member WHERE email = 'hong@sopt.org'), '2026-10-03 09:00:00', '2026-10-03 09:00:00'),
    ('스프링 JPA 연관관계 매핑',    '단방향과 양방향 매핑',           'SOPT',  0, (SELECT id FROM member WHERE email = 'hong@sopt.org'), '2026-10-04 09:00:00', '2026-10-04 09:00:00'),
    ('한강 피크닉 후기',            '날씨가 정말 좋았어요',           'PLAY',  0, (SELECT id FROM member WHERE email = 'kim@sopt.org'),  '2026-10-05 09:00:00', '2026-10-05 09:00:00'),
    ('댄스 배틀 공지',       '참가 신청 받습니다',             'DANCE', 0, (SELECT id FROM member WHERE email = 'kim@sopt.org'),  '2026-10-06 09:00:00', '2026-10-06 09:00:00'),
    ('SOPT 세미나 3주차 정리',      'JPA 영속성 컨텍스트',            'SOPT',  0, (SELECT id FROM member WHERE email = 'hong@sopt.org'), '2026-10-07 09:00:00', '2026-10-07 09:00:00'),
    ('스프링 MT 장소 추천',         '봄 MT 장소 추천 받아요',         'PLAY',  0, (SELECT id FROM member WHERE email = 'kim@sopt.org'),  '2026-10-08 09:00:00', '2026-10-08 09:00:00'),
    ('케이팝 커버 연습 일정',       '매주 수요일 저녁',               'DANCE', 0, (SELECT id FROM member WHERE email = 'hong@sopt.org'), '2026-10-09 09:00:00', '2026-10-09 09:00:00'),
    ('스프링 트랜잭션 전파 정리',   'REQUIRED와 REQUIRES_NEW',        'SOPT',  0, (SELECT id FROM member WHERE email = 'hong@sopt.org'), '2026-10-10 09:00:00', '2026-10-10 09:00:00'),
    ('볼링 번개 모집',              '오늘 저녁 볼링 치실 분',         'PLAY',  0, (SELECT id FROM member WHERE email = 'kim@sopt.org'),  '2026-10-11 12:00:00', '2026-10-11 12:00:00'),
    ('왁킹 워크숍 신청',            '외부 강사 초빙 워크숍',          'DANCE', 0, (SELECT id FROM member WHERE email = 'hong@sopt.org'), '2026-10-11 12:00:00', '2026-10-11 12:00:00'),
    ('스프링 QueryDSL 동적 쿼리',   'BooleanExpression 활용',         'SOPT',  0, (SELECT id FROM member WHERE email = 'kim@sopt.org'),  '2026-10-11 12:00:00', '2026-10-11 12:00:00'),
    ('방탈출 카페 후기',            '난이도 최상 테마 도전',          'PLAY',  0, (SELECT id FROM member WHERE email = 'kim@sopt.org'),  '2026-10-12 09:00:00', '2026-10-12 09:00:00'),
    ('동적 쿼리 너무 어려워요',            '진심 너무 어려움',             'DANCE', 0, (SELECT id FROM member WHERE email = 'hong@sopt.org'), '2026-10-13 09:00:00', '2026-10-13 09:00:00'),
    ('코드 리뷰 문화 이야기',       '좋은 리뷰를 남기는 법',          'SOPT',  0, (SELECT id FROM member WHERE email = 'kim@sopt.org'),  '2026-10-14 09:00:00', '2026-10-14 09:00:00'),
    ('야구 직관 같이 가요',         '잠실 야구장 단체 관람',          'PLAY',  0, (SELECT id FROM member WHERE email = 'hong@sopt.org'), '2026-10-15 09:00:00', '2026-10-15 09:00:00'),
    ('댄스 동아리 공연 영상',       '지난 공연 영상 공유',            'DANCE', 0, (SELECT id FROM member WHERE email = 'kim@sopt.org'),  '2026-10-16 09:00:00', '2026-10-16 09:00:00'),
    ('스프링 시큐리티 입문',        '필터 체인 구조 이해하기',        'SOPT',  0, (SELECT id FROM member WHERE email = 'hong@sopt.org'), '2026-10-17 09:00:00', '2026-10-17 09:00:00'),
    ('노래방 번개',                 '코인 노래방 가실 분',            'PLAY',  0, (SELECT id FROM member WHERE email = 'kim@sopt.org'),  '2026-10-18 09:00:00', '2026-10-18 09:00:00'),
    ('락킹 연습 후기',              '포인트 동작 연습',               'DANCE', 0, (SELECT id FROM member WHERE email = 'hong@sopt.org'), '2026-10-19 09:00:00', '2026-10-19 09:00:00'),
    ('SOPT 합동 세미나 후기',       '파트 간 협업 경험',              'SOPT',  0, (SELECT id FROM member WHERE email = 'kim@sopt.org'),  '2026-10-20 09:00:00', '2026-10-20 09:00:00'),
    ('캠핑 준비물 공유',            '가평 캠핑 준비물 리스트',        'PLAY',  0, (SELECT id FROM member WHERE email = 'hong@sopt.org'), '2026-10-21 09:00:00', '2026-10-21 09:00:00'),
    ('안무 창작 스터디 모집',       '자유 안무 창작 스터디',          'DANCE', 0, (SELECT id FROM member WHERE email = 'kim@sopt.org'),  '2026-10-22 09:00:00', '2026-10-22 09:00:00'),
    ('스프링 첫 글 (뒤늦게 등록)',   '가장 오래된 created_at',         'SOPT',  0, (SELECT id FROM member WHERE email = 'hong@sopt.org'), '2026-09-30 09:00:00', '2026-09-30 09:00:00');
