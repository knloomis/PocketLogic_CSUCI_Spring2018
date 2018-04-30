package com.pocketlogic.pocketlogic;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

/*======================================   USER MANUAL    ==========================================
Usage: All game logic implements hear.

Declaration
gameDirector = new GameDirector(this,this);

Functions

//=====     HANDLERS     =====
void tileClick(String tileID)
void tileSelect(String choice)
void switchSelect(String choice)

//=====     PROCESSOR     =====
void linkTile(String tileID1, String tileID2)
void disconnectTile(String tileID1)

//=====     GRAPHIC     =====
void renderGraphics()

//=====     EVALUATION    =====
void evalEachTiles()
String evalTiles(String A, String B, String Operator)

//====      GATES      =====
AND
OR
XOR
...
*/

public class GameDirector {
    //===============================       PRIVATE MEMBERS      ===================================

    // Android System
    private Context context;
    private Activity activity;

    // Core Variables
    private TileManager tileManager;    //The data structure which stores all Tiles
    private LineDrawer lineDrawer;      //A line drawer
    private Boolean linkingMode;        // true - next tile click will link two tiles together;
    // false - next tile click will bring up a menu
    private String clickedTile_ID;      // The tile being clicked

    //=====================================    CONSTRUCTOR    ======================================
    public GameDirector(Context context, Activity activity){
        this.context = context;
        this.activity = activity;

        //Init all tiles
        tileManager = new TileManager(this.activity);
        //Init line drawer
        lineDrawer = new LineDrawer((ImageView) this.activity.findViewById(R.id.superCanvas));

        //Default Vars
        linkingMode = false;
        clickedTile_ID = "NULL";

        //Hide two menus
        hideTileSelectMenu();
        hideSwitchSelectMenu();

    }

    //=============================        Layer Controller        =================================

    private void hideTileSelectMenu(){
        this.activity.findViewById(R.id.Layer4_TileChooseBG).setVisibility(View.INVISIBLE);
        this.activity.findViewById(R.id.Layer5_TileChooseButtons).setVisibility(View.INVISIBLE);
    }

    private void showTileSelectMenu(){
        this.activity.findViewById(R.id.Layer4_TileChooseBG).setVisibility(View.VISIBLE);
        this.activity.findViewById(R.id.Layer5_TileChooseButtons).setVisibility(View.VISIBLE);
    }

    private void hideSwitchSelectMenu(){
        this.activity.findViewById(R.id.Layer6_SwitchChooseBG).setVisibility(View.INVISIBLE);
        this.activity.findViewById(R.id.Layer7_SwitchChooseButtons).setVisibility(View.INVISIBLE);
    }

    private void showSwitchSelectMenu(){
        this.activity.findViewById(R.id.Layer6_SwitchChooseBG).setVisibility(View.VISIBLE);
        this.activity.findViewById(R.id.Layer7_SwitchChooseButtons).setVisibility(View.VISIBLE);
    }

    //============================= ====        Handlers        ====================================

    /*========================  tileClick  =======================
     When a game tile is click, this function will be called.
     The tileID will be passed in.

     Logic:
     If not linking mode (One tile is clicked)
            switch is click -> open switch menu
            tile is click -> open tile menu
            light is click -> alter light mode

     If it's linking mode (Connecting two tiles)
            switch is click -> deny (Nothing should connect to it)
            tile is click -> check Y coordinates if not in same level, connect.
            light is click -> change light's input and connect directly

     Calculate Graphics

     =============================================================*/
    public void tileClick(String tileID) {

        Tile tile = tileManager.findTileById(tileID);

        if (this.linkingMode == false) {

            Log.d("INFO", "Tile Clicked :: Normal Mode");

            this.clickedTile_ID = tileID;
            if (tile.getType().equals("switch")){
                showSwitchSelectMenu();
            } else if (tile.getType().equals("tile")) {
                showTileSelectMenu();
            } else {
                //Alternate light ON and OFF
                Tile light = tileManager.findTileById(tileID);
                if (light.getValue().equals("ON+") || light.getValue().equals("ON-")) {
                    light.setValue("OFF-");
                } else {
                    light.setValue("ON-");
                }
            }
        } else {
            Log.d("INFO", "Tile Clicked :: Linking Mode");
            if (tile.getType().equals("switch")) {
                Log.d("INFO", "Switch Cannot be connected");
            } else if (tile.getType().equals("tile")) {
                if (clickedTile_ID!="NULL") {

                    // tile2 is the previous tile.
                    // tile2.output -> tile
                    Tile tile2 = tileManager.findTileById(clickedTile_ID);
                    if (tile2.getType().equals("switch")) {

                        //If switch connect again to a tile it already connects, cut connection.
                        if (tile.getInput(0).equals(tile2.getID()) || tile.getInput(1).equals(tile2.getID())) {
                            tile.removeInput(tile2.getID());
                        } else {
                            tile.setInput(tile2.getID());
                        }
                    } else if (tile2.getType().equals("tile")) {

                        //If a tile connect to a tile has same or upper level then it, block it
                        if (tile2.getIndex().charAt(1) < tile.getIndex().charAt(1)) {
                            linkTile(tile2.getID(), tile.getID());
                        } else {
                            Log.d("ERROR:","A gate can't output to a same or upper level tile.");
                        }
                    }
                }
            } else if (tile.getType().equals("light")) {
                if (!tile.getInput(0).equals("NULL")) {
                    disconnectTile(tile.getInput(0));
                }
                tile.setInputLight("NULL");
                Tile tile2 = tileManager.findTileById(clickedTile_ID);
                linkTile(tile2.getID(),tile.getID());
            }
            this.linkingMode = false;
            this.clickedTile_ID = "NULL";
        }

        renderGraphics();
    }

