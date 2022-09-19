package org.encheres.bll;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.encheres.bo.ArticleVendu;
import org.encheres.bo.Retrait;
import org.encheres.bo.Utilisateur;
import org.encheres.dal.ArticleVenduDAO;
import org.encheres.dal.DALException;
import org.encheres.dal.DAOFactory;

public class ArticleVenduManager {
	
	private static ArticleVenduManager mngr;
	private ArticleVenduDAO artDAO;
	
	private ArticleVenduManager() {
		artDAO = DAOFactory.getArticleVenduDAO();
	}
	
	public static ArticleVenduManager getInstance() {
			if (mngr == null) {
				mngr = new ArticleVenduManager();
			}
		return mngr;
	}
	
	public ArticleVendu addArticle(ArticleVendu art, Retrait retrait) throws BLLException {
		BLLException bllExceptions = new BLLException();
		
		if (art.getNom_article() == null || art.getNom_article().isEmpty()){
			Exception e = new Exception ("Le nom article est nécessaire !");
			bllExceptions.addException(e);
		}
		
		if (art.getDescription() == null || art.getDescription().isEmpty()){
			Exception e = new Exception ("La description article est nécessaire !");
			bllExceptions.addException(e);
		}
		
		if (art.getDate_debut_enchere() == null){
			Exception e = new Exception ("La date de début d'enchère article est nécessaire !");
			bllExceptions.addException(e);
		}
		
		if (art.getDate_fin_enchere() == null){
			Exception e = new Exception ("La date de fin d'enchère article est nécessaire !");
			bllExceptions.addException(e);
		}
		
		if (art.getPrix_initial() == 0){
			Exception e = new Exception ("Le prix initial article est nécessaire !");
			bllExceptions.addException(e);
		}
		
		if (art.getNo_categorie() == 0){
			Exception e = new Exception ("La catégorie article est nécessaire !");
			bllExceptions.addException(e);
		}
		
		if (retrait.getRue() == null || retrait.getRue().isEmpty() ){
			Exception e = new Exception ("La rue de retrait de l'article est nécessaire !");
			bllExceptions.addException(e);
		}
		
		if (retrait.getCode_postal() == null || retrait.getCode_postal().isEmpty() ){
			Exception e = new Exception ("Le code postal de la ville de retrait de l'article est nécessaire !");
			bllExceptions.addException(e);
		}
		
		if (retrait.getVille() == null || retrait.getVille().isEmpty() ){
			Exception e = new Exception ("La ville de retrait de l'article est nécessaire !");
			bllExceptions.addException(e);
		}
		
		if (bllExceptions.isEmpty()) {
			try {
				return artDAO.insert(art,retrait);
			} catch (DALException e) {
				Exception ex = new Exception(e.getMessage());
				bllExceptions.addException(ex);
				throw bllExceptions;
			}
		}	else {
			throw bllExceptions;
		}
	}

	public ArticleVendu selectParId(int idArticle) throws BLLException {
		BLLException bllExceptions = new BLLException();
		ArticleVendu article = null ; 
		try {
			article = artDAO.selectArticleById(idArticle);
		} catch (DALException e) {
			Exception ex = new Exception(e.getMessage());
			bllExceptions.addException(ex);
			throw bllExceptions;
		}
		return article;
	}
	
	
	public List <ArticleVendu> listeArticleVenteEC () throws BLLException{
		BLLException bllExceptions = new BLLException();
		List <ArticleVendu> articleEC;
		try {
			articleEC = artDAO.selectAllEncheresEnCours();
			
		} catch (DALException e) {
			Exception ex = new Exception(e.getMessage());
			bllExceptions.addException(ex);
			throw bllExceptions;
		}
		return articleEC;
	}
	

	public List <ArticleVendu> selectArticlesParFiltreConnecte(int idUtilisateur, int cat, String nomArt, boolean encheresOuverts, boolean encheresEnCours, boolean encheresRemportees) throws BLLException {
		List<ArticleVendu> listeArticleFiltreConnecte = new ArrayList<ArticleVendu>();
		BLLException bllExceptions = new BLLException();
		
		try {
			listeArticleFiltreConnecte = artDAO.selectArticlesParFiltreConnecte(idUtilisateur, cat, nomArt, encheresOuverts, encheresEnCours, encheresRemportees);
			
		} catch (DALException e) {
			Exception ex = new Exception(e.getMessage());
			bllExceptions.addException(ex);
			throw bllExceptions;
		}
		return listeArticleFiltreConnecte ;
	}


	public List <ArticleVendu> selectVentesParFiltreConnecte(int idUtilisateur, int cat, String nomArt, boolean ventesEnCours, boolean ventesNonDebutees, boolean ventesTerminees) throws BLLException {
		List<ArticleVendu> listeArticleFiltreConnecte = new ArrayList<ArticleVendu>();
		BLLException bllExceptions = new BLLException();
		
		try {
			listeArticleFiltreConnecte = artDAO.selectVentesParFiltreConnecte(idUtilisateur, cat, nomArt, ventesEnCours, ventesNonDebutees, ventesTerminees);
		} catch (DALException e) {
			Exception ex = new Exception(e.getMessage());
			bllExceptions.addException(ex);
			throw bllExceptions;
		}
		return listeArticleFiltreConnecte ;
	}

	public List<ArticleVendu>listeArticlesECByCatNom (int n_categorie, String nom_article) throws BLLException{
		BLLException bllExceptions = new BLLException();
		List <ArticleVendu> articlesByCatNom;
		try {
			articlesByCatNom = artDAO.selectByCatNomArt(n_categorie, nom_article);
		} catch (DALException e) {
			Exception ex = new Exception(e.getMessage());
			bllExceptions.addException(ex);
			throw bllExceptions;
		}
		return articlesByCatNom;
	}
	
	public void executeProcedureStockee() throws BLLException {
		BLLException bllExceptions = new BLLException();
		try {
			artDAO.executeProcedureStockee();
		} catch (DALException e) {
			Exception ex = new Exception(e.getMessage());
			bllExceptions.addException(ex);
			throw bllExceptions;
		}
	}
}
