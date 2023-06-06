<%@page import="modelo.Favorito"%>
<%@page import="modelo.FavoritoDB"%>
<%@page import="modelo.Usuario"%>
<%@page import="modelo.TagTreeDB"%>
<%@page import="modelo.UsuarioDB"%>
<%@page import="java.util.ArrayList"%>
<%@page import="modelo.ArchivoDB"%>
<%@page import="modelo.Archivo"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" isELIgnored="false"%>

<!DOCTYPE html>
<html lang="es">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Biblioteca Alejandría</title>

    <!--CUSTOM CSS-->
    <link rel="stylesheet" href="styles.css">
</head>
<body>
  
  <div class="container">
    <nav class="navMain">    
      <div id="sideNavMenu" class="sideNav">
        <a href="javascript:void(0)" class="closebtn" onclick="closeNav()">&times;</a>
        
        <%if(session.getAttribute("usuario") == null){%>
            <a href="InicioSesion.html">Favoritos ★</a>
            <a href="InicioSesion.html">Subir Archivos</a>
            <a href="#">Ajustes</a>
        <%}else{%>
            <a href="Favoritos.jsp">Favoritos ★</a>
            <a href="SubirArchivo.html">Subir Archivos</a>
            <a href="#">Ajustes</a>
        <%} %>
      </div>
      <span onclick="openNav()"><img src="img/menuLogo.png"  class="menu-sidebar"></span>     
        <ul class="navBanner">
          <a href="MainPage.jsp"><img src="img/Logo.png" class= "bannerLogo" alt="Logo Imagen"></a>
        </ul>
        <ul class="navLogIn">
            <div class="dropdown">
                <button class="dropbtn"><img src="img/logoInicioSesion.png" width="50px"></button>
                <div class="dropdown-content">
                    <%if(session.getAttribute("usuario") == null){%>
                  
                        <a href="InicioSesion.html">Iniciar sesión</a>
                        <a href="Registro.html">Registrarse</a>
                  
                    <%}else{%>
                  
                        <a href="InicioSesionServlet?parametro=0">Cerrar sesión</a>  
                        <a href="InicioSesionServlet?parametro=1">Cambiar cuenta</a>
                        
                    <%} %>
                         
                  
                </div>
            </div>
        </ul>    
    </nav>
                    
    <div class="buscarOrdenar">
        <h1 class="titulo-fav">Mis archivos favoritos ★</h1>
    </div>
      
    <div class="resultados">
        
            
        <%
            ArrayList<Archivo> archivos = new ArrayList<>();
            ArrayList<Favorito> favoritos = new ArrayList<>();
            
            Usuario usuario = (Usuario) request.getSession().getAttribute("usuario");
            favoritos = FavoritoDB.selectFavoritosByUserId(usuario.getId());
                
                
            for(int i=0; i<favoritos.size(); i++){
                archivos.add(ArchivoDB.selectFileById(favoritos.get(i).getMiArchivo()));
            }
            
          %>
      <table class="tabla-resultados">
          <% for(int i=0; i < archivos.size(); i++){ %>
          
            <tr onclick="document.location = 'VisualizarArchivoServlet?file=<%=archivos.get(i).getIdArchivo()%>';">
            
                <td><%=archivos.get(i).getTitulo()%></td>
                <td><img src="img/fecha.png" alt="fecha icono" id="icono"><%=archivos.get(i).getFechaSubida()%></td>
                <td><img src="img/view.png" alt="visto icono" id="icono"><%= archivos.get(i).getNumVistas()%> vistas</td>
                <td><img src="img/descarga.png" alt="descarga icono" id="icono"><%= archivos.get(i).getNumDescargas()%> descargas</td>
                <td><img src="img/estrella.png" alt="estrella icono" id="icono"><%= archivos.get(i).getValoracionMedia()%> / 5 valoración</td>
                <td><img src="img/logoInicioSesion.png" alt="persona icono" id="icono"><%= UsuarioDB.selectUserById(archivos.get(i).getPropietario()).getUsername()%></td>
            </tr>
          
          <%
            }
          %>  
        
      </table>
    </div>
  </div>
  <!--CUSTOM JS-->
  <script>
    /*Funcionalidad de SideBar */
  function openNav() {
    document.getElementById("sideNavMenu").style.width = "250px";
  }

  /*Pone el width = 0 del sidebar*/
  function closeNav() {
    document.getElementById("sideNavMenu").style.width = "0";
  }
  
  function submitTag() {
      document.getElementById("uniSelectForm").submit();
      console.log(document.getElementById("uniSelectForm"));
      document.getElementById("gradoSelectForm").submit();
      document.getElementById("cursoSelectForm").submit();
      document.getElementById("cuatriSelectForm").submit();
      document.getElementById("asigSelectForm").submit();
  }
  
  </script>
</body>
</html>