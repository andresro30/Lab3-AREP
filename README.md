# Lab3-AREP

Servidor Web implementado desde cero sin usar frameworks de conexión web. Responde a peticiones de archivos de tipo:
* txt
* html
* js
* jpg

Para usar esta servidor puede ingresar a esta [página](https://arep-webserver.herokuapp.com). Cuando ingrese a ese link debe aparece una pantalla como esta:

![]()

Solo debe leer las instrucciones e ingresar el conjunto de números que desee operar. Cuando de click en el botón verá los resultados

![](https://github.com/andresro30/SparkWebCalculadora-Lab2AREP/blob/master/images/resultado.PNG)

## Pre-requisitos
* [Maven](https://maven.apache.org/) : Herramienta encargada de la construcción del proyecto y el manejo de dependencias
* [Git](https://git-scm.com/) : Sistema de control de versiones.
* [JDK](https://www.oracle.com/technetwork/es/java/javase/downloads/index.html) : Herramienta de Desarrollo para Java 

Es importante verificar que usted cuente con los siguientes programas 
* Versión de Maven:
```
mvn -version
```
* Versión de Git:
```
git --version
```
* Versión del JDK:
```
java -version
```


## Instalación
Para instalar el programa debe descargar el repositorio usando el siguiente comando desde su consola CMD
```
git clone https://github.com/andresro30/Lab3-AREP.git
```
## Ejecución
Para compilar el proyecto debe use el siguiente comando cuando acceda al directorio SparkWebCalculadora
```
mvn package
```
Cuando ya el proyecto ha compilado, puede proceder a ejecutarlo, use el siguiente comando sobre el directorio SparkWebCalculadora
```
java -cp target/WebServer-1.0-SNAPSHOT.jar edu.escuelaing.arep.spark.SparkServer
```
## Visualiación del programa
Para acceder de forma local al programa solo debes acceder a un navegador web y buscar la siguiente dirección:
```
localhost:36000/
```
Tenga presente que primero debe ejecutar la el programa.

![]()

## Ejecución de pruebas
Para correr las pruebas debe usar el siguiente comando
```
mvn test
```

## Construido con
* [Maven](https://maven.apache.org/) : Herramienta encargada de la construcción del proyecto y el manejo de dependencias
* [Spark](https://spark.apache.org/docs/0.9.1/java-programming-guide.html) : Framwework para desarrollo de aplicaciones web
* [Git](https://git-scm.com/) : Sistema de control de versiones.
* [CircleCI](https://circleci.com/) : Integración contínua 
* [Heroku](https://dashboard.heroku.com/apps) : Despliegue contínuo. 
* Java : Lenguaje de Programación.
## Autor
* **Andrés Rocha** - [andresro30](https://github.com/andresro30)

## Licencia 
Este proyecto está licenciado por Apache License 2.0 - mira aquí [LICENSE.md](LICENSE.txt) los detalles
