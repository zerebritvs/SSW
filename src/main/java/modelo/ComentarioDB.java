
package modelo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;


public class ComentarioDB {
    private static ArrayList<Comentario> listaComentarios = new ArrayList<>();
    
    /**
     * Insertar un nuevo comentario en la base de datos
     * @param comentario
     * @return 
     */
    public static int insert(Comentario comentario) {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        String query = "INSERT INTO COMENTARIO "
                + "(CONTENIDO, VALORACION, AUTOR, ARCHIVO) "
                + "VALUES (?, ?, ?, ?)";
        
        try {
            ps = connection.prepareStatement(query,
            Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, comentario.getTexto());
            ps.setInt(2, comentario.getValoracion());
            ps.setInt(3, comentario.getAutor());
            ps.setInt(4, comentario.getArchivo());
            int res = ps.executeUpdate();
   
            ps.close();
            
            return res;
            } catch (SQLException e) {
            e.printStackTrace();
            return 0;
            }
    }
    
     /**
     * Obtener todos los comentarios
     * @return listaComentarios
     */
    public static ArrayList<Comentario> getAllComentarios() {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        String query = "SELECT * FROM Comentario";
        try {
            ps = connection.prepareStatement(query);
            rs = ps.executeQuery();
            Comentario comentario = null;
            listaComentarios.clear();

            while (rs.next()) {
                comentario = new Comentario(rs.getInt("idComentario"),
                    rs.getString("contenido"), rs.getInt("valoracion"), rs.getInt("autor") ,rs.getInt("archivo"));
                
                listaComentarios.add(comentario);
                
            }
            
            rs.close();
            ps.close();
            pool.freeConnection(connection);
            return listaComentarios;
            
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
          }       
    }
    
    /**
     * Seleccionar comentarios de la base de datos a partir de su id de archivo
     * @param id
     * @return 
     */
    public static ArrayList<Comentario> selectCommentsByFileId(int ArchivoId) {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        String query = "SELECT * FROM Comentario WHERE archivo = ?";
        try {
            ps = connection.prepareStatement(query);
            ps.setInt(1, ArchivoId);
            rs = ps.executeQuery();
            Comentario comentario = null;
            listaComentarios.clear();

            while (rs.next()) {
                comentario = new Comentario(rs.getInt("idComentario"),
                    rs.getString("contenido"), rs.getInt("valoracion"), rs.getInt("autor") ,rs.getInt("archivo"));
                
                listaComentarios.add(comentario);
                
            }
            
            rs.close();
            ps.close();
            pool.freeConnection(connection);
            return listaComentarios;
            
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
          }       
    } 
}
