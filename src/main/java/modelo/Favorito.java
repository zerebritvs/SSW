
package modelo;

import java.io.Serializable;

/**
 * 
 * Clase JavaBeans que implementa a un archivo favorito relacionado con un usuario
 */
public class Favorito implements Serializable{
    
    private int miArchivo;
    private int idFavorito;
    
    public Favorito(){
        this.miArchivo = -1;
        this.idFavorito = -1;
    }

    public Favorito(int miArchivo, int idFavorito) {
        this.miArchivo = miArchivo;
        this.idFavorito = idFavorito;
    }
    
    public int getMiArchivo() {
        return miArchivo;
    }

    public void setMiArchivo(int miArchivo) {
        this.miArchivo = miArchivo;
    }

    public int getIdFavorito() {
        return idFavorito;
    }

    public void setIdFavorito(int idFavorito) {
        this.idFavorito = idFavorito;
    }
    
}
