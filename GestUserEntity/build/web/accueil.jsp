
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="well">
    <div class="text-center titre">
        <h2>Accueil</h2>
    </div>
    <div class="container">
        <h3>
            <dl class="dl-horizontal">
                <dt>Bienvenue</dt>
                <dd>${userR.prenom} ${userR.nom}</dd>
                <dt>Catégorie</dt>
                <dd>${userR.categorie.libCategorie}</dd>                
            </dl>
        </h3>
    </div>    
</div>
</div>

