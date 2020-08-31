package edu.escuelaing.arep.httpserver;


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

public class HttpServer {

    private boolean running;
    private int port;


    public HttpServer(int port) {
        this.port = port;
        running = false;
    }

    public HttpServer() {
        this.port = getPort();
        running = false;
    }

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


    private String[] createEntry(String rawEntry) {
        return rawEntry.split(":");
    }

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

        if(type.equals("jpg") ){
            try {
                getImagen("src/main/resource/images/"+value+".jpg",out);
            } catch (Exception e) {
                outputLine = notFound(value);
                System.out.println(outputLine);
                printWriter.println(outputLine);
                printWriter.close();
            }
        }
        else{
            String descripcion = "";
            if(type.equals("txt")){
                //Devolver el contenido del txt
                outputLine = "HTTP/1.1 200 OK\r\n"
                        + "Content-Type: text/html\r\n"
                        + "\r\n"
                        + "<!DOCTYPE html>\n"
                        + "<html>\n"
                        + "<head>\n"
                        + "<meta charset=\"UTF-8\">\n"
                        + "<title>Web Server</title>\n"
                        + "</head>\n"
                        + "<body>\n"
                        + "<h1>Bienvenido a tu Servidor Web</h1>\n"
                        + "<h2>Archivo " + value + " : </h2>\n"
                        + "<h2>"+descripcion+"</h2>\n"
                        + "</body>\n"
                        + "</html>\n";
            }
            else if (type.equals("html") || type.equals("js")) {
                try {
                    outputLine = getIndex("src/main/resource/"+value+"."+type,type);
                } catch (Exception e) {
                    outputLine = notFound(value);
                }
            }
            else
                outputLine = notFound(value);

            System.out.println(outputLine);
            printWriter.println(outputLine);
            printWriter.close();
        }
    }

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

    private void getImagen(String path, OutputStream out) throws Exception {
        BufferedImage img = null;
        String output = null;
        try {
            PrintWriter response = new PrintWriter(out, true);
            img = ImageIO.read(new File(path));
            output = "HTTP/1.1 200 OK\r\n"
                    + "Content-Type: image/jpeg\r\n";
            response.println(output);
            ImageIO.write(img, "jpg", out);
        } catch (IOException e) {
            throw new Exception("Error: la imagen no fue encontrada");
        }
    }

    private String getIndex(String path,String type) throws Exception{
        System.out.println(path+" "+type);
        StringBuilder outputLine = new StringBuilder();
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(path));
            outputLine.append("HTTP/1.1 200 OK\r\n");
            if(type.equals("html"))
                outputLine.append("Content-Type: text/html\r\n");
            else
                outputLine.append("Content-Type: application/javascript\r\n");

            outputLine.append("\r\n");
            String line = br.readLine();
            while (line != null) {
                outputLine.append(line + "\n");
                line = br.readLine();
            }
        } catch (FileNotFoundException e) {
            throw new Exception("Error: Fichero no encontrado");
        } finally {
            try {
                if (br != null)
                    br.close();
            } catch (Exception e) {
                throw new Exception("Error al cerrar el fichero");
            }
        }
        return outputLine.toString();
    }

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

    private int getPort() {
        if (System.getenv("PORT") != null) {
            return Integer.parseInt(System.getenv("PORT"));
        }
        return 36000; // returns default port if heroku-port isn't set (i.e. on localhost)
    }
}
