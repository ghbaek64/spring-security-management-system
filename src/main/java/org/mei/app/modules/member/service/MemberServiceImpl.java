package org.mei.app.modules.member.service;

import org.mei.app.modules.member.dao.MemberDAO;
import org.mei.app.modules.member.domain.Member;
import org.mei.core.security.authentication.ConsumerDetailsService;
import org.mei.core.security.authorization.Consumer;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * @author Seok Kyun. Choi. 최석균 (Syaku)
 * @site http://syaku.tistory.com
 * @since 16. 6. 8.
 */
@Service("memberService")
@Transactional
public class MemberServiceImpl implements ConsumerDetailsService, MemberService {
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

		return new Consumer(member.getUserId(), member.getPassword(), !accountNonLocked.booleanValue(), member.getRoleName());
	}
}
