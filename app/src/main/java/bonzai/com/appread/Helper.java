package bonzai.com.appread;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class Helper extends SQLiteOpenHelper {

    public Helper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE ALARMAS(ID INTEGER PRIMARY KEY,HORA INTEGER,MINUTO INTEGER);");
        db.execSQL("INSERT INTO ALARMAS VALUES(NULL,12,0)");
        db.execSQL("INSERT INTO ALARMAS VALUES(NULL,12,0)");
        db.execSQL("CREATE TABLE REGISTROS(ID INTEGER PRIMARY KEY,FECHA TEXT, S02 TEXT,FREQ TEXT, PRESA1 TEXT, PRESA2 TEXT,TEMPE TEXT);");



    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
