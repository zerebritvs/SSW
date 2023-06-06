
package control;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import modelo.ArchivoDB;


public class BuscarArchivoServlet extends HttpServlet {

    
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
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        
        String orden = request.getParameter("orden");
        
        
        String url = "";
        
        // store the user in the session
        HttpSession session = request.getSession();

        session.setAttribute("orden", orden);
        url = "/MainPage.jsp";

        RequestDispatcher rs = request.getRequestDispatcher(url);
        rs.forward(request, response);
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
        
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        
        String busqueda = request.getParameter("busqueda");
        
        
        String url = "";
        if (ArchivoDB.buscarArchivoNombre(busqueda).isEmpty()) {
            
            out.println("<script>alert('No se han encontrado coincidencias'); </script");
            url = "/MainPage.jsp";
                
        } else {
            
            // store the user in the session
            HttpSession session = request.getSession();
            
            session.setAttribute("variable", busqueda);
            url = "/MainPage.jsp";
            
            }
            RequestDispatcher rs = request.getRequestDispatcher(url);
                rs.include(request, response);
            
    }


}
