package org.encheres.controlers;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.encheres.bll.BLLException;
import org.encheres.bll.UtilisateurManager;
import org.encheres.bo.Utilisateur;

/**
 * Servlet implementation class ServletSupprimerUtilisateur
 */
@WebServlet("/SupprimerUtilisateur")
public class ServletSupprimerUtilisateur extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ServletSupprimerUtilisateur() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Utilisateur userDelete = (Utilisateur) request.getSession().getAttribute("utilisateurConnecte");
		int idUserDelete = userDelete.getNoUtilisateur();
		System.out.println("id utilisateur = " + idUserDelete);
		UtilisateurManager mgr = UtilisateurManager.getInstance();
		
		
		try {
			
			mgr.deleteUtilisateur(idUserDelete);
			boolean statutDeConnexion = false ;
			boolean Usuppr = true;
			request.getSession().setAttribute("statutDeConnexion", statutDeConnexion);
			request.getSession().invalidate();
			String message ="Votre profil a été supprimé avec succès";
			request.setAttribute("SupprProfil", Usuppr);
			request.setAttribute("messageSuppr", message);
			RequestDispatcher	rd = request.getRequestDispatcher("/WEB-INF/Page_d_accueil.jsp");
			rd.forward(request, response);
			
		} catch (BLLException e) {
			boolean erreurDeleteUser = true;
			List<Exception> listeErrModifUtil = e.getBllExceptions();
			request.setAttribute("erreurDeleteUser", erreurDeleteUser);
			request.setAttribute("listeErrModifUtil", listeErrModifUtil);
			RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/profil/afficherProfil.jsp");
			rd.forward(request, response);
		}
		
	}

}
