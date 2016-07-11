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
        PACKAGE_NAME = context.getPackageName();
        widgetIds = appWidgetIds;
        Intent update = new Intent(context, UpdateService.class);
        update.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, appWidgetIds);
        update.putExtra("ServerStatus",-1);
        update.putExtra("toRequest?",1);

        context.startService(update);

        final int N = appWidgetIds.length;

        for (int i=0; i<N; i++) {
            int appWidgetId = appWidgetIds[i];

            Intent intent = new Intent(context, MainActivity.class);
            PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);

            RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.pokemon_go_widget_initial_layout);

            appWidgetManager.updateAppWidget(appWidgetId, views);
        }
    }

}
