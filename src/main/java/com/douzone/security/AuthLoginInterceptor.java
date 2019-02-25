package com.douzone.security;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.douzone.mysite.service.UserService;
import com.douzone.mysite.vo.UserVo;

public class AuthLoginInterceptor extends HandlerInterceptorAdapter {
	@Autowired
	private UserService userService;
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		// ac 는 servletContext 전역범위에 묶여 있다. 톰캣이 내려가기전까지 죽지않는다. application scope
//		ApplicationContext ac = 
//				WebApplicationContextUtils.getWebApplicationContext(request.getServletContext());
//		UserService userService = ac.getBean(UserService.class);

		String email = request.getParameter("email");
		String password = request.getParameter("password");
		
		UserVo userVo = userService.loginCheck(email, password);
		
		if (userVo == null) {
			response.sendRedirect(request.getContextPath() + "/user/login");
			return false;
		}

		// 여기까지오면 인증 완료
		// 이제부터 로그인 처리
		HttpSession session = request.getSession(true);
		session.setAttribute("authuser", userVo);
		response.sendRedirect(request.getContextPath() + "/");
		
		return false;
	}

}
