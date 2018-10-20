<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<div class="site-content bg-light">
    <header class="bg-dark">
            <%@ include file="lang.jsp" %>
            <div class="container">
                <div class="row">

                    <div class="col-md-9 py-4">
                        <h4 class="text-white">${headerTitle}</h4>
                        <p class="text-muted">${headerDesc}</p>
                    </div>

                    <c:choose>
                    <c:when test="${sessionScope.user.getIsTutor()}">

                        <div class="btn-group">
                            <div><a href="/editor" class="btn btn-primary">
                            <fmt:message key="catalog.createtest"/>
                            </a></div>
                            <div><a href="/userList" class="btn btn-primary">
                            <fmt:message key="catalog.userspage"/>
                            </a></div>
                            <div><a href="/logout" class="btn btn-primary">
                            <fmt:message key="catalog.logout"/>
                            </a></div>
                        </div>
                    </c:when>
                    <c:otherwise>
                        <div class="btn-group">
                           <div><a href="/profile" class="btn btn-primary">
                           <fmt:message key="catalog.resultpage"/>
                           </a></div>
                           <div><a href="/logout" class="btn btn-primary">
                           <fmt:message key="catalog.logout"/>
                           </a></div>
                        </div>
                     </c:otherwise>
                    </c:choose>

                </div>
            </div>
    </header>

    <div class="container">