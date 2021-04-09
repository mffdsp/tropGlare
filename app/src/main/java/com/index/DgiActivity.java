package com.index;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;
import com.handler.HandlerTool;
import com.tropglare.R;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DgiActivity extends AppCompatActivity {

    TextView tv5;
    ImageView iv1;

    //EditText EvInput, LInput, wInput, pInput;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setTitle("Cálculo de DGI");
        setContentView(R.layout.activity_dgi_acitivy);

        HandlerTool.buttonEffect(new ArrayList<View>(Arrays.asList(
                findViewById(R.id.button1)
        )));

    }

    public void b1 (View v){

        try {

            String LsInputText =  ((EditText) findViewById(R.id.editText1)).getText().toString();
            String LbnputText = ((EditText) findViewById(R.id.editText2)).getText().toString();
            String wInputText = ((EditText) findViewById(R.id.editText3)).getText().toString();
            String omegaInputText = ((EditText) findViewById(R.id.editText4)).getText().toString();

            if(TextUtils.isEmpty(LsInputText)
                    || TextUtils.isEmpty(LbnputText)
                    || TextUtils.isEmpty(wInputText)
                    ||TextUtils.isEmpty(omegaInputText)){

                HandlerTool.newSnack("Preencha todos os campos!", findViewById(R.id.dgi_view), Color.RED);
                tv5.setVisibility(View.INVISIBLE);
                throw new Exception("Empty Input");
            }

            float LsValue = Float.parseFloat(LsInputText);
            float Lbvalue = Float.parseFloat(LbnputText);
            float wValue = Float.parseFloat(wInputText);
            float omegaValue = Float.parseFloat(omegaInputText);

            double numerator = Math.pow(LsValue,1.6) * Math.pow(omegaValue, 0.8);
            double denominator = Lbvalue + (0.07*Math.pow(wValue,0.5) * LsValue);

            double result = 10 * Math.log10(0.478 * (numerator/denominator));

            if(Float.isNaN((float) result)){
                HandlerTool.newSnack("Verifique as entradas.", findViewById(R.id.dgi_view), Color.RED);
                tv5.setVisibility(View.INVISIBLE);
                throw new Exception("NaN exception");
            }
            tv5 = findViewById(R.id.tv5);

            HandlerTool.hideKeyboard(this);
            colorHandler(tv5, (result));
            tv5.setVisibility(View.VISIBLE);


        } catch (Exception e){
            System.err.println(e.toString());
        }

    }

    public void colorHandler(TextView tv, double result){

        boolean imperceptivel = result < 18;
        boolean perceptivel = result >= 18 && result <= 22;
        boolean perturbador = result > 22 && result < 31;
        boolean intoleravel = result >= 31;

        int color = 0;
        String finalText = "";

        if(imperceptivel){
            color = Color.rgb(255,215,0);
            finalText = "Ofuscamento Imperceptível";
        }else if(perceptivel){
            color = Color.rgb(255,165,0);
            finalText = "Ofuscamento aceitável";
        } else if(perturbador){
            color = Color.rgb(255,140,0);
            finalText = "Ofuscamento Desconfortável";
        } else if(intoleravel){
            color = Color.rgb(255,69,0);
            finalText = "Ofuscamento Intolerável";
        }
        tv5.setText("ÍNDICE DGI = " + String.format("%.0f", result) + "\n" + finalText);
        tv.setBackgroundColor(color);
    }

}
