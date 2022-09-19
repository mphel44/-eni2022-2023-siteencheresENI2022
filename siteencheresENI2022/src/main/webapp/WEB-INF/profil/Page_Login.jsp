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
    <link rel="stylesheet" href="css/style.css">

    <!--icons-->
    <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.7.0/css/all.css" integrity="sha384-lZN37f5QGtY3VHgisS14W3ExzMWZxybE1SJSEsQp9S+oqd12jhcu+A56Ebc1zFSJ" crossorigin="anonymous">

    <title>ENI-Encheres : Connexion</title>

<!--[if gte mso 9]><xml>
<mso:CustomDocumentProperties>
<mso:xd_Signature msdt:dt="string"></mso:xd_Signature>
<mso:display_urn_x003a_schemas-microsoft-com_x003a_office_x003a_office_x0023_Editor msdt:dt="string">Bruno MARTIN</mso:display_urn_x003a_schemas-microsoft-com_x003a_office_x003a_office_x0023_Editor>
<mso:Order msdt:dt="string">493200.000000000</mso:Order>
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
        <!--emptyHeader-->
        <header>
            <nav class="pr-5 navbar navbar-expand-sm bg-dark navbar-dark align-top justify-content-between">
                <!-- Brand/logo -->
                 
				<form action="${pageContext.request.contextPath}/Accueillir" method = "get">
                        <input type = "image" src="${pageContext.request.contextPath}/img/logoEni.png" alt="Accueil ENI-Encheres" value = "Retour a l'acueil" class ="bouton" >
                    </form>
                <a class="navbar-brand">
                    <strong class ="string">ENI-Encheres - Connexion</strong>
                </a>
                

                <ul class="navbar-nav ml-auto">
                    <!-- Dropdown for small screen -->
                    <li class="nav-item dropdown d-lg-none">
                        <a class="nav-link dropdown-toggle" href="#" id="navbardrop" data-toggle="dropdown">
                            <img class="small-icon" src="images/menu.svg" alt="Menu ENI-Encheres">
                        </a>
                        <div class="dropdown-menu">
                             <a class="dropdown-item" href="${pageContext.request.contextPath}/Accueillir" alt="Retour à l'acceuil">Retour à l'accueil</a>
                            
                        </div>	
                    </li>  
                    <!-- Links for medium screen-->
                   
	                
                    <li class="nav-item d-none d-lg-block">
                    <form action="${pageContext.request.contextPath}/Accueillir" method = "get">
                        <input type = submit value = "Retour a l'accueil" class ="bouton" >
                    </form>
                    </li>
                    
                   
                </ul>
			</nav>
        </header>
 
        <!--main bloc-->
        <main>
            <!--title-->
            <div class="mx-auto text-center">
                <h1>Connexion</h1>
                <img class="mb-4 large-icon rounded-circle" src="images/user.svg" alt="">
            </div>
            
          	<!--erreur de connexion-->
            <c:if test ="${requestScope.erreurdeconnexion}">
	           <div class="d-flex alert-danger">
			        <ul class="col-6 list-unstyled p-2 text-center offset-3">
	                    <li>${requestScope.erreur}</li>
	                </ul>
	            </div> 
            </c:if>
            
            <!--erreur-->
            <c:if test ="${erreur}">
            	<div class="d-flex alert-danger">
			        <ul class="col-6 list-unstyled p-2 text-center offset-3">
	                    <li>${sessionScope.Message}</li>
	                </ul>
            	</div>
			</c:if>
           
            <!--formulaire-->
            <div class = "col-md-10 offset-md-1">
	            <form class="form-login" action="${pageContext.request.contextPath}/Connecter" method="post">
	                <label for="inputIdentifiant" class="sr-only">Identifiant</label>
	                <input type="text" id="inputIdentifiant" class="form-control mt-2" name="pseudo" placeholder="Pseudo" required autofocus >
	                <label for="inputPassword" class="sr-only">Password</label>
	                <input type="password" id="inputPassword" class="form-control mt-3" name="password" placeholder="Mot de passe" required>
	                
	                <button class="btn btn-lg btn-primary btn-block mt-3" type="submit" title="Me connecter">
	                	Me connecter
	                </button>
	               
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
</body>
</html>