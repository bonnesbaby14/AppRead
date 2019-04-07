package bonzai.com.appread;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Calendar;
import java.util.UUID;

public class Trabajo extends AppCompatActivity {
    private ConnectedThread MyConexionBT;
    Helper helper;
    Handler bluetoothIn;
    final int handlerState = 0;
    private BluetoothAdapter btAdapter = null;
    private BluetoothSocket btSocket = null;
    private StringBuilder DataStringIN = new StringBuilder();
    private static final UUID BTMODULEUUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
    private static String address = null;
    ProgressBar progressBar;
    TextView FREQ,PRES2,PRES,S02,TEMP;
    String ss02,spres1,spres2,sfreq,stemp;
    Button REGISTRAR;
ProgressDialog progressDialog2;
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

    @Override
    protected void onStart() {


    super.onStart();
    }

    @SuppressLint("HandlerLeak")
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trabajo);

Bluetooth.progressDialog.dismiss();
        progressBar=(ProgressBar) findViewById(R.id.progres);
        FREQ=findViewById(R.id.freq);
        PRES2=findViewById(R.id.press2);
        PRES=findViewById(R.id.press);
        S02=findViewById(R.id.s02);
        TEMP=findViewById(R.id.temp);
        REGISTRAR=findViewById(R.id.btnRegistrar);
        REGISTRAR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 helper=new Helper(getApplicationContext(),"DB",null,1);
                SQLiteDatabase DB=helper.getWritableDatabase();
                Calendar calendar=Calendar.getInstance();
                String fecha=calendar.getTime().toString();
                try {

                    DB.execSQL("INSERT INTO REGISTROS VALUES(NULL,'" + fecha + "','" + ss02 + "','" + sfreq + "','" + spres1 + "','" + spres2 + "','" + stemp + "');");
                    //DB.execSQL("INSERT INTO REGISTROS VALUES(NULL,HOY,123,1234,12345,123,1222)");
                    Toast.makeText(getApplicationContext(),"SE REGISTRO CORRECTAMENTE",Toast.LENGTH_LONG).show();
                }catch (Exception e){
                    Toast.makeText(getApplicationContext(),"NO SE PUDO REGISTAR",Toast.LENGTH_LONG).show();

                }




            }
        });

        bluetoothIn = new Handler() {
            public void handleMessage(android.os.Message msg) {
                if (msg.what == handlerState) {
                    String readMessage = (String) msg.obj;
                    DataStringIN.append(readMessage);

                    int endOfLineIndex = DataStringIN.indexOf("#");

                    if (endOfLineIndex > 0) {
                        String dataInPrint = DataStringIN.substring(0, endOfLineIndex);
int x=0;
                        for( ; x<dataInPrint.length();x++){
                            if(dataInPrint.charAt(x)=='$'){
                                ss02=dataInPrint.substring(0,x);
                                S02.setText(ss02);
break;
                            }
                        }
                        int y=x+1;
                        for( ; y<dataInPrint.length();y++){
                            if(dataInPrint.charAt(y)=='$'){
                                sfreq=dataInPrint.substring(x+1,y);
                                FREQ.setText(sfreq);
                                break;

                            }
                        }
                        int z=y+1;
                        for( ; z<dataInPrint.length();z++){
                            if(dataInPrint.charAt(z)=='$'){
                                spres1=dataInPrint.substring(y+1,z);
                                PRES.setText(spres1);
                                break;

                            }
                        }
                        int w=z+1;
                        for( ; w<dataInPrint.length();w++){
                            if(dataInPrint.charAt(w)=='$'){
                               spres2=dataInPrint.substring(z+1,w);
                                PRES2.setText(spres2);
                                break;

                            }
                        }
                        int q=w+1;
                        for( ; q<dataInPrint.length();q++){
                            if(dataInPrint.charAt(q)=='$'){
                                stemp=dataInPrint.substring(w+1,q);
                                TEMP.setText(stemp);
                                break;

                            }
                        }

                        DataStringIN.delete(0, DataStringIN.length());
                    }
                }
            }
        };

    btAdapter=BluetoothAdapter.getDefaultAdapter();






    }
    @Override
    public void onResume()
    {
        boolean falla=false;
        Intent back;
        super.onResume();
        //Consigue la direccion MAC desde DeviceListActivity via intent
        Intent intent = getIntent();
        //Consigue la direccion MAC desde DeviceListActivity via EXTRA
        address = intent.getStringExtra("mac");//<-<- PARTE A MODIFICAR >->->
        //Setea la direccion MAC
        BluetoothDevice device = btAdapter.getRemoteDevice(address);

        try
        {
            btSocket = createBluetoothSocket(device);

        } catch (IOException e) {
            falla=true;
            Toast.makeText(getBaseContext(), "La creacción del Socket fallo", Toast.LENGTH_LONG).show();
            back=new Intent(getApplicationContext(),Bluetooth.class);
            startActivity(back);

        }
        // Establece la conexión con el socket Bluetooth.
        try
        {
            btSocket.connect();
            falla=false;
        } catch (IOException e) {
            Toast.makeText(getBaseContext(), "La conexion fallo", Toast.LENGTH_LONG).show();
            back=new Intent(getApplicationContext(),Bluetooth.class);
            startActivity(back);


            try {
                btSocket.close();
            } catch (IOException e2) {
                Toast.makeText(getBaseContext(), "El cierre del socket fallo", Toast.LENGTH_LONG).show();
            }
        }
        if(!falla) {
            MyConexionBT = new ConnectedThread(btSocket);
            MyConexionBT.start();
        }
        }



    @Override
    public void onPause()
    {
        super.onPause();
        try
        { // Cuando se sale de la aplicación esta parte permite
            // que no se deje abierto el socket
            btSocket.close();
        } catch (IOException e2) {}
    }
    private BluetoothSocket createBluetoothSocket(BluetoothDevice device) throws IOException
    {
        //crea un conexion de salida segura para el dispositivo
        //usando el servicio UUID
        return device.createRfcommSocketToServiceRecord(BTMODULEUUID);
    }

    //Crea la clase que permite crear el evento de conexion
    private class ConnectedThread extends Thread
    {
        private final InputStream mmInStream;
        private final OutputStream mmOutStream;

        public ConnectedThread(BluetoothSocket socket)
        {
            InputStream tmpIn = null;
            OutputStream tmpOut = null;
            try
            {
                tmpIn = socket.getInputStream();
                tmpOut = socket.getOutputStream();
            } catch (IOException e) { }
            mmInStream = tmpIn;
            mmOutStream = tmpOut;
        }

        public void run()
        {
            byte[] buffer = new byte[256];
            int bytes;

            // Se mantiene en modo escucha para determinar el ingreso de datos
            while (true) {
                try {
                    bytes = mmInStream.read(buffer);
                    String readMessage = new String(buffer, 0, bytes);
                    // Envia los datos obtenidos hacia el evento via handler
                    bluetoothIn.obtainMessage(handlerState, bytes, -1, readMessage).sendToTarget();
                } catch (IOException e) {
                    break;
                }
            }
        }
        //Envio de trama
        public void write(String input)
        {
            try {
                mmOutStream.write(input.getBytes());
            }
            catch (IOException e)
            {
                //si no es posible enviar datos se cierra la conexión
                Toast.makeText(getBaseContext(), "La Conexión fallo", Toast.LENGTH_LONG).show();
                finish();
            }
        }
    }
}

