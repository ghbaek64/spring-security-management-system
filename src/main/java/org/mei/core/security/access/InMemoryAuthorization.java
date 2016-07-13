package org.mei.core.security.access;

import org.mei.core.common.object.Collecting;
import org.mei.core.security.authorization.ConsumerPermit;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 접근 권한 정보
 * @author Seok Kyun. Choi. 최석균 (Syaku)
 * @site http://syaku.tistory.com
 * @since 16. 6. 22.
 */
public class InMemoryAuthorization implements AccessControlService {
	private static final Map<String, BasicPermit> basicPermit;
	private static final List<AccessRule> accessRules;
	private static final List<AccessPermit> accessPermits;
	private static final Map<String, List<ConsumerPermit>> consumerPermit;

	static {
		basicPermit = new HashMap<>();
		accessRules = new ArrayList<>();
		accessPermits = new ArrayList<>();
		consumerPermit = new HashMap<>();
	}

	public InMemoryAuthorization add(BasicPermit permit) {
		basicPermit.put(permit.getRoleName(), permit);
		return this;
	}

	public InMemoryAuthorization add(AccessRule rules) {
		accessRules.add(rules);
		return this;
	}

	public InMemoryAuthorization add(AccessPermit permits) {
		accessPermits.add(permits);
		return this;
	}

	public InMemoryAuthorization add(ConsumerPermit permits) {
		String username = permits.getUsername();

		if (consumerPermit.containsKey(username)) {
			consumerPermit.get(username).add(permits);
		} else {
			consumerPermit.put(username, Collecting.createList(permits));
		}

		return this;
	}

	@Override
	public List<AccessRule> getAccessRules() {
		return accessRules;
	}

	@Override
	public List<AccessPermit> getAccessPermits() {
		return accessPermits;
	}

	@Override
	public Map<String, BasicPermit> getBasicPermit() {
		return basicPermit;
	}

	@Override
	public BasicPermit getBasicPermit(String roleName) {
		if (basicPermit == null) return null;
		return basicPermit.get(roleName);
	}

	@Override
	public Map<String, List<ConsumerPermit>> getConsumerPermit() {
		return consumerPermit;
	}

	@Override
	public List<ConsumerPermit> getConsumerPermits(String username) {
		if (consumerPermit == null) return null;
		return consumerPermit.get(username);
	}
}
