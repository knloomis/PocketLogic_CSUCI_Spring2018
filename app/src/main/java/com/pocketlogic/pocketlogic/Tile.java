package com.pocketlogic.pocketlogic;

public class Tile
{
    private int type;
    private Tile inputA;
    private Tile inputB;
    private boolean value;

    private int[] gateDrawables = {R.drawable.hexagon, R.drawable.and, R.drawable.or, R.drawable.not, R.drawable.switch_0, R.drawable.nor, R.drawable.xor, R.drawable.xnor};
    private int[] switchDrawables = {};
    private int[] lightDrawables = {};

    public Tile(int type)
    {
        if (type >= 0 && type <= 7){
            this.type = type;
        }else{
            this.type = 0;
        }
    }

    public Tile(boolean value)
    {
        this.type = -1;
        this.value = value;
    }

    public int nextType()
    {
        if (this.type < 7) this.type++;
        else this.type = 0;
        //this.type = (this.type++) % 7;

        return this.type;
    }

    public String getTypeString()
    {
        //for debug
        switch (this.type)
        {
            case -2 : return "LIGHT";
            case -1 : return "SWITCH";
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

    public boolean eval()
    {
        switch (this.type)
        {
            case -1 : return this.value;
            case  1 : return this.AND (this.inputA.eval(), this.inputB.eval());
            case  2 : return this.OR (this.inputA.eval(), this.inputB.eval());
            case  3 : return this.NOT (this.inputA.eval());
            case  4 : return this.NAND (this.inputA.eval(), this.inputB.eval());
            case  5 : return this.NOR (this.inputA.eval(), this.inputB.eval());
            case  6 : return this.XNOR (this.inputA.eval(), this.inputB.eval());
            case  7 : return this.XNOR (this.inputA.eval(), this.inputB.eval());
            default : return false;
        }
    }

    private boolean AND(boolean A, boolean B)
    {
        if (A && B) return true;
        return false;
    }
    private boolean OR(boolean A, boolean B)
    {
        if (A || B) return true;
        return false;
    }
    private boolean NOT(boolean A)
    {
        return !(A);
    }
    private boolean NAND(boolean A, boolean B)
    {
        return !(AND(A, B));
    }
    private boolean NOR(boolean A, boolean B)
    {
        return !(OR(A, B));
    }
    private boolean XOR(boolean A, boolean B)
    {
        if (A != B) return true;
        return false;
    }
    private boolean XNOR(boolean A, boolean B)
    {
        return !(XOR(A, B));
    }
}