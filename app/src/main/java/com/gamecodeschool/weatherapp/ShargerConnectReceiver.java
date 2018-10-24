package com.gamecodeschool.weatherapp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.BatteryManager;
import android.util.Log;
import android.widget.Toast;

public class ShargerConnectReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Intent intent1 = new Intent(context, MainActivity.class);
        context.startService(intent1);
        int status = intent.getIntExtra(BatteryManager.EXTRA_STATUS, -1);
        Log.d("Charger", "onReceive: " + status);
        boolean isCharging = status == BatteryManager.BATTERY_STATUS_CHARGING ||
                status == BatteryManager.BATTERY_STATUS_FULL;
        Log.d("Charger", "onReceive: " + isCharging);

        int chargePlug = intent.getIntExtra(BatteryManager.EXTRA_PLUGGED, -1);
        Log.d("Charger", "onReceive: " + chargePlug);

        boolean usbCharge = chargePlug == BatteryManager.BATTERY_PLUGGED_USB;
        Log.d("Charger", "onReceive: " + usbCharge);

        boolean acCharge = chargePlug == BatteryManager.BATTERY_PLUGGED_AC;
        Log.d("Charger", "onReceive: " + acCharge);

        Toast.makeText(context, "heyyyyyyyyyy", Toast.LENGTH_LONG).show();
    }
}
