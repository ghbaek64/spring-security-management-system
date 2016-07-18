package org.mei.app.modules.grant.dao;

import org.mei.app.modules.grant.domain.GrantAccessPermit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * @author Seok Kyun. Choi. 최석균 (Syaku)
 * @site http://syaku.tistory.com
 * @since 16. 7. 14.
 */
@Repository
public interface GrantAccessPermitDAO extends JpaRepository<GrantAccessPermit, String>{

	@Query("select max(t.seq) from #{#entityName} t")
	Integer getMaxSeq();
}
