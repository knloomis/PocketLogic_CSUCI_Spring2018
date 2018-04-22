package com.pocketlogic.pocketlogic;


import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.drawable.BitmapDrawable;
import android.media.Image;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Context;
import android.text.Layout;
import android.view.Display;
import android.view.View;
import android.widget.ImageView;
import android.content.Intent;
import android.support.v4.view.GravityCompat;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;

/*
//COMMENT: when I was going to commit on gitkraken, it claimed some of these were removed. Saving them here in case problems occurr...

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
 */

public class gameplay extends AppCompatActivity {
    NavigationView rightNavigationView;
    NavigationView leftNavigationView;
    DrawerLayout drawer;
    Canvas canvas;
    LinearLayout lineLayout;
    Bitmap canvasBitmap;
    Paint linePaint;

    ArrayList<TilePair> connectedTiles = new ArrayList<TilePair>();




    int num_grid_tiles = 36;
    int num_switches = 4;

    final int[][] INPUTS1 ={{1}, {0}};
    final int[][] INPUTS2 ={{0, 0}, {0, 1}, {1, 0}, {1, 1}};
    final int[][] INPUTS3 ={{0, 0, 0}, {0, 0, 1},
            {0, 1, 0}, {0, 1, 1}, {1, 0, 0},
            {1, 0, 1}, {1, 1, 0}, {1, 1, 1}};
    final int[][] INPUTS4 = {{0, 0, 0, 0},
            {0, 0, 0, 1}, {0, 0, 1, 0},
            {0, 0, 1, 1}, {0, 1, 0, 0},
            {0, 1, 0, 1}, {0, 1, 1, 0},
            {0, 1, 1, 1}, {1, 0, 0, 0},
            {1, 0, 0, 1}, {1, 0, 1, 0},
            {1, 0, 1, 1}, {1, 1, 0, 0},
            {1, 1, 0, 1}, {1, 1, 1, 0},
            {1, 1, 1, 1},};

    private int[] outputValues = {0, 1, 1, 0, 1, 1, 0, 0, 0, 1, 1, 0, 1, 1, 0, 0};

    // Create an array to hold all the gates in the game
    //Level level = new Level();
    Tile grid[] = new Tile[num_grid_tiles];
    Switch switches[] = new Switch[num_switches];
    Output output;

    Tile activeTile = null;

    ImageView[] tileImages = new ImageView[num_grid_tiles];

