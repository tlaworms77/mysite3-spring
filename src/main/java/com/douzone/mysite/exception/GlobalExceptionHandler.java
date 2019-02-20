package com.douzone.mysite.exception;

import java.io.PrintWriter;
import java.io.StringWriter;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

// @ControllerAdvice도 스캐닝이 되도록 해야된다. => context에 등록
@ControllerAdvice
public class GlobalExceptionHandler{
	
	private static final Log LOG = LogFactory.getLog(GlobalExceptionHandler.class);
	
	@ExceptionHandler( Exception.class )
	public ModelAndView handlerException( 
			HttpServletRequest request, 
			Exception e) {
		
		// 1. 로깅 작업
		StringWriter errors = new StringWriter();
		e.printStackTrace(new PrintWriter(errors));
		LOG.error(errors.toString());
		
		// 2. 시스템 오류 안내 페이지 전환
		ModelAndView mav = new ModelAndView();
		mav.addObject("errors", errors.toString());
		mav.setViewName("error/exception");
		//mav.setViewName("error/404[500]");
		return mav;
	}
}
