
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
import modelo.Usuario;


public class AddComentarioServlet extends HttpServlet {


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
        
        String texto = request.getParameter("texto");
        String valoracion = request.getParameter("valoracion");
        Usuario usuario = (Usuario) request.getSession().getAttribute("usuario");
        String file = request.getParameter("file");
        
        Comentario comentario = new Comentario();
        comentario.setTexto(texto);
        comentario.setValoracion(Integer.parseInt(valoracion));
        comentario.setAutor(usuario.getId());
        comentario.setArchivo(Integer.parseInt(file));
        
        int id = ComentarioDB.insert(comentario);
            
        comentario.setIdComentario(id);
        
        ArrayList<Comentario> comentarios = new ArrayList<>();
        comentarios = ComentarioDB.selectCommentsByFileId(Integer.parseInt(file));
        
        
        Archivo archivo = ArchivoDB.selectFileById(Integer.parseInt(file));
        
        double valoracionMedia = 0;
        int sumaValoracion = 0;
        
        for(int i = 0; i < comentarios.size(); i++){
            sumaValoracion = sumaValoracion + comentarios.get(i).getValoracion();
        }
        
        valoracionMedia = (double) sumaValoracion / comentarios.size();
        
        double valoracionMedia2 = Math.round(valoracionMedia * 100.0) / 100.0;
 
        archivo.setValoracionMedia(valoracionMedia2);
        
        ArchivoDB.updateValoracionMedia(archivo);
        
        // store the user in the session
        HttpSession session = request.getSession();
        session.setAttribute("comentarios", comentarios);
            
        String url = "/VisualizarArchivo.jsp";
        
        RequestDispatcher rs = request.getRequestDispatcher(url);
        rs.include(request, response);
    }


}
