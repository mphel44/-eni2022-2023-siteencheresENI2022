package org.encheres.controlers;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.encheres.bll.ArticleVenduManager;
import org.encheres.bll.BLLException;
import org.encheres.bo.ArticleVendu;
import org.encheres.bo.Retrait;
import org.encheres.bo.Utilisateur;

/**
 * Servlet implementation class ServletCreationVente
 */
@WebServlet("/add/sales")
public class ServletCreationVente extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ServletCreationVente() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/article/CreationVente.jsp");
		rd.forward(request, response);

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		Utilisateur user = (Utilisateur)request.getSession().getAttribute("utilisateurConnecte");
		
			//String rue = user.getRue();
			//request.getSession().setAttribute("rue", rue);
			//String codePostal = user.getCodePostal();
			//request.getSession().setAttribute("codePostal", codePostal);
			//String ville = user.getVille();
			//request.getSession().setAttribute("ville", ville);
		
		String nom = request.getParameter("name");
		String description = request.getParameter("description");
			//String dateD = request.getParameter("start");
			//String dateH = "T" + request.getParameter("startTime");
			//System.out.println(dateD);
			//System.out.println(dateH);
		LocalDateTime dateDebut = null;
		LocalDateTime dateFin = null;
		int prixInitial = 0;
		int prixVente = 0;
		
		try {
			String dateD = request.getParameter("start");
			String heureD = request.getParameter("startTime");
			String dateHeureDebut = request.getParameter("start") + "T" + request.getParameter("startTime");
			dateDebut = LocalDateTime.parse(dateHeureDebut);
			String dateF = request.getParameter("end");
			String heureFin = request.getParameter("endTime");
			String dateHeureFin = request.getParameter("end") + "T" + request.getParameter("endTime");
			dateFin = LocalDateTime.parse(dateHeureFin);
			
			prixInitial = Integer.parseInt(request.getParameter("price"));
			prixVente = 0;
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			int noUtilisateur = user.getNoUtilisateur();
			String categorie = request.getParameter("categorie");
			int noCategorie = 0;
				if (categorie.equals("Informatique")) {
					noCategorie = 1;	
				} else if (categorie.equals("Ameublement")) {
					noCategorie = 2;	
				} else if (categorie.equals("Vêtement")) {
					noCategorie = 3;
				} else if (categorie.equals("Sport & Loisir")) {
					noCategorie = 4;
				}
			
		String etat = "CR";
		String image = null;
		
		String rue = request.getParameter("street");
		String codePostal = request.getParameter("postcode");
		String ville = request.getParameter("town");
		
		ArticleVendu article = new ArticleVendu(nom, description, dateDebut, dateFin, prixInitial, prixVente, noUtilisateur, noCategorie, etat, image);
		Retrait retrait = new Retrait(rue, codePostal, ville);
		
		ArticleVenduManager mngr = ArticleVenduManager.getInstance();
		
		try {
			mngr.addArticle(article,retrait);
			boolean ok = true;
			request.setAttribute("ok", ok);
			request.setAttribute("validation", "Votre nouvelle vente a été créée avec succés");
			this.doGet(request, response);
			
		} catch (BLLException e) {
			List <Exception> listeErreurs = e.getBllExceptions() ;
				for (Exception exp : listeErreurs) {
					System.out.println(exp.getMessage());
				}
			boolean erreur = true;
			request.setAttribute("erreur", erreur);
			request.setAttribute("listeErreurs", listeErreurs);
			this.doGet(request, response);
		}
		
	}

}
