INSERT INTO MEMBER (MEMBER_IDX, USER_ID, USER_NAME, EMAIL, PASSWORD, REG_DATE, LAST_ACCESS_DATE, LAST_ACCESS_IP, SESSION_ID, ROLE_NAME)
VALUES ('MEM00000000000000001', 'admin', '관리자', null, '03ac674216f3e15c761ee1a5e255f067953623c8b388b4459e13f978d7c846f4', CURRENT_TIMESTAMP(), null, null, null, 'ROLE_ADMIN');

INSERT INTO MEMBER (MEMBER_IDX, USER_ID, USER_NAME, EMAIL, PASSWORD, REG_DATE, LAST_ACCESS_DATE, LAST_ACCESS_IP, SESSION_ID, ROLE_NAME)
VALUES ('MEM00000000000000002', 'test', '테스트', null, '03ac674216f3e15c761ee1a5e255f067953623c8b388b4459e13f978d7c846f4', CURRENT_TIMESTAMP(), null, null, null, 'ROLE_USER');

INSERT INTO MEMBER (MEMBER_IDX, USER_ID, USER_NAME, EMAIL, PASSWORD, REG_DATE, LAST_ACCESS_DATE, LAST_ACCESS_IP, SESSION_ID, ROLE_NAME)
VALUES ('MEM00000000000000003', 'block', '차단사용자', null, '03ac674216f3e15c761ee1a5e255f067953623c8b388b4459e13f978d7c846f4', CURRENT_TIMESTAMP(), null, null, null, 'ROLE_BLOCK');