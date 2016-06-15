package org.mei.app.modules.board.web;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author Seok Kyun. Choi. 최석균 (Syaku)
 * @site http://syaku.tistory.com
 * @since 16. 6. 15.
 */
@Controller
@RequestMapping("/board")
public class BoardController {

	@RequestMapping(value = "", method = RequestMethod.GET)
	public ModelAndView dispBoardList() {
		ModelAndView mav = new ModelAndView("/app/modules/board/list");
		return mav;
	}

	@RequestMapping(value = "/view", method = RequestMethod.GET)
	public ModelAndView dispBoardView() {
		ModelAndView mav = new ModelAndView("/app/modules/board/view");
		return mav;
	}

	@RequestMapping(value = "/write", method = RequestMethod.GET)
	public ModelAndView dispBoardWrite() {
		ModelAndView mav = new ModelAndView("/app/modules/board/write");
		return mav;
	}

	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView dispBoardDelete() {
		ModelAndView mav = new ModelAndView("/app/modules/board/delete");
		return mav;
	}
}
