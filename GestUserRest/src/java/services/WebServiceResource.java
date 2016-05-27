/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import dal.Utilisateur;
import java.math.BigDecimal;
import javax.ejb.EJB;
import javax.json.Json;
import javax.json.JsonObject;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.PathParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import outils.Utilitaire;
import session.CategorieFacade;
import session.UtilisateurFacade;

/**
 * REST Web Service
 *
 * @author Epulapp
 */
@Path("webservice")
public class WebServiceResource {

    @EJB
    UtilisateurFacade utilisateurF;
    
    @EJB
    CategorieFacade categorieF;
    /**
     * Creates a new instance of WebServiceResource
     */
    public WebServiceResource() {
    }

    @GET
    @Path("getConnexion/{login}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getConnexion(@PathParam("login") String login)
            throws Exception {
        Response response = null;
        try {
            Utilisateur user = utilisateurF.lireLogin(login);
            response = Response.status(Response.Status.OK).entity(user).build();
        } catch (Exception ex) {
            JsonObject retour = Json.createObjectBuilder().add("message", Utilitaire.getExceptionCause(ex)).build();
            response = Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(retour).build();
        }
        
        return response;
    }
}
