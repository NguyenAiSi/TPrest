package dal;

import dal.Categorie;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2016-05-03T11:15:46")
@StaticMetamodel(Utilisateur.class)
public class Utilisateur_ { 

    public static volatile SingularAttribute<Utilisateur, Categorie> categorie;
    public static volatile SingularAttribute<Utilisateur, String> adresse;
    public static volatile SingularAttribute<Utilisateur, Integer> idUtilisateur;
    public static volatile SingularAttribute<Utilisateur, String> login;
    public static volatile SingularAttribute<Utilisateur, String> pwd;
    public static volatile SingularAttribute<Utilisateur, String> nom;
    public static volatile SingularAttribute<Utilisateur, String> prenom;

}