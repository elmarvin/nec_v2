package com.example.pedro.inec2015;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.SQLException;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.pedro.inec2015.database.DatabaseHelper;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

public class coeficiente_r extends baseActivity  {
    Spinner spnTipo, categoria, subcategoria;
    Button btnAcptarR;
    public static final String PREFS = "datos";
    private DatabaseHelper mDBHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coeficiente_r);
        mDBHelper = new DatabaseHelper(this);

        spnTipo = (Spinner) findViewById(R.id.spnTipo);
        categoria = (Spinner) findViewById(R.id.spnCategoria);
        subcategoria = (Spinner) findViewById(R.id.spnSubcategoria);
        SharedPreferences z = getSharedPreferences(PREFS,0);

        String perfil = z.getString("coeficienteI","no hay valor");
        spnTipo .setOnItemSelectedListener(new SpinnerListener());
        categoria .setOnItemSelectedListener(new SpinnerListener1());

        DatabaseHelper myDbHelper = new DatabaseHelper(coeficiente_r.this);

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

      cargarSpinnerTipo();

        final Button button = (Button) findViewById(R.id.btnAceptarR);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                System.out.println("Valor : "+valorR(spnTipo.getSelectedItem().toString(),categoria.getSelectedItem().toString(),subcategoria.getSelectedItem().toString()));
                String s = valorR(spnTipo.getSelectedItem().toString(),categoria.getSelectedItem().toString(),subcategoria.getSelectedItem().toString());

                String coeficienter = String.valueOf(s);
                SharedPreferences settings = getSharedPreferences(PREFS, 0);
                SharedPreferences.Editor editor;
                editor = settings.edit();
                editor.putString("coeficienteR", coeficienter);
                editor.commit();


                Intent intent = new Intent(coeficiente_r.this,coeficiente_ct.class);

                startActivity(intent);
                finish();

                startActivity(new Intent(getApplicationContext(), coeficiente_ct.class));
            }
        });
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

    private void cargarSpinnerTipo() {
        List<String> cantonces = mDBHelper.getCoeficienteR();
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, cantonces);
        dataAdapter
                .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnTipo.setAdapter(dataAdapter);
    }
    public class SpinnerListener implements AdapterView.OnItemSelectedListener {

        // Metodo onItemSelected en el que indicamos lo que queremos hacer
        // cuando sea seleccionado un elemento del Spinner
        public void onItemSelected(AdapterView<?> parent,
                                   View view, int pos, long id) {
            //mDBHelper.getCanton(parent.getItemAtPosition(pos). toString());
            cargarSpinnerGrupo(parent.getItemAtPosition(pos).toString());

        }

        public void onNothingSelected(AdapterView<?> parent) {
            // Do nothing.
        }
    }
    private void cargarSpinnerGrupo(String tipo) {
        List<String> cantonces = mDBHelper.getCoeficienteRGrupo(tipo);
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, cantonces);
        dataAdapter
                .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categoria.setAdapter(dataAdapter);
    }

    private void cargarSpinnerDescripcion(String tipo,String grupo) {
        List<String> cantonces = mDBHelper.getCoeficienteRDescripcion(tipo,grupo);
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, cantonces);
        dataAdapter
                .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        subcategoria.setAdapter(dataAdapter);
    }

    public class SpinnerListener1 implements AdapterView.OnItemSelectedListener {

        // Metodo onItemSelected en el que indicamos lo que queremos hacer
        // cuando sea seleccionado un elemento del Spinner
        public void onItemSelected(AdapterView<?> parent,
                                   View view, int pos, long id) {
            cargarSpinnerDescripcion(spnTipo.getSelectedItem().toString(), parent.getItemAtPosition(pos).toString());

        }

        public void onNothingSelected(AdapterView<?> parent) {
            // Do nothing.
        }
    }

    private String valorR(String tipo, String categoria, String descripcion) {
        String z = String.valueOf(mDBHelper.getCoeficienteRValor(tipo,categoria,descripcion));
        return z;
    }
}