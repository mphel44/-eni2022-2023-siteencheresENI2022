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
<title>Afficher Profil</title>
</head>
<body>
<header>
            <nav class="pr-5 navbar navbar-expand-sm bg-dark navbar-dark align-top justify-content-between">
                <!-- Brand/logo -->
                 
                 <c:if test="${!userConnectePageProfilPartiel}">
	                 <form action="${pageContext.request.contextPath}/Accueillir" method = "get">
	                    <input type = "image" src="${pageContext.request.contextPath}/img/logoEni.png" alt="Accueil ENI-Encheres" value = "Retour a l'accueil" class ="bouton" >
	                </form>
                </c:if>
                 
                <c:if test="${userConnectePageProfilPartiel}">
	                 <form action="${pageContext.request.contextPath}/servlet/accueil/connected" method = "get">
	                    <input type = "image" src="${pageContext.request.contextPath}/img/logoEni.png" alt="Accueil ENI-Encheres" value = "Retour a l'accueil" class ="bouton" >
	                </form>
                </c:if>
                
                
                <a class="navbar-brand">
                    <strong class ="string">ENI-Encheres - Afficher le profil partiel</strong>
                </a>

               

                <ul class="navbar-nav ml-auto">
                    <!-- Dropdown for small screen -->
                    <li class="nav-item dropdown d-lg-none">
                        <a class="nav-link dropdown-toggle" href="#" id="navbardrop" data-toggle="dropdown">
                            <img class="small-icon" src="images/menu.svg" alt="Menu ENI-Encheres">
                        </a>
                        <div class="dropdown-menu">
	                        <c:if test="${!userConnectePageProfilPartiel}">
	                            <a class="dropdown-item" href="${pageContext.request.contextPath}/Accueillir" alt="Retour accueil">Retour à l'accueil</a>
	                        </c:if>
	                        
	                        <c:if test="${userConnectePageProfilPartiel}">
	                            <a class="dropdown-item" href="${pageContext.request.contextPath}/servlet/accueil/connected" alt="Retour accueil">Retour à l'accueil</a>
	                        </c:if>
	                        
                        </div>	
                    </li>  
                    <!-- Links for medium screen-->
                   
	                
                   
                    
                    <li class="nav-item d-none d-lg-block">
	 				<c:if test="${!userConnectePageProfilPartiel}">
	 					<form action="${pageContext.request.contextPath}/Accueillir" method = "get">
	                        <input type = submit value = "Retour a l'accueil" class ="bouton" >
	                    </form>   
	                </c:if>

	 				<c:if test="${userConnectePageProfilPartiel}">
	 					<form action="${pageContext.request.contextPath}/servlet/accueil/connected" method = "get">
	                        <input type = submit value = "Retour a l'accueil" class ="bouton" >
	                    </form>   
	                </c:if>
	                    
	                                     
                    </li>
                </ul>
            </nav>
        </header>

<main>
 
	<c:if test ="${requestScope.erreurDeleteUser}">
		<div class="d-flex alert-danger">
			<ul class="col-6 list-unstyled p-2 text-center offset-3">
				<c:forEach items="${listeErrModifUtil}" var="err">
					<li>${err.getMessage()}</li>
				</c:forEach>
			</ul>
		</div>
	</c:if>
	
	<div class="container text-center">
		<h1 class = "text-center mb-4"> Le profil de l'utilisateur</h1>    
	       <div class="col-lg-8 offset-lg-2">
	        <div class="card mb-4">
	          <div class="card-body">
	            <div class="row">
	            
	              <div class="col-sm-3">
	                <p class="mb-0">Pseudo</p>
	              </div>
	              	             
	              
	              <div class="col-sm-9">
	                <p class="text-muted mb-0">${profil.getPseudo()}</p>
	              </div>
	              
	            </div>
	             <hr>
	            <div class="row">
	              <div class="col-sm-3">
	                <p class="mb-0">Nom</p>
	              </div>
	              <div class="col-sm-9">
	                <p class="text-muted mb-0">${profil.getNom()}</p>
	              </div>
	            </div>
	          
	            <hr>
	            <div class="row">
	              <div class="col-sm-3">
	                <p class="mb-0">Prenom</p>
	              </div>
	              <div class="col-sm-9">
	                <p class="text-muted mb-0">${profil.getPrenom()}</p>
	              </div>
	            </div>
	            <hr>
	            <div class="row">
	              <div class="col-sm-3">
	                <p class="mb-0">Email</p>
	              </div>
	              <div class="col-sm-9">
	                <p class="text-muted mb-0">${profil.getEmail()}</p>
	              </div>
	            </div>
	           
	         
	            
	          </div>
	        </div>      
	        
	</div>

</main>
      <footer class="border-top text-center align-bottom">
    
              <%@ include file="../fragments/footer.jspf" %>
        </footer>
  
         <!-- Optional JavaScript -->
    <!-- jQuery first, then Popper.js, then Bootstrap JS -->
    <script src="https://code.jquery.com/jquery-3.2.1.slim.min.js" integrity="sha384-KJ3o2DKtIkvYIK3UENzmM7KCkRr/rE9/Qpg6aAZGJwFDMVNA/GpGFF93hXpG5KkN" crossorigin="anonymous"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js" integrity="sha384-ApNbgh9B+Y1QKtv3Rn7W3mgPxhU9K/ScQsAP7hUibX39j7fakFPskvXusvfa0b4Q" crossorigin="anonymous"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.js" integrity="sha384-JZR6Spejh4U02d8jOt6vLEHfe/JQGiRRSQQxSfFWpi1MquVdAyjUar5+76PVCmYl" crossorigin="anonymous"></script>
  <script src="https://cdn.jsdelivr.net/npm/jquery@3.5.1/dist/jquery.slim.min.js" integrity="sha384-DfXdz2htPH0lsSSs5nCTpuj/zy4C+OGpamoFVy38MVBnE+IbbVYUew+OrCXaRkfj" crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.1/dist/js/bootstrap.bundle.min.js" integrity="sha384-fQybjgWLrvvRgtW6bFlB7jaZrFsaBXjsOMm/tB9LTS58ONXgqbR9W8oWht/amnpF" crossorigin="anonymous"></script>
  
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