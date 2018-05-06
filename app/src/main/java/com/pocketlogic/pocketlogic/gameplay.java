package com.pocketlogic.pocketlogic;


import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.drawable.BitmapDrawable;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Context;
import android.view.Display;
import android.view.View;
import android.widget.ImageView;
import android.content.Intent;
import android.support.v4.view.GravityCompat;
import android.view.MenuItem;
import android.widget.LinearLayout;

import java.util.ArrayList;

/*
//COMMENT: when I was going to commit on gitkraken, it claimed some of these were removed. Saving them here in case problems occur...

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

    //private int[] outputValues = {0, 1, 1, 0, 1, 1, 0, 0, 0, 1, 1, 0, 1, 1, 0, 0};
    private int[] outputValues = {0, 0, 0, 0, 0, 1, 0, 1, 0, 0, 0, 0, 0, 1, 0, 1};

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
            switches[index].setPositionNum(index);
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
        final ImageView outputButton = (ImageView) findViewById(R.id.light_1);

        for(int curr_cell = 0; curr_cell < num_grid_tiles; curr_cell++){
            final int final_curr_cell = curr_cell;
            int resourceID = getGateResourceID(curr_cell);
            tileImages[curr_cell] = (ImageView) findViewById(resourceID);

            tileImages[curr_cell].setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    tileImages[final_curr_cell].setImageResource(grid[final_curr_cell].getNextImage());
                    if(grid[final_curr_cell].getType() == 0){
                        TilePair pair = getPairWithTileInList(grid[final_curr_cell]);
                        if(pair != null){
                            //TO DO: looks like this is not actually removing the connection
                            pair.getOutputTile().changeInputConnection(pair.getInputTile());

                            // Visually, removes the line between a pair if one of them becomes a blank tile
                            connectedTiles.remove(getPairWithTileInList(grid[final_curr_cell]));
                            drawLines();
                        }

                    }
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
                            //Toast.makeText(getApplicationContext(), "Added pair to list", Toast.LENGTH_SHORT).show();
                        }else{
                            connectedTiles.remove(getPairInList(activeTile, thisTile));
                            //Toast.makeText(getApplicationContext(), "Removed pair from list", Toast.LENGTH_SHORT).show();
                        }

                        outputButton.setImageResource(output.getImage());

                        drawLines();
                        activeTile = null;

                    }
                    //add to onclick listener: if active: if type != 0, set as their output; else, remove connection if exists and set inactive


                    return true;
                }
            });
        }

        //output.setImageResource(table.getImageType());
        outputButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //here to bring up truth table eval
                // Maybe instead, run eval really quick here, then send string results to new activity?
                Intent eval = new Intent(getApplicationContext(), evaluation.class);
                boolean[] evalResults = getAllEvalResults();

               // boolean[] evalResults = fakeResults();
                for(int i = 0; i < Math.pow(2, num_switches); i++){
                    eval.putExtra("" + i, evalResults[i]);
                }

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
                        //Toast.makeText(getApplicationContext(), "Added pair to list", Toast.LENGTH_SHORT).show();
                    }else{
                        connectedTiles.remove(getPairInList(activeTile, thisTile));
                        //Toast.makeText(getApplicationContext(), "Removed pair from list", Toast.LENGTH_SHORT).show();
                    }

                    outputButton.setImageResource(output.getImage());

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

                    drawLines();

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
        linePaint.setColor(Color.GRAY);
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
            Tile currInput = currPair.getInputTile();
            Tile currOutput = currPair.getOutputTile();

           // Toast.makeText(getApplicationContext(), "Input value: " + currInput.eval(), Toast.LENGTH_SHORT).show();

            int inputValue = currInput.eval();
            if(inputValue == 0){
                linePaint.setColor(getResources().getColor(R.color.switch_green));
            }else if(inputValue == 1){
                linePaint.setColor(getResources().getColor(R.color.switch_purple));
            }else{
                linePaint.setColor(Color.GRAY);
            }



//            Toast.makeText(getApplicationContext(), ""+ currInput.getX() +"; " + currInput.getY() + "; " + currOutput.getX() + "; " + currOutput.getY(), Toast.LENGTH_SHORT).show();
//
           canvas.drawLine(currInput.getX(), currInput.getY(), currOutput.getX(), currOutput.getY(), linePaint);
        }

        updateLinesDisplayed();
    }

 //   public void longClickContent(Tile lastClickedTile){
        //

    public int getSwitchResourceID(int num){
        switch(num){
            case 0:
                return R.id.switch_1;
            case 1:
                return R.id.switch_2;
            case 2:
                return R.id.switch_3;
            case 3:
                return R.id.switch_4;
            default:
                return 0;
        }
    }
    public int getGateResourceID(int num){
        switch(num){
        /*    case 0:
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
                return R.id.tile_32;
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
                return R.id.tile_23;
            case 20:
                return R.id.C20;
            case 21:
                return R.id.tile_43;
            case 22:
                return R.id.C22;
            case 23:
                return R.id.C15;
            case 24:
                return R.id.C24;
            case 25:
                return R.id.C25;
            case 26:
                return R.id.C26;
            case 27:
                return R.id.tile_14;
            case 28:
                return R.id.C28;
            case 29:
                return R.id.tile_34;
            case 30:
                return R.id.C30;
            case 31:
                return R.id.C31;
            case 32:
                return R.id.D14;
            case 33:
                return R.id.D15;
            case 34:
                return R.id.D16;
            case 35:
                return R.id.D17;
           */ default:
                return 0;
        }
    }

    public int getOutputID(){
        return R.id.light_1;
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

    public boolean[] getAllEvalResults(){
        for(Switch currInput: switches) {
            currInput.setType(0);
        }
        switches[3].setType(1);
        output.setValue(getOutputValueOfRow(getCurrRowNum()));

        boolean[] results = new boolean[(int) Math.pow(2, num_switches)];
        int currResultIndex = 0;

        //boolean allMatch = true;

        for(int firstBitCount = 0; firstBitCount < 2; firstBitCount++) {
            for(int secondBitCount = 0; secondBitCount < 2; secondBitCount++) {
                for(int thirdBitCount = 0; thirdBitCount < 2; thirdBitCount++) {
                    for(int fourthBitCount = 0; fourthBitCount < 2; fourthBitCount++) {
                        switches[3].getNext();
                        output.setValue(getOutputValueOfRow(getCurrRowNum()));
                        results[currResultIndex] = output.currentValueMatches();
                        currResultIndex++;

                    }
                    // CODE HERE: switch third bit
                    switches[2].getNext();
                }
                //CODE HERE: switch second bit
                switches[1].getNext();
            }
            //CODE HERE: switch first bit
            switches[0].getNext();
        }

        return results;
    }

    public boolean[] fakeResults(){
        return new boolean[] {true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true};
    }

    public TilePair getPairWithTileInList(Tile tile){
        for(TilePair currPair: connectedTiles){
            if(currPair.hasTile(tile)){
                return currPair;
            }
        }
        return null;
    }

    public void helpPopup(View tileImgView){
        Intent help = new Intent(gameplay.this, help.class);
        startActivity(help);
        overridePendingTransition(0,0);
        //help.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);

    }

    public void restartPopup(View tileImgView){
        Intent restart = new Intent(gameplay.this, RestartConfirmPopupActivity.class);
        startActivity(restart);
        overridePendingTransition(0,0);
        restart.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
    }

    public void quitPopup(View tileImgView){
        Intent quit = new Intent(gameplay.this, QuitConfirmPopupActivity.class);
        startActivity(quit);
        overridePendingTransition(0,0);
        quit.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
    }


}