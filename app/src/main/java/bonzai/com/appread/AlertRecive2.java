package bonzai.com.appread;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.app.NotificationCompat;

public class AlertRecive2 extends BroadcastReceiver {
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onReceive(Context context, Intent intent) {
        NotifiacionHelper helper2= new NotifiacionHelper(context);
        NotificationCompat.Builder nb2=helper2.getChannelNotifiacion("Alarma 2","Se activo la alarma dos");
        helper2.getManager().notify(1,nb2.build());
    }
}
