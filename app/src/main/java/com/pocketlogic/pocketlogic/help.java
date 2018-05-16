package com.pocketlogic.pocketlogic;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.method.ScrollingMovementMethod;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;


/**
 * Created by Vitaliy Six on 2018/3/10.
 */

public class help extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.help_screen);

        TextView textView = findViewById(R.id.playInfoText);
        textView.setMovementMethod(new ScrollingMovementMethod());
        //textView.setCompoundDrawablesWithIntrinsicBounds(R.drawable.xor, 0, 0, 0);

        Button btn_close = (Button) findViewById(R.id.btn_help_back);
        btn_close.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                finish();
            }
        });
        //addListeners();

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int) (width*.95), (int) (height *.95));

        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.gravity = Gravity.CENTER;
        params.x=0;
        params.y=-20;
        getWindow().setAttributes(params);

       //getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    }
/*
    public void addListeners() {
        final Context context = this;

        Button button= (Button) findViewById(R.id.btn_help_back);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent intent = new Intent(context, selectLevel.class);
                //startActivity(intent);
                finish();
            }
        });
    }
    */
}
