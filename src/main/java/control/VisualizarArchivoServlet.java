
package control;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import modelo.Archivo;
import modelo.ArchivoDB;
import modelo.Comentario;
import modelo.ComentarioDB;
import modelo.FavoritoDB;
import modelo.Usuario;


public class VisualizarArchivoServlet extends HttpServlet {


    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
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
        
        String encoding = request.getCharacterEncoding();
        if(encoding==null){
            request.setCharacterEncoding("UTF-8");
        }
        // get parameters from the request
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        String url = "";
        
        String file = request.getParameter("file");
        
        Archivo archivo = null;
        archivo = ArchivoDB.selectFileById(Integer.parseInt(file));
        
        //Actualiza el numero de vistas del archivo
        archivo.setNumVistas(archivo.getNumVistas() + 1);
        ArchivoDB.updateNumVistas(archivo);
        
        ArrayList<Comentario> comentarios = new ArrayList<>();
        comentarios = ComentarioDB.selectCommentsByFileId(Integer.parseInt(file));
        
        boolean yaEsFav = false;
        if(request.getSession().getAttribute("usuario") != null){
            Usuario usuario = (Usuario) request.getSession().getAttribute("usuario");
            yaEsFav = FavoritoDB.favoritoExists(usuario.getId(), Integer.parseInt(file));
        }
        

        
        // store the user in the session
        HttpSession session = request.getSession();
        session.setAttribute("comentarios", comentarios);
        session.setAttribute("archivo", archivo);
        session.setAttribute("yaEsFav", yaEsFav);
        url = "/VisualizarArchivo.jsp";
        
        RequestDispatcher rs = request.getRequestDispatcher(url);
                rs.include(request, response);
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
        
    }


}
