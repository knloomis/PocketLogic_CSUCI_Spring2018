package com.pocketlogic.pocketlogic;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

/**
 * Created by Vitaliy Six on 2018/3/10.
 */

public class activeLevel2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.active_level2);
        addListeners();
    }

    public void addListeners() {
        final Context context = this;

        Button test= (Button) findViewById(R.id.btn_active_test);
        test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, evaluation.class);
                startActivity(intent);
            }
        });

        Button menu= (Button) findViewById(R.id.btn_active2_menu);
        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ingameMenu.class);
                startActivity(intent);
            }
        });
    }
}
