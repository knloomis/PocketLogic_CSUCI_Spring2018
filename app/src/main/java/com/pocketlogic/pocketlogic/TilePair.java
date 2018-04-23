package com.pocketlogic.pocketlogic;

/**
 * Created by vulpi on 4/18/2018.
 */

public class TilePair {
    private Tile inputTile;
    private Tile outputTile;

    public TilePair(Tile input, Tile output){
        inputTile = input;
        outputTile = output;
    }

    public Tile getInputTile(){
        return inputTile;
    }

    public Tile getOutputTile(){
        return outputTile;
    }

    public boolean pairMatches(Tile first, Tile second){
        return(first == inputTile && second == outputTile);
    }

    public boolean hasTile(Tile tile){
        return (inputTile == tile || outputTile == tile);
    }
}
