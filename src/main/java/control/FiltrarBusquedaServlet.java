
package control;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import modelo.TagTreeDB;

/**
 *
 * @author Mario
 */
@WebServlet(name = "FiltrarBusquedaServlet", urlPatterns = {"/FiltrarBusquedaServlet"})
public class FiltrarBusquedaServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try ( PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet FiltrarBusquedaServlet</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet FiltrarBusquedaServlet at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

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
        response.setContentType("text/html;charset=UTF-8");
        
        String uniSelect = request.getParameter("uniSelect");
        String gradoSelect = request.getParameter("gradoSelect");
        String cursoSelect = request.getParameter("cursoSelect");
        String asigSelect = request.getParameter("asigSelect");
        
        
        
        ArrayList<String> unis = TagTreeDB.getOptions("root");
        
        HttpSession session = request.getSession();
        if (uniSelect != null) {
            session.setAttribute("uniSelect", uniSelect);
            session.setAttribute("gradoSelect", "0");
        } else if (gradoSelect != null) {
            session.setAttribute("gradoSelect", gradoSelect);        
            session.setAttribute("cursoSelect", "0");        
        } else if (cursoSelect != null) {
            session.setAttribute("cursoSelect", cursoSelect);
            session.setAttribute("asigSelect", "0");
        } else if (asigSelect != null) {
            session.setAttribute("asigSelect", asigSelect);
        }
        
        
        ArrayList<String> grados = new ArrayList<>();
        ArrayList<String> cursos = new ArrayList<>();
        ArrayList<String> asigs = new ArrayList<>();
        
        if (!"0".equals(uniSelect) && uniSelect !=null) {
            String uni = unis.get(Integer.parseInt(uniSelect)-1);
            for (int i = 0; i < TagTreeDB.getOptions(uni).size(); i++) {
                grados.add(TagTreeDB.getOptions(uni).get(i));
            }
            session.setAttribute("gradoData", grados);
            session.setAttribute("cursoData", cursos);
            session.setAttribute("asigData", asigs);
            
        }
        
        if (gradoSelect != "0" && gradoSelect!=null) {
            
            grados = (ArrayList<String>)session.getAttribute("gradoData");
            String grado = grados.get(Integer.parseInt(gradoSelect)-1);
            for (int i = 0; i < TagTreeDB.getOptions(grado).size(); i++) {
                cursos.add(TagTreeDB.getOptions(grado).get(i));
            }
            session.setAttribute("cursoData", cursos);
            session.setAttribute("asigData", asigs);
        }
        
        if (!"0".equals(cursoSelect) && cursoSelect!=null) {
            
            cursos = (ArrayList<String>)session.getAttribute("cursoData");
            String curso = cursos.get(Integer.parseInt(cursoSelect)-1);
            for (int i = 0; i < TagTreeDB.getOptions(curso).size(); i++) {
                asigs.add(TagTreeDB.getOptions(curso).get(i));
            }
            session.setAttribute("asigData", asigs);
        }
        
        String url = "/MainPage.jsp";

        RequestDispatcher rs = request.getRequestDispatcher(url);
        rs.forward(request, response);
    }

}
