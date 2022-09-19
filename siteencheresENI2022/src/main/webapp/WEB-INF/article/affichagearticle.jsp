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

    <title>ENI-Encheres : Affichage d'une enchère</title>

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
                    <strong class ="string">ENI-Encheres - Afficher un article</strong>

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
                            <a class="dropdown-item" href="${pageContext.request.contextPath}/servlet/accueil/connected"">Retour à l'accueil</a>
                        </div>
                    </li>  
                    <!-- Links for medium screen-->
                    <li class="nav-item d-none d-lg-block">
                        <a class="nav-link" href="${pageContext.request.contextPath}/servlet/accueil/connected"">Retour à l'accueil</a>
                    </li>
                </ul>
            </nav>
        </header>
        <!--main bloc-->
        <main>
        	<!--title-->
           			<c:if test="${requestScope.ok}">
			            <div class="d-flex alert-success">
			                <ul class="col-6 list-unstyled p-2 text-center offset-3">
			                    <li>${requestScope.validation}</li>
			                </ul>
			            </div>	
		            </c:if>
		            
		            <c:if test="${requestScope.testErreurServlet}">
			            <div class="d-flex alert-danger">
			                <ul class="col-6 list-unstyled p-2 text-center offset-3">
			                    <li>${requestScope.erreur}</li>
			                </ul>
			            </div>	
		            </c:if>
		            
		            <c:if test="${requestScope.testErreurBll}">
		            	<div class="d-flex alert-danger">
			                <ul class="col-6 list-unstyled p-2 text-center offset-3">
				                <c:forEach items="${listeErrModifUtil}" var="err">
				                   <li>${err.getMessage()}</li>
				                </c:forEach>
				            </ul>
		            	</div>
		            </c:if>
	        <div class="container text-center mb-4">
		<h1 class = "text-center mb-4"> Détail Vente</h1>    
	       <div class="col-lg-8 offset-lg-2">
	        <div class="card mb-4">
	          <div class="card-body">
	            <div class="row">
	            
	              <div class="col-sm-3">
	                <p class="mb-0">Nom Article</p>
	              </div>
	              
	              <div class="col-sm-9">
	                <p class="text-muted mb-0">${requestScope.nom}</p>
	              </div>
	              
	            </div>
	             <hr>
	            <div class="row">
	              <div class="col-sm-3">
	                <p class="mb-0">Description</p>
	              </div>
	              <div class="col-sm-9">
	                <p class="text-muted mb-0">${requestScope.description}</p>
	              </div>
	            </div>
	            
	            <hr>
	            <div class="row">
	              <div class="col-sm-3">
	                <p class="mb-0">Catégories</p>
	              </div>
	              <div class="col-sm-9">
	                <p class="text-muted mb-0">${requestScope.categorie}</p>
	              </div>
	            </div>
	            <hr>
	            <div class="row">
	              <div class="col-sm-3">
	                <p class="mb-0">Date de Début</p>
	              </div>
	              <div class="col-sm-9">
	                <p class="text-muted mb-0">${requestScope.debut}</p>
	              </div>
	            </div>
	            
	            
	            <hr>
	            <div class="row">
	              <div class="col-sm-3">
	                <p class="mb-0">Prix Initial</p>
	              </div>
	              <div class="col-sm-9">
	                <p class="text-muted mb-0">${requestScope.prix}</p>
	              </div>
	            </div>
	             <hr>
	            <div class="row">
	              <div class="col-sm-3">
	                <p class="mb-0">Meilleure Offre</p>
	              </div>
	              <div class="col-sm-9">
	                <p class="text-muted mb-0">${requestScope.offre}</p>
	              </div>
	            </div>
	             <hr>
	            <div class="row">
	              <div class="col-sm-3">
	                <p class="mb-0">Date de Fin</p>
	              </div>
	              <div class="col-sm-9">
	                <p class="text-muted mb-0">${requestScope.fin}</p>
	              </div>
	            </div>
	            <hr>
	             <div class="row">
	              <div class="col-sm-3">
	                <p class="mb-0">Retrait</p>
	              </div>
	              <div class="col-sm-9">
	                <p class="text-muted mb-0">${requestScope.retrait}</p>
	              </div>
	            </div>
	            <hr>
	             <div class="row">
	              <div class="col-sm-3">
	                <p class="mb-0">Vendeur</p>
	              </div>
	              <div class="col-sm-9">
	                <p class="text-muted mb-0">${requestScope.vendeur}</p>
	              </div>
	            </div>
	          </div>
	        </div>      
	       
	    	</div>
	    </form>
	     
			
	</div>
		       		
	        
            <!--filtre-->
                            
			
<!-- BOUTON DE TEST POUR FAIRE ENCHERE -->			
	<form class=" text-center mb-4 col-12" action="${pageContext.request.contextPath}/servlet/add/enchere" method="post"> 	
		<div class = "row">
		
		
		</div>
		
		
		
		<div class="form-group row">
			<div class = "col-4 offset-4 text-center">
				<h2>Faire une enchère :</h2>	
				<input type="number" class="form-control" id="seller-input" name="montant" value="${requestScope.enchere}">
				<input type="hidden" value="${requestScope.idArticle}" name="idArticle" class="form-control" id="seller-input">
				<input type="hidden" value="${sessionScope.utilisateurConnecte.getNoUtilisateur()}" name="idEncherisseur" class="form-control" id="seller-input">
			
			</div>
		</div>
		<div class="row">
			<div class="form-group  offset-4 col-4 text-center">
				<input type="submit" value="Enchérir"  class ="btn btn-primary btn-lg btn-block">
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