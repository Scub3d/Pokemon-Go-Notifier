package s.cub3d.pokemongonotifier;

import android.app.IntentService;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.content.Intent;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
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

    private int status = -1;

    public UpdateService() {
        super("UpdateService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Bundle extras = intent.getExtras();
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);
        int[] appWidgetIds = extras
                .getIntArray(AppWidgetManager.EXTRA_APPWIDGET_IDS);
        int toRequest = extras.getInt("toRequest?");
        int status = extras.getInt("ServerStatus");
        this.status = status;
        if(toRequest==1){
            HandleRequests hr = new HandleRequests();
            hr.requestServerStatus();
            hr.setThisContext(this.getApplicationContext());
            hr.setAppWidgetIds(appWidgetIds);
            RemoteViews views = new RemoteViews(getPackageName(), R.layout.pokemon_go_widget_initial_layout);
            views.setViewVisibility(R.id.requestStatus, View.VISIBLE);
            for (int i = 0; i < appWidgetIds.length; i++) {
                appWidgetManager.updateAppWidget(appWidgetIds[i], views);
            }
            update(appWidgetManager, appWidgetIds, status);
        } else {
            RemoteViews views = new RemoteViews(getPackageName(), R.layout.pokemon_go_widget_initial_layout);
            views.setViewVisibility(R.id.requestStatus, View.INVISIBLE);
            for (int i = 0; i < appWidgetIds.length; i++) {
                appWidgetManager.updateAppWidget(appWidgetIds[i], views);
            }
            update(appWidgetManager, appWidgetIds, status);
        }
    }

    private void update(AppWidgetManager appWidgetManager, int[] appWidgetIds, int status) {
        for (int i = 0; i < appWidgetIds.length; i++) {
            int appWidgetId = appWidgetIds[i];
            RemoteViews views = new RemoteViews(getPackageName(), R.layout.pokemon_go_widget_initial_layout);

            Intent update = new Intent(this, UpdateService.class);
            update.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, appWidgetIds);
            update.putExtra("toRequest?", 1);
            update.putExtra("ServerStatus", this.status);

            PendingIntent pendingIntent = PendingIntent.getService(this, 0, update,
                    PendingIntent.FLAG_UPDATE_CURRENT);
            views.setOnClickPendingIntent(R.id.pokemonWidgetFrame, pendingIntent);

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
