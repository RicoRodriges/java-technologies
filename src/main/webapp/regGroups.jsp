<%@ page contentType="text/html;charset=UTF-8" %>

<!DOCTYPE html>
<html>
<head>
    <%@ include file="parts/head.jsp" %>
    <title>Create university</title>

    <script src="/js/onePageEditor.js"></script>
    <script src="/js/jquery.redirect.js"></script>

</head>
<body>

<fmt:message key="catalog.tts" var="headerTitle"/>
<fmt:message key="catalog.description" var="headerDesc"/>
<%@ include file="parts/header.jsp" %>

<main role="main" class="container">
    <form method="post">
        <c:if test="${requestScope.needUniver}">
            <input name="univer" placeholder="Enter univer name"><br>
        </c:if>
        <input name="fac" placeholder="Enter faculty name"><br>
        <input name="dep" placeholder="Enter department name"><br>
        <input name="group" placeholder="Enter group name"><br>
        <input type="submit" value="Create">
    </form>
    Hierarchy:
    <ul>
        <c:if test="${!requestScope.needUniver}">
            <c:forEach items="${requestScope.faculties}" var="q">
                <li>
                        ${q.name}
                        <ul style="padding-left: 20px;">
                            <c:forEach items="${q.getDepartments()}" var="qd">
                                <li>
                                        ${qd.name}
                                    <ul style="padding-left: 20px;">
                                        <c:forEach items="${qd.getGroups()}" var="qdg">
                                            <li>${qdg.name}</li>
                                        </c:forEach>
                                    </ul>
                                </li>
                            </c:forEach>
                        </ul>
                </li>
            </c:forEach>
        </c:if>
        <c:if test="${requestScope.needUniver}">
            <c:forEach items="${requestScope.universities}" var="un">
                <li>
                        ${un.name}
                    <ul style="padding-left: 20px;">
                        <c:forEach items="${un.getFaculties()}" var="q">
                            <li>
                                    ${q.name}
                                <ul style="padding-left: 20px;">
                                    <c:forEach items="${q.getDepartments()}" var="qd">
                                        <li>
                                                ${qd.name}
                                            <ul style="padding-left: 20px;">
                                                <c:forEach items="${qd.getGroups()}" var="qdg">
                                                    <li>${qdg.name}</li>
                                                </c:forEach>
                                            </ul>
                                        </li>
                                    </c:forEach>
                                </ul>
                            </li>
                        </c:forEach>
                    </ul>
                </li>
            </c:forEach>
        </c:if>
    </ul>
</main>

<%@ include file="parts/footer.jsp" %>

</body>
</html>
