package com.douzone.mysite.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.douzone.mysite.service.UserService;
import com.douzone.mysite.vo.UserVo;

@Controller
@RequestMapping("/user")
public class UserController {
	// userService 를 직접 만들서 사용하지않고 주입받아서 사용한다.
	@Autowired
	private UserService userService;

	@RequestMapping(value = "/join", method = RequestMethod.GET)
	public String join() {
		return "/user/join";
	}

	@RequestMapping(value = "/join", method = RequestMethod.POST)
	public String join(@ModelAttribute UserVo userVo) {
		userService.join(userVo);
		return "redirect:/user/joinsuccess";
	}

	@RequestMapping("/joinsuccess")
	public String joinSuccess() {
		return "/user/joinsuccess";
	}

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String login() {
		return "/user/login";
	}

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String login(HttpSession session, Model model, String email, String password) {
		UserVo authuser = userService.loginCheck(email, password);

		if (authuser == null) {
			model.addAttribute("result", "fail");
			return "/user/login";
		}
		session.setAttribute("authuser", authuser);
		return "redirect:/";
	}
// interceptoer 로 logout 처리
//	@RequestMapping("/logout")
//	public String logout(HttpSession session) {
//		if (session != null && session.getAttribute("authuser") != null) {
//			// logout 처리
//			// session 을 날림
//			session.removeAttribute("authuser");
//			session.invalidate();
//			return "redirect:/";
//		}
//
//		// 거의 없는 경우이지만 인증이 안된 상황에서 접근한 상황 logout을
//		UserVo authUser = (UserVo) session.getAttribute("authuser");
//		if (authUser == null) {
//			return "redirect:/";
//		}
//
//		return "redirect:/";
//	}

	@RequestMapping(value = "/modify", method = RequestMethod.GET)
	public String modify(HttpSession session, Model model) {
		/* 접근제어 */
		UserVo authUser = null;
		if (session != null) {
			authUser = (UserVo) session.getAttribute("authuser");
			System.out.println(authUser);
		}
		if (authUser == null) {
			System.out.println(authUser);
			return "redirect:/";
		}

		authUser =  userService.getUserInfo(authUser.getNo());
		model.addAttribute("authuser", authUser);

		return "/user/modify";
	}

	@RequestMapping(value = "/modify", method = RequestMethod.POST)
	public String modify(HttpSession session, @ModelAttribute UserVo vo) {
		if(session.getAttribute("authuser") == null) {
			return "redirect:/";
		}
		
		vo.setNo(((UserVo)session.getAttribute("authuser")).getNo());
		boolean modifySuccess = userService.modify(vo);
		
		if(!modifySuccess) {
			System.out.println("회원 정보 수정 실패");
			return "/user/modify";
		}
		
		System.out.println("회원 정보 수정 성공!");
		
		return "redirect:/";
	}
	
//	@ExceptionHandler( UserDaoException.class )
//	public String handleUserDaoException() {
//		// 1. 로깅작업
//		// 2. 페이지 전환(사과 페이지)
//		return "error/exception";
//	}
}
