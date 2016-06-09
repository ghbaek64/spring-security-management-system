CREATE SEQUENCE MEMBER_IDX_SEQ;

CREATE TABLE MEMBER
(
	MEMBER_IDX           CHAR(20) NOT NULL ,
	USER_ID              VARCHAR2(15) NOT NULL ,
	USER_NAME            VARCHAR2(60) NOT NULL ,
	EMAIL                VARCHAR2(150) NULL ,
	PASSWORD             VARCHAR2(200) NULL ,
	REG_DATE             DATETIME NULL ,
	LAST_ACCESS_DATE     DATETIME NULL ,
	LAST_ACCESS_IP       VARCHAR2(128) NULL ,
	SESSION_ID           VARCHAR2(150) NULL ,
	ROLE_NAME            VARCHAR2(50) NOT NULL
);



ALTER TABLE MEMBER
	ADD CONSTRAINT  MEMBER_PK PRIMARY KEY (MEMBER_IDX);



ALTER TABLE MEMBER
ADD CONSTRAINT  MEMBER_UQ_1 UNIQUE (USER_ID);

COMMENT ON TABLE MEMBER IS '회원';

COMMENT ON COLUMN MEMBER.MEMBER_IDX IS '회원 IDX';
COMMENT ON COLUMN MEMBER.USER_ID IS '아이디';
COMMENT ON COLUMN MEMBER.USER_NAME IS '이름';
COMMENT ON COLUMN MEMBER.PASSWORD IS '비밀번호';
COMMENT ON COLUMN MEMBER.EMAIL IS '이메일';
COMMENT ON COLUMN MEMBER.REG_DATE IS '등록일';
COMMENT ON COLUMN MEMBER.LAST_ACCESS_DATE IS '최근 접속일';
COMMENT ON COLUMN MEMBER.LAST_ACCESS_IP IS '최근 접속 아이피';
COMMENT ON COLUMN MEMBER.SESSION_ID IS '세션ID';
COMMENT ON COLUMN MEMBER.ROLE_NAME IS '권한';