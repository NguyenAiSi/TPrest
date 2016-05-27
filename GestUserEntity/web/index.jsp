<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link href="lib/css/gestusers.css" rel="stylesheet" type="text/css"/>
        <link href="lib/bootstrap/css/bootstrap.css" rel="stylesheet" type="text/css"/>
        <script src="lib/bootstrap/js/bootstrap.js" type="text/javascript"></script>
        <title>Gestion des utilisateurs</title>
    </head>
    <body class="body">
        <div class="container">
            <c:import url="/menu.jsp"/>
            <div>
                <c:if test="${pageR != null}">
                    <c:import url="${pageR}"/>
                </c:if>
                <c:if test="${erreurR != null}">
                    <c:import url="/erreur.jsp"/>
                </c:if>   
            </div>
        </div>
    </body>
</html>
