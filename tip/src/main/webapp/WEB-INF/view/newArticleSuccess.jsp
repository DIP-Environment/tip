<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>Insert title here</title>
	</head>
	<body>
		게시글을 등록했습니다.
		<br>
		${ctxPath=pageContext.request.contextPath; ""}
		<a href="${ctxPath }/articleList.do">[게시글 목록 보기]</a>
		<a href="${ctxPath }/articleRead.do?no=${newArticleNo}">[게시글 내용 보기]</a>
	</body>
</html>