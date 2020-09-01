package edu.escuelaing.arep.spark;
import edu.escuelaing.arep.httpserver.HttpServer;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.HashMap;
import java.util.function.BiFunction;

public class Spark {

    private static HashMap<String,String> recursos = new HashMap<>();

    public static void get(String path, BiFunction<String,String,String> f)
    {
        //si el servidor no está corriendo , ponerlo a correr
        //poner f con el nombre de resourcePath en el arreglo de paths funcionales

    }

    /**
     * Método encargado de traer archivos de tipo texto desde la Base de Datos
     * @param value de tipo String
     * @param descripcion de tipo String
     * @return
     */
    public static String getResource(String value,String descripcion){
        String outputLine = "HTTP/1.1 200 OK\r\n"
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
                + "<h3>Archivo " + value + " : </h3>\n"
                + "<h3>"+descripcion+"</h3>\n"
                + "</body>\n"
                + "</html>\n";
        return outputLine;
    }

    /**
     * Método encargado de traer archivos estáticos en la aplicación
     * @param value de tipo String
     * @param type de tipo String
     * @param out de tipo OutputStream
     * @param printWriter de tipo PrintWriter
     * @return archivo de tipo String
     * @throws Exception
     */
    public static String getStaticResourcePath(String value,String type, OutputStream out,PrintWriter printWriter) throws Exception {
        String outputLine = null;

        if(recursos.containsKey(value+type)){
            outputLine = recursos.get(value+type);
        }
        else{
            if(type.equals("jpg") ){
                try {
                    getImagen("src/main/resource/images/"+value+".jpg",out);
                    outputLine = "imagen";
                } catch (Exception e) {
                    System.out.println(outputLine);
                    printWriter.println(outputLine);
                    printWriter.close();
                    throw new Exception("Archivo no encontrado");
                }
            }
            else if(type.equals("txt")){
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
                        + "<h3>Archivo " + value + " no tiene está en la Base de Datos </h3>\n"
                        + "</body>\n"
                        + "</html>\n";
            }
            else if (type.equals("html") || type.equals("js")) {
                try {
                    outputLine = getIndex("src/main/resource/"+value+"."+type,type);
                } catch (Exception e) {
                    throw new Exception("Archivo no encontrado");
                }
            }
        }

        if(outputLine!=null && !type.equals("jpg"))
            recursos.put(value+type,outputLine);

        return outputLine;
    }

    /**
     * Método encargado de generar la respuesta de archivos tipo imagen
     * @param path de tipo String
     * @param out de tipo OutputStream
     * @throws Exception
     */
    private static void getImagen(String path, OutputStream out) throws Exception {
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

    /**
     * Méotodo encargado de generar la respuesta de archivos tipo html o js
     * @param path de tipo String
     * @param type de tipo String
     * @return respuesta del servidor
     * @throws Exception
     */
    private static String getIndex(String path, String type) throws Exception{
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
}
