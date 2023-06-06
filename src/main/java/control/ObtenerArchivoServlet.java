
package control;

import java.io.IOException;
import java.io.OutputStream;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import modelo.Archivo;
import modelo.ArchivoDB;


public class ObtenerArchivoServlet extends HttpServlet {


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
        
        String idArchivo = request.getParameter("download-file");
        
        Archivo archivo = null;
        archivo = ArchivoDB.selectFileById(Integer.parseInt(idArchivo));
        
        response.setContentType("APPLICATION/OCTET-STREAM");
        response.setHeader("Content-Disposition", "attachment; filename=\""+archivo.getNombre()+"\"");
        OutputStream respuesta = response.getOutputStream();
        
        
        
        
        //Actualiza el numero de descargas del archivo
        archivo.setNumDescargas(archivo.getNumDescargas() + 1);
        ArchivoDB.updateNumDescargas(archivo);
        
        //Obtiene el archivo para poder descargarlo
        ArchivoDB.getFile(Integer.parseInt(idArchivo), respuesta);
        respuesta.close();
        response.flushBuffer();
    }


}
