package edu.escuelaing.arep.Model;

/**
 * Propiedades de la clase Archivo
 */
public class Archivo {
    private String nombre;
    private String tipo;
    private String descripcion;

    /**
     * Constructor de la clase
     */
    public Archivo(){
    }

    /**
     * Retorna el nombre del archivo
     * @return nombre
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Modifica el nombre del archivo
     * @param nombre de tipo String
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Retorna el tipo del archivo
     * @return tipo
     */
    public String getTipo() {
        return tipo;
    }

    /**
     * Modifica el tipo del archivo
     * @param tipo de tipo String
     */
    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    /**
     * Retorna la descripción del archivo
     * @return descripción
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * Modifica la descripción del archivo
     * @param descripcion de tipo String
     */
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}
