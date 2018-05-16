package com.pocketlogic.pocketlogic.PLEngine;

import android.util.Log;
import android.widget.ImageView;
import com.pocketlogic.pocketlogic.R;

/*======================================   USER MANUAL    ==========================================
Usage: store all information for one certain tile.
A tile could be a switch, a gate, a light.

Declaration
Tile tile = new Tile((ImageView)viewOfTile)

Available Functions

void setXY(int[] originXY);         <- Takes origin XY and calculate top and bottom vertex XY
void setValue(String value)                    <- Set values of tile to "AND", "OR", "ON", "OFF"
void setInput(String targetTileID)                    <- Set inputs. 2 slot to storage, auto alternate
void setInputLight(String targetTileID)               <- Set input for light, 1 slot to storage.
void setOutput(String targetTileID)                   <- Set output for this tile.
void removeInput(String targetTileID)                 <- Remove matched input
void setRedacted(Boolean disable)                     <- to disable certain tile

String getID()                                        <- Get a string ID like "tile_42", "switch_3"
String getType()                                      <- Get a type like "tile", "switch", "light"
String getIndex()                                     <- Get a index like "42", "3"
String getValue()                                     <- Get a value like "ON", "OFF", "AND", "NOR"
ImageView getView()                                   <- Get the imageView of the tile
int[] getOriginXY()                                    <- Get the center XY of the tile
int[] getTopXY()                                      <- Get top XY of the tile
int[] getBottomXY()                                   <- Get bottom XY of the tile
String getInput(int inputIndex)                       <- Get input 0 or input 1 's ID ex. "tile_12"
String getOutput()                                    <- Get output ID ex."light_1"

*/

public class Tile {

    //===============================       PRIVATE MEMBERS      ===================================

    // Tile content
    private String id;         //Tile name ex. "switch_2", "tile_54", "light_1"
    private String type;       //Tile type ex. "switch", "tile", "light"
    private String index;      //For swtichs: {"1","2","3","4"}
                               //For gates XY: {"11","12","13","14","21","22"..."51","52","53","54"}
                               //For light: "1"
    private String value;      //For switchs: {"ON", "OFF"}
                               //For gates: {"AND", "OR", "NOT", "NAND", "NOR", "XOR", "XNOR", "NULL"}
                               //For light: "ON+","ON-", "OFF+", "OFF-"
    private boolean redacted;  //If certain tile is disabled; true -> enabled; false-> disabled;
    private String evalValue;  //This is the value of the current tile.  {"ON","OFF","NULL"}
    private int inputPtr;   //0 to modify the first input, 1 to modify the second input;
    private ImageView tileIMG; //ImageView of the tile

    //========================
    // Coordination
    private int[] originXY;     //XY of the center of the tile
    private int[] topXY;       //XY of the top vertex (inputs) of the tile
    private int[] bottomXY;    //XY of the bottom vertex (ouput) of the tile

    //========================
    // Linking
    private String[] input;        //Tile name ex."switch_2", "tile_54", "NULL"
    private String output;         //Tile name ex."tile_54", "light_1", "NULL"


    //=====================================    CONSTRUCTOR    ======================================

    public Tile(ImageView tileIMG){
        this.id = tileIMG.getTag().toString();                             //ex. tile_43
        this.type = tileIMG.getTag().toString().split("_")[0];     //ex. tile
        this.index = tileIMG.getTag().toString().split("_")[1];    //ex. 43

        if (this.type.equals("switch")) {
            this.value = "OFF";
        } else if (this.type.equals("tile")) {
            this.value = "NULL";
        } else if (this.type.equals("light")) {
            this.value = "ON-";
        } else {
            Log.d("ERROR:","Tile::Constructor BUGGED OUT! UNKNOWN TYPE:" + this.value);
        }

        this.tileIMG = tileIMG;
        this.originXY = new int [2];
        this.topXY = new int[2];
        this.bottomXY = new int [2];
        this.input = new String[2];
        this.originXY[0] = -1;
        this.originXY[1] = -1;
        this.topXY[0] = -1;
        this.topXY[1] = -1;
        this.bottomXY[0] = -1;
        this.bottomXY[1] = -1;

        input[0] = "NULL";
        input[1] = "NULL";
        output = "NULL";
        evalValue = "NULL";
        inputPtr=0;

        this.redacted = false;

        updateICON();
    }

    //=====================================      SETTERS      ======================================

    //===================================
    // This function receives a origin XY to calculate Top and Bottom XY.
    // XY[0] = X; XY[1] = Y
    public void setXY(int[] originXY){

        final int Y_OFFSET = 160;                       //offset pixels

        this.originXY[0] = originXY[0];
        this.originXY[1] = originXY[1];
        this.topXY[0] = this.originXY[0];
        this.topXY[1] = this.originXY[1] - Y_OFFSET;
        this.bottomXY[0] = this.originXY[0];
        this.bottomXY[1] = this.originXY[1] + Y_OFFSET;
    }

    //===================================
    // Use this method to configure the value of a tile.
    public void setValue(String value){
        this.value = value;
        updateICON();
    }

    //===================================
    // Use this method to configure the evaluated value of a tile.
    public void setEvalValue(String value){
        this.evalValue = value;
    }

    //===================================
    // A gate has two possible input,
    // Setup input by setInput("tile_11");
    //It will alternates between two inputs to save.
    public void setInput(String targetTileID){
        input[inputPtr] = targetTileID;
        inputPtr++;
        inputPtr %= 2;
    }

    //===================================
    // A light only has one input
    public void setInputLight(String targetTileID){
        input[0] = targetTileID;
        input[1] = "NULL";
        inputPtr=0;
    }

