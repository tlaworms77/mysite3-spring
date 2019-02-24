package com.douzone.mysite.repository;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.douzone.mysite.vo.GuestbookVo;

@Repository
public class GuestbookDao {
	@Autowired
	private SqlSession sqlSession;
	
	public int delete( GuestbookVo vo ) {
		return sqlSession.delete("guestbook.delete", vo);
	}
	
	public long insert(GuestbookVo vo) {
		sqlSession.insert("guestbook.insert", vo);
		long no = vo.getNo();
		return no;
	}

	public List<GuestbookVo> getList() {
		List<GuestbookVo> list = sqlSession.selectList("guestbook.getList");
		return list;
	}


	public List<GuestbookVo> getList(int page) {
		return sqlSession.selectList("guestbook.getListPage");
	}

	/*public GuestbookVo get(long no) {
		GuestbookVo vo = new GuestbookVo();

		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;

		try {
			conn = dataSource.getConnection();

			// Statement 객체 생성
			stmt = conn.createStatement();

			// SQL문 실행
			String sql = "select no, name, message, date_format(reg_date, '%Y-%m-%d %h:%i:%s') from guestbook where no=" + no + " order by reg_date desc";
			rs = stmt.executeQuery( sql );

			// 결과 가져오기(사용하기)
			while (rs.next()) {
				Long no1 = rs.getLong(1);
				String name = rs.getString(2);
				String message = rs.getString(3);
				String regDate = rs.getString(4);

				vo.setNo(no1);
				vo.setName(name);
				vo.setMessage( message );
				vo.setRegDate( regDate );
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

		return vo;
	}*/	
}