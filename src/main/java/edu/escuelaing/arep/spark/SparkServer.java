package edu.escuelaing.arep.spark;

import edu.escuelaing.arep.httpserver.HttpServer;

import java.io.IOException;

public class SparkServer  {

    public static void main(String[] args) throws IOException {

        HttpServer server = new HttpServer();
        server.start();
    }
}
