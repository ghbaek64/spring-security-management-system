package org.mei.app.modules.grant.dao;

import org.mei.app.modules.grant.domain.GrantBasicPermit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Seok Kyun. Choi. 최석균 (Syaku)
 * @site http://syaku.tistory.com
 * @since 16. 7. 14.
 */
@Repository
public interface GrantBasicPermitDAO extends JpaRepository<GrantBasicPermit, String> {
}
