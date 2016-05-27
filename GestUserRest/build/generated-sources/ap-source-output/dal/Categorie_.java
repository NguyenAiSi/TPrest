package dal;

import dal.Utilisateur;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2016-05-03T11:15:46")
@StaticMetamodel(Categorie.class)
public class Categorie_ { 

    public static volatile SingularAttribute<Categorie, String> libCategorie;
    public static volatile SingularAttribute<Categorie, Integer> idCategorie;
    public static volatile ListAttribute<Categorie, Utilisateur> utilisateurList;

}