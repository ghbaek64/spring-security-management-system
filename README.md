## Spring Security Management System

스프링 시큐리티를 이용하여 보안 시스템을 구축하고, 사용자와 권한을 쉽게 관리할 수 있는 인터페이스를 제공합니다.

본 자료를 데모용으로 개발되었습니다.


#### 개발환경

>Java 1.7+  
Tomcat 7.x  
Gradle 2.12  
Spring MVC 4.2.4.RELEASE  
Spring security 4.1.0.RELEASE  
H2  
Hibernate  
Spring Data JPA  


#### 개발 사항

1. 스프링 및 시큐리티 설정을 모두 자바기반을 사용 (XML namespace to Java configure
2. 로그인 과정을 직접 구현하기.
3. 데이터를 이용하여 로그인 처리
4. 데이터를 이용한 권한 관리

H2 데이터베이스를 이용하여 사용자과 권한을 관리합니다. 데이터베이스는 ORM 프레임워크와 JPA 라이브러리를 사용하였습니다.