    /*========================  tileSelect  =======================
     When a game tile selection is made, this function is called.
     A choice will be send in as argument.
     choice -> {"CONN", "RESET", "AND", "OR", "NOT", "NAND", "NOR", "XOR", "XNOR", "NULL"}

     Logic:
     If request is "CONN" (connect)
        Check if current tile has connect to some tile
            If so -> remove connection
            If not -> enter LINK MODE, wait for next tile click
     If request is "RESET"
            find current tile, set it to NULL (display as empty gate)

     If request is all other gates
            Change tile to the gate it wants

     After all
           Hide menu
           Render Graphics

     =============================================================*/
    public void tileSelect(String choice){
        if (choice.equals("CONN")) {
            if (!tileManager.findTileById(this.clickedTile_ID).getOutput().equals("NULL")) {
                //If it has connection, disconnect.
                disconnectTile(this.clickedTile_ID);
                this.clickedTile_ID = "";
            } else {
                //If it doesn't, enable link mode, wait for next tile click
                this.linkingMode = true;
            }
        } else if (choice.equals("RESET")) {
            //Apply gate selections -> "NULL"
            tileManager.findTileById(this.clickedTile_ID).setValue("NULL");
            this.clickedTile_ID = "";
        } else {
            //Apply gate selections -> "AND", "OR", "NOT", "NAND", "NOR", "XOR", "XNOR", "NULL"
            tileManager.findTileById(this.clickedTile_ID).setValue(choice);
            this.clickedTile_ID = "";
        }

        hideTileSelectMenu();
        renderGraphics();
    }

    /*========================  switchSelect  =======================
     When a switch selection is clicked from menu, this function is called
     A choice will be send in as argument.
     choice -> {"CONN", "ON", "OFF"}

     Logic:
     If request is "CONN" (connect)
        enter LINK MODE, wait for next tile click (The switch wants to connect to a tile)

     If request is "ON", "OFF"
        set up tile's value to ON and OFF

     After all
           Hide menu
           Render Graphics

     =============================================================*/

    public void switchSelect(String choice){
        if (choice.equals("CONN")) {
            this.linkingMode = true;
        } else {
            tileManager.findTileById(this.clickedTile_ID).setValue(choice);
            this.clickedTile_ID = "";
        }

        hideSwitchSelectMenu();
        renderGraphics();
    }

    //================================        Node Control        ==================================

    /*========================  linkTile  =======================
    This functions links two tiles together.
    tile1.output -> tile2

    Arguments:
    String tileID1 : ex."tile_32"
    String tileID2 : ex. "switch_1"

    Logic:

    Get two tiles by their ID.
    Connects them

    =============================================================*/
    private void linkTile(String tileID1, String tileID2) {
        Tile tile1 = tileManager.findTileById(tileID1);
        Tile tile2 = tileManager.findTileById(tileID2);

        tile1.setOutput(tileID2);
        tile2.setInput(tileID1);

        Log.d("DEBUG",tile1.getID() + ".output = " + tile1.getOutput());
        Log.d("DEBUG",tile2.getID() + ".input = " + tile2.getInput(0));
        Log.d("DEBUG",tile2.getID() + ".input = " + tile2.getInput(1));
    }




