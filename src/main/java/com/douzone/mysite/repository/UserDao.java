package com.douzone.mysite.repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.annotation.sql.DataSourceDefinition;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.douzone.mysite.exception.UserDaoException;
import com.douzone.mysite.vo.UserVo;

@Repository
public class UserDao {
	@Autowired
	private DataSource dataSource;
	
	/*
	 * private Connection getConnection() throws SQLException { Connection conn =
	 * null; try { // 1. 드라이버 로딩 Class.forName("com.mysql.jdbc.Driver");
	 * 
	 * // 2. 연결하기 String url =
	 * "jdbc:mysql://localhost/webdb?characterEncoding=utf8&serverTimezone=UTC";
	 * conn = DriverManager.getConnection(url, "webdb", "webdb"); } catch
	 * (ClassNotFoundException e) { System.out.println("드러이버 로딩 실패:" + e); }
	 * 
	 * return conn; }
	 */
	public int insert(UserVo vo) {
		int count = 0;
		Connection conn = null;
		PreparedStatement pstmt = null;

		try {
			conn = dataSource.getConnection();

			String sql = " inser" + "   into user" + " values ( null, ?, ?, ?, ?, now() )";
			pstmt = conn.prepareStatement(sql);

			pstmt.setString(1, vo.getName());
			pstmt.setString(2, vo.getEmail());
			pstmt.setString(3, vo.getPassword());
			pstmt.setString(4, vo.getGender());

			count = pstmt.executeUpdate();

		} catch (SQLException e) {
			throw new UserDaoException("회원 정보 저장 실패");
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
				throw new UserDaoException("회원 정보 저장 실패");
			}
		}

		return count;
	}

	public UserVo loginCheck(String email, String password) {

		UserVo userVo = null;

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			conn = dataSource.getConnection();

			String sql = "select no, name from user where email=? and password=?";
			pstmt = conn.prepareStatement(sql);

			pstmt.setString(1, email);
			pstmt.setString(2, password);

			rs = pstmt.executeQuery();
			if (rs.next()) {
				long no = rs.getLong(1);
				String name = rs.getString(2);

				userVo = new UserVo();
				userVo.setNo(no);
				userVo.setName(name);
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

		return userVo;

	}

	public UserVo getUserInfo(long no) {
		UserVo userVo = null;

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			conn = dataSource.getConnection();

			String sql = "select name, email, gender from user where no = ?";
			pstmt = conn.prepareStatement(sql);

			pstmt.setLong(1, no);

			rs = pstmt.executeQuery();
			if (rs.next()) {
				String name = rs.getString(1);
				String email = rs.getString(2);
				String gender = rs.getString(3);

				userVo = new UserVo();
				userVo.setName(name);
				userVo.setEmail(email);
				userVo.setGender(gender);
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

		return userVo;

	}

	public boolean modify(UserVo authUser) {

		boolean result = false;

		Connection conn = null;
		PreparedStatement pstmt = null;

		try {

			conn = dataSource.getConnection();

			String sql = "update " + "user " + "set " + "name = ?, " + "password = ?, " + "gender = ? " + "where "
					+ "no = ?";
			pstmt = conn.prepareStatement(sql);

			pstmt.setString(1, authUser.getName());
			pstmt.setString(2, authUser.getPassword());
			pstmt.setString(3, authUser.getGender());
			pstmt.setLong(4, authUser.getNo());
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

	public UserVo get(String email) {
		UserVo userVo = null;

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			conn = dataSource.getConnection();

			String sql = "select no, name from user where email=?";
			pstmt = conn.prepareStatement(sql);

			pstmt.setString(1, email);

			rs = pstmt.executeQuery();
			if (rs.next()) {
				long no = rs.getLong(1);
				String name = rs.getString(2);

				userVo = new UserVo();
				userVo.setNo(no);
				userVo.setName(name);
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

		return userVo;
	}

}