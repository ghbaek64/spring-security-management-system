package org.mei.core.security.access;

import org.mei.core.security.authorization.Authority;
import org.mei.core.security.authorization.ConsumerManager;
import org.mei.core.security.enums.Method;
import org.mei.core.security.enums.Permission;
import org.mei.core.util.AntPathMatchers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * Request에 대한 일치하는 권한(Role, Permission)을 조회한다.
 *
 * @author Seok Kyun. Choi. 최석균 (Syaku)
 * @site http ://syaku.tistory.com
 * @since 16. 6. 13.
 */
public class AccessMatchingService {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	private AntPathMatchers antPathMatchers = new AntPathMatchers();

	private final AccessControlService accessControlService;
	public AccessMatchingService(AccessControlService accessControlService) {
		this.accessControlService = accessControlService;
	}

	public AccessControlService getAccessControlService() {
		return accessControlService;
	}

	/**
	 * url과 method에 접속하기 위해 필요한 룰을 조회한다.
	 * 일치하는 룰을 찾으면 리턴하고 작업을 종료한다.
	 *
	 * @param url    request url
	 * @param method request method
	 * @return
	 */
	public Collection<ConfigAttribute> needConfigAttributes(String url, Method method) {
		Collection<ConfigAttribute> attributes = new ArrayList<>();

		AccessRule accessRule = this.needRule(url, method);
		AccessPermit accessPermit = this.needPermit(url, method);

		List<String> roleNames = new ArrayList<>();
		if (accessRule != null) roleNames.addAll(accessRule.getRoleName());
		if (accessPermit != null) roleNames.add(accessPermit.getRoleName());

		for(String roleName : roleNames) {
			attributes.add(new SecurityConfig(roleName));
		}

		return attributes;
	}

	public AccessRule needRule(String url, Method method) {
		List<AccessRule> accessRules = accessControlService.getAccessRules();

		if (accessRules == null) return null;

		for(AccessRule accessRole : accessRules) {
			List<Method> methods = accessRole.getMethod();

			if (methods != null) {
				if (methods.indexOf(method) == -1) continue;
			}

			List<String> patterns = accessRole.getPattern();
			if (antPathMatchers.matches(patterns, url)) {
				return accessRole;
			}
		}

		return null;
	}

	public AccessPermit needPermit(String url, Method method) {
		List<AccessPermit> accessPermits = accessControlService.getAccessPermits();

		if (accessPermits == null) return null;

		for(AccessPermit accessPermit : accessPermits) {
			List<Method> methods = accessPermit.getMethod();

			if (methods != null) {
				if (methods.indexOf(method) == -1) continue;
			}

			List<String> patterns = accessPermit.getPattern();
			if (antPathMatchers.matches(patterns, url)) {
				return accessPermit;
			}
		}

		return null;
	}

	public Permission needPermission(String url, Method method) {
		return needPermission(url, method, null);
	}

	public Permission needPermission(String url, Method method, AccessPermit accessPermit) {
		if (accessPermit == null) accessPermit = needPermit(url, method);

		if (accessPermit == null) return null;

		List<RulePermit> rulePermits = accessPermit.getAccessPermission();
		if (rulePermits == null) return null;

		for(RulePermit rulePermit : rulePermits) {
			List<Method> methods = rulePermit.getMethod();

			if (methods != null) {
				if (methods.indexOf(method) == -1) continue;
			}

			List<String> patterns = rulePermit.getPattern();
			if (antPathMatchers.matches(patterns, url)) {
				return rulePermit.getPermission();
			}
		}

		return null;
	}

	/**
	 * 기본 퍼미션의 데이터를 조회한다.
	 *
	 * @param roleName
	 * @return 기본 퍼미션
	 */
	public List<Permission> basicPermits(String roleName) {

		if (roleName == null) return null;

		Map<String, BasicPermit> basicPermitMap = accessControlService.getBasicPermit();
		BasicPermit basicPermit = basicPermitMap.get(roleName);

		if (basicPermit == null) return null;

		return basicPermit.getPermission();
	}

	/**
	 * 사용자가 가진 퍼미션을 조회한다.
	 *
	 * 1) 사용가(Consumer) 가진 룰(Authority)에 대한 기본 퍼미션(BasicPermission) 조회
	 * 2) 사용자(Consumer)가 접근한 룰(roleName)에 대한 기본 퍼미션(BasicPermission) 조회
	 * 3) 사용자(Consumer)가 접근한 룰(roleName)에 대한 사용자가 가진 퍼미션(Authority -> Privilege) 조회
	 *
	 * 1번의 퍼미션에 ADMIN or MANAGER 을 가진 경우 반환 후 종료
	 * 2번의 퍼미션에 NONE를 가진 경우 1번에서 얻은 퍼미션 모두 초기화 하고 3번으로 넘어간다.
	 * 3번의 결과를 반환한다.
	 *
	 * @param consumerAuthentication 사용자 인증된 정보
	 * @param roleName roleName
	 * @return 사용자가 가진 퍼미션
	 */
	public List<Permission> hasPermission(ConsumerManager consumerAuthentication, String roleName) {
		List<Permission> hasPrivilege = this.basicPermits(consumerAuthentication.getGroupRole());

		if (hasPrivilege == null) hasPrivilege = new ArrayList<>();

		if (hasPrivilege.indexOf(Permission.ADMIN) > -1 || hasPrivilege.indexOf(Permission.MANAGER) > -1 || roleName == null) return hasPrivilege;

		List<Permission> basicPermits = this.basicPermits(roleName);

		if (basicPermits != null) {
			if (basicPermits.indexOf(Permission.NONE) > -1) {
				hasPrivilege.clear();
			} else {
				if (basicPermits.indexOf(Permission.ADMIN) > -1 || basicPermits.indexOf(Permission.MANAGER) > -1) return basicPermits;
				for(Permission permission : basicPermits) {
					if (hasPrivilege.indexOf(permission) == -1) hasPrivilege.add(permission);
				}
			}
		}

		List<Authority> authorities = (List<Authority>) consumerAuthentication.getAuthorities();
		List<Permission> hasPermission = null;

		for(Authority hasAuthority : authorities) {
			String authority = hasAuthority.getAuthority();
			if (!authority.equals(roleName)) continue;
			hasPermission = hasAuthority.getPrivilege();
			break;
		}

		if (hasPermission == null) {
			return hasPrivilege;
		} else {
			if (hasPermission.indexOf(Permission.ADMIN) > -1 || hasPermission.indexOf(Permission.MANAGER) > -1) return hasPermission;
			for(Permission permission : hasPermission) {
				if (hasPrivilege.indexOf(permission) == -1) hasPrivilege.add(permission);
			}
		}

		return hasPrivilege;
	}
}
