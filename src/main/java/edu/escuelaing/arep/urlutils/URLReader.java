package edu.escuelaing.arep.urlutils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 * Propiedades de la clase URLReader
 */
public class URLReader {

    /**
     * Método principal de la clase
     * @param args de tipo String[]
     */
    public static void main(String[] args) {
        readURL(
                "http://campusvirtual.escuelaing.edu.co/moodle/course/view.php?id=892");
    }

    /**
     * Método encargado de leer una url
     * @param site de tipo String
     */
    public static void readURL(String site) {
        try {
            URL siteUrl = new URL(site);
            try {
                // Crea el objeto que representa una URL
                URL siteURL = new URL(site);
                // Crea el objeto que URLConnection
                URLConnection urlConnection = siteURL.openConnection();
                // Obtiene los campos del encabezado y los almacena en un estructura Map
                Map<String, List<String>> headers = urlConnection.getHeaderFields();
                // Obtiene una vista del mapa como conjunto de pares <K,V>
                // para poder navegarlo
                Set<Map.Entry<String, List<String>>> entrySet = headers.entrySet();
                // Recorre la lista de campos e imprime los valores
                for (Map.Entry<String, List<String>> entry : entrySet) {
                    String headerName = entry.getKey();
                    //Si el nombre es nulo, significa que es la linea de estado
                    if (headerName != null) {
                        System.out.print(headerName + ":");
                    }
                    List<String> headerValues = entry.getValue();
                    for (String value : headerValues) {
                        System.out.print(value);
                    }
                    System.out.println("");
                    //System.out.println("");
                }
                System.out.println("-------message-body------");
                BufferedReader reader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                String inputLine = null;
                while ((inputLine = reader.readLine()) != null) {
                    System.out.println(inputLine);
                }
            } catch (IOException x) {
                System.err.println(x);
            }
        } catch (MalformedURLException ex) {
            Logger.getLogger(URLScanner.class.getName()).log(Level.SEVERE, null, ex);
        }
    }


}