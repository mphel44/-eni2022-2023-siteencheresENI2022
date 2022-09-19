package org.encheres.controlers;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.encheres.bll.ArticleVenduManager;
import org.encheres.bll.BLLException;
import org.encheres.bo.ArticleVendu;
import org.encheres.bo.Enchere;

/**
 * Servlet implementation class AfficherArticle
 */
@WebServlet("/display/article")
public class ServletAfficherArticle extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ServletAfficherArticle() {
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
		
		//FWD DE L'ID ARTICLE - ne pas supprimer
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
		String retrait = article.getUtilisateur().getRue() + " - " +article.getUtilisateur().getCodePostal() + " " + article.getUtilisateur().getVille();
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
		
		doGet(request, response);
	}

}
