
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="form-group" >
    <div class="col-md-6  col-md-offset-3">
        <div class="alert-danger" role="alert">
            <c:if test="${erreurR != ''}">                    
                <span class="glyphicon glyphicon-exclamation-sign" aria-hidden="true"></span>
                <c:out value="${erreurR}"/>                            
            </c:if>
        </div>
    </div>
</div>