    //===================================
    // A gate has two possible input,
    // removeInput requires input's ID
    // It will reset input which matches the id
    public void removeInput (String targetTileID){
        if (input[0].equals(targetTileID)){
            input[0] = "NULL";
        }
        if (input[1].equals(targetTileID)){
            input[1] = "NULL";
        }
    }

    //===================================
    // A gate has one possible output
    // $ tile.setOutput("light_1");
    public void setOutput(String targetTileID){
        this.output = targetTileID;
    }

    //===================================
    //To disable a tile
    public void setRedacted( boolean disable){
        this.redacted = disable;
        this.updateICON();
    }

    //===================================      GETTERS      ========================================
    public boolean isRedacted(){
        return this.redacted;
    }

    public String getID(){
        return this.id;
    }
    public String getType(){
        return this.type;
    }
    public String getIndex(){
        return this.index;
    }
    public String getValue(){
        return this.value;
    }
    public ImageView getView(){
        return this.tileIMG;
    }
    public int[] getOriginXY(){
        return this.originXY;
    }
    public int[] getTopXY(){
        return this.topXY;
    }
    public int[] getBottomXY(){
        return this.bottomXY;
    }
    public String getInput(int inputIndex){
        if (inputIndex < 2 && inputIndex > -1) {
            return this.input[inputIndex];
        } else {
            Log.d("ERROR:","Tile::getInput inputIndex must be 0 or 1");
            return "NULL";
        }
    }
    public String getOutput(){
        return output;
    }
    public String getEvalValue(){
        return evalValue;
    }

    //==================================   INTERNAL FUNCTIONS   ====================================

    // setValue and constructer will call this function
    private void updateICON(){

        //========================================================
        // Setup image for switch
        //For switchs: {"ON", "OFF"}
        if (this.type.equals("switch")) {
            if (this.redacted == true){
                this.tileIMG.setImageResource(R.drawable.switch_2);
            } else if (this.value.equals("ON")) {
                this.tileIMG.setImageResource(R.drawable.switch_1);
            } else if (this.value.equals("OFF")) {
                this.tileIMG.setImageResource(R.drawable.switch_0);
            } else {
                Log.d("ERROR:","Unknown value for switch type:" + this.value);
            }
        }

        //========================================================
        // Setup image for tile
        //For gates: {"AND", "OR", "NOT", "NAND", "NOR", "XOR", "XNOR", "NULL"}
        else if (this.type.equals("tile")) {
            if (this.redacted){
                this.tileIMG.setImageResource(R.drawable.locked_grid);
            } else {
                if (this.value.equals("NULL")) {
                    this.tileIMG.setImageResource(R.drawable.empty_grid_space);
                } else if (this.value.equals("AND")) {
                    this.tileIMG.setImageResource(R.drawable.and);
                } else if (this.value.equals("OR")) {
                    this.tileIMG.setImageResource(R.drawable.or);
                } else if (this.value.equals("NOT")) {
                    this.tileIMG.setImageResource(R.drawable.not);
                } else if (this.value.equals("NAND")) {
                    this.tileIMG.setImageResource(R.drawable.nand);
                } else if (this.value.equals("NOR")) {
                    this.tileIMG.setImageResource(R.drawable.nor);
                } else if (this.value.equals("XOR")) {
                    this.tileIMG.setImageResource(R.drawable.xor);
                } else if (this.value.equals("XNOR")) {
                    this.tileIMG.setImageResource(R.drawable.xnor);
                } else if (this.value.equals("NULL")) {
                    this.tileIMG.setImageResource(R.drawable.empty_grid_space);
                } else {
                    Log.d("ERROR:","Unknown value for tile type:" + this.value);
                }
            }
        }

        //========================================================
        // Setup image for light
        else if (this.type.equals("light")) {
            //For light "ON+","ON-", "OFF+", "OFF-"
            if (this.value.equals("ON+")) {
                this.tileIMG.setImageResource(R.drawable.lightbulb_1_lit);
            } else if (this.value.equals("ON-")) {
                this.tileIMG.setImageResource(R.drawable.lightbulb_1_unlit);
            } else if (this.value.equals("OFF+")) {
                this.tileIMG.setImageResource(R.drawable.lightbulb_0_lit);
            } else if (this.value.equals("OFF-")) {
                this.tileIMG.setImageResource(R.drawable.lightbulb_0_unlit);
            } else {
                Log.d("ERROR:","Unknown value for tile type:" + this.value);
            }
        }
    }

    /*===================================  EXAMPLE CODE  ===========================================
        Tile tile = new Tile((ImageView)tileImgView);

        Log.d("getID",tile.getID());
        Log.d("getType()",tile.getType());
        Log.d("getIndex()",tile.getIndex());
        Log.d("getValue()",tile.getValue());
        Log.d("getView()",tile.getView().getTag().toString());
        Log.d("getInput()",tile.getInput(0) + ", " + tile.getInput(1));
        Log.d("getOutput()",tile.getOutput());

        int XY[]= {100,100};
        tile.setXY(XY);
        Log.d("getOriginXY()",Integer.toString(tile.getOriginXY()[0]) + "," + Integer.toString(tile.getOriginXY()[1]));
        Log.d("getTopXY()",Integer.toString(tile.getTopXY()[0]) + "," + Integer.toString(tile.getTopXY()[1]));
        Log.d("getBottomXY()",Integer.toString(tile.getBottomXY()[0]) + "," + Integer.toString(tile.getBottomXY()[1]));
        tile.setValue("NEW VAL");
        Log.d("getValue()",tile.getValue());
        tile.setInput(0,"input1");
        tile.setInput(1,"input2");
        Log.d("getInput()",tile.getInput(0) + ", " + tile.getInput(1));
        tile.setOutput("output");
        Log.d("getOutput()",tile.getOutput());
     */
}
