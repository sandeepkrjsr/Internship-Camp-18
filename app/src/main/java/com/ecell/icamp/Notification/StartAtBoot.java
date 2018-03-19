package com.ecell.icamp.Notification;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by 1505560 on 02-Jan-18.
 */

public class StartAtBoot extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        context.startService(new Intent(BackgroundService.class.getName()));
    }
}
