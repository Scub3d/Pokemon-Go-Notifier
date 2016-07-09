package s.cub3d.pokemongonotifier;

import android.util.Log;

import org.json.JSONException;

/**
 * Created by solys on 7/9/2016.
 */
public class HandleRequests implements HandleResponseInterface {

    private int serverStatus = -1;
    // 0 = red, 1 = yellow, 2 = green

    public int getServerStatusVariable() {
        return this.serverStatus;
    }

    public void requestServerStatus() {
        Request createUserRequest = new Request(this);
        createUserRequest.executeGetRequest();
    }

    @Override
    public void handleResponse(String response) throws JSONException {
        String value = response.split("h2")[1].split("=")[1].split("\"")[1];
        if(value.equals("green"))
            this.serverStatus = 2;
        else if(value.equals("yellow") || value.equals("orange"))
            this.serverStatus = 1;
        else if(value.equals("red"))
            this.serverStatus = 0;

        Log.d("=====================", value);
    }

    @Override
    public void handleError() {
        Log.d("Something Happened", "Error!!!?!");
    }
}
