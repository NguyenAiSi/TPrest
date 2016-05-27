package controleurs;

import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.ejb.EJB;
import dal.*;
import outils.Utilitaire;
import session.*;


public class UserServlet extends HttpServlet {
    @EJB
    private UtilisateurFacade utilisateurF;
    @EJB
    private CategorieFacade categorieF;  
    
    private String erreur;
    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String demande;
        // Si aucune demande n'est satisfaite, c'est la vue home.jsp
        // qui sera affichée
        String vueReponse = "/home.jsp";
        erreur = "";
        try {
            demande = getDemande(request);
            if (demande.equalsIgnoreCase("login.user")) {
                vueReponse = login(request);   
            }else if (demande.equalsIgnoreCase("connecter.user")) {
                vueReponse = connecter(request);
            }else if (demande.equalsIgnoreCase("modifier.user")) {
                vueReponse = modifierUtilisateur(request);
            } else if (demande.equalsIgnoreCase("accueil.user")) {
                vueReponse = "/accueil.jsp";
            } else if (demande.equalsIgnoreCase("enregistrer.user")) {
                vueReponse = enregistrerUtilisateur(request);
            } else if (demande.equalsIgnoreCase("creer.user")) {
                vueReponse = creerUtilisateur(request);
            } else if (demande.equalsIgnoreCase("deconnecter.user")) {
                vueReponse = deconnecter(request);
            }else if (demande.equalsIgnoreCase("lister.user")) {
                vueReponse = listerUtilisateurs(request);
            } else if (demande.equalsIgnoreCase("supprimer.user")) {
                vueReponse = supprimerUtilisateur(request);
            }

        } catch (Exception e) {
            erreur = Utilitaire.getExceptionCause(e);
        } finally {
            request.setAttribute("erreurR", erreur);
            request.setAttribute("pageR", vueReponse); 
            // Par défaut la page à afficher est index.jsp
            // sauf s'il faut rediriger vers une fonction
            RequestDispatcher dsp = request.getRequestDispatcher("/index.jsp");
            if (vueReponse.contains(".user"))
                dsp= request.getRequestDispatcher(vueReponse);
            dsp.forward(request, response);            
        }

    }

    /**
     * Définit la vue à exposer
     * @param request
     * @return
     * @throws Exception 
     */
    private String login(HttpServletRequest request) throws Exception {
        String vueReponse;
        try {
            vueReponse = "/login.jsp";
            return (vueReponse);
        } catch (Exception e) {
            throw e;
        }
    }

    /**
     * Récupère la liste des utilisateurs, la place que le scope request
     * et définit la vue à exposer liste.jsp
     * @param request
     * @return
     * @throws Exception 
     */
    private String listerUtilisateurs(HttpServletRequest request) throws Exception {
        String vueReponse;
        try {
            vueReponse = "";
            request.setAttribute("lUtilisateursR", utilisateurF.lister());
            vueReponse = "/liste.jsp";
            return (vueReponse);
        } catch (Exception e) {
            throw e;
        }
    }
    
    /**
     * Déconnecter en supprimant userId de la session
     * @param request
     * @return
     * @throws Exception 
     */
    private String deconnecter(HttpServletRequest request) throws Exception {
        String vueReponse;
        try {
            HttpSession session = request.getSession(true);
            session.setAttribute("userId", null);
            vueReponse = "/home.jsp";
            return (vueReponse);
        } catch (Exception e) {
            throw e;
        }
    }
    
    /**
     * Enregitrer l'ajout ou la modification d'un utilisateur
     * @param request
     * @return
     * @throws Exception 
     */
    private String enregistrerUtilisateur(HttpServletRequest request) throws Exception {
               
        String vueReponse;
        int id_utilisateur = 0;
        try {
            String titre = "Créer un profil";
            String id = request.getParameter("id");
            Utilisateur user = new Utilisateur();   
            // Si on est en Modification ou Ajout            
            if ( !id.equals("")){
                id_utilisateur = Integer.parseInt(request.getParameter("id"));
                // Affecter l'Id de l'utilisateur à Modifier
                user.setIdUtilisateur(id_utilisateur);                
                titre = "Modifier un profil";
            }  
            // Peupler les propriétés de Utilisateur            
            user.setLogin(request.getParameter("txtLogin"));
            user.setPwd(request.getParameter("txtPwd"));
            user.setNom(request.getParameter("txtNom"));
            user.setPrenom(request.getParameter("txtPrenom"));
            user.setAdresse(request.getParameter("txtAdresse"));
            // Instancier l'objet Categorie de la classe Utilisateur
            user.setCategorie(categorieF.lire(Integer.parseInt(request.getParameter("lstCategories"))));
            // Il faut conserver les valeurs pour pouvoir
            // les réafficher en cas d'erreur
            request.setAttribute("titre",titre);
            request.setAttribute("userR", user);            
            // Si on a un id c'est qu'il s'agit d'une modification
            if (id_utilisateur > 0) {
                utilisateurF.modifier(user);
            } else {
                utilisateurF.ajouter(user);
            }
            vueReponse = "/home.jsp";            
            HttpSession session = request.getSession(true);
            String userId = session.getAttribute("userId").toString();
            // Après une modification l'administrateur accède
            // à la liste des utilisateur alors qu'un utilisateur
            // lambda retourne à la page home
            if(userId.equals("1"))
              vueReponse = "lister.user";               
            return (vueReponse);
        } catch (Exception e) {
            // On reste sur la même page qui est réaffichée
            request.setAttribute("lstCategoriesR", categorieF.lister());            
            erreur = Utilitaire.getExceptionCause(e);
            return "/profil.jsp";
        }
    }    
    
    /**
     * Suppression d'un utilisateur
     * @param request
     * @return
     * @throws Exception 
     */
    private String supprimerUtilisateur(HttpServletRequest request) throws Exception {
               
        String vueReponse;
        int id_utilisateur = 0;
        try {
            String id = request.getParameter("id");
            if ( !id.equals(""))
                id_utilisateur = Integer.parseInt(request.getParameter("id"));
            if (id_utilisateur > 0) {
                utilisateurF.supprimer(id_utilisateur);
            } 
            vueReponse = "/home.jsp";            
            HttpSession session = request.getSession(true);
            String userId = session.getAttribute("userId").toString();
            // Après une suppression l'administrateur accède
            // à la liste des utilisateur alors qu'un utilisateur
            // lambda retourne à la page home
            if(userId.equals("1"))
              vueReponse = "lister.user";               
            return (vueReponse);
        } catch (Exception e) {
            throw e;
        }
    } 
    
    /**
     * Lit la liste des catégories, la met dans le scope request
     * Place le formulaire profil.jsp en mode création
     * @param request
     * @return
     * @throws Exception 
     */
    private String creerUtilisateur(HttpServletRequest request) throws Exception {
        try {;
            request.setAttribute("lstCategoriesR", categorieF.lister());
            request.setAttribute("titre", "Créer un profil");
            return ("/profil.jsp");
        } catch (Exception e) {
            throw e;
        }
    }    

    /**
     * Lit l'utilisateur, le met dans le scope request
     * Place le formulaire profil.jsp en dode modification
     * @param request
     * @return
     * @throws Exception 
     */
    private String modifierUtilisateur(HttpServletRequest request) throws Exception {
        try {
            String vueReponse = "/login.jsp";
            int id_utilisateur = Integer.parseInt(request.getParameter("id"));
            if (id_utilisateur > 0) {
                Utilisateur user = utilisateurF.lire(id_utilisateur);
                request.setAttribute("userR", user);
                request.setAttribute("lstCategoriesR", categorieF.lister());
                request.setAttribute("titre", "Modifier un profil");
                vueReponse = "/profil.jsp";
            } else {
                erreur = "Utilisateur inconnu !";
            }
            return (vueReponse);
        } catch (Exception e) {
            throw e;
        }
    }
    
   /**
     * Vérifie que l'utilisateur a saisi le bon login et mot de passe
     * @param request
     * @return String page de redirection
     * @throws Exception
     */
    private String connecter(HttpServletRequest request) throws Exception {
        String login, pwd;
        String vueReponse = "/login.jsp";
        erreur = "";
        try {
            login = request.getParameter("txtLogin");
            pwd = request.getParameter("txtPwd");
            if (utilisateurF.connecter(login, pwd)) {
                Utilisateur user = utilisateurF.getUtilisateur();
                vueReponse = "/accueil.jsp";
                HttpSession session = request.getSession(true);
                session.setAttribute("userId", user.getIdUtilisateur());                
                request.setAttribute("userR", user);                
                request.setAttribute("categorieR", user.getCategorie());
            } else {
                erreur = "Login ou mot de passe inconnus !";
            }
        } catch (Exception e) {
            erreur = e.getMessage();
        }
        finally{
            return (vueReponse);            
        }
    }    

    private String getDemande(HttpServletRequest request) {
        String demande = "";
        demande = request.getRequestURI();
        demande = demande.substring(demande.lastIndexOf("/") + 1);
        return demande;
    }
    
    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
