package org.encheres.listeners;

import java.util.List;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import org.encheres.bll.ArticleVenduManager;
import org.encheres.bll.BLLException;

/**
 * Application Lifecycle Listener implementation class UpdateStatutArticles
 *
 */
@WebListener
public class UpdateStatutArticles implements ServletContextListener {
	private Thread task ;
    /**
     * Default constructor. 
     */
    public UpdateStatutArticles() {
        // TODO Auto-generated constructor stub
    }

	/**
     * @see ServletContextListener#contextDestroyed(ServletContextEvent)
     */
    public void contextDestroyed(ServletContextEvent sce)  { 
        if (task != null && task.isAlive()) {
       	 task.interrupt();
       	 System.out.println("Traitement stoppé");
        }    
    }

	/**
     * @see ServletContextListener#contextInitialized(ServletContextEvent)
     */
    public void contextInitialized(ServletContextEvent sce)  { 
    	task = new Thread (new Runnable() {
		@Override
		public void run() {
			try {
				System.out.println("Procédure d'update des enchères lancée");
				ArticleVenduManager articleMgr = ArticleVenduManager.getInstance(); 
				while(!task.isInterrupted()) {
					try {
						articleMgr.executeProcedureStockee();
					} catch (BLLException e) {
						List<Exception> listeErrModifUtil = e.getBllExceptions();
						for (Exception exception : listeErrModifUtil) {
							System.out.println(exception.getMessage());
						}
					}
					Thread.sleep(60000); 
				}
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
	}) ;
		
	task.start();    
    }
}
