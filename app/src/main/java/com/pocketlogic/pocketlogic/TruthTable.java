package com.pocketlogic.pocketlogic;

import android.view.View;

/**
 * Created by vulpi on 4/5/2018.
 */

public class TruthTable {
    int[] resultNums;
    int columns;
    int numInputs = 0;
    int currOutputValue = 0;
    int[] inputNums;
    int[] offDrawables = new int[]{R.drawable.off_unlit, R.drawable.off_lit};
    int[] onDrawables = new int[]{R.drawable.on_unlit, R.drawable.on_lit};
    int numBitValues = 2;
    int image;

    // TO DO: fix everything lol

    public TruthTable(int numColumns){
        int numRows = (int) Math.pow(2, numColumns);
        columns = numColumns;

        resultNums = new int[numRows];

       // table = new int[numRows][numColumns];

        // to be replaced with actual initialization
    /*
        for(int i = 0; i < numRows; i++){
            for(int j = 0; j < columns; j++){
                table[i][j] = 0;
            }
        }
        */
        //initializeTableEntries();

    }

    public TruthTable(){
        resultNums = new int[] {0, 1, 1, 0, 1, 1, 0, 0, 0, 1, 1, 0, 1, 1, 0, 0};
        columns = 5;
        numInputs = 4;
        inputNums = new int[] {0, 0, 0, 0};
        currOutputValue = getOutput(0,0,0,0);
        if(currOutputValue == 0){
            image = R.drawable.off_unlit;
        }else{
            image = R.drawable.on_unlit;
        }
    }

    public void switchOutputValue(int bitSwitched){
        inputNums[bitSwitched] = (inputNums[bitSwitched] + 1) % numBitValues;
        currOutputValue = getOutput(inputNums[0], inputNums[1], inputNums[2], inputNums[3]);
        updateImage();

    }

    public int getOutput(int bitOne, int bitTwo, int bitThree, int bitFour){
        int rowNumber = getRowOfFourInputs(bitFour, bitThree, bitTwo, bitOne);
        return resultNums[rowNumber];
    }

    public int getRowOfFourInputs(int bitOne, int bitTwo, int bitThree, int bitFour){
        //int rowNumber = -1;
        String binaryString = "" + bitOne + "" + bitTwo + "" + bitThree + "" + bitFour;

        return Integer.parseInt(binaryString, 2);

    }

//    public void initializeTableEntries(){


  //  }

    public void setNumInputs(int numIn){
        numInputs = numIn;
        columns = numInputs + 1;
    }

    public void setInputVal(int num, int newVal){
        inputNums[num] = newVal;

    }

    public void updateImage(){
        switch (currOutputValue){
            case 0:
                image = R.drawable.off_unlit;
                break;
            case 1:
                image = R.drawable.on_unlit;
                break;
        }
    }

    public int getImageType(){
        return image;
    }

}
