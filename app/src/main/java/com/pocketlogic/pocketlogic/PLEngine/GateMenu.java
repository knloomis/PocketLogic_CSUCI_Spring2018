package com.pocketlogic.pocketlogic.PLEngine;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import com.pocketlogic.pocketlogic.R;

/*======================================   USER MANUAL    ==========================================
    Usage: Gate selection menu controller

    Declaration
    GateMenu tileMenu = new GateMenu((Activity) activity, (Context) context);

    Available Functions

    public void showMenu(String[] allowedGates)       <- Render menu with buttons ex.{"NOT","NULL"}
    public void hideMenu()                            <- Hide the menu

*/

public class GateMenu {

    //===============================       PRIVATE MEMBERS      ===================================
    private ImageView[] menuButtons;
    private Activity activity;
    private Context context;

    //=====================================    CONSTRUCTOR    ======================================
    public GateMenu(Context context, Activity activity) {
        this.activity = activity;
        this.context = context;
        menuButtons = new ImageView[11];

        menuButtons[0] = (ImageView) activity.findViewById(R.id.tileChoose_NOR);
        menuButtons[1] = (ImageView) activity.findViewById(R.id.tileChoose_OR);
        menuButtons[2] = (ImageView) activity.findViewById(R.id.tileChoose_XOR);
        menuButtons[3] = (ImageView) activity.findViewById(R.id.tileChoose_XNOR);
        menuButtons[4] = (ImageView) activity.findViewById(R.id.tileChoose_NOT);
        menuButtons[5] = (ImageView) activity.findViewById(R.id.tileChoose_NAND);
        menuButtons[6] = (ImageView) activity.findViewById(R.id.tileChoose_AND);
        menuButtons[7] = (ImageView) activity.findViewById(R.id.tileChoose_NULL);

        menuButtons[8] = (ImageView) activity.findViewById(R.id.tileChoose_CONN);

        menuButtons[9] = (ImageView) activity.findViewById(R.id.tileChoose_RET);
        menuButtons[10] = (ImageView) activity.findViewById(R.id.tileChoose_CIRCLE);
    }

    //===================================   PUBLIC FUNCTIONS    ====================================
    public void showMenu(String[] allowedGates) {

        //Disable all gates
        this.disableAllButton();

        //Traverse allowedGates, enable allowed gates.
        for (int i=0;i<allowedGates.length;i++) {
            switch(allowedGates[i]){
                case "NOR":
                    this.findButtonById("NOR").setImageResource(R.drawable.nor);
                    this.findButtonById("NOR").setTag("tileChoose_NOR");
                    break;
                case "OR":
                    this.findButtonById("OR").setImageResource(R.drawable.or);
                    this.findButtonById("OR").setTag("tileChoose_OR");
                    break;
                case "XOR":
                    this.findButtonById("XOR").setImageResource(R.drawable.xor);
                    this.findButtonById("XOR").setTag("tileChoose_XOR");
                    break;
                case "XNOR":
                    this.findButtonById("XNOR").setImageResource(R.drawable.xnor);
                    this.findButtonById("XNOR").setTag("tileChoose_XNOR");
                    break;
                case "NOT":
                    this.findButtonById("NOT").setImageResource(R.drawable.not);
                    this.findButtonById("NOT").setTag("tileChoose_NOT");
                    break;
                case "NAND":
                    this.findButtonById("NAND").setImageResource(R.drawable.nand);
                    this.findButtonById("NAND").setTag("tileChoose_NAND");
                    break;
                case "AND":
                    this.findButtonById("AND").setImageResource(R.drawable.and);
                    this.findButtonById("AND").setTag("tileChoose_AND");
                    break;
                case "NULL":
                    this.findButtonById("NULL").setImageResource(R.drawable.reset);
                    this.findButtonById("NULL").setTag("tileChoose_NULL");
                    break;
                default:
                    Log.d("PLEngine","ERROR GateMenu::showMenu can't resolve gate type -> " + allowedGates[i]);
                    break;
            }
        }

        //Show Layer
        this.activity.findViewById(R.id.Layer4_TileChooseBG).setVisibility(View.VISIBLE);
        this.activity.findViewById(R.id.Layer4_1_TileChooseCircle).setVisibility(View.VISIBLE);
        this.activity.findViewById(R.id.Layer5_TileChooseButtons).setVisibility(View.VISIBLE);
    }
    //=================================================
    public void hideMenu(){

        //Disable all gates
        disableAllButton();

        //Hide Layer
        this.activity.findViewById(R.id.Layer4_TileChooseBG).setVisibility(View.INVISIBLE);
        this.activity.findViewById(R.id.Layer4_1_TileChooseCircle).setVisibility(View.INVISIBLE);
        this.activity.findViewById(R.id.Layer5_TileChooseButtons).setVisibility(View.INVISIBLE);
    }

