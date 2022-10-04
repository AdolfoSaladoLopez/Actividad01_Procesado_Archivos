package org.example;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class Main {

    public static String nameArchive;
    public static HashMap<String, Integer> listadoHash = new HashMap<>();

    public static void main(String[] args) {

        showMenu();
        listadoHash = mainProgram();
        readHahsMap(listadoHash);
        createCSV(listadoHash, nameArchive);

    }

    public static void createCSV(HashMap<String, Integer> historiograma, String nombreFichero) {
        try (BufferedWriter writer = Files.newBufferedWriter(Path.of(nombreFichero + "_histograma.csv"))) {
            CSVPrinter printer = new CSVPrinter(writer,
                    CSVFormat.DEFAULT);

            /* Recorre el hash */
            historiograma.forEach((key, value) -> {
                if (key.length() > 2) {
                    try {
                        printer.printRecord(key, value);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            });

            System.out.println();
            System.out.println("Fichero CSV generado correctamente.");

            printer.flush();
            printer.close();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void readHahsMap(HashMap<String, Integer> listadoPalabras) {

        System.out.println();
        System.out.println("El histograma queda de la siguiente manera: ");

        listadoPalabras.forEach((key, value) -> {
            if (key.length() > 2) {
                System.out.println(key + " " + value);
            }
        });
    }

    public static void readNameArchive() {
        System.out.print("Introduzca el nombre del fichero a leer: ");
        var sc = new Scanner(System.in);

        nameArchive = sc.nextLine();
    }

    public static HashMap<String, Integer> mainProgram() {
        var listadoHash = new HashMap<String, Integer>();
        Scanner almacenPalabras = new Scanner("");

        BufferedReader br = null;

        try {
            /* Crea un buffer a partir de un fichero. En este caso, 'prueba.txt' */
            br = new BufferedReader(new FileReader(nameArchive + ".txt"));

            /* Guardamos una línea concreta del fichero */
            String lineaFichero = br.readLine();

            System.out.println();
            System.out.println("El fichero contiene el siguiente texto: ");

            while (lineaFichero != null) {
                System.out.println("\t" + lineaFichero);

                Scanner delimitador = new Scanner(lineaFichero);
                almacenPalabras = delimitador.useDelimiter("[\\s,.:()?!¿¡«'<>-]");

                /* Avanza una línea del fichero */
                lineaFichero = br.readLine();

                while (almacenPalabras.hasNext()) {
                    String palabra = almacenPalabras.next();

                    /* Comprueba que existan palabras en el HashMap */
                    if (listadoHash.containsKey(palabra)) { //En el caso de que lo contenga
                        listadoHash.replace(palabra, (listadoHash.get(palabra) + 1)); // Actualiza las veces que aparece.
                    } else {
                        listadoHash.put(palabra, 1); // Añade la palabra
                    }
                }
            }

        } catch (FileNotFoundException ex) {
            System.out.println(ex);
            System.out.println("El fichero no existe.");
            readNameArchive();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return listadoHash;

    }

    public static void showMenu() {
        System.out.println("¡Bienvenid@! Este software permite leer un fichero y transformarlo a CSV.");
        readNameArchive();
    }
}
