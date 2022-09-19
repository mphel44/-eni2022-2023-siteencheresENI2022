package org.encheres.dal.jdbc;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.encheres.bo.ArticleVendu;
import org.encheres.bo.Categorie;
import org.encheres.bo.Enchere;
import org.encheres.bo.Retrait;
import org.encheres.bo.Utilisateur;
import org.encheres.dal.ArticleVenduDAO;
import org.encheres.dal.DALException;

public class ArticleVenduDAOJdbcImpl implements ArticleVenduDAO {
	
	//private static final String SELECT_ALL = "SELECT * FROM ARTICLES_VENDUS;";
	private static final String INSERT_ARTICLE = "INSERT INTO ARTICLES_VENDUS (nom_article, description, date_debut_enchere, date_fin_enchere, prix_initial, prix_vente, no_utilisateur, no_categorie, etat_vente, image) VALUES (?,?,?,?,?,?,?,?,?,?)";
    private static final String INSERT_RETRAITS = "INSERT INTO RETRAITS (no_article, rue, code_postal, ville) VALUES (?,?,?,?)";
	private static final String SELECT_ALL_EC = "select u.no_utilisateur, u.pseudo, u.nom, u.prenom, u.email, u.telephone, u.rue, code_postal, u.ville, u.mot_de_passe,u.credit,u.administrateur, av.no_article,av.nom_article,av.no_categorie, av.description, av.date_debut_enchere,av.date_fin_enchere,av.prix_initial,av.prix_vente,\r\n"
			+ "av.etat_vente, e.no_utilisateur as encherisseur, e.date_enchere, e.montant_enchere, av.no_categorie,c.libelle from ARTICLES_VENDUS av inner join UTILISATEURS u on av.no_utilisateur = u.no_utilisateur\r\n"
			+ "left join ENCHERES e on av.no_article = e.no_article\r\n"
			+ "left join CATEGORIES c on av.no_categorie = c.no_categorie  where etat_vente = 'EC' group by u.no_utilisateur, u.pseudo, u.nom, u.prenom, u.email, u.telephone, u.rue, code_postal, u.ville, u.mot_de_passe,u.credit,u.administrateur, av.no_article,av.nom_article,av.no_categorie, av.description, av.date_debut_enchere,av.date_fin_enchere,av.prix_initial,av.prix_vente,\r\n"
			+ "av.etat_vente, e.no_utilisateur, e.date_enchere, e.montant_enchere,  av.no_categorie,c.libelle ;"	;	

	private static final String SELECT_BY_CAT_NOM_ART = "select u.no_utilisateur, u.pseudo, u.nom, u.prenom, u.email, u.telephone, u.rue, code_postal, u.ville, u.mot_de_passe,u.credit,u.administrateur, av.no_article,av.nom_article,av.no_categorie, av.description, av.date_debut_enchere,av.date_fin_enchere,av.prix_initial,av.prix_vente,\r\n"
			+ "			 av.etat_vente, e.no_utilisateur as encherisseur, e.date_enchere, e.montant_enchere, av.no_categorie,c.libelle from ARTICLES_VENDUS av  "
			+ " inner join UTILISATEURS u on av.no_utilisateur = u.no_utilisateur\r\n"
			+ "	left join ENCHERES e on av.no_article = e.no_article\r\n"
			+ "	left join CATEGORIES c on av.no_categorie = c.no_categorie  where etat_vente = 'EC' AND c.no_categorie = ? AND av.nom_article  LIKE ? ";
	