    /*========================  disconnectTile  =======================
    This functions cleans a tile's output
    tile1.output --X--> tile2
    tile2.input --X--> tile1

    Arguments:
    String tileID1 : ex."tile_32"

    Logic:

    Get two tiles by their ID.
    Erase Connection

    =============================================================*/
    private void disconnectTile(String tileID1) {
        Tile tile1 = tileManager.findTileById(tileID1);

        if (!tile1.getOutput().equals("NULL")){
            Tile tile2 = tileManager.findTileById(tile1.getOutput());
            tile2.removeInput(tile1.getID());
            tile1.setOutput("NULL");

            Log.d("DEBUG",tile1.getID() + ".output = " + tile1.getOutput());
            Log.d("DEBUG",tile2.getID() + ".input = " + tile2.getInput(0));
            Log.d("DEBUG",tile2.getID() + ".input = " + tile2.getInput(1));
        } else {
            Log.d("DEBUG",tile1.getID() + ".output = " + tile1.getOutput());
        }

    }


    //==================================        Graphics        ====================================


    /*========================  renderGraphics  =======================
    When graphic is need, this function kicks off.

    Logic:

    Evaluate all Tiles, assign boolean result for each tile "ON", "OFF" "NULL"

    Get tiles array from Engine
       Traverse all tiles
           if it is light, determine it is ON or OFF
           draw lines between a tile and its input tiles.
                Color that line
           render all Graphics

    =============================================================*/
    public void renderGraphics() {
        lineDrawer.newPaint();

        evalEachTiles();

        Tile tiles[] = tileManager.getAllTiles();
        for (int i=0;i<tiles.length;i++) {
            for (int j=0;j<2;j++) {
                if (!tiles[i].getInput(j).equals("NULL")) {
                    Tile srcTile = tiles[i];
                    Tile tgtTile = tileManager.findTileById(tiles[i].getInput(j));

                    int srcXY[] = srcTile.getTopXY();
                    int tgtXY[] = tgtTile.getBottomXY();

                    int drawColor;
                    boolean isLight = srcTile.getType().equals("light");
                    if (tgtTile.getEvalValue().equals("NULL")) {
                        drawColor = Color.WHITE;
                        if (isLight) {
                            if (srcTile.getValue().startsWith("ON")) {
                                srcTile.setValue("ON-");
                            } else {
                                srcTile.setValue("OFF-");
                            }
                        }
                    } else if (tgtTile.getEvalValue().equals("ON")) {
                        drawColor = Color.argb(255,152,120,219);
                        if (isLight) {
                            if (srcTile.getValue().startsWith("ON")) {
                                srcTile.setValue("ON+");
                            } else {
                                srcTile.setValue("OFF-");
                            }
                        }
                    } else {
                        drawColor = Color.argb(255,58,219,134);
                        if (isLight) {
                            if (srcTile.getValue().startsWith("ON")) {
                                srcTile.setValue("ON-");
                            } else {
                                srcTile.setValue("OFF+");
                            }
                        }
                    }

                    lineDrawer.drawLine(srcXY[0],srcXY[1],tgtXY[0],tgtXY[1], drawColor);
                }
            }
        }

        lineDrawer.renderLines();
    }

    //=================================        Evaluation        ===================================

    /*========================  evalEachTiles()  =======================
    When evaluation of each tile is needed, this function kicks off.

    Logic:

    Get tiles array from Engine
       Traverse all tiles
           if it is a switch, its type is the value "ON" "OFF"
           if it is a tile
                It has 2 inputs:
                    grab two evaluated value of the inputs, eval the value of this tile.
                It has 1 input:
                    if it is NOT gate
                        eval it with one input
                Other
                    bad information, can't evaluate.
           if it is a light
                DO NOTHING

    ====================================================================*/

    public void evalEachTiles() {
        Tile tiles[] = tileManager.getAllTiles();

        for (int i=0;i<tiles.length;i++) {
            Tile currentTile = tiles[i];

            if (currentTile.getType().equals("switch")) {
                currentTile.setEvalValue(currentTile.getValue());
            } else if (currentTile.getType().equals("tile")) {
                currentTile.setEvalValue("NULL");
                if (!currentTile.getValue().equals("NULL")) {
                    if ((!currentTile.getInput(0).equals("NULL")) && (!currentTile.getInput(1).equals("NULL")) && (!currentTile.getValue().equals("NOT"))) {
                        Tile parentA = tileManager.findTileById(tiles[i].getInput(0));
                        Tile parentB = tileManager.findTileById(tiles[i].getInput(1));
                        if ((!parentA.getEvalValue().equals("NULL")) && (!parentA.getEvalValue().equals("NULL"))) {
                            String evaluated = evalTiles(parentA.getEvalValue(), parentB.getEvalValue(), currentTile.getValue());
                            currentTile.setEvalValue(evaluated);
                        }
                    } else if ((!currentTile.getInput(0).equals("NULL")) && (currentTile.getInput(1).equals("NULL"))) {
                        if (currentTile.getValue().equals("NOT")) {
                            Tile parentA = tileManager.findTileById(tiles[i].getInput(0));
                            if (!parentA.getEvalValue().equals("NULL"))  {
                                String evaluated = evalTiles(parentA.getEvalValue(), "OFF", currentTile.getValue());
                                currentTile.setEvalValue(evaluated);
                            }
                        }
                    } else if ((currentTile.getInput(0).equals("NULL")) && (!currentTile.getInput(1).equals("NULL"))) {
                        if (currentTile.getValue().equals("NOT")) {
                            Tile parentB = tileManager.findTileById(tiles[i].getInput(1));
                            if (!parentB.getEvalValue().equals("NULL"))  {
                                String evaluated = evalTiles(parentB.getEvalValue(), "OFF", currentTile.getValue());
                                currentTile.setEvalValue(evaluated);
                            }
                        }
                    }
                }
            }
        }
    }

