package s.cub3d.pokemongonotifier;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.RemoteViews;

import org.json.JSONException;

/**
 * Created by solys on 7/9/2016.
 */
public class HandleRequests implements HandleResponseInterface {

    private static int serverStatus = -1;
    private static Context thisContext;
    private static int appWidgetIds[];
    // 0 = red, 1 = yellow, 2 = green

    public static int getServerStatusVariable() {
        return serverStatus;
    }

    public void setThisContext(Context context){
        thisContext = context;
    }

    public void setAppWidgetIds(int[] ids){
        appWidgetIds = ids;
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

        Intent update = new Intent(thisContext, UpdateService.class);
        update.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, appWidgetIds);
        update.putExtra("ServerStatus",this.serverStatus);
        update.putExtra("toRequest?",0);

        thisContext.startService(update);
    }

    @Override
    public void handleError() {
        Log.d("Something Happened", "Error!!!?!");
    }
}
