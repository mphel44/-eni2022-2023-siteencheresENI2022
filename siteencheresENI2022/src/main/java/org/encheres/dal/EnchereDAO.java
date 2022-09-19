package org.encheres.dal;

import org.encheres.bo.Enchere;

public interface EnchereDAO {
	
	public void insertEnchere(Enchere enchereAjoutee) throws DALException ;
	
	public void deleteEnchere (int idArticle) throws DALException ;

}
