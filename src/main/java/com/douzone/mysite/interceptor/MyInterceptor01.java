package com.douzone.mysite.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

public class MyInterceptor01 implements HandlerInterceptor {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		// Handler 호출 전
		// Handler 호출 여부를 결정(boolean의 반환 값에 따라서 호출 여부)
		System.out.println("MyInterceptor01: preHandle");
		return false; // false 라서 핸들러가 일어나기 전에 호출 따라서 밑에 있는 handler들이 일어나지않는다.
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		// 헨들러(작업[비즈니스]이 다끝난다음) 호출이 된 후
		System.out.println("MyInterceptor01: postHandle");

	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		// View의 rendering 작업까지 완료된 후에 호출
		System.out.println("MyInterceptor01: afterCompletion");

	}

}
