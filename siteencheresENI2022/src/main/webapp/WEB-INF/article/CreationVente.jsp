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

    <title>ENI-Encheres : Création d'un article</title>

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
                    <input type = "image" src="${pageContext.request.contextPath}/img/logoEni.png" alt="Accueil ENI-Encheres" value = "Retour a l'accueil" class ="bouton" >
                </form>
                <a class="navbar-brand">
                    <strong class ="string">ENI-Encheres - Creation d'un article à vendre</strong>
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
                            <img class="small-icon" src="images/menu.svg" alt="Menu ENI-Encheres">
                        </a>
                        <div class="dropdown-menu">
                          
                            
                            <a class="dropdown-item" href="${pageContext.request.contextPath}/servlet/accueil/connected" alt="Retour à l'acceuil">Retour à l'accueil</a>
                            
                        </div>	
                    </li>  
                    <!-- Links for medium screen-->
                   
	                
                    <li class="nav-item d-none d-lg-block">
                    <form action="${pageContext.request.contextPath}/servlet/accueil/connected" method = "get">
                        <input type = submit value = "Retour a l'accueil" class ="bouton" >
                    </form>
                    </li>
                    
                   
                </ul>
            </nav>
        </header>
       
        <!--main bloc-->
        <main>
        	<!--title-->
            <div class="mx-auto text-center mt-2 mb-2">
                <h1>Vente d'un article</h1>
	        </div>
	        
		       		<c:if test="${requestScope.ok}">
			            <div class="d-flex alert-success">
			                <ul class="col-6 list-unstyled p-2 text-center offset-3">
			                    <li>${requestScope.validation}</li>
			                </ul>
			            </div>	
		            </c:if>
		            
		            <c:if test="${requestScope.erreur}">
		            	<div class="d-flex alert-danger">
		     
				            <ul class="col-6 list-unstyled p-2 text-center offset-3">
				                <c:forEach items="${listeErreurs}" var="err">
				                   <li>${err.getMessage()}</li>
				                </c:forEach>
				            </ul>
		            	</div>
		            </c:if>

	        <form class="form-filter border mb-3" action="${pageContext.request.contextPath}/add/sales" method="post"> 
	        
	            <div class="d-flex bg-info text-center">
	                <div class="col-12 text-white titre2    ">
	                  Informations Article(s)
	                </div>
	            </div>
            <!--filtre-->
                <div class="row">
                
                	<c:if test="${!requestScope.erreur}">
                    <!--Partie gauche-->
                    <div class="col-md-6 mb-3 mt-2">
                        <div class="form-group">
                                <label for="name-input">Nom Article :</label>
                                <input type="text" class="form-control" id="name-input" name="name" >
                        </div>
                        <div class="form-group">
                                <label for="description-input">Description :</label>
                                <input type="text" class="form-control" id="description-input" name="description" >
                        </div>	
                        <div class="form-group">
                        		<label for="start-input">Date de Début :</label>
                                <input type="date" class="form-control" id="start-input" name="start" value="jj-mm-aaaa-hh-ss" min="2022-01-01" max="2024-01-01">
                        </div>
                        <div class="form-group">
                        		<label for="start-input">Heure de Début :</label>
                                <input type="time" class="form-control" id="start-input" name="startTime" value="hh-ss" required >
	                   </div>     	
                   </div>
                   
                   <!--Partie droite-->	
                   <div class="col-md-6 mb-3 mt-2">	
                        <div class="form-group">		
                        		<label for="end-input">Date de Fin :</label>
                                <input type="date" class="form-control" id="end-input" name="end" value="jj-mm-aaaa-hh-ss" min="2022-01-01" max="2024-01-01">
                        </div>
                        <div class="form-group">		
                        		<label for="end-input">Heure de Fin :</label>
                                <input type="time" class="form-control" id="end-input" name="endTime" value="hh-ss" required>
                        </div>
                        <div class="form-group"> 		
                        		<label for="price-input">Prix Initial :</label>
                                <input type="text" class="form-control" id="price-input" name="price" >
                        </div>	
                        <div class="form-group">
                            <label for="categories-select">Catégories</label>
                            <select class="form-control" id="categories-select" name="categorie">
                                <option selected>Toutes</option>
                                <option name="informatique" value="Informatique">Informatique</option>
                                <option name="ameublement" value="Ameublement">Ameublement</option>
                                <option name="vêtement" value="Vêtement">Vêtement</option>
                                <option name="sport & loisir" value="Sport & Loisir">Sport & Loisir</option>
                            </select>
                        </div>         
                    </div>
                    </c:if> 
                    
                    <c:if test="${requestScope.erreur}">
	                    <!--Partie gauche-->
	                    <div class="col-md-6 mb-3">
	                        <div class="form-group">
	                                <label for="name-input">Nom Article :</label>
	                                <input type="text" class="form-control" id="name-input" name="name" value="${param.name}">
	                        </div>
	                        <div class="form-group">
	                                <label for="description-input">Description :</label>
	                                <input type="text" class="form-control" id="description-input" name="description" value="${param.description}">
	                        </div>	
	                        <div class="form-group">
	                        		<label for="start-input">Date de Début :</label>
	                                <input type="date" class="form-control" id="start-input" name="start" value="${param.start}" min="2022-01-01" max="2024-01-01">
	                        </div>
	                        <div class="form-group">
	                        		<label for="start-input">Heure de Début :</label>
	                                <input type="time" class="form-control" id="start-input" name="startTime" value="${param.startTime}" required>
		                   </div>     	
	                   </div>
	                   
	                   <!--Partie droite-->	
	                   <div class="col-md-6 mb-3">	
	                        <div class="form-group">		
	                        		<label for="end-input">Date de Fin :</label>
	                                <input type="date" class="form-control" id="end-input" name="end" value="${param.end}" min="2022-01-01" max="2024-01-01">
	                        </div>
	                        <div class="form-group">		
	                        		<label for="end-input">Heure de Fin :</label>
	                                <input type="time" class="form-control" id="end-input" name="endTime" value="${param.endTime}" required>
	                        </div>
	                        <div class="form-group"> 		
	                        		<label for="price-input">Prix Initial :</label>
	                                <input type="text" class="form-control" id="price-input" name="price" value="${param.price}">
	                        </div>	
	                        <div class="form-group">
	                            <label for="categories-select">Catégories</label>
	                            <select class="form-control" id="categories-select" name="categorie" value="${param.categorie}">
	                                <option selected>Toutes</option>
	                                <option name="informatique" value="Informatique">Informatique</option>
	                                <option name="ameublement" value="Ameublement">Ameublement</option>
	                                <option name="vêtement" value="Vêtement">Vêtement</option>
	                                <option name="sport & loisir" value="Sport & Loisir">Sport & Loisir</option>
	                            </select>
	                        </div>         
	                    </div>
                    </c:if> 
              	 </div>
		<!--Partie basse-->
		            <div class="d-flex bg-info text-center">
		                <div class="col-12 text-white titre2">
		                    Modalités de Retrait (A modifier uniquement si différente de l'adresse du vendeur)
		                </div>
            		</div>  
            	<div class = row> 
                   	<div class="col-md-6 mb-3 mt-2">
                        <div class="form-group">
                                <label for="street-input">Rue :</label>
                                <input type="text" class="form-control" id="street-input" name="street" value="${sessionScope.utilisateurConnecte.getRue()}">
                        </div>
                        <div class="form-group">
                                <label for="postcode-input">Code Postal :</label>
                                <input type="text" class="form-control" id="postcode-input" name="postcode" value="${sessionScope.utilisateurConnecte.getCodePostal()}">
                    	</div>
                    </div>
                    <div class="col-md-6 mb-3 mt-2">	
                        <div class="form-group">
                        		<label for="town-input">Ville :</label>
                                <input type="text" class="form-control" id="town-input" name="town" value="${sessionScope.utilisateurConnecte.getVille()}">
                        </div>
                        <div class="form-group mt-5">
                                	<input type="submit" value="Créer" class ="btn btn-primary btn-lg btn-block">
                        </div>
                    </div>  
               </div>                            
			</form>	
			
		</main>
	</div>
    	<!--footer-->
	    	<footer class="border-top text-center align-bottom">
    
              <%@ include file="../fragments/footer.jspf" %>
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