    ImageView[] switchImages = new ImageView[num_switches];







    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gameplay);
        //addListeners();

        for (int index = 0; index < num_grid_tiles; index++)
        {
            grid[index] = new gate();
            grid[index].setPositionNum(index);
        }

        for(int index = 0; index < num_switches; index++){
            switches[index] = new Switch();
        }

        output = new Output(getOutputValueOfRow(getCurrRowNum()));

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        initializeLineDrawingComponents();

        addListeners();

        leftNavigationView = (NavigationView) findViewById(R.id.nav_view_left);
        leftNavigationView.bringToFront();

        leftNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                // Handle Left navigation view item clicks here.
                int id = item.getItemId();

                switch(id){

                    case R.id.drawerHelp:
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

                drawer.closeDrawer(GravityCompat.END);
                return true;
            }
        });

    }

    public void addListeners() {
        final Context context = this;

        for(int curr_cell = 0; curr_cell < num_grid_tiles; curr_cell++){
            final int final_curr_cell = curr_cell;
            int resourceID = getGateResourceID(curr_cell);
            tileImages[curr_cell] = (ImageView) findViewById(resourceID);

            tileImages[curr_cell].setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    tileImages[final_curr_cell].setImageResource(grid[final_curr_cell].getNextImage());
                }
            });

            tileImages[curr_cell].setOnLongClickListener(new View.OnLongClickListener() {
                public boolean onLongClick(View v) {
                    Tile thisTile = grid[final_curr_cell];
                    //logic: onLongClick, if no one active, set as active
                    // if someone active, set as their output; OR if was someone's output and clicked them again, remove connection
                    if(activeTile == null){
                        activeTile = thisTile;
                    }else{

                        if(activeTile.changeInputConnection(thisTile)){
                            connectedTiles.add(new TilePair(activeTile, thisTile));
                            Toast.makeText(getApplicationContext(), "Added pair to list", Toast.LENGTH_SHORT).show();
                        }else{
                            connectedTiles.remove(getPairInList(activeTile, thisTile));
                            Toast.makeText(getApplicationContext(), "Removed pair from list", Toast.LENGTH_SHORT).show();
                        }

                        drawLines();
                        activeTile = null;

                    }
                    //add to onclick listener: if active: if type != 0, set as their output; else, remove connection if exists and set inactive


                    return true;
                }
            });
        }

        final ImageView outputButton = (ImageView) findViewById(R.id.output);
        //output.setImageResource(table.getImageType());
        outputButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //here to bring up truth table eval
                Intent eval = new Intent(getApplicationContext(), evaluation.class);
                startActivity(eval);
            }
        });
        outputButton.setOnLongClickListener(new View.OnLongClickListener() {
            public boolean onLongClick(View v) {
                Tile thisTile = output;
                //logic: onLongClick, if no one active, set as active
                // if someone active, set as their output; OR if was someone's output and clicked them again, remove connection
                if(activeTile != null){

                    if(thisTile.changeInputConnection(activeTile)){
                        connectedTiles.add(new TilePair(activeTile, thisTile));
                        Toast.makeText(getApplicationContext(), "Added pair to list", Toast.LENGTH_SHORT).show();
                    }else{
                        connectedTiles.remove(getPairInList(activeTile, thisTile));
                        Toast.makeText(getApplicationContext(), "Removed pair from list", Toast.LENGTH_SHORT).show();
                    }

                    drawLines();
                    activeTile = null;

                }

                return true;
            }
        });



        for(int curr_cell = 0; curr_cell < num_switches; curr_cell++) {
            final int final_curr_cell = curr_cell;
            int resourceID = getSwitchResourceID(curr_cell);
            switchImages[curr_cell] = (ImageView) findViewById(resourceID);

            switchImages[curr_cell].setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    switchImages[final_curr_cell].setImageResource(switches[final_curr_cell].getNextImage());
                    output.setValue(getOutputValueOfRow(getCurrRowNum()));
                    outputButton.setImageResource(output.getImage());
                }
            });

            switchImages[curr_cell].setOnLongClickListener(new View.OnLongClickListener() {
                public boolean onLongClick(View v) {
                    Tile thisTile = switches[final_curr_cell];
                    //logic: onLongClick, if no one active, set as active
                    // if someone active, set as their output; OR if was someone's output and clicked them again, remove connection
                    if(activeTile == null){
                        activeTile = thisTile;
                    }


                    return true;
                }
            });
        }

    }

    public int getCurrRowNum() {
        String binaryString = "";
        for(int i = 0; i < 4; i++) {
            binaryString += switches[i].getType();
        }

        return(Integer.parseInt(binaryString, 2));
    }

    public int getOutputValueOfRow(int row){
        return outputValues[row];
    }

    //public void changeConnections(Tile inputTile, Tile outputTile){
    //    outputTile.changeInputConnection(inputTile);
    //    inputTile.changeOutputConnection(outputTile);
    //}


    public void initializeLineDrawingComponents(){
        Display metrics = getWindowManager().getDefaultDisplay();
        lineLayout = (LinearLayout) findViewById(R.id.lines_layout);

        int height = metrics.getHeight();
        int width = metrics.getWidth();

        canvasBitmap = Bitmap.createBitmap(height, width, Bitmap.Config.ARGB_8888);
        canvas = new Canvas(canvasBitmap);

        linePaint = new Paint();
        linePaint.setColor(Color.RED);
        linePaint.setStrokeWidth(10);
        //linePaint.setAntiAlias(true);
    }

    // What actually updates all lines shown on the screen
    public void updateLinesDisplayed(){
        lineLayout.setBackgroundDrawable(new BitmapDrawable(canvasBitmap));
    }

    public void clearAllLinesDisplayed(){
        canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
    }

    public void drawLines(){
        clearAllLinesDisplayed();

        for(TilePair currPair: connectedTiles){
 //           Toast.makeText(getApplicationContext(), "Got a pair!", Toast.LENGTH_SHORT).show();
            Tile first = currPair.getInputTile();
            Tile second = currPair.getOutputTile();

            int firstNum = first.getPositionNum();
            int secondNum = second.getPositionNum();

            ImageView firstImage = tileImages[firstNum];
            ImageView secondImage = tileImages[secondNum];

            if(firstImage == null ){
                Toast.makeText(getApplicationContext(),"null!", Toast.LENGTH_SHORT).show();
            }

            int[] img1_coordinates = new int[2];


//            //TO DO: change below to dynamically show color of current outputValue of input tile of pair:
            linePaint.setColor(getResources().getColor(R.color.red));
            //Toast.makeText(getApplicationContext(), ""+ firstImage.getX() +"; " + firstImage.getY() + "; " + secondImage.getX() + "; " + secondImage.getY(), Toast.LENGTH_SHORT).show();
//
           canvas.drawLine((firstImage.getLeft() + firstImage.getWidth())/2.F, (firstImage.getBottom() + firstImage.getHeight())/2.F, (secondImage.getLeft()+ secondImage.getWidth())/2.F, (secondImage.getBottom()+ secondImage.getWidth())/2.F, linePaint);
        }

        updateLinesDisplayed();
    }

 //   public void longClickContent(Tile lastClickedTile){
        //

    public int getSwitchResourceID(int num){
        switch(num){
            case 0:
                return R.id.inputA;
            case 1:
                return R.id.inputB;
            case 2:
                return R.id.inputC;
            case 3:
                return R.id.inputD;
            default:
                return 0;
        }
    }
    public int getGateResourceID(int num){
        switch(num){
            case 0:
                return R.id.C0;
            case 1:
                return R.id.C1;
            case 2:
                return R.id.C2;
            case 3:
                return R.id.C3;
            case 4:
                return R.id.C4;
            case 5:
                return R.id.C5;
            case 6:
                return R.id.C6;
            case 7:
                return R.id.C7;
            case 8:
                return R.id.C8;
            case 9:
                return R.id.C9;
            case 10:
                return R.id.C10;
            case 11:
                return R.id.C11;
            case 12:
                return R.id.C12;
            case 13:
                return R.id.C13;
            case 14:
                return R.id.C14;
            case 15:
                return R.id.C15;
            case 16:
                return R.id.C16;
            case 17:
                return R.id.C17;
            case 18:
                return R.id.C18;
            case 19:
                return R.id.C19;
            case 20:
                return R.id.C20;
            case 21:
                return R.id.C21;
            case 22:
                return R.id.C22;
            case 23:
                return R.id.C23;
            case 24:
                return R.id.C24;
            case 25:
                return R.id.C25;
            case 26:
                return R.id.C26;
            case 27:
                return R.id.C27;
            case 28:
                return R.id.C28;
            case 29:
                return R.id.C29;
            case 30:
                return R.id.C30;
            case 31:
                return R.id.C31;
            case 32:
                return R.id.C32;
            case 33:
                return R.id.C33;
            case 34:
                return R.id.C34;
            case 35:
                return R.id.C35;
            default:
                return 0;
        }
    }

    public int getOutputID(){
        return R.id.output;
    }

    public int getResourceID(Tile tile){
        if(tile instanceof gate){
            return getGateResourceID(tile.getPositionNum());
        }else if(tile instanceof Switch){
            return getSwitchResourceID(tile.getPositionNum());
        }else if(tile instanceof Output){
            return getOutputID();
        }
        return 0;
    }

    public TilePair getPairInList(Tile currInput, Tile currOutput){
        for(TilePair currPair : connectedTiles){
            if(currPair.pairMatches(currInput, currOutput)){
                return currPair;
            }
        }
        return null;
    }

    public Output getOutput(){

        return output;
    }

    public int[] getOutputValues(){
        return outputValues;
    }

    public Switch[] getInputs(){
        return switches;
    }

}