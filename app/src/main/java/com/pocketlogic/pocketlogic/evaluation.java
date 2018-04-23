package com.pocketlogic.pocketlogic;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

/**
 * Created by Vitaliy Six on 2018/3/10.
 */

public class evaluation extends AppCompatActivity {

    Button win;
    Button back;

    double height_modifier = 1;
    double width_modifier = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Intent intent = getIntent();
        boolean[] resultsFromPrevious = new boolean[(int)Math.pow(2,4)];
        Bundle extras = getIntent().getExtras();
        if(extras != null){
            for(int i = 0; i < Math.pow(2,4); i++){
                resultsFromPrevious[i] = extras.getBoolean("" + i);
            }
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_evaluation);
        addListeners();

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int) (width*width_modifier), (int) (height *height_modifier));

        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.gravity = Gravity.CENTER;
        params.x=0;
        params.y=-20;
        getWindow().setAttributes(params);

        showResults(resultsFromPrevious);
    }

    public void addListeners() {
        final Context context = this;

        win = (Button) findViewById(R.id.btn_eval_close);
        win.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, winScreen.class);
                startActivity(intent);
            }
        });

        back = (Button) findViewById(R.id.btn_eval_back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public void showResults(boolean[] results){
        boolean allMatch = true;
        for(boolean result: results){
            allMatch &= result;
        }

        if(allMatch){
       //     Toast.makeText(getApplicationContext(), "Result: true", Toast.LENGTH_LONG).show();
            //code to make finish button visible
            win.setVisibility(View.VISIBLE);
            back.setVisibility(View.GONE);

        }else{
        //    Toast.makeText(getApplicationContext(), "Result: false", Toast.LENGTH_LONG).show();
        }
    }
}
