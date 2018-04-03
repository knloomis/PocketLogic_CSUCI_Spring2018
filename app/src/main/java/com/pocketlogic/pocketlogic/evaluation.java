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

public class evaluation extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_evaluation);
        addListeners();
    }

    public void addListeners() {
        final Context context = this;

        Button menu= (Button) findViewById(R.id.btn_eval_close);
        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, winScreen.class);
                startActivity(intent);
            }
        });
    }
}
