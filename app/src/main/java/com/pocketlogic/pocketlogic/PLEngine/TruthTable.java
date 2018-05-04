package com.pocketlogic.pocketlogic.PLEngine;

import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import com.pocketlogic.pocketlogic.R;

/*======================================   USER MANUAL    ==========================================
    Usage:
    Truth table controller.

    Declaration:
    TruthTable truthTable = new TruthTable((Activity) activity);

    Available Functions:
    public void hideTruthTable()                                                       <- Hide table
    public void showTruthTable(boolean[] truthTable, boolean[] completeStatus)    <- show truthTable

*==================================================================================================*/

public class TruthTable {

    //===============================       PRIVATE MEMBERS      ===================================
    ImageView outputValues[][];                                  //arr[truthTable_index][nth_output]
    ImageView completeStatus[][];                                //arr[truthTable_index][nth_condition]
    Activity activity;                                           //Android Requirement

    //=====================================    CONSTRUCTOR    ======================================
    public TruthTable(Activity activity) {
        this.activity = activity;
        outputValues = new ImageView[4][];
        completeStatus = new ImageView[4][];
        loadTTableIMGs();
    }

    //=====================================  METHODS ===============================================

    //============================================
    // Logic:
    // Find and hide all truthtable layers
    public void hideTruthTable(){
        this.activity.findViewById(R.id.Layer6_TruthTable_BG).setVisibility(View.INVISIBLE);
        this.activity.findViewById(R.id.Layer7_truthTables_1).setVisibility(View.INVISIBLE);
        this.activity.findViewById(R.id.Layer7_truthTables_2).setVisibility(View.INVISIBLE);
        this.activity.findViewById(R.id.Layer7_truthTables_3).setVisibility(View.INVISIBLE);
        this.activity.findViewById(R.id.Layer7_truthTables_4).setVisibility(View.INVISIBLE);
        this.activity.findViewById(R.id.Layer8_TruthTable_RET).setVisibility(View.INVISIBLE);
    }

    /*============================================
     Logic:
     If it has 1 switch, use truthTable_1 to display
     If it has 2 switches, use truthTable_2 to display
     If it has 3 switches, use truthTable_3 to display
     If it has 4 switches, use truthTable_4 to display

     For every boolean results in the truthTable:
        Update corresponding table's output value
        Update corresponding table's complete status
    */
    public void showTruthTable(boolean[] truthTable, boolean[] statusTable){

        final int TABLE_LENGTH;                      //2,4,8,16  -  Values count
        final int TABLE_INDEX;                       //0,1,2,3   -  Which table
        boolean validTable = true;                   //If input is valid table

        switch(truthTable.length){

            //If it's 1 switch mode, there are 2 results
            case 2:TABLE_LENGTH = 2;
                   TABLE_INDEX = 0;
                   break;

            //If it's 2 switches mode, there are 4 results
            case 4:
                   TABLE_LENGTH = 4;
                   TABLE_INDEX = 1;
                   break;

            //If it's 3 switches mode, there are 8 results
            case 8:
                   TABLE_LENGTH = 8;
                   TABLE_INDEX = 2;
                   break;

            //If it's 4 switches mode, there are 16 results
            case 16:
                   TABLE_LENGTH = 16;
                   TABLE_INDEX = 3;
                   break;

            //Defensive programming: if table is invalid, CUT!
            default:
                Log.d("PLEngine","ERROR::Truthtable has ILLEGAL Size: " + Integer.toString(truthTable.length));
                validTable = false;
                TABLE_LENGTH = -1;
                TABLE_INDEX = -1;
                break;
        }
        if (validTable) {

            //For each boolean in the pass-in table, update it to the VIEW
            for (int i = 0; i < TABLE_LENGTH; i++) {
                changeTruthValue(TABLE_INDEX, i, truthTable[i]);
                changeStatus(TABLE_INDEX, i, statusTable[i]);
            }

            //Display base layers
            this.activity.findViewById(R.id.Layer6_TruthTable_BG).setVisibility(View.VISIBLE);
            this.activity.findViewById(R.id.Layer8_TruthTable_RET).setVisibility(View.VISIBLE);

            //Display the table layer
            switch (TABLE_INDEX) {
                case 0:
                    this.activity.findViewById(R.id.Layer7_truthTables_1).setVisibility(View.VISIBLE);
                    break;
                case 1:
                    this.activity.findViewById(R.id.Layer7_truthTables_2).setVisibility(View.VISIBLE);
                    break;
                case 2:
                    this.activity.findViewById(R.id.Layer7_truthTables_3).setVisibility(View.VISIBLE);
                    break;
                case 3:
                    this.activity.findViewById(R.id.Layer7_truthTables_4).setVisibility(View.VISIBLE);
                    break;
            }
        }
    }

