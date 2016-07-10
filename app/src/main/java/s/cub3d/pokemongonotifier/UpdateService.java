package s.cub3d.pokemongonotifier;

import android.app.IntentService;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.content.Intent;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.TextView;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p/>
 * TODO: Customize class - update intent actions, extra parameters and static
 * helper methods.
 */
public class UpdateService extends IntentService {
    // TODO: Rename actions, choose action names that describe tasks that this
    // IntentService can perform, e.g. ACTION_FETCH_NEW_ITEMS
    private static final String ACTION_FOO = "s.cub3d.pokemongonotifier.action.FOO";
    private static final String ACTION_BAZ = "s.cub3d.pokemongonotifier.action.BAZ";

    // TODO: Rename parameters
    private static final String EXTRA_PARAM1 = "s.cub3d.pokemongonotifier.extra.PARAM1";
    private static final String EXTRA_PARAM2 = "s.cub3d.pokemongonotifier.extra.PARAM2";

    public UpdateService() {
        super("UpdateService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Log.d("NathanTesting","Received intent");
        Bundle extras = intent.getExtras();
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);
        int[] appWidgetIds = extras
                .getIntArray(AppWidgetManager.EXTRA_APPWIDGET_IDS);
        update(appWidgetManager, appWidgetIds);
    }

    /**
     * Handle action Foo in the provided background thread with the provided
     * parameters.
     */
    private void handleActionFoo(String param1, String param2) {
        // TODO: Handle action Foo
        throw new UnsupportedOperationException("Not yet implemented");

    }

    /**
     * Handle action Baz in the provided background thread with the provided
     * parameters.
     */
    private void handleActionBaz(String param1, String param2) {
        // TODO: Handle action Baz
        throw new UnsupportedOperationException("Not yet implemented");
    }

    private void update(AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        Log.d("NathanTesting","update");
        for (int i = 0; i < appWidgetIds.length; i++) {
            int appWidgetId = appWidgetIds[i];
            RemoteViews views = new RemoteViews(getPackageName(), R.layout.pokemon_go_widget_initial_layout);

            Intent update = new Intent(this, UpdateService.class);
            update.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, appWidgetIds);

            PendingIntent pendingIntent = PendingIntent.getService(this, 0, update,
                    PendingIntent.FLAG_UPDATE_CURRENT);
            views.setOnClickPendingIntent(R.id.pokemonWidgetFrame, pendingIntent);
            Log.d("NathanTesting", "Set the click Listener?");

            int status = HandleRequests.getServerStatusVariable();
            int color = getResources().getColor(R.color.orange);
            String statusText = "Unknown";
            if(status == 2) {
                color = getResources().getColor(R.color.green);
                statusText = "Online";
            }
            else if(status == 1) {
                color = getResources().getColor(R.color.orange);
                statusText = "Unstable";
            }
            else if(status == 0) {
                color = getResources().getColor(R.color.red);
                statusText = "Offline";
            }
            views.setTextViewText(R.id.statusText, statusText);
            views.setTextColor(R.id.statusText, color);
            views.setInt(R.id.statusBar, "setBackgroundColor", color);
            appWidgetManager.updateAppWidget(appWidgetId, views);
        }
    }

}
