package com.pocketlogic.pocketlogic;

/**
 * Created by vulpi on 4/17/2018.
 */

public class Switch extends Tile{
    //private boolean value;
    //private int value;
    //private int[] drawables; /* = new int[] {R.drawables.switch_0, R.drawables.switch_1}; */

    public Switch(int type) {
        this.type = type;
        drawables = new int[] {R.drawable.switch_0, R.drawable.switch_1};
    }

    public Switch() {
        this.type = 0;
        drawables = new int[] {R.drawable.switch_0, R.drawable.switch_1};
    }

    public int getNext() {
        if(this.type == 0) {
            this.type = 1;
        }else {
            this.type = 0;
        }

        return this.type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getType() {
        return this.type;
    }

    public int getNextImage() {
        getNext();
        return drawables[this.type];
    }

    public int getImage() {
        return drawables[this.type];
    }

    public int eval() {
        return this.type;
    }

}
