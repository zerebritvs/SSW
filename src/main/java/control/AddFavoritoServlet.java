
package control;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import modelo.Favorito;
import modelo.FavoritoDB;
import modelo.Usuario;


public class AddFavoritoServlet extends HttpServlet {

    

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
        
        String file = request.getParameter("file");
        Usuario usuario = (Usuario) request.getSession().getAttribute("usuario");
        
        
        Favorito favorito = new Favorito();
        
        favorito.setIdFavorito(usuario.getId());
        favorito.setMiArchivo(Integer.parseInt(file));
        boolean yaEsFav = false;
        //Si el archivo ya es favorito
        if(FavoritoDB.favoritoExists(usuario.getId(), Integer.parseInt(file))){
            
            FavoritoDB.delete(favorito); //elimina el archivo de favoritos
            
            yaEsFav = false;
            
        }else{
            
            FavoritoDB.insert(favorito); //agrega el archivo a favoritos
            yaEsFav = true;
        }
        
            // almacena el atributo yaEsFav en la sesion
            HttpSession session = request.getSession();
            session.setAttribute("yaEsFav", yaEsFav);
            String url = "/VisualizarArchivo.jsp";
            
            RequestDispatcher rs = request.getRequestDispatcher(url);
            rs.include(request, response);
    }

}
