<%@ page contentType="text/html;charset=UTF-8" %>

<!DOCTYPE html>
<html>
<head>
    <%@ include file="parts/head.jsp" %>
    <title><fmt:message key="result.result"/></title>
</head>
<body>

    <fmt:message key="result.testresults" var="headerTitle" />
    <c:set value="${requestScope.test.getName()}" var="headerDesc" />
    <%@ include file="parts/header.jsp" %>

    <c:if test="${requestScope.score > 60}">
        <div class="row result-success"><fmt:message key="result.success"/>.</div>
    </c:if>
    <c:if test="${requestScope.score <= 60}">
        <div class="row result-fail"><fmt:message key="result.fail"/>.</div>
    </c:if>
    <div class="row">
        <fmt:message key="result.res1"/> ${requestScope.result.getCorrectAnswers()}
        <fmt:message key="result.res2"/> ${requestScope.result.getCountAnswers()}
        <fmt:message key="result.res3"/>. <fmt:message key="result.yourresult"/> ${requestScope.score}%
    </div>
    <div class="d-flex justify-content-md-center" style="padding-top:100px;">
        <a href="<c:url value="/catalog"/>" class="btn btn-primary"><fmt:message key="result.return"/></a>
    </div>

    <%@ include file="parts/footer.jsp" %>

</body>
</html>