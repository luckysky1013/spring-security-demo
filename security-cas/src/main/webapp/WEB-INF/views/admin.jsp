<%@ page language="java" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page session="true" %>
<html>
<head>
    <meta charset="utf-8">
</head>
<body><h1>标题: ${title}</h1>
<h1>消息 : ${message}</h1> <c:if test="${pageContext.request.userPrincipal.name != null}"><h2>
    欢迎: ${pageContext.request.userPrincipal.name} |


    <a href="<c:url value="/j_spring_security_logout" />"> Logout</a>
</h2></c:if></body>
</html>
