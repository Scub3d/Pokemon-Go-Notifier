package s.cub3d.pokemongonotifier;

import org.json.JSONException;

/**
 * Created by solys on 1/4/2016.
 */
public interface HandleResponseInterface {
    void handleResponse(String response) throws JSONException;
    void handleError();
}