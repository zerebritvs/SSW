
package modelo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;


public class FavoritoDB {
    private static ArrayList<Favorito> listaFavoritos = new ArrayList<>();
    
    /**
     * Insertar un nuevo favorito en la base de datos
     * @param favorito
     * @return 
     */
    public static int insert(Favorito favorito) {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        String query = "INSERT INTO FAVORITO "
                + "(IDFAVORITO, MIARCHIVO) "
                + "VALUES (?, ?)";
        
        try {
            ps = connection.prepareStatement(query);
            
            ps.setInt(1, favorito.getIdFavorito());
            ps.setInt(2, favorito.getMiArchivo());
            
            int res = ps.executeUpdate();
   
            ps.close();
            
            return res;
            } catch (SQLException e) {
            e.printStackTrace();
            return 0;
            }
    }
    
        /**
     * Elimina un favorito de la base de datos
     * @param favorito
     * @return 
     */
    public static int delete(Favorito favorito) {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        String query = "DELETE FROM FAVORITO "
                + "WHERE IDFAVORITO = ? and MIARCHIVO = ? ";
        
        try {
            ps = connection.prepareStatement(query);
            
            ps.setInt(1, favorito.getIdFavorito());
            ps.setInt(2, favorito.getMiArchivo());
            
            int res = ps.executeUpdate();
   
            ps.close();
            
            return res;
            } catch (SQLException e) {
            e.printStackTrace();
            return 0;
            }
    }
    
    /**
     * Comprueba si ya existe un favorito
     * @param idFavorito
     * @param miArchivo
     * @return 
     */
    public static boolean favoritoExists(int idFavorito, int miArchivo){
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        String query = "SELECT * FROM Favorito "
        + "WHERE idFavorito = ? and miArchivo = ?";
        try {
            ps = connection.prepareStatement(query);
            ps.setInt(1, idFavorito);
            ps.setInt(2, miArchivo);
            rs = ps.executeQuery();
            boolean res = rs.next();
            rs.close();
            ps.close();
            pool.freeConnection(connection);
            
            return res;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    /**
     * Seleccionar archivos favoritos de la base de datos a partir de su id de usuario
     * @param idUsuario
     * @return 
     */
    public static ArrayList<Favorito> selectFavoritosByUserId(int idUsuario) {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        String query = "SELECT * FROM Favorito WHERE idFavorito = ?";
        try {
            ps = connection.prepareStatement(query);
            ps.setInt(1, idUsuario);
            rs = ps.executeQuery();
            Favorito favorito = null;
            listaFavoritos.clear();

            while (rs.next()) {
                favorito = new Favorito(rs.getInt("miArchivo"), rs.getInt("idFavorito"));
                
                listaFavoritos.add(favorito);
            }
            
            rs.close();
            ps.close();
            pool.freeConnection(connection);
            return listaFavoritos;
            
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
          }       
    } 
}
