package org.encheres.tests;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.encheres.bo.ArticleVendu;
import org.encheres.bo.Utilisateur;
import org.encheres.dal.ArticleVenduDAO;
import org.encheres.dal.DAOFactory;
import org.encheres.dal.UtilisateurDAO;
import org.encheres.dal.jdbc.ConnectionProvider;

import org.encheres.dal.jdbc.UtilisateurDAOJdbcImpl;


/**
 * Servlet implementation class TesterPoolConnection
 */
@WebServlet("/TesterPoolConnection")
public class ServletTest extends HttpServlet {
    private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ServletTest() {
        super();
        // TODO Auto-generated constructor stub
    }
    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Tester la connexion à la base de données
        String message="";
       // Utilisateur user1 = new Utilisateur("Tutu","Durand","turlu","prgmailcom","02458","ffran","45056","Melin","1856",12, true);
        try {
            ConnectionProvider.getConnection();
            message = "connexion établie";
           
            UtilisateurDAO dao = DAOFactory.getUtilisateurDAO();
            ArticleVenduDAO Vdao = DAOFactory.getArticleVenduDAO();
          //  dao.insert(user1);
         
          //  user1 = dao.SelectByPseudoMdp("Titi", "1908");
          //  System.out.println(user1.toString());
          System.out.println(dao.SelectByPseudo("tom")); 
//          List <ArticleVendu> nouvelleListe = Vdao.selectAllEncheresEnCours();
//          System.out.println(nouvelleListe.get(1));
          List <ArticleVendu>nouvelleListemodif = Vdao.selectByCatNomArt(1, "article");
          System.out.println(nouvelleListemodif);
          
           // dao.delete(59);
        } catch (SQLException e) {
            e.printStackTrace();
            message = e.getMessage();
        } catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//<<<<<<< HEAD
//        response.getWriter().append("Test de connexion: ").append(message).append(user.toString());
//=======
//        response.getWriter().append("Test de connexion: ").append(user1.toString());
//>>>>>>> branch 'main' of https://github.com/mpheleni2022/siteencheres2022.git
    }
}