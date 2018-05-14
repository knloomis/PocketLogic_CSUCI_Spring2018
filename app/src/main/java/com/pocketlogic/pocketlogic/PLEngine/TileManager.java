package com.pocketlogic.pocketlogic.PLEngine;

import android.app.Activity;
import android.graphics.Color;
import android.util.Log;
import android.widget.ImageView;
import com.pocketlogic.pocketlogic.R;

/*======================================   USER MANUAL    ==========================================
Usage: arrayList of all tiles
It will loads all tiles, and give you whatever tile you want.

Declaration
TileManager tileManager = new TileManager((Activity)this);

Available Functions

Tile findTileById(String id)                  <- Returns the tile for matching ID.
Tile findTileByView(ImageView view)           <- Returns the tile for matching view.
Tile[] getAllTiles()                          <- Returns all tiles.

*/

public class TileManager{
    //===============================       PRIVATE MEMBERS      ===================================
    private Tile[] tileLibrary; //All tiles, range 0-24
    public Activity activity;   //MainActivity

    //=====================================    CONSTRUCTOR    ======================================
    public TileManager(Activity activity){

        tileLibrary = new Tile [15];        //Init tile array
        this.activity = activity;           //Storage activity
        loadLibrary();                      //Loads all views to tile
        loadCoordinates();                  //Set XY for all tiles

    }

    //=====================================   TILE GETTERS    ======================================
    public Tile findTileById(String id) {
        for (int i=0;i<tileLibrary.length;i++) {
            if (tileLibrary[i].getID().equals(id)) {
                return tileLibrary[i];
            }
        }
        Log.d("ERROR:","findTileById::Invalid Tile ID");
        return new Tile(new ImageView(this.activity));
    }

    public Tile findTileByView(ImageView view) {
        for (int i=0;i<tileLibrary.length;i++) {
            if (tileLibrary[i].getView().equals(view)) {
                return tileLibrary[i];
            }
        }
        Log.d("ERROR:","findTileByView::Invalid Tile View");
        return new Tile(new ImageView(this.activity));
    }

    public Tile[] getAllTiles() {
        return tileLibrary;
    }

    //====================================   LOAD FUNCTIONS    =====================================
    private void loadLibrary(){
        tileLibrary[0] = new Tile ((ImageView) this.activity.findViewById(R.id.switch_1));
        tileLibrary[1] = new Tile ((ImageView) this.activity.findViewById(R.id.switch_2));
        tileLibrary[2] = new Tile ((ImageView) this.activity.findViewById(R.id.switch_3));
        tileLibrary[3] = new Tile ((ImageView) this.activity.findViewById(R.id.switch_4));
        //tileLibrary[4] = new Tile ((ImageView) this.activity.findViewById(R.id.tile_11));
        tileLibrary[4] = new Tile ((ImageView) this.activity.findViewById(R.id.tile_21));
        //tileLibrary[6] = new Tile ((ImageView) this.activity.findViewById(R.id.tile_31));
        tileLibrary[5] = new Tile ((ImageView) this.activity.findViewById(R.id.tile_41));
        //tileLibrary[8] = new Tile ((ImageView) this.activity.findViewById(R.id.tile_51));
        tileLibrary[6] = new Tile ((ImageView) this.activity.findViewById(R.id.tile_12));
        //tileLibrary[10] = new Tile ((ImageView) this.activity.findViewById(R.id.tile_22));
        tileLibrary[7] = new Tile ((ImageView) this.activity.findViewById(R.id.tile_32));
        //tileLibrary[12] = new Tile ((ImageView) this.activity.findViewById(R.id.tile_42));
        tileLibrary[8] = new Tile ((ImageView) this.activity.findViewById(R.id.tile_52));
        //tileLibrary[14] = new Tile ((ImageView) this.activity.findViewById(R.id.tile_13));
        tileLibrary[9] = new Tile ((ImageView) this.activity.findViewById(R.id.tile_23));
        //tileLibrary[16] = new Tile ((ImageView) this.activity.findViewById(R.id.tile_33));
        tileLibrary[10] = new Tile ((ImageView) this.activity.findViewById(R.id.tile_43));
        //tileLibrary[18] = new Tile ((ImageView) this.activity.findViewById(R.id.tile_53));
        tileLibrary[11] = new Tile ((ImageView) this.activity.findViewById(R.id.tile_14));
        //tileLibrary[20] = new Tile ((ImageView) this.activity.findViewById(R.id.tile_24));
        tileLibrary[12] = new Tile ((ImageView) this.activity.findViewById(R.id.tile_34));
        //tileLibrary[22] = new Tile ((ImageView) this.activity.findViewById(R.id.tile_44));
        tileLibrary[13] = new Tile ((ImageView) this.activity.findViewById(R.id.tile_54));
        tileLibrary[14] = new Tile ((ImageView) this.activity.findViewById(R.id.light_1));
    }

    private void loadCoordinates(){
        //Switches XY
        tileLibrary[0].setXY(new int [] {295,210});
        tileLibrary[1].setXY(new int [] {575,210});
        tileLibrary[2].setXY(new int [] {855,210});
        tileLibrary[3].setXY(new int [] {1135,210});

        //Tile_11 - Tile_51 XY
        //tileLibrary[4].setXY(new int [] {165,685});
        tileLibrary[4].setXY(new int [] {443,572});
        //tileLibrary[6].setXY(new int [] {725,685});
        tileLibrary[5].setXY(new int [] {1003,572});
        //tileLibrary[8].setXY(new int [] {1285,685});

        //Tile_12 - Tile_52 XY
        tileLibrary[6].setXY(new int [] {161,1070});
        //tileLibrary[10].setXY(new int [] {445,1120});
        tileLibrary[7].setXY(new int [] {721,1070});
        //tileLibrary[12].setXY(new int [] {1005,1120});
        tileLibrary[8].setXY(new int [] {1282,1070});

        //Tile_13 - Tile_53 XY
        //tileLibrary[14].setXY(new int [] {165,1550});
        tileLibrary[9].setXY(new int [] {443,1577});
        //tileLibrary[16].setXY(new int [] {725,1550});
        tileLibrary[10].setXY(new int [] {999,1577});
        //tileLibrary[18].setXY(new int [] {1285,1550});

        //Tile_14 - Tile_54 XY
        tileLibrary[11].setXY(new int [] {162,2082});
        //tileLibrary[20].setXY(new int [] {445,1985});
        tileLibrary[12].setXY(new int [] {721,2082});
        //tileLibrary[22].setXY(new int [] {1005,1985});
        tileLibrary[13].setXY(new int [] {1282,2082});

        //light_1 XY
        tileLibrary[14].setXY(new int [] {721,2530});

    }

    // Debug for XY offset
    // Will draw a line for each tile from top to bottom.
    private void drawLineAcrossTiles(){
        LineDrawer lineDrawer = new LineDrawer((ImageView) this.activity.findViewById(R.id.superCanvas));
        lineDrawer.newPaint();

        for (int i=0;i<tileLibrary.length;i++) {
            lineDrawer.drawLine(tileLibrary[i].getTopXY()[0],tileLibrary[i].getTopXY()[1],tileLibrary[i].getBottomXY()[0], tileLibrary[i].getBottomXY()[1], Color.BLUE);
        }

        lineDrawer.renderLines();
    }
}
