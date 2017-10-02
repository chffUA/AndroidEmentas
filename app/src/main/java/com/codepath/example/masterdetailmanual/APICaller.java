package com.codepath.example.masterdetailmanual;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.TreeSet;

public class APICaller {

    //cache
    String jsonStr = null;

    public String callAPI() {
        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;


        String res = null;

        try {
            URL url = new URL("http://services.web.ua.pt/sas/ementas?date=week&format=json");

            // Create the request to OpenWeatherMap, and open the connection
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            // Read the input stream into a String
            InputStream inputStream = urlConnection.getInputStream();
            StringBuffer buffer = new StringBuffer();
            if (inputStream == null) {
                // Nothing to do.
                res = null;
            }
            reader = new BufferedReader(new InputStreamReader(inputStream));

            String line;
            while ((line = reader.readLine()) != null) {
                // Since it's JSON, adding a newline isn't necessary (it won't affect parsing)
                // But it does make debugging a *lot* easier if you print out the completed
                // buffer for debugging.
                buffer.append(line + "\n");
            }

            if (buffer.length() == 0) {
                // Stream was empty.  No point in parsing.
                res = null;
            }
            res = buffer.toString();
        } catch (IOException e) {
            Log.e("PlaceholderFragment", "Error ", e);
            // If the code didn't successfully get the weather data, there's no point in attempting
            // to parse it.
            res = null;

        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (final IOException e) {
                    Log.e("PlaceholderFragment", "Error closing stream", e);
                }
            }
        }
        return res;
    }

    private String dayTranslator(String original) {
        if (original.equals("Sunday"))
            return "Domingo";
        else if (original.equals("Monday"))
            return "Segunda-feira";
        else if (original.equals("Tuesday"))
            return "Terça-feira";
        else if (original.equals("Wednesday"))
            return "Quarta-feira";
        else if (original.equals("Thursday"))
            return "Quinta-feira";
        else if (original.equals("Friday"))
            return "Sexta-feira";
        else if (original.equals("Saturday"))
            return "Sábado";
        else return "(Dia não reconhecido)";
    }

    public void getData()
            throws JSONException {

        //cache
        if (jsonStr == null)
            jsonStr = callAPI();

        //parsing
        JSONObject json = new JSONObject(jsonStr);

        JSONArray menu = json.getJSONObject("menus").getJSONArray("menu"); //menu completo

        TreeSet<String> canteens = new TreeSet<String>();

        //lista de cantinas sem repeticoes
        for (int i = 0; i < menu.length(); i++) {
            JSONObject entry = menu.getJSONObject(i);
            canteens.add(entry.getJSONObject("@attributes").getString("canteen"));
        }

        //construir menu para cada cantina
        for (String canteen : canteens) {
            String weeklyMenu = "";

            //percorrer o json
            for (int i = 0; i < menu.length(); i++) {
                JSONObject entry = menu.getJSONObject(i);

                //cantina correspondente
                if (entry.getJSONObject("@attributes").getString("canteen").equals(canteen)) {
                    //titulo
                    weeklyMenu += "~~ ";
                    weeklyMenu += dayTranslator(entry.getJSONObject("@attributes").getString("weekday"));
                    weeklyMenu += " ~~ ";
                    weeklyMenu += entry.getJSONObject("@attributes").getString("meal");
                    weeklyMenu += " ~~\n\n";

                    //ementa
                    String disabled = entry.getJSONObject("@attributes").getString("disabled");

                    if (!disabled.equals("0")) { //sem ementa

                        weeklyMenu += disabled + "\n";

                    } else { //existe ementa

                        JSONArray items = entry.getJSONObject("items").getJSONArray("item");
                        for (int x = 0; x < items.length(); x++) {
                            if (!items.getString(x).contains("{")) //nao definido, nao incluir
                                weeklyMenu += items.getString(x) + "\n";
                        }

                    }

                    weeklyMenu += "\n";

                }
            }

            //ementa feita, adicionar aos itens
            Item.addItem(canteen, weeklyMenu);
        }

    }


}