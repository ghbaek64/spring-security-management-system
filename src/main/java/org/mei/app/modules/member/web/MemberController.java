package org.mei.app.modules.member.web;

import org.mei.app.modules.member.service.MemberService;
import org.mei.core.module.handler.ModuleAndView;
import org.mei.core.module.handler.SuccessHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.session.SessionInformation;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Seok Kyun. Choi. 최석균 (Syaku)
 * @site http://syaku.tistory.com
 * @since 16. 5. 31.
 */
@Controller
@RequestMapping("/member")
public class MemberController {

	@Autowired
	private SessionRegistry sessionRegistry;

	@Resource(name= "memberService") private MemberService memberService;

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public ModelAndView dispMemberLogin(
			@RequestParam(name = "redirect_url", required = false) String redirectUrl,
			@RequestParam(name = "chain", required = false) String chain) {
		ModuleAndView mav = new ModuleAndView("/member/login.ftl");
		mav.addObject("redirectUrl", redirectUrl);
		mav.addObject("chain", chain);
		return mav.ok();
	}

	@RequestMapping(value = "/mypage", method = RequestMethod.GET)
	public ModelAndView dispMemberMypage(Authentication authentication) {
		ModuleAndView mav = new ModuleAndView("/member/mypage.ftl");

		mav.addObject("member", memberService.getMemberObject(authentication.getName()));

		return mav.ok();
	}

	@RequestMapping(value = "/visitor", method = RequestMethod.GET)
	public ModelAndView dispMemberVisitor() {
		ModuleAndView mav = new ModuleAndView("/member/visitor.ftl");

		List<SessionInformation> activeSessions = new ArrayList<>();
		for(Object principal : sessionRegistry.getAllPrincipals()) {
			activeSessions.addAll(sessionRegistry.getAllSessions(principal, false));
		}

		List<Map<String, Object>> visitors = new ArrayList<>();
		for(SessionInformation sessionInformation : activeSessions) {
			System.out.println(sessionInformation.isExpired());
			System.out.println(sessionInformation.getSessionId());
			System.out.println(sessionInformation.getLastRequest());

			Map<String, Object> data = new HashMap<>();

			data.put("lastRequest", sessionInformation.getLastRequest());
			data.put("sessionId", sessionInformation.getSessionId());

			Object principalObj = sessionInformation.getPrincipal();
			if (principalObj instanceof User) {
				User user = (User) principalObj;

				data.put("username", user.getUsername());
				System.out.println(user.toString());
			}

			visitors.add(data);

		}

		mav.addObject("visitors", visitors);

		return mav.ok();
	}

	@RequestMapping(value = "/visitor", method = RequestMethod.DELETE)
	public @ResponseBody SuccessHandler procMemberVisitorDelete(@RequestBody Map< String, Object> data) {
		SessionInformation session = sessionRegistry.getSessionInformation((String) data.get("sessionId"));
		if (session != null) {
			session.expireNow();
		}

		return new SuccessHandler();
	}
}
