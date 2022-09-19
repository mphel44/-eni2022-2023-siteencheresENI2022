package org.encheres.bo;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Enchere {
	
	//ATTRIBUTS
	protected int noUtilisateur ; 
	protected int noArticle ;
	protected LocalDateTime dateEnchere ;
	protected int montantEnchere ;
	//protected List<ArticleVendu> listeArticlesVendus = new ArrayList<ArticleVendu>(); 
	
	//CONSTRUCTORS
	public Enchere(){
	}
	
	public Enchere(int noUtilisateur, int noArticle, LocalDateTime dateEnchere, int montantEnchere) {
		super();
		this.noUtilisateur = noUtilisateur;
		this.noArticle = noArticle;
		this.dateEnchere = dateEnchere;
		this.montantEnchere = montantEnchere;
	}
	


	//GETTERS ET SETTERS
	public int getNoUtilisateur() {
		return noUtilisateur;
	}

	public void setNoUtilisateur(int noUtilisateur) {
		this.noUtilisateur = noUtilisateur;
	}

	public int getNoArticle() {
		return noArticle;
	}

	public void setNoArticle(int noArticle) {
		this.noArticle = noArticle;
	}

	public LocalDateTime getDateEnchere() {
		return dateEnchere;
	}

	public void setDateEnchere(LocalDateTime dateEnchere) {
		this.dateEnchere = dateEnchere;
	}

	public int getMontantEnchere() {
		return montantEnchere;
	}

	public void setMontantEnchere(int montantEnchere) {
		this.montantEnchere = montantEnchere;
	}
	
//	public List<ArticleVendu> getListeArticlesVendus() {
//		return listeArticlesVendus;
//	}
//
//	public void setListeArticlesVendus(List<ArticleVendu> listeArticlesVendus) {
//		this.listeArticlesVendus = listeArticlesVendus;
//	}

	//TOSTRING
	@Override
	public String toString() {
		return "Enchere [noUtilisateur=" + noUtilisateur + ", noArticle=" + noArticle + ", dateEnchere=" + dateEnchere
				+ ", montantEnchere=" + montantEnchere + "]";
	}

}
