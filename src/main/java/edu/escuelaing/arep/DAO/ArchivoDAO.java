package edu.escuelaing.arep.DAO;

import edu.escuelaing.arep.Model.Archivo;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


/**
 * Propiedades de la clase ArchivoDAO
 */
public class ArchivoDAO extends DBConection{

    private static final String cs_FIND_BY_NAME = "SELECT * FROM ARCHIVO WHERE nombre = ?";

    /**
     * Constructor de la clase
     */
    public ArchivoDAO() {
    }

    /**
     * Método encargado de consultar un archivo por su nombre
     * @param name de tipo String
     * @return archivo
     * @throws Exception
     */
    public Archivo findByName(String name) throws Exception {
        Archivo result = null;
        PreparedStatement pr = null;
        ResultSet rs = null;

        try {
            pr = getConection().prepareStatement(cs_FIND_BY_NAME);
            pr.setObject(1,name);
            rs = pr.executeQuery();
            if(rs.next()){
                result = getArchivo(rs);
            }
        } catch (SQLException throwables) {
            System.out.println(throwables);
            throw new Exception("Error SQL : "+throwables.getMessage());
        }finally {
            if(pr!=null)
                pr.close();
            if(rs!=null)
                rs.close();
        }
        return result;
    }

    /**
     * Método encargado de crear el nuevo archivo
     * @param rs de tipo ResultSet
     * @return archivo
     * @throws SQLException
     */
    private Archivo getArchivo(ResultSet rs) throws SQLException {
        Archivo archivo = new Archivo();

        archivo.setNombre(rs.getString("nombre"));
        archivo.setTipo(rs.getString("tipo"));
        archivo.setDescripcion(rs.getString("descripcion"));
        return archivo;
    }
}