	public ArticleVendu insert(ArticleVendu article, Retrait retrait) throws DALException {
		try (Connection cnx = ConnectionProvider.getConnection();
				PreparedStatement stmt = cnx.prepareStatement(INSERT_ARTICLE, PreparedStatement.RETURN_GENERATED_KEYS);
				PreparedStatement restmt = cnx.prepareStatement(INSERT_RETRAITS);){
			try {
				cnx.setAutoCommit(false);
				stmt.setString (1,article.getNom_article());
				stmt.setString(2, article.getDescription());
				stmt.setTimestamp(3, Timestamp.valueOf(article.getDate_debut_enchere()));
				stmt.setTimestamp(4, Timestamp.valueOf(article.getDate_fin_enchere()));
				stmt.setInt(5, article.getPrix_initial());
				stmt.setInt(6, article.getPrix_vente());
				stmt.setInt(7, article.getNo_utilisateur());
				stmt.setInt(8, article.getNo_categorie());
				stmt.setString(9, article.getEtat_vente());
				stmt.setString(10, article.getImage());
				
				stmt.executeUpdate();
				
				ResultSet rs = stmt.getGeneratedKeys();
				if (rs.next()) {
					article.setNo_article(rs.getInt(1));
				}
				
				restmt.setInt(1,article.getNo_article());
				restmt.setString (2,retrait.getRue());
				restmt.setString(3, retrait.getCode_postal());
				restmt.setString(4, retrait.getVille());
				
				restmt.executeUpdate();
			
				cnx.commit();
			} catch (SQLException e) {
				cnx.rollback();
				throw new DALException("Erreur système lors de la tentative de création de la vente. Contacter l'administrateur technique du système");
			}
		} catch (SQLException e) {
			throw new DALException("Problème de connexion lors d'un d'ajout d'un article vendu a la base. Cause :" + e.getMessage());
		}
		return article;
	}
	
	
	public List <ArticleVendu> selectAllEncheresEnCours () throws DALException{
		List<ArticleVendu> articleEnchereEnCours = new ArrayList<ArticleVendu>();
		ArticleVendu articleEC = null;

		try (Connection cnx = ConnectionProvider.getConnection();
			 PreparedStatement rqt = cnx.prepareStatement(SELECT_ALL_EC);){
		
			ResultSet rs = rqt.executeQuery();
			
			while (rs.next()) {
				
				// a chaque nouvel article
				articleEC = new ArticleVendu();
				
				articleEC.setNo_utilisateur(rs.getInt("no_utilisateur"));
				articleEC.setNo_article((rs.getInt("no_article")));   
				articleEC.setNom_article(rs.getString("nom_article"));
				articleEC.setNo_categorie(rs.getInt("no_categorie"));
				articleEC.setDescription(rs.getString("description"));
				articleEC.setDate_debut_enchere(LocalDateTime.of((rs.getDate("date_debut_enchere").toLocalDate()),
						rs.getTime("date_debut_enchere").toLocalTime()));
				articleEC.setDate_fin_enchere(LocalDateTime.of((rs.getDate("date_fin_enchere").toLocalDate()),
						rs.getTime("date_fin_enchere").toLocalTime()));
				articleEC.setPrix_initial(rs.getInt("prix_initial"));
				articleEC.setPrix_vente(rs.getInt("prix_vente"));
				articleEC.setEtat_vente(rs.getString("etat_vente"));

				//Associer les enchères à cet article
				
				if (rs.getInt("montant_enchere")!=0) {

						Enchere enchere = new Enchere(
							rs.getInt("encherisseur"),
							articleEC.getNo_article(),
							LocalDateTime.of((rs.getDate("date_enchere").toLocalDate()),
							rs.getTime("date_enchere").toLocalTime()),
							rs.getInt("montant_enchere"));
							articleEC.setEnchere(enchere);
				}
				//Associer les utilisateurs
				Utilisateur utilisateur = new Utilisateur(
						rs.getInt("no_utilisateur"),
						rs.getString("pseudo"),
						rs.getString("nom"),
						rs.getString("prenom"),
						rs.getString("email"),
						rs.getString("telephone"),
						rs.getString("rue"),
						rs.getString("code_Postal"),
						rs.getString("ville"),
						rs.getString("mot_De_Passe"),
						rs.getInt("credit"), 
						rs.getBoolean("administrateur"));
				articleEC.setUtilisateur(utilisateur);
				articleEnchereEnCours.add(articleEC);

			}
			
		}catch (SQLException e) {
			throw new DALException("Problème pour selection les articles vendus avec des enchères en cours. Cause :" + e.getMessage());

		}
		return articleEnchereEnCours;
	}

