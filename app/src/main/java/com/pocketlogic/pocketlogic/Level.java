package com.pocketlogic.pocketlogic;

import java.util.ArrayList;

public class Level
{
    ArrayList<Boolean[][]> levels = new ArrayList<Boolean[][]>();
    final boolean[][] INPUTS1 ={{true}, {false}};
    final boolean[][] INPUTS2 ={{false, false}, {false, true}, {true, false}, {true, true}};
    final boolean[][] INPUTS3 ={{false, false, false}, {false, false, true},
            {false, true, false}, {false, true, true}, {true, false, false},
            {true, false, true}, {true, true, false}, {true, true, true}};
    final boolean[][] INPUTS4 = {{false, false, false, false},
            {false, false, false, true}, {false, false, true, false},
            {false, false, true, true}, {false, true, false, false},
            {false, true, false, true}, {false, true, true, false},
            {false, true, true, true}, {true, false, false, false},
            {true, false, false, true}, {true, false, true, false},
            {true, false, true, true}, {true, true, false, false},
            {true, true, false, true}, {true, true, true, false},
            {true, true, true, true},};

    final boolean[] level1 = {true, false, true, false, true, false, true, false};

    public int num_gate_types = 7;

    public Level()
    {

    }

    public boolean[] evaluate(Tile gametable, boolean[] truthTable)
    {
        //TODO evaluate() method
        boolean [] returnTable = new boolean[truthTable.length];

        for (boolean b : truthTable)
        {

        }

        return returnTable;
    }


    public boolean[][] genRandomLevel(int numOfFinalValues)
    {
        if (numOfFinalValues < 1) numOfFinalValues = 1;
        boolean[][] level = new boolean[16][4 + numOfFinalValues];


        for (int row = 0; row < 16; row++) for (int col = 0; col < 4; col++)
        {
            //copying input values.
            level[row][col] = this.INPUTS4[row][col];
        }
        for (int row = 0; row < 16; row++) for (int col = 4; col < 4 + numOfFinalValues; col++)
        {
            //adding random final values.
            level[row][col] = getRandomBoolean();
        }
        return level;
    }

    public static boolean getRandomBoolean()
    {
        return Math.random() < 0.5;
    }

    // ---- Static level definitions ----
}