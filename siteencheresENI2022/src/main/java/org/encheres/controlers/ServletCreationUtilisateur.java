package org.encheres.controlers;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.encheres.bll.ArticleVenduManager;
import org.encheres.bll.BLLException;
import org.encheres.bll.UtilisateurManager;
import org.encheres.bo.ArticleVendu;
import org.encheres.bo.Utilisateur;
import org.encheres.dal.DALException;



/**
 * Servlet implementation class ServletCreation
 */
@WebServlet("/Creation")
public class ServletCreationUtilisateur extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ServletCreationUtilisateur() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/profil/InscriptionUtilisateur.jsp");
		rd.forward(request, response);	
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String pseudo = request.getParameter("pseudo");
		String nom = request.getParameter("nom");
		String prenom = request.getParameter("prenom");
		String email = request.getParameter("email");
		String telephone = request.getParameter("telephone");
		String rue = request.getParameter("rue");
		String codepostal = request.getParameter("codepostal");
		String ville = request.getParameter("ville");
		int credit = 100;
		String mdp = request.getParameter("mdp");
		String mdpconfirm = request.getParameter ("mdpconfir");
		Utilisateur user = null;
		boolean erreurdeconnexion = false;
		boolean erreurmessage = false;
		boolean premiereconnexion= false;
System.out.println(mdp);
System.out.println(mdpconfirm);
		if (!mdpconfirm.equals(mdp)) {
System.out.println("Les mots de passe sont différents");
			erreurmessage = true;
			String erreur = "Les deux mots de passe ne correspondent pas.";
			request.setAttribute("messageMdpDifferents", erreur);
			request.setAttribute("erreurMdpDifferents", erreurmessage);
			RequestDispatcher	rd = request.getRequestDispatcher("/WEB-INF/profil/InscriptionUtilisateur.jsp");
			rd.forward(request, response);
		} else {
			UtilisateurManager mgr = UtilisateurManager.getInstance();
			if (telephone.isEmpty()|| telephone==null) {
				user = new Utilisateur (pseudo,nom,prenom,email,rue,codepostal,ville,mdp,credit);
			} else {
				user = new Utilisateur (pseudo,nom,prenom,email,telephone,rue,codepostal,ville,mdp,credit);
			}			
			try {
				mgr.addUser(user);
				System.out.println("insertion réussie");
				premiereconnexion= true;
				request.setAttribute("premiereconnexion", premiereconnexion);
				String felicatation = "Vous avez crée votre fiche d'inscription. Veuillez vous connecter";
				request.setAttribute("felicitation", felicatation);
				//affichage liste
				ArticleVenduManager articleMgr = ArticleVenduManager.getInstance();
				try {
					List<ArticleVendu> listeArticleVendu = articleMgr.listeArticleVenteEC();
					request.setAttribute("listeAEC", listeArticleVendu);
					request.getSession().setAttribute("liste", listeArticleVendu);
				} catch (Exception e) {
					request.setAttribute("erreur", e);
				}
				//envoi requête
				RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/Page_d_accueil.jsp");
				rd.forward(request, response);	
			} catch ( BLLException e) {
				erreurdeconnexion = true;
				String message = "Pseudo et / ou email invalide. Veuillez les modifier.";
				request.setAttribute("erreurdeConnexion",erreurdeconnexion );
				request.setAttribute("message", message);
				ArticleVenduManager articleMgr = ArticleVenduManager.getInstance();
				try {
					List<ArticleVendu> listeArticleVendu = articleMgr.listeArticleVenteEC();
					request.setAttribute("listeAEC", listeArticleVendu);
					request.getSession().setAttribute("liste", listeArticleVendu);
				} catch (Exception exc) {
					request.setAttribute("erreur", exc);
				}
				RequestDispatcher	rd = request.getRequestDispatcher("/WEB-INF/profil/InscriptionUtilisateur.jsp");
				rd.forward(request, response);
			}	
		}	

	}
}
