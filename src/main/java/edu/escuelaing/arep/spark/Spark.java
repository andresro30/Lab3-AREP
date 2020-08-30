package edu.escuelaing.arep.spark;
import edu.escuelaing.arep.httpserver.HttpServer;

import java.util.function.BiFunction;

public class Spark {

    public static void get(String path, BiFunction<String,String,String> f)
    {
        //si el servidor no está corriendo , ponerlo a correr
        //poner f con el nombre de resourcePath en el arreglo de paths funcionales

    }

    public static void setStaticResourcePath(String path){}
}
