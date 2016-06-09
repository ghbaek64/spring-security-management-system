package org.mei.app.modules.member.service;

import org.mei.app.modules.member.domain.Member;

/**
 * @author Seok Kyun. Choi. 최석균 (Syaku)
 * @site http://syaku.tistory.com
 * @since 16. 6. 9.
 */
public interface MemberService {
	Member getMemberObject(String userId);
}
