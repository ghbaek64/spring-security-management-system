package org.mei.app.modules.grant.dao;

import org.mei.app.modules.grant.domain.GrantRulePermit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 * @author Seok Kyun. Choi. 최석균 (Syaku)
 * @site http://syaku.tistory.com
 * @since 16. 7. 14.
 */
public interface GrantRulePermitDAO extends JpaRepository<GrantRulePermit, String> {

	@Query("select max(t.seq) from #{#entityName} t")
	Integer getMaxSeq();
}
