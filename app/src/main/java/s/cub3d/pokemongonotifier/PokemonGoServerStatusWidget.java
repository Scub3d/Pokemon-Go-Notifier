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

    private Context _context;

    public static String PACKAGE_NAME;

    private static AlarmManager alarm;

    private static int[] widgetIds;

    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // Useful for setting loading spinner
        PACKAGE_NAME = context.getPackageName();
        widgetIds = appWidgetIds;
        Intent update = new Intent(context, UpdateService.class);
        update.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, appWidgetIds);
        update.putExtra("ServerStatus",-1);
        update.putExtra("toRequest?",1);

        Log.d("NathanTesting","update Success");
        context.startService(update);
        Log.d("NathanTesting", "Started the service!");

//        if(alarmEnabled){
//            Log.d("NathanTesting", "Started the Alarm");
//            final Intent intent = new Intent(context, UpdateService.class);
//            intent.putExtra("ServerStatus",-1);
//            intent.putExtra("toRequest?",1);
//            final PendingIntent pending = PendingIntent.getService(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
//            final AlarmManager alarm = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
////            alarm.cancel(pending);
//            long interval = 1000*60;
//            alarm.cancel(pending);
//            alarm.setRepeating(AlarmManager.ELAPSED_REALTIME, SystemClock.elapsedRealtime() + interval,interval, pending);
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

    public static void createAlarm(Context context) {
        Log.d("NathanTesting", "+ Created Alarm" );
        if(widgetIds == null){
            Log.d("NathanTesting", "AW HELL NAW");
        }
        final Intent intent = new Intent(context, UpdateService.class);
        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, widgetIds);
        intent.putExtra("ServerStatus",-1);
        intent.putExtra("toRequest?",1);
        final PendingIntent pending = PendingIntent.getService(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        if(alarm == null){
            alarm = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        }
        long interval = 1000*60;
        alarm.setRepeating(AlarmManager.ELAPSED_REALTIME, SystemClock.elapsedRealtime() + interval,interval, pending);
    }

    public static void cancelAlarm(Context context) {
        Log.d("NathanTesting", "+ Canceled Alarm");
        final Intent intent = new Intent(context, UpdateService.class);
        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, widgetIds);
        intent.putExtra("ServerStatus",-1);
        intent.putExtra("toRequest?",1);
        final PendingIntent pending = PendingIntent.getService(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        if(alarm == null) {
            final AlarmManager alarm = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
            alarm.cancel(pending);
            return;
        }
        alarm.cancel(pending);
    }

}
