package com.pocketlogic.pocketlogic;

import android.widget.ImageView;
import android.widget.Toast;

/**
 * Created by vulpi on 4/1/2018.
 */

public class gate {
    int type = 0;
    int[] drawables; /*= new int[]{R.drawable.hexagon, R.drawable.and, R.drawable.or, R.drawable.xor, R.drawable.nor, R.drawable.xnor, R.drawable.not};
    int[] switchDrawables = new int[]{R.drawable.switch_off, R.drawable.switch_on}; */
    int num_types; /*= 7;
    int num_switch_types = 2; */
    private gate[] inputs = new gate[2];
    private gate output = null;
    int currNumInputs = 0;

    public gate(boolean isGateTile){
        inputs[0] = null;
        inputs[1] = null;
        currNumInputs = 0;
        if(isGateTile == true){
            drawables = new int[]{R.drawable.hexagon, R.drawable.and, R.drawable.or, R.drawable.xor, R.drawable.nor, R.drawable.xnor, R.drawable.not};
            num_types = 7;
        }else{
            drawables = new int[]{R.drawable.switch_off, R.drawable.switch_on};
            num_types = 2;
        }

    }


    public int getImageType(){
        return drawables[type];
    }

    public void rotateImageType(){
        type = (type + 1) % num_types;
    }

    public int getType(){
        return type;
    }

    public gate getOutput(){
        return output;
    }

    public gate getFirstInput(){
        return inputs[0];
    }

    public gate getSecondInput(){
        return inputs[1];
    }

    public void addInput(gate newGate){
        if(currNumInputs < 2){
            if(getFirstInput() == null){
                inputs[0] = newGate;
                currNumInputs++;
            }else if(getSecondInput() == null){
                inputs[1] = newGate;
                currNumInputs++;
            }
        }
    }
}
