package bonzai.com.appread;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.icu.text.CurrencyPluralInfo;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

public class Datos extends AppCompatActivity {
    RecyclerView recyclerView;
    ArrayList arrayList;
    Helper helper;
    Dato dato;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_datos);
        recyclerView=findViewById(R.id.rvDatos);
arrayList=new ArrayList<>();
helper=new Helper(getApplicationContext(),"DB",null,1);
recyclerView.setLayoutManager(new LinearLayoutManager(this));
conexion();
MyAdapter myAdapter=new MyAdapter(arrayList);
recyclerView.setAdapter(myAdapter);



    }
void conexion(){
    SQLiteDatabase DB=helper.getReadableDatabase();
    Cursor cursor=DB.rawQuery("SELECT * FROM REGISTROS;", null);
    int count=cursor.getCount();

while(cursor.moveToNext()){
    dato=new Dato(cursor.getString(1),cursor.getString(2),cursor.getString(3),cursor.getString(4),cursor.getString(5),cursor.getString(6));
arrayList.add(dato);
}



}
}
