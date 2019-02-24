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
				<form id="search_form" action="${pageContext.servletContext.contextPath }/board" method="get">
					<input type="text" id="kwd" name="kwd" value="${map.kwd }">
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
					<c:set var="count" value="${ fn:length(map.list) }"></c:set>
					<c:forEach items="${ map.list }" var="vo" varStatus="status">
						<tr>
							<td>${ count - status.index }</td>
							<c:choose>
								<c:when test="${ vo.depth == 0 }">
									<td><a href="${pageContext.servletContext.contextPath }/board/view?no=${ vo.no }">${ vo.title }</a></td>
								</c:when>
								<c:otherwise>
									<td style="padding-left: ${ 3 * vo.depth}px">
										<img src="${pageContext.servletContext.contextPath }/assets/images/reply.png" width="20px">
										<a href="${pageContext.servletContext.contextPath }/board/view?no=${ vo.no }">${ vo.title }</a>
									</td>
								</c:otherwise>
							</c:choose>
							<td>${ vo.name }</td>
							<td>${ vo.hit }</td>
							<td>${ vo.writeDate }</td>
							<td>
							<c:if test="${not empty authuser && authuser.no == vo.userNo }">
							<a href="${ pageContext.servletContext.contextPath }/board/delete?no=${ vo.no }" class="del">삭제</a>
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
								<c:when test="${ map.pageVo.pageNo == null || map.pageVo.pageNo < 1 }">
									<a href="${ pageContext.servletContext.contextPath }/board">◀</a>
								</c:when>
								<c:otherwise>
									<a href="${ pageContext.servletContext.contextPath }/board?no=${map.pageVo.prevPageNo}&kwd=${map.kwd}">◀</a>
								</c:otherwise>
							</c:choose>
						</li>
						<c:forEach var="i" begin="${ map.pageVo.startPageNo }" end="${ map.pageVo.endPageNo }" step="1">
							<c:choose>
								<c:when test="${ map.pageVo.pageNo == i }">
									<li class="selected"><a href="${ pageContext.servletContext.contextPath }/board?no=${map.pageVo.pageNo}&kwd=${map.kwd}">${map.pageVo.pageNo}</a></li>
								</c:when>
								<c:when test="${ map.pageVo.pageSize < i }">
									<li>${ i }</li>
								</c:when>
								<c:otherwise>
									<li><a href="${ pageContext.servletContext.contextPath }/board?no=${i}&kwd=${map.kwd}">${i}</a></li>
								</c:otherwise>
							</c:choose>
						</c:forEach>
						<c:choose>
							<c:when test="${ map.pageVo.pageNo < map.pageVo.pageSize }">
								<li><a href="${ pageContext.servletContext.contextPath }/board?no=${map.pageVo.nextPageNo}&kwd=${map.kwd}">▶</a></li>
							</c:when>
							<c:otherwise>
								<li><a href="${ pageContext.servletContext.contextPath }/board?no=${map.pageVo.pageNo}&kwd=${map.kwd}">▶</a></li>
							</c:otherwise>
						</c:choose>
					</ul>
				</div>					
				<!-- pager 추가 -->
				
				<c:if test="${not empty authuser }">
					<div class="bottom">
						<a href="${ pageContext.servletContext.contextPath }/board/write" id="new-book">글쓰기</a>
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