	public List <ArticleVendu> selectByCatNomArt (int cat, String nomArt) throws DALException {
		List<ArticleVendu> articleEnchereEnCoursByCatNom = new ArrayList<ArticleVendu>();
		ArticleVendu articleVendu = null;
	
		try(Connection cnx = ConnectionProvider.getConnection();
			PreparedStatement stmt = cnx.prepareStatement(SELECT_BY_CAT_NOM_ART)){
		
			//Préparation de la requete
			stmt.setInt(1,cat);
			stmt.setString(2,"%"+ nomArt + "%");
			
			//exécution de la requete	
			ResultSet rs =stmt.executeQuery();
			while (rs.next()) {
				articleVendu = new ArticleVendu();
				//articleVendu = new ArticleVendu();
				articleVendu.setNo_utilisateur(rs.getInt("no_utilisateur"));
				articleVendu.setNo_article((rs.getInt("no_article")));   
				articleVendu.setNom_article(rs.getString ("nom_article"));
				articleVendu.setNo_categorie(rs.getInt("no_categorie"));
				articleVendu.setDescription(rs.getString("description"));
				articleVendu.setDate_debut_enchere(LocalDateTime.of((rs.getDate("date_debut_enchere").toLocalDate()),
						rs.getTime("date_debut_enchere").toLocalTime()));
				articleVendu.setDate_fin_enchere(LocalDateTime.of((rs.getDate("date_fin_enchere").toLocalDate()),
						rs.getTime("date_fin_enchere").toLocalTime()));
				articleVendu.setPrix_initial(rs.getInt("prix_initial"));
				articleVendu.setPrix_vente(rs.getInt("prix_vente"));
				articleVendu.setEtat_vente(rs.getString("etat_vente"));
							
				//Associer les enchères à cet article
				if (rs.getInt("montant_enchere")!=0) {
					Enchere enchere = new Enchere(
								rs.getInt("encherisseur"),
								articleVendu.getNo_article(),
								LocalDateTime.of((rs.getDate("date_enchere").toLocalDate()),
								rs.getTime("date_enchere").toLocalTime()),
								rs.getInt("montant_enchere"));
					articleVendu.setEnchere(enchere);
				}
				
				//Associer les utilisateurs
				Utilisateur utilisateur = new Utilisateur(rs.getInt("no_utilisateur"),
						rs.getString("pseudo"),rs.getString("nom"),
						rs.getString("prenom"),rs.getString("email"),
						rs.getString("telephone"),rs.getString("rue"),
						rs.getString("code_Postal"),
						rs.getString("ville"),
						rs.getString("mot_De_Passe"),
						rs.getInt("credit"), 
						rs.getBoolean("administrateur"));
				
				articleVendu.setUtilisateur(utilisateur);
				articleEnchereEnCoursByCatNom.add(articleVendu);
			}
		} catch (SQLException e) {
			throw new DALException("Problème pour selection les articles vendus avec des enchères en cours (catégorie + nom. Cause :" + e.getMessage());
		}
		return articleEnchereEnCoursByCatNom;
	}

	public ArticleVendu selectArticleById (int idArticle) throws DALException {
		ArticleVendu article = null ;
		Utilisateur utilisateur = null ;
		Enchere enchere = null ;
		String requete = "SELECT * \r\n"
				+ "FROM ARTICLES_VENDUS av\r\n"
				+ "	inner join UTILISATEURS u on  av.no_utilisateur = u.no_utilisateur \r\n"
				+ "	left join ENCHERES e on av.no_article = e.no_article\r\n"
				+ "WHERE av.no_article = ? ;" ;
		try(Connection cnx = ConnectionProvider.getConnection();
				PreparedStatement stmt = cnx.prepareStatement(requete)){
				//Préparation de la requete
				stmt.setInt(1,idArticle);
				//exécution de la requete	
				ResultSet rs =stmt.executeQuery();

				if (rs.next()) {
					//création d'un article
					article = new ArticleVendu(
							rs.getInt(1),
							rs.getString(2),
							rs.getString(3),
							LocalDateTime.of((rs.getDate(4).toLocalDate()),
									rs.getTime(4).toLocalTime()),
							LocalDateTime.of((rs.getDate(5).toLocalDate()),
									rs.getTime(5).toLocalTime()),
							rs.getInt(6),
							rs.getInt(7),
							rs.getInt(8),
							rs.getInt(9),
							rs.getString(10),
							rs.getString(11));
							
					//association de l'utilisateur vendeur à cet article
					utilisateur = new Utilisateur(
										rs.getInt(12),
										rs.getString(13),
										rs.getString(14),
										rs.getString(15),
										rs.getString(16),
										rs.getString(17),
										rs.getString(18),
										rs.getString(19),
										rs.getString(20),
										rs.getString(21),
										rs.getInt(22), 
										rs.getBoolean(23));
					article.setUtilisateur(utilisateur);
					
					//association de l'enchère à cet article
					if (rs.getInt("montant_enchere")!=0) {
						enchere = new Enchere(
									rs.getInt(24),
									article.getNo_article(),
									LocalDateTime.of((rs.getDate("date_enchere").toLocalDate()),rs.getTime("date_enchere").toLocalTime()),
									rs.getInt("montant_enchere"));
						System.out.println(enchere);
						article.setEnchere(enchere);
					}	
				}
			} catch (SQLException e) {
				throw new DALException("Problème lors de la sélection de l'article par ID. Contactez l'administrateur du site. Cause :" + e.getMessage());
			}
		return article ;
	}
	
	
	

