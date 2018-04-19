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
        return (this.type == eval());
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

}