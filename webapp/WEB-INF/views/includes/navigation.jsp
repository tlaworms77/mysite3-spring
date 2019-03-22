<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
		<div id="navigation">
			<ul>
				<c:choose>
					<c:when test='${ param.menu == "main" }'>
						<div id="navigation">
							<h2>카테고리</h2>
							<ul>
								<li><a href="">닥치고 스프링</a></li>
								<li><a href="">스프링 스터디</a></li>
								<li><a href="">스프링 프로젝트</a></li>
								<li><a href="">기타</a></li>
							</ul>
						</div>
					</c:when>
					<c:when test='${ param.menu == "guestbook" }'>
						<div id="navigation">
							<h2>카테고리</h2>
							<ul>
								<li><a href="">닥치고 스프링</a></li>
								<li><a href="">스프링 스터디</a></li>
								<li><a href="">스프링 프로젝트</a></li>
								<li><a href="">기타</a></li>
							</ul>
						</div>
					</c:when>
					<%-- <c:when test='${ param.menu == "guestbook-ajax"}'>
						<li><a href="${ pageContext.servletContext.contextPath }">심재근</a></li>
						<li><a href="${ pageContext.servletContext.contextPath }/guestbook">방명록</a></li>
						<li class="selected"><a href="${ pageContext.servletContext.contextPath }/guestbook?a=ajax">방명록(Ajax)</a></li>
						<li><a href="${ pageContext.servletContext.contextPath }/board">게시판</a></li>
					</c:when>
					<c:when test='${ param.menu == "board"}'>
						<li><a href="${ pageContext.servletContext.contextPath }">심재근</a></li>
						<li><a href="${ pageContext.servletContext.contextPath }/guestbook">방명록</a></li>
						<li><a href="${ pageContext.servletContext.contextPath }/guestbook?a=ajax">방명록(Ajax)</a></li>
						<li class="selected"><a href="${ pageContext.servletContext.contextPath }/board">게시판</a></li>
					</c:when>
					<c:otherwise>
						<li><a href="${ pageContext.servletContext.contextPath }">심재근</a></li>
						<li><a href="${ pageContext.servletContext.contextPath }/guestbook">방명록</a></li>
						<li><a href="${ pageContext.servletContext.contextPath }/guestbook?a=ajax">방명록(Ajax)</a></li>
						<li><a href="${ pageContext.servletContext.contextPath }/board">게시판</a></li>
					</c:otherwise> --%>
				</c:choose>
			</ul>
		</div>