package bonzai.com.appread;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.app.NotificationCompat;

public class AlertRecive extends BroadcastReceiver {






    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onReceive(Context context, Intent intent) {
NotifiacionHelper helper= new NotifiacionHelper(context);
 NotificationCompat.Builder nb=helper.getChannelNotifiacion("Alarma 1","Se activo la alarma uno");
 helper.getManager().notify(1,nb.build());


    }
}
