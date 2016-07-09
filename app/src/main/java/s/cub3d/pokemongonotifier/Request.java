package s.cub3d.pokemongonotifier;

/**
 * Created by solys on 1/4/2016.
 */
import java.io.BufferedReader;
import java.io.InputStreamReader;

import java.net.HttpURLConnection;
import java.net.URL;
import android.os.AsyncTask;
import android.util.Log;


import org.json.JSONException;

public class Request {
    private String ip = "http://cmmcd.com/PokemonGo/";
    private HandleResponseInterface handleResponseInterface;


    public Request (HandleResponseInterface handleResponseInterface) {
        this.handleResponseInterface = handleResponseInterface;
    }

    public void executeGetRequest() {
        DoGet doGet = new DoGet();
        doGet.execute();
    }

    private void handleResponse(final String info) {
        final String information = info;

        try {
            handleResponseInterface.handleResponse(information);
        } catch (JSONException e) {
            e.printStackTrace();

            Log.d("1q2341235123541253", "OMGOMGOMG");
        }

    }

    private void errorOccurred() {
        handleResponseInterface.handleError();
    }

    private class DoGet extends AsyncTask<Void, Void, String> {
        private static final String TAG = "doGet";
        public String serverURL = Request.this.ip;

        @Override
        protected String doInBackground(Void... params) {
            try {

                URL obj = new URL(serverURL);
                HttpURLConnection con = (HttpURLConnection) obj.openConnection();
                con.setRequestMethod("GET");
                con.setRequestProperty("User-Agent", "Mozilla/5.0");
                con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
                con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

                BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
                String inputLine;
                final StringBuilder response = new StringBuilder();
                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();

                handleResponse(response.toString());

            } catch(Exception ex) {
                Log.e(TAG, "Failed to send HTTP GET request due to: " + ex);
                errorOccurred();
            }
            return null;
        }
    }
}