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

	
    <title>ENI-Encheres : Gestion du profil</title>
    
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
                    <strong class ="string">ENI-Encheres - Gestion du profil</strong>
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
                          	<c:if test ="${statutAdministrateur}">
                           		<a class="dropdown-item" href="#" alt="Administrer le site">Administrer</a> 
                           	</c:if> 
                            
                            <a class="dropdown-item" href="${pageContext.request.contextPath}/servlet/accueil/connected" alt="Retour à l'acceuil">Retour à l'accueil</a>
                            
                            <a class="dropdown-item" href="${pageContext.request.contextPath}/disconnection/servlet" alt="Me déconnecter">Me déconnecter</a>
                        </div>	
                    </li>  
                    <!-- Links for medium screen-->
                   	<c:if test ="${statutAdministrateur}">
	                    <li class="nav-item d-none d-lg-block">
	                        <a class="nav-link" href="${pageContext.request.contextPath}/disconnection/servlet" alt="Administrer le site">Administrer</a>
	                    </li> 
	                </c:if> 
	                
                    <li class="nav-item d-none d-lg-block">
                        <form action="${pageContext.request.contextPath}/servlet/accueil/connected" method = "get">
                        	<input type = submit value = "Retour a l'acueil" class ="bouton" >
                   		 </form>
                    </li>
                    
                    <li class="nav-item d-none d-lg-block">
                        <a class="nav-link" href="${pageContext.request.contextPath}/disconnection/servlet" alt="Me déconnecter">Me déconnecter</a>
                    </li>
                </ul>
            </nav>
        </header>

	<main>
		
		<c:if test ="${requestScope.erreurMdpDiff}">
        	<div class="d-flex alert-danger">
			    <ul class="col-6 list-unstyled p-2 text-center offset-3">
                	<li>${messErrMdpDiff}</li>
                </ul>
            </div>
		</c:if>
		
		<c:if test ="${requestScope.erreurUpdate}">
        	<div class="d-flex alert-danger">
			     <ul class="col-6 list-unstyled p-2 text-center offset-3">
                	<c:forEach items="${listeErrModifUtil}" var="err">
                		<li>${err.getMessage()}</li>
                	</c:forEach>
               </ul>
            </div>
		</c:if>
		
		<c:if test ="${requestScope.succesModif}">
        	<div class="d-flex alert-success">
			    <ul class="col-6 list-unstyled p-2 text-center offset-3">
                    <li>${requestScope.messSucces}</li>
                </ul>
            </div>
		</c:if>
	
 		<div class="container">
           <h1 class = "text-center mb-4">Modifier mon profil</h1>
             <form action="${pageContext.request.contextPath}/profil/servlet" method = post>
                <div class="row mb-3">
                    <div class="col">
                    <input type = "text" class="form-control" name ="pseudo" value="${sessionScope.utilisateurConnecte.getPseudo()}" required>
                    </div>
                    <div class="col">
                     <input type = "text" name ="nom"  class="form-control" value="${sessionScope.utilisateurConnecte.getNom()}" required>
                    </div>
                </div>
                <div class="row mb-3">
                    <div class="col">
                    <input type = "text" name ="prenom"  class="form-control" value="${sessionScope.utilisateurConnecte.getPrenom()}" required>
                    </div>
                    <div class="col">
                     <input type = "text" name ="email"  class="form-control" value="${sessionScope.utilisateurConnecte.getEmail()}" required>
                    </div>
                </div>
                 <div class="row mb-3">
                    <div class="col">
                  <input type = "text" name ="telephone"   class="form-control" value="${sessionScope.utilisateurConnecte.getTelephone()}">
                    </div>
                    <div class="col">
                    <input type = "text" name ="rue"   class="form-control" value="${sessionScope.utilisateurConnecte.getRue()}">
                    </div>
                </div>
                 <div class="row mb-3">
                    <div class="col">
                   <input type = "text" name ="codepostal"  class="form-control" value="${sessionScope.utilisateurConnecte.getCodePostal()}">
                    </div>
                    <div class="col">
                    <input type = "text" name ="ville"   class="form-control" value="${sessionScope.utilisateurConnecte.getVille()}">
                    </div>
                </div>
                 <div class="row mb-3">
                   <div class="col">
                   		<input type = "password" name ="mdp"  class="form-control" value="${sessionScope.utilisateurConnecte.getMotDePasse()}"required>
                   </div>
                   <div class="col">
                   		<input type = "password" name ="mdpconfir"  class="form-control" value="${sessionScope.utilisateurConnecte.getMotDePasse()}"required>
                   </div>
                </div>
                <div class="row mb-3">
                    <div class="col 12 ">
                 		<input type="submit" value="Modifier" class ="btn btn-primary btn-lg btn-block">
                   	</div>
                  
                </div>
            </form>
            	
         </div>
		 
	</main>
        <!--footer-->
             <footer class="border-top text-center align-bottom">
    
              <%@ include file="../fragments/footer.jspf" %>
        </footer>
    </div>
    
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
</html>