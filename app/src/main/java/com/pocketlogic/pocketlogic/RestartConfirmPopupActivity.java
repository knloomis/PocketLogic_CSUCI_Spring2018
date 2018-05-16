package com.pocketlogic.pocketlogic;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

public class RestartConfirmPopupActivity extends Activity {

    Button btn_close_yes;
    Button btn_close_no;
    double height_modifier = .5;
    double width_modifier = .7;
    String levelName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restart_confirm_popup);

        Intent intent = getIntent();
        levelName = intent.getExtras().getString("levelName");


        btn_close_yes = (Button) findViewById(R.id.btn_restart_popup_close_yes);
        btn_close_no = (Button) findViewById(R.id.btn_restart_popup_close_no);

        btn_close_no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                finish();
            }
        });

        btn_close_yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                //finish();
                Intent restart = new Intent(getApplicationContext(), GameScene.class);
                restart.putExtra("levelName", levelName);
                startActivity(restart);
                overridePendingTransition(0,0);
                restart.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
            }
        });

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

        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    }
}
