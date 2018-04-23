package com.pocketlogic.pocketlogic;

import java.util.ArrayList;

/**
 * Created by vulpi on 4/5/2018.
 */


public class Output extends Tile {
    //private int value;
    private Tile input;

    //   private Tile inputB;

    public Output(int value) {
        drawables = new int[] {R.drawable.lightbulb_0_unlit, R.drawable.lightbulb_0_lit, R.drawable.lightbulb_1_unlit, R.drawable.lightbulb_1_lit};

        this.type = value;
    }

    public void setValue(int value) {
        this.type = value;
    }

    public int getImage() {
        int imageNum = drawables[0];
        //int trueValue = eval();

        boolean valuesMatch = currentValueMatches();
       // boolean valuesMatch = false;

        if(valuesMatch) {
            switch(this.type) {
                case 0: imageNum = drawables[1]; break;
                case 1: imageNum = drawables[3]; break;
                default: break;
            }
        }else {
            switch(this.type) {
                case 0: imageNum = drawables[0]; break;
                case 1: imageNum = drawables[2]; break;
            }
        }

        return imageNum;
    }

    public boolean currentValueMatches() {
        return (this.type == this.eval());
    }

    public int eval()
    {
        //something in here to account for if inputA/B is null
        if(input == null) {
            return -1;
        }else {
            return input.eval();
        }

    }

    public void addOutput(Tile newOutput){
        ;
    }

    public ArrayList<Tile> getOutputs(){
        return null;
    }

    public void clearOutputConnections(){
        for(Tile currOutput : outputTiles){
            currOutput.clearInputConnection(this);
        }
    }

    public boolean changeInputConnection(Tile inputTile){
        if(inputTile == this){
            return false;
        }else if(inputTile instanceof gate) {
            if(inputTile.type == 0){
                return false;
            }
        }

        boolean addedAsInput = false;
        //idea:
        //1. if highlight self, then touch self, do nothing; OR if are an empty block, do nothing
        //2. if clicked second and first guy was already one of the inputs, delete that connection
        //3. else if is not one of connections and there's an empty connection, set as that connection

        if(inputTile == input){
            input = null;
            inputTile.removeOutput(this);

        }else{
            if(input == null){
                input = inputTile;
                input.addOutput(this);
                addedAsInput = true;
            }
        }
        return addedAsInput;
    }

    public int getX(){
        return 1600;
    }

    public int getY(){
        return 1125;
    }

    public String getClassType(){
        return "Output";
    }

}