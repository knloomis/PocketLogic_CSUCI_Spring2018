package com.pocketlogic.pocketlogic;

import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by vulpi on 4/1/2018.
 */


public class gate extends Tile {
    //private int type;
    private Tile inputA;
    private Tile inputB;
    //private ArrayList<Tile> outputTiles = new ArrayList<Tile>();
    //private int[] drawables = new int[] {R.drawable.hexagon, R.drawable.and, R.drawable.or, R.drawable.not, R.drawable.switch_0, R.drawable.nor, R.drawable.xor, R.drawable.xnor};

//    X private boolean value;

//    X private int[] gateDrawables = {R.drawable.hexagon, R.drawable.and, R.drawable.or, R.drawable.not, R.drawable.switch_0, R.drawable.nor, R.drawable.xor, R.drawable.xnor};
//    X private int[] switchDrawables = {};
//    X private int[] lightDrawables = {};

    public gate() {


        drawables = new int[] {R.drawable.hexagon, R.drawable.and, R.drawable.or, R.drawable.not, R.drawable.nand, R.drawable.nor, R.drawable.xor, R.drawable.xnor};
    }

    public gate(int type)
    {
        if (type >= 0 && type <= 7){
            this.type = type;
        }else{
            this.type = 0;
        }

        drawables = new int[] {R.drawable.hexagon, R.drawable.and, R.drawable.or, R.drawable.not, R.drawable.switch_0, R.drawable.nor, R.drawable.xor, R.drawable.xnor};

    }

    public int getNextImage() {
        getNext();
        return drawables[type];
    }

    public int getNext()
    {
        if (this.type < 7) this.type++;
        else this.type = 0;

        return this.type;
    }

    public int getImage(){
        return drawables[type];
    }

    public String getTypeString()
    {
        //for debug
        switch (this.type)
        {
//            case -2 : return "LIGHT";
//            case -1 : return "SWITCH";
            case  0 : return "BLANK";
            case  1 : return "AND";
            case  2 : return "OR";
            case  3 : return "NOT";
            case  4 : return "NAND";
            case  5 : return "NOR";
            case  6 : return "XOR";
            case  7 : return "XNOR";
            default : return "INVALID";
        }
    }

    public void setInputs(Tile inputA, Tile inputB)
    {
        this.inputA = inputA;
        this.inputB = inputB;
    }

    public int eval()
    {
        //something in here to account for if inputA/B is null
        if(inputA == null || inputB == null) {
            return -1;
        }

        switch (this.type)
        {
            // case -1 : return this.value;
            case  1 : return AND (this.inputA.eval(), this.inputB.eval());
            case  2 : return OR (this.inputA.eval(), this.inputB.eval());
            case  3 : return NOT (this.inputA.eval());
            case  4 : return NAND (this.inputA.eval(), this.inputB.eval());
            case  5 : return NOR (this.inputA.eval(), this.inputB.eval());
            case  6 : return XNOR (this.inputA.eval(), this.inputB.eval());
            case  7 : return XNOR (this.inputA.eval(), this.inputB.eval());
            default : return -1;
        }
    }

    public boolean changeInputConnection(Tile inputTile){
        if(inputTile == this || this.type == 0){
            return false;
        }else if(inputTile instanceof gate) {
            if(inputTile.type == 0){
                return false;
            }


        }
 /*       if(this.type == 0){
            //want remove all output connections of parameter, right?
            inputTile.clearOutputConnections();
        }
        */

        boolean addedAsInput = false;
        //idea:
        //1. if highlight self, then touch self, do nothing; OR if are an empty block, do nothing
        //2. if clicked second and first guy was already one of the inputs, delete that connection
        //3. else if is not one of connections and there's an empty connection, set as that connection

        if(inputTile == inputA || inputTile == inputB){
            if(inputTile == inputA){
                inputA = null;
                inputTile.removeOutput(this);
            }else{
                inputB = null;
                inputTile.removeOutput(this);
            }

        }else{
            if(inputA == null){
                inputA = inputTile;
                inputA.addOutput(this);
                addedAsInput = true;
            }else if(inputB == null){
                inputB = inputTile;
                inputB.addOutput(this);
                addedAsInput = true;
            }
        }
        return addedAsInput;
    }

