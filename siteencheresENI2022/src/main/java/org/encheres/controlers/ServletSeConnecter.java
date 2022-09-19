package org.encheres.controlers;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.encheres.bll.ArticleVenduManager;
import org.encheres.bll.BLLException;
import org.encheres.bll.UtilisateurManager;
import org.encheres.bo.ArticleVendu;
import org.encheres.bo.Utilisateur;

/**
 * Servlet implementation class ServletSeconnecter
 */
@WebServlet("/Connecter")
public class ServletSeConnecter extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ServletSeConnecter() {
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
		// TODO Auto-generated method stub
		RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/profil/Page_Login.jsp");
		rd.forward(request, response);	
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//boolean gestion affichage des boutons
		Boolean statutDeConnexion = false ;
		Boolean statutAdministrateur = false ;
		String pseudo = request.getParameter("pseudo");
		String mdp = request.getParameter("password");
		HttpSession session = request.getSession();
		boolean erreurdeconnexion = false;
		//Instance de navigation vers le Manager
		UtilisateurManager mgr = UtilisateurManager.getInstance() ;
		
		try {
			Utilisateur user = mgr.getUserbyPseudoMdp(pseudo, mdp);
			if (user != null) {
				request.setAttribute("connexionReussie", "Vous êtes connecté !");
				if (user.getCredit()!=0) {
					String creditString = String.valueOf(user.getCredit());
					session.setAttribute("creditString",creditString);
				}
				session.setAttribute("utilisateurConnecte",user);
				statutAdministrateur = user.isAdministrateur();
				request.getSession().setAttribute("statutAdministrateur", statutAdministrateur); 
				statutDeConnexion = true ; 
				boolean checkConnexionReussie = true ;
				request.setAttribute("checkConnexionReussie", checkConnexionReussie);
				request.getSession().setAttribute("statutDeConnexion", statutDeConnexion);
				//mise en place de la liste des articles
				ArticleVenduManager articleManager = ArticleVenduManager.getInstance();
				try {
					List<ArticleVendu> listeArticleVendu = articleManager.listeArticleVenteEC();
					request.setAttribute("listeConnecte", listeArticleVendu);
				} catch (Exception e) {
					request.setAttribute("erreur", e);
				}
				
				RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/Page_d_accueil.jsp");
				rd.forward(request, response);
			} else {
			erreurdeconnexion = true;
				request.setAttribute("erreurdeconnexion", erreurdeconnexion);
				String message = "Pseudo et / ou mot de passe inconnu. Le cas échéant, veuillez créer un compte.";
				request.setAttribute("erreur", message);
				RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/profil/Page_Login.jsp");
				rd.forward(request, response);
			}
		
		} catch (BLLException e) {
			erreurdeconnexion = true;
			request.setAttribute("erreurdeconnexion", erreurdeconnexion);
			String message = "Pseudo et / ou mot de passe inconnu. Le cas échéant, veuillez créer un compte.";
			request.setAttribute("erreur", message);
			RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/profil/Page_Login.jsp");
			rd.forward(request, response);
		}
	}
}
