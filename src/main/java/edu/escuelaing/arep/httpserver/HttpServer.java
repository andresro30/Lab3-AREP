package edu.escuelaing.arep.httpserver;


import edu.escuelaing.arep.DAO.ArchivoDAO;
import edu.escuelaing.arep.Model.Archivo;
import edu.escuelaing.arep.spark.Spark;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;
import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 * Propiedades de la clase HttpServer
 */
public class HttpServer {

    private boolean running;
    private int port;
    private Spark spark;


    /**
     * Constructor de la clase
     * @param port
     * @param spark
     */
    public HttpServer(int port,Spark spark) {
        this.port = port;
        this.spark = spark;
        running = false;
    }


    /**
     * Constructor de la clase
     * @param spark de tipo Spark
     */
    public HttpServer(Spark spark) {
        this.port = getPort();
        this.spark = spark;
        running = false;
    }

    /**
     * Método encargado de iniciar el servidor
     */
    public void start() {
        try {
            ServerSocket serverSocket = null;
            try {
                serverSocket = new ServerSocket(port);
            } catch (IOException e) {
                System.err.println("Could not listen on port: " + port);
                System.exit(1);
            }
            running = true;
            while (running) {
                Socket clientSocket = null;
                try {
                    System.out.println("Listo para recibir ...");
                    clientSocket = serverSocket.accept();
                } catch (IOException e) {
                    System.err.println("Accept failed.");
                    System.exit(1);
                }
                processRequest(clientSocket);
            }
            serverSocket.close();
        } catch (IOException ex) {
            Logger.getLogger(HttpServer.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    /**
     * Método encargado de procesar las peticiones del servidor
     * @param clientSocket de tipo Socket
     * @throws IOException
     */
    public void processRequest(Socket clientSocket) throws IOException {
        BufferedReader in = new BufferedReader(
                new InputStreamReader(clientSocket.getInputStream()));
        String inputLine;
        Map<String, String> request = new HashMap<>();
        boolean requestLineReady = false;
        while ((inputLine = in.readLine()) != null) {
            if (!requestLineReady) {
                request.put("requestLine", inputLine);
                requestLineReady = true;
            } else {
                String[] entry = createEntry(inputLine);
                if (entry.length > 1) {
                    request.put(entry[0], entry[1]);
                }
            }
            if (!in.ready()) {
                break;
            }
        }
        System.out.println("RequestLine: " + request.get("requestLine"));

        createResponse(clientSocket.getOutputStream(), request.get("requestLine"));
        in.close();
    }


    /**
     * Método encargado de crear la entidad
     * @param rawEntry de tipo String
     * @return
     */
    private String[] createEntry(String rawEntry) {
        return rawEntry.split(":");
    }

    /**
     * Método encargado de crear la respuesta del servidor
     * @param out de tipo OutputStream
     * @param request de tipo String
     */
    private void createResponse(OutputStream out, String request) {
        PrintWriter printWriter = new PrintWriter(out);
        String[] resource = response(request);
        String value = "";
        String type = "";
        String outputLine;

        if (resource != null) {
            value = resource[0];
            type = resource[1];

        }

        if(type.equals("txt")){
            String descripcion = "El archivo no está en la Base de Datos";
            Archivo archivo;
            try {
                archivo = new ArchivoDAO().findByName(value);
                if(archivo!=null){
                    descripcion = archivo.getDescripcion();
                }
            } catch (Exception e) {
                descripcion = "El archivo no está en la Base de Datos";
            }

            if(!descripcion.equals("El archivo no está en la Base de Datos"))
                outputLine = spark.getResource(value,descripcion);
            else {
                try {
                    outputLine = spark.getStaticResourcePath(value,type,out,printWriter);
                } catch (Exception e) {
                    outputLine = notFound(value);
                }
            }
        }
        else {
            try {
                outputLine = spark.getStaticResourcePath(value,type,out,printWriter);
            } catch (Exception e) {
                outputLine = notFound(value);
            }
        }

        if(outputLine==null)
            outputLine = notFound(value);

        System.out.println(outputLine);
        printWriter.println(outputLine);
        printWriter.close();
    }

    /**
     * Método que contiene la respuesta de tipo  notFound
     * @param value de tipo String
     * @return respuesta del servidor
     */
    private String notFound(String value){
        String outputLine = "HTTP/1.1 404 Not Found\r\n"
                + "Content-Type: text/html\r\n"
                + "\r\n"
                + "<!DOCTYPE html>\n"
                + "<html>\n"
                + "<head>\n"
                + "<meta charset=\"UTF-8\">\n"
                + "<title>Web Server</title>\n"
                + "</head>\n"
                + "<body>\n"
                + "<h1>El recurso "+value +" no fue encontrado</h1>\n"
                + "</body>\n"
                + "</html>\n";
        return outputLine;
    }

    /**
     * Método encragado de clasificar la información de la petición
     * @param re de tipo String
     * @return String[] con la información clasificada
     */
    private String[] response(String re) {
        String[] info;
        info = null;
        if (re != null) {
            info =new String[2];
            String[] values = re.split(" ");
            String resource = values[1].replace("/", "");
            if (resource.contains(".")) {
                String[] data = resource.split("\\.");
                info[0] = data[0];
                info[1] = data[1];
            } else {
                info[0] = resource;
                info[1] = "text";
            }
        }
        return info;
    }

    /**
     * Método encargado de obtener el puerto por donde corre la aplicación
     * @return puerto
     */
    private int getPort() {
        if (System.getenv("PORT") != null) {
            return Integer.parseInt(System.getenv("PORT"));
        }
        return 36000; // returns default port if heroku-port isn't set (i.e. on localhost)
    }
}
