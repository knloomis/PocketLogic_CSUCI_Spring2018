package com.pocketlogic.pocketlogic;


import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class gameplay extends AppCompatActivity {
    NavigationView rightNavigationView;
    NavigationView leftNavigationView;
    DrawerLayout drawer;
    int max_num_gates = 36;

    // Create an array to hold all the gates in the game
    gate grid[] = new gate[36];
    int[] drawables = new int[]{R.drawable.and, R.drawable.or, R.drawable.xor, R.drawable.nor, R.drawable.xnor, R.drawable.not, R.drawable.empty_grid_space};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gameplay);
        addListeners();

        for (int index = 0; index < 36; index++)
        {
            grid[index] = new gate();
        }
        grid[5].type = 6;

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
                        Intent help = new Intent(gameplay.this, help.class);
                        startActivity(help);
                        overridePendingTransition(0,0);
                        help.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                        break;
                    case R.id.drawerRestart:
                        Intent restart = new Intent(gameplay.this, RestartConfirmPopupActivity.class);
                        startActivity(restart);
                        overridePendingTransition(0,0);
                        restart.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                        break;
                    case R.id.drawerQuit:
                        Intent quit = new Intent(gameplay.this, QuitConfirmPopupActivity.class);
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

    }

    public void addListeners() {
        final Context context = this;

        ImageView[] cells = new ImageView[max_num_gates];
        for(int curr_cell = 0; curr_cell < max_num_gates; curr_cell++){
            final int final_curr_cell = curr_cell;

            int cell_num = final_curr_cell;
            String stringID = "R.id.C" + cell_num;
            int resourceID = getResources().getIdentifier(stringID, "id", getPackageName());
            cells[final_curr_cell] = (ImageView) findViewById(resourceID);
        }
/*
        final ImageView[] final_cells = cells;

        for(int i = 0; i < max_num_gates; i++){
            final int final_curr_cell = i;
            final_cells[final_curr_cell].setOnClickListener(new View.OnClickListener() {
                public void onClick(View v){
                    final_cells[final_curr_cell].setImageResource(drawables[(grid[final_curr_cell].type++) % 7]);
                }
            });
        }
        */
//final int curr_num = 0;
//final ImageView currView = cells[curr_num];
        final ImageView C0 = (ImageView) findViewById(R.id.C0);
        C0.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                C0.setImageResource(drawables[(grid[0].type++) % 7]);
            }
        });

        final ImageView C1 = (ImageView) findViewById(R.id.C1);
        C1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                C1.setImageResource(drawables[(grid[1].type++) % 7]);
            }
        });

        final ImageView C2 = (ImageView) findViewById(R.id.C2);
        C2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                C2.setImageResource(drawables[(grid[2].type++) % 7]);
            }
        });

        final ImageView C3 = (ImageView) findViewById(R.id.C3);
        C3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                C3.setImageResource(drawables[(grid[3].type++) % 7]);
            }
        });

        final ImageView C4 = (ImageView) findViewById(R.id.C4);
        C4.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                C4.setImageResource(drawables[(grid[4].type++) % 7]);
            }
        });

        final ImageView C5 = (ImageView) findViewById(R.id.C5);
        C5.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                C5.setImageResource(drawables[(grid[5].type++) % 7]);
            }
        });

        final ImageView C6 = (ImageView) findViewById(R.id.C6);
        C6.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                C6.setImageResource(drawables[(grid[6].type++) % 7]);
            }
        });

        final ImageView C7 = (ImageView) findViewById(R.id.C7);
        C7.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                C7.setImageResource(drawables[(grid[7].type++) % 7]);
            }
        });

        final ImageView C8 = (ImageView) findViewById(R.id.C8);
        C8.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                C8.setImageResource(drawables[(grid[8].type++) % 7]);
            }
        });

        final ImageView C9 = (ImageView) findViewById(R.id.C9);
        C9.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                C9.setImageResource(drawables[(grid[9].type++) % 7]);
            }
        });

        final ImageView C10 = (ImageView) findViewById(R.id.C10);
        C10.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                C10.setImageResource(drawables[(grid[10].type++) % 7]);
            }
        });

        final ImageView C11 = (ImageView) findViewById(R.id.C11);
        C11.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                C11.setImageResource(drawables[(grid[11].type++) % 7]);
            }
        });

        final ImageView C12 = (ImageView) findViewById(R.id.C12);
        C12.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                C12.setImageResource(drawables[(grid[12].type++) % 7]);
            }
        });

        final ImageView C13 = (ImageView) findViewById(R.id.C13);
        C13.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                C13.setImageResource(drawables[(grid[13].type++) % 7]);
            }
        });

        final ImageView C14 = (ImageView) findViewById(R.id.C14);
        C14.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                C14.setImageResource(drawables[(grid[14].type++) % 7]);
            }
        });

        final ImageView C15 = (ImageView) findViewById(R.id.C15);
        C15.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                C15.setImageResource(drawables[(grid[15].type++) % 7]);
            }
        });

        final ImageView C16 = (ImageView) findViewById(R.id.C16);
        C16.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                C16.setImageResource(drawables[(grid[16].type++) % 7]);
            }
        });

        final ImageView C17 = (ImageView) findViewById(R.id.C17);
        C17.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                C17.setImageResource(drawables[(grid[17].type++) % 7]);
            }
        });

        final ImageView C18 = (ImageView) findViewById(R.id.C18);
        C18.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                C18.setImageResource(drawables[(grid[18].type++) % 7]);
            }
        });

        final ImageView C19 = (ImageView) findViewById(R.id.C19);
        C19.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                C19.setImageResource(drawables[(grid[19].type++) % 7]);
            }
        });

        final ImageView C20 = (ImageView) findViewById(R.id.C20);
        C20.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                C20.setImageResource(drawables[(grid[20].type++) % 7]);
            }
        });

        final ImageView C21 = (ImageView) findViewById(R.id.C21);
        C21.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                C21.setImageResource(drawables[(grid[21].type++) % 7]);
            }
        });

        final ImageView C22 = (ImageView) findViewById(R.id.C22);
        C22.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                C22.setImageResource(drawables[(grid[22].type++) % 7]);
            }
        });

        final ImageView C23 = (ImageView) findViewById(R.id.C23);
        C23.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                C23.setImageResource(drawables[(grid[23].type++) % 7]);
            }
        });

        final ImageView C24 = (ImageView) findViewById(R.id.C24);
        C24.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                C24.setImageResource(drawables[(grid[24].type++) % 7]);
            }
        });

        final ImageView C25 = (ImageView) findViewById(R.id.C25);
        C25.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                C25.setImageResource(drawables[(grid[25].type++) % 7]);
            }
        });

        final ImageView C26 = (ImageView) findViewById(R.id.C26);
        C26.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                C26.setImageResource(drawables[(grid[26].type++) % 7]);
            }
        });

        final ImageView C27 = (ImageView) findViewById(R.id.C27);
        C27.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                C27.setImageResource(drawables[(grid[27].type++) % 7]);
            }
        });

        final ImageView C28 = (ImageView) findViewById(R.id.C28);
        C28.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                C28.setImageResource(drawables[(grid[28].type++) % 7]);
            }
        });

        final ImageView C29 = (ImageView) findViewById(R.id.C29);
        C29.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                C29.setImageResource(drawables[(grid[29].type++) % 7]);
            }
        });

        final ImageView C30 = (ImageView) findViewById(R.id.C30);
        C30.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                C30.setImageResource(drawables[(grid[30].type++) % 7]);
            }
        });

        final ImageView C31 = (ImageView) findViewById(R.id.C31);
        C31.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                C31.setImageResource(drawables[(grid[31].type++) % 7]);
            }
        });

        final ImageView C32 = (ImageView) findViewById(R.id.C32);
        C32.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                C32.setImageResource(drawables[(grid[32].type++) % 7]);
            }
        });

        final ImageView C33 = (ImageView) findViewById(R.id.C33);
        C33.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                C33.setImageResource(drawables[(grid[33].type++) % 7]);
            }
        });

        final ImageView C34 = (ImageView) findViewById(R.id.C34);
        C34.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                C34.setImageResource(drawables[(grid[34].type++) % 7]);
            }
        });

        final ImageView C35 = (ImageView) findViewById(R.id.C35);
        C35.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                C35.setImageResource(drawables[(grid[35].type++) % 7]);
            }
        });

    }

}

