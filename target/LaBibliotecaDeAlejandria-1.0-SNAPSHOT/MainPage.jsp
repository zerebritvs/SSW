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
        <% 
            ArrayList<Archivo> archivos = new ArrayList<>(); 
          
            if(request.getSession().getAttribute("variable") != null){
                  
                archivos = ArchivoDB.buscarArchivoNombre((String)request.getSession().getAttribute("variable"));
                
            }else{
                 archivos = ArchivoDB.getAllArchivos();
            }  
            for (int i = 0; i < archivos.size(); i++) {
                TagTreeDB.insert(archivos.get(i).getUniversidad(), archivos.get(i).getGrado(), 
                archivos.get(i).getCurso() + "", archivos.get(i).getAsignatura());
            }
            ArrayList<String> tags = new ArrayList<>();
        %>
    <div class="filtros">
            <div title="Filtrar"><img src="img/filtro.png" alt="filtro icono" id="iconoFiltro"></div>
            <form class="box" name="uniSelectForm" action="FiltrarBusquedaServlet">
                <select name="uniSelect" onchange="javascript:document.uniSelectForm.submit()">
                    <% ArrayList<String> options = TagTreeDB.getOptions("root");
                    int selectedUni = 0;
                    if (session.getAttribute("uniSelect")!= null) {
                        selectedUni = Integer.parseInt((String)session.getAttribute("uniSelect")); 
                    }%>
                    <option value="0">-- Seleccione universidad --</option>
                    <%for (int j = 0; j < (options.size()); j++) {
                        if (selectedUni == j+1) { %>
                            <option value=<%=j+1%> selected><%=options.get(j)%></option>
                            <%tags.add(options.get(j));%>
                        <% }else{ %>
                            <option value=<%=j+1%>><%=options.get(j)%></option>
                    <%}}%>
                </select>
              </form>
            <form class="box" name="gradoSelectForm" action="FiltrarBusquedaServlet">
                <div name="gradoData">
                <select name="gradoSelect" onchange="javascript:document.gradoSelectForm.submit()">
                    <option value="0">-- Seleccione Grado --</option>
                    <% ArrayList<String> grados = (ArrayList<String>)session.getAttribute("gradoData");
                    if (grados != null) {
                    int selectedGrado = 0;
                    if (session.getAttribute("gradoSelect")!= null) {
                        selectedGrado = Integer.parseInt((String)session.getAttribute("gradoSelect")); 
                    }%>
                    <%for (int j = 0; j < (grados.size()); j++) {
                        if (selectedGrado == j+1) { %>
                            <option value=<%=j+1%> selected><%=grados.get(j)%></option>
                            <%tags.add(grados.get(j));%>
                        <% }else{ %>
                            <option value=<%=j+1%>><%=grados.get(j)%></option>
                    <%}}}%>
                </select>
                </div>
              </form>
            <form class="box" name="cursoSelectForm" action="FiltrarBusquedaServlet">
                <div name="cursoData">
                <select name ="cursoSelect" onchange="javascript:document.cursoSelectForm.submit()">
                    <option value="0">-- Seleccione Curso --</option>
                  <% ArrayList<String> cursos = (ArrayList<String>)session.getAttribute("cursoData");
                    if (cursos != null) {
                    int selectedCurso = 0;
                    if (session.getAttribute("cursoSelect")!= null) {
                        selectedCurso = Integer.parseInt((String)session.getAttribute("cursoSelect")); 
                    }%>
                    <%for (int j = 0; j < (cursos.size()); j++) {
                        if (selectedCurso == j+1) { %>
                            <option value=<%=j+1%> selected><%=cursos.get(j)%></option>
                            <%tags.add(cursos.get(j));%>
                        <% }else{ %>
                            <option value=<%=j+1%>><%=cursos.get(j)%></option>
                    <%}}}%>
                </select>
                </div>
              </form>
            <form class="box" name="asigSelectForm" action="FiltrarBusquedaServlet">
                <div name="asigData">
                <select name ="asigSelect" onchange="javascript:document.asigSelectForm.submit()">
                    <option value="0">-- Seleccione Asignatura --</option>
                  <% ArrayList<String> asigs = (ArrayList<String>)session.getAttribute("asigData");
                    if (asigs != null) {
                    int selectedAsig = 0;
                    if (session.getAttribute("asigSelect")!= null) {
                        selectedAsig = Integer.parseInt((String)session.getAttribute("asigSelect")); 
                    }%>
                    <%for (int j = 0; j < (asigs.size()); j++) {
                        if (selectedAsig == j+1) { %>
                            <option value=<%=j+1%> selected><%=asigs.get(j)%></option>
                            <%tags.add(asigs.get(j));%>
                        <% }else{ %>
                            <option value=<%=j+1%>><%=asigs.get(j)%></option>
                    <%}}}%>
                </select>
                </div>
              </form>
              <div class="box">
                <select>
                  <option>PDF</option>
                  <option>Word</option>
                  <option>Imagen</option>
                  <option>Presentación</option>
                </select>
              </div>
        
    </div>
    <div class="buscarOrdenar">
          <!-- Search -->
          <div class="wrap">
            <form action="BuscarArchivoServlet" method="post">
            <div class="search" >
                
              <input type="text" name="busqueda" class="searchTerm" placeholder="¿Qué estás buscando?">
              <button type="submit" class="searchButton">
                <img src="img/buscar.png" id="icono">
              </button>
               
            </div>
            </form>
        </div>
        <div class="ordenar">
          <img title="Ordenar" src="img/ordenar.png" alt="ordenar icono" id="icono">
          <form name="formOrdenar" action="BuscarArchivoServlet" method="GET">
          <div class="box">
            <% 
                String orden = (String)request.getSession().getAttribute("orden");
                String option0 = null;
                String option1 = null;
                String option2 = null;
                String option3 = null;
                String option4 = null;
                
                if(orden != null){
                    switch(orden){
                    
                    case "0": option0="selected"; break;
                    case "1": option1="selected"; break;
                    case "2": option2="selected"; break;
                    case "3": option3="selected"; break;
                    case "4": option4="selected"; break;
                    default: option0="selected";
                    
                    }
                }

            %>
            <select name="orden" onchange="javascript:document.formOrdenar.submit();">
              <option value="0" <%=option0%>>Mejor valorados</option>
              <option value="1" <%=option1%>>Más descargados</option>
              <option value="2" <%=option2%>>Más vistos</option>
              <option value="3" <%=option3%>>Más recientes</option>
              <option value="4" <%=option4%>>Alfabéticamente</option>
            </select>
          </div>
          </form>
        </div>
        
    </div>
      
    <div class="resultados">
        
            
        <%
            archivos = ArchivoDB.filtrarArchivos(tags, archivos);
 
            
            if(orden == null){
                archivos = ArchivoDB.ordenarArchivos("0", archivos);
            }else{
                archivos = ArchivoDB.ordenarArchivos(orden, archivos);
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