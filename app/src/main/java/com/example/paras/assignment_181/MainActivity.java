package com.example.paras.assignment_181;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.BatteryManager;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {

    TextView textView, textViewBatteryStatus;
    ProgressBar progressBar;
    BroadcastReceiver broadcastReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // creating the instance of the text views and progress bar
        textView = (TextView) findViewById(R.id.tvBatteryLevel);
        progressBar = (ProgressBar) findViewById(R.id.pbBatteryProgressBar);
        textViewBatteryStatus = findViewById(R.id.tvBatteryStatus);

        // creating the object of the broadcast receiver by calling the method MyBatteryBroadcast.
        broadcastReceiver = new MyBatteryBroadcast();
    }

    // on start register the receiver with the intent filter of action of battery changed.
    @Override
    protected void onStart() {
        registerReceiver(broadcastReceiver, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
        super.onStart();
    }

    // on stop unregister the receiver.
    @Override
    protected void onStop() {
        unregisterReceiver(broadcastReceiver);
        super.onStop();
    }

// my battery class which extends the BroadcastReceiver.
    private class MyBatteryBroadcast extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
// integer value to get the intent data with battery manager class with default value as 0
            int batteryLevel = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, 0);
            // set the battery level to the textView and progress bar.
            textView.setText(batteryLevel+getString(R.string.battery_percentage_symbol));
            progressBar.setProgress(batteryLevel);

// get the status of the battery by receiving the intent of ACTION_BATTERY_CHANGED.
            int status = registerReceiver(broadcastReceiver,  new IntentFilter(Intent.ACTION_BATTERY_CHANGED)).getIntExtra(BatteryManager.EXTRA_STATUS, -1);
           // if the battery status is charging set the status of the textview and color as green.
            if (BatteryManager.BATTERY_STATUS_CHARGING == status){
                textViewBatteryStatus.setText("Status: Charging");
                textViewBatteryStatus.setBackgroundColor(Color.GREEN);
            }else {
                // if the status is not charging then set the status to not charging
                textViewBatteryStatus.setText("Status: Not Charging");
                if (batteryLevel > 30){
                    // if battery level is above 30% then yellow background of textView else make it red and set text as low battery.
                    textViewBatteryStatus.setBackgroundColor(Color.YELLOW);
                } else
                {
                    textViewBatteryStatus.setText("Status: Low Battery");
                    textViewBatteryStatus.setBackgroundColor(Color.RED);
                }
            }
        }
    }
}
