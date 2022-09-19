<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    
    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css" integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">
    
    <!-- Personnal CSS-->
    <link rel="stylesheet" href="css/style.css">

    <!--icons-->
    <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.7.0/css/all.css" integrity="sha384-lZN37f5QGtY3VHgisS14W3ExzMWZxybE1SJSEsQp9S+oqd12jhcu+A56Ebc1zFSJ" crossorigin="anonymous">

<title>Inscription_Utilisateur</title>
</head>


<body>
 	<div class="container-fluid">
        <!--fullHeader-->
        <header>
            <nav class="pr-5 navbar navbar-expand-sm bg-dark navbar-dark align-top justify-content-between">
                <!-- Brand/logo -->
                 <form action="${pageContext.request.contextPath}/Accueillir" method = "get">
                    <input type = "image" src="${pageContext.request.contextPath}/img/logoEni.png" alt="Accueil ENI-Encheres" value = "Retour a l'acueil" class ="bouton" >
                </form>
                <a class="navbar-brand">
                    <strong class ="string">ENI-Encheres - Inscription</strong>
                </a>

                
                   
                <ul class="navbar-nav ml-auto">
                    <!-- Dropdown for small screen -->
                    <li class="nav-item dropdown d-lg-none">
                        <a class="nav-link dropdown-toggle" href="#" id="navbardrop" data-toggle="dropdown">
                            <img class="small-icon" src="images/menu.svg" alt="Menu ENI-Encheres">
                        </a>
                        <div class="dropdown-menu">
                           
                            <a class="dropdown-item" href="${pageContext.request.contextPath}/Connecter" alt="Se connecter à ENI-Encheres">Me connecter</a>
                        </div>
                    </li>  
                    <!-- Links for medium screen-->
                    
                    <li class="nav-item d-none d-lg-block">
                        <a class="nav-link" href="${pageContext.request.contextPath}//Accueillir" alt="Retour à l'accueil">Retour à l'accueil</a>
                    </li>
                </ul>
            </nav>
        </header>
 
<!-- 
<form action="${pageContext.request.contextPath}/Creation" method = post>
<input type = "text" name ="pseudo" placeholder="Créer un pseudo" required>
<input type = "text" name ="nom" placeholder="Nom" required>
<input type = "text" name ="prenom" placeholder="prenom" required>
<input type = "text" name ="email" placeholder="Rentrez votre email" required>
<input type = "text" name ="telephone" placeholder="Votre telephone">
<input type = "text" name ="rue" placeholder="Votre rue" required>
<input type = "text" name ="codepostal" placeholder="Le code postal" required>
<input type = "text" name ="ville" placeholder="La ville" required>
<input type = "text" name ="mdp" placeholder="motdepasse"required>
<input type="submit" value="Créer un utilisateur">
 -->


		<c:if test ="${requestScope.erreurdeConnexion }">
			<div class="d-flex alert-danger">
				<ul class="col-6 list-unstyled p-2 text-center offset-3">
					<li>${requestScope.message}</li>
				</ul>
			</div>        
		</c:if>

		<c:if test ="${requestScope.erreurMdpDifferents }">
			<div class="d-flex alert-danger">
				<ul class="col-6 list-unstyled p-2 text-center offset-3">
					<li>${requestScope.messageMdpDifferents}</li>
				</ul>
			</div>        
		</c:if>

                
  <div class="container">
             <h1 class = "text-center mb-4">Mon profil</h1>
             <form action="${pageContext.request.contextPath}/Creation" method = post>
                <div class="row mb-3">
                    <div class="col">
                    <input type = "text" class="form-control" name ="pseudo" placeholder="Créer un pseudo" required>
                     
                    </div>
                    <div class="col">
                     <input type = "text" name ="nom"  class="form-control" placeholder="Nom" required>
                    </div>
                </div>
                <div class="row mb-3">
                    <div class="col">
                    <input type = "text" name ="prenom"  class="form-control" placeholder="prenom" required>
                    </div>
                    <div class="col">
                     <input type = "text" name ="email"  class="form-control" placeholder="Rentrez votre email" required>
                    </div>
                </div>
                 <div class="row mb-3">
                    <div class="col">
                  <input type = "text" name ="telephone"   class="form-control"placeholder="Votre telephone">
                    </div>
                    <div class="col">
                    <input type = "text" name ="rue"   class="form-control"placeholder="Votre rue" required>
                    </div>
                </div>
                 <div class="row mb-3">
                    <div class="col">
                   <input type = "text" name ="codepostal"  class="form-control" placeholder="Le code postal" required>
                    </div>
                    <div class="col">
                    <input type = "text" name ="ville"   class="form-control" placeholder="La ville" required>
                    
                    </div>
                </div>
                 <div class="row mb-3">
                    <div class="col">
                   <input type = "password" name ="mdp"  class="form-control" placeholder="mot de passe"required>
                    </div>
                    <div class="col">
                   <input type = "password" name ="mdpconfir"  class="form-control" placeholder="Confirmation du mot de passe"required>
                 
                 
                    
                    
                  
                    </div>
                    
                </div>
                <div class="row mb-3">
                    <div class="col ">             
                    <input type="submit" value="Valider" class ="btn btn-primary btn-lg btn-block ">          
                    </div>
                    <div class="col">
                    <a href = "${pageContext.request.contextPath}/Accueillir" alt="Retour à l'accueil">
                    <input type="button" value="Annuler"class = "btn btn-primary btn-lg btn-block ">
                    </a>
                 
                    </div>
                    
                </div>
            </form>

         </div>
         
               <footer class="border-top text-center align-bottom">
    
              <%@ include file="../fragments/footer.jspf" %>
        </footer>
    </body>





</html>