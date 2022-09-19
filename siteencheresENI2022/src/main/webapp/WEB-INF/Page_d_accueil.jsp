<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>  
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>


<html lang="fr" xmlns:mso="urn:schemas-microsoft-com:office:office" xmlns:msdt="uuid:C2F41010-65B3-11d1-A29F-00AA00C14882">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    
    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css" integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">
    
    <!-- Personnal CSS-->
   <link href="${pageContext.request.contextPath}/css/style.css" rel="stylesheet">

    <!--icons-->
    <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.7.0/css/all.css" integrity="sha384-lZN37f5QGtY3VHgisS14W3ExzMWZxybE1SJSEsQp9S+oqd12jhcu+A56Ebc1zFSJ" crossorigin="anonymous">

    <title>ENI-Encheres : Liste des enchères</title>

<!--[if gte mso 9]><xml>
<mso:CustomDocumentProperties>
<mso:xd_Signature msdt:dt="string"></mso:xd_Signature>
<mso:display_urn_x003a_schemas-microsoft-com_x003a_office_x003a_office_x0023_Editor msdt:dt="string">Bruno MARTIN</mso:display_urn_x003a_schemas-microsoft-com_x003a_office_x003a_office_x0023_Editor>
<mso:Order msdt:dt="string">493100.000000000</mso:Order>
<mso:xd_ProgID msdt:dt="string"></mso:xd_ProgID>
<mso:_ExtendedDescription msdt:dt="string"></mso:_ExtendedDescription>
<mso:SharedWithUsers msdt:dt="string"></mso:SharedWithUsers>
<mso:display_urn_x003a_schemas-microsoft-com_x003a_office_x003a_office_x0023_Author msdt:dt="string">Bruno MARTIN</mso:display_urn_x003a_schemas-microsoft-com_x003a_office_x003a_office_x0023_Author>
<mso:ComplianceAssetId msdt:dt="string"></mso:ComplianceAssetId>
<mso:TemplateUrl msdt:dt="string"></mso:TemplateUrl>
<mso:ContentTypeId msdt:dt="string">0x010100263DB1995E0D954B97BE6C60AEAEE054</mso:ContentTypeId>
<mso:TriggerFlowInfo msdt:dt="string"></mso:TriggerFlowInfo>
<mso:_SourceUrl msdt:dt="string"></mso:_SourceUrl>
<mso:_SharedFileIndex msdt:dt="string"></mso:_SharedFileIndex>
<mso:MediaLengthInSeconds msdt:dt="string"></mso:MediaLengthInSeconds>
</mso:CustomDocumentProperties>
</xml><![endif]-->
</head>
<body>
    <div class="container-fluid">
        <!--fullHeader-->
        <header>
            <nav class="pr-5 navbar navbar-expand-sm bg-dark navbar-dark align-top justify-content-between">
                <!-- Brand/logo -->
                <form action="${pageContext.request.contextPath}/servlet/accueil/connected" method = "get">
                        <input type = "image" src="${pageContext.request.contextPath}/img/logoEni.png" alt="Accueil ENI-Encheres" value = "Retour a l'acueil" class ="bouton" >
                    </form>
                <a class="navbar-brand">
                    <strong class ="string">ENI-Encheres</strong>
                </a>
	
                <a class="navbar-brand" href="#" alt="Gérer mon profil" title="Gérer mon profil">   
                    <span class="align-middle text-muted"> 	
                   		${sessionScope.utilisateurConnecte.getNom()!= null? sessionScope.utilisateurConnecte.getNom(): ""}									
				 		${sessionScope.utilisateurConnecte.getPrenom()!= null? sessionScope.utilisateurConnecte.getPrenom(): ""} 
				 		${sessionScope.utilisateurConnecte.getCredit()!= null ? sessionScope.utilisateurConnecte.afficherCredit() : ""} 
				 	</span>
                </a>

                <ul class="navbar-nav ml-auto">
                    <!-- Dropdown for small screen -->
                    <li class="nav-item dropdown d-lg-none">
                        <a class="nav-link dropdown-toggle" href="#" id="navbardrop" data-toggle="dropdown">
