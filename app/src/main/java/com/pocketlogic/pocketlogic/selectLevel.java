package com.pocketlogic.pocketlogic;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class selectLevel extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        Log.d("mine","selectLevel Triggered");

        addListeners();
    }

    public void addListeners() {
        final Context context = this;

        Button btn_select= (Button) this.findViewById(R.id.btn_main_select);
        btn_select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("mine","onClick Triggered");
                Intent intent = new Intent(context, selectLevel2.class);
                startActivity(intent);
            }
        });

        Button btn_scores= (Button) this.findViewById(R.id.btn_main_scores);
        btn_scores.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, highscores.class);
                startActivity(intent);
            }
        });

        Button btn_help= (Button) this.findViewById(R.id.btn_main_help);
        btn_help.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, help.class);
                startActivity(intent);
            }
        });

    }
}
