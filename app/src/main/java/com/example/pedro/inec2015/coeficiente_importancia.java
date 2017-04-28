package com.example.pedro.inec2015;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class coeficiente_importancia extends baseActivity {
    Spinner spnCategoriaCoeficiente;
    TextView tvDestinoImportancia;
    Button btnAceptarCoeficiente;
    public static final String PREFS = "datos";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coeficiente_importancia);
        tvDestinoImportancia = (TextView) findViewById(R.id.tvDestinoImportancia);
        btnAceptarCoeficiente = (Button) findViewById(R.id.btnAceptarCoeficiente);
        addItemsOnSpinner();
        SharedPreferences z = getSharedPreferences(PREFS,0);
        String string = z.getString("valor","no hay valor");
        String perfil = z.getString("perfilSuelo","no hay valor");

        System.out.println("valor : "+perfil);
        btnAceptarCoeficiente.setEnabled(false);
/*
        Intent intent = new Intent(coeficiente_importancia.this, coeficiente_r.class);
        Bundle extras = getIntent().getExtras();
//        String z = extras.getString("z");
       // String provincia = extras.getString("provincia1");
        String perfilSuelo = getIntent().getStringExtra("perfilSuelo");
        //intent.putExtra("provincia1", provincia);
        //intent.putExtra("z", z);
        intent.putExtra("perfilSuelo", perfilSuelo);
        startActivity(intent);
        //Coeficiente(String.valueOf(spnCategoriaCoeficiente.getSelectedItem()));*/

        spnCategoriaCoeficiente.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {

                String item = "Ninguno seleccionado";
                // On selecting a spinner item
                if (parent.getItemAtPosition(position).toString().equalsIgnoreCase("Edificaciones esenciales")) {
                    item = parent.getItemAtPosition(position).toString();
                    tvDestinoImportancia.setText("Hospitales, clinicas, centros de salud o emergencia sanitaria. " +
                            "Instalaciones militares, de policia, bomberos, defensa civil. Garajes o estacionamientos " +
                            "para vehiculos y aviones que atienden emergencias. Torres de control aereo. " +
                            "Estructuras de centros de telecomunicaciones u otros centros de atención de emergencias. " +
                            "Estructuras que albergan equipos de generación y distribución electrica. Tanques u otras estructuras " +
                            "utilizadas para depósito de agua u otras substancias anti-incendio. Estructuras que albergan depósitos " +
                            "tóxicos, explosivos, químicos u otras substancias peligrosas.");
                    btnAceptarCoeficiente.setEnabled(true);
                }
                if (parent.getItemAtPosition(position).toString().equalsIgnoreCase("Estrucuras de ocupación especial")) {
                    item = parent.getItemAtPosition(position).toString();
                    tvDestinoImportancia.setText("Museos, iglesias, escuelas y centros de educación o deportivos que albergan más de " +
                            "trecientas personas. Todas las estructuras que albergan más de cinco mil personas. Edificios públicos " +
                            "que requieren operar continuamente.");
                    btnAceptarCoeficiente.setEnabled(true);
                }
                if (parent.getItemAtPosition(position).toString().equalsIgnoreCase("Otras estructuras")) {
                    item = parent.getItemAtPosition(position).toString();
                    tvDestinoImportancia.setText("Todas las estructuras de edificación y otras que no clasifican dentro de las categorías anteriores.");
                    btnAceptarCoeficiente.setEnabled(true);
                } else {

                }

                // showing a toast on selecting an item
                Toast.makeText(parent.getContext(), item, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub

            }

        });
        final Button button = (Button) findViewById(R.id.button);
        btnAceptarCoeficiente.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click
//                zonaZ.setFactor(valor);
                double coeficienteI = 0;
                //startActivity(new Intent(getApplicationContext(),MainActivityPerfilSuelo.class));
                String resultado = spnCategoriaCoeficiente.getSelectedItem().toString();

                System.out.println("Categoria : " +resultado);
                System.out.println("Coeficiente I : "+valorCoeficiente(resultado));
                //zonaZ.setFactor(valor);

                String coeficientei = String.valueOf(valorCoeficiente(resultado));
                SharedPreferences settings = getSharedPreferences(PREFS, 0);
                SharedPreferences.Editor editor;
                editor = settings.edit();
                editor.putString("coeficienteI", coeficientei);
                editor.commit();

                Intent intent = new Intent(coeficiente_importancia.this, coeficiente_r.class);

                startActivity(intent);
                finish();
                startActivity(new Intent(getApplicationContext(), coeficiente_r.class));
            }
        });
    }

    public void addItemsOnSpinner() {
        List<String> list = new ArrayList<String>();
        list.add("");
        list.add("Edificaciones esenciales");
        list.add("Estrucuras de ocupación especial");
        list.add("Otras estructuras");
        spnCategoriaCoeficiente = (Spinner) findViewById(R.id.spnCategoriaCoeficiente);

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, list);
        spnCategoriaCoeficiente.setAdapter(dataAdapter);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

    }

    public double valorCoeficiente(String coeficiente) {
        double valor = 0;
        if (coeficiente.equals("Edificaciones esenciales")) {
            valor = 1.5;
        } else if (coeficiente.equals("Estrucuras de ocupación especial")) {
            valor = 1.3;
        } else if (coeficiente.equals("Otras estructuras")) {
            valor = 1;
        }
        return valor;
    }

}
