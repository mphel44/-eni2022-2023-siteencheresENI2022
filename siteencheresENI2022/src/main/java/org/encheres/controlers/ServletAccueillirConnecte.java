package org.encheres.controlers;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.encheres.bll.ArticleVenduManager;
import org.encheres.bo.ArticleVendu;
import org.encheres.bo.Utilisateur;

/**
 * Servlet implementation class ServletAccueillirConnecte
 */
@WebServlet("/servlet/accueil/connected")
public class ServletAccueillirConnecte extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ServletAccueillirConnecte() {
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
		ArticleVenduManager mgr = ArticleVenduManager.getInstance();

		try {
			List<ArticleVendu> listeArticleFiltreConnecte = mgr.listeArticleVenteEC();
			request.setAttribute("listeConnecte", listeArticleFiltreConnecte);
		} catch (Exception e) {
			request.setAttribute("erreur", e);
		}
		RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/Page_d_accueil.jsp");
		rd.forward(request, response);	
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
System.out.println("on entre dans la DOPOSTE");
		ArticleVenduManager mgr = ArticleVenduManager.getInstance();
		Utilisateur user = (Utilisateur)request.getSession().getAttribute("utilisateurConnecte");
		int idUtilisateur = user.getNoUtilisateur();
		String radioButton = request.getParameter("type-encheres");
		String rechercheMotClef = request.getParameter("filtre");
		String categorieChoisie = request.getParameter("categorie");
		int cat = 0 ;
		switch (categorieChoisie) {
			case "Informatique" : cat=1 ; break ;
			case "Ameublement" : cat=2 ; break ;
			case "Vêtement" : cat=3 ; break ;
			case "Sport & Loisirs" : cat=4 ; break ;
			default : cat=0; break ;
		}
		
		if (radioButton.equals("achats")) {
			boolean encheresOuvertes = false ;
			boolean encheresEnCours = false ;
			boolean encheresRemportees = false ;
			
			if (request.getParameterValues("encheres")!= null) {
				String[] choixSelectAccueil = request.getParameterValues("encheres");
				List<String> listeChoixSelectAccueil = Arrays.asList(choixSelectAccueil);
				for (String choix : listeChoixSelectAccueil) {
					if (choix.equals("ouvertes")) {
						encheresOuvertes = true;
					}
					if (choix.equals("encours")) {
						encheresEnCours = true;
					}
					if (choix.equals("remportees")) {
						encheresRemportees = true;
					}
				}
			}
			try {
				List<ArticleVendu> listeArticleFiltreConnecte = mgr.selectArticlesParFiltreConnecte(idUtilisateur, cat, rechercheMotClef, encheresOuvertes, encheresEnCours, encheresRemportees);
				request.setAttribute("listeConnecte", listeArticleFiltreConnecte);
			} catch (Exception e) {
				request.setAttribute("erreur", e);
			}
			RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/Page_d_accueil.jsp");
			rd.forward(request, response);			
		} else {
			//if radioButton == "ventes"
			boolean ventesEnCours = false ;
			boolean ventesNonDebutees = false ;
			boolean ventesTerminees = false ;
			if (request.getParameterValues("ventes")!= null) {
				String[] choixSelectAccueil = request.getParameterValues("ventes");
				List<String> listeChoixSelectAccueil = Arrays.asList(choixSelectAccueil);
				for (String choix : listeChoixSelectAccueil) {
					if (choix.equals("venteencours")) {
						ventesEnCours = true;
					}
					if (choix.equals("nondebutees")) {
						ventesNonDebutees = true;
					}
					if (choix.equals("terminees")) {
						ventesTerminees = true;
					}
				}
			} 
			try {
				List<ArticleVendu> listeArticleFiltreConnecte = mgr.selectVentesParFiltreConnecte(idUtilisateur, cat, rechercheMotClef, ventesEnCours, ventesNonDebutees, ventesTerminees);
				request.setAttribute("listeConnecte", listeArticleFiltreConnecte);
			} catch (Exception e) {
				request.setAttribute("erreur", e);
			}
			RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/Page_d_accueil.jsp");
			rd.forward(request, response);	
			
			
		}
	}

}
