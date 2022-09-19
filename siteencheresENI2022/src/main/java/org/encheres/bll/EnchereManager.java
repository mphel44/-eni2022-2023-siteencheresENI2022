package org.encheres.bll;

import org.encheres.bo.Enchere;
import org.encheres.dal.DALException;
import org.encheres.dal.DAOFactory;
import org.encheres.dal.EnchereDAO;

public class EnchereManager {

	//attributs
	private static EnchereManager mngr ; 
	private EnchereDAO enchereDAO ;
	
	//constructeur prive
	private EnchereManager() {
		enchereDAO = DAOFactory.getEnchereDAO();
	}
	
	//singleton
	public static EnchereManager getInstance() {
		if (mngr == null) {
			mngr = new EnchereManager() ;
		}
		return mngr ; 
	}
	
	public void addEnchere(Enchere enchere) throws BLLException {
		BLLException bllExceptions = new BLLException() ;
		try  {
			if (enchere.getDateEnchere() == null) {
				bllExceptions.addException(new Exception("La date doit être complétée. "));
			}
			if (enchere.getMontantEnchere()==0) {
				bllExceptions.addException(new Exception("Le montant de l'enchère doit être complété. "));
			}
			if(!bllExceptions.isEmpty()){
				throw bllExceptions;
			}
			enchereDAO.insertEnchere(enchere);
		} catch (DALException e){
			bllExceptions.addException(new Exception("BLL - Erreur lors de l'ajout de l'enchère : " + e.getMessage()));
			throw bllExceptions;
		}
	}
	
	public void deleteEnchere(int idArticle) throws BLLException {
		BLLException bllExceptions = new BLLException() ;
		try {
			enchereDAO.deleteEnchere(idArticle);
		} catch (DALException e){
			bllExceptions.addException(new Exception("BLL - Erreur lors de la suppression de l'enchère : " + e.getMessage()));
			throw bllExceptions;
		}		
	}
}
