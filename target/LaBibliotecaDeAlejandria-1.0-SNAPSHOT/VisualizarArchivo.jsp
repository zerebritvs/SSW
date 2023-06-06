
<%@page import="modelo.Usuario"%>
<%@page import="modelo.ArchivoDB"%>
<%@page import="modelo.Archivo"%>
<%@page import="modelo.UsuarioDB"%>
<%@page import="modelo.ComentarioDB"%>
<%@page import="modelo.Comentario"%>
<%@page import="java.util.ArrayList"%>
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
    <!-- Add icon library -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
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
                  
      <%
                        
      Archivo archivo = (Archivo) session.getAttribute("archivo");
      boolean yaEsFav = (Boolean) session.getAttribute("yaEsFav");

      %>
      <div class="visualizarArea">
          <div class="preview-box">
              
            <iframe src="files/torneo_dtd.pdf"></iframe>
          </div>
          <div class="descripcion-box">
              
                <div class="fila-fav">
                  <h1>Título</h1>
                  
                  <%if(session.getAttribute("usuario") != null){%>
                  <form class="fav-form" action="AddFavoritoServlet" method="post">
                      
                    <%if(yaEsFav){%>    
                        <button class="btn-fav-ya" type="submit" title="Quitar de favoritos"><i class="fa fa-star"></i></button>
                        <input type="hidden" name="file" value=<%=archivo.getIdArchivo()%>>
                    <%}else{%>
                        <button class="btn-fav" type="submit" title="Añadir a favoritos"><i class="fa fa-star"></i></button>
                        <input type="hidden" name="file" value=<%=archivo.getIdArchivo()%>>
                    <%} %>
                    
                  </form>
                  <%}else{%>
                  <button class="btn-fav" onclick="location.href = 'InicioSesion.html'" title="Debes iniciar sesión para añadir a favoritos"><i class="fa fa-star"></i></button>
                  <%} %>
                  
                </div>
              
              <form class="download-form" action="ObtenerArchivoServlet" method="post">
              <label for="titulo"><%=archivo.getTitulo()%></label>
              <h1>Descripción</h1>
              <label for="descripcion"><%=archivo.getDescripcion()%></label>
              <h1>Tags</h1>
              <ul class="tags">
                <li><a href="#" class="tag"><%=archivo.getUniversidad()%></a></li>
                <li><a href="#" class="tag"><%=archivo.getGrado()%></a></li>
                <li><a href="#" class="tag">Curso <%=archivo.getCurso()%></a></li>
                <li><a href="#" class="tag">Cuatrimestre <%=archivo.getCuatrimestre()%></a></li>
                <li><a href="#" class="tag"><%=archivo.getAsignatura()%></a></li>
              </ul>
 
              <button class="btn-download" type="submit" title="Descargar el archivo"><i class="fa fa-download"></i> Descargar</button>
              <input type="hidden" name="download-file" value=<%=archivo.getIdArchivo()%>>

            </form>
            </div>
        </div>
      <div class="comentarios">
        <% ArrayList<Comentario> comentarios = (ArrayList<Comentario>) session.getAttribute("comentarios"); %>
        <h1>Comentarios</h1>
        <div class="comentariosHead">
          <label>Numero de comentarios: <%=comentarios.size()%></label>
          
          <%if(session.getAttribute("usuario") != null){%>
            <button onclick="document.getElementById('id01').style.display='block'">Añadir Comentario</button>
          <%}else{%>
            <button onclick="window.location.href='InicioSesion.html'">Añadir Comentario</button>
          <%}%>
          
        </div>
        <table>
          <% 
            for(int i=0; i < comentarios.size(); i++){
          %>
          <tr>
              <td><img src="img/logoInicioSesion.png" id="iconoFiltro"></td>
              <td><%= UsuarioDB.selectUserById(comentarios.get(i).getAutor()).getUsername()%></td>
              <td><%=comentarios.get(i).getTexto()%></td>
              <td>Valoracion: <%=comentarios.get(i).getValoracion()%></td>
          </tr>
          
          <%
            }
          %> 
        </table>
      </div>
    </div>

    <div id="id01" class="modal">
      <span onclick="document.getElementById('id01').style.display='none'" class="close" title="Close Modal">&times;</span>
      <form class="modal-content" action="AddComentarioServlet" method="post">
        <div class="container">
          <h1>Añadir Comentario</h1>
          <div class="inputComentario">
              <textarea placeholder="Comentario" name="texto"></textarea>
            <br>
            <label>Valoracion</label>
            <input type="number" min="1" max="5" placeholder="5" name="valoracion">
            <input type="hidden" name="file" value=<%=archivo.getIdArchivo()%>>
          </div>
          <div class="buttonsComentario">
            <button type="button" class="cancelbtn" onclick="window.location.href='VisualizarArchivo.jsp'">Cancelar</button>
            <button type="submit" class="addbtn">Añadir</button>
          </div>
        </div>
      </form>
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
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                              
    </script>
</body>