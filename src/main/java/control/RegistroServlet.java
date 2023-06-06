
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


public class RegistroServlet extends HttpServlet {


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

        // get parameters from the request
        String encoding = request.getCharacterEncoding();
        if(encoding==null){
            request.setCharacterEncoding("UTF-8");
        }
        
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
     
        String email = request.getParameter("email");
        String username = request.getParameter("username");
        String password1 = request.getParameter("password1");
        String password2 = request.getParameter("password2");
        
        String url = "";
        
        
        if(!password1.equals(password2)){
            
            out.println("<script>alert('Las contraseñas no coinciden.'); </script>");
            url = "/Registro.html";
        }else{
                // creamos un nuevo usuario con los parametros del form
            Usuario usuario = new Usuario();
            usuario.setEmail(email);
            usuario.setUsername(username);
            usuario.setPassword(password1);
            
            // comprueba si el correo o el username ya esta en el sistema
            if (UsuarioDB.emailExists(usuario.getEmail()) || UsuarioDB.userExists(usuario.getUsername())) {
                out.println("<script>alert('Ya estás registrado en el sistema.'); </script>");
                
                url = "/InicioSesion.html";
            } else {
                int id = UsuarioDB.insert(usuario);
                usuario.setId(id);
                usuario = UsuarioDB.selectUserByName(username);
                
                url = "/MainPage.jsp";
                
                
                // store the user in the session
                HttpSession session = request.getSession();
                session.setAttribute("usuario", usuario);
            }

        }
        RequestDispatcher rs = request.getRequestDispatcher(url);
            rs.include(request, response);
    }

}
