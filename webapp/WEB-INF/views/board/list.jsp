<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%
	/* 예외 count 증가 관련 부분들은  */
	pageContext.setAttribute("newline", "\n");
%>

<!DOCTYPE html>
<html>
<head>
<title>mysite</title>
<meta http-equiv="content-type" content="text/html; charset=utf-8">
<link href="${pageContext.servletContext.contextPath }/assets/css/board.css" rel="stylesheet" type="text/css">
</head>
<body>
	<div id="container">
		<c:import url="/WEB-INF/views/includes/header.jsp"/>
		<div id="content">
			<div id="board">
				<form id="search_form" action="${pageContext.servletContext.contextPath }/board" method="post">
					<input type="text" id="kwd" name="kwd" value="">
					<input type="submit" value="찾기">
				</form>
				<table class="tbl-ex">
					<tr>
						<th>번호</th>
						<th>제목</th>
						<th>글쓴이</th>
						<th>조회수</th>
						<th>작성일</th>
						<th>&nbsp;</th>
					</tr>
					<c:set var="count" value="${ fn:length(list) }"></c:set>
					<c:forEach items="${ list }" var="vo" varStatus="status">
						<tr>
							<td>${ count - status.index }</td>
							<c:choose>
								<c:when test="${ vo.depth == 0 }">
									<td><a href="${pageContext.servletContext.contextPath }/board?a=view&no=${ vo.no }">${ vo.title }</a></td>
								</c:when>
								<c:otherwise>
									<td style="padding-left: ${ 3 * vo.depth}px">
										<img src="${pageContext.servletContext.contextPath }/assets/images/reply.png" width="20px">
										<a href="${pageContext.servletContext.contextPath }/board?a=view&no=${ vo.no }">${ vo.title }</a>
									</td>
								</c:otherwise>
							</c:choose>
							<td>${ vo.userNo.name }</td>
							<td>${ vo.hit }</td>
							<td>${ vo.writeDate }</td>
							<td>
							<c:if test="${not empty authuser && authuser.no == vo.userNo.no }">
							<a href="${ pageContext.servletContext.contextPath }/board?a=deleteform&no=${ vo.no }" class="del">삭제</a>
							</c:if>
							</td>
						</tr>
					</c:forEach>
				</table>
				
				<!-- pager 추가 -->
				<div class="pager">
					<ul>
						<li>
							<c:choose>
								<c:when test="${ pageVo.pageNo == null || pageVo.pageNo < 1 }">
									<a href="${ pageContext.servletContext.contextPath }/board">◀</a>
								</c:when>
								<c:otherwise>
									<a href="${ pageContext.servletContext.contextPath }/board?page=${pageVo.prevPageNo}&kwd=${param.kwd}">◀</a>
								</c:otherwise>
							</c:choose>
						</li>
						<c:forEach var="i" begin="${ pageVo.startPageNo }" end="${ pageVo.endPageNo }" step="1">
							<c:choose>
								<c:when test="${ pageVo.pageNo == i }">
									<li class="selected"><a href="${ pageContext.servletContext.contextPath }/board?page=${pageVo.pageNo}&kwd=${param.kwd}">${pageVo.pageNo}</a></li>
								</c:when>
								<c:when test="${ pageVo.pageSize < i }">
									<li>${ i }</li>
								</c:when>
								<c:otherwise>
									<li><a href="${ pageContext.servletContext.contextPath }/board?page=${i}&kwd=${param.kwd}">${i}</a></li>
								</c:otherwise>
							</c:choose>
						</c:forEach>
						<c:choose>
							<c:when test="${ pageVo.pageNo < pageVo.pageSize }">
								<li><a href="${ pageContext.servletContext.contextPath }/board?page=${pageVo.nextPageNo}&kwd=${param.kwd}">▶</a></li>
							</c:when>
							<c:otherwise>
								<li><a href="${ pageContext.servletContext.contextPath }/board?page=${pageVo.pageNo}&kwd=${param.kwd}">▶</a></li>
							</c:otherwise>
						</c:choose>
					</ul>
				</div>					
				<!-- pager 추가 -->
				
				<c:if test="${not empty authuser }">
					<div class="bottom">
						<a href="${ pageContext.servletContext.contextPath }/board?a=writeform" id="new-book">글쓰기</a>
					</div>				
				</c:if>
			</div>
		</div>
		<c:import url="/WEB-INF/views/includes/navigation.jsp">
			<c:param name="menu" value="board"/>
		</c:import>
		<c:import url="/WEB-INF/views/includes/footer.jsp"/>
	</div>
</body>
</html>