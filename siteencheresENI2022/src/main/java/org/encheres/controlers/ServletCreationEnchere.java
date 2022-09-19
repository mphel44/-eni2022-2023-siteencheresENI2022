package org.encheres.controlers;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.encheres.bll.ArticleVenduManager;
import org.encheres.bll.BLLException;
import org.encheres.bll.EnchereManager;
import org.encheres.bll.UtilisateurManager;
import org.encheres.bo.ArticleVendu;
import org.encheres.bo.Enchere;
import org.encheres.bo.Utilisateur;

/**
 * Servlet implementation class ServletCreationEnchere
 */
@WebServlet("/servlet/add/enchere")
public class ServletCreationEnchere extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ServletCreationEnchere() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Servlet#init(ServletConfig)
	 */
	public void init(ServletConfig config) throws ServletException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String idArticle = request.getParameter("idArticle");
		request.setAttribute("idArticle", idArticle);
		int idArt = Integer.parseInt(idArticle);
		ArticleVendu article = null;
		
		ArticleVenduManager mngr = ArticleVenduManager.getInstance();
		
		try {
			article = mngr.selectParId(idArt);
		} catch (BLLException e) {
			e.printStackTrace();
		}
		
		String nom = article.getNom_article();
		String description = article.getDescription();
		int noCategorie = article.getNo_categorie();
		String categorie = null;	
			if (noCategorie == 1) {
				categorie = "Informatique";
			} else if (noCategorie == 2) {
				categorie = "Ameublement";
			} else if (noCategorie == 3) {
				categorie = "Vêtement";
			} else if (noCategorie == 4) {
				categorie = "Sport & Loisir";
			}
		
		LocalDate debut = article.getDate_debut_enchere().toLocalDate();
		int prixInitial = article.getPrix_initial();
		String prix = String.valueOf(article.getPrix_initial());
		int meilleureOffre = 0;
		String offre = null;
			if (article.getEnchere() != null) {
			meilleureOffre = article.getEnchere().getMontantEnchere();	
			offre = String.valueOf(article.getEnchere().getMontantEnchere());
			} else {
				offre = "Pas d'enchère actuellement sur cet article";
			}		
		LocalDate fin = article.getDate_fin_enchere().toLocalDate();
		String retrait = article.getUtilisateur().getRue() + " - " + article.getUtilisateur().getCodePostal() + " " + article.getUtilisateur().getVille();
		String vendeur = article.getUtilisateur().getPseudo();
		int enchere = 0;	
			if (prixInitial < meilleureOffre) {
				enchere = article.getEnchere().getMontantEnchere();
			} else {
				enchere = article.getPrix_initial();
			}
		
		request.setAttribute("nom", nom);
		request.setAttribute("description", description);
		request.setAttribute("categorie", categorie);
		request.setAttribute("debut", debut);
		request.setAttribute("prix", prix);
		request.setAttribute("offre", offre);
		request.setAttribute("fin", fin);
		request.setAttribute("retrait", retrait);
		request.setAttribute("vendeur", vendeur);
		request.setAttribute("enchere", enchere);
		
		RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/article/affichagearticle.jsp");
		rd.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Utilisateur encherisseur = (Utilisateur)request.getSession().getAttribute("utilisateurConnecte");
		String idArticle = request.getParameter("idArticle");
		String idEncherisseur = request.getParameter("idEncherisseur");
		int idArticleInt = Integer.parseInt(idArticle);
		int idEncherisseurInt = Integer.parseInt(idEncherisseur);
		String montant = request.getParameter("montant");
		int montantInt = 0;	
			try {
			montantInt = Integer.parseInt(montant);
			} catch (Exception e) {
				e.printStackTrace();
			}
		ArticleVenduManager articleMgr = ArticleVenduManager.getInstance() ;
		UtilisateurManager userMgr = UtilisateurManager.getInstance();
		EnchereManager enchereMgr = EnchereManager.getInstance() ;
		try {
			ArticleVendu article = articleMgr.selectParId(idArticleInt);
			//CHECK suffisamment de fonds
			if (encherisseur.getCredit()>=montantInt) {
				//CHECK encherisseur n'est pas vendeur
				if (article.getUtilisateur().getNoUtilisateur()!=idEncherisseurInt) {
					if (article.getEtat_vente().equals("EC")) {
						if(article.getEnchere()!=null) {
							//il existe une enchère
							if (montantInt>article.getPrix_initial() && montantInt>article.getEnchere().getMontantEnchere()) {
								//geston crédit encherisseur
								int nouveauCredit = encherisseur.getCredit()-montantInt;
								encherisseur.setCredit(nouveauCredit);
								request.getSession().setAttribute("utilisateurConnecte", encherisseur) ;
								userMgr.changeCreditUser(nouveauCredit, idEncherisseurInt);

								//gestion crédit ancien encherisseur - avant changement enchere
								int creditAncienEncherisseur = userMgr.selectCreditUser(article.getEnchere().getNoUtilisateur());
								int nouveauCreditAncienEncherisseur = creditAncienEncherisseur + article.getEnchere().getMontantEnchere();
								userMgr.changeCreditUser(nouveauCreditAncienEncherisseur, article.getEnchere().getNoUtilisateur());
								//DELETE LA PRECEDENTE ENCHERE 
								enchereMgr.deleteEnchere(idArticleInt) ; 
								//Ajout enchere
								Enchere enchere = new Enchere (
										idEncherisseurInt,
										idArticleInt,
										LocalDateTime.now(),
										montantInt);
								enchereMgr.addEnchere(enchere);	
								boolean ok = true;
								request.setAttribute("ok", ok);
								request.setAttribute("validation", "Votre enchère a été créée avec succés");
								this.doGet(request, response);
							} else {
								String erreur = "Enchère impossible : le montant doit être supérieur au prix initial ou à l'enchère actuelle";
								Erreur(erreur, request, response);
								
							}
						} else {
							//il n'existe pas d'enchère
							if (montantInt>article.getPrix_initial()) {
								//ENCHERE POSSIBLE SANS DELETE CAR PAS D'ENCHERE
								Enchere enchere = new Enchere (
										idEncherisseurInt,
										idArticleInt,
										LocalDateTime.now(),
										montantInt);
								enchereMgr.addEnchere(enchere);
								int nouveauCredit = encherisseur.getCredit()-montantInt;
								encherisseur.setCredit(nouveauCredit);
								request.getSession().setAttribute("utilisateurConnecte", encherisseur) ;
								userMgr.changeCreditUser(nouveauCredit, idEncherisseurInt);
								boolean ok = true;
								request.setAttribute("ok", ok);
								request.setAttribute("validation", "Votre enchère a été créée avec succés");
								this.doGet(request, response);
							} else {
								String erreur = "Enchère impossible : le montant doit être supérieur au prix initial ou à l'enchère actuelle";
								Erreur(erreur, request, response);
							}
						}
					} else {
						String erreur = "Les enchères ne sont pas encore ouvertes pour cet objet";
						Erreur(erreur, request, response);
					}
									
				} else {
					String erreur = "C'est votre propre objet ! Vous ne pouvez pas enchérir dessus !";
					Erreur(erreur, request, response);
				}	
			} else {
				String erreur = "Vous n'avez pas suffisamment de crédits";
				Erreur(erreur, request, response);
			}
		} catch (BLLException e) {
			List<Exception> listeErrModifUtil = e.getBllExceptions();
				for (Exception erreur : listeErrModifUtil) {
					System.out.println(erreur.getMessage());
				}
			boolean testErreurBll = true;
			request.setAttribute("testErreurBll", testErreurBll);
			request.setAttribute("listeErrModifUtil", listeErrModifUtil);
			this.doGet(request, response);
		}
	
	}
	
	public void Erreur(String erreur, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException { 	
		boolean testErreurServlet = true;
		request.setAttribute("testErreurServlet", testErreurServlet);
		request.setAttribute("erreur", erreur);
		this.doGet(request, response);
	}
		
	//FIN CLASSE
}
