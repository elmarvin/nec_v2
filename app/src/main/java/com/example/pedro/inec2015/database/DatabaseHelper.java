package com.example.pedro.inec2015.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import com.example.pedro.inec2015.model.FactorFa;
import com.example.pedro.inec2015.model.ZonaZ;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Pedro on 27/07/2016.
 */
public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String DBNAME = "vlee.sqlite";
    public static final String DBLOCATION = "/data/data/com.example.pedro.inec2015/databases/";
    private Context mContext;
    private SQLiteDatabase mDatabase;
    private static final int DB_VERSION = 5;


    public DatabaseHelper(Context context) {
        super(context, DBNAME, null, DB_VERSION);
        this.mContext = context;

    }
    public void createDataBase() throws IOException{

        boolean dbExist = checkDataBase();

        if(dbExist){
            //do nothing - database already exist
        }else{

            //By calling this method and empty database will be created into the default system path
            //of your application so we are gonna be able to overwrite that database with our database.
            this.getReadableDatabase();

            try {
                        copyDataBase();

            } catch (IOException e) {

                throw new Error("Error copying database");

            }
        }

    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }
    private boolean checkDataBase(){

        SQLiteDatabase checkDB = null;

        try{
            String myPath = DBLOCATION + DBNAME;
            checkDB = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);

        }catch(SQLiteException e){

            //database does't exist yet.

        }

        if(checkDB != null){

            checkDB.close();

        }

        return checkDB != null ? true : false;
    }
    private void copyDataBase() throws IOException{

        //Open your local db as the input stream
        InputStream myInput = mContext.getAssets().open(DBNAME);

        // Path to the just created empty db
        String outFileName = DBLOCATION + DBNAME;

        //Open the empty db as the output stream
        OutputStream myOutput = new FileOutputStream(outFileName);

        //transfer bytes from the inputfile to the outputfile
        byte[] buffer = new byte[1024];
        int length;
        while ((length = myInput.read(buffer))>0){
            myOutput.write(buffer, 0, length);
        }

        //Close the streams
        myOutput.flush();
        myOutput.close();
        myInput.close();

    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void openDatabase() {
        String dbPath = mContext.getDatabasePath(DBNAME).getPath();
        if(mDatabase != null && mDatabase.isOpen()) {
            return;
        }
        mDatabase = SQLiteDatabase.openDatabase(dbPath, null, SQLiteDatabase.OPEN_READWRITE);
    }

    public void closeDatabase() {
        if(mDatabase!=null) {
            mDatabase.close();
        }
    }

    public List<ZonaZ> getListFactor(){
        ZonaZ zonaZ = null;
        List<ZonaZ> factorList = new ArrayList<>();
        openDatabase();
        Cursor cursor = mDatabase.rawQuery("SELECT * FROM ZONAZ", null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            zonaZ = new ZonaZ(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getString(5) );
            factorList.add(zonaZ);
            cursor.moveToNext();
        }
        cursor.close();
        closeDatabase();
        return factorList;
    }
    public List<String> getAllProvincias() {
        List<String> zonaz = new ArrayList<String>();

        // Select All Query
        String selectQuery = "SELECT PROVINCIA FROM ZONAZ GROUP BY PROVINCIA;";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor;
        cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                zonaz.add(cursor.getString(0));
            } while (cursor.moveToNext());
        }

        // closing connection
        cursor.close();
        db.close();

        // returning lables
        return zonaz;
    }

    public List<String> getAllCanton(String provincia) {
        List<String> zonaz = new ArrayList<String>();

        // Select All Query
        String selectQuery = "SELECT CANTON FROM ZONAZ WHERE PROVINCIA = '"+provincia+"' GROUP BY CANTON;";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor;
        cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                zonaz.add(cursor.getString(0));
            } while (cursor.moveToNext());
        }

        // closing connection
        cursor.close();
        db.close();

        // returning lables
        return zonaz;
    }
    public List<String> getAllParroquia(String provincia, String canton ) {
        List<String> zonaz = new ArrayList<String>();

        // Select All Query
        String selectQuery =
                "SELECT PARROQUIA FROM ZONAZ WHERE PROVINCIA = '"+provincia+"' AND CANTON = '"+canton+"' GROUP BY PARROQUIA;";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor;
        cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                zonaz.add(cursor.getString(0));
            } while (cursor.moveToNext());
        }

        // closing connection
        cursor.close();
        db.close();

        // returning lables
        return zonaz;
    }
    public List<String> getAllPoblacion(String provincia, String canton, String parroquia) {
        List<String> zonaz = new ArrayList<String>();

        // Select All Query
        String selectQuery =
                "SELECT POBLACION FROM ZONAZ WHERE PROVINCIA = '"+provincia+"' AND CANTON = '"+canton+"' AND PARROQUIA = '"+parroquia+"' GROUP BY POBLACION;";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor;
        cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                zonaz.add(cursor.getString(0));
            } while (cursor.moveToNext());
        }

        // closing connection
        cursor.close();
        db.close();

        // returning lables
        return zonaz;
    }
    String z;
    /*public String getZ(String provincia, String canton, String parroquia,String poblacion) {
        List<String> zonaz = new ArrayList<String>();

        // Select All Query
        String selectQuery =
                "SELECT Z FROM ZONAZ WHERE PROVINCIA = '"+provincia+"' AND CANTON = '"+canton+"' AND PARROQUIA = '"+parroquia+"' AND POBLACION = '"+poblacion+"'";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor;
        cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                zonaz.add(cursor.getString(0));
            } while (cursor.moveToNext());
        }


        // closing connection
        cursor.close();
        db.close();

        // returning lables
        return z;
    }*/
    public String getValorZ(String provincia, String canton, String parroquia,String poblacion) {
        String selectQuery =
                "SELECT Z FROM ZONAZ WHERE PROVINCIA = '"+provincia+"' AND CANTON = '"+canton+"' AND PARROQUIA = '"+parroquia+"' AND POBLACION = '"+poblacion+"'";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor result = db.rawQuery(selectQuery,null);
        result.moveToFirst();
        String valor = result.getString(result.getColumnIndex("z"));
        return valor;
    }
    public String getFActorfa(String z, String perfilSuelo) {
        String selectQuery =
                "SELECT "+z+" FROM FACTORFA WHERE FA = '"+perfilSuelo+"'";
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor c = db.rawQuery(selectQuery, null);
        c.moveToFirst();
        String valor =c.getString(0);

        return valor;

    }
    public String getFActorfd(String z, String perfilSuelo) {
        String selectQuery =
                "SELECT "+z+" FROM FACTORFD WHERE FA = '"+perfilSuelo+"'";
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor c = db.rawQuery(selectQuery, null);
        c.moveToFirst();
        String valor =c.getString(0);

        return valor;

    }
    public String getFActorfs(String z, String perfilSuelo) {
        String selectQuery =
                "SELECT "+z+" FROM FACTORFS WHERE FA = '"+perfilSuelo+"'";
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor c = db.rawQuery(selectQuery, null);
        c.moveToFirst();
        String valor =c.getString(0);

        return valor;

    }
    public double getAmpliacionEspectral(String provincia) {
        String selectQuery =
                "SELECT VALOR FROM AMPLIACIONESPECTRAL WHERE PROVINCIA ='"+provincia+"'";
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor c = db.rawQuery(selectQuery, null);
        c.moveToFirst();
        String valor =c.getString(0);
        double v = Double.parseDouble(valor);

        return v;

    }


    public List<String> getCoeficienteR() {
        List<String> zonaz = new ArrayList<String>();

        // Select All Query
        String selectQuery = "SELECT TIPO FROM COEFICIENTER GROUP BY TIPO;";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor;
        cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                zonaz.add(cursor.getString(0));
            } while (cursor.moveToNext());
        }

        // closing connection
        cursor.close();
        db.close();

        // returning lables
        return zonaz;
    }
    public List<String> getCoeficienteRGrupo(String tipo) {
        List<String> zonaz = new ArrayList<String>();

        // Select All Query
        String selectQuery = "SELECT GRUPO FROM COEFICIENTER WHERE TIPO  = '"+tipo+"' GROUP BY GRUPO;";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor;
        cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                zonaz.add(cursor.getString(0));
            } while (cursor.moveToNext());
        }

        // closing connection
        cursor.close();
        db.close();

        // returning lables
        return zonaz;
    }
    public List<String> getCoeficienteRDescripcion(String tipo,String grupo) {
        List<String> zonaz = new ArrayList<String>();

        // Select All Query
        String selectQuery = "SELECT DESCRIPCCION FROM COEFICIENTER WHERE TIPO  = '"+tipo+"' AND GRUPO = '"+grupo+"';";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor;
        cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                zonaz.add(cursor.getString(0));
            } while (cursor.moveToNext());
        }

        // closing connection
        cursor.close();
        db.close();

        // returning lables
        return zonaz;
    }

    public double getCoeficienteRValor(String tipo,String grupo,String descripcion) {
        String selectQuery =
                "SELECT VALOR FROM COEFICIENTER WHERE TIPO  = '"+tipo+"' AND GRUPO = '"+grupo+"' AND DESCRIPCCION = '"+descripcion+"';";
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor c = db.rawQuery(selectQuery, null);
        c.moveToFirst();
        String valor =c.getString(0);
        double v = Double.parseDouble(valor);

        return v;

    }

    public List<String> getConfiguracion() {
        List<String> zonaz = new ArrayList<String>();

        // Select All Query
        String selectQuery = "SELECT TIPO FROM CONFIGURACION GROUP BY TIPO;";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor;
        cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                zonaz.add(cursor.getString(0));
            } while (cursor.moveToNext());
        }

        // closing connection
        cursor.close();
        db.close();

        // returning lables
        return zonaz;
    }

    public List<String> getConfiguracionElevacion(String tipo) {
        List<String> zonaz = new ArrayList<String>();

        // Select All Query
        String selectQuery = "SELECT NOMBRE FROM CONFIGURACION WHERE TIPO  = '"+tipo+"' AND GRUPO = 'Elevacion';";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor;
        cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                zonaz.add(cursor.getString(0));
            } while (cursor.moveToNext());
        }

        // closing connection
        cursor.close();
        db.close();

        // returning lables
        return zonaz;
    }
    public List<String> getConfiguracionPlanta(String tipo) {
        List<String> zonaz = new ArrayList<String>();

        // Select All Query
        String selectQuery = "SELECT NOMBRE FROM CONFIGURACION WHERE TIPO  = '"+tipo+"' AND GRUPO = 'Planta';";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor;
        cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                zonaz.add(cursor.getString(0));
            } while (cursor.moveToNext());
        }

        // closing connection
        cursor.close();
        db.close();

        // returning lables
        return zonaz;
    }

    public String getConfiguracionDescripcionElevacion(String nombre) {
        String selectQuery =
                "SELECT DESCRIPCION FROM CONFIGURACION WHERE TIPO  = 'No Recomendada' AND GRUPO = 'Elevacion' AND NOMBRE = '"+nombre+"';";
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor c = db.rawQuery(selectQuery, null);
        c.moveToFirst();
        String valor =c.getString(0);


        return valor;

    }
    public String getConfiguracionDescripcionPlanta(String nombre) {
        String selectQuery =
                "SELECT DESCRIPCION FROM CONFIGURACION WHERE TIPO  = 'No Recomendada' AND GRUPO = 'Planta' AND NOMBRE = '"+nombre+"';";
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor c = db.rawQuery(selectQuery, null);
        c.moveToFirst();
        String valor =c.getString(0);


        return valor;

    }
    public double getConfiguracionValor(String tipo) {
        String selectQuery =
                "SELECT VALOR FROM CONFIGURACION WHERE TIPO = '"+tipo+"'";
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor c = db.rawQuery(selectQuery, null);
        c.moveToFirst();
        String valor =c.getString(0);
        double v = Double.parseDouble(valor);

        return v;

    }
    public String getConfiguracionNombre(int id) {
        String selectQuery =
                "SELECT NOMBRE FROM CONFIGURACION WHERE ID = '"+id+"'";
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor c = db.rawQuery(selectQuery, null);
        c.moveToFirst();
        String valor =c.getString(0);


        return valor;

    }
}
