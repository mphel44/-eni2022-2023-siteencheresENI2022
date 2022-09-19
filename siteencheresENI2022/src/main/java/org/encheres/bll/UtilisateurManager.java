package org.encheres.bll;

import java.util.List;

import org.encheres.bo.Utilisateur;
import org.encheres.dal.DALException;
import org.encheres.dal.DAOFactory;
import org.encheres.dal.UtilisateurDAO;



public class UtilisateurManager {
	
	//attributs
	private static UtilisateurManager mngr;
	private UtilisateurDAO dao;
	
	//manager
	private UtilisateurManager() {
		dao = DAOFactory.getUtilisateurDAO();
	}
	
	//singleton
	public static UtilisateurManager getInstance () {
		if (mngr == null) {
			mngr = new UtilisateurManager();
		}
		return mngr;
	}
	
	//méthode utilisée pour la connection
	public Utilisateur getUserbyPseudoMdp(String pseudo,String mdp) throws BLLException {
		BLLException bllExceptions = new BLLException();
		
		if (pseudo == null || pseudo.isEmpty()) {
			Exception e = new Exception("Le pseudo est nécessaire !");
			bllExceptions.addException(e);
		}
		if (mdp == null || mdp.isEmpty()) {
			Exception e = new Exception("Le mot de passe est nécessaire !");
			bllExceptions.addException(e);
		}
		if (bllExceptions.isEmpty()) {
			try {
				return dao.SelectByPseudoMdp (pseudo,mdp);
			} catch (DALException e) {
					Exception ex = new Exception(e.getMessage());
					bllExceptions.addException(ex);
					throw bllExceptions;
			}
		} else {
			throw bllExceptions;
		}
	}
	public Utilisateur getUserbyID (int id) throws BLLException{
		BLLException bllException = new BLLException();
		try {
			return dao.SelectById(id);
		}catch (DALException e) {
			Exception ex = new Exception(e.getMessage());
			bllException.addException(ex);
			throw bllException;
		}
		
	}
	public Utilisateur getUserbyPseudo (String Pseudo) throws BLLException {
		BLLException bllException = new BLLException();
		try {
			return dao.SelectByPseudo(Pseudo);
		} catch (DALException e) {
			Exception ex = new Exception(e.getMessage());
			bllException.addException(ex);
			throw bllException;
		}
	}
	//méthode d'insertion d'un utilisateur
	public Utilisateur addUser(Utilisateur user) throws BLLException {
		BLLException bllExceptions = new BLLException();
		try {
			if (user.getPseudo() == null || user.getPseudo().isEmpty()) {
				Exception e = new Exception("Le pseudo est nécessaire !");
				bllExceptions.addException(e);
				throw new BLLException("Erreur, le pseudo est nécessaire");
			}
			if (user.getNom() == null || user.getNom().isEmpty()) {
				Exception e = new Exception("Le nom est nécessaire !");
				bllExceptions.addException(e);
				throw new BLLException("Erreur, le nom est nécessaire");
			}
			if (user.getPrenom() == null || user.getPrenom().isEmpty()) {
				Exception e = new Exception("Le prenom est nécessaire !");
				bllExceptions.addException(e);
				throw new BLLException("Erreur, le prenom est nécessaire");
			}
			if (user.getEmail() == null || user.getEmail().isEmpty()) {
				Exception e = new Exception("L'email est nécessaire !");
				bllExceptions.addException(e);
				throw new BLLException("Erreur, l'email est nécessaire");
			}
			if (user.getRue() == null || user.getRue().isEmpty()) {
				Exception e = new Exception("Le nom de la rue est nécessaire !");
				bllExceptions.addException(e);
				throw new BLLException("Erreur, le nom est nécessaire");
			}
			if (user.getCodePostal() == null || user.getCodePostal().isEmpty()) {
				Exception e = new Exception("Le code postal est nécessaire !");
				bllExceptions.addException(e);
				throw new BLLException("Erreur, le code postal est nécessaire");
			}
			if (user.getVille() == null || user.getVille().isEmpty()) {
				Exception e = new Exception("La ville est nécessaire !");
				bllExceptions.addException(e);
				throw new BLLException("Erreur, la ville est nécessaire");
			}
			if (user.getMotDePasse() == null || user.getMotDePasse().isEmpty()) {
				Exception e = new Exception("Le mot de passe est nécessaire !");
				bllExceptions.addException(e);
				throw new BLLException("Erreur, le mdp est nécessaire");
			}
			return dao.insert(user);
		} catch (DALException e) {
				Exception ex = new Exception(e.getMessage());
				bllExceptions.addException(ex);
				throw bllExceptions;
		}
	}

