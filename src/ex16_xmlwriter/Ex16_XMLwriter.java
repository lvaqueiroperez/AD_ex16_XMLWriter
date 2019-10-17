package ex16_xmlwriter;
//importamos las Clases para trabajar en este proyecto, puede que alguna no se use
//(borrar al final)

import ex13_serializacion2.Product;
import ex13_serializacion2.Ex13_serializacion2;
import java.io.*;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
//Para que funcione correctamente, hemos tenido que crear la Clase product como
//"public" en el ejercicio 13, además de ponerla en un fichero de clase separado
//al de la clase principal con el main

public class Ex16_XMLwriter {

    public static void main(String[] args) throws IOException, ClassNotFoundException, XMLStreamException {

        //Para leer los objetos almacenados en un fichero, necesitaremos las
        //Clases FileInputStream y ObjectIntputStream
        FileInputStream leerFich = new FileInputStream("/home/oracle/Desktop/ex13/serial2.txt");
        ObjectInputStream leerOIS = new ObjectInputStream(leerFich);

        //tenemos que almacenar los objetos en una variable Object ó Product,
        //de manera que podamos acceder a sus getters/setters
        //Si almacenamos el objeto en una variable tipo "Product", nos pedirá
        //castear la lectura a "Product"
        //En cambio, si almacenamos el objeto en una variable tipo "Object", no
        //Escribir todos los datos en un xml de manera automática
        File fich = new File("/home/oracle/Desktop/ex16/products.xml");
        FileWriter fichFW = new FileWriter(fich);

        XMLOutputFactory xmlOF = XMLOutputFactory.newInstance();
        XMLStreamWriter xmlSW = xmlOF.createXMLStreamWriter(fichFW);

        //Object puede tomar cualquier valor por ser la clase primigenia(?)
        //Utilizaremos esta variable para almacenar nuestros objetos y comprobar
        //que el fichero ha llegado a su fin
        /*
         se creará un xml simple con su declaración, una etiqueta raíz,
         una etiqueta que englobe a cada objeto y dentro de ellas los 3 atributos
         de los objetos (etiqueta - valor)
         */
        xmlSW.writeStartDocument("1.0");
        //raiz
        xmlSW.writeStartElement("Products");

        Product obj = (Product) leerOIS.readObject();
        int cont = 0;
        String codigo;
        String desc;
        String precio;
        //Hay algún problema con el bucle o con la forma de obtener las variables?
        //los métodos de escritura xml aceptan variables, pero no llamadas a getters???
        while (obj != null) {
            cont++;
            //para cada objeto una etiqueta que lo englobe:
            xmlSW.writeStartElement("Producto_" + Integer.toString(cont));
            //Cada atributo su etiqueta y valor:
            xmlSW.writeStartElement("Codigo");
            xmlSW.writeCharacters(obj.getCodigo());
            xmlSW.writeEndElement();
            xmlSW.writeStartElement("Descripion");
            xmlSW.writeCharacters(obj.getDescripcion());
            xmlSW.writeEndElement();
            xmlSW.writeStartElement("Precio");
            xmlSW.writeCharacters(Double.toString(obj.getPrecio()));
            xmlSW.writeEndElement();
            xmlSW.writeEndElement();
            
            obj = (Product) leerOIS.readObject();

        }
        xmlSW.writeEndElement();
        xmlSW.writeEndDocument();

        xmlSW.close();
        fichFW.close();

    }

}