    /*========================  evalEachTiles()  =======================
    To evaluate two inputs and one operator
    ex. A = "ON" B = "OFF" Operator = "AND" -> eval to "OFF"
        ON AND OFF = OFF

    Logic:

    Convert A & B from ON, OFF to 1,0
    Call GATE FUNCTIONS to evaluate

    if anything is not right, return "NULL" (When BUGGED OUT)

    ====================================================================*/
    public String evalTiles(String A, String B, String Operator){
        int numA = 0, numB = 0, numC = 0;
        boolean result = false;
        String resultStr;

        boolean buggedOut=false;
        switch (A) {
            case "ON":
                numA = 1;
                break;
            case "OFF":
                numA = 0;
                break;
            default:
                Log.d("ERROR:","Invalid tile evaluateValue: " + A);
                buggedOut = true;
                break;
        }
        switch (B) {
            case "ON":
                numB = 1;
                break;
            case "OFF":
                numB = 0;
                break;
            default:
                Log.d("ERROR:","Invalid tile evaluateValue: " + B);
                buggedOut = true;
                break;
        }

        if (!buggedOut) {
            switch (Operator){
                case "AND":
                    numC = AND(numA,numB);
                    break;
                case "OR":
                    numC = OR(numA,numB);
                    break;
                case "NOT":
                    numC = NOT(numA);
                    break;
                case "NAND":
                    numC = NAND(numA,numB);
                    break;
                case "NOR":
                    numC = NOR(numA,numB);
                    break;
                case "XOR":
                    numC = XOR(numA,numB);
                    break;
                case "XNOR":
                    numC = XNOR(numA,numB);
                    break;
                default:
                    Log.d("ERROR:","Unexpected Gate:" + Operator);
                    buggedOut = true;
                    break;
            }
            result = getBoolean(numC);
        }

        if (buggedOut) {
            resultStr = "NULL";
        } else if (result) {
            resultStr = "ON";
        } else {
            resultStr = "OFF";
        }

        return resultStr;
    }

    public int AND(int A, int B)
    {
        if((A == -1) || (B == -1)) {
            return -1;
        }else{
            boolean boolA = getBoolean(A);
            boolean boolB = getBoolean(B);

            if(boolA && boolB) {
                return 1;
            }else {
                return 0;
            }
        }

    }
    public int OR(int A, int B)
    {
        if((A == -1) || (B == -1)) {
            return -1;
        }else {
            boolean boolA = getBoolean(A);
            boolean boolB = getBoolean(B);

            if (boolA || boolB) {
                return 1;
            }else {
                return 0;
            }
        }
    }

    public int NOT(int A)
    {
        int result = -1;
        if(A == -1) {
            return -1;
        }else {
            switch(A) {
                case 0: result = 1;
                case 1: result = 0;
            }
            return result;
        }
    }

    public int NAND(int A, int B)
    {
        int value = AND(A, B);
        switch(value) {
            case 0: return 1;
            case 1: return 0;
            default: return -1;
        }
    }
    public int NOR(int A, int B)
    {
        int value = OR(A, B);
        switch(value) {
            case 0: return 1;
            case 1: return 0;
            default: return -1;
        }
    }
    public int XOR(int A, int B)
    {
        if(A == -1 || B == -1) {
            return -1;
        } else if(A == B) {
            return 0;
        }else {
            return 1;
        }
    }
    public int XNOR(int A, int B)
    {
        int xorValue = XOR(A, B);
        switch(xorValue) {
            case 0: return 1;
            case 1: return 0;
            default: return -1;
        }
    }

    private boolean getBoolean(int number) {
        boolean result;
        switch(number) {
            case 0: result = false; break;
            case 1: result = true; break;
            default: result = false; break;
        }

        return result;
    }
}
