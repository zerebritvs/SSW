
package modelo;

import java.io.Serializable;
import java.sql.Date;
import javax.servlet.http.Part;

/**
 * 
 * Clase JavaBeans que implementa a un Archivo
 */
public class Archivo implements Serializable{
    
    private String titulo;
    private String nombre;
    private int idArchivo;
    private int propietario;
    private String descripcion;
    private String universidad;
    private String grado;
    private int curso;
    private int cuatrimestre;
    private String asignatura;
    private int numVistas;
    private Date fechaSubida;
    private int numDescargas;
    private double valoracionMedia;
    private Part contenido;

    
    public Archivo() {
        this.titulo = "";
        this.nombre = ""; 
        this.idArchivo = -1;
        this.propietario = -1;
        this.descripcion = "";
        this.universidad = "";
        this.grado = "";
        this.curso = -1;
        this.cuatrimestre = -1;
        this.asignatura = "";
        this.numVistas = 0;
        this.fechaSubida = null;
        this.numDescargas = 0;
        this.valoracionMedia = 0;
        this.contenido = null;
    }
    
    
    public Archivo(String titulo, String nombre, int idArchivo, int propietario, String descripcion, String universidad, String grado, int curso, int cuatrimestre, String asignatura, int numVistas, Date fechaSubida, int numDescargas, double valoracionMedia, Part contenido) {
        
        this.titulo = titulo;
        this.nombre = nombre;
        this.idArchivo = idArchivo;
        this.propietario = propietario;
        this.descripcion = descripcion;
        this.universidad = universidad;
        this.grado = grado;
        this.curso = curso;
        this.cuatrimestre = cuatrimestre;
        this.asignatura = asignatura;
        this.numVistas = numVistas;
        this.fechaSubida = fechaSubida;
        this.numDescargas = numDescargas;
        this.valoracionMedia = valoracionMedia;
        this.contenido = contenido;
    }
    
    
    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }
    
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
    public Part getContenido() {
        return contenido;
    }

    public void setContenido(Part contenido) {
        this.contenido = contenido;
    }
    

    public int getIdArchivo() {
        return idArchivo;
    }

    public void setIdArchivo(int idArchivo) {
        this.idArchivo = idArchivo;
    }

    public int getPropietario() {
        return propietario;
    }

    public void setPropietario(int propietario) {
        this.propietario = propietario;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getUniversidad() {
        return universidad;
    }

    public void setUniversidad(String universidad) {
        this.universidad = universidad;
    }

    public String getGrado() {
        return grado;
    }

    public void setGrado(String grado) {
        this.grado = grado;
    }

    public int getCurso() {
        return curso;
    }

    public void setCurso(int curso) {
        this.curso = curso;
    }

    public int getCuatrimestre() {
        return cuatrimestre;
    }

    public void setCuatrimestre(int cuatrimestre) {
        this.cuatrimestre = cuatrimestre;
    }

    public String getAsignatura() {
        return asignatura;
    }

    public void setAsignatura(String asignatura) {
        this.asignatura = asignatura;
    }

    public int getNumVistas() {
        return numVistas;
    }

    public void setNumVistas(int numVistas) {
        this.numVistas = numVistas;
    }

    public Date getFechaSubida() {
        return fechaSubida;
    }

    public void setFechaSubida(Date fechaSubida) {
        this.fechaSubida = fechaSubida;
    }

    public int getNumDescargas() {
        return numDescargas;
    }

    public void setNumDescargas(int numDescargas) {
        this.numDescargas = numDescargas;
    }

    public double getValoracionMedia() {
        return valoracionMedia;
    }

    public void setValoracionMedia(double valoracionMedia) {
        this.valoracionMedia = valoracionMedia;
    }
    
    
    
    
}