    //===================================   PRIVATE FUNCTIONS    ===================================
    private ImageView findButtonById(String ID){
        switch(ID){
            case "NOR":
                return menuButtons[0];
            case "OR":
                return menuButtons[1];
            case "XOR":
                return menuButtons[2];
            case "XNOR":
                return menuButtons[3];
            case "NOT":
                return menuButtons[4];
            case "NAND":
                return menuButtons[5];
            case "AND":
                return menuButtons[6];
            case "NULL":
                return menuButtons[7];
            case "CONN":
                return menuButtons[8];
            case "RET":
                return menuButtons[9];
            case "CIRCLE":
                return menuButtons[10];
            default:
                Log.d("PLEngine","ERROR GateMenu::findButtonById can't resolve ID -> " + ID);
                return new ImageView(context);
        }
    }

    private void disableAllButton() {
        ((ImageView) this.activity.findViewById(R.id.tileChoose_AND)).setImageResource(R.drawable.locked_grid);
        ((ImageView) this.activity.findViewById(R.id.tileChoose_AND)).setTag("tileChoose_REDACTED");
        ((ImageView) this.activity.findViewById(R.id.tileChoose_NAND)).setImageResource(R.drawable.locked_grid);
        ((ImageView) this.activity.findViewById(R.id.tileChoose_NAND)).setTag("tileChoose_REDACTED");
        ((ImageView) this.activity.findViewById(R.id.tileChoose_NOR)).setImageResource(R.drawable.locked_grid);
        ((ImageView) this.activity.findViewById(R.id.tileChoose_NOR)).setTag("tileChoose_REDACTED");
        ((ImageView) this.activity.findViewById(R.id.tileChoose_NOT)).setImageResource(R.drawable.locked_grid);
        ((ImageView) this.activity.findViewById(R.id.tileChoose_NOT)).setTag("tileChoose_REDACTED");
        ((ImageView) this.activity.findViewById(R.id.tileChoose_OR)).setImageResource(R.drawable.locked_grid);
        ((ImageView) this.activity.findViewById(R.id.tileChoose_OR)).setTag("tileChoose_REDACTED");
        ((ImageView) this.activity.findViewById(R.id.tileChoose_XNOR)).setImageResource(R.drawable.locked_grid);
        ((ImageView) this.activity.findViewById(R.id.tileChoose_XNOR)).setTag("tileChoose_REDACTED");
        ((ImageView) this.activity.findViewById(R.id.tileChoose_XOR)).setImageResource(R.drawable.locked_grid);
        ((ImageView) this.activity.findViewById(R.id.tileChoose_XOR)).setTag("tileChoose_REDACTED");
        ((ImageView) this.activity.findViewById(R.id.tileChoose_NULL)).setImageResource(R.drawable.locked_grid);
        ((ImageView) this.activity.findViewById(R.id.tileChoose_NULL)).setTag("tileChoose_REDACTED");
    }
}
