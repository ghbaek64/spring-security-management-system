package org.mei.app.modules.grant.dao;

import org.mei.app.modules.grant.domain.GrantUserPermit;
import org.mei.app.modules.grant.domain.GrantUserPermitPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Seok Kyun. Choi. 최석균 (Syaku)
 * @site http://syaku.tistory.com
 * @since 16. 7. 14.
 */
@Repository
public interface GrantUserPermitDAO extends JpaRepository<GrantUserPermit, GrantUserPermitPK>{
	List<GrantUserPermit> findByUserId(String userId);
}
