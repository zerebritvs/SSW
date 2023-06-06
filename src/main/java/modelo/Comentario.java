
package modelo;

import java.io.Serializable;

/**
 * 
 * Clase JavaBeans que implementa a un Comentario
 */
public class Comentario implements Serializable{

    private int idComentario;
    private String texto;
    private int valoracion;
    private int autor;
    private int archivo;
    
    public Comentario(){
        this.idComentario = -1;
        this.texto = "";
        this.valoracion = 0;
        this.autor = -1;
        this.archivo = -1;
    }
    
    public Comentario(int idComentario, String texto, int valoracion, int autor, int archivo) {
        this.idComentario = idComentario;
        this.texto = texto;
        this.valoracion = valoracion;
        this.autor = autor;
        this.archivo = archivo;
    }
    

    public int getIdComentario() {
        return idComentario;
    }

    public void setIdComentario(int idComentario) {
        this.idComentario = idComentario;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public int getValoracion() {
        return valoracion;
    }

    public void setValoracion(int valoracion) {
        this.valoracion = valoracion;
    }

    public int getAutor() {
        return autor;
    }

    public void setAutor(int autor) {
        this.autor = autor;
    }
    
    public int getArchivo() {
        return archivo;
    }

    public void setArchivo(int archivo) {
        this.archivo = archivo;
    }
    
    
}
