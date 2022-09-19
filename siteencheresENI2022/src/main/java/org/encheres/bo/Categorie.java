package org.encheres.bo;

import java.util.ArrayList;
import java.util.List;

public class Categorie {
	protected int no_categorie;
	protected String libelle;
	protected List <ArticleVendu> articlevendu = new ArrayList<ArticleVendu>();
	
	//CONSTRUCTEURS
	public Categorie() {
		
	}

	public Categorie(int no_categorie, String libelle, List<ArticleVendu> articlevendu) {
		super();
		this.no_categorie = no_categorie;
		this.libelle = libelle;
		this.articlevendu = articlevendu;
	}
	public Categorie(int no_categorie, String libelle) {
		super();
		this.no_categorie = no_categorie;
		this.libelle = libelle;
	}




	//GETTERS ET SETTERS	
	
	
	

	public int getNo_categorie() {
		return no_categorie;
	}


	public void setNo_categorie(int no_categorie) {
		this.no_categorie = no_categorie;
	}


	public String getLibelle() {
		return libelle;
	}


	public void setLibelle(String libelle) {
		this.libelle = libelle;
	}

	public List<ArticleVendu> getArticlevendu() {
		return articlevendu;
	}

	public void setArticlevendu(List<ArticleVendu> articlevendu) {
		this.articlevendu = articlevendu;
	}

	@Override
	public String toString() {
		return "Categorie [no_categorie=" + no_categorie + ", libelle=" + libelle + "]";
	}
	
	
}


