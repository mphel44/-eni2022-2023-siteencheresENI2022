package org.encheres.dal.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;

import org.encheres.bo.Enchere;
import org.encheres.dal.DALException;
import org.encheres.dal.EnchereDAO;

public class EnchereDAOJdbcImpl implements EnchereDAO {
	
	public void insertEnchere(Enchere enchereAjoutee) throws DALException {
		String requete = "INSERT into ENCHERES (no_utilisateur, no_article, date_enchere, montant_enchere) "
				+ "VALUES (?, ?, ?, ?)";
		try (Connection cnx = ConnectionProvider.getConnection();
				PreparedStatement stmt = cnx.prepareStatement(requete)){
			try {
				//début commit
				cnx.setAutoCommit(false);
				//requête
				stmt.setInt(1, enchereAjoutee.getNoUtilisateur());
				stmt.setInt (2, enchereAjoutee.getNoArticle());
				stmt.setTimestamp(3, Timestamp.valueOf(enchereAjoutee.getDateEnchere()));
				stmt.setInt(4, enchereAjoutee.getMontantEnchere());
				stmt.executeUpdate();
				//fin commit
				cnx.commit();
			} catch (SQLException e) {
				cnx.rollback();
				throw new DALException("Problème lors de l'insertion de l'enchère dans la base des donnéees. L'insertion n'a pas été commitée." + e.getMessage());
			}
		} catch (SQLException e) {
			throw new DALException("Problème lors de l'insertion de l'enchère dans la base des donnéees.");
		}
	}
	
	public void deleteEnchere (int idArticle) throws DALException {
		String requete = "DELETE FROM ENCHERES WHERE no_article = ?;" ; 
		System.out.println("PENDANT DELETE");
		try (Connection cnx = ConnectionProvider.getConnection();
				PreparedStatement stmt = cnx.prepareStatement(requete)){
			try {
				cnx.setAutoCommit(false);
				stmt.setInt(1, idArticle);
				stmt.executeUpdate();
				cnx.commit();
			} catch (SQLException e) {
				cnx.rollback();
				throw new DALException("Problème lors de la suppression de l'enchère dans la BDD. La suppression a été commitée.");
			}
		} catch (SQLException e) {
			throw new DALException("Problème lors de la suppression de l'enchère dans la BDD. La suppression a été commitée.");
		}
	}
	
	
	
	
	
}

