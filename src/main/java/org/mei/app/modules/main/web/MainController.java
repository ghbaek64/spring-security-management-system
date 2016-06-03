package org.mei.app.modules.main.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author Seok Kyun. Choi. 최석균 (Syaku)
 * @site http://syaku.tistory.com
 * @since 16. 6. 3.
 */
@Controller
public class MainController {

	@RequestMapping(value = {"", "/"}, method = RequestMethod.GET)
	public ModelAndView dispMainView() {
		ModelAndView mav = new ModelAndView("/app/modules/main/main");
		return mav;
	}
}
