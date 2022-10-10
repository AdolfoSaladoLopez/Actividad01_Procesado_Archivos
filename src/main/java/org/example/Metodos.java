package org.example;

import java.util.HashMap;

public interface Metodos {
    void showMenu();

    void readNameArchive();

    void createCSV(HashMap<String, Integer> histograma, String nombreFichero);

    HashMap<String, Integer> mainProgram();

    void readHashMap(HashMap<String, Integer> listadoPalabras);


}
