
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div  class="col-md-8 col-md-offset-1">
    <h1 align='center'>Liste des utilisateurs</h1>
    <table class="table table-bordered table-striped">
        <thead>
            <tr>
                <td>Nom</td>
                <td>Prénom</td>
                <td>Catégorie</td>
                <td>Modifier</td>
                <td>Supprimer</td>                
            </tr>  
        </thead>
        <tbody>
            <c:forEach var="user" items="${lUtilisateursR}">
                <tr>
                    <td>${user.nom}</td>
                    <td>${user.prenom}</td>
                    <td>${user.categorie.libCategorie}</td>                    
                    <td><a class="btn btn-primary" href="modifier.user?id=${user.idUtilisateur}">Modifier</a></td>      
                    <td><a class="btn btn-primary" onclick="javascript:if (confirm('Suppression confirmée ?')){ window.location='supprimer.user?id=${user.idUtilisateur}';}">Supprimer</a></td> 
                </tr>
            </c:forEach>
        </tbody>
    </table>
</div>