	//méthode de suppression d'un utilisateur
	public void deleteUtilisateur(int idUtilisateur) throws BLLException {
		BLLException bllExceptions = new BLLException();
		try {
			dao.delete(idUtilisateur);
		} catch (DALException e) {
			Exception ex = new Exception("Erreur lors de la suppression de l'utilisateur " + e.getMessage());
			bllExceptions.addException(ex);
			throw bllExceptions;
		}
	}
	
	//méthode de modification d'un utilisateur
	public Utilisateur updateUtilisateur(Utilisateur user) throws BLLException {
		BLLException bllExceptions = new BLLException();
		try {
			if (user.getPseudo() == null || user.getPseudo().isEmpty()) {
				bllExceptions.addException(new Exception("Le pseudo est nécessaire. "));
			}
			if (user.getNom() == null || user.getNom().isEmpty()) {
				bllExceptions.addException(new Exception("Le nom est nécessaire. "));
			}
			if (user.getPrenom() == null || user.getPrenom().isEmpty()) {
				bllExceptions.addException(new Exception("Le prenom est nécessaire. "));
			}
			if (user.getEmail() == null || user.getEmail().isEmpty()) {
				bllExceptions.addException(new Exception("L'email est nécessaire. "));
			}
			if (user.getRue() == null || user.getRue().isEmpty()) {
				Exception e = new Exception("Le nom de la rue est nécessaire. ");
				bllExceptions.addException(e);
			}
			if (user.getCodePostal() == null || user.getCodePostal().isEmpty()) {
				bllExceptions.addException(new Exception("Le code postal est nécessaire. "));
			}
			if (user.getVille() == null || user.getVille().isEmpty()) {
				bllExceptions.addException(new Exception("La ville est nécessaire. "));
			}
			if (user.getMotDePasse() == null || user.getMotDePasse().isEmpty()) {
				bllExceptions.addException(new Exception("Le mot de passe est nécessaire. "));
			}
			if(!bllExceptions.isEmpty()){
				throw bllExceptions;
			}
			user = dao.updateUtilisateur(user);
		} catch (DALException e) {
			bllExceptions.addException(new Exception("Erreur lors de la modification de l'utilisateur " + e.getMessage()));
			throw bllExceptions;
		}
		return user ;
	}
	
	
	public int selectCreditUser(int idUser) throws BLLException {
		BLLException bllExceptions = new BLLException();
		int credit = 0 ; 
		try {
			
			credit = dao.selectCreditUser(idUser);
		} catch (DALException e) {
			bllExceptions.addException(new Exception("Erreur lors de la sélection du crédit de l'utilisateur. " + e.getMessage()));
			throw bllExceptions;
		}
		return credit ;
	}
	
	public void changeCreditUser(int nouveauCredit, int idUser) throws BLLException {
		BLLException bllExceptions = new BLLException();
		try {
			 dao.changeCreditUser(nouveauCredit, idUser);
		} catch (DALException e) {
			bllExceptions.addException(new Exception("Erreur lors de la modification du crédit de l'utilisateur " + e.getMessage()));
			throw bllExceptions;
		}
	}
	
}
