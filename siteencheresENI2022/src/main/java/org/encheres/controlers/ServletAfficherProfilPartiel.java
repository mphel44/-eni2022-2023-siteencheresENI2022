package org.encheres.controlers;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
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
 * Servlet implementation class ServletAfficherProfilPartiel
 */
@WebServlet("/AfficherProfilPartiel")
public class ServletAfficherProfilPartiel extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ServletAfficherProfilPartiel() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		Utilisateur utilisateur = (Utilisateur)request.getSession().getAttribute("utilisateurConnecte");
		boolean userConnectePageProfilPartiel = false ; 
		if (utilisateur!= null) {
			userConnectePageProfilPartiel= true; 
		}
		request.setAttribute("userConnectePageProfilPartiel", userConnectePageProfilPartiel);
		Utilisateur user = new Utilisateur();

		UtilisateurManager mgr = UtilisateurManager.getInstance();
		String Pseudo= request.getParameter("userT");
		try {
			user = mgr.getUserbyPseudo(Pseudo);
			request.setAttribute("profil", user);
		} catch (BLLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	//	Utilisateur user = (Utilisateur)test;
		
		request.getRequestDispatcher("/WEB-INF/profil/afficherProfilPartiel.jsp").forward(request, response);
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
