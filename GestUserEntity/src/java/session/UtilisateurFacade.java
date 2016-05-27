package session;

import java.util.List;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import dal.*;
import javax.ejb.EJB;

@Stateless
@LocalBean
public class UtilisateurFacade {
    @PersistenceContext(unitName ="GestUserEntityPU")
    private EntityManager em;
    
    @EJB
    CategorieFacade categorieF;
    
    private Utilisateur user;
    public Utilisateur getUtilisateur(){
        return this.user;
    }
    public void setUtilisateur(Utilisateur user){
        this.user = user;
    }
    
    protected EntityManager getEntityManager(){
        return this.em;
    }
    
    /**
     * Liste des Utilisateurs
     * @return Collection d'Utilisateur
     * @throws Exception 
     */
    public List<Utilisateur> lister() throws Exception {
        try {
            return (em.createNamedQuery("Utilisateur.findAll").getResultList());
        } catch (Exception e) {
            throw e;
        }
    }

    /**
     * Lecture d'un utilisateur sur son id
     * @param id Identifiant de l'utilisateur à lire
     * @return Utilisateur
     * @throws Exception 
     */
    public Utilisateur lire(int id) throws Exception {
        try {
            return em.find(Utilisateur.class, id);
        } catch (Exception e) {
            throw e;
        }
    }

    /**
     * Lecture de l'utilisateur sur son login
     * Note : le login est unique (contrainte de bd)
     * @param login login de l'utilisateur à lire
     * @return un objet Utilisateur
     * @throws Exception 
     */
    public Utilisateur lireLogin(String login) throws Exception {
        try {
            ClientGestUser clientGestUser = new ClientGestUser();
            return clientGestUser.getConnexion(Utilisateur.class, login);
        } catch (Exception e) {
            throw e;
        }
    }  
    
    /**
     * Vérifie qu'un Utilisateur voulant s'authentifier
     * a bien fournit le bon login et le bon mot de passe
     * @param login Login de l'Utilisateur
     * @param pwd Mot de passe de l'Utilisateur
     * @return True : si tout OK, False sinon
     * @throws Exception 
     */
    public boolean connecter(String login, String pwd) throws Exception {
        boolean retour = false;
        try {
            this.user = lireLogin(login);
            if (pwd.equals(user.getPwd())) {
                retour = true;
            }
            return retour;
        } catch (Exception e) {
            throw e;
        }
    }    

    /**
     * Modification d'un utilisateur
     * @param user : Utilisateurs à modifier
     * @throws Exception 
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
     public void modifier(Utilisateur user) throws Exception {
        try {
            Utilisateur utilisateurE = lire(user.getIdUtilisateur());  
            utilisateurE.setCategorie(user.getCategorie());
            utilisateurE.setAdresse(user.getAdresse());
            utilisateurE.setNom(user.getNom());
            utilisateurE.setPrenom(user.getPrenom());            
            utilisateurE.setLogin(user.getLogin());
            utilisateurE.setPwd(user.getPwd());           
            em.merge(utilisateurE);
        } catch (Exception e) {
            throw e;
        }
    }
    
    /**
     * Ajout d'un utilisateur
     * @param Utilisateur : Utilisateur à ajouter
     * @throws Exception 
     */ 
    public void ajouter(Utilisateur user) throws Exception {           
        try {            
            em.persist(user);
        } catch (Exception e) {
            throw e;
        }
    }      
   
    /**
     * Suppression d'un utilisateur
     * @param id : id de l'utilisateur à supprimer
     * @throws Exception 
     */
    public void supprimer(int id) throws Exception {           
        try {      
            Utilisateur user = lire(id);             
            em.remove(user);
        } catch (Exception e) {
            throw e;
        }
    }      
}
