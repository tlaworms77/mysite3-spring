package com.douzone.mysite.repository;

import java.util.HashMap;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.douzone.mysite.vo.UserVo;

@Repository
public class UserDao {
	@Autowired
	private SqlSession sqlSession;
	
	public int insert(UserVo vo) {
		int count = sqlSession.insert("user.insert", vo);
		return count;
	}

	public UserVo loginCheck(String email, String password) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("email", email);
		map.put("password", password);
		
		return sqlSession.selectOne("user.getByEmailAndPassword", map);
	}

	public UserVo getUserInfo(long no) {
		return sqlSession.selectOne("user.getUserInfo", no);
	}

	public int modify(UserVo vo) {
		return sqlSession.update("user.modify", vo);
	}

	public UserVo get(String email) {
		return sqlSession.selectOne("user.getByEmail", email);	
	}

}