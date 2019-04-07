package bonzai.com.appread;

import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.app.TimePickerDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.zip.DataFormatException;


public class MainActivity extends AppCompatActivity implements TimePickerDialog.OnTimeSetListener {
TextView Alarma1,Alarma2;
boolean Alarma;
Button btnconectar;

Helper helper;
BluetoothAdapter bluetoothAdapter;
static public int hora1, minuto1,hora2,minuto2;

    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu,menu);
        return true;
    }

    public  boolean onOptionsItemSelected(MenuItem menuItem){
        if(menuItem.getItemId()==R.id.btnRegistros){
            Intent intent=new Intent(getApplicationContext(),Datos.class);
            startActivity(intent);

        }
        return super.onOptionsItemSelected(menuItem);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bluetoothAdapter= BluetoothAdapter.getDefaultAdapter();


        if(bluetoothAdapter==null){

            Log.d("d","el bluett no esta disponible");
        }else {
            Intent enableBluetooth =new Intent(bluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBluetooth,1);
        }


        Alarma1=findViewById(R.id.tvAlarma1);
        Alarma2=findViewById(R.id.tvAlarma2);
        btnconectar=findViewById(R.id.btnConectar);


        Alarma1.setOnClickListener(new View.OnClickListener() {


            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                Alarma=true;
                DialogFragment dialogFragment=new TimmerPicket();
                dialogFragment.show(getSupportFragmentManager(),"timmer picket");




            }
        });

        Alarma2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
Alarma=false;
                DialogFragment dialogFragment=new TimmerPicket();
                dialogFragment.show(getSupportFragmentManager(),"timmer picket");
            }
        });
btnconectar.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {

   if(bluetoothAdapter.isEnabled()) {
       Intent intent = new Intent(getApplicationContext(), Bluetooth.class);
       startActivity(intent);

   }else {
       Toast.makeText(getApplicationContext(),"Activa el Bluetooth",Toast.LENGTH_LONG).show();

   }
    }
});
        helper=new Helper(getApplicationContext(),"DB",null,1);
        SQLiteDatabase DB=helper.getReadableDatabase();
        Cursor cursor=DB.rawQuery("SELECT * FROM ALARMAS;", null);
        cursor.moveToFirst();


Alarma1.setText("Alarma 1:     "+String.valueOf(cursor.getInt(1))+" : "+String.valueOf(cursor.getInt(2)));
        cursor.moveToNext();
        Alarma2.setText("Alarma 2:     "+String.valueOf(cursor.getInt(1))+" : "+String.valueOf(cursor.getInt(2)));


    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

        Calendar c=Calendar.getInstance();
        c.set(Calendar.HOUR_OF_DAY,hourOfDay);
        c.set(Calendar.MINUTE,minute);
        c.set(Calendar.SECOND,0);





        if(Alarma){
    hora1=hourOfDay;
    minuto1=minute;
    Alarma1.setText("Alarma 1:       "+hourOfDay+" : "+minute);
            startAlamr1(c,hora1,minuto1);
}else{
    hora2=hourOfDay;
    minuto2=minute;
    Alarma2.setText("Alarma 2:       "+hourOfDay+" : "+minute);
            startAlamr2(c,hora2,minuto2);



}
    }


    private void startAlamr1(Calendar c,int hora,int minuto){
       AlarmManager  alarmManager=(AlarmManager) getSystemService(Context.ALARM_SERVICE);
 Intent intent =new Intent(this, AlertRecive.class);
PendingIntent pendingIntent =PendingIntent.getBroadcast(this,1,intent,0);
        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP,c.getTimeInMillis(),AlarmManager.INTERVAL_DAY,pendingIntent);
        helper=new Helper(getApplicationContext(),"DB",null,1);
        SQLiteDatabase DB=helper.getWritableDatabase();


        DB.execSQL("UPDATE ALARMAS SET HORA="+ hora+",  MINUTO="+ minuto+" where ID=1 ");



    }
    private void startAlamr2(Calendar c, int hora,int minuto){
         AlarmManager alarmManager2=(AlarmManager) getSystemService(Context.ALARM_SERVICE);
          Intent intent =new Intent(this, AlertRecive.class);
         PendingIntent pendingIntent=PendingIntent.getBroadcast(this,2,intent,0);

        alarmManager2.setInexactRepeating(AlarmManager.RTC_WAKEUP,c.getTimeInMillis(),AlarmManager.INTERVAL_DAY,pendingIntent);
        helper=new Helper(getApplicationContext(),"DB",null,1);
        SQLiteDatabase DB=helper.getWritableDatabase();
        DB.execSQL("UPDATE ALARMAS SET HORA="+ hora+",  MINUTO="+ minuto+" where ID=2 ");


    }


}
