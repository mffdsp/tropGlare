package com.index;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.handler.HandlerTool;
import com.tropglare.R;

import java.util.ArrayList;
import java.util.Arrays;

public class DgpActivity extends AppCompatActivity {

    TextView tv5;
    ImageView iv1;
    //EditText EvInput, LInput, wInput, pInput;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setTitle("Cálculo de DGP");
        setContentView(R.layout.activity_dgp_acitivy);

        HandlerTool.buttonEffect(new ArrayList<View>(Arrays.asList(
                findViewById(R.id.button1)
        )));
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void b1 (View v){

        try {

            String EvInputText =  ((EditText) findViewById(R.id.editText1)).getText().toString();
            String LInputText = ((EditText) findViewById(R.id.editText2)).getText().toString();
            String wInputText = ((EditText) findViewById(R.id.editText3)).getText().toString();
            String pInputText = ((EditText) findViewById(R.id.editText4)).getText().toString();

            if(TextUtils.isEmpty(EvInputText)
                    || TextUtils.isEmpty(LInputText)
                    || TextUtils.isEmpty(wInputText)
                    ||TextUtils.isEmpty(pInputText)){

                HandlerTool.newSnack("Preencha todos os campos!", findViewById(R.id.dgp_view), Color.RED);
                tv5.setVisibility(View.INVISIBLE);
                throw new Exception("Empty Input");
            }

            float EvValue = Float.parseFloat(EvInputText);
            float Lvalue = Float.parseFloat(LInputText);
            float wValue = Float.parseFloat(wInputText);
            float pValue = Float.parseFloat(pInputText);

            double numerator = Math.pow(Lvalue,2) * wValue;
            double denominator = Math.pow(EvValue, 1.87) * Math.pow(pValue,2);

            double log = Math.log10(1 + numerator/denominator);

            double firstTerm = 5.87 * Math.pow(10, -5) * EvValue;
            double secondTerm = 9.18 * Math.pow(10, -2) * log;

            double result = firstTerm + secondTerm + 0.16;

            if(Float.isNaN((float) result)){
                HandlerTool.newSnack("Verifique as entradas.", findViewById(R.id.dgp_view), Color.RED);
                tv5.setVisibility(View.INVISIBLE);
                throw new Exception("NaN exception");
            }

            if(!Double.isFinite(result)){
                HandlerTool.newSnack("Há um erro na entrada. Por favor verifique-a.", findViewById(R.id.dgi_view), Color.RED);
                tv5.setVisibility(View.INVISIBLE);
                throw new Exception("NaN exception");
            }

            tv5 = findViewById(R.id.tv5);
            //iv1 = findViewById(R.id.iv1);

            HandlerTool.hideKeyboard(this);
            colorHandler(tv5, result);
            tv5.setVisibility(View.VISIBLE);
            //iv1.setVisibility(View.VISIBLE);


        } catch (Exception e){
            System.err.println(e.toString());
        }

    }

    public void colorHandler(TextView tv, double result){

        boolean imperceptivel = result < 0.35;
        boolean perceptivel = result >= 0.35 && result < 0.4;
        boolean perturbador =  result >= 0.4 && result < 0.45;
        boolean intoleravel = result >= 0.45;

        int color = 0;
        String finalText = "";

        if(imperceptivel){
            color = Color.rgb(255,215,0);
            finalText = "Ofuscamento Imperceptível";
        }else if(perceptivel){
            color = Color.rgb(255,165,0);
            finalText = "Ofuscamento Perceptível";
        } else if(perturbador){
            color = Color.rgb(255,140,0);
            finalText = "Ofuscamento Perturbador";
        } else if(intoleravel){
            color = Color.rgb(255,69,0);
            finalText = "Ofuscamento Intolerável";
        }
        tv5.setText("ÍNDICE DGP = " + String.format("%.3f", result) + "\n" + finalText);
        tv.setBackgroundColor(color);
    }

    public void hintOnClick(View v){
        Snackbar snackbar = Snackbar.make(findViewById(R.id.dgp_ac), "Clique nos textos das variáveis para informações adicionais", Snackbar.LENGTH_SHORT);
        snackbar.getView().setBackgroundColor(Color.parseColor("#DAA520"));
        snackbar.show();

        //Toast.makeText(DgiActivity.this, "Clique nos textos das variáveis para informações adicionais", Toast.LENGTH_SHORT).show();
    }
    public void tv1(View v){
        Toast.makeText(DgpActivity.this, "iluminância vertical em lux", Toast.LENGTH_SHORT).show();
    }
    public void tv2(View v){
        Toast.makeText(DgpActivity.this, "luminância em cd/m²", Toast.LENGTH_SHORT).show();

    }
    public void tv3(View v){
        Toast.makeText(DgpActivity.this, "Ângulo sólido em esferorradiano", Toast.LENGTH_SHORT).show();

    }
    public void tv4(View v){
        Toast.makeText(DgpActivity.this, "Índice de posição de Guth para o campo de visão", Toast.LENGTH_SHORT).show();

    }
}
