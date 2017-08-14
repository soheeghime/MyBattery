package personal.android.riela.mybatterywidget;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.Log;

public class AlarmReceiver extends BroadcastReceiver{
    private static final String TAG = "AlarmReceiver";
    private AlarmManager alarmManager;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d("TEST", "------- onReceive !!! ");

        widgetUpdate(context);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void widgetUpdate(Context context) {
        Log.d(TAG, "------ [widgetUpdate 2222 ] ");
        Class<BatteryOneWidgetProvider> providerClass = BatteryOneWidgetProvider.class;
        int[] ids = AppWidgetManager.getInstance(context).getAppWidgetIds(new ComponentName(context, providerClass));
        if (ids.length != 0) {
            widgetUpdate(context, providerClass, ids);
        }else{
            cancelAlarm(context);
        }
    }

    public void widgetUpdate(Context context, Class<BatteryOneWidgetProvider> provider, int[] ids) {
        Intent intent = new Intent(context, provider);
        intent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, ids);
        context.sendBroadcast(intent);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void cancelAlarm(Context context) {
        Log.d(TAG, "[cancelAlarm]  hasAlarm()  : " + hasAlarm(context));
        if(hasAlarm(context)){
            Intent intent = new Intent(context, AlarmReceiver.class);
            PendingIntent pIntent = PendingIntent.getBroadcast(context, MainActivity.MY_ALARM_REQUEST_CODE, intent, PendingIntent.FLAG_UPDATE_CURRENT);

            alarmManager.cancel(pIntent);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public boolean hasAlarm(Context context) {
        alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        if(alarmManager!=null) {
            return alarmManager.getNextAlarmClock() != null ? true : false;
        }
        return false;
    }
}
