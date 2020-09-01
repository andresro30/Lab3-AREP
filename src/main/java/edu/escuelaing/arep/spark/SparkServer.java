package edu.escuelaing.arep.spark;

import edu.escuelaing.arep.httpserver.HttpServer;

import java.io.IOException;


/**
 * Propiedades de la clase SparkServer
 */
public class SparkServer  {

    /**
     * Método principal que corre la aplicación
     * @param args de tipo String[]
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
        HttpServer server = new HttpServer();
        server.start();
    }
}
