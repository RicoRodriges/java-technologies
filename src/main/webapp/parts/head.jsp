<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<%@ taglib prefix="f" uri="/WEB-INF/tld/tags" %>

<c:if test="${not empty param.language}">
    <c:set var="locale" scope="session" value="${param.language}"/>
</c:if>
<c:if test="${empty sessionScope.locale}">
    <c:set var="locale" scope="session" value="en"/>
    <fmt:setLocale value="en"/>
</c:if>
<c:if test="${sessionScope.locale eq 'en'}">
    <fmt:setLocale value="en"/>
</c:if>
<c:if test="${sessionScope.locale eq 'ru'}">
    <fmt:setLocale value="ru"/>
</c:if>

<fmt:setBundle basename="internationalization"/>

<meta http-equiv="Content-Type" content="text/html; charset=utf-8">

<link rel="stylesheet" href="/css/style.css">
<script src="/js/changeLanguage.js"></script>

<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css">
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js"></script>