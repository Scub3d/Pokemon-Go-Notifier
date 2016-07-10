package s.cub3d.pokemongonotifier;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;
import android.util.Log;
import android.widget.RemoteViews;

/**
 * Created by Jeremy on 7/8/2016.
 */
public class PokemonGoServerStatusWidget extends AppWidgetProvider {
    private boolean alarmEnabled = true;
    private boolean alarmActive = false;
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        Intent update = new Intent(context, UpdateService.class);
        update.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, appWidgetIds);
        Log.d("NathanTesting","update Success");
        context.startService(update);
        Log.d("NathanTesting", "Started the service");
//        if(alarmEnabled){
//            final Intent intent = new Intent(context, PokemonGoServerStatusWidget.class);
//            final PendingIntent pending = PendingIntent.getService(context, 0, intent, 0);
//            final AlarmManager alarm = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
//            alarm.cancel(pending);
//            long interval = 1000*60;
//            alarm.setRepeating(AlarmManager.ELAPSED_REALTIME, SystemClock.elapsedRealtime(),interval, pending);
//        }

        final int N = appWidgetIds.length;

        // Perform this loop procedure for each App Widget that belongs to this provider
        for (int i=0; i<N; i++) {
            int appWidgetId = appWidgetIds[i];

            // Create an Intent to launch ExampleActivity
            Intent intent = new Intent(context, MainActivity.class);
            PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);

            // Get the layout for the App Widget and attach an on-click listener
            // to the button
            RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.pokemon_go_widget_initial_layout);
//            views.setOnClickPendingIntent(R.id.button, pendingIntent);
//
            // Tell the AppWidgetManager to perform an update on the current app widget
            appWidgetManager.updateAppWidget(appWidgetId, views);
        }
    }

}
