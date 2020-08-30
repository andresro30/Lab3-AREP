package edu.escuelaing.arep.urlutils;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

public class URLScanner {

    public static void scanURL(String site){
        try{
            URL siteUrl= new URL("http://campusvirtual.escuelaing.edu.co/moodle/course/view.php?id=892");

            System.out.println("Protocol: "+siteUrl.getProtocol());
            System.out.println("Port: "+siteUrl.getPort());
            System.out.println("Host: "+siteUrl.getHost());
            System.out.println("Path: "+siteUrl.getPath());
            System.out.println("File:"+siteUrl.getFile());
            System.out.println("Query: "+siteUrl.getQuery());
            System.out.println("Ref: "+siteUrl.getRef());
            System.out.println("Authority: "+siteUrl.getAuthority());

        }catch (MalformedURLException ex){
            Logger.getLogger(URLScanner.class.getName()).log(Level.SEVERE,null,ex);
        }

    }
}