<!--                             <img class="small-icon" src="images/menu.svg" alt="Menu ENI-Encheres">
 -->                        </a>
                        <div class="dropdown-menu">
                            <c:if test ="${!sessionScope.statutDeConnexion}">
                   				<a class="dropdown-item" href="${pageContext.request.contextPath}/Creation">M'inscrire</a>
                            	<a class="dropdown-item" href="${pageContext.request.contextPath}/Connecter" alt="Se connecter à ENI-Encheres">Me connecter</a>
							</c:if> 
							<c:if test ="${sessionScope.statutDeConnexion}">
							    <a class="dropdown-item" href="${pageContext.request.contextPath}/add/sales" alt="Vendre un article">Vendre un article</a>
							   	<a class="dropdown-item" href="${pageContext.request.contextPath}/profil/AfficherP" alt="Mon profil">Mon profil</a>
							    <a class="dropdown-item" href="${pageContext.request.contextPath}/disconnection/servlet" alt="Me déconnecter">Me déconnecter</a>
							    
							    <c:if test ="${sessionScope.statutAdministrateur}">
                           			<a class="dropdown-item" href="#" alt="Administrer le site">Administrer</a> 
                           		</c:if> 
							</c:if> 
                        </div>	
                    </li>  
              
                    <!-- Links for medium screen-->                  
                     <c:if test ="${!statutDeConnexion}">
	                    <li class="nav-item d-none d-lg-block">
	                        <a class="nav-link" href="${pageContext.request.contextPath}/Creation">M'inscrire</a>
	                    </li>
	                    <li class="nav-item d-none d-lg-block">
	                        <a class="nav-link" href="${pageContext.request.contextPath}/Connecter" alt="Se connecter à ENI-Encheres">Me connecter</a>
	                    </li>
	                </c:if> 
	               	<c:if test ="${statutDeConnexion}">
	               	
		               	<li class="nav-item d-none d-lg-block">
	                        <a class="nav-link" href="${pageContext.request.contextPath}/add/sales" alt="Vendre un article">Vendre un article</a>
	                    </li> 
	               		<li class="nav-item d-none d-lg-block">
	                        <a class="nav-link" href="${pageContext.request.contextPath}/profil/AfficherP" alt="Mon profil">Mon profil</a>
	                    </li>
	                    <li class="nav-item d-none d-lg-block">
                        	<a class="nav-link" href="${pageContext.request.contextPath}/disconnection/servlet" alt="Me déconnecter">Me déconnecter</a>
                    	</li>
                    	
                    	<c:if test ="${statutAdministrateur}">
		                    <li class="nav-item d-none d-lg-block">
		                        <a class="nav-link" href="#" alt="Administrer le site">Administrer</a>
		                    </li> 
	                	</c:if> 
	              	</c:if> 
                </ul>
            </nav>
        </header>
      
        <!--main bloc-->
   <main>
	   <!--title-->
	   <div class="mx-auto text-center">
	       <h1>Enchères</h1>
	   </div>
            <c:if test="${SupprProfil }">
	            <div class="d-flex alert-success">
			     		<ul class="col-6 list-unstyled p-2 text-center offset-3">
			     		
			         		<li>${messageSuppr}</li>
			       
			    		</ul>
				</div> 
            </c:if>
		<!--Connexion réussie-->
			<c:if test ="${requestScope.checkConnexionReussie}">
				<div class="d-flex alert-success ">
			        <ul class="col-6 list-unstyled p-2 text-center offset-3">
		         		<li>${requestScope.connexionReussie}</li>
		    		</ul>
				</div> 
			</c:if>
		
	            
		<!--Inscription réussie-->
		<div>
			<c:if test ="${requestScope.premiereconnexion}">
				<div class="d-flex alert-success text-center">
			        <ul class="col-6 list-unstyled p-2 text-center offset-3">
		         		<li>${requestScope.felicitation}</li>
		    		</ul>
				</div> 
			</c:if>
		</div>
		
      <!--FORMULAIRE FILTRE SI DECONNECTE-->      
      
      <c:if test="${!sessionScope.statutDeConnexion}">
      
      
      
      <form class="form-filter  mb-3" action="${pageContext.request.contextPath}/Accueillir" method="post">
                <div class="row">
                    <!--Partie gauche-->
                    <div class="col-md-6 mb-3">
                        <div class="form-group">
                                <label for="filter-input">Filtre</label>
                                <input type="text" class="form-control" id="filter-input" name="filtre" placeholder="articles contenant..." required>
                        </div>
                        <div class="form-group">
                            <label for="categories-select">Catégories</label>
                            <select class="form-control" id="categories-select" name="categorie">
                                <option selected name = "categorie" value = "AucuneSelection">Toutes</option>
                                <option name="categorie" value="Informatique">Informatique</option>
                                <option name="categorie" value="Ameublement">Ameublement</option>
                                <option name="categorie" value="Vêtement">Vêtement</option>
                                <option name="categorie" value="Sport&Loisirs">Sport & Loisirs</option>
                            </select>
                        </div>
		                <c:if test="${erreurCategorie}">
							<div class=" alert-danger text-center col-md-12 ">   		
							     ${erreurCat}
							</div> 
		        		</c:if> 
                    </div>
                 <div>
                 
		</div>
		  
                    <!--Partie droite-->
                    
                    <div class="col-md-6 mb-3">  	
                        <div class="form-check">
	                        <c:if test="${sessionScope.statutDeConnexion}">
	                            <label class="form-check-label">
	                                <input type="radio" class="form-check-input" checked name="type-encheres" value="achats" id="achats">Achats
	                            </label>
	                        </c:if>
                        </div>
                        <div class="form-group">
                            <div class="form-check">
                            <c:if test="${sessionScope.statutDeConnexion}">
                                <label class="form-check-label">
                                    <input type="checkbox" class="form-check-input" checked name="encheres" value="ouvertes" id="ouvertes">Enchères ouvertes
                                </label>
                            </c:if>
                            </div>
                            <div class="form-check">
                               <c:if test="${sessionScope.statutDeConnexion}">
                                <label class="form-check-label">
                                    <input type="checkbox" class="form-check-input" name="encheres" value="encours" id="encours">Mes enchères en cours
                                </label>
                                </c:if>
                            </div>
                            <div class="form-check">
                               <c:if test="${sessionScope.statutDeConnexion}">
                                <label class="form-check-label">
                                    <input type="checkbox" class="form-check-input" name="encheres" value="remportees" id="remportees">Mes enchères remportées
                                </label>
                                </c:if>
                            </div>
                        </div>
                        <div class="form-check">
                           <c:if test="${sessionScope.statutDeConnexion}">
                            <label class="form-check-label">
                                <input type="radio" class="form-check-input" name="type-encheres" value="ventes" id="ventes">Ventes
                            </label>
                            </c:if>
                        </div>
                        <div class="form-group">
                           <c:if test="${sessionScope.statutDeConnexion}">
                            	<div class="form-check">
	                                <label class="form-check-label">
	                                    <input type="checkbox" class="form-check-input" name="ventes" value="venteencours" id="venteencours">Mes ventes en cours
	                                </label>
	                             </div>
                           </c:if>
                           <c:if test="${sessionScope.statutDeConnexion}">
	                            <div class="form-check">
	                                <label class="form-check-label">
	                                    <input type="checkbox" class="form-check-input" name="ventes" value="nondebutees" id="nondebutees">Mes ventes non débutées
	                                </label>
	                            </div>
                          	</c:if>
                           <c:if test="${sessionScope.statutDeConnexion}">
	                            <div class="form-check">
	                                <label class="form-check-label">
	                                    <input type="checkbox" class="form-check-input" name="ventes" value="terminees" id="terminees">Mes ventes terminées
	                                </label>
	                            </div>
                            </c:if>
                       </div>
                     </div>
                
                
	                <button class="btn btn-primary btn-lg btn-block col-10 offset-1" type="submit" value= "Rechercher"> Rechercher
	<!--            <img class="small-icon" src="images/search.svg" alt="Eni Ecole">
	 -->            </button>
              </div> 
    	</form>
        </c:if>
        
      <!--FORMULAIRE FILTRE SI CONNECTE-->   
      
      <c:if test="${sessionScope.statutDeConnexion}">
      <form class="form-filter  mb-3" action="${pageContext.request.contextPath}/servlet/accueil/connected" method="post">
                <div class="row">
                    <!--Partie gauche-->
                    <div class="col-md-6 mb-3">
                        <div class="form-group">
                                <label for="filter-input">Filtre</label>
                                <input type="text" class="form-control" id="filter-input" name="filtre" placeholder="articles contenant...">
                        </div>
                        <div class="form-group">
                            <label for="categories-select">Catégories</label>
                            <select class="form-control" id="categories-select" name="categorie">
                                <option selected name = "categorie" value = "AucuneSelection">Toutes</option>
                                <option name="categorie" value="Informatique">Informatique</option>
                                <option name="categorie" value="Ameublement">Ameublement</option>
                                <option name="categorie" value="Vêtement">Vêtement</option>
                                <option name="categorie" value="Sport&Loisirs">Sport & Loisirs</option>
                            </select>
                        </div>
                       
                    </div>
                    
                    <!--Partie droite-->
                    
                    <div class="col-md-6 mb-3">  	
                        <div class="form-check">
	                            <label class="form-check-label">
	                                <input type="radio" class="form-check-input" checked name="type-encheres" value="achats" id="achats">Achats
	                            </label>
                        </div>
                        <div class="form-group">
                            <div class="form-check">
                                <label class="form-check-label">
                                    <input type="checkbox" class="form-check-input" name="encheres" value="ouvertes" id="ouvertes">Enchères ouvertes
                                </label>
                            </div>
                            <div class="form-check">
                                <label class="form-check-label">
                                    <input type="checkbox" class="form-check-input" name="encheres" value="encours" id="encours">Mes enchères en cours
                                </label>
                            </div>
                            <div class="form-check">
                                <label class="form-check-label">
                                    <input type="checkbox" class="form-check-input" name="encheres" value="remportees" id="remportees">Mes enchères remportées
                                </label>
                            </div>
                        </div>
                        <div class="form-check">
                            <label class="form-check-label">
                                <input type="radio" class="form-check-input" name="type-encheres" value="ventes" id="ventes">Ventes
                            </label>
                        </div>
                        <div class="form-group">
                            	<div class="form-check">
	                                <label class="form-check-label">
	                                    <input type="checkbox" class="form-check-input" name="ventes" value="venteencours" id="venteencours">Mes ventes en cours
	                                </label>
	                             </div>
	                            <div class="form-check">
	                                <label class="form-check-label">
	                                    <input type="checkbox" class="form-check-input" name="ventes" value="nondebutees" id="nondebutees">Mes ventes non débutées
	                                </label>
	                            </div>
	                            <div class="form-check">
	                                <label class="form-check-label">
	                                    <input type="checkbox" class="form-check-input" name="ventes" value="terminees" id="terminees">Mes ventes terminées
	                                </label>
	                            </div>
                       </div>
                     </div>
                
                
	                <button class="btn btn-primary btn-lg btn-block col-10 offset-1" type="submit" value= "Rechercher"> Trier les articles en vente
	<!--            <img class="small-icon" src="images/search.svg" alt="Eni Ecole">
	 -->            </button>
              </div> 
    	</form>
        </c:if>
        
        
            
           <!--enchères-->
        <!--Liste des encheres sans filtre-->      
        <div class=" row  border-top card-deck" > 
        	<c:forEach items="${listeAEC}" var="liste">
                	<div class = "col-6 sm-6 p-2 ">
                    <div class=" card mb-2 ">
                        <div class="card-header text-center">
                            <h4 class="my-0 font-weight-normal">${liste.getNom_article() }</h4>
                        </div>
                        <div class="d-flex">
                            <div class="col-2 border border-dark rounded-circle  p-4">
                            
				            	</div>
                            <ul class="col-6 list-unstyled p-2 ">
                                <li>Prix : ${liste.getPrix_initial()}</li>
								<c:if test="${liste.enchere.getMontantEnchere()!=null}">
                                	<li>Meilleure enchère : ${liste.enchere.getMontantEnchere()} point(s)</li>
                                </c:if>
                                <c:if test="${liste.enchere.getMontantEnchere()==null}">
                                	<li>Meilleure enchère : Aucune enchère</li>
                                </c:if>                                <li>Fin de l'enchère : <input type ="datetime-local" value ="${liste.getDate_fin_enchere() }" disabled class = "date">	</li>
                                <li>Vendeur : ${liste.getUtilisateur().getPseudo()}</li>
                            </ul>
                        </div>
                    </div>
                   </div> 
			</c:forEach>
  		</div>
  		<c:if test="${!erreurCategorie}">
			<c:if test="${listeVide}">
				<div class = "alert-danger text-center col-md-12">
					${messageListeVide}
				</div>
		  	</c:if>
		</c:if> 
  		
  		<!--Liste des encheres après application filtre en mode DECONNECTE-->      
  		<div class=" row  border-top card-deck  " >
       		<c:forEach items="${listeAECCN}" var="liste">
               	<div class = "col-6 sm-6 p-2 ">
                   	<div class=" card mb-2 ">
                        <div class="card-header text-center">
                            <h4 class="my-0 font-weight-normal">${liste.getNom_article() }</h4>
                        </div>
                        <div class="d-flex">
                            <div class="col-2 border border-dark rounded-circle  p-4">
                            
				            </div>
                            <ul class="col-9 list-unstyled p-2">
                                <li>Prix : ${liste.getPrix_initial()}</li>
                               		<c:if test="${liste.enchere.getMontantEnchere()!=null}">
                                		<li>Meilleure enchère : ${liste.enchere.getMontantEnchere()} point(s)</li>
                                	</c:if>
                                	<c:if test="${liste.enchere.getMontantEnchere()==null}">
                                		<li>Meilleure enchère : Aucune enchère</li>
                                	</c:if>
                                <li>Fin de l'enchère : <input type ="datetime-local" value ="${liste.getDate_fin_enchere() }" disabled class = "date"> 	</li>
                                <li>Vendeur :  ${liste.getUtilisateur().getPseudo()}</li>
                            </ul>
                        </div>
                	</div>
            	</div>  
  			</c:forEach>
		</div>

  		<!--Liste des encheres AVANT filtre en mode CONNECTE-->      
		
		<div class=" row  border-top card-deck" > 
        	<c:forEach items="${requestScope.listeConnecte}" var="article">
                	<div class = "col-6 sm-6 p-2 ">
                    <div class=" card mb-2 ">
                        <div class="card-header text-center">
                            <h4 class="my-0 font-weight-normal">${article.getNom_article() }</h4>
                        </div>
                        <div class="d-flex">
                           <div class="col-2 border border-dark rounded-circle  p-4">                          
				           </div>
                            <ul class="col-6 list-unstyled p-2 ">
                                <li>Prix : ${article.getPrix_initial()}</li>
                                	<c:if test="${article.enchere.getMontantEnchere()!=null}">
                                		<li>Meilleure enchère : ${article.enchere.getMontantEnchere()} point(s)</li>
                                	</c:if>
                                	<c:if test="${article.enchere.getMontantEnchere()==null}">
                                		<li>Meilleure enchère : Aucune enchère</li>
                                	</c:if>
                                <li>Fin de l'enchère : <input type ="datetime-local" value ="${article.getDate_fin_enchere() }" disabled class = "date">	</li>
                                <li>Vendeur :  <a href ="${pageContext.request.contextPath}/AfficherProfilPartiel?userT=${article.utilisateur.pseudo}">${article.getUtilisateur().getPseudo()}</a></li>
                            </ul>
                        </div>
                        <a class="mt-3 btn btn-lg btn-block " href="${pageContext.request.contextPath}/display/article?idArticle=${article.getNo_article()}" title="faire une enchère">
                            <img class="small-icon marteau" src="${pageContext.request.contextPath}/img/marteauGIF.gif">
                        </a>          
                    </div>
                   </div> 
			</c:forEach>
  		</div>
  
  		


	</main>

        <!--footer-->
        <footer class="border-top text-center align-bottom">
    
              <%@ include file="./fragments/footer.jspf" %>
        </footer>
        
      
    
    <!-- Optional JavaScript -->
    <!-- jQuery first, then Popper.js, then Bootstrap JS -->
    <script src="https://code.jquery.com/jquery-3.2.1.slim.min.js" integrity="sha384-KJ3o2DKtIkvYIK3UENzmM7KCkRr/rE9/Qpg6aAZGJwFDMVNA/GpGFF93hXpG5KkN" crossorigin="anonymous"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js" integrity="sha384-ApNbgh9B+Y1QKtv3Rn7W3mgPxhU9K/ScQsAP7hUibX39j7fakFPskvXusvfa0b4Q" crossorigin="anonymous"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js" integrity="sha384-JZR6Spejh4U02d8jOt6vLEHfe/JQGiRRSQQxSfFWpi1MquVdAyjUar5+76PVCmYl" crossorigin="anonymous"></script>
    <script>
        // Example starter JavaScript for disabling form submissions if there are invalid fields
        (function() {
            'use strict';
    
            window.addEventListener('load', function() {
            	checkAchats();
            	checkVentes();
                achats.addEventListener('change', function(event) {
                	checkAchats();
                }, false);
                ventes.addEventListener('change', function(event) {
                	checkVentes();
                }, false);
                
                function checkAchats() {
                	//id radio button achats
                	var achats = document.getElementById('achats');
                    if (achats.checked){
                    	//id des checkbox
                        document.getElementById('venteencours').disabled = true;
                        document.getElementById('nondebutees').disabled = true;
                        document.getElementById('terminees').disabled = true;
                        document.getElementById('encours').disabled = false;
                        document.getElementById('ouvertes').disabled = false;
                        document.getElementById('remportees').disabled = false;
                    }
                }
                function checkVentes(){
                	//id radio button ventes
                	var ventes = document.getElementById('ventes');
                    if (ventes.checked){
                    	//id des checkbox
                        document.getElementById('venteencours').disabled = false;
                        document.getElementById('nondebutees').disabled = false;
                        document.getElementById('terminees').disabled = false;
                        document.getElementById('encours').disabled = true;
                        document.getElementById('ouvertes').disabled = true;
                        document.getElementById('remportees').disabled = true;
                    }
                }
            }, false);
        })();
    </script>
</body>
</html>
