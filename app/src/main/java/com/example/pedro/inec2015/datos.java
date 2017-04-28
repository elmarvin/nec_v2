package com.example.pedro.inec2015;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.SQLException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pedro.inec2015.database.DatabaseHelper;
import com.github.mikephil.charting.charts.LineChart;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class datos extends baseActivity implements View.OnClickListener{
    private TextView tvZ, tvFA, tvN, tvFS, tvFD, tvT0, tvTc, tvR,tvTa;
    private EditText etH;
    private DatabaseHelper mDBHelper;
    public static final String PREFS = "datos";
    private  Button btnH;
    List<List<Double>> ValoresXY = new ArrayList<List<Double>>();


    ArrayList<Double> xvals = new ArrayList<Double>();
    ArrayList<Double> yvals = new ArrayList<Double>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_datos);
        tvZ = (TextView) findViewById(R.id.tvZ);
        tvFA = (TextView) findViewById(R.id.tvFA);
        tvN = (TextView) findViewById(R.id.tvN);
        tvFS = (TextView) findViewById(R.id.tvFs);
        tvFD = (TextView) findViewById(R.id.tvFd);
        tvT0 = (TextView) findViewById(R.id.tvT0);
        tvTc = (TextView) findViewById(R.id.tvTc);
        tvR = (TextView) findViewById(R.id.tvR);
        tvTa = (TextView) findViewById(R.id.tvTa);
        etH = (EditText) findViewById(R.id.etH);


        for(int i3 =0; i3<= 1; i3++){
            ValoresXY.add(new ArrayList<Double>());
        }
        String v = "";
        mDBHelper = new DatabaseHelper(this);
        mDBHelper.openDatabase();


        Intent intent = getIntent();
        //valor z
        //String value = getIntent().getStringExtra("z");
        //String provincia = getIntent().getStringExtra("provincia1");
        // String perfilSuelo = getIntent().getStringExtra("perfilSuelo");
        // System.out.println("Provincia :" + provincia);
        // System.out.println("Factor z :" + value);
        // System.out.println("Perfil Suelo :" + perfilSuelo);
        //System.out.println("Valor z :" + darValorZ(value));
        // System.out.println("Factor FA :" + fa(darValorZ(value), perfilSuelo));

        SharedPreferences z = getSharedPreferences(PREFS, 0);
        String value = z.getString("valor", "no hay valor");
        String provincia = z.getString("provincia", "no hay valor");
        String perfilSuelo = z.getString("perfilSuelo", "no hay valor");
        String coeficienteI = z.getString("coeficienteI", "no hay valor");
        String coeficienteR = z.getString("coeficienteR", "no hay valor");
        String ct = z.getString("ct", "no hay valor");
        String a = z.getString("a", "no hay valor");
        String ei = z.getString("ei", "no hay valor");
        String pi = z.getString("pi", "no hay valor");
        tvZ.setText(value);
        tvN.setText(String.valueOf(setAmpliacionEspectral(provincia)));
        String r = String.valueOf(setR(perfilSuelo));
        tvR.setText(r);

        System.out.println(setAmpliacionEspectral(provincia));
        v = darValorZ(value);
        tvFA.setText(fa(v, perfilSuelo));

        tvFD.setText(fd(v, perfilSuelo));
        tvFS.setText(fs(v, perfilSuelo));
        double t0 = t0(fs(v, perfilSuelo), fa(v, perfilSuelo), fd(v, perfilSuelo));
        double tc =tc(fs(v, perfilSuelo), fa(v, perfilSuelo), fd(v, perfilSuelo));
        tvT0.setText(String.valueOf(t0));
        tvTc.setText(String.valueOf(tc(fs(v, perfilSuelo), fa(v, perfilSuelo), fd(v, perfilSuelo))));
        final Button btnH = (Button) findViewById(R.id.btnH);
        final EditText edH = (EditText)findViewById(R.id.etH);
        edH.setOnClickListener(this);
