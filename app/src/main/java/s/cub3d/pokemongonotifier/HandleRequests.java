package s.cub3d.pokemongonotifier;

import android.util.Log;

import org.json.JSONException;

/**
 * Created by solys on 7/9/2016.
 */
public class HandleRequests implements HandleResponseInterface {

    private int serverStatus = -1;

    public int getServerStatusVariable() {
        return this.serverStatus;
    }

    public void requestServerStatus() {
        Request createUserRequest = new Request(this);
        createUserRequest.executeGetRequest();
        Log.d("++++++++++++++++++", "Requested");
    }



    @Override
    public void handleResponse(String response) throws JSONException {
        String value = response.split("h2")[1].split("=")[1].split("\"")[1];
        Log.d("aaaaaaaaaaaaaaaaaaaaaaa", value);
    }

    @Override
    public void handleError() {
        Log.d("Uhhhhh", "Error!!!?!");
    }
}
