package personal.android.riela.mybatterywidget;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;
import android.util.Log;
import android.widget.RemoteViews;

/**
 * Created by riela on 2017. 8. 13..
 */

public class BatteryOneWidgetProvider extends AppWidgetProvider {

    private static final String TAG = "WidgetProvider";
    private int percentage;
    private Context context;

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);

        Log.d(TAG, "------ [onReceive] : ");
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {

        this.context = context;
        int batteryLevel = getBattery();
        Log.d(TAG, "------ [onUpdate] batteryLevel : " + batteryLevel);


        if(batteryLevel >=0 ) {

            for (int i = 0; i < appWidgetIds.length; i++) {


                int appWidgetId = appWidgetIds[i];
                RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget_one_layout);
                views.setTextViewText(R.id.percentage, Integer.toString(batteryLevel) + "%");
                appWidgetManager.updateAppWidget(appWidgetId, views);
            }
        }
    }

    public int getBattery() {
        if(context != null) {
            IntentFilter ifilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
            Intent batteryStatus = context.registerReceiver(null, ifilter);

            // Are we charging / charged?
            int status = batteryStatus.getIntExtra(BatteryManager.EXTRA_STATUS, -1);
            boolean isCharging = status == BatteryManager.BATTERY_STATUS_CHARGING || status == BatteryManager.BATTERY_STATUS_FULL;

            // 현재 배터리 수준 확인
            int level = batteryStatus.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
            int scale = batteryStatus.getIntExtra(BatteryManager.EXTRA_SCALE, -1);

            float batteryPct = level / (float) scale * 100;
            return (int) batteryPct;
        }else{
            return -1;
        }
    }
}