    public void clearOutputConnections(){
        for(Tile currOutput : outputTiles){
            currOutput.clearInputConnection(this);
        }
    }

    public void clearInputConnection(Tile inputToRemove){
        if(inputA == inputToRemove){
            inputA = null;
        }else if(inputB == inputToRemove){
            inputB = null;
        }
    }

    /*
    public void changeOutputConnection(Tile outputTile){
        if(outputTile == this){
            return;
        }
        if(outputTile.getType() == 0){
            for(Tile currOutputTile : outputTiles){
                currOutputTile.changeInputConnection(this);
            }
        }else{
            outputTiles.add(outputTile);
        }
    }
    */

    public void addOutput(Tile newOutput){
        outputTiles.add(newOutput);
    }

    public void removeOutput(Tile tileToRemove){
        outputTiles.remove(tileToRemove);
    }

    public int getCellRowNum(){
        if(positionNum >= 32){
            return 7;
        }else if(positionNum >= 27){
            return 6;
        }else if(positionNum >= 23){
            return 5;
        }else if(positionNum >= 18){
            return 4;
        }else if(positionNum >= 14){
            return 3;
        }else if(positionNum >= 9){
            return 2;
        }else if(positionNum >= 5){
            return 1;
        }else if(positionNum >= 0){
            return 0;
        }
        return 0;
    }

    public int getX(){

        switch(this.getCellColNum()){
            case 0: return 250;
            case 1: return 525;
            case 2: return 800;
            case 3: return 1075;
            case 4: return 1350;
            case 5: return 1625;
            case 6: return 1900;
            case 7: return 2175;
            case 8: return 2450;
            default: return 0;
        }
    }

    public int getY(){
        switch(this.getCellRowNum()){
            case 0: return 200;
            case 1: return 320;
            case 2: return 450;
            case 3: return 580;
            case 4: return 720;
            case 5: return 860;
            case 6: return 1000;
            case 7: return 1125;
            default: return 0;
        }
    }

    public int getCellColNum(){
 /*       if((positionNum % 9) == 0){
            return 0;
        }else if(((positionNum - 1) % 9) == 0){
            return 1;
        }else if(((positionNum - 2) % 9) == 0){
            return 2;
        }else if(((positionNum - 3) % 9) == 0){
            return 3;
        }else if(((positionNum - 4) % 9) == 0){
            return 4;
        }else if(((positionNum - 5) % 9) == 0){
            return 5;
        }else if(((positionNum - 6) % 9) == 0){
            return 6;
        }else if(((positionNum - 7) % 9) == 0){
            return 7;
        }else if(((positionNum - 8) % 9) == 0){
            return 8;
        }
        */
        if (positionNum == 0 || positionNum == 9 || positionNum == 18 || positionNum == 27) {
            return 0;
        } else if (positionNum == 1 || positionNum == 10 || positionNum == 19 || positionNum == 28) {
            return 2;
        } else if (positionNum == 2 || positionNum == 11 || positionNum == 20 || positionNum == 29) {
            return 4;
        } else if (positionNum == 3 || positionNum == 12 || positionNum == 21 || positionNum == 30) {
            return 6;
        } else if (positionNum == 4 || positionNum == 13 || positionNum == 22 || positionNum == 31) {
            return 8;
        } else if (positionNum == 5 || positionNum == 14 || positionNum == 23 || positionNum == 32) {
            return 1;
        } else if (positionNum == 6 || positionNum == 15 || positionNum == 24 || positionNum == 33) {
            return 3;
        } else if (positionNum == 7 || positionNum == 16 || positionNum == 25 || positionNum == 34) {
            return 5;
        } else if (positionNum == 8 || positionNum == 17 || positionNum == 26 || positionNum == 35) {
            return 7;
        }
        return 0;
    }

    public String getClassType(){
        return "Gate";
    }

}
