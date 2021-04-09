package com.index;

import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;
import com.handler.HandlerTool;
import com.tropglare.R;

import java.util.ArrayList;
import java.util.Arrays;

public class DgpsActivity extends AppCompatActivity {

    TextView tv5;
    ImageView iv1;
    //EditText EvInput, LInput, wInput, pInput;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setTitle("Cálculo de DGPs");
        setContentView(R.layout.activity_dgps_acitivy);

        HandlerTool.buttonEffect(new ArrayList<View>(Arrays.asList(
                findViewById(R.id.button1)
        )));
    }

    public void b1 (View v){

        try {

            String EvInputText =  ((EditText) findViewById(R.id.editText1)).getText().toString();

            if(TextUtils.isEmpty(EvInputText)){
                HandlerTool.newSnack("Preencha todos os campos!", findViewById(R.id.dgps_view), Color.RED);
                tv5.setVisibility(View.INVISIBLE);
                throw new Exception("Empty Input");
            }

            float EvValue = Float.parseFloat(EvInputText);

            double result = (6.22 * Math.pow(10, -5) * EvValue) + 0.184;

            if(Float.isNaN((float) result)){
                HandlerTool.newSnack("Verifique as entradas.", findViewById(R.id.dgps_view), Color.RED);
                tv5.setVisibility(View.INVISIBLE);
                throw new Exception("NaN exception");
            }


            tv5 = findViewById(R.id.tv5);

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
        tv5.setText("ÍNDICE DGPs = " + String.format("%.3f", result) + "\n" + finalText);
        tv.setBackgroundColor(color);
    }

}
