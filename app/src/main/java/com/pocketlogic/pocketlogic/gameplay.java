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
    int num_grid_tiles = 36;
    int num_tile_types = 8;
    int num_switches = 4;

    int inputA = 0;
    int inputB = 0;
    int inputC = 0;
    int inputD = 0;

    TruthTable table;

    // Create an array to hold all the gates in the game
    gate grid[] = new gate[num_grid_tiles];
    gate switches[] = new gate[num_switches];
    int[] drawables = new int[]{R.drawable.and, R.drawable.or, R.drawable.xor, R.drawable.nor, R.drawable.xnor, R.drawable.nand, R.drawable.not, R.drawable.hexagon};
    int[] switch_graphics = new int[]{R.drawable.switch_0, R.drawable.switch_1};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gameplay);
        addListeners();

        table = new TruthTable();

        // Initiate the rest of the tiles
        for (int index = 5; index < num_grid_tiles; index++) {
            grid[index] = new gate(true);
        }

        // TODO: Set "switch" or "locked" based on imported level settings array
        for (int index = 0; index < num_switches; index++) {
            grid[index] = new gate(false);
            switches[index] = new gate(false);
        }

        // Please for the love of god delete the following code once we get the switch array working


        // end of aforementioned screwed code

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
//               case R.id.drawerTruthTable:
//                break;
                    case R.id.drawerHelp:
                        // finish();
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

        ImageView[] cells = new ImageView[num_grid_tiles];
        for(int curr_cell = 0; curr_cell < num_grid_tiles; curr_cell++){
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
                    final_cells[final_curr_cell].setImageResource(drawables[(grid[final_curr_cell].type++) % num_tile_types]);
                }
            });
        }
        */
