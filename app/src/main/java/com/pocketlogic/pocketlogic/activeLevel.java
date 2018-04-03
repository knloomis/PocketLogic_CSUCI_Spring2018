package com.pocketlogic.pocketlogic;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

/**
 * Created by Vitaliy Six on 2018/3/10.
 */

public class activeLevel extends AppCompatActivity {

    //DrawerLayout mDrawerLayout;
    // NavigationView navigationView;
    //private ActionBarDrawerToggle mToggle;

    NavigationView leftNavigationView;
    NavigationView rightNavigationView;
    DrawerLayout drawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.active_level);

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        //navigationView = (NavigationView) findViewById(R.id.drawer);
        //navigationView.setNavigationItemSelectedListener(this);
        addListeners();

        leftNavigationView = (NavigationView) findViewById(R.id.nav_view_left);
        //navigationView.setNavigationItemSelectedListener(this);
        leftNavigationView.bringToFront();

        leftNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                // Handle Left navigation view item clicks here.
                int id = item.getItemId();

                switch(id){
//            case R.id.drawerTruthTable:
//                break;
                    case R.id.drawerHelp:
                        //finish();
                        Intent help = new Intent(activeLevel.this, help.class);
                        startActivity(help);
                        overridePendingTransition(0,0);
                        help.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                        break;
                    case R.id.drawerRestart:
                        Intent restart = new Intent(activeLevel.this, RestartConfirmPopupActivity.class);
                        startActivity(restart);
                        overridePendingTransition(0,0);
                        restart.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                        break;
                    case R.id.drawerQuit:
                        Intent quit = new Intent(activeLevel.this, QuitConfirmPopupActivity.class);
                        startActivity(quit);
                        overridePendingTransition(0,0);
                        quit.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                        break;
                    default:
                        break;
                }

                drawer.closeDrawer(GravityCompat.START);
                return true;
            }
        });

        rightNavigationView = (NavigationView) findViewById(R.id.nav_view_right);
        rightNavigationView.bringToFront();
        rightNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                // Handle Right navigation view item clicks here.
                int id = item.getItemId();

                drawer.closeDrawer(GravityCompat.END); /*Important Line*/
                return true;
            }
        });

        //navigationView.bringToFront();
/*
        RelativeLayout layout = (RelativeLayout) findViewById(R.id.drawer_table_layout);
// Gets the layout params that will allow you to resize the layout
        ViewGroup.LayoutParams params = layout.getLayoutParams();

// Changes the height and width to the specified *pixels*
        params.height = 100;
        params.width = 100;
        layout.setLayoutParams(params);
        */

        //mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer);
        //mToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.open, R.string.close);
       //mDrawerLayout.addDrawerListener(mToggle);
        //mToggle.syncState();
        //getSupportActionBar().hide();
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //navigationView = (NavigationView) findViewById(R.id.nav);
    }
/*
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        switch(id){
//            case R.id.drawerTruthTable:
//                break;
            case R.id.drawerHelp:
                finish();
                Intent help = new Intent(activeLevel.this, help.class);
                startActivity(help);
                overridePendingTransition(0,0);
                help.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                break;
            case R.id.drawerRestart:
                Intent restart = new Intent(activeLevel.this, activeLevel.class);
                startActivity(restart);
                overridePendingTransition(0,0);
                restart.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                break;
            case R.id.drawerQuit:
                Intent quit = new Intent(activeLevel.this, selectLevel.class);
                startActivity(quit);
                overridePendingTransition(0,0);
                quit.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                break;
            default:
                break;
        }

        return true;
    }
    */

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

        Button menu= (Button) findViewById(R.id.btn_active1_menu);
        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ingameMenu.class);
                startActivity(intent);
            }
        });
    }

}
