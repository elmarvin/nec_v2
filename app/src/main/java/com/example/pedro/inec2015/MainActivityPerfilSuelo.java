package com.example.pedro.inec2015;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import android.view.View.OnClickListener;

import com.example.pedro.inec2015.model.PerfilSuelo;
import com.example.pedro.inec2015.model.ZonaZ;

public class MainActivityPerfilSuelo extends baseActivity {
    private RadioGroup rdgGrupo;
    private RadioButton rbA, rbB, rbC, rbD, rbE, rbF;
    private Button btnAceptarTS;
    private String string;
    private TextView tv;

    int id;
    String poblacion;
    String parroquia;
    String canton;
    String provincia;
    String factor;
    public static final String PREFS = "datos";
    private ZonaZ zonaZ = new ZonaZ(id, poblacion, parroquia, canton, provincia, factor);
    String perfilSuelo = null;


    public void init() {
        btnAceptarTS = (Button) findViewById(R.id.btnAceptarTS);

        rbA = (RadioButton) findViewById(R.id.rdbA);
        rbB = (RadioButton) findViewById(R.id.rdbB);
        rbC = (RadioButton) findViewById(R.id.rdbC);
        rbD = (RadioButton) findViewById(R.id.rdbD);
        rbE = (RadioButton) findViewById(R.id.rdbE);
        rbF = (RadioButton) findViewById(R.id.rdbF);
        tv = (TextView) findViewById(R.id.tvTipoSuelo);
        btnAceptarTS.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {




                SharedPreferences settings = getSharedPreferences(PREFS, 0);
                SharedPreferences.Editor editor;
                editor = settings.edit();
                editor.putString("perfilSuelo", perfilSuelo);
                editor.commit();

                startActivity(new Intent(getApplicationContext(), coeficiente_importancia.class));
                int selectedId = rdgGrupo.getCheckedRadioButtonId();

                // find which radioButton is checked by id
                if (selectedId == rbA.getId()) {
                    perfilSuelo = "A";

                   // intent.putExtra("perfilSuelo", perfilSuelo);
                    editor.putString("perfilSuelo", perfilSuelo);
                    editor.commit();
                    tv.setText("");

                   //startActivity(intent);
                } else if (selectedId == rbB.getId()) {
                    perfilSuelo = "B";
                    editor.putString("perfilSuelo", perfilSuelo);
                    editor.commit();
                } else if (selectedId == rbC.getId()) {
                    perfilSuelo = "C";
                    editor.putString("perfilSuelo", perfilSuelo);
                    editor.commit();
                } else if (selectedId == rbD.getId()) {
                    perfilSuelo = "D";
                    editor.putString("perfilSuelo", perfilSuelo);
                    editor.commit();
                } else if (selectedId == rbE.getId()) {
                    perfilSuelo = "E";
                    editor.putString("perfilSuelo", perfilSuelo);
                    editor.commit();
                } else if (selectedId == rbF.getId()){
                    perfilSuelo = "F";
                    editor.putString("perfilSuelo", perfilSuelo);
                    editor.commit();
                }
            }

        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.perfilsuelo);
        rdgGrupo = (RadioGroup) findViewById(R.id.myRadioGroup);


        init();
        String valor;
        btnAceptarTS.setEnabled(false);

        SharedPreferences z = getSharedPreferences(PREFS,0);
        String string = z.getString("valor","no hay valor");
        System.out.println("valor : "+string);

        rdgGrupo.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                // find which radio button is selected
                if (checkedId == R.id.rdbA) {
                    Toast.makeText(getApplicationContext(), "Escogio: A",
                            Toast.LENGTH_SHORT).show();
                    tv.setText("Perfil de roca competente.");
                    btnAceptarTS.setEnabled(true);

                } else if (checkedId == R.id.rdbB) {
                    Toast.makeText(getApplicationContext(), "Escogio: B",
                            Toast.LENGTH_SHORT).show();
                    tv.setText("Perfil de roca de rigidez media.");
                    btnAceptarTS.setEnabled(true);
                } else if (checkedId == R.id.rdbC) {
                    Toast.makeText(getApplicationContext(), "Escogio: C",
                            Toast.LENGTH_SHORT).show();
                    tv.setText("- Perfiles de suelos muy densos o roca blanda, que cumplan con el criterio de velocidad de la onda cortante.\n- Perfiles de suelos muy densos o roca blanda, que cumplan con cualquiera de los dos criterios");
                    btnAceptarTS.setEnabled(true);
                } else if (checkedId == R.id.rdbD) {
                    Toast.makeText(getApplicationContext(), "Escogio: D",
                            Toast.LENGTH_SHORT).show();
                    tv.setText("- Perfiles de suelos rígidos que cumplan  con el criterio de velocidad de la onda de cortante.\n- Perfiles de suelos rígidos que cumplan  con cualquiera de las dos condiciones.");
                    btnAceptarTS.setEnabled(true);
                } else if (checkedId == R.id.rdbE) {
                    Toast.makeText(getApplicationContext(), "Escogio: E",
                            Toast.LENGTH_SHORT).show();
                    tv.setText("- Perfil que cumpla el criterio de velocidad de la onda de cortante.\n- Perfil que contiene un espesor total H mayor de 3 m de arcillas blandas.");
                    btnAceptarTS.setEnabled(true);
                } else if (checkedId == R.id.rdbF) {
                    Toast.makeText(getApplicationContext(), "Escogio: F",
                            Toast.LENGTH_SHORT).show();
                    tv.setText("Los perfiles de suelo tipo F, requieren una evaluación realizada explicitamente en el sitio por un ingeniero geotecnista. Se contemplan las siguientes subclases :" +
                            "\nF1.- Suelos susceptibles a la falla o colapso causado por la excitación sismica, tales como: suelos licuables, arcillas sensitivas, suelos dispersivos o debilmente cementados, etc" +
                            "\nF2.- Turba y arcillas orgánicas y muy orgánicas (H > 3 m para turba o arcillas orgánicas y muy orgánicas." +
                            "\nF3.- Arcillas de muy alta plasticidad (H > 7.5 m con índice de plasticidad IP > 75)." +
                            "\nF4.- Perfiles de gran espesor de arcillas de rigidez mediana a blanda (H > 30 m)." +
                            "\nF5.- Suelos con constrastes de impedancia ocurriendo dentro de los primeros 30 m superiores del perfil de subsuelo, incluyendo contactos entre suelos blandos y roca, con variaciones bruscas de velocidad de ondas de corte" +
                            "\nF6.- Rellenos colocados sin control ingenieril.");
                    btnAceptarTS.setEnabled(true);

                }
            }

        });

    }


}
