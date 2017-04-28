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
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.pedro.inec2015.database.DatabaseHelper;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

public class configuracion extends baseActivity {
    Button btnConfiguracion;
    Spinner spnTipo,spnElevacion,spnPlanta;
    public static final String PREFS = "datos";
    private DatabaseHelper mDBHelper;
    public ImageView imagen,imagen2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configuracion);
        btnConfiguracion = (Button) findViewById(R.id.btnConfiguracion);
        spnTipo = (Spinner) findViewById(R.id.spnConfTipo);
        spnElevacion = (Spinner) findViewById(R.id.spnElevacion);
        spnPlanta = (Spinner) findViewById(R.id.spnPlanta);
        imagen = (ImageView) findViewById(R.id.imagenConfinguracion);
        imagen2 = (ImageView) findViewById(R.id.imagenConfinguracionP);
        mDBHelper = new DatabaseHelper(this);
        DatabaseHelper myDbHelper = new DatabaseHelper(configuracion.this);
        spnTipo .setOnItemSelectedListener(new SpinnerListener());
        spnElevacion .setOnItemSelectedListener(new SpinnerListener1());
        spnPlanta .setOnItemSelectedListener(new SpinnerListener2());

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


        final Button btnConfiguracion = (Button) findViewById(R.id.btnConfiguracion);
        btnConfiguracion.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click
//             /*   zonaZ.setFactor(valor);
               /* double ei = 0;
                double pi = 0;
                //startActivity(new Intent(getApplicationContext(),MainActivityPerfilSuelo.class));
                String resultado = spnConfiguracion.getSelectedItem().toString();

                System.out.println("Categoria : " + resultado);
                if (resultado.equals("Configuraciones estructurales recomendadas")) {
                    ei=1;
                    pi=1;
                }else if(resultado.equals("Configuraciones estructurales no recomendadas")){
                    ei=0.9;
                    pi=0.9;
                }
                System.out.println("Valor Ei : "+ei);
                System.out.println("Valor Pi : "+pi);
                //zonaZ.setFactor(valor);

                String valorei = String.valueOf(ei);
                String valorpi = String.valueOf(pi);
                SharedPreferences settings = getSharedPreferences(PREFS, 0);
                SharedPreferences.Editor editor;
                editor = settings.edit();
                editor.putString("ei", valorei);
                editor.putString("pi", valorpi);
                editor.commit();

               Intent intent = new Intent(configuracion.this,MainActivityPerfilSuelo.class);

                startActivity(intent);
                finish();

                startActivity(new Intent(getApplicationContext(), datos.class))*/
                String s = valorR(spnTipo.getSelectedItem().toString());
                System.out.println("Valor : "+s);
                String valor = String.valueOf(s);

                SharedPreferences settings = getSharedPreferences(PREFS, 0);
                SharedPreferences.Editor editor;
                editor = settings.edit();
                editor.putString("ei", valor);
                editor.putString("pi", valor);
                editor.commit();
                startActivity(new Intent(getApplicationContext(), datos.class));
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
        List<String> cantonces = mDBHelper.getConfiguracion();
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
            cargarSpinnerElevacion(parent.getItemAtPosition(pos).toString());
            cargarSpinnerPlanta(parent.getItemAtPosition(pos).toString());
        }

        public void onNothingSelected(AdapterView<?> parent) {
            // Do nothing.
        }
    }
    private void cargarSpinnerElevacion(String tipo) {
        List<String> cantonces = mDBHelper.getConfiguracionElevacion(tipo);
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, cantonces);
        dataAdapter
                .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnElevacion.setAdapter(dataAdapter);
    }
    private void cargarSpinnerPlanta(String tipo) {
        List<String> cantonces = mDBHelper.getConfiguracionPlanta(tipo);
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, cantonces);
        dataAdapter
                .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnPlanta.setAdapter(dataAdapter);
    }
    public class SpinnerListener1 implements AdapterView.OnItemSelectedListener {

        // Metodo onItemSelected en el que indicamos lo que queremos hacer
        // cuando sea seleccionado un elemento del Spinner
        public void onItemSelected(AdapterView<?> parent,
                                   View view, int pos, long id) {
            if (parent.getItemAtPosition(pos).toString().equalsIgnoreCase(mDBHelper.getConfiguracionNombre(1))) {
                imagen.setImageResource(R.mipmap.plantar1);
            }else if(parent.getItemAtPosition(pos).toString().equalsIgnoreCase(mDBHelper.getConfiguracionNombre(2))){
                imagen.setImageResource(R.mipmap.elevacion2);
            }
            else if(parent.getItemAtPosition(pos).toString().equalsIgnoreCase(mDBHelper.getConfiguracionNombre(4))){
                imagen.setImageResource(R.mipmap.elevacion3);
            }
            else if(parent.getItemAtPosition(pos).toString().equalsIgnoreCase(mDBHelper.getConfiguracionNombre(5))){
                imagen.setImageResource(R.mipmap.elevacion4);
            }
            else if(parent.getItemAtPosition(pos).toString().equalsIgnoreCase(mDBHelper.getConfiguracionNombre(6))){
                imagen.setImageResource(R.mipmap.elevacion5);
            }else if(parent.getItemAtPosition(pos).toString().equalsIgnoreCase(mDBHelper.getConfiguracionNombre(7))){
                imagen.setImageResource(R.mipmap.elevacion6);
            }
            else if(parent.getItemAtPosition(pos).toString().equalsIgnoreCase(mDBHelper.getConfiguracionNombre(8))){
                imagen.setImageResource(R.mipmap.elevacion7);
            }
            else if(parent.getItemAtPosition(pos).toString().equalsIgnoreCase(mDBHelper.getConfiguracionNombre(9))){
                imagen.setImageResource(R.mipmap.elevacion8);
            }

        }


        public void onNothingSelected(AdapterView<?> parent) {
            // Do nothing.
        }
    }
    private String valorR(String tipo ){
        String z = String.valueOf(mDBHelper.getConfiguracionValor(tipo));
        return z;
    }
    public class SpinnerListener2 implements AdapterView.OnItemSelectedListener {

        // Metodo onItemSelected en el que indicamos lo que queremos hacer
        // cuando sea seleccionado un elemento del Spinner
        public void onItemSelected(AdapterView<?> parent,
                                   View view, int pos, long id) {
            if (parent.getItemAtPosition(pos).toString().equalsIgnoreCase(mDBHelper.getConfiguracionNombre(3))) {
                imagen2.setImageResource(R.mipmap.planta1);
            } else if(parent.getItemAtPosition(pos).toString().equalsIgnoreCase(mDBHelper.getConfiguracionNombre(10))){
                imagen2.setImageResource(R.mipmap.planta2);
            }else if(parent.getItemAtPosition(pos).toString().equalsIgnoreCase(mDBHelper.getConfiguracionNombre(11))){
                imagen2.setImageResource(R.mipmap.planta3);
            }else if(parent.getItemAtPosition(pos).toString().equalsIgnoreCase(mDBHelper.getConfiguracionNombre(12))){
                imagen2.setImageResource(R.mipmap.planta4);
            }else if(parent.getItemAtPosition(pos).toString().equalsIgnoreCase(mDBHelper.getConfiguracionNombre(13))){
                imagen2.setImageResource(R.mipmap.planta5);
            }else if(parent.getItemAtPosition(pos).toString().equalsIgnoreCase(mDBHelper.getConfiguracionNombre(14))){
                imagen2.setImageResource(R.mipmap.planta6);
            }

        }


        public void onNothingSelected(AdapterView<?> parent) {
            // Do nothing.
        }
    }

}
