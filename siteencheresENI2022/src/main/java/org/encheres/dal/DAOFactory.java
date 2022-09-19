package org.encheres.dal;

import org.encheres.dal.jdbc.ArticleVenduDAOJdbcImpl;
import org.encheres.dal.jdbc.EnchereDAOJdbcImpl;
import org.encheres.dal.jdbc.UtilisateurDAOJdbcImpl;

public class DAOFactory {
	
	public static UtilisateurDAO getUtilisateurDAO() {
		UtilisateurDAO userDAO = new UtilisateurDAOJdbcImpl();
		return userDAO;
	}
	
	public static ArticleVenduDAO getArticleVenduDAO() {
		ArticleVenduDAO articleDAO = new ArticleVenduDAOJdbcImpl();
		return articleDAO;
	}
	
	public static EnchereDAO getEnchereDAO() {
		EnchereDAO enchereDAO = new EnchereDAOJdbcImpl();
		return enchereDAO;
	}
}
