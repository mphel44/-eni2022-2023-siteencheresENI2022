
package org.encheres.tests;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.encheres.bll.BLLException;
import org.encheres.bll.UtilisateurManager;
import org.encheres.bo.Utilisateur;
import org.encheres.dal.DALException;



/**
 * Servlet implementation class TesterBLL
 */
@WebServlet("/TesterBLL")
public class TesterBLL extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public TesterBLL() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		UtilisateurManager mngr = UtilisateurManager.getInstance();	
		try {
			Utilisateur userTest = mngr.getUserbyPseudoMdp("Titi","");
			System.out.println("Il est présent");
			System.out.println(userTest);
		//	System.out.println(mngr.getUsreByMailPwd("mlgy@gmail.com", "Pa$$w0rd"));
		} catch (BLLException e) {
			//System.err.println("ERREUR AU STADE DE LA SEVLET");
			//e.getMessage();
			//e.getBllExceptions();
			List<Exception> listeException = e.getBllExceptions(); 
			for (Exception exceptions : listeException) {
				System.out.println(exceptions);
			}
			e.printStackTrace();

		}

	}
}


