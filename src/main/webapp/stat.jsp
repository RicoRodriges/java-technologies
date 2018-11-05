<%@ page contentType="text/html;charset=UTF-8" %>

<!DOCTYPE html>
<html>
<head>
    <%@ include file="parts/head.jsp" %>
    <title>Statistics</title>
</head>
<body>

    <fmt:message key="catalog.tts" var="headerTitle" />
    <fmt:message key="catalog.description" var="headerDesc" />
    <%@ include file="parts/header.jsp" %>

    <h2><fmt:message key="profile.yourresults"/></h2>
    <p><fmt:message key="profile.text"/></p>
    <div id="app">
        <vue-good-table
                :columns="columns"
                :rows="rows" />
    </div>

    <script>
        new Vue({
            el: '#app',
            data: {
                columns: [
                    {
                        label: 'Test',
                        field: 'test',
                        filterOptions: {
                            enabled: true
                        }
                    },
                    {
                        label: 'User',
                        field: 'user',
                        filterOptions: {
                            enabled: true
                        }
                    },
                    {
                        label: 'University',
                        field: 'univer',
                        filterOptions: {
                            enabled: true
                        }
                    },
                    {
                        label: 'Faculty',
                        field: 'fac',
                        filterOptions: {
                            enabled: true
                        }
                    },
                    {
                        label: 'Department',
                        field: 'dep',
                        filterOptions: {
                            enabled: true
                        }
                    },
                    {
                        label: 'Group',
                        field: 'group',
                        filterOptions: {
                            enabled: true
                        }
                    },
                    {
                        label: 'Result',
                        field: 'result',
                        filterOptions: {
                            enabled: false
                        }
                    },
                    {
                        label: 'Percentage',
                        field: 'perc',
                        filterOptions: {
                            enabled: true
                        }
                    },
                    {
                        label: 'Date',
                        field: 'date',
                        filterOptions: {
                            enabled: true
                        }
                    },
                ],
                rows: ${requestScope.jsonData}
            }
        });
    </script>

    <%@ include file="parts/footer.jsp" %>

</body>
</html>