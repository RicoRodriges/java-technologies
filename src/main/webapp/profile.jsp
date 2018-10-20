<%@ page contentType="text/html;charset=UTF-8" %>

<!DOCTYPE html>
<html>
<head>
    <%@ include file="parts/head.jsp" %>
    <title>Profile</title>
</head>
<body>

    <fmt:message key="catalog.tts" var="headerTitle" />
    <fmt:message key="catalog.description" var="headerDesc" />
    <%@ include file="parts/header.jsp" %>

    <h2><fmt:message key="profile.yourresults"/></h2>
    <p><fmt:message key="profile.text"/></p>
    <table class="table table-striped">
        <thead>
            <tr>
                <th><fmt:message key="profile.test"/></th>
                <th><fmt:message key="profile.result"/></th>
                <th><fmt:message key="profile.percentage"/></th>
                <th><fmt:message key="profile.date"/></th>
            </tr>
        </thead>
        <tbody>
            <c:forEach items="${requestScope.testResults}" var="tR">
                <tr>
                    <td>${tR.getTest().getName()}</td>
                    <td>${tR.getCorrectAnswers()}/${tR.getCountAnswers()}</td>
                    <td>${100 * tR.getCorrectAnswers() / tR.getCountAnswers()}</td>
                    <td>${f:dateFormat(tR.getDate())}</td>
                </tr>
            </c:forEach>
        </tbody>
    </table>

    <%@ include file="parts/footer.jsp" %>

</body>
</html>