	public List <ArticleVendu> selectArticlesParFiltreConnecte (int idUtilisateur, int cat, String nomArt, boolean encheresOuverts, boolean encheresEnCours, boolean encheresRemportees) throws DALException {
		List<ArticleVendu> articlesEnVenteParFiltre = new ArrayList<ArticleVendu>();
		ArticleVendu articleVendu = null;
		Enchere enchere = null ;
		Utilisateur utilisateur = null ; 
		String reqPart1 = "SELECT	"
										+ "		u.no_utilisateur, u.pseudo, u.nom, u.prenom, u.email, u.telephone, u.rue, u.code_postal, u.ville, u.mot_de_passe, u.credit, u.administrateur, \r\n"
										+ "		av.no_article,av.nom_article,av.no_categorie, av.description, av.date_debut_enchere,av.date_fin_enchere,av.prix_initial,av.prix_vente, av.etat_vente, \r\n"
										+ "		e.date_enchere, e.montant_enchere, e.no_utilisateur as encherisseur, \r\n"
										+ "		c.libelle \r\n"
										+ "\r\n"
										+ "FROM ARTICLES_VENDUS av \r\n"
										+ "			inner join UTILISATEURS u on av.no_utilisateur = u.no_utilisateur\r\n"
										+ "         left join ENCHERES e on av.no_article = e.no_article\r\n"
										+ "         left join CATEGORIES c on av.no_categorie = c.no_categorie " ;
		
		//préparation de la partie 2 de la requpete
		String part2 = null ;
		if (!encheresOuverts && !encheresEnCours && !encheresRemportees) {
			part2 = " WHERE etat_vente='EC' " ;
		}
		if (encheresOuverts && !encheresEnCours && !encheresRemportees) {
			part2 = " WHERE etat_vente='EC' " ;
		}
		if (!encheresOuverts && encheresEnCours && !encheresRemportees) {
			part2 = " WHERE etat_vente='EC' AND e.no_utilisateur = " + idUtilisateur + " " ;
		}
		if (!encheresOuverts && !encheresEnCours && encheresRemportees) {
			part2 = " WHERE etat_vente='VD' AND e.no_utilisateur = " + idUtilisateur + " " ;
		}
		if (encheresOuverts && encheresEnCours && !encheresRemportees) {
			part2 = " WHERE etat_vente='EC' AND e.no_utilisateur = " + idUtilisateur + " " ;
		}
		if (encheresOuverts && !encheresEnCours && encheresRemportees) {
			part2 = " WHERE etat_vente in ('EC', 'VD') AND e.no_utilisateur = " + idUtilisateur + " " ;
		}
		if (!encheresOuverts && encheresEnCours && encheresRemportees) {
			part2 = " WHERE etat_vente in ('EC', 'VD') AND e.no_utilisateur = " + idUtilisateur + " " ;
		}
		if (encheresOuverts && encheresEnCours && encheresRemportees) {
			part2 = " WHERE etat_vente in ('EC', 'VD') AND e.no_utilisateur = " + idUtilisateur + " " ;
		}
		
		String requete = reqPart1 + part2 ;
		//préparation partie 3 de la requête (catégorie)
		String part3 = null ;
		if (cat != 0) {
			part3 = " AND c.no_categorie = " + cat + " ";
			requete += part3 ;
		}
		
		//préparation partie 2 de la requête (catégorie)
		String part4 = null ;
		if (!nomArt.isEmpty() && nomArt!=null) {
			part4 = " AND av.nom_article  LIKE ? " ;
			requete += part4 ;
		}
		
		try(Connection cnx = ConnectionProvider.getConnection();
			PreparedStatement stmt = cnx.prepareStatement(requete)){
		
			//Préparation de la requete
			if (!nomArt.isEmpty() && nomArt!=null) {
				stmt.setString(1,"%"+ nomArt + "%");
			}
			
			//exécution de la requete	
			ResultSet rs =stmt.executeQuery();
			while (rs.next()) {

				//création d'un article
				articleVendu = new ArticleVendu();
				articleVendu.setNo_utilisateur(rs.getInt("no_utilisateur"));
				articleVendu.setNo_article((rs.getInt("no_article")));   
				articleVendu.setNom_article(rs.getString ("nom_article"));
				articleVendu.setNo_categorie(rs.getInt("no_categorie"));
				articleVendu.setDescription(rs.getString("description"));
				articleVendu.setDate_debut_enchere(LocalDateTime.of((rs.getDate("date_debut_enchere").toLocalDate()),
						rs.getTime("date_debut_enchere").toLocalTime()));
				articleVendu.setDate_fin_enchere(LocalDateTime.of((rs.getDate("date_fin_enchere").toLocalDate()),
						rs.getTime("date_fin_enchere").toLocalTime()));
				articleVendu.setPrix_initial(rs.getInt("prix_initial"));
				articleVendu.setPrix_vente(rs.getInt("prix_vente"));
				articleVendu.setEtat_vente(rs.getString("etat_vente"));
				//association de l'utilisateur vendeur à cet article
				utilisateur = new Utilisateur(rs.getInt("no_utilisateur"),
						rs.getString("pseudo"),rs.getString("nom"),
						rs.getString("prenom"),rs.getString("email"),
						rs.getString("telephone"),rs.getString("rue"),
						rs.getString("code_Postal"),
						rs.getString("ville"),
						rs.getString("mot_De_Passe"),
						rs.getInt("credit"), 
						rs.getBoolean("administrateur"));
				articleVendu.setUtilisateur(utilisateur);
				//association de l'enchère à cet article
				if (rs.getInt("montant_enchere")!=0) {
					enchere = new Enchere(rs.getInt("encherisseur"),
								articleVendu.getNo_article(),
								LocalDateTime.of((rs.getDate("date_enchere").toLocalDate()),rs.getTime("date_enchere").toLocalTime()),
								rs.getInt("montant_enchere"));
					articleVendu.setEnchere(enchere);
				}
				
				//ajout dans la liste
				articlesEnVenteParFiltre.add(articleVendu);
			}
		} catch (SQLException e) {
			throw new DALException("Problème lors de la selection des articles. Cause :" + e.getMessage());
		}
		return articlesEnVenteParFiltre;
	}




