package org.mei.core.module.handler;

import org.springframework.web.servlet.ModelAndView;

/**
 * @author Seok Kyun. Choi. 최석균 (Syaku)
 * @site http://syaku.tistory.com
 * @since 16. 8. 4.
 */
public class ModuleAndView {
	ModelAndView mav;
	private final String template;

	public ModuleAndView(String template) {
		mav = new ModelAndView("/index.ftl");
		this.template = template;
	}

	public void addObject(String name, Object value) {
		mav.addObject(name, value);
	}

	public ModelAndView ok() {
		mav.addObject("template", template);
		return mav;
	}
}
