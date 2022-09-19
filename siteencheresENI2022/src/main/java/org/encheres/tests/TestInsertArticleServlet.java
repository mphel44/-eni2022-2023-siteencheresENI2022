package org.encheres.tests;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDateTime;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.encheres.bo.ArticleVendu;
import org.encheres.bo.Retrait;
import org.encheres.dal.ArticleVenduDAO;
import org.encheres.dal.DALException;
import org.encheres.dal.DAOFactory;
import org.encheres.dal.jdbc.ConnectionProvider;

/**
 * Servlet implementation class TestInsertArticleServlet
 */
@WebServlet("/TestInsertArticleServlet")
public class TestInsertArticleServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public TestInsertArticleServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String message = "";
		ArticleVendu art = new ArticleVendu("tong", "chaussure de plage", LocalDateTime.parse("2022-11-20T13:45:30"), LocalDateTime.parse("2022-12-30T13:45:30"), 20, 20,"CR", "tong");
		Retrait retrait = new Retrait("snsjdfd","35350","St-Malo");
		
		try {
			ConnectionProvider.getConnection();
			message = "connexion établie";
			
			ArticleVenduDAO artDAO = DAOFactory.getArticleVenduDAO();
			
			try {
				artDAO.insert(art,retrait);
			} catch (DALException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		 } catch (SQLException e) {
	            e.printStackTrace();
	            message = e.getMessage();
		 }
		
		response.getWriter().append("Served at: ").append(message);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
}
