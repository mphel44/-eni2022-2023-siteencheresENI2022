package org.encheres.dal.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.encheres.bo.Utilisateur;
import org.encheres.dal.DALException;
import org.encheres.dal.UtilisateurDAO;

public class UtilisateurDAOJdbcImpl implements UtilisateurDAO {
	
	private final String INSERT_UTILSATEUR = "INSERT into UTILISATEURS (pseudo, nom, prenom, email, telephone, rue, code_postal, ville, mot_de_passe, credit, administrateur) "
			+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?,?)";
	private static final String SELECT_UTILISATEURS = "select * from UTILISATEURS where pseudo=? AND mot_de_passe=?;";
	//private static final String DELETE_UTILISATEURS = " DELETE FROM UTILISATEURS where no_utilisateur = ?";
	private static final String SELECT_UTILISATEURS_BY_ID = "Select no_utilisateur, pseudo,nom,prenom, email,telephone,rue,code_postal, ville from UTILISATEURS where no_utilisateur = ?";
	private static final String SELECT_UTILISATEURS_BY_PSEUDO ="Select no_utilisateur, pseudo,nom,prenom, email,telephone,rue,code_postal, ville from UTILISATEURS where pseudo = ?";
	public Utilisateur insert (Utilisateur user) throws DALException{
		try (Connection cnx = ConnectionProvider.getConnection();
			PreparedStatement stmt = cnx.prepareStatement(INSERT_UTILSATEUR, PreparedStatement.RETURN_GENERATED_KEYS)){
			try {
				//début commit
				cnx.setAutoCommit(false);
				//envoi requête
				stmt.setString(1, user.getPseudo());
				stmt.setString (2,user.getNom());
				stmt.setString(3, user.getPrenom());
				stmt.setString(4, user.getEmail());
				stmt.setString(5, user.getTelephone());
				stmt.setString(6, user.getRue());
				stmt.setString(7, user.getCodePostal());
				stmt.setString(8, user.getVille());
				stmt.setString(9, user.getMotDePasse());
				stmt.setInt(10, user.getCredit());
				stmt.setBoolean(11, user.isAdministrateur());
				stmt.executeUpdate();
				//intégration de la clef
				ResultSet rs = stmt.getGeneratedKeys();
				if (rs.next()) {
					user.setNoUtilisateur(rs.getInt(1));
				}
				//application du commit
				cnx.commit();
			}catch (SQLException e) {
				cnx.rollback();
				throw new DALException("Problème d'ajout d'un utilisateur a la base, rollback méthode. Cause :" + e.getMessage());
			}
		}catch (SQLException e) {
			throw new DALException("Problème de connexion lors d'un d'ajout d'un utilisateur a la base. Cause :" + e.getMessage());
		}
		return user;
	}
	
	public Utilisateur SelectByPseudoMdp(String pseudo, String mdp ) throws DALException {
		Utilisateur utilisateur = null ; 
		try(Connection cnx = ConnectionProvider.getConnection();
			PreparedStatement stmt = cnx.prepareStatement(SELECT_UTILISATEURS);){
			//préparation de la requête
			stmt.setString(1, pseudo);
			stmt.setString(2, mdp);
			//exécution de la requête
			ResultSet rs = stmt.executeQuery();
			//création de l'objet utilisateur pour connection
			if (rs.next()) {
				utilisateur = new Utilisateur() ;
				utilisateur.setNoUtilisateur(rs.getInt("no_utilisateur"));
				utilisateur.setPseudo(rs.getString("pseudo"));
				utilisateur.setNom(rs.getString("nom"));
				utilisateur.setPrenom(rs.getString("prenom"));
				utilisateur.setEmail(rs.getString("email"));
				utilisateur.setTelephone(rs.getString("telephone"));
				utilisateur.setRue(rs.getString("rue"));
				utilisateur.setCodePostal(rs.getString("code_postal"));
				utilisateur.setVille(rs.getString("ville"));
				utilisateur.setMotDePasse(rs.getString("mot_de_passe"));
				utilisateur.setCredit(rs.getInt("credit"));
				utilisateur.setAdministrateur(rs.getBoolean("administrateur"));
			}
		} catch (SQLException e) {
			throw new DALException("Problème d'accès à la base de donnée. Nous vous invitons à contacter l'administrateur du site" + e.getMessage()); 
		}	
		return utilisateur;
	}
	
	public void  delete (int id) throws DALException {
		boolean autoriserSuppression = false ;
		try(Connection cnx = ConnectionProvider.getConnection();){
			//boolean pour vérifier que l'utilisateur n'a pas d'article en cours de vente
			
			try {
				//suppression des enchères de l'utilisateur 
				String requete = "SELECT * FROM ARTICLES_VENDUS WHERE no_utilisateur = ? AND (etat_vente = 'EC' OR etat_vente = 'VD');"; 
				PreparedStatement stmt = cnx.prepareStatement(requete);
				stmt.setInt(1,id);
				ResultSet rs = stmt.executeQuery();
				if (!rs.next()) {
					autoriserSuppression = true ;
					System.out.println("Passage d'autoriser suppression à true");
				} else {
					System.out.println("Autoriser supp reste false");
					throw new DALException("Suppression refusée. Veuillez vérifiez que vous n'avez pas de vente en cours ou en attente de retrait.");
				}
				//ATTENTION hypothèse où article EN COURS DE VENTE en MEME TEMPS qu'article dont la vente est terminée/pas demarrée
			}catch (SQLException e) {
				throw new DALException("Suppression refusée. Veuillez vérifiez que vous n'avez pas de vente en cours ou en attente de retrait. Cause :" + e.getMessage());
			}
			
			//boolean après vérification que l'utilisateur n'essaie pas de supprimer des éléments sur un article en cours de VENTE ;
			if (autoriserSuppression) {
				try {
					
					//suppression des enchères de l'utilisateur 
					String requete = "DELETE FROM ENCHERES WHERE no_article in (SELECT no_article FROM ARTICLES_VENDUS WHERE no_utilisateur = ? AND (etat_vente = 'CR' OR etat_vente = 'RT'));"; 
					PreparedStatement stmt = cnx.prepareStatement(requete);
					//début commit
					cnx.setAutoCommit(false);
					stmt.setInt(1,id);
					stmt.executeUpdate();
					//fin commit
					cnx.commit();
					
					//ATTENTION hypothèse où article EN COURS DE VENTE en MEME TEMPS qu'article dont la vente est terminée/pas demarré
					System.out.println("Passage d'autoriser suppression à true");
				}catch (SQLException e) {
					cnx.rollback();
					throw new DALException("Suppression refusée. Veuillez vérifiez que vous n'avez pas de vente en cours ou en attente de retrait. Cause :" + e.getMessage());
				}
				try {
					//début commit
					System.out.println("On rentre dans le if d'autorisation suppr");
					cnx.setAutoCommit(false);
					//suppression des enchères sur les objets de l'utilisateur
					String requete = "DELETE FROM ENCHERES WHERE no_utilisateur = ?;" ; 
					PreparedStatement stmt = cnx.prepareStatement(requete);
					stmt.setInt(1,id);
					stmt.executeUpdate();
					//fin commit
					cnx.commit();
				}catch (SQLException e) {
					cnx.rollback();
					throw new DALException("Problème lors de la suppression des enchères sur les objets de l'utilisateur. Cause :" + e.getMessage());
				}
				
				try {
					//début commit
					cnx.setAutoCommit(false);
					//suppression des objets de l'utilisateurs AVANT vente et APRES vente
					String requete = "DELETE FROM ARTICLES_VENDUS WHERE no_utilisateur = ?;" ; 
					PreparedStatement stmt = cnx.prepareStatement(requete);
					stmt.setInt(1,id);
					stmt.executeUpdate();
					//fin commit
					cnx.commit();
				}catch (SQLException e) {
					cnx.rollback();
					throw new DALException("Problème lors de la suppression des articles de l'utilisateur. Cause :" + e.getMessage());
				}
				
				try {
					//début commit
					cnx.setAutoCommit(false);
					//suppression des objets de l'utilisateurs AVANT vente et APRES vente
					String requete = "DELETE FROM UTILISATEURS WHERE no_utilisateur = ?;" ; 
					PreparedStatement stmt = cnx.prepareStatement(requete);
					stmt.setInt(1,id);
					stmt.executeUpdate();
					//fin commit
					cnx.commit();
				}catch (SQLException e) {
					cnx.rollback();
					throw new DALException("Problème lors de la suppression de l'utilisateur. Cause :" + e.getMessage());
				}
			}
		}catch (SQLException e) {
			throw new DALException("Problème la phase de suppression de l'utilisateur et de l'ensemble des données associées");
		}
	}
	
	public Utilisateur updateUtilisateur(Utilisateur user) throws DALException {
		String requete = "UPDATE Utilisateurs SET pseudo=?, nom=?, prenom=?, email=?, telephone=?, rue=?, code_postal=?, ville=?, mot_de_passe=? WHERE no_utilisateur=?";
		try (Connection cnx = ConnectionProvider.getConnection();
			PreparedStatement stmt = cnx.prepareStatement(requete)){
			try {
				//début commit
				cnx.setAutoCommit(false);
					
				//envoi requête
				stmt.setString(1, user.getPseudo());
				stmt.setString (2,user.getNom());
				stmt.setString(3, user.getPrenom());
				stmt.setString(4, user.getEmail());
				stmt.setString(5, user.getTelephone());
				stmt.setString(6, user.getRue());
				stmt.setString(7, user.getCodePostal());
				stmt.setString(8, user.getVille());
				stmt.setString(9, user.getMotDePasse());
				stmt.setInt(10, user.getNoUtilisateur());
				stmt.executeUpdate();
				
				//fin commit
				cnx.commit();
			}catch (SQLException e) {
				cnx.rollback();
				throw new DALException("Problème de modification des données dans la base. Rollback. Cause :" + e.getMessage());
			}	
		} catch (SQLException e) {
			throw new DALException("Problème de modification des données dans la base. Cause :" + e.getMessage());

		}
		return user ;
	}
	
	public Utilisateur SelectById (int id) throws DALException {
		Utilisateur utilisateur = new Utilisateur();
		try(Connection cnx = ConnectionProvider.getConnection();
			PreparedStatement stmt = cnx.prepareStatement(SELECT_UTILISATEURS_BY_ID);){
	
			stmt.setInt(1, id);
			
			ResultSet rs = stmt.executeQuery();
			
			if (rs.next()){
				utilisateur.setNoUtilisateur(rs.getInt("no_utilisateur"));
				utilisateur.setPseudo(rs.getString("pseudo"));
				utilisateur.setNom(rs.getString("nom"));
				utilisateur.setPrenom(rs.getString("prenom"));
				utilisateur.setEmail(rs.getString("email"));
				utilisateur.setTelephone(rs.getString("telephone"));
				utilisateur.setRue(rs.getString("rue"));
				utilisateur.setCodePostal(rs.getString("code_postal"));
				utilisateur.setVille(rs.getString("ville"));
			}

			
			}catch (SQLException e) {
				
				throw new DALException("Problème de selection d'un utilisateur via id. Cause :" + e.getMessage());
			}
		return utilisateur;
				
	}
	public Utilisateur SelectByPseudo(String pseudo ) throws DALException {
		Utilisateur utilisateur = new Utilisateur();
		try(Connection cnx = ConnectionProvider.getConnection();
			PreparedStatement stmt = cnx.prepareStatement(SELECT_UTILISATEURS_BY_PSEUDO);){
	
			stmt.setString(1, pseudo);
			
			ResultSet rs = stmt.executeQuery();
			
			if (rs.next()){
				
				utilisateur.setNoUtilisateur(rs.getInt("no_utilisateur"));
				utilisateur.setPseudo(rs.getString("pseudo"));
				utilisateur.setNom(rs.getString("nom"));
				utilisateur.setPrenom(rs.getString("prenom"));
				utilisateur.setEmail(rs.getString("email"));
				utilisateur.setTelephone(rs.getString("telephone"));
				utilisateur.setRue(rs.getString("rue"));
				utilisateur.setCodePostal(rs.getString("code_postal"));
				utilisateur.setVille(rs.getString("ville"));
				
			}

			
			}catch (SQLException e) {
				
				throw new DALException("Problème de selection d'un utilisateur via id. Cause :" + e.getMessage());
			}
		return utilisateur;
	}
	
	
	public int selectCreditUser(int idUser) throws DALException {
		String requete = "SELECT credit FROM UTILISATEURS WHERE no_utilisateur = ? ;" ;
		int credit = 0 ;
		try(Connection cnx = ConnectionProvider.getConnection();
			PreparedStatement stmt = cnx.prepareStatement(requete);){
			stmt.setInt(1, idUser);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()){
				credit = rs.getInt(1);
			}	
		} catch (SQLException e) {
			throw new DALException("Problème lors de la sélection du crédit de l'utilisateur." + e.getMessage()); 
		}
		return credit ;
	}
	
	public void changeCreditUser(int nouveauCredit, int idUser) throws DALException {
		String requete = "UPDATE UTILISATEURS SET credit = ? WHERE no_utilisateur = ? ;" ;
		try(Connection cnx = ConnectionProvider.getConnection();
			PreparedStatement stmt = cnx.prepareStatement(requete);){
			
			try {
				cnx.setAutoCommit(false);
				stmt.setInt(1, nouveauCredit);
				stmt.setInt(2, idUser);
				stmt.executeUpdate() ; 
				cnx.commit();
			}catch (SQLException e) {
				cnx.rollback();
				throw new DALException("Problème lors de la suppression de l'utilisateur. Cause :" + e.getMessage());
			}
		} catch (SQLException e) {
			throw new DALException("Problème lors de la mise à jour du crédit de l'utilisateur." + e.getMessage()); 
		}
	}
	
}