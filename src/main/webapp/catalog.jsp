<%@ page contentType="text/html;charset=UTF-8" %>

<!DOCTYPE html>
<html>
<head>
    <%@ include file="parts/head.jsp" %>

    <c:if test="${not empty param.theme}">
        <c:set var="theme" scope="session" value="${param.theme}"/>
    </c:if>
    <c:if test="${empty sessionScope.theme}">
        <c:set var="theme" scope="session" value="All"/>
    </c:if>
    <c:set var="tests" scope="session" value="${f:getAllTestsByTheme(sessionScope.theme)}"/>

    <title><fmt:message key="catalog.catalog"/></title>
</head>
<body>

    <fmt:message key="catalog.tts" var="headerTitle" />
    <fmt:message key="catalog.description" var="headerDesc" />
    <%@ include file="parts/header.jsp" %>

    <select id="theme" onchange="changeTheme()" class="custom-select custom-select-sm col-md-1">
            <option value="All" ${sessionScope.theme == 'All' ? 'selected' : ''}><fmt:message key="catalog.all"/></option>
            <option value="English" ${sessionScope.theme == 'English' ? 'selected' : ''}><fmt:message
                    key="catalog.english"/></option>
            <option value="Russian" ${sessionScope.theme == 'Russian' ? 'selected' : ''}><fmt:message
                    key="catalog.russian"/></option>
            <option value="Math" ${sessionScope.theme == 'Math' ? 'selected' : ''}><fmt:message
                    key="catalog.math"/></option>
            <option value="Physics" ${sessionScope.theme == 'Physics' ? 'selected' : ''}><fmt:message
                    key="catalog.physics"/></option>
    </select>


    <c:choose>
        <c:when test="${(sessionScope.tests ne null) && (not empty sessionScope.tests)}">
            <div class="row">
                <c:forEach items="${sessionScope.tests}" var="test">

                    <c:choose>
                        <c:when test="${test.getType().getName() eq 'Math'}">
                             <c:set var="imgTest" value="/images/math.png"/>
                        </c:when>
                        <c:when test="${test.getType().getName() eq 'Physics'}">
                            <c:set var="imgTest" value="/images/physics.png"/>
                        </c:when>
                        <c:when test="${test.getType().getName() eq 'Russian'}">
                            <c:set var="imgTest" value="/images/russian.png"/>
                        </c:when>
                        <c:when test="${test.getType().getName() eq 'English'}">
                            <c:set var="imgTest" value="/images/english.png"/>
                        </c:when>
                    </c:choose>

                    <div class="col-md-4">
                        <div class="card mb-4 box-shadow">
                            <c:if test="${sessionScope.user.getIsTutor()}">
                                <div class="d-flex justify-content-end align-items-baseline test-del">
                                    <a href="<c:url value="/delete?id=${test.getId()}"/>" class="btn btn-danger btn-xs">X</a>
                                </div>
                            </c:if>
                            <img class="card-img-top test-img" src="${imgTest}" alt="test icon">
                            <div class="card-body">
                                <h5 class="card-title">${test.getName()}</h5>
                                <p class="card-text">
                                    <c:if test="${test.getType().getName() eq 'Math'}">
                                        <fmt:message key="catalog.descriptionmath"/>
                                    </c:if>
                                    <c:if test="${test.getType().getName() eq 'Physics'}">
                                        <fmt:message key="catalog.descriptionphys"/>
                                    </c:if>
                                    <c:if test="${test.getType().getName() eq 'Russian'}">
                                        <fmt:message key="catalog.descriptionrus"/>
                                    </c:if>
                                    <c:if test="${test.getType().getName() eq 'English'}">
                                        <fmt:message key="catalog.descriptioneng"/>
                                    </c:if>
                                </p>
                                <div class="d-flex justify-content-between align-items-center">
                                    <a href="<c:url value="/test?id=${test.getId()}"/>" class="btn btn-primary">
                                    <fmt:message key="catalog.start"/>
                                    </a>
                                    <c:if test="${sessionScope.user.getIsTutor()}">
                                      <a href="<c:url value="/editor?id=${test.getId()}"/>" class="btn btn-primary btn-xs">
                                      <fmt:message key="catalog.edit"/>
                                      </a>
                                    </c:if>
                                    <small class="text-muted">
                                        <c:out value="${test.getCreationDate().toString()}"/>
                                    </small>
                                </div>
                            </div>
                        </div>
                    </div>

                </c:forEach>
            </div>
        </c:when>
        <c:otherwise>

            <div class="row">
                <div class="<col-md-3"></div>
                <div class="<col-md-6">
                    <h1 class="mt-5"><fmt:message key="catalog.hasnotests"/></h1>
                    <p class="lead"><fmt:message key="catalog.hasnotestsdesc"/></p>
                </div>
                <div class="<col-md-3"></div>
            </div>

        </c:otherwise>
    </c:choose>

    <%@ include file="parts/footer.jsp" %>

</body>
</html>