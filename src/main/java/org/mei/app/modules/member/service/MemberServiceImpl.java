package org.mei.app.modules.member.service;

import org.mei.app.modules.member.dao.MemberDAO;
import org.mei.app.modules.member.domain.Member;
import org.mei.core.security.service.Consumer;
import org.mei.core.security.service.ConsumerService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;

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

	private Consumer getMemberToConsumer(Member member) {
		Consumer consumer = new Consumer(member.getUserId());
		consumer.setUserName(member.getUserName());
		consumer.setPassword(member.getPassword());
		consumer.setRole(member.getRoleName());

		return consumer;
	}

	@Override
	public Consumer getConsumerDetails(String username) throws Exception {
		Member member = this.getMemberObject(username);

		if (member == null) return null;

		return getMemberToConsumer(member);
	}

	@Override
	public Consumer saveMemberLoginSuccess(Authentication authentication, UserDetails userDetails) {
		WebAuthenticationDetails details = (WebAuthenticationDetails) authentication.getDetails();
		String userId = userDetails.getUsername();
		Member member = memberDAO.findByUserId(userId);

		member.setUserId(userDetails.getUsername());
		member.setLastAccessIp(details.getRemoteAddress());
		member.setSessionId(details.getSessionId());
		member.setLastAccessDate(new Date());

		memberDAO.saveAndFlush(member);

		return getMemberToConsumer(member);
	}
}
