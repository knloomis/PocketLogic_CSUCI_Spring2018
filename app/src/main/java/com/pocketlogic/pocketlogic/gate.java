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



}
