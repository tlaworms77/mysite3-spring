package com.douzone.mysite.exception;

public class UserDaoException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;

	public UserDaoException(String msg) {
		super(msg);
	}
	
	public UserDaoException() {
		System.out.println("UserDaoException");
	}
	
}
