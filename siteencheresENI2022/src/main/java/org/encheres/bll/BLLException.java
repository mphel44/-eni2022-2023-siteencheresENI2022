package org.encheres.bll;

import java.util.ArrayList;
import java.util.List;

public class BLLException extends Exception {
	
	//la classe gère une liste de messages d'erreur
	
	private List<Exception> bllExceptions = new ArrayList<Exception>() ;

	public BLLException() {
		// TODO Auto-generated constructor stub
	}
	
	public BLLException(String message) {
		super(message);
	}
	
	public BLLException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	public void addException(Exception e) {
		bllExceptions.add(e);
	}
	
	public boolean isEmpty() {
		return bllExceptions.size() > 0 ? false : true;
	}

	public List<Exception> getBllExceptions() {
		return bllExceptions;
	}
}
