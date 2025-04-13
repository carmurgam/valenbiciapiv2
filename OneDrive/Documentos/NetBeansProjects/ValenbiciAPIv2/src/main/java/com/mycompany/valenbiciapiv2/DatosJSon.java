package com.mycompany.valenbiciapiv2;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;

/**
 *
 * @author cmgtr.
 */
public class DatosJSon {
    private static String API_URL;
    private String datos = ""; // para mostrar en el jTextArea los datos de las estaciones
    private String[] values;   // para añadir los datos de las estaciones Valenbici a la BDD
    private int numEst;

    public DatosJSon(int nE) {
        numEst = nE;
        datos = "";
        API_URL = "https://valencia.opendatasoft.com/api/explore/v2.1/catalog/datasets/valenbisi-disponibilitat-valenbisidsiponibilidad/records?f=json&location=39.46447,-0.39308&distance=10&limit=" + nE;

        values = new String[numEst];
        for (int i = 0; i < numEst; i++)
            values[i] = "";
    }

    public void mostrarDatos(int nE) {
        numEst = nE;
        datos = "";
        API_URL = "https://valencia.opendatasoft.com/api/explore/v2.1/catalog/datasets/valenbisi-disponibilitat-valenbisidsiponibilidad/records?f=json&location=39.46447,-0.39308&distance=10&limit=" + nE;

        values = new String[numEst];
        for (int i = 0; i < numEst; i++)
            values[i] = "";

        if (API_URL.isEmpty()) {
            setDatos(getDatos().concat("La URL de la API no está especificada."));
            return;
        }

        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpGet request = new HttpGet(API_URL);
            HttpResponse response = httpClient.execute(request);
            HttpEntity entity = response.getEntity();

            if (entity != null) {
                String result = EntityUtils.toString(entity);

                try {
                    JSONObject jsonObject = new JSONObject(result);
                    JSONArray resultsArray = jsonObject.getJSONArray("results");

                    for (int i = 0; i < resultsArray.length(); i++) {
                        JSONObject estacion = resultsArray.getJSONObject(i);

                        String direccion = estacion.optString("address", "Sin dirección");
                        int disponibles = estacion.optInt("available", 0);
                        int libres = estacion.optInt("free", 0);
                        int total = disponibles + libres;

                        values[i] = direccion + "," + disponibles + "," + libres + "," + total;

                        datos += "Estación " + (i + 1) + ":\n";
                        datos += "Dirección: " + direccion + "\n";
                        datos += "Bicis disponibles: " + disponibles + "\n";
                        datos += "Anclajes libres: " + libres + "\n";
                        datos += "Total: " + total + "\n\n";
                    }

                } catch (org.json.JSONException e) {
                    setDatos(getDatos().concat("Error al procesar los datos JSON: " + e.getMessage()));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return;
    }

    /**
     * Devuelve los datos de una estación como array: [direccion, disponibles, libres, total]
     */
    public String[] getEstacion(int i) {
        if (i >= 0 && i < values.length) {
            return values[i].split(",");
        }
        return new String[]{"", "0", "0", "0"};
    }

    // Getters y setters
    public String getDatos() {
        return datos;
    }

    public void setDatos(String datos) {
        this.datos = datos;
    }

    public String[] getValues() {
        return values;
    }

    public void setValues(String[] values) {
        this.values = values;
    }

    public int getNumEst() {
        return numEst;
    }

    public void setNumEst(int numEst) {
        this.numEst = numEst;
    }
}