    //=======================================  INTERNAL ============================================
    //Update boolean result to VIEW
    // true -> 1
    // false -> 0
    private void changeTruthValue(int tableIndex, int outputIndex, boolean value) {
        if (value)
            this.outputValues[tableIndex][outputIndex].setImageResource(R.drawable.truth_num1);
        else
            this.outputValues[tableIndex][outputIndex].setImageResource(R.drawable.truth_num0);
    }

    //Update boolean status to VIEW
    // true -> tick
    // false -> cross
    private void changeStatus(int tableIndex, int outputIndex, boolean value) {
        if (value)
            this.completeStatus[tableIndex][outputIndex].setImageResource(R.drawable.tick);
        else
            this.completeStatus[tableIndex][outputIndex].setImageResource(R.drawable.cross);
    }

    //Loads all IMG to database
    private void loadTTableIMGs(){

        //Load table 1
        outputValues[0] = new ImageView[2];
        outputValues[0][0] = this.activity.findViewById(R.id.truthTable_1_out_1);
        outputValues[0][1] = this.activity.findViewById(R.id.truthTable_1_out_2);

        completeStatus[0] = new ImageView[2];
        completeStatus[0][0] = this.activity.findViewById(R.id.truthTable_1_status_1);
        completeStatus[0][1] = this.activity.findViewById(R.id.truthTable_1_status_2);

        //Load table 2
        outputValues[1] = new ImageView[4];
        outputValues[1][0] = this.activity.findViewById(R.id.truthTable_2_out_1);
        outputValues[1][1] = this.activity.findViewById(R.id.truthTable_2_out_2);
        outputValues[1][2] = this.activity.findViewById(R.id.truthTable_2_out_3);
        outputValues[1][3] = this.activity.findViewById(R.id.truthTable_2_out_4);

        completeStatus[1] = new ImageView[4];
        completeStatus[1][0] = this.activity.findViewById(R.id.truthTable_2_status_1);
        completeStatus[1][1] = this.activity.findViewById(R.id.truthTable_2_status_2);
        completeStatus[1][2] = this.activity.findViewById(R.id.truthTable_2_status_3);
        completeStatus[1][3] = this.activity.findViewById(R.id.truthTable_2_status_4);

        //Load table 3
        outputValues[2] = new ImageView[8];
        outputValues[2][0] = this.activity.findViewById(R.id.truthTable_3_out_1);
        outputValues[2][1] = this.activity.findViewById(R.id.truthTable_3_out_2);
        outputValues[2][2] = this.activity.findViewById(R.id.truthTable_3_out_3);
        outputValues[2][3] = this.activity.findViewById(R.id.truthTable_3_out_4);
        outputValues[2][4] = this.activity.findViewById(R.id.truthTable_3_out_5);
        outputValues[2][5] = this.activity.findViewById(R.id.truthTable_3_out_6);
        outputValues[2][6] = this.activity.findViewById(R.id.truthTable_3_out_7);
        outputValues[2][7] = this.activity.findViewById(R.id.truthTable_3_out_8);

        completeStatus[2] = new ImageView[8];
        completeStatus[2][0] = this.activity.findViewById(R.id.truthTable_3_status_1);
        completeStatus[2][1] = this.activity.findViewById(R.id.truthTable_3_status_2);
        completeStatus[2][2] = this.activity.findViewById(R.id.truthTable_3_status_3);
        completeStatus[2][3] = this.activity.findViewById(R.id.truthTable_3_status_4);
        completeStatus[2][4] = this.activity.findViewById(R.id.truthTable_3_status_5);
        completeStatus[2][5] = this.activity.findViewById(R.id.truthTable_3_status_6);
        completeStatus[2][6] = this.activity.findViewById(R.id.truthTable_3_status_7);
        completeStatus[2][7] = this.activity.findViewById(R.id.truthTable_3_status_8);

        //Load table 4
        outputValues[3] = new ImageView[16];
        outputValues[3][0] = this.activity.findViewById(R.id.truthTable_4_out_1);
        outputValues[3][1] = this.activity.findViewById(R.id.truthTable_4_out_2);
        outputValues[3][2] = this.activity.findViewById(R.id.truthTable_4_out_3);
        outputValues[3][3] = this.activity.findViewById(R.id.truthTable_4_out_4);
        outputValues[3][4] = this.activity.findViewById(R.id.truthTable_4_out_5);
        outputValues[3][5] = this.activity.findViewById(R.id.truthTable_4_out_6);
        outputValues[3][6] = this.activity.findViewById(R.id.truthTable_4_out_7);
        outputValues[3][7] = this.activity.findViewById(R.id.truthTable_4_out_8);
        outputValues[3][8] = this.activity.findViewById(R.id.truthTable_4_out_9);
        outputValues[3][9] = this.activity.findViewById(R.id.truthTable_4_out_10);
        outputValues[3][10] = this.activity.findViewById(R.id.truthTable_4_out_11);
        outputValues[3][11] = this.activity.findViewById(R.id.truthTable_4_out_12);
        outputValues[3][12] = this.activity.findViewById(R.id.truthTable_4_out_13);
        outputValues[3][13] = this.activity.findViewById(R.id.truthTable_4_out_14);
        outputValues[3][14] = this.activity.findViewById(R.id.truthTable_4_out_15);
        outputValues[3][15] = this.activity.findViewById(R.id.truthTable_4_out_16);

        completeStatus[3] = new ImageView[16];
        completeStatus[3][0] = this.activity.findViewById(R.id.truthTable_4_status_1);
        completeStatus[3][1] = this.activity.findViewById(R.id.truthTable_4_status_2);
        completeStatus[3][2] = this.activity.findViewById(R.id.truthTable_4_status_3);
        completeStatus[3][3] = this.activity.findViewById(R.id.truthTable_4_status_4);
        completeStatus[3][4] = this.activity.findViewById(R.id.truthTable_4_status_5);
        completeStatus[3][5] = this.activity.findViewById(R.id.truthTable_4_status_6);
        completeStatus[3][6] = this.activity.findViewById(R.id.truthTable_4_status_7);
        completeStatus[3][7] = this.activity.findViewById(R.id.truthTable_4_status_8);
        completeStatus[3][8] = this.activity.findViewById(R.id.truthTable_4_status_9);
        completeStatus[3][9] = this.activity.findViewById(R.id.truthTable_4_status_10);
        completeStatus[3][10] = this.activity.findViewById(R.id.truthTable_4_status_11);
        completeStatus[3][11] = this.activity.findViewById(R.id.truthTable_4_status_12);
        completeStatus[3][12] = this.activity.findViewById(R.id.truthTable_4_status_13);
        completeStatus[3][13] = this.activity.findViewById(R.id.truthTable_4_status_14);
        completeStatus[3][14] = this.activity.findViewById(R.id.truthTable_4_status_15);
        completeStatus[3][15] = this.activity.findViewById(R.id.truthTable_4_status_16);

    }
}
