
package control;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import modelo.Usuario;
import modelo.UsuarioDB;


public class InicioSesionServlet extends HttpServlet {

    
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
            
            request.getSession().invalidate();
            String url = "";
            
            
            if(request.getParameter("parametro").equals("0")){
                url = "/MainPage.jsp";
            }else{
                url = "/InicioSesion.html";
            }
            
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
        
        String encoding = request.getCharacterEncoding();
        if(encoding==null){
            request.setCharacterEncoding("UTF-8");
        }
        // get parameters from the request
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        String url = "";
        //invalida la cuenta si ya esta abierta
        if(request.getSession().getAttribute("usuario") != null){
            
            request.getSession().invalidate();
            url = "/MainPage.jsp";
        }else{
            String username = request.getParameter("username");
            String password = request.getParameter("password");
        
        
            //Validar si las credenciales para iniciar sesion son correctas
            if (!UsuarioDB.validate(username, password)) {
                
                out.println("<script>alert('Nombre de usuario o contraseña incorrectos'); </script>");
                
                url = "/InicioSesion.html";
                
            } else {
                Usuario usuario = UsuarioDB.selectUserByName(username);     //sacar el usuario de la base de datos
                // store the user in the session
                HttpSession session = request.getSession();
                session.setAttribute("usuario", usuario);
                url = "/MainPage.jsp";
                
            }
        }
        
            RequestDispatcher rs = request.getRequestDispatcher(url);
                rs.include(request, response);
        
    }

}
