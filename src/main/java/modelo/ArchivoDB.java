
package modelo;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import javax.servlet.http.Part;


public class ArchivoDB {
    
    private static ArrayList<Archivo> listaArchivos = new ArrayList<>();
    
    /**
     * Insertar un nuevo archivo en la base de datos
     * @param archivo
     * @return 
     */
    public static int insert(Archivo archivo) throws IOException {

        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        String query = "INSERT INTO ARCHIVO (PROPIETARIO, NOMBRE, TITULO, DESCRIPCION, UNIVERSIDAD, GRADO, CURSO, CUATRIMESTRE, ASIGNATURA, NUMVISTAS, FECHASUBIDA, NUMDESCARGAS, VALORACIONMEDIA, CONTENIDO) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try {
            ps = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, archivo.getPropietario());
            ps.setString(2, archivo.getNombre());
            ps.setString(3, archivo.getTitulo());
            ps.setString(4, archivo.getDescripcion());
            ps.setString(5, archivo.getUniversidad());
            ps.setString(6, archivo.getGrado());
            ps.setInt(7, archivo.getCurso());
            ps.setInt(8, archivo.getCuatrimestre());
            ps.setString(9, archivo.getAsignatura());
            ps.setInt(10, archivo.getNumVistas());
            ps.setDate(11, archivo.getFechaSubida());
            ps.setInt(12, archivo.getNumDescargas());
            ps.setDouble(13, archivo.getValoracionMedia());
            ps.setBlob(14, archivo.getContenido().getInputStream());
            
            TagTreeDB.insert(archivo.getUniversidad(), archivo.getGrado(),      //insertar los tags al arbol de filtros
                    archivo.getCurso() + "", archivo.getAsignatura());
            
            
            int res = 0;
            
