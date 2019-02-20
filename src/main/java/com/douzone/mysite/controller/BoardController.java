package com.douzone.mysite.controller;

import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.douzone.mysite.service.BoardService;
import com.douzone.mysite.vo.BoardVo;
import com.douzone.mysite.vo.PageVo;

@RequestMapping("/board")
@Controller
public class BoardController {
	@Autowired
	private BoardService boardService;

	@RequestMapping({ "", "list" })
	public String list(Model model, String no, String kwd) {
		Map<String, Object> map = boardService.getList(no, kwd);
		model.addAttribute("list", map.get("list"));
		model.addAttribute("pageVo", ((PageVo) map.get("pageVo")));
		return "/board/list";
	}

	@RequestMapping(value = "/write", method = RequestMethod.GET)
	public String write() {
		return "/board/write";
	}

	@RequestMapping(value = "/write", method = RequestMethod.POST)
	public String write(HttpSession session, @ModelAttribute BoardVo vo) {
		String path = boardService.insert(session, vo);
		return path;
	}

	@RequestMapping("/view")
	public String view(Model model, String no) {
		model.addAttribute("vo", boardService.getView(no));
		return "/board/view";
	}

	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public String delete(Model model, String no) {
		System.out.println("no: " + no);
		model.addAttribute("no", no);
		return "/board/delete";
	}

	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public String delete(HttpSession session, long no, String password) {
		System.out.println("pw: " + password);
		String path = boardService.delete(session, no, password);
		return path;
	}

	@RequestMapping(value = "/modify", method = RequestMethod.GET)
	public String modify(Model model, BoardVo vo) {
		model.addAttribute("vo", vo);
		return "/board/modify";
	}
	
	@RequestMapping(value = "/modify", method = RequestMethod.POST)
	public String modify(HttpSession session, BoardVo vo) {
		System.out.println("no: " + vo.getNo());
		String path = boardService.modify(session, vo);
		return path;
	}

	@RequestMapping(value = "/reply", method = RequestMethod.GET)
	public String reply(Model model, BoardVo vo) {
		model.addAttribute("vo", vo);
		return "/board/reply";
	}

	@RequestMapping(value = "/reply", method = RequestMethod.POST)
	public String reply(HttpSession session, BoardVo vo) {
		String path = boardService.reply(session, vo);
		return path;
	}

}
