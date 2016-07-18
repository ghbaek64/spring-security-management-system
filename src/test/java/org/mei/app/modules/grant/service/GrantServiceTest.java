package org.mei.app.modules.grant.service;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mei.app.modules.grant.dao.*;
import org.mei.app.modules.grant.domain.*;
import org.mei.config.BootstrapContext;
import org.mei.core.security.access.AccessPermit;
import org.mei.core.security.enums.Method;
import org.mei.core.security.enums.Permission;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Seok Kyun. Choi. 최석균 (Syaku)
 * @site http://syaku.tistory.com
 * @since 16. 7. 14.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { BootstrapContext.class })
@ActiveProfiles("dev")
@Transactional
public class GrantServiceTest {

	@Resource(name = "grantAccessRuleDAO")
	private GrantAccessRuleDAO grantAccessRuleDAO;

	@Resource(name = "grantBasicPermitDAO")
	private GrantBasicPermitDAO grantBasicPermitDAO;

	@Resource(name = "grantUserPermitDAO")
	private GrantUserPermitDAO grantUserPermitDAO;

	@Resource(name = "grantAccessPermitDAO")
	private GrantAccessPermitDAO grantAccessPermitDAO;

	@Resource(name = "grantRulePermitDAO")
	private GrantRulePermitDAO grantRulePermitDAO;

	private final String boardRoleName = AccessPermit.rolePrefix + "BOARD";

	@Test
	public void grantUserPermit() {
		GrantUserPermit grantUserPermit = new GrantUserPermit();
		grantUserPermit.setUserId("admin");
		grantUserPermit.setRoleName(boardRoleName);
		grantUserPermit.setPermissions(Permission.ADMIN.name());

		grantUserPermitDAO.save(grantUserPermit);

		grantUserPermit = new GrantUserPermit();
		grantUserPermit.setUserId("test");
		grantUserPermit.setRoleName("ROLE_PERM_0001");
		grantUserPermit.setPermissions(Permission.LIST.name() + "," + Permission.WRITE.name());

		grantUserPermitDAO.save(grantUserPermit);

		grantUserPermitDAO.findAll();

		grantBasicPermitDAO.deleteAll();
	}

	@Test
	public void grantAccessRule() {
		List<GrantAccessRule> grantAccessRules = new ArrayList<>();

		GrantAccessRule grantAccessRule = new GrantAccessRule();
		grantAccessRule.setPatterns("/member/login");
		grantAccessRule.setAllow(true);
		grantAccessRules.add(grantAccessRule);

		grantAccessRule = new GrantAccessRule();
		grantAccessRule.setPatterns("/member/mypage");
		grantAccessRule.setAllow(true);
		grantAccessRule.setRoleNames("ROLE_USER,ROLE_ADMIN");
		grantAccessRules.add(grantAccessRule);

		grantAccessRule = new GrantAccessRule();
		grantAccessRule.setPatterns("/member/visitor");
		grantAccessRule.setAllow(true);
		grantAccessRule.setRoleNames("ROLE_ADMIN");
		grantAccessRules.add(grantAccessRule);

		grantAccessRule = new GrantAccessRule();
		grantAccessRule.setPatterns("/**");
		grantAccessRule.setAllow(true);
		grantAccessRule.setRoleNames("ROLE_USER,ROLE_ADMIN");
		grantAccessRules.add(grantAccessRule);

		for(GrantAccessRule rule : grantAccessRules) {
			Integer seq = grantAccessRuleDAO.getMaxSeq();
			if (seq == null) seq = 0;
			rule.setSeq(seq + 1);
			grantAccessRuleDAO.save(rule);
		}

		grantAccessRuleDAO.findAll();

		grantAccessRuleDAO.deleteAll();

	}

	@Test
	public void grantBasicPermit() {
		GrantBasicPermit grantBasicPermit = new GrantBasicPermit();
		grantBasicPermit.setRoleName("ROLE_ADMIN");
		grantBasicPermit.setPermissions(Permission.ADMIN.name());

		grantBasicPermitDAO.save(grantBasicPermit);

		grantBasicPermitDAO.findAll();

		grantBasicPermitDAO.deleteAll();
	}

	@Test
	public void grantAccessPermit() {
		GrantAccessPermit grantAccessPermit = new GrantAccessPermit();

		grantAccessPermit.setRoleName(boardRoleName);
		grantAccessPermit.setPatterns("/board/**");

		Integer grantAccessPermitSeq = grantAccessPermitDAO.getMaxSeq();
		if (grantAccessPermitSeq == null) grantAccessPermitSeq = 0;
		grantAccessPermit.setSeq(grantAccessPermitSeq + 1);

		grantAccessPermitDAO.save(grantAccessPermit);

		List<GrantRulePermit> grantRulePermits = new ArrayList<>();

		GrantRulePermit grantRulePermit = new GrantRulePermit();
		grantRulePermit.setRoleName(boardRoleName);
		grantRulePermit.setPatterns("/board");
		grantRulePermit.setPermission(Permission.LIST.name());
		grantRulePermits.add(grantRulePermit);

		grantRulePermit = new GrantRulePermit();
		grantRulePermit.setRoleName(boardRoleName);
		grantRulePermit.setPatterns("/board/*");
		grantRulePermit.setPermission(Permission.VIEW.name());
		grantRulePermits.add(grantRulePermit);

		grantRulePermit = new GrantRulePermit();
		grantRulePermit.setRoleName(boardRoleName);
		grantRulePermit.setPatterns("/board/write");
		grantRulePermit.setPermission(Permission.WRITE.name());
		grantRulePermits.add(grantRulePermit);

		grantRulePermit = new GrantRulePermit();
		grantRulePermit.setRoleName(boardRoleName);
		grantRulePermit.setPatterns("/board");
		grantRulePermit.setMethods(Method.DELETE.name());
		grantRulePermit.setPermission(Permission.MANAGER.name());
		grantRulePermits.add(grantRulePermit);

		for(GrantRulePermit rule : grantRulePermits) {
			Integer seq = grantRulePermitDAO.getMaxSeq();
			if (seq == null) seq = 0;
			rule.setSeq(seq + 1);
			grantRulePermitDAO.save(rule);
		}

		grantAccessPermitDAO.findAll();
		grantRulePermitDAO.findAll();

		grantAccessPermitDAO.deleteAll();
		grantRulePermitDAO.deleteAll();
	}

}
