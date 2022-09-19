package org.encheres.dal;

import java.util.List;

import org.encheres.bo.ArticleVendu;


import org.encheres.bo.Categorie;



import org.encheres.bo.Retrait;

public interface ArticleVenduDAO {

	public ArticleVendu insert(ArticleVendu article, Retrait retrait) throws DALException;
	public List <ArticleVendu> selectAllEncheresEnCours () throws DALException;
	public List <ArticleVendu> selectByCatNomArt (int cat, String nomArt) throws DALException;
	public List <ArticleVendu> selectArticlesParFiltreConnecte (int idUtilisateur, int cat, String nomArt, boolean encheresOuverts, boolean encheresEnCours, boolean encheresRemportees) throws DALException ;
	public List <ArticleVendu> selectVentesParFiltreConnecte (int idUtilisateur, int cat, String nomArt, boolean ventesEnCours, boolean ventesNonDebutees, boolean ventesTerminees) throws DALException ;
	public ArticleVendu selectArticleById (int idArticle) throws DALException ;
	public void executeProcedureStockee() throws DALException ;

}
