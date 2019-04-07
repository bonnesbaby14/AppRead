package bonzai.com.appread;

import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Set;

public class Bluetooth extends AppCompatActivity {
    BluetoothAdapter bluetoothAdapter;
    Set<BluetoothDevice> paired;
    ListView listView;
    static public ProgressDialog progressDialog;
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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bluetooth);
        listView = findViewById(R.id.lista);


    }
    @Override
    public void onBackPressed() {
        Intent back=new Intent(Bluetooth.this,MainActivity.class);
        startActivity(back);
        finish();

    }
    @Override
    protected void onResume() {
        super.onResume();


        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        paired = bluetoothAdapter.getBondedDevices();
        ArrayList list = new ArrayList();
        for (BluetoothDevice bt : paired) {
            list.add(bt.getName() + "\n" + bt.getAddress());
        }
        ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, list);


        AdapterView.OnItemClickListener mDeviceSelected = new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView parent, View view, int position, long id) {
                String info = ((TextView) view).getText().toString();
                String address = info.substring(info.length() - 17);
                Intent trabajo=new Intent(getApplicationContext(),Trabajo.class);
                trabajo.putExtra("mac",address);

                progressDialog=new ProgressDialog(Bluetooth.this);
                progressDialog.setMessage("Conectando...");
                progressDialog.setCancelable(false);
                progressDialog.show();



                startActivity(trabajo);
            }
        };

        listView.setAdapter(arrayAdapter);
        listView.setOnItemClickListener(mDeviceSelected);
    }
}