;
        btnH.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String string = etH.getText().toString();
                double h = Double.parseDouble(string);
                SharedPreferences z = getSharedPreferences(PREFS, 0);
                String ct = z.getString("ct", "no hay valor");
                String a = z.getString("a", "no hay valor");


                tvTa.setText(String.valueOf(ta(ct,h,a)));
            }
        });
        //Variables
        double valueZ = Double.parseDouble(value.replace(",", "."));
        double fa= Double.parseDouble(fa(v, perfilSuelo).replace(",", "."));
        double i= Double.parseDouble(coeficienteI);
        double r1= Double.parseDouble(coeficienteR);
        double Cpi= Double.parseDouble(pi);
        double Cei= Double.parseDouble(ei);
        double AEspectral = setAmpliacionEspectral(provincia);
        double R1 = setR(perfilSuelo);

        double v1 = v1(valueZ,fa,i,r1,Cei,Cpi);
        double v2 = v2(valueZ,fa,i,r1,Cei,Cpi,AEspectral);
        double v3 = v3(valueZ,fa,i,r1,Cei,Cpi,AEspectral,tc,R1);
       System.out.println("V1 : "+  v1);
        System.out.println("V2 : "+  v2);
        System.out.println("V3 : "+v3);

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
        double T = 4;
        double x,y;
        x=-5.0;
       GraphView graph = (GraphView) findViewById(R.id.graph);
       LineGraphSeries<DataPoint> series = new LineGraphSeries<DataPoint>(new DataPoint[] {


               new DataPoint(0, v1),
               new DataPoint(t0, v2),
               new DataPoint(tc, v2),
            /*   new DataPoint(0.41250000000000003, 0.090675),
                new DataPoint(1.4125, 0.026480309734513277),
                new DataPoint(2.4125, 0.015504015544041452),
                new DataPoint(3.4125, 0.010960714285714287),*/

        });


        LineGraphSeries<DataPoint> series1 = new LineGraphSeries<DataPoint>();

        //deberia ser para m<= ValoresXy.size()


        for (int m = 0; m < ValoresXY.get(1).size(); m++) {
            double dou = xvals.get(m);

            series1.appendData(new DataPoint(ValoresXY.get(0).get(m) , ValoresXY.get(1).get(m)),true,500);

        }

       /* for (int i2 = 0; i2 <= ValoresXY.size() ; i++) {
          //  System.out.println("x: " + ValoresXY.get(1).get(i2) );
        }*/
        System.out.println("VALORES : "+ValoresXY.get(1));
        System.out.println("logitud :"+ValoresXY.size());
        System.out.println("VALORES X: "+ValoresXY.get(0));
    System.out.println("VALORES Y: "+ValoresXY.get(1));
    System.out.println("longitud Y: "+ValoresXY.get(1).size());
    System.out.println("longitud X: "+ValoresXY.get(0));
      //  System.out.println(ValoresXY.get);
        graph.addSeries(series);
        graph.addSeries(series1);
        graph.setTitle("Espectro de Diseno");
        graph.getViewport().setScalable(true);
        graph.getViewport().setScrollable(true);


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

    public String darValorZ(String value) {
        if (value.equals("0,15")) {
            value = "UNO";
        } else if (value.equals("0,25")) {
            value = "DOS";
        } else if (value.equals("0,3")) {
            value = "TRES";
        } else if (value.equals("0,35")) {
            value = "TRESC";
        } else if (value.equals("0,4")) {
            value = "CUATRO";
        } else if (value.equals("0,5")) {
            value = "CINCO";
        }
        return value;
    }

    //obtener valores
    public String fa(String z, String perfil) {
        String v = mDBHelper.getFActorfa(z, perfil);
        return v;
    }

    public String fd(String z, String perfil) {
        String v = mDBHelper.getFActorfd(z, perfil);
        return v;
    }

    public String fs(String z, String perfil) {
        String v = mDBHelper.getFActorfs(z, perfil);
        return v;
    }

    public double t0(String fs, String fa, String fd) {
        //convertir string a double sustituyendo coma por punto
        double valorFS = Double.parseDouble(fs.replace(",", "."));
        double valorFA = Double.parseDouble(fa.replace(",", "."));
        double valorFD = Double.parseDouble(fd.replace(",", "."));

        double c = (0.10);
        double f0 = ((c) * valorFS * (valorFD / valorFA));
        return f0;
    }

    public double tc(String fs, String fa, String fd) {
        //convertir string a double sustituyendo coma por punto
        double valorFS = Double.parseDouble(fs.replace(",", "."));
        double valorFA = Double.parseDouble(fa.replace(",", "."));
        double valorFD = Double.parseDouble(fd.replace(",", "."));

        double c = (0.55);
        double f0 = ((c) * valorFS * (valorFD / valorFA));
        return f0;
    }

    public double setAmpliacionEspectral(String provincia) {
        double v = mDBHelper.getAmpliacionEspectral(provincia);
        return v;
    }

    public double setR(String perfilSuelo) {
        double valor = 0;
        if (perfilSuelo.equals("E")) {
            valor = 1.5;
        } else {
            valor = 1;
        }
        return valor;
    }

    public double ta(String ct, double altura, String a) {
        double valorCt = Double.parseDouble(ct);
        double valorA = Double.parseDouble(a);
        double ta = valorCt * Math.pow(altura, valorA);
        return ta;
    }
    public double v1(double z,double fa1,double i,double r,double ei,double pi){
        double v=0;
        v = ((i*(z*fa1))/(r*ei*pi));
        return v;
    }
    public double v2(double z,double fa1,double i,double r,double ei,double pi,double n){
        double v=0;
        v = ((i*(n*z*fa1))/(r*ei*pi));
        return v;
    }
    public double v3(double z,double fa1,double i,double r,double ei,double pi,double n,double tc,double R){
        double v=0;
        double c=4;


        for (double t=tc; t< c; t+=0.1){

            v = ((i*(n*z*fa1* Math.pow     ((tc/t),R)))   /   (r*ei*pi));

            System.out.println("X : "+t);
            System.out.println("Y : "+v);
            ValoresXY.get(0).add(t);
            ValoresXY.get(1).add(v);
            xvals.add(t);
            yvals.add(v);



        }

        return v;
    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        etH.setText("");

    }
}
