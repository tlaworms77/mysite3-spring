package com.douzone.mysite.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.douzone.mysite.repository.UserDao;
import com.douzone.mysite.vo.UserVo;

@Service
public class UserService {
	
	@Autowired
	private UserDao userDao;

	public void join(@ModelAttribute UserVo userVo) {
		// 비즈니스 로직
		// 1. DB에 가입 회원 정보 insert 하기
		userDao.insert(userVo);
		// 2. email 주소 확인하는 메일 보내기
	}
	
	
	
}