	public List <ArticleVendu> selectVentesParFiltreConnecte (int idUtilisateur, int cat, String nomArt, boolean ventesEnCours, boolean ventesNonDebutees, boolean ventesTerminees) throws DALException {
		List<ArticleVendu> articlesEnVenteParFiltre = new ArrayList<ArticleVendu>();
		ArticleVendu articleVendu = null;
		Enchere enchere = null ;
		Utilisateur utilisateur = null ; 
		String reqPart1 = "SELECT	"
										+ "		u.no_utilisateur, u.pseudo, u.nom, u.prenom, u.email, u.telephone, u.rue, u.code_postal, u.ville, u.mot_de_passe, u.credit, u.administrateur, \r\n"
										+ "		av.no_article,av.nom_article,av.no_categorie, av.description, av.date_debut_enchere,av.date_fin_enchere,av.prix_initial,av.prix_vente, av.etat_vente, \r\n"
										+ "		e.date_enchere, e.montant_enchere, e.no_utilisateur as encherisseur, \r\n"
										+ "		c.libelle \r\n"
										+ "\r\n"
										+ "FROM ARTICLES_VENDUS av \r\n"
										+ "			inner join UTILISATEURS u on av.no_utilisateur = u.no_utilisateur\r\n"
										+ "         left join ENCHERES e on av.no_article = e.no_article\r\n"
										+ "         left join CATEGORIES c on av.no_categorie = c.no_categorie " ;
		
		//préparation de la partie 2 de la requpete
		String part2 = null ;
		if (!ventesEnCours && !ventesNonDebutees && !ventesTerminees) {
			part2 = " WHERE etat_vente in ('EC', 'CR', 'VD') AND u.no_utilisateur = " + idUtilisateur + " " ; 
		}
		if (ventesEnCours && !ventesNonDebutees && !ventesTerminees) {
			part2 = " WHERE etat_vente='EC' AND u.no_utilisateur = " + idUtilisateur + " " ; 
		}
		if (!ventesEnCours && ventesNonDebutees && !ventesTerminees) {
			part2 = " WHERE etat_vente='CR' AND u.no_utilisateur = " + idUtilisateur + " " ; 
		}
		if (!ventesEnCours && !ventesNonDebutees && ventesTerminees) {
			part2 = " WHERE etat_vente='VD' AND u.no_utilisateur = " + idUtilisateur + " " ; 
		}
		if (ventesEnCours && ventesNonDebutees && !ventesTerminees) {
			part2 = " WHERE etat_vente in ('EC', 'CR') AND u.no_utilisateur = " + idUtilisateur + " " ; 
		}
		if (ventesEnCours && !ventesNonDebutees && ventesTerminees) {
			part2 = " WHERE etat_vente in ('EC', 'VD') AND u.no_utilisateur = " + idUtilisateur + " " ; 
		}
		if (!ventesEnCours && ventesNonDebutees && ventesTerminees) {
			part2 = " WHERE etat_vente in ('CR', 'VD') AND u.no_utilisateur = " + idUtilisateur + " " ; 
		}
		if (ventesEnCours && ventesNonDebutees && ventesTerminees) {
			part2 = " WHERE etat_vente in ('EC', 'CR', 'VD') AND u.no_utilisateur = " + idUtilisateur + " " ; 
		}
		
		String requete = reqPart1 + part2 ;
		//préparation partie 3 de la requête (catégorie)
		String part3 = null ;
		if (cat != 0) {
			part3 = " AND c.no_categorie = " + cat + " ";
			requete += part3 ;
		}
		
		//préparation partie 2 de la requête (catégorie)
		String part4 = null ;
		if (!nomArt.isEmpty() && nomArt!=null) {
			part4 = " AND av.nom_article  LIKE ? " ;
			requete += part4 ;
		}
		
		try(Connection cnx = ConnectionProvider.getConnection();
			PreparedStatement stmt = cnx.prepareStatement(requete)){
		
			//Préparation de la requete
			if (!nomArt.isEmpty() && nomArt!=null) {
				stmt.setString(1,"%"+ nomArt + "%");
			}
			
			System.out.println(requete);
			//exécution de la requete	
			ResultSet rs =stmt.executeQuery();
			while (rs.next()) {
	
				//création d'un article
				articleVendu = new ArticleVendu();
				articleVendu.setNo_utilisateur(rs.getInt("no_utilisateur"));
				articleVendu.setNo_article((rs.getInt("no_article")));   
				articleVendu.setNom_article(rs.getString ("nom_article"));
				articleVendu.setNo_categorie(rs.getInt("no_categorie"));
				articleVendu.setDescription(rs.getString("description"));
				articleVendu.setDate_debut_enchere(LocalDateTime.of((rs.getDate("date_debut_enchere").toLocalDate()),
						rs.getTime("date_debut_enchere").toLocalTime()));
				articleVendu.setDate_fin_enchere(LocalDateTime.of((rs.getDate("date_fin_enchere").toLocalDate()),
						rs.getTime("date_fin_enchere").toLocalTime()));
				articleVendu.setPrix_initial(rs.getInt("prix_initial"));
				articleVendu.setPrix_vente(rs.getInt("prix_vente"));
				articleVendu.setEtat_vente(rs.getString("etat_vente"));
				//association de l'utilisateur vendeur à cet article
				utilisateur = new Utilisateur(rs.getInt("no_utilisateur"),
						rs.getString("pseudo"),rs.getString("nom"),
						rs.getString("prenom"),rs.getString("email"),
						rs.getString("telephone"),rs.getString("rue"),
						rs.getString("code_Postal"),
						rs.getString("ville"),
						rs.getString("mot_De_Passe"),
						rs.getInt("credit"), 
						rs.getBoolean("administrateur"));
				articleVendu.setUtilisateur(utilisateur);
				//association de l'enchère à cet article
				if (rs.getInt("encherisseur")!=0) {
					enchere = new Enchere(rs.getInt("no_utilisateur"),
								articleVendu.getNo_article(),
								LocalDateTime.of((rs.getDate("date_enchere").toLocalDate()),rs.getTime("date_enchere").toLocalTime()),
								rs.getInt("montant_enchere"));
					articleVendu.setEnchere(enchere);
				}
				
				//ajout dans la liste
				articlesEnVenteParFiltre.add(articleVendu);
			}
		} catch (SQLException e) {
			throw new DALException("Problème lors de la selection des articles. Cause :" + e.getMessage());
		}
		return articlesEnVenteParFiltre;
	}
	
	
	public void executeProcedureStockee() throws DALException {
		String requete = "{CALL updateArticle()}" ; 
		try (Connection cnx = ConnectionProvider.getConnection();
			CallableStatement stmt = cnx.prepareCall(requete);){
			stmt.execute();
		} catch (SQLException e) {
			throw new DALException("Problème lors de la mise à jour de la base via la procédure stockée. Cause :" + e.getMessage());
		
		}
	}


}

