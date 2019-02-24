package com.douzone.mysite.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.douzone.mysite.vo.BoardVo;
import com.douzone.mysite.vo.UserVo;

@Repository
public class BoardDao {
	
	@Autowired
	private SqlSession sqlSession;
	/*
	public List<BoardVo> getList(int pageNo) {
		return sqlSession.selectList("board.getList", pageNo);
	}*/
	
	public List<BoardVo> getList(String kwd, RowBounds rowBounds) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("kwd", kwd);
		return sqlSession.selectList("board.getList", map, rowBounds);
	}
	
	public int insert(BoardVo vo) {
		if(vo.getGroupNo() == 0) {
			System.out.println("groupNo : 0");
		} 
		return sqlSession.insert("board.insert", vo);
	}

	public BoardVo getView(long no) {
		return sqlSession.selectOne("board.getView", no);
	}

	public int modify(BoardVo vo) {
		return sqlSession.update("board.modify", vo);
	}

	public int insertReply(BoardVo vo) {
		return sqlSession.insert("board.insert", vo);	
	}

	public List<BoardVo> getReplyList(long no) {
		return sqlSession.selectList("board.getReplyList", no);
	}

	public void updateOrder(BoardVo vo) {
		sqlSession.update("board.updateOrder", vo);
	}

	////// parameter two
	public void updateOrder(BoardVo vo, int orderNo) {
		Map<String, Long> map = new HashMap<String, Long>();
		map.put("no", vo.getNo());
		map.put("orderNo", (long) orderNo);
		sqlSession.update("board.updateOrder", map);		
	}
	
	public int getOrderNo(long no) {
		return sqlSession.selectOne("board.getOrderNo", no);
	}

	public int totalPage() {
		return sqlSession.selectOne("board.getTotalPage");
	}

	public int delete(long no) {
		return sqlSession.update("board.deleteByNo", no);
	}

	public boolean deleteCheck(UserVo userVo) {
		return sqlSession.selectOne("board.getDeleteCheck", userVo);
	}

	public List<BoardVo> getDeleteCheckList(long no) {
		return sqlSession.selectList("board.getDeleteCheckList", no);
	}

	public void upHit(long no) {
		sqlSession.update("board.upHit", no);
	}

}
