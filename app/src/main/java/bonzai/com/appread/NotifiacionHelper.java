package bonzai.com.appread;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.ContextWrapper;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.app.NotificationCompat;

public class NotifiacionHelper extends ContextWrapper {
public  static final String CHANNEL1ID= "ID1";
public  static final String NOMBRE1= "channel 1";
public  static final String CHANNEL2ID= "ID2";
public  static final String NOMBRE2= "channel 2";
NotificationManager notificationManager;



    @RequiresApi(api = Build.VERSION_CODES.O)
    public NotifiacionHelper(Context base) {
        super(base);


    if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            createChannel();

        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void createChannel(){
        NotificationChannel channel=new NotificationChannel(CHANNEL1ID,NOMBRE1, NotificationManager.IMPORTANCE_DEFAULT);
        channel.enableLights(true);
        channel.enableVibration(true);
        channel.setLockscreenVisibility(Notification.VISIBILITY_PRIVATE);
getManager().createNotificationChannel(channel);
    }
public NotificationManager getManager(){
        if(notificationManager==null){
notificationManager=(NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);


        }
return notificationManager;
}

public NotificationCompat.Builder getChannelNotifiacion(String Title,String Text){
        return new NotificationCompat.Builder(getApplicationContext(),CHANNEL1ID).setContentTitle(Title).setContentText(Text)
                .setSmallIcon(R.mipmap.ic_launcher).setVibrate(new long[] { 1000, 1000, 1000, 1000, 1000 });


}



}
