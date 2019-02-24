package com.douzone.mysite.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.douzone.mysite.repository.UserDao;
import com.douzone.mysite.vo.UserVo;

@Service
public class UserService {

	@Autowired
	private UserDao userDao;

	public void join(UserVo userVo) {
		// 비즈니스 로직
		// 1. DB에 가입 회원 정보 insert 하기

		// SQLException은 기술은 맞긴하지만
		// SQLException은 service비즈니스에 알맞지 않은 기술이다.
		// 직접 기술할 필요 없다. ==> 사용자 정의로 바꿔서 알맞은 기술로 변경하기!
	
			userDao.insert(userVo);
//		} catch (UserDaoException e) {
//			// 1. 로깅
//			System.out.println("error:" + e);
//			
//			// 2. 화면전환 -> service단에서 할 수 없기 때문에 화면단에 올려야된다.
//		}

		// 2. email 주소 확인하는 메일 보내기
	}

	public UserVo loginCheck(UserVo vo) {
		UserVo userVo = userDao.loginCheck(vo.getEmail(), vo.getPassword());
		return userVo;
	}

	public UserVo getUserInfo(long authNo) {
		// 회원정보 수정은 많이 안하기 때문에 session에 담을 필요없이 쿼리로 때리는 것이 낳다.
		UserVo authUser = userDao.getUserInfo(authNo);
		return authUser;
	}

	public boolean modify(UserVo vo) {
		boolean result = 1 == userDao.modify(vo);
		return result;
	}
	
}