//final int curr_num = 0;
//final ImageView currView = cells[curr_num];
        final ImageView C0 = (ImageView) findViewById(R.id.C0);
        C0.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                C0.setImageResource(switch_graphics[(grid[0].type++) % 2]);
                // C0 is locked.
                // This row is reserved for switches.
                // TODO: This tile may or may not contain a switch depending on level preset values
            }
        });

        final ImageView C1 = (ImageView) findViewById(R.id.C1);
        C1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // C1.setImageResource(drawables[(grid[1].type++) % num_tile_types]);
                // This row is reserved for switches.
                // TODO: This tile may or may not contain a switch depending on level preset values
            }
        });

        final ImageView C2 = (ImageView) findViewById(R.id.C2);
        C2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // C2.setImageResource(drawables[(grid[2].type++) % num_tile_types]);
                // This row is reserved for switches.
                // TODO: This tile may or may not contain a switch depending on level preset values
            }
        });

        final ImageView C3 = (ImageView) findViewById(R.id.C3);
        C3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // C3.setImageResource(drawables[(grid[3].type++) % num_tile_types]);
                // This row is reserved for switches.
                // TODO: This tile may or may not contain a switch depending on level preset values
            }
        });

        final ImageView C4 = (ImageView) findViewById(R.id.C4);
        C4.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // C4.setImageResource(drawables[(grid[4].type++) % num_tile_types]);
                // This row is reserved for switches.
                // TODO: This tile may or may not contain a switch depending on level preset values
            }
        });

        final ImageView C5 = findViewById(R.id.C5);
        C5.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                C5.setImageResource(drawables[(grid[5].type++) % num_tile_types]);
            }
        });

        final ImageView C6 = findViewById(R.id.C6);
        C6.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                C6.setImageResource(drawables[(grid[6].type++) % num_tile_types]);
            }
        });

        final ImageView C7 = (ImageView) findViewById(R.id.C7);
        C7.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                C7.setImageResource(drawables[(grid[7].type++) % num_tile_types]);
            }
        });

        final ImageView C8 = (ImageView) findViewById(R.id.C8);
        C8.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                C8.setImageResource(drawables[(grid[8].type++) % num_tile_types]);
            }
        });

        final ImageView C9 = (ImageView) findViewById(R.id.C9);
        C9.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                C9.setImageResource(drawables[(grid[9].type++) % num_tile_types]);
            }
        });

        final ImageView C10 = (ImageView) findViewById(R.id.C10);
        C10.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                C10.setImageResource(drawables[(grid[10].type++) % num_tile_types]);
            }
        });

        final ImageView C11 = (ImageView) findViewById(R.id.C11);
        C11.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                C11.setImageResource(drawables[(grid[11].type++) % num_tile_types]);
            }
        });

        final ImageView C12 = (ImageView) findViewById(R.id.C12);
        C12.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                C12.setImageResource(drawables[(grid[12].type++) % num_tile_types]);
            }
        });

        final ImageView C13 = (ImageView) findViewById(R.id.C13);
        C13.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                C13.setImageResource(drawables[(grid[13].type++) % num_tile_types]);
            }
        });

        final ImageView C14 = (ImageView) findViewById(R.id.C14);
        C14.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                C14.setImageResource(drawables[(grid[14].type++) % num_tile_types]);
            }
        });

        final ImageView C15 = (ImageView) findViewById(R.id.C15);
        C15.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                C15.setImageResource(drawables[(grid[15].type++) % num_tile_types]);
            }
        });

        final ImageView C16 = (ImageView) findViewById(R.id.C16);
        C16.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                C16.setImageResource(drawables[(grid[16].type++) % num_tile_types]);
            }
        });

        final ImageView C17 = (ImageView) findViewById(R.id.C17);
        C17.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                C17.setImageResource(drawables[(grid[17].type++) % num_tile_types]);
            }
        });

        final ImageView C18 = (ImageView) findViewById(R.id.C18);
        C18.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                C18.setImageResource(drawables[(grid[18].type++) % num_tile_types]);
            }
        });

        final ImageView C19 = (ImageView) findViewById(R.id.C19);
        C19.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                C19.setImageResource(drawables[(grid[19].type++) % num_tile_types]);
            }
        });

        final ImageView C20 = (ImageView) findViewById(R.id.C20);
        C20.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                C20.setImageResource(drawables[(grid[20].type++) % num_tile_types]);
            }
        });

        final ImageView C21 = (ImageView) findViewById(R.id.C21);
        C21.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                C21.setImageResource(drawables[(grid[21].type++) % num_tile_types]);
            }
        });

        final ImageView C22 = (ImageView) findViewById(R.id.C22);
        C22.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                C22.setImageResource(drawables[(grid[22].type++) % num_tile_types]);
            }
        });

        final ImageView C23 = (ImageView) findViewById(R.id.C23);
        C23.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                C23.setImageResource(drawables[(grid[23].type++) % num_tile_types]);
            }
        });

        final ImageView C24 = (ImageView) findViewById(R.id.C24);
        C24.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                C24.setImageResource(drawables[(grid[24].type++) % num_tile_types]);
            }
        });

        final ImageView C25 = (ImageView) findViewById(R.id.C25);
        C25.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                C25.setImageResource(drawables[(grid[25].type++) % num_tile_types]);
            }
        });

        final ImageView C26 = (ImageView) findViewById(R.id.C26);
        C26.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                C26.setImageResource(drawables[(grid[26].type++) % num_tile_types]);
            }
        });

        final ImageView C27 = (ImageView) findViewById(R.id.C27);
        C27.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                C27.setImageResource(drawables[(grid[27].type++) % num_tile_types]);
            }
        });

        final ImageView C28 = (ImageView) findViewById(R.id.C28);
        C28.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                C28.setImageResource(drawables[(grid[28].type++) % num_tile_types]);
            }
        });

        final ImageView C29 = (ImageView) findViewById(R.id.C29);
        C29.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                C29.setImageResource(drawables[(grid[29].type++) % num_tile_types]);
            }
        });

        final ImageView C30 = (ImageView) findViewById(R.id.C30);
        C30.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                C30.setImageResource(drawables[(grid[30].type++) % num_tile_types]);
            }
        });

        final ImageView C31 = (ImageView) findViewById(R.id.C31);
        C31.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                C31.setImageResource(drawables[(grid[31].type++) % num_tile_types]);
            }
        });

        final ImageView C32 = (ImageView) findViewById(R.id.C32);
        C32.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                C32.setImageResource(drawables[(grid[32].type++) % num_tile_types]);
            }
        });

        final ImageView C33 = (ImageView) findViewById(R.id.C33);
        C33.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                C33.setImageResource(drawables[(grid[33].type++) % num_tile_types]);
            }
        });

        final ImageView C34 = (ImageView) findViewById(R.id.C34);
        C34.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                C34.setImageResource(drawables[(grid[34].type++) % num_tile_types]);
            }
        });

        final ImageView C35 = (ImageView) findViewById(R.id.C35);
        C35.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                grid[35].rotateImageType();
                C35.setImageResource(grid[35].getImageType());
            }
        });



        //TO DO: fix broken code lol
        // inside of each listener to onClick should include method call to updateOutputValue

       /*

        ImageView[] switchCells = new ImageView[num_switches];
        for(int curr_switch = 0; curr_switch < num_switches; curr_switch++){
            int switch_num = curr_switch;
            String stringID = "R.id.C" + switch_num;
            int resourceID = getResources().getIdentifier(stringID, "id", getPackageName());
            switchCells[curr_switch] = (ImageView) findViewById(resourceID);
        }

        */

        final ImageView output = (ImageView) findViewById(R.id.output);
        //output.setImageResource(table.getImageType());
        output.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //here to bring up truth table eval
                Intent eval = new Intent(getApplicationContext(), evaluation.class);
                startActivity(eval);
            }
        });




        final ImageView switchA = (ImageView) findViewById(R.id.inputA);
        switchA.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                switches[0].rotateImageType();
                switchA.setImageResource(switches[0].getImageType());
                inputA = switches[0].getType();
                table.switchOutputValue(3);
                output.setImageResource(table.getImageType());

            }
        });


        final ImageView switchB = (ImageView) findViewById(R.id.inputB);
        switchB.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                switches[1].rotateImageType();
                switchB.setImageResource(switches[1].getImageType());
                inputB = switches[1].getType();
                table.switchOutputValue(2);
                output.setImageResource(table.getImageType());
            }
        });

        final ImageView switchC = (ImageView) findViewById(R.id.inputC);
        switchC.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                switches[2].rotateImageType();
                switchC.setImageResource(switches[2].getImageType());
                inputC = switches[2].getType();
                table.switchOutputValue(1);
                output.setImageResource(table.getImageType());
            }
        });

        final ImageView switchD = (ImageView) findViewById(R.id.inputD);
        switchD.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                switches[3].rotateImageType();
                switchD.setImageResource(switches[3].getImageType());
                inputD = switches[3].getType();
                table.switchOutputValue(0);
                output.setImageResource(table.getImageType());
            }
        });


    }

}

