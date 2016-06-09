package org.mei.app.modules.member.dao;

import org.mei.app.modules.member.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

/**
 * @author Seok Kyun. Choi. 최석균 (Syaku)
 * @site http://syaku.tistory.com
 * @since 16. 6. 8.
 */
public interface MemberDAO extends JpaRepository<Member, String> {

	Member findByUserId(@Param("user_id") String userId);
}
