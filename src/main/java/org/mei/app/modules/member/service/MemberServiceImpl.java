package org.mei.app.modules.member.service;

import org.mei.app.modules.member.dao.MemberDAO;
import org.mei.app.modules.member.domain.Member;
import org.mei.core.common.object.Collecting;
import org.mei.core.security.authorization.Authority;
import org.mei.core.security.authorization.Consumer;
import org.mei.core.security.authorization.Privilege;
import org.mei.core.security.enums.Permission;
import org.mei.core.security.service.ConsumerService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Seok Kyun. Choi. 최석균 (Syaku)
 * @site http://syaku.tistory.com
 * @since 16. 6. 8.
 */
@Service("memberService")
@Transactional
public class MemberServiceImpl implements ConsumerService, MemberService {
	@Resource(name="memberDAO") private MemberDAO memberDAO;

	@Override
	@Transactional(readOnly = true)
	public Member getMemberObject(String userId) {
		return memberDAO.findByUserId(userId);
	}

	@Override
	public Consumer loadUserByUsername(String username) throws UsernameNotFoundException {
		Member member = this.getMemberObject(username);

		if (member == null) throw new UsernameNotFoundException("");

		Boolean accountNonLocked = member.isAccountBlock();

		List<Authority> authorities =
				Collecting.createList(
					new Authority(member.getRoleName(), null),
					new Authority("ROLE_PERM_0001",
						Collecting.createList(
							Permission.LIST, Permission.WRITE
						)
					)
				);

		return new Consumer(member.getUserId(), member.getPassword(), !accountNonLocked.booleanValue(), authorities);
	}
}