            ps.executeUpdate();
            
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()){
                res = rs.getInt(1);
            }
            ps.close();
            pool.freeConnection(connection);
            return res;
            
            } catch (SQLException e) {
            e.printStackTrace();
            return 0;
            }
    }
    
    /**
     * Seleccionar un usuario de la base de datos a partir de su nombre de usuario
     * @return listaArchivos
     */
    public static ArrayList<Archivo> getAllArchivos() {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        String query = "SELECT * FROM Archivo";
        try {
            ps = connection.prepareStatement(query);
            rs = ps.executeQuery();
            Archivo archivo = null;
            listaArchivos.clear();

            while (rs.next()) {
                archivo = new Archivo(rs.getString("titulo"), rs.getString("nombre"), rs.getInt("idArchivo"),
                    rs.getInt("propietario"), rs.getString("descripcion"), rs.getString("universidad"),
                    rs.getString("grado"), rs.getInt("curso"), rs.getInt("cuatrimestre"),
                    rs.getString("asignatura"), rs.getInt("numVistas"), rs.getDate("fechaSubida"),
                    rs.getInt("numDescargas"), rs.getDouble("valoracionMedia"), null);
                
                listaArchivos.add(archivo);
                
            }
            
            rs.close();
            ps.close();
            pool.freeConnection(connection);
            return listaArchivos;
            
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
          }       
    }
    
    /**
     * Busca un archivo en la base de datos en funcion del nombre
     * 
     * @param name
     * @return listaArchivos
     */
    public static ArrayList<Archivo> buscarArchivoNombre(String name) {
        
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        String query = "SELECT * FROM Archivo WHERE titulo LIKE ?";
        try {
            ps = connection.prepareStatement(query);
            ps.setString(1, "%"+name+"%");
            rs = ps.executeQuery();
            Archivo archivo = null;
            listaArchivos.clear();
            
            while (rs.next()) {
                archivo = new Archivo();
                archivo.setIdArchivo(rs.getInt("idArchivo"));
                archivo.setTitulo(rs.getString("titulo"));
                archivo.setNombre(rs.getString("Nombre"));
                archivo.setPropietario(rs.getInt("Propietario"));
                archivo.setDescripcion(rs.getString("Descripcion"));
                archivo.setUniversidad(rs.getString("Universidad"));
                archivo.setGrado(rs.getString("Grado"));
                archivo.setCurso(rs.getInt("Curso"));
                archivo.setCuatrimestre(rs.getInt("Cuatrimestre"));
                archivo.setAsignatura(rs.getString("Asignatura"));
                archivo.setNumVistas(rs.getInt("numVistas"));
                archivo.setFechaSubida(rs.getDate("FechaSubida"));
                archivo.setNumDescargas(rs.getInt("numDescargas"));
                archivo.setValoracionMedia(rs.getInt("ValoracionMedia"));
                
                
                listaArchivos.add(archivo);
                
            }
            rs.close();
            ps.close();
            pool.freeConnection(connection);
            return listaArchivos;
            
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
          }       
    }
    
    
    public static ArrayList<Archivo> filtrarArchivos(ArrayList<String> tags, ArrayList<Archivo> archivos) {
        
        ArrayList<Archivo> filtrados = new ArrayList<>();
        for (Archivo archivo : archivos) {
            switch(tags.size()) {
                case 0: 
                    filtrados.add(archivo);
                    break;
                case 1:
                    if (archivo.getUniversidad().equals(tags.get(0))) {
                        filtrados.add(archivo);
                    }
                    break;
                case 2:
                    if (archivo.getUniversidad().equals(tags.get(0))&& archivo.getGrado().equals(tags.get(1))) {
                        filtrados.add(archivo);
                    }
                    break;
                case 3:
                    if (archivo.getUniversidad().equals(tags.get(0))&& archivo.getGrado().equals(tags.get(1))
                            &&archivo.getCurso() == Integer.parseInt(tags.get(2))) {
                        filtrados.add(archivo);
                    }
                    break;
                case 4:
                    if (archivo.getUniversidad().equals(tags.get(0))&& archivo.getGrado().equals(tags.get(1))
                            &&archivo.getCurso() == Integer.parseInt(tags.get(2)) && archivo.getAsignatura().equals(tags.get(3))) {
                        filtrados.add(archivo);
                        
                    }
                    break;
            }
        }
        return filtrados;
    }
    
    /**
     * Ordena los archivos en funcion del parametro orden
     * 
     * @param orden
     * @return listaArchivos
     */
    public static ArrayList<Archivo> ordenarArchivos(String orden, ArrayList<Archivo> archivos){
        
        switch(orden){
            case "0":
                Collections.sort(archivos, new Comparator<Archivo>(){
                    public int compare(Archivo a1, Archivo a2){
                        if(a1.getValoracionMedia() == a2.getValoracionMedia())
                            return 0;
                        return a2.getValoracionMedia() < a1.getValoracionMedia() ? -1 : 1;
                    }
                });
                
                break;
                
                
            case "1":
                
                Collections.sort(archivos, new Comparator<Archivo>(){
                    public int compare(Archivo a1, Archivo a2){
                        if(a1.getNumDescargas() == a2.getNumDescargas())
                            return 0;
                        return a2.getNumDescargas() < a1.getNumDescargas() ? -1 : 1;
                    }
                });
                
                break;
                
                
            case "2":
                Collections.sort(archivos, new Comparator<Archivo>(){
                    public int compare(Archivo a1, Archivo a2){
                        if(a1.getNumVistas() == a2.getNumVistas())
                            return 0;
                        return a2.getNumVistas() < a1.getNumVistas() ? -1 : 1;
                    }
                });
                
                break;
                
            case "3":
                Collections.sort(archivos, new Comparator<Archivo>() {
                    public int compare(Archivo a1, Archivo a2) {
                        return a2.getFechaSubida().compareTo(a1.getFechaSubida());
                    }
                });
                
                break;
                
            case "4":
                Collections.sort(archivos, new Comparator<Archivo>() {
                    public int compare(Archivo a1, Archivo a2) {
                        return a1.getTitulo().compareTo(a2.getTitulo());
                    }
                });
                
                break;
                
            default:
             
        }
        
        return archivos;
    }
    
    
    /**
     * Seleccionar un archivo de la base de datos a partir de su id de archivo
     * @param id
     * @return 
     */
    public static Archivo selectFileById(int idArchivo) {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        String query = "SELECT * FROM Archivo WHERE idArchivo = ?";
        try {
            ps = connection.prepareStatement(query);
            ps.setInt(1, idArchivo);
            rs = ps.executeQuery();
            Archivo archivo = null;

            if (rs.next()) {
                archivo = new Archivo();
                archivo.setIdArchivo(rs.getInt("idArchivo"));
                archivo.setPropietario(rs.getInt("propietario"));
                archivo.setTitulo(rs.getString("titulo"));
                archivo.setNombre(rs.getString("nombre"));
                archivo.setDescripcion(rs.getString("descripcion"));
                archivo.setUniversidad(rs.getString("universidad"));
                archivo.setGrado(rs.getString("grado"));
                archivo.setCurso(rs.getInt("curso"));
                archivo.setCuatrimestre(rs.getInt("cuatrimestre"));
                archivo.setAsignatura(rs.getString("asignatura"));
                archivo.setNumVistas(rs.getInt("numVistas"));
                archivo.setFechaSubida(rs.getDate("fechaSubida"));
                archivo.setNumDescargas(rs.getInt("numDescargas"));
                archivo.setValoracionMedia(rs.getDouble("valoracionMedia"));
                
                
            }
            rs.close();
            ps.close();
            pool.freeConnection(connection);
            return archivo;
            
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
          }       
    }
    
    /**
     * Obtener un archivo de la base de datos a partir de su id de archivo
     * @param id
     * @param respuesta
     */
    public static void getFile(int idArchivo, OutputStream respuesta) throws IOException {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        String query = "SELECT contenido FROM Archivo WHERE idArchivo = ?";
        try {
            ps = connection.prepareStatement(query);
            ps.setInt(1, idArchivo);
            rs = ps.executeQuery();
            

            if (rs.next()) {
                Blob blob = rs.getBlob("contenido");
                if(!rs.wasNull() && blob.length() > 1){
                    InputStream archivo = blob.getBinaryStream();
                    byte[] buffer = new byte[1000];
                    int len = archivo.read(buffer);
                    while(len != -1){
                        respuesta.write(buffer, 0, len);
                        len = archivo.read(buffer);
                    }
                    archivo.close();
                }
            }
            
            pool.freeConnection(connection);
     
        } catch (SQLException e) {
            e.printStackTrace();
          }       
    }
    
    /**
     * Actualiza el numero de vistas de un archivo en la base de datos
     * @param archivo
     * 
     */
    public static void updateNumVistas(Archivo archivo) throws IOException {

        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        String query = "UPDATE ARCHIVO SET NumVistas = ? WHERE idArchivo = ?";

        try {
            ps = connection.prepareStatement(query);
            ps.setInt(1, archivo.getNumVistas());
            ps.setInt(2, archivo.getIdArchivo());
            
            ps.executeUpdate();
            

            ps.close();
            pool.freeConnection(connection);
            
            
            } catch (SQLException e) {
            e.printStackTrace();
            
            }
    }
    
    /**
     * Actualiza el numero de descargas de un archivo en la base de datos
     * @param archivo
     * 
     */
    public static void updateNumDescargas(Archivo archivo) throws IOException {

        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        String query = "UPDATE ARCHIVO SET NumDescargas = ? WHERE idArchivo = ?";

        try {
            ps = connection.prepareStatement(query);
            ps.setInt(1, archivo.getNumDescargas());
            ps.setInt(2, archivo.getIdArchivo());
            
            ps.executeUpdate();
            

            ps.close();
            pool.freeConnection(connection);
            
            
            } catch (SQLException e) {
            e.printStackTrace();
            
            }
    }
    
    /**
     * Actualiza la valoracion media de un archivo en la base de datos
     * @param archivo
     * 
     */
    public static void updateValoracionMedia(Archivo archivo) throws IOException {

        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        String query = "UPDATE ARCHIVO SET valoracionMedia = ? WHERE idArchivo = ?";

        try {
            ps = connection.prepareStatement(query);
            ps.setDouble(1, archivo.getValoracionMedia());
            ps.setInt(2, archivo.getIdArchivo());
            
            ps.executeUpdate();
            

            ps.close();
            pool.freeConnection(connection);
            
            
            } catch (SQLException e) {
            e.printStackTrace();
            
            }
    }
    
}
