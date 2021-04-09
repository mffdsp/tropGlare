package com.tropglare;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;


import com.handler.HandlerTool;
import com.index.DgiActivity;
import com.index.DgpActivity;
import com.index.DgpsActivity;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

        ImageView mainImg;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);

            getSupportActionBar().hide();

            setContentView(R.layout.activity_main);

            HandlerTool.buttonEffect(new ArrayList<View>(Arrays.asList(
                    findViewById(R.id.button1),
                    findViewById(R.id.button2),
                    findViewById(R.id.button3)
            )));


        mainImg = (ImageView) findViewById(R.id.mainImg);
        mainImg.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    Intent webIntent = new Intent(Intent.ACTION_VIEW).setData(Uri.parse("https://ctec.ufal.br/grupopesquisa/grilu/"));
                    startActivity(webIntent);
                }
            });

    }

    @Override
    public void onBackPressed() {

        Intent a = new Intent(Intent.ACTION_MAIN);
        a.addCategory(Intent.CATEGORY_HOME);
        a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(a);

    }

    public void b1(View v){
        startActivity(new Intent(this, DgpActivity.class));
    }

    public void b2(View v){
        startActivity(new Intent(this, DgpsActivity.class));
    }

    public void b3(View v){
        startActivity(new Intent(this, DgiActivity.class));
    }

}
