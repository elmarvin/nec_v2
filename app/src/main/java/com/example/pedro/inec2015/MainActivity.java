package com.example.pedro.inec2015;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pedro.inec2015.database.DatabaseHelper;
import com.example.pedro.inec2015.model.ZonaZ;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

public class MainActivity extends baseActivity {
    private List<ZonaZ> mFactorList;
    private DatabaseHelper mDBHelper;
    int id;
    String poblacion;
    String parroquia;
    String canton;
    String provincia;
    String factor;
    private ZonaZ zonaZ = new ZonaZ(id, poblacion, parroquia, canton, provincia, factor);
    public static final String PREFS = "datos";


    Spinner spinnerProvincia;
    Spinner spinnerCanton;
    Spinner spinnerParroquia;
    Spinner spinnerPoblacion;
    Button btnAceptarFZonaZ;
    TextView tvZ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mDBHelper = new DatabaseHelper(this);
        spinnerProvincia = (Spinner) findViewById(R.id.spinnerProvincia);
        spinnerCanton = (Spinner) findViewById(R.id.spinnerCanton);
        spinnerParroquia = (Spinner) findViewById(R.id.spinnerParroquia);
        spinnerPoblacion = (Spinner) findViewById(R.id.spinnerPoblacion);
        btnAceptarFZonaZ = (Button) findViewById(R.id.button);
        tvZ = (TextView) findViewById(R.id.tvZ);
        // Spinner click listener
        spinnerProvincia.setOnItemSelectedListener(new SpinnerListener());
        spinnerCanton.setOnItemSelectedListener(new SpinnerListener1());
        spinnerParroquia.setOnItemSelectedListener(new SpinnerListener2());
        DatabaseHelper myDbHelper = new DatabaseHelper(MainActivity.this);

        try {

            myDbHelper.createDataBase();

        } catch (IOException ioe) {

            throw new Error("Unable to create database");

        }

        try {

            myDbHelper.openDatabase();

        } catch (SQLException sqle) {

            throw sqle;

        }


        final Button button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click
                String valor = valorZ(spinnerProvincia.getSelectedItem().toString(), spinnerCanton.getSelectedItem().toString(), spinnerParroquia.getSelectedItem().toString(), spinnerPoblacion.getSelectedItem().toString());
//                zonaZ.setFactor(valor);
                startActivity(new Intent(getApplicationContext(), MainActivityPerfilSuelo.class));
                System.out.println(valor);
                //zonaZ.setFactor(valor);
                System.out.println("FACTOR FA " + fa("UNO", "D"));
                Intent intent = new Intent(MainActivity.this, MainActivityPerfilSuelo.class);
                //intent.putExtra("valor", valor);
                String provincia = spinnerProvincia.getSelectedItem().toString();
               // intent.putExtra("provincia", provincia);
                System.out.println(provincia);
                startActivity(intent);
                finish();
                double z = Double.parseDouble(valor.replace(",", "."));
                //base.setValor(z);

                SharedPreferences settings = getSharedPreferences(PREFS, 0);
                SharedPreferences.Editor editor;
                editor = settings.edit();
                editor.putString("valor", valor);
                editor.putString("provincia", provincia);
                editor.commit();




            }
        });

        loadSpinnerData();


        //Check exists database
        File database = getApplicationContext().getDatabasePath(DatabaseHelper.DBNAME);
        if (false == database.exists()) {
            mDBHelper.getReadableDatabase();
            //Copy db
            if (copyDatabase(this)) {
                Toast.makeText(this, "Copy database succes", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Copy data error", Toast.LENGTH_SHORT).show();
                return;
            }
        }
        //Get product list in db when db exists

        mFactorList = mDBHelper.getListFactor();
//        tvZ.setText(valorZ(spinnerProvincia.getSelectedItem().toString(),spinnerCanton.getSelectedItem().toString(),spinnerParroquia.getSelectedItem().toString(),spinnerPoblacion.getSelectedItem().toString()));

    }

    private boolean copyDatabase(Context context) {
        try {

            InputStream inputStream = context.getAssets().open(DatabaseHelper.DBNAME);
            String outFileName = DatabaseHelper.DBLOCATION + DatabaseHelper.DBNAME;
            OutputStream outputStream = new FileOutputStream(outFileName);
            byte[] buff = new byte[1024];
            int length = 0;
            while ((length = inputStream.read(buff)) > 0) {
                outputStream.write(buff, 0, length);
            }
            outputStream.flush();
            outputStream.close();
            Log.w("MainActivity", "DB copied");
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private void loadSpinnerData() {
        // database handler


        // Spinner Drop down elements
        List<String> provincias = mDBHelper.getAllProvincias();

        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, provincias);

        // Drop down layout style - list view with radio button
        dataAdapter
                .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spinnerProvincia.setAdapter(dataAdapter);
    }

    public class SpinnerListener implements AdapterView.OnItemSelectedListener {

        // Metodo onItemSelected en el que indicamos lo que queremos hacer
        // cuando sea seleccionado un elemento del Spinner
        public void onItemSelected(AdapterView<?> parent,
                                   View view, int pos, long id) {
            //mDBHelper.getCanton(parent.getItemAtPosition(pos). toString());
            cargarSpinnerCanton(parent.getItemAtPosition(pos).toString());

        }

        public void onNothingSelected(AdapterView<?> parent) {
            // Do nothing.
        }
    }

    public class SpinnerListener1 implements AdapterView.OnItemSelectedListener {

        // Metodo onItemSelected en el que indicamos lo que queremos hacer
        // cuando sea seleccionado un elemento del Spinner
        public void onItemSelected(AdapterView<?> parent,
                                   View view, int pos, long id) {
            cargarSpinnerParroquia(spinnerProvincia.getSelectedItem().toString(), parent.getItemAtPosition(pos).toString());

        }

        public void onNothingSelected(AdapterView<?> parent) {
            // Do nothing.
        }
    }

    public class SpinnerListener2 implements AdapterView.OnItemSelectedListener {

        // Metodo onItemSelected en el que indicamos lo que queremos hacer
        // cuando sea seleccionado un elemento del Spinner
        public void onItemSelected(AdapterView<?> parent,
                                   View view, int pos, long id) {
            cargarSpinnerPoblacion(spinnerProvincia.getSelectedItem().toString(), spinnerCanton.getSelectedItem().toString(), parent.getItemAtPosition(pos).toString());

        }

        public void onNothingSelected(AdapterView<?> parent) {
            // Do nothing.
        }
    }

    private void cargarSpinnerCanton(String provincia) {
        List<String> cantonces = mDBHelper.getAllCanton(provincia);
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, cantonces);
        dataAdapter
                .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCanton.setAdapter(dataAdapter);
    }

    private void cargarSpinnerParroquia(String provincia, String canton) {
        List<String> parroquias = mDBHelper.getAllParroquia(provincia, canton);
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, parroquias);
        dataAdapter
                .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerParroquia.setAdapter(dataAdapter);
    }

    private void cargarSpinnerPoblacion(String provincia, String canton, String poblacion) {
        List<String> poblaciones = mDBHelper.getAllPoblacion(provincia, canton, poblacion);
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, poblaciones);
        dataAdapter
                .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerPoblacion.setAdapter(dataAdapter);
    }

    private String valorZ(String provincia, String canton, String parroquia, String poblacion) {
        String z = mDBHelper.getValorZ(provincia, canton, parroquia, poblacion);
        return z;
    }

    public String fa(String z, String perfil) {
        String v = mDBHelper.getFActorfa(z, perfil);
        return v;
    }

}



