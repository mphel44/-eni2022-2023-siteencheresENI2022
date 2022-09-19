package org.encheres.bo;

import java.time.LocalDateTime;
import java.util.List;

public class ArticleVendu {
	protected int no_article;
	protected String nom_article;
	protected String description;
	protected LocalDateTime date_debut_enchere;
	protected LocalDateTime date_fin_enchere;
	protected int prix_initial;
	protected int prix_vente;
	protected int no_utilisateur;
	protected int no_categorie;
	protected String etat_vente;
	protected String image;
	protected Utilisateur utilisateur;
	protected Enchere  enchere;
	protected Categorie categorie;
	protected Retrait retrait ;
	//CONSTRUCTEURS
	
	public ArticleVendu() {
		
	}

	public ArticleVendu(int no_article, String nom_article, String description, LocalDateTime date_debut_enchere,
			LocalDateTime date_fin_enchere, int prix_initial, int prix_vente, int no_utilisateur, int no_categorie,
			String etat_vente, String image) {
		super();
		this.no_article = no_article;
		this.nom_article = nom_article;
		this.description = description;
		this.date_debut_enchere = date_debut_enchere;
		this.date_fin_enchere = date_fin_enchere;
		this.prix_initial = prix_initial;
		this.prix_vente = prix_vente;
		this.no_utilisateur = no_utilisateur;
		this.no_categorie = no_categorie;
		this.etat_vente = etat_vente;
		this.image = image;
	}

	public ArticleVendu(String nom_article, String description, LocalDateTime date_debut_enchere,
			LocalDateTime date_fin_enchere, int prix_initial, int prix_vente, int no_utilisateur, int no_categorie,
			String etat_vente, String image) {
		super();
		this.nom_article = nom_article;
		this.description = description;
		this.date_debut_enchere = date_debut_enchere;
		this.date_fin_enchere = date_fin_enchere;
		this.prix_initial = prix_initial;
		this.prix_vente = prix_vente;
		this.no_utilisateur = no_utilisateur;
		this.no_categorie = no_categorie;
		this.etat_vente = etat_vente;
		this.image = image;
	}
	
	public ArticleVendu(String nom_article, String description, LocalDateTime date_debut_enchere,
			LocalDateTime date_fin_enchere, int prix_initial, int prix_vente,
			String etat_vente, String image) {
		super();
		this.nom_article = nom_article;
		this.description = description;
		this.date_debut_enchere = date_debut_enchere;
		this.date_fin_enchere = date_fin_enchere;
		this.prix_initial = prix_initial;
		this.prix_vente = prix_vente;
		this.etat_vente = etat_vente;
		this.image = image;
	}
	
	public ArticleVendu(String nom_article, String description, LocalDateTime date_debut_enchere,
			LocalDateTime date_fin_enchere, int prix_initial, int prix_vente, int no_utilisateur, int no_categorie,
			String etat_vente, String image, Enchere enchere) {
		super();
		this.nom_article = nom_article;
		this.description = description;
		this.date_debut_enchere = date_debut_enchere;
		this.date_fin_enchere = date_fin_enchere;
		this.prix_initial = prix_initial;
		this.prix_vente = prix_vente;
		this.no_utilisateur = no_utilisateur;
		this.no_categorie = no_categorie;
		this.etat_vente = etat_vente;
		this.image = image;
		this.enchere = enchere;
	}
	
	//GETTERS ET SETTERS
	
	

	public int getNo_article() {
		return no_article;
	}

	public void setNo_article(int no_article) {
		this.no_article = no_article;
	}

	public String getNom_article() {
		return nom_article;
	}

	public void setNom_article(String nom_article) {
		this.nom_article = nom_article;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public LocalDateTime getDate_debut_enchere() {
		return date_debut_enchere;
	}

	public void setDate_debut_enchere(LocalDateTime date_debut_enchere) {
		this.date_debut_enchere = date_debut_enchere;
	}

	public LocalDateTime getDate_fin_enchere() {
		return date_fin_enchere;
	}

	public void setDate_fin_enchere(LocalDateTime date_fin_enchere) {
		this.date_fin_enchere = date_fin_enchere;
	}

	public int getPrix_initial() {
		return prix_initial;
	}

	public void setPrix_initial(int prix_initial) {
		this.prix_initial = prix_initial;
	}

	public int getPrix_vente() {
		return prix_vente;
	}

	public void setPrix_vente(int prix_vente) {
		this.prix_vente = prix_vente;
	}

	public int getNo_utilisateur() {
		return no_utilisateur;
	}

	public void setNo_utilisateur(int no_utilisateur) {
		this.no_utilisateur = no_utilisateur;
	}

	public int getNo_categorie() {
		return no_categorie;
	}

	public void setNo_categorie(int no_categorie) {
		this.no_categorie = no_categorie;
	}

	public String getEtat_vente() {
		return etat_vente;
	}

	public void setEtat_vente(String etat_vente) {
		this.etat_vente = etat_vente;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}
	

	public void setEnchere(Enchere enchere) {
		this.enchere = enchere;
	}

	public Utilisateur getUtilisateur() {
		return utilisateur;
	}

	public void setUtilisateur(Utilisateur utilisateur) {
		this.utilisateur = utilisateur;
	}

	public Enchere getEnchere() {
		return enchere;
	}
	
	public Categorie getCategorie() {
		return categorie;
	}

	public void setCategorie(Categorie categorie) {
		this.categorie = categorie;
	}
	
	//TO STRING
	
	@Override
	public String toString() {
		return "ArticleVendu [no_article=" + no_article + ", nom_article=" + nom_article + ", description="
				+ description + ", date_debut_enchere=" + date_debut_enchere + ", date_fin_enchere=" + date_fin_enchere
				+ ", prix_initial=" + prix_initial + ", prix_vente=" + prix_vente + ", no_utilisateur=" + no_utilisateur
				+ ", no_categorie=" + no_categorie + ", etat_vente=" + etat_vente + ", image=" + image + ", Encheres="
				+ enchere + "Utilisateur ="+utilisateur+"]";
	}

	
		
	}
	
	
	
	



	

	
	
	
	
	
	

