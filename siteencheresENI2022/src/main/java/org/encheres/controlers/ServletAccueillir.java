package org.encheres.controlers;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.zip.DataFormatException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.encheres.bll.ArticleVenduManager;
import org.encheres.bo.ArticleVendu;

/**
 * Servlet implementation class ServletAccueillir
 */
@WebServlet("/Accueillir")
public class ServletAccueillir extends HttpServlet {
	boolean statutDeConnexion ;
	boolean statutAdministrateur ;
	boolean erreurCategorie;
	boolean listeVide;
	private static final long serialVersionUID = 1L;
//	boolean Sansfiltre;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ServletAccueillir() {
        super();
        // TODO Auto-generated constructor stub
    }

    
    
	@Override
	public void init(ServletConfig config) throws ServletException {
		// TODO Auto-generated method stub
		super.init(config);
		statutDeConnexion = false ; 
		statutAdministrateur = false ;
		erreurCategorie=false;
		listeVide = false;
	//	Sansfiltre = true;
	}



	/**
	 * @see Servlet#init(ServletConfig)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.getSession().setAttribute("statutDeConnexion", statutDeConnexion);
		request.getSession().setAttribute("statutAdministrateur", statutAdministrateur);
	//	request.getSession().setAttribute("Avecfiltre", Sansfiltre);
		
		ArticleVenduManager mgr = ArticleVenduManager.getInstance();
		try {
			List<ArticleVendu> listeArticleVendu = mgr.listeArticleVenteEC();
			request.setAttribute("listeAEC", listeArticleVendu);
			request.getSession().setAttribute("liste", listeArticleVendu);
		} catch (Exception e) {
			request.setAttribute("erreur", e);
		}
		
		RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/Page_d_accueil.jsp");
		rd.forward(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String filtre = request.getParameter("filtre");
		String message ="Veuillez choisir une catégorie";
	//	Sansfiltre = true;
	//	request.getSession().setAttribute("Avecfiltre", Sansfiltre);
		String categorie = request.getParameter("categorie");
		int noCategorie = 0;
		if (categorie.equals("AucuneSelection")) {
			erreurCategorie = true;
			request.setAttribute("erreurCategorie", erreurCategorie);
			request.setAttribute("erreurCat", message);
			System.out.println(erreurCategorie);
			System.out.println(message);
		}
		
			if (categorie.equals("Informatique")) {
				
				noCategorie = 1;	
				System.out.println(categorie + noCategorie);
			} else if (categorie.equals("Ameublement")) {

				noCategorie = 2;
				System.out.println(categorie + noCategorie);

			} else if (categorie.equals("Vêtement")) {

				noCategorie = 3;
				System.out.println(categorie + noCategorie);

			} else if (categorie.equals("Sport&Loisirs")) {
				noCategorie = 4;
				System.out.println(categorie + noCategorie);

			}
			ArticleVenduManager mgr = ArticleVenduManager.getInstance();
			try {
				
				List<ArticleVendu> listeArticle = mgr.listeArticlesECByCatNom(noCategorie, filtre);
				
				
				
				if (listeArticle.isEmpty()) {
					listeVide = true;
					request.setAttribute("listeVide", listeVide);
					message ="Aucun article n'a été trouvé. Veuillez modifier le filtre et/ou les catégories ";
					request.setAttribute("messageListeVide", message);
				}
				
				request.setAttribute("listeAECCN", listeArticle);
				
			} catch (Exception e) {
				request.setAttribute("erreur", e);
			}
			
			RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/Page_d_accueil.jsp");
			rd.forward(request, response);
			
	}
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */

}
