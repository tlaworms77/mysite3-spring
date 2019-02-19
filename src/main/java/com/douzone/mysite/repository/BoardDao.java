package com.douzone.mysite.repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.douzone.mysite.vo.BoardVo;
import com.douzone.mysite.vo.UserVo;

@Repository
public class BoardDao {
	
	private Connection getConnection() throws SQLException {
		Connection conn = null;
		try {
			// 1. 드라이버 로딩
			Class.forName("com.mysql.jdbc.Driver");

			// 2. 연결하기
			String url = "jdbc:mysql://localhost/webdb?characterEncoding=utf8&serverTimezone=UTC";
			conn = DriverManager.getConnection(url, "webdb", "webdb");
		} catch (ClassNotFoundException e) {
			System.out.println("드러이버 로딩 실패:" + e);
		}

		return conn;
	}
	
	public List<BoardVo> getList(int pageNo) {
		List<BoardVo> list = new ArrayList<BoardVo>();

		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;

		try {
			conn = getConnection();

			// Statement 객체 생성
			stmt = conn.createStatement();
			if(pageNo>1) {
				pageNo = (pageNo-1)*10;
			} else {
				pageNo = 0;
			}
			System.out.println("getList's pageNo = " + pageNo);
			// SQL문 실행
			String sql = "select "
								+ "a.*, "
								+ "b.name "
						+ "from "
								+ "board a, "
								+ "user b "
					   + "where "
					   			+ "a.user_no = b.no "
					+ "order by "
							    + "a.g_no desc, a.o_no asc limit " + pageNo + ", 10";
			
			rs = stmt.executeQuery( sql );

			// 결과 가져오기(사용하기)
			while (rs.next()) {
				Long no = rs.getLong(1);
				String title = rs.getString(2);
				String content = rs.getString(3);
				Date writeDate = rs.getDate(4);
				int hit = rs.getInt(5);
				int g_no = rs.getInt(6);
				int o_no = rs.getInt(7);
				int depth = rs.getInt(8);
				long userNo = rs.getLong(9);
				String name = rs.getString(10);
				BoardVo vo = new BoardVo();
				
				vo.setNo(no);
				vo.setTitle(title);
				UserVo uVo = new UserVo();
				uVo.setNo(userNo);
				uVo.setName(name);
				vo.setUserNo(uVo);
				vo.setHit(hit);
				vo.setWriteDate(writeDate);
				vo.setDepth(depth);
				
				list.add(vo);
			}
		} catch (SQLException e) {
			System.out.println("error :" + e);
		} finally {
			// 자원 정리
			try {
				if (rs != null) {
					rs.close();
				}
				if (stmt != null) {
					stmt.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return list;
	}
	
	public boolean insert(BoardVo vo) {
		boolean result = false; 
		Connection conn = null;
		PreparedStatement pstmt = null;

		try {
			conn = getConnection();

			String sql = "insert into board values(null, ?, ?, now(), 0, ifnull((select max(a.g_no)+1 from board a), 1), 1, 0, ?)";
			pstmt = conn.prepareStatement(sql);

			pstmt.setString(1, vo.getTitle());
			pstmt.setString(2, vo.getContents());
			UserVo userVo = new UserVo();
			pstmt.setLong(3, vo.getUserNo().getNo());

			int count = pstmt.executeUpdate();
			
			result = count == 1;

		} catch (SQLException e) {
			System.out.println("error :" + e);
		} finally {
			// 자원 정리
			try {
				if (pstmt != null) {
					pstmt.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return result;
	}

	public BoardVo getView(long writeNo) {
		BoardVo vo = new BoardVo();
		System.out.println("writeNo : " + writeNo);
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			conn = getConnection();

			// SQL문 실행
			String sql = "select * from board where no = ?";
			
			// Statement 객체 생성
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setLong(1, writeNo);
			
			rs = pstmt.executeQuery();

			// 결과 가져오기(사용하기)
			if (rs.next()) {
				Long no = rs.getLong(1);
				String title = rs.getString(2);
				String contents = rs.getString(3);
				int hit = rs.getInt(5);
				int g_no = rs.getInt(6);
				int o_no = rs.getInt(7);
				int depth = rs.getInt(8);
				
				vo.setNo(no);
				vo.setTitle(title);
				vo.setContents(contents);
				vo.setHit(hit);
				vo.setGroupNo(g_no);
				vo.setOrderNo(o_no);
				vo.setDepth(depth);
				
			}
		} catch (SQLException e) {
			System.out.println("error :" + e);
		} finally {
			// 자원 정리
			try {
				if (rs != null) {
					rs.close();
				}
				if (pstmt != null) {
					pstmt.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return vo;

	}

	public boolean modify(BoardVo vo) {

		boolean result = false;

		Connection conn = null;
		PreparedStatement pstmt = null;

		try {
			
			conn = getConnection();

			String sql = "update board set title=?, contents=? where no = ?";
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, vo.getTitle());
			pstmt.setString(2, vo.getContents());
			pstmt.setLong(3,  vo.getNo());
			int count = pstmt.executeUpdate();

			result = count == 1;
			
		} catch (SQLException e) {
			System.out.println("error :" + e);
		} finally {
			// 자원 정리
			try {
				if (pstmt != null) {
					pstmt.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return result;
	}

	public List<BoardVo> search(String kwd, int pageNo) {
		List<BoardVo> list = new ArrayList<BoardVo>();

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			conn = getConnection();
			
			pageNo = (pageNo-1)*10;
			
			// SQL문 실행
			String sql = "select a.no, a.title, b.name, a.hit, a.write_date from board a, user b where a.user_no = b.no and (a.title like '%"+kwd+"%' or a.contents like '%" + kwd + "%') order by a.g_no desc, a.o_no asc limit " + pageNo + ", 10";
			
			// Statement 객체 생성
			pstmt = conn.prepareStatement(sql);
	
			rs = pstmt.executeQuery( sql );

			// 결과 가져오기(사용하기)
			while (rs.next()) {
				Long no = rs.getLong(1);
				String title = rs.getString(2);
				String name = rs.getString(3);
				int hit = rs.getInt(4);
				Date writeDate = rs.getDate(5);
				
				BoardVo vo = new BoardVo();
				
				vo.setNo(no);
				vo.setTitle(title);
				UserVo uVo = new UserVo();
				uVo.setName(name);
				vo.setUserNo(uVo);
				vo.setHit(hit);
				vo.setWriteDate(writeDate);
				
				list.add(vo);
			}
		} catch (SQLException e) {
			System.out.println("error :" + e);
		} finally {
			// 자원 정리
			try {
				if (rs != null) {
					rs.close();
				}
				if (pstmt != null) {
					pstmt.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return list;
	}

	public boolean insertReply(BoardVo vo) {
		boolean result = false; 
		Connection conn = null;
		PreparedStatement pstmt = null;

		try {
			conn = getConnection();
			long no = vo.getNo();
			long userNo = vo.getUserNo().getNo();
			String sql = "insert "
						 + "into board "
					   + "values "
					   		  + "(null, ?, ?, now(), 0, "
					   		  + "(SELECT c.g_no from board c where c.no = " + no + "), "
					   	 	  + "(SELECT ifnull(MAX(b.o_no) + 1, 1) FROM board b where b.g_no = (SELECT d.g_no from board d where d.no = " + no + ")), "
					   	 	  + "(SELECT ifnull(MAX(a.depth)+10, 10) FROM board a where a.no = " + no + "), " + userNo + ")";
			
			pstmt = conn.prepareStatement(sql);

			pstmt.setString(1, vo.getTitle());
			pstmt.setString(2, vo.getContents());

			int count = pstmt.executeUpdate();
			
			result = count == 1;

		} catch (SQLException e) {
			System.out.println("error :" + e);
		} finally {
			// 자원 정리
			try {
				if (pstmt != null) {
					pstmt.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return result;
		
	}

	public boolean insertReply(long no, long authuserNo) {
		boolean result = false; 
		Connection conn = null;
		PreparedStatement pstmt = null;

		try {
			conn = getConnection();

			String sql = "insert into board values(null, ?, ?, now(), 0, (SELECT c.g_no from board c where c.no = ?), (SELECT ifnull(MAX(b.o_no) + 1, 1) FROM board b where b.no = ?), (SELECT ifnull(MAX(a.depth)+10, 10) FROM board a where a.no = ?), ?)";
			pstmt = conn.prepareStatement(sql);

			int count = pstmt.executeUpdate();
			
			result = count == 1;

		} catch (SQLException e) {
			System.out.println("error :" + e);
		} finally {
			// 자원 정리
			try {
				if (pstmt != null) {
					pstmt.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return result;
		
	}

	public List<BoardVo> getReplyList(long no) {
		List<BoardVo> list = new ArrayList<BoardVo>();

		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;

		try {
			conn = getConnection();

			// Statement 객체 생성
			stmt = conn.createStatement();

			// SQL문 실행
			String sql = "select "
								+ "a.*, "
								+ "b.name "
						+ "from "
								+ "board a, "
								+ "user b "
					   + "where "
					   			+ "a.user_no = b.no "
					   	 + "and "
					   			+ "a.g_no=(select c.g_no from board c where c.no=" + no + ") "
					   	+ "and "
					   			+ "a.o_no>(select d.o_no from board d where d.no=" + no + ") "
					+ "order by "
							    + "a.o_no asc";
			

			rs = stmt.executeQuery( sql );

			// 결과 가져오기(사용하기)
			while (rs.next()) {
				Long replyno = rs.getLong(1);
				String title = rs.getString(2);
				String content = rs.getString(3);
				Date writeDate = rs.getDate(4);
				int hit = rs.getInt(5);
				int g_no = rs.getInt(6);
				int o_no = rs.getInt(7);
				int depth = rs.getInt(8);
				long userNo = rs.getLong(9);
				String name = rs.getString(10);
				BoardVo vo = new BoardVo();
				
				vo.setNo(replyno);
				vo.setTitle(title);
				vo.setContents(content);
				vo.setWriteDate(writeDate);
				vo.setHit(hit);
				vo.setGroupNo(g_no);
				vo.setOrderNo(o_no);
				vo.setDepth(depth);
				
				UserVo uVo = new UserVo();
				uVo.setNo(userNo);
				uVo.setName(name);
				vo.setUserNo(uVo);
				
				list.add(vo);
			}
		} catch (SQLException e) {
			System.out.println("error :" + e);
		} finally {
			// 자원 정리
			try {
				if (rs != null) {
					rs.close();
				}
				if (stmt != null) {
					stmt.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return list;
	}

	public List<BoardVo> getList(BoardVo vo) {
		List<BoardVo> list = new ArrayList<BoardVo>();

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			conn = getConnection();

			// SQL문 실행
			String sql = "select "
								+ "no, "
								+ "order, "
								+ "depth"
						+ "from "
								+ "board "
					   + "where "
					   			+ "a.user_no = b.no "
					+ "order by "
							    + "a.g_no desc, a.o_no asc";

			// Statement 객체 생성
			pstmt = conn.prepareStatement(sql);
			
			
			
			rs = pstmt.executeQuery();

			// 결과 가져오기(사용하기)
			while (rs.next()) {
				Long no = rs.getLong(1);
				String title = rs.getString(2);
				String name = rs.getString(3);
				int hit = rs.getInt(4);
				Date writeDate = rs.getDate(5);
				int depth = rs.getInt(6);
				
				BoardVo vo2 = new BoardVo();
				
				vo.setNo(no);
				vo.setTitle(title);
				UserVo uVo = new UserVo();
				uVo.setName(name);
				vo.setUserNo(uVo);
				vo.setHit(hit);
				vo.setWriteDate(writeDate);
				vo.setDepth(depth);
				
				list.add(vo);
			}
		} catch (SQLException e) {
			System.out.println("error :" + e);
		} finally {
			// 자원 정리
			try {
				if (rs != null) {
					rs.close();
				}
				if (pstmt != null) {
					pstmt.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return list;
	}

	public void updateOrder(BoardVo replyvo) {
		Connection conn = null;
		PreparedStatement pstmt = null;

		try {
			
			conn = getConnection();
			long no = replyvo.getNo();
			String sql = "update board e, (select o_no from board where no="+no+")t1 set e.o_no=t1.o_no+1 where no=" + no;
			pstmt = conn.prepareStatement(sql);

			pstmt.executeUpdate();

		} catch (SQLException e) {
			System.out.println("error :" + e);
		} finally {
			// 자원 정리
			try {
				if (pstmt != null) {
					pstmt.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	////// parameter two
	public void updateOrder(BoardVo replyvo, int orderNo) {
		Connection conn = null;
		PreparedStatement pstmt = null;

		try {
			
			conn = getConnection();
			long no = replyvo.getNo();
			String sql = "update board set o_no="+orderNo+" where no=" + no;
			pstmt = conn.prepareStatement(sql);

			pstmt.executeUpdate();

		} catch (SQLException e) {
			System.out.println("error :" + e);
		} finally {
			// 자원 정리
			try {
				if (pstmt != null) {
					pstmt.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	public int getOrderNo(long no) {
		
		int orderNo = 0;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			conn = getConnection();

			// SQL문 실행
			String sql = "select o_no from board where no=" + no;

			// Statement 객체 생성
			pstmt = conn.prepareStatement(sql);
			
			rs = pstmt.executeQuery();

			// 결과 가져오기(사용하기)
			while (rs.next()) {
				orderNo = rs.getInt(1);
			}
		} catch (SQLException e) {
			System.out.println("error :" + e);
		} finally {
			// 자원 정리
			try {
				if (rs != null) {
					rs.close();
				}
				if (pstmt != null) {
					pstmt.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return orderNo;
	}

	public int totalPage() {
		int totalCount = 0;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			conn = getConnection();

			// SQL문 실행
			String sql = "select count(*) from board";
			
			// Statement 객체 생성
			pstmt = conn.prepareStatement(sql);
			
			//pstmt.setLong(1, writeNo);
			
			rs = pstmt.executeQuery();

			// 결과 가져오기(사용하기)
			if (rs.next()) {
				totalCount = rs.getInt(1);
			}
		} catch (SQLException e) {
			System.out.println("error :" + e);
		} finally {
			// 자원 정리
			try {
				if (rs != null) {
					rs.close();
				}
				if (pstmt != null) {
					pstmt.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return totalCount;
	}

	public boolean delete(long no) {
		
		boolean result = false; 
		Connection conn = null;
		PreparedStatement pstmt = null;

		try {
			conn = getConnection();

			String sql = "delete from board where no = ?";
			pstmt = conn.prepareStatement(sql);

			pstmt.setLong(1, no);

			int count = pstmt.executeUpdate();
			
			result = count == 1;

		} catch (SQLException e) {
			System.out.println("error :" + e);
		} finally {
			// 자원 정리
			try {
				if (pstmt != null) {
					pstmt.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return result;
		
	}

	public boolean deleteCheck(long no, UserVo userVo) {
		boolean result = false; 
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			conn = getConnection();

			String sql = "select * from board a, user b where a.user_no = b.no and a.no = ? and password = ?";
			pstmt = conn.prepareStatement(sql);

			pstmt.setLong(1, no);
			pstmt.setString(2, userVo.getPassword());

			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				result = true;
			}

		} catch (SQLException e) {
			System.out.println("error :" + e);
		} finally {
			// 자원 정리
			try {
				if (rs != null) {
					rs.close();
				}
				if (pstmt != null) {
					pstmt.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return result;
	}

	public List<BoardVo> getDeleteCheckList(long no) {
		List<BoardVo> list = new ArrayList<BoardVo>();

		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;

		try {
			conn = getConnection();

			// Statement 객체 생성
			stmt = conn.createStatement();

			// SQL문 실행
			String sql = "select * "
						 + "from board "
					    + "where g_no = (select b.g_no from board b where b.no = " + no + ") "
					      + "and o_no >= (select c.o_no from board c where c.no = " + no + ") "
					 + "order by o_no asc";
			
			rs = stmt.executeQuery( sql );

			// 결과 가져오기(사용하기)
			while (rs.next()) {
				Long replyno = rs.getLong(1);
				String title = rs.getString(2);
				String content = rs.getString(3);
				Date writeDate = rs.getDate(4);
				int hit = rs.getInt(5);
				int g_no = rs.getInt(6);
				int o_no = rs.getInt(7);
				int depth = rs.getInt(8);
				BoardVo vo = new BoardVo();
				
				vo.setNo(replyno);
				vo.setTitle(title);
				vo.setContents(content);
				vo.setWriteDate(writeDate);
				vo.setHit(hit);
				vo.setGroupNo(g_no);
				vo.setOrderNo(o_no);
				vo.setDepth(depth);
				
				list.add(vo);
			}
		} catch (SQLException e) {
			System.out.println("error :" + e);
		} finally {
			// 자원 정리
			try {
				if (rs != null) {
					rs.close();
				}
				if (stmt != null) {
					stmt.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return list;
	}

	public void upHit(long no) {
		Connection conn = null;
		PreparedStatement pstmt = null;

		try {
			
			conn = getConnection();
			String sql = "update board b, (select a.hit from board a where a.no = " + no + ") t1 set b.hit = t1.hit + 1 where b.no=" + no;
			pstmt = conn.prepareStatement(sql);

			pstmt.executeUpdate();

		} catch (SQLException e) {
			System.out.println("error :" + e);
		} finally {
			// 자원 정리
			try {
				if (pstmt != null) {
					pstmt.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

}
