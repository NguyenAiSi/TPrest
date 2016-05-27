package session;

import java.util.List;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import dal.*;

@Stateless
@LocalBean
public class CategorieFacade {
    @PersistenceContext(unitName ="GestUserEntityPU")
    private EntityManager em;
    protected EntityManager getEntityManager(){
        return this.em;
    }
    
    /**
     * Liste des catégories
     * @return : Collection de Categorie
     * @throws Exception 
     */
    public List<Categorie> lister() throws Exception {
        try {
            return (em.createNamedQuery("Categorie.findAll").getResultList());
        } catch (Exception e) {
            throw e;
        }
    }

    /**
     * Lecture dune catégorie sur son Id
     * @param id : id de la Categorie à lire
     * @return : Categorie
     * @throws Exception 
     */
    public Categorie lire(int id) throws Exception {
        try {
            return em.find(Categorie.class, id);
        } catch (Exception e) {
            throw e;
        }
    }     
}
