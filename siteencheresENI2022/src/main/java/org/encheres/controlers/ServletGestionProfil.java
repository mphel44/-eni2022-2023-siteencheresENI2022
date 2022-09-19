package org.encheres.controlers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.encheres.bll.BLLException;
import org.encheres.bll.UtilisateurManager;
import org.encheres.bo.Utilisateur;

/**
 * Servlet implementation class ServletGestionProfil
 */
@WebServlet("/profil/servlet")
public class ServletGestionProfil extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ServletGestionProfil() {
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
		request.getRequestDispatcher("/WEB-INF/profil/gestionprofil.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Utilisateur userModif = (Utilisateur) request.getSession().getAttribute("utilisateurConnecte") ; 
		int idUser = userModif.getNoUtilisateur();
		int creditUser = userModif.getCredit();
		boolean statutUser = userModif.isAdministrateur();
		if (!request.getParameter("mdp").equals(request.getParameter("mdpconfir"))) {
			boolean erreurMdpDiff = true ;
			request.setAttribute("erreurMdpDiff", erreurMdpDiff);
			request.setAttribute("messErrMdpDiff", "Les mots de passe doivent être identiques");
			this.doGet(request, response);
		} else {
			userModif= new Utilisateur(idUser,
					request.getParameter("pseudo"),
					request.getParameter("nom"),
					request.getParameter("prenom"), 
					request.getParameter("email"),
					request.getParameter("telephone"),
					request.getParameter("rue"),
					request.getParameter("codepostal"),
					request.getParameter("ville"),
					request.getParameter("mdp"),
					creditUser,
					statutUser);
			UtilisateurManager mgr = UtilisateurManager.getInstance();
			try {
				userModif = mgr.updateUtilisateur(userModif) ;
				boolean succesModif = true ;
				request.getSession().setAttribute("utilisateurConnecte", userModif);
				request.setAttribute("succesModif", succesModif);
				request.setAttribute("messSucces", "Modifications effectuées avec succès");
				this.doGet(request, response);
				
			} catch (BLLException e) {
				List<Exception> listeErrModifUtil = e.getBllExceptions();
				boolean erreurUpdate = true ;
				request.setAttribute("erreurUpdate", erreurUpdate);
				request.setAttribute("listeErrModifUtil", listeErrModifUtil);
				this.doGet(request, response);
			}
		}
	}
}
