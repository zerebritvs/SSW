
package control;

import java.io.IOException;
import java.io.PrintWriter;



import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import modelo.Archivo;
import modelo.ArchivoDB;
import modelo.Usuario;


@MultipartConfig
public class SubirArchivoServlet extends HttpServlet {

    
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String encoding = request.getCharacterEncoding();
        if(encoding==null){
            request.setCharacterEncoding("UTF-8");
        }
        
        // get parameters from the request
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        
        String titulo = request.getParameter("nombre");
        String universidad = request.getParameter("universidad");
        String grado = request.getParameter("grado");
        String curso = request.getParameter("curso");
        String cuatrimestre = request.getParameter("cuatrimestre");
        String asignatura = request.getParameter("asignatura");
        String descripcion = request.getParameter("descripcion");
        Part contenido = request.getPart("archivo");
        String nombre = contenido.getSubmittedFileName();
        
        long millis = System.currentTimeMillis();
        java.sql.Date fechaSubida = new java.sql.Date(millis);
        Usuario usuario = (Usuario) request.getSession().getAttribute("usuario");

        
        String url = "";
        
        if(!curso.equals("1") && !curso.equals("2") && !curso.equals("3") && !curso.equals("4") && !curso.equals("5") && !curso.equals("6")){
            out.println("<script>alert('Curso tiene que estar entre 1 y 6.'); </script>");
            url = "/SubirArchivo.html";
            
        }else if(!cuatrimestre.equals("1") && !cuatrimestre.equals("2")){
            out.println("<script>alert('Cuatrimestre tiene que ser 1 o 2.'); </script>");
            url = "/SubirArchivo.html";
            
        }else{
            
            Archivo archivo = new Archivo();
            archivo.setPropietario(usuario.getId());
            archivo.setTitulo(titulo);
            archivo.setNombre(nombre);
            archivo.setDescripcion(descripcion);
            archivo.setUniversidad(universidad);
            archivo.setGrado(grado);  
            archivo.setCurso(Integer.parseInt(curso));
            archivo.setCuatrimestre(Integer.parseInt(cuatrimestre));
            archivo.setAsignatura(asignatura);
            archivo.setFechaSubida(fechaSubida);
            archivo.setContenido(contenido);

            int id = ArchivoDB.insert(archivo);
            
            archivo.setIdArchivo(id);
            
            url = "/MainPage.jsp";
        }

        RequestDispatcher rs = request.getRequestDispatcher(url);
        rs.include(request, response);
    }

}
