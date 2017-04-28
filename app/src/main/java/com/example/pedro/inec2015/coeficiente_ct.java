package com.example.pedro.inec2015;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.AdapterView.OnItemSelectedListener;

public class coeficiente_ct extends baseActivity implements OnItemSelectedListener {
    Button btnAceptarCt;
    Spinner menu, submenu;
    public static final String PREFS = "datos";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coeficiente_ct);
        menu = (Spinner) findViewById(R.id.spnConfTipo);
        submenu = (Spinner) findViewById(R.id.spnMenu);

      /*  Intent intent = new Intent(coeficiente_ct.this, configuracion.class);
        Bundle extras = getIntent().getExtras();
        String z = extras.getString("z");
        String provincia = extras.getString("provincia1");
        String perfilSuelo = getIntent().getStringExtra("perfilSuelo");
        String coeficienteI = getIntent().getStringExtra("coeficienteI");
        String coeficienteR = getIntent().getStringExtra("coeficienteR");
        intent.putExtra("provincia1", provincia);
        intent.putExtra("z", z);
        intent.putExtra("perfilSuelo", perfilSuelo);
        intent.putExtra("coeficienteI", coeficienteI);
        intent.putExtra("coeficienteR", coeficienteR);
        startActivity(intent);*/

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this,
                R.array.coeficientect, //Se carga el array definido en el XML
                android.R.layout.simple_spinner_item);

        //Se carga el tipo de vista para el adaptador
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        //Se aplica el adaptador al Spinner de localidades
        menu.setAdapter(adapter);
        //Se aplica listener para saber que item ha sido seleccionado
        //y poder usarlo en el método "onItemSelected"
        menu.setOnItemSelectedListener(this);

        final Button btnAceptarCt = (Button) findViewById(R.id.btnAceptarCt);
        btnAceptarCt.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click
//                zonaZ.setFactor(valor);
                double ct = 0;
                double a = 0;
                //startActivity(new Intent(getApplicationContext(),MainActivityPerfilSuelo.class));
                String resultado = submenu.getSelectedItem().toString();

                System.out.println("Categoria : " + resultado);
                if (resultado.equals("Sin arriostramientos")) {
                    ct = 0.072;
                    a = 0.8;
                }else if(resultado.equals("Con arriostramientos")){
                    ct = 0.073;
                    a = 0.75;
                }else if (resultado.equals("Sin muros estructurales ni diagonales rigidizadoras")){
                    ct = 0.055;
                    a = 0.9;
                }else if (resultado.equals("Con muros estructurales o diagonales rigidizadoras y para otras estructuras basadas en muros estructurales y mamposteria estructural")){
                    ct = 0.055;
                    a = 0.75;
                }
                System.out.println("Valor ct : "+ct);
                System.out.println("Valor a : "+a);

                //zonaZ.setFactor(valor);
                String valorct = String.valueOf(ct);
                String valora = String.valueOf(a);
                SharedPreferences settings = getSharedPreferences(PREFS, 0);
                SharedPreferences.Editor editor;
                editor = settings.edit();
                editor.putString("ct", valorct);
                editor.putString("a", valora);
                editor.commit();

                Intent intent = new Intent(coeficiente_ct.this,configuracion.class);
                startActivity(intent);
                finish();

                startActivity(new Intent(getApplicationContext(), configuracion.class));
            }
        });

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        //Se guarda en array de enteros los arrays de las provincias
        int[] localidades = {R.array.array_estructura, R.array.array_portico};

        //Construcción del "adaptador" para el segundo Spinner
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this,
                localidades[position],
                android.R.layout.simple_spinner_item);

        //Se carga el tipo de vista para el adaptador
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        //Se aplica el adaptador al Spinner de localidades
        submenu.setAdapter(adapter);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        // TODO Auto-generated method stub

    }

}
