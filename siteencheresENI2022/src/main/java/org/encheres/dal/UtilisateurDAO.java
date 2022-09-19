package org.encheres.dal;

import org.encheres.bo.Utilisateur;

public interface UtilisateurDAO {
	
	public Utilisateur SelectByPseudoMdp(String pseudo, String mdp ) throws DALException;
	
	public Utilisateur insert (Utilisateur user) throws DALException ;
	
	public void delete (int id) throws DALException ;
	
	public Utilisateur updateUtilisateur(Utilisateur user) throws DALException ;
	
	public Utilisateur SelectById (int id) throws DALException ; 

	public Utilisateur SelectByPseudo(String pseudo ) throws DALException;

	public int selectCreditUser(int idUser) throws DALException ;
	
	public void changeCreditUser(int nouveauCredit, int idUser) throws DALException ;


}
