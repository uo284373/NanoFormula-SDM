package com.example.nanoformula.API;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;

public class Api {

    public static String makeQuery(String query) {
        try {
            // Crea una URL a partir de la cadena de la API
            URL url = new URL(query);

            // Abre una conexión HTTP
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            // Establece el método HTTP (GET, POST, etc.)
            conn.setRequestMethod("GET");

            conn.connect();

            if(conn.getResponseCode() != 200){
                throw  new RuntimeException("Ocurrió un error: " + conn.getResponseCode());
            } else{
                StringBuilder sb = new StringBuilder();
                Scanner sc = new Scanner(url.openStream());

                while (sc.hasNext()){
                    sb.append(sc.nextLine());
                }
                sc.close();

                return sb.toString();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public static String[] getStandings() {
        try {
            // Define la URL de la API a la que deseas llamar
            String apiUrl = "https://ergast.com/api/f1/current/constructorstandings.json";

            // Crea una URL a partir de la cadena de la API
            URL url = new URL(apiUrl);

            // Abre una conexión HTTP
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            // Establece el método HTTP (GET, POST, etc.)
            conn.setRequestMethod("GET");

            conn.connect();

            if(conn.getResponseCode() != 200){
                throw  new RuntimeException("Ocurrió un error: " + conn.getResponseCode());
            } else{
                StringBuilder sbEscuderias = new StringBuilder();
                Scanner sc = new Scanner(url.openStream());

                while (sc.hasNext()){
                    sbEscuderias.append(sc.nextLine());
                }
                sc.close();


                // Define la URL de la API a la que deseas llamar
                apiUrl = "https://ergast.com/api/f1/current/driverstandings.json";

                // Crea una URL a partir de la cadena de la API
                url = new URL(apiUrl);

                // Abre una conexión HTTP
                conn = (HttpURLConnection) url.openConnection();

                // Establece el método HTTP (GET, POST, etc.)
                conn.setRequestMethod("GET");

                conn.connect();

                if(conn.getResponseCode() != 200){
                    throw  new RuntimeException("Ocurrió un error: " + conn.getResponseCode());
                } else {
                    StringBuilder sbPilotos = new StringBuilder();
                    sc = new Scanner(url.openStream());

                    while (sc.hasNext()) {
                        sbPilotos.append(sc.nextLine());
                    }
                    sc.close();

                    return new String[] {sbEscuderias.toString(), sbPilotos.toString()};
                }


            }

        } catch (Exception e) {
            e.printStackTrace();
        }



        return null;
    }

}
