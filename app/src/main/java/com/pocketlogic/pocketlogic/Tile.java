package com.pocketlogic.pocketlogic;

import java.util.ArrayList;

public class Tile
{
    protected int[] drawables;
    protected int type;
    protected ArrayList<Tile> outputTiles = new ArrayList<Tile>();
    protected int positionNum;

    public void setPositionNum(int num){
        this.positionNum = num;
    }

    public int getPositionNum(){
        return this.positionNum;
    }

    public int getNext() {

        return 0;
    }

    public int getNextImage() {
        //getNext();
        return R.drawable.hexagon;

    }

    public int getImage(){
        return R.drawable.hexagon;
    }

    public void addOutput(Tile newOutput){
        //outputTiles.add(newOutput);
        ;
    }

    public ArrayList<Tile> getOutputs(){
        //return outputTiles;
        return null;
    }

    public void removeOutput(Tile tileToRemove){
        ;
    }

    public int getType(){
        return this.type;
    }

    public int eval() {
        return -1;
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

    public boolean changeInputConnection(Tile inputTile){
        return false;
    }

    public void changeOutputConnection(Tile outputTile){
        ;
    }

    public void clearOutputConnections(){ ; }
    public void clearInputConnection(Tile inputToRemove){ ; }

    public int getX(){
        return 0;
    }

    public int getY(){
        return 0;
    }

    public String getClassType(){
        return "Tile";
    }

}