package org.mei.core.security.access;

import org.mei.core.common.object.Collecting;
import org.mei.core.security.authorization.ConsumerPermission;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Seok Kyun. Choi. 최석균 (Syaku)
 * @site http://syaku.tistory.com
 * @since 16. 6. 22.
 */
public class InMemoryAuthorization implements AccessControlService {
	private static final Map<String, BasicPermission> basicPermission;
	private static final List<AccessRole> accessRole;
	private static final List<AccessPermissionRole> accessPermissionRole;
	private static final Map<String, List<ConsumerPermission>> consumerPermission;

	static {
		basicPermission = new HashMap<>();
		accessRole = new ArrayList<>();
		accessPermissionRole = new ArrayList<>();
		consumerPermission = new HashMap<>();
	}

	public InMemoryAuthorization add(BasicPermission permission) {
		basicPermission.put(permission.getRoleName(), permission);
		return this;
	}

	public InMemoryAuthorization add(AccessRole role) {
		accessRole.add(role);
		return this;
	}

	public InMemoryAuthorization add(AccessPermissionRole role) {
		accessPermissionRole.add(role);
		return this;
	}

	public InMemoryAuthorization add(ConsumerPermission consumer) {
		String username = consumer.getUsername();

		if (consumerPermission.containsKey(username)) {
			consumerPermission.get(username).add(consumer);
		} else {
			consumerPermission.put(username, Collecting.createList(consumer));
		}

		return this;
	}

	@Override
	public List<AccessRole> getAccessRoleList() {
		return accessRole;
	}

	@Override
	public List<AccessPermissionRole> getAccessPermissionRoleList() {
		return accessPermissionRole;
	}

	@Override
	public Map<String, BasicPermission> getBasicPermissionObject() {
		return basicPermission;
	}

	@Override
	public BasicPermission getBasicPermissionObject(String roleName) {
		if (basicPermission == null) return null;
		return basicPermission.get(roleName);
	}

	@Override
	public Map<String, List<ConsumerPermission>> getConsumerPermissionObject() {
		return consumerPermission;
	}

	@Override
	public List<ConsumerPermission> getConsumerPermissionObject(String username) {
		if (consumerPermission == null) return null;
		return consumerPermission.get(username);
	}
}
