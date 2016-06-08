package org.mei.app.modules.member.service;

import org.mei.app.modules.member.dao.MemberDAO;
import org.mei.app.modules.member.domain.Member;
import org.mei.core.security.service.Consumer;
import org.mei.core.security.service.ConsumerService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * @author Seok Kyun. Choi. 최석균 (Syaku)
 * @site http://syaku.tistory.com
 * @since 16. 6. 8.
 */
@Service
public class MemberService implements ConsumerService {
	@Resource(name="memberDAO") private MemberDAO memberDAO;

	@Transactional(readOnly = true)
	public Member getMemberObject(String userId) {
		return memberDAO.findByUserId(userId);
	}

	@Override
	public Consumer getConsumerDetails(String username) throws Exception {
		Member member = this.getMemberObject(username);

		if (member == null) return null;

		Consumer consumer = new Consumer();
		consumer.setUserId(member.getUserId());
		consumer.setUserName(member.getUserName());
		consumer.setPassword(member.getPassword());
		consumer.setRole(member.getRoleName());

		return consumer;
	}
}
