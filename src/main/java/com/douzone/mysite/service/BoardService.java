package com.douzone.mysite.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.douzone.mysite.repository.BoardDao;
import com.douzone.mysite.vo.BoardVo;
import com.douzone.mysite.vo.PageVo;
import com.douzone.mysite.vo.UserVo;

@Service
public class BoardService {

	@Autowired
	private BoardDao boardDao;

	public Map<String, Object> getList(Integer pageNo, String kwd) {
		List<BoardVo> list = null;
		int page = pageNo;
		
		PageVo pageVo = paging(pageNo);
		
		if(page>1) {
			page = (page-1)*10;
		} else {
			page = 0;
		}
		
		RowBounds rowBounds = new RowBounds(page, 10);
		
		if("".equals(kwd))	kwd = null;
		
		list = boardDao.getList(kwd, rowBounds);
		
		Map<String, Object> map = new HashMap<String, Object>();
		System.out.println(pageVo);
		if(kwd != null) {
			map.put("kwd", kwd);
			pageVo.setTotalCount(list.size());
		} else {
			map.put("kwd", "");
		}
		map.put("pageVo", pageVo);
		map.put("list", list);
		
		return map;
	}

	private PageVo paging(int pageNo) {
		PageVo pageVo = new PageVo();

		int totalCount = boardDao.totalPage();
		
		int pageSize = 0;

		if (totalCount % 10 > 0)
			pageSize = totalCount / 10 + 1;
		else {
			pageSize = totalCount / 10;
		}

		if (pageNo < 1) {
			pageNo = 1;
		}

		pageVo.setTotalCount(totalCount);
		pageVo.setPageSize(pageSize);
		pageVo.pageSetting(pageNo);

		return pageVo;
	}

	public String insert(HttpSession session, BoardVo vo) {

		if (((UserVo) session.getAttribute("authuser")) == null) {
			System.out.println("세션이 종료되었습니다.");
			System.out.println("다시 로그인해주세요.");
			return "redirect:/user/login";
		}

		long userNo = ((UserVo) session.getAttribute("authuser")).getNo();
		vo.setUserNo(userNo);
		System.out.println(vo);
		boolean result = 1 == boardDao.insert(vo);
		
		if (result == false) {
			System.out.println("실패");
			return null;
		}
		return "redirect:/board/list";
	}

	public BoardVo getView(String no) {
		BoardVo vo = boardDao.getView(Long.parseLong(no));
		System.out.println(vo);
		return vo;
	}

	public String delete(HttpSession session, long no, String password) {
		UserVo userVo = (UserVo) session.getAttribute("authuser");
		userVo.setPassword(password);

		boolean userCheck = boardDao.deleteCheck(userVo);

		if (!userCheck) {
			System.out.println("비밀번호가 틀리셨습니다.");
			return "/board/delete?no=" + no;
		} else {

			List<BoardVo> list = boardDao.getDeleteCheckList(no);
			int parentDepth = list.get(0).getDepth();
			int count = 0;

			for (BoardVo vo : list) {
				if (parentDepth >= vo.getDepth()) {
					count++;
					if (count == 2) {
						break;
					}
				}
				boardDao.delete(vo.getNo());
			}
			System.out.println("[" + no + "] 게시글 삭제 성공");

		}

		return "redirect:/board/list";
	}

	public String reply(HttpSession session, BoardVo vo) {
		if (((UserVo) session.getAttribute("authuser")) == null) {
			System.out.println("세션이 종료되었습니다.");
			System.out.println("다시 로그인해주세요.");
			return "redirect:/user/login";
		}

		UserVo userVo = (UserVo) session.getAttribute("authuser");
		vo.setUserNo(userVo.getNo());
		boolean result = 1 == boardDao.insertReply(vo);
		List<BoardVo> grouplist = boardDao.getReplyList(vo.getNo());
		int minorderNo = boardDao.getOrderNo(vo.getNo()) + 1;
		
		for (BoardVo replyvo : grouplist) {
			boardDao.updateOrder(replyvo);
		}
		
		boardDao.updateOrder(grouplist.get(grouplist.size() - 1), minorderNo);
		
		if (!result) {
			System.out.println("reply insert fail");
			return "redirect:/board/reply?no=" + vo.getNo() + "&g_no=" + vo.getGroupNo() + "&o_no=" + vo.getOrderNo()
					+ "&depth=" + vo.getDepth();
		}
		
		System.out.println("reply insert success");
		return "redirect:/board";

	}

	public String modify(HttpSession session, BoardVo vo) {
		System.out.println("no: " + vo.getNo());
		boolean modifySuccess = 1 == boardDao.modify(vo);
		int no = (int) vo.getNo();
		if (!modifySuccess) {
			System.out.println("회원 정보 수정 실패");
			return "/board/modify?no=" + no;
		}

		System.out.println("회원 정보 수정 성공");
		return "redirect:/board/view?no=" + no;
	}
}
