package edu.escuelaing.arep.DAO;

import java.sql.*;

/**
 * Propiedades de la clase DBConection
 */
public class DBConection {
    private String nombre = "d7euoks7l1crhg";
    private String host = "ec2-3-217-87-84.compute-1.amazonaws.com";
    private String usuario = "rfgtppxzdcwhll";
    private String clave = "f31c38de5d364bb9e7bad3efacf0ea87d657226e581d78cad45f6a270ec44377";

    /**
     * Método encragado de geenrar la conexión a la base de datos
     * @return variable de tipo Connection
     */
    public Connection getConection() {
        Connection connection = null;
        try {
            try {
                Class.forName("org.postgresql.Driver");
            } catch (ClassNotFoundException ex) {
                System.out.println("Error al registrar el driver de PostgreSQL: " + ex);
            }
            connection = DriverManager.getConnection("jdbc:postgresql://"+host+":5432/"+nombre,usuario,clave);
            boolean valid = connection.isValid(50000);
            System.out.println(valid ? "TEST OK" : "TEST FAIL");
        } catch (java.sql.SQLException sqle) {
            System.out.println("Error: " + sqle);
        }
        return connection;
    }
}
