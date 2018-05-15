package com.pocketlogic.pocketlogic.PocketLogic;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.widget.ImageView;

import com.pocketlogic.pocketlogic.R;
import com.pocketlogic.pocketlogic.PLEngine.*;

import java.util.Arrays;

/*========================================   USER MANUAL   =========================================

PLEngine Version 1.0.0000
Director Version 1.0.0000

Usage: Control Game Logics of Pocket Logic

Declaration
Director director = new Director((Activity)this,(Context)this);

=============   Debugger   =============
void say(String info, Boolean isErrorMsg)                            <- PLEngine Log

===========   Game Control   ===========
void gameInit()                                                     <- When game scene is shown
void gameStart()                                                     <- When game begins
void gameOver(boolean result)                                        <- Show End Screen
String getGameResult()                                               <- "Win" "Lose" "Progress"

==========   Level Adapter   ===========
void load(Level map)                                                 <- Loads level to director

=======   Interaction Handlers   =======
void tileClick(String tileID)                                        <- When a tile is clicked
void tileSelect(String choice)                                       <- When a tile selection is made
void switchToggle(String index)                                      <- When a switch toggle is clicked

==========   Light Control   ===========
void updateLight()                                                   <- Light ON\OFF control

========   TruthTable Control   ========
void openTruthTable()                                                <- Bring up truth table
void exitTruthTable()                                                <- Close truth table

========   BombSquad Control   =========
void bombStart()                                                     <- Trigger Bomb Timer
void bombTick(int currentTime)                                       <- When bomb ticks 1 sec
void bombDefuse()                                                    <- To defuse the bomb
void bombExplode()                                                   <- When timer reaches 0
void openBombSquadPrompt(int secs)                                   <- Show Bomb info
void exitBombSquadPrompt()                                           <- Turn off Bomb info board

===========   Tile Linker   ===========
void linkTile(String tileID1, String tileID2)                        <- Link up 2 tiles
void disconnectTile(String tileID1)                                  <- Break link

============   Evaluation   ============
boolean evalGame()                                                   <-Eval game with truthTable
boolean evalMap(boolean[] inputs)                                    <-Eval a map with defined switches
String evalNode(String A, String B, String Operator)                 <-Eval a node's value by Logic.Gates

=========   Graphics Render   =========
void renderGraphics()                                                <-Update lines and light

*/

public class Director{
    //===============================       PRIVATE MEMBERS      ===================================
    // Android System
    private Context context;
    private Activity activity;

    // Version Control
    final String PLENGINE_VERSION = "1.1.0000";
    final String DIRECTOR_VERSION = "1.2.0000";

    // PLEngine
    private TileManager tileManager;    // The data structure which stores all Tiles
    private LineDrawer lineDrawer;      // A line drawer
    private GateMenu gateMenu;          // Tile selection menu Controller
    private TruthTable truthTable;      // Truth table Controller
    private BombSquad bombSquad;        // bombSquad assets manager
    private EndGame endGame;            // A Manager shows\hide end game menu

    // Core Variables
    private Boolean linkingMode;        // true - next tile click will link two tiles together;
                                        // false - next tile click will bring up a menu
    private String clickedTile_ID;      // The ID of the tile that is being clicked
    private boolean gameStarted;        // Once truthtable is closed, game starts
    private BombTimer bombTimer;        // A custom timer from PLEngine
    private String gameResult;          //"Win" "Lose" "Progress"

    // Level Variables
    private Level map;                  // Level file
    private boolean[] truthtable;       // Level complete requirements
    private boolean[] completeStatus;   // Level requiremnts completed status(Tick, Cross)
    private boolean bombSquadMode;      // If BombSquad is enabled

    //========================================   CONSTRUCTOR   ========================================
    public Director(Activity activity, Context context){
        this.context = context;
        this.activity = activity;

        //PLEngine initialization
        //Init all tiles
        tileManager = new TileManager(this.activity);
        //Init line drawer
        lineDrawer = new LineDrawer((ImageView) this.activity.findViewById(R.id.superCanvas));
        //Init tile menu controller
        gateMenu = new GateMenu(this.context,this.activity);
        //Init truth table controller
        truthTable = new TruthTable(this.activity);
        //Init bombSquad module
        bombSquad = new BombSquad(this.activity);
        //Init end game manager
        endGame = new EndGame(this.activity);

        //Default Vars
        linkingMode = false;
        clickedTile_ID = "NULL";
        completeStatus = new boolean[16];
        gameStarted = false;
        gameResult = "Progress";

        //Hide gate menu
        gateMenu.hideMenu();

        //Hide bomb squad prompt
        bombSquad.hideBombSquadPrompt();

        //Hide End Game Menu
        endGame.hideScreen();

        //Hide Truth Table
        truthTable.hideTruthTable();

        //Load default map
        load(new Level());
    }

    //===========================================   DEBUG   ===========================================

    //==================   say()   ==================
    //Always Log with this function
    //Filter "PocketLogic Director", so you can view only your logs in Logcat
    public void say(String info, Boolean isErrorMsg) {
        if (isErrorMsg){
            Log.d("PLEngine Director","(V"+ DIRECTOR_VERSION + ")ERROR : " + info);
        } else {
            Log.d("PLEngine Director","(V"+ DIRECTOR_VERSION + ")INFO : " + info);
        }
    }

    //========================================   GAME HOOK   =======================================

    //===============   gameInit()   ================
    // Trigger: When gameplay is shown
    // Hook your plugin functions here
    public void gameInit(){
        gameResult = "Progress";
        updateLight();
    }

    //===============   gameStart()   ===============
    // Trigger: When user close the initial TruthTable
    // Hook your plugin functions here
    public void gameStart(){
        gameResult = "Progress";
    }

    //===============   gameOver()   ================
    // Trigger1: When user open truth table, game won
    // Trigger2: When bomb explodes
    // Hook your plugin functions here
    public void gameOver(boolean result) {
        if (result){
            if (bombSquadMode){
                bombDefuse();
            }
            gameResult = "Win";
            endGame.showScreen(true);
        }
        else{
            gameResult = "Lose";
            endGame.showScreen(false);
        }
    }

    //==============   gameResult()   ==============
    //It returns game status
    public String getGameResult() {
        return gameResult;
    }

    //=======================================   LEVEL ADAPTER   =======================================

    //==================   load()   ==================
    // Trigger1: Director Init
    // Trigger2: Custom map is loaded
    public void load(Level map) {

        //Hide level requirement.
        truthTable.hideTruthTable();

        this.map = map;
        Tile allTiles[] = tileManager.getAllTiles();               //Get all tiles

        //Lock down switches
        if (map.getSwitchesAmount()<4) {
            tileManager.findTileById("switch_4").setRedacted(true);
        }
        if (map.getSwitchesAmount()<3) {
            tileManager.findTileById("switch_3").setRedacted(true);
        }
        if (map.getSwitchesAmount()<2) {
            tileManager.findTileById("switch_2").setRedacted(true);
        }
        if (map.getSwitchesAmount()<1) {
            tileManager.findTileById("switch_1").setRedacted(true);
        }

        //Init complete status
        switch(map.getSwitchesAmount()){
            case 1: this.completeStatus = new boolean[2];
                break;
            case 2: this.completeStatus = new boolean[4];
                break;
            case 3: this.completeStatus = new boolean[8];
                break;
            case 4: this.completeStatus = new boolean[16];
                break;
        }

        //Lock down tiles and Set default gates
        for (int i=4;i<allTiles.length-1;i++) {             //Traverse all tiles
            if (map.isDisabled(allTiles[i].getID())) {      //If map says a tile is disabled.
                allTiles[i].setRedacted(true);              //disable it then
            }
            allTiles[i].setValue(map.getDefaultGate(allTiles[i].getID())); //Set default gate
        }

        //Grab truth table
        this.truthtable = map.getTruthTable();

        //BombSquad Hook
        if (map.getTimeLimit()==0) {
            bombSquadMode = false;
            bombSquad.disableBomb();
            openTruthTable();
        } else {
            showBombSquadPrompt();
            bombSquadMode = true;
            bombSquad.enableBomb();
            bombSquad.setBombTimer(map.getTimeLimit());
        }

        //Init Game
        gameInit();
    }

    //====================================   INTERACTION HANDLERS   ====================================


    /*======================   tileClick()   ======================
    When a game tile is click, this function will be called.
    The tileID will be passed in.
    tileID ex. "tile_42" "switch_3"

    Logic:

    If tile is not locked, proceed

        If not linking mode (Simple tile click)
               switch is click -> enable link mode
               tile is click -> open tile menu (With available gates of this tile)
               light is click -> alter light mode

        If it's linking mode (Connecting two tiles)
               switch is click -> deny (Nothing should connect to it)
               tile is click -> check Y coordinates if not in same level, connect.
               light is click -> change light's input and connect directly

    Calculate Graphics

    =============================================================*/
    public void tileClick(String tileID) {

        Tile tile = tileManager.findTileById(tileID);

        //If Tile is clickable
        if (!tile.isRedacted()) {

            //If it's a simple tile click
            if (this.linkingMode == false) {

                //Cache clicked tile
                this.clickedTile_ID = tileID;

                //If clicked tile is a SWITCH
                if (tile.getType().equals("switch")) {
                    this.linkingMode = true;
                }
                //If clicked tile is a TILE
                else if (tile.getType().equals("tile")) {
                    gateMenu.showMenu(map.getAvailableGates(tile.getID()));
                }
                //If clicked tile is a LIGHT
                else {
                    say("Light toggle has been disabled in Director 1.2",true);
                }
            }

            //If it's a Link Mode Click
            else {

                //If clicked tile is a SWITCH
                if (tile.getType().equals("switch")) {
                    say("A switch can't be connected!", true);
                }

                //If clicked tile is a TILE
                else if (tile.getType().equals("tile")) {
                    if (clickedTile_ID != "NULL") {

                        // tileCached is the previous tile click
                        // Procedure: tileCached clicked, link mode enabled, tile clicked
                        // tileCached.output -> tile
                        Tile tileCached = tileManager.findTileById(clickedTile_ID);

                        //If cached tile is switch
                        if (tileCached.getType().equals("switch")) {

                            if (tile.getInput(0).equals(tileCached.getID())
                                    ||
                                tile.getInput(1).equals(tileCached.getID()))
                            {
                                //If switch connect again to a tile it already connects, cut connection.
                                tile.removeInput(tileCached.getID());
                            }
                            else {
                                //If not, connect switch to new tile
                                linkTile(tileCached.getID(),tile.getID());
                            }

                        //If cached tile is gate
                        } else if (tileCached.getType().equals("tile")) {

                            //Only lower level connection is allowed
                            if (tileCached.getIndex().charAt(1) < tile.getIndex().charAt(1)) {
                                linkTile(tileCached.getID(), tile.getID());
                            } else {
                                say(":: tileClick :: Illegal connection to tile (Same or higher level)", true);
                            }
                        }
                    }

                //If clicked tile is a LIGHT
                } else if (tile.getType().equals("light")) {
                    Tile tileCached = tileManager.findTileById(clickedTile_ID);

                    //If switch connect again to a tile it already connects, cut connection.
                    if (tile.getInput(0).equals(tileCached.getID())) {
                        tile.removeInput(tileCached.getID());
                    } else {

                        //If light has been Linked, remove the link
                        if (!tile.getInput(0).equals("NULL")) {
                            disconnectTile(tile.getInput(0));
                        }
                        tile.setInputLight("NULL");

                        //Link cached tile to light
                        linkTile(tileCached.getID(), tile.getID());
                    }
                }

                //Clear cache and finish link
                this.linkingMode = false;
                this.clickedTile_ID = "NULL";
            }

            renderGraphics();
        }
    }



    /*========================  tileSelect  =======================
     When a game tile selection is made, this function is called.
     A choice will be send in as argument.
     choice -> {"CONN", "AND", "OR", "NOT", "NAND", "NOR", "XOR", "XNOR", "NULL", "RET", "REDACTED"}

     Logic:
     If request is "CONN" (connect)
        Check if current tile has connect to some tile
            If so -> remove connection
            If not -> enter LINK MODE, wait for next tile click
     If request is "RET"
            Clear cache and close menu
     If request is "REDACTED"
            If tile is redacted, don't close menu
     If request is all other gates
            Change tile to the gate it wants

     After all
           Hide menu
           Render Graphics

     =============================================================*/
    public void tileSelect(String choice){

        boolean hideMenu = true;                                       //Hide menu after this

        //If request is CONN
        if (choice.equals("CONN")) {

            //If current tile has Linked to something, CUT LINK
            if (!tileManager.findTileById(this.clickedTile_ID).getOutput().equals("NULL")) {
                disconnectTile(this.clickedTile_ID);
                this.clickedTile_ID = "";
            }

            //If current tile hasn't link to anything, enable LINK MODE
            else {
                this.linkingMode = true;
            }
        }

        //If request is RET
        else if (choice.equals("RET")) {
            if (linkingMode != true) {
                this.clickedTile_ID = "";
            } else {
                say("Link LEAK! This menu shouldn't be opened!",true);
            }
        }

        //If choosing disabled tiles
        else if (choice.equals("REDACTED")) {
            say("Not selectable gate!",true);
            hideMenu = false;
        }

        //If choosing gates
        else {
            //Apply gate selections -> "AND", "OR", "NOT", "NAND", "NOR", "XOR", "XNOR", "NULL"
            tileManager.findTileById(this.clickedTile_ID).setValue(choice);
            this.clickedTile_ID = "";
        }

        //If menu needs to be closed
        if (hideMenu)
            gateMenu.hideMenu();

        renderGraphics();
    }


    /*====================   switchToggle()   ====================
     When a switch is click, it needs to be toggle on and off

     Input:
     switch index "1","2","3" or "4"

     Logic:
     getTile("switch_" + index).turnOn

    =============================================================*/

    public void switchToggle(String index){
        //It's On?
        if (tileManager.findTileById("switch_" + index).getValue().equals("ON"))
            //Turn it Off
            tileManager.findTileById("switch_" + index).setValue("OFF");
        else {
            //Turn it On
            tileManager.findTileById("switch_" + index).setValue("ON");
        }

        updateLight();
    }

    //====================================   LIGHT CONTROLLER   ====================================


    /*=====================   updateLight()   ====================
    Trigger: When switch clicks
    Feature: Change light corresponding to truth table

    Logic:
        Change switches to int array ex. "{0,1},{0,1,1}"
        Determine corresponding truth table index.
        Update light with corresponding truth table value.
    =============================================================*/
    public void updateLight(){

        //Director V1.2 Patch
        //Light value = truthTable
        final int switchesUsed = this.map.getSwitchesAmount();
        Tile switches[] = new Tile[switchesUsed];
        int inputs[] = new int[switchesUsed];
        for (int i=0;i<switchesUsed;i++) {
            switches[i] = tileManager.findTileById("switch_" + Integer.toString(i+1));
            inputs[i] = (switches[i].getValue().startsWith("ON")) ? 1 : 0;
        }
        int truthTablePtr = -1;
        int targetPtr = -1;

        if (switchesUsed==1) {
            for (int i=0;i<2;i++) {
                truthTablePtr++;
                if (i==inputs[0]) {
                    targetPtr=truthTablePtr;
                }
            }
        } else if (switchesUsed==2) {
            for (int i=0;i<2;i++) {
                for (int j=0;j<2;j++) {
                    truthTablePtr++;
                    if (i==inputs[0] && j==inputs[1]) {
                        targetPtr=truthTablePtr;
                    }
                }
            }
        } else if (switchesUsed==3) {
            for (int i=0;i<2;i++) {
                for (int j=0;j<2;j++) {
                    for (int k=0;k<2;k++) {
                        truthTablePtr++;
                        if (i==inputs[0] && j==inputs[1] && k==inputs[2]) {
                            targetPtr=truthTablePtr;
                        }
                    }
                }
            }
        } else if (switchesUsed==4) {
            for (int i=0;i<2;i++) {
                for (int j=0;j<2;j++) {
                    for (int k=0;k<2;k++) {
                        for (int l=0;l<2;l++) {
                            truthTablePtr++;
                            if (i==inputs[0] && j==inputs[1] && k==inputs[2] && l==inputs[3]) {
                                targetPtr=truthTablePtr;
                            }
                        }
                    }
                }
            }
        }
        if (targetPtr==-1)
            say("updateLight()::Can't find corresponding light in truthtable",true);
        else {
            boolean lightValue = this.map.getTruthTable()[targetPtr];
            Tile light = tileManager.findTileById("light_1");
            say(Boolean.toString(lightValue),false);
            if (lightValue == true){
                light.setValue("ON-");
            } else {
                light.setValue("OFF-");
            }
        }

        renderGraphics();
    }



    //===================================   TRUTH TABLE CONTROLLER   ===================================


    /*===================   openTruthTable()   ===================
    Trigger: tablet icon listener
    Feature: open truth table

    Logic:
        Evaluate the whole game
        Show the truth table with current Complete status

        if (game has finished)
            gameOver it with win option
    =============================================================*/
    public void openTruthTable(){
        boolean gameWon = evalGame();
        truthTable.showTruthTable(this.truthtable,this.completeStatus);
        if (gameWon) {
                gameOver(true);
        }
    }

    /*===================   exitTruthTable()   ===================
    Trigger: truth table is clicked
    Feature: turn off the truth table

    Logic:
        Hide truth table
        If it's the 1st time closing truth table
            call gameStart, loads all hooks
    =============================================================*/
    public void exitTruthTable(){
        truthTable.hideTruthTable();
        if (!gameStarted) {
            gameStarted = true;
            gameStart();
        }
    }

    /*=====================================   BOMB SQUAD CONTROL   ====================================
        bombStart()           <- Starts the bomb with timer
        bombTick()            <- Each seconds this will be called
        bombDefuse()          <- Disable the bomb
        bombExplode()         <- End Game
        exitBombSquadPrompt() <- Exit information
    =============================================================*/
    public void bombStart(){
        this.bombTimer = new BombTimer(this,this.map.getTimeLimit() * 1000,1000);
        this.bombTimer.start();
    }
    public void bombTick(int currentTime){
        this.bombSquad.setBombTimer(currentTime);
    }

    public void bombDefuse(){
        this.bombTimer.cancel();
        this.bombSquad.disableBomb();
    }

    public void bombExplode(){
        gameOver(false);
    }

    public void exitBombSquadPrompt(){
        this.bombSquad.hideBombSquadPrompt();
        openTruthTable();
        bombStart();
    }

    public void showBombSquadPrompt(){
        bombSquad.showBombSquadPrompt(map.getTimeLimit());
        }

    //========================================   TILE LINKER   ========================================


    /*========================  linkTile  =======================
    This functions links two tiles together.
    tile1.output -> tile2

    Arguments:
    String tileID1 : ex."tile_32"
    String tileID2 : ex. "switch_1"

    Logic:

    Get two tiles by their ID.
    Connects them

    =============================================================*/
    private void linkTile(String tileID1, String tileID2) {
        Tile tile1 = tileManager.findTileById(tileID1);
        Tile tile2 = tileManager.findTileById(tileID2);

        tile1.setOutput(tileID2);
        tile2.setInput(tileID1);

        say(tile1.getID() + ".output ----> " + tile1.getOutput(),false);
        say(tile2.getID() + ".input1 ----> " + tile2.getInput(0),false);
        say(tile2.getID() + ".input2 ----> " + tile2.getInput(1),false);
    }




    /*========================  disconnectTile  =======================
    This functions cleans a tile's output
    tile1.output --X--> tile2
    tile2.input --X--> tile1

    Arguments:
    String tileID1 : ex."tile_32"

    Logic:

    Get two tiles by their ID.
    Erase Connection

    ==================================================================*/
    private void disconnectTile(String tileID1) {
        Tile tile1 = tileManager.findTileById(tileID1);

        if (!tile1.getOutput().equals("NULL")){
            Tile tile2 = tileManager.findTileById(tile1.getOutput());
            tile2.removeInput(tile1.getID());
            tile1.setOutput("NULL");

            say(tile1.getID() + ".output ----> " + tile1.getOutput(),false);
            say(tile2.getID() + ".input1 ----> " + tile2.getInput(0),false);
            say(tile2.getID() + ".input2 ----> " + tile2.getInput(1),false);
        } else {
            say(tile1.getID() + ".output ----> " + tile1.getOutput(),false);
        }
    }

    public boolean connectedLight(){
        boolean isConnected = false;
        if (!tileManager.findTileById("light_1").getInput(0).equals("NULL")){
            Tile light = tileManager.findTileById("light_1");
            Tile parent = tileManager.findTileById(light.getInput(0));
            if (!parent.getEvalValue().equals("NULL")) {
                isConnected = true;
            }
        }
        say("Light is connected:" + Boolean.toString(isConnected),false);
        return isConnected;
    }

    //=========================================   EVALUATION   =========================================
     /*======================   evalGame()   ======================
     This function evaluate the whole Game by
     simulating different inputs and produce a
     current status lists.

     Logic:
     For each switches
        simulate different inputs
            feed input to evalMap()
            verify if condition is met
     Return if all conditions are met.

     ==============================================================*/
    public boolean evalGame() {

        int conditionPtr = 0;

        if (this.map.getSwitchesAmount()==1) {
            for (int i=0;i<2;i++) {
                boolean[] virtualSwitches = {Logic.getBoolean(i),false,false,false};
                //If light has a input
                if (connectedLight()) {
                    this.completeStatus[conditionPtr] = (evalMap(virtualSwitches) == this.truthtable[conditionPtr]);
                } else {
                    this.completeStatus[conditionPtr] = false;
                }
                conditionPtr++;
            }
        }

        else if (this.map.getSwitchesAmount()==2) {
            for (int i=0;i<2;i++) {
                for (int j=0;j<2;j++) {
                    boolean[] virtualSwitches = {Logic.getBoolean(i),Logic.getBoolean(j),false,false};
                    if (connectedLight()) {
                        this.completeStatus[conditionPtr] = (evalMap(virtualSwitches) == this.truthtable[conditionPtr]);
                    } else {
                        this.completeStatus[conditionPtr] = false;
                    }
                    conditionPtr++;
                }
            }
        }

        else if (this.map.getSwitchesAmount()==3) {
            for (int i=0;i<2;i++) {
                for (int j=0;j<2;j++) {
                    for (int k=0;k<2;k++) {
                        boolean[] virtualSwitches = {Logic.getBoolean(i),Logic.getBoolean(j),Logic.getBoolean(k),false};
                        if (connectedLight()) {
                            this.completeStatus[conditionPtr] = (evalMap(virtualSwitches) == this.truthtable[conditionPtr]);
                        } else {
                            this.completeStatus[conditionPtr] = false;
                        }
                        conditionPtr++;
                    }
                }
            }
        }

        else if (this.map.getSwitchesAmount()==4) {
            for (int i=0;i<2;i++) {
                for (int j=0;j<2;j++) {
                    for (int k=0;k<2;k++) {
                        for (int l=0;l<2;l++) {
                            boolean[] virtualSwitches = {Logic.getBoolean(i),Logic.getBoolean(j),Logic.getBoolean(k),Logic.getBoolean(l)};
                            if (connectedLight()) {
                                this.completeStatus[conditionPtr] = (evalMap(virtualSwitches) == this.truthtable[conditionPtr]);
                            } else {
                                this.completeStatus[conditionPtr] = false;
                            }
                            conditionPtr++;
                        }
                    }
                }
            }
        }

        boolean allClear = true;
        say("completeStatus.length:" + Integer.toString(completeStatus.length),false);
        for (int i=0;i<completeStatus.length;i++) {
            if (completeStatus[i] == false) {
                allClear = false;
            }
        }

        renderGraphics();
        return allClear;
    }

    /*=========================   evalMap()   =========================
    To evaluate the current map with custom switches

    for each tiles:
        if it has two inputs & it isn't a NOT gate
            Eval it.
        if it has one input & it is a NOT gate
            Eval it.

    for light
        lit or unlit

    ====================================================================*/
    public boolean evalMap(boolean[] inputs) {

        //Setup final return value to false
        boolean finalValue = false;

        //Retreive all tiles
        Tile tiles[] = tileManager.getAllTiles();

        //Backup 4 inputs and override it with ARG
        Tile originalSwitches[] = new Tile[4];
        for (int i=0;i<4;i++) {
            originalSwitches[i] = tiles[i];
            if (inputs[i])
                tiles[i].setValue("ON");
            else
                tiles[i].setValue("OFF");
        }

        //Traverse all tiles
        for (int i=0;i<tiles.length;i++) {

            Tile currentTile = tiles[i];

            //If it is switch, "ON","OFF" already is the evaluated value.
            if (currentTile.getType().equals("switch")) {
                currentTile.setEvalValue(currentTile.getValue());
            }

            //If it is tile
            else if (currentTile.getType().equals("tile")) {

                //Default Evaluation Result = NULL
                currentTile.setEvalValue("NULL");

                //If it has a Gate, proceed
                if (!currentTile.getValue().equals("NULL")) {

                    //If it has pointing to 2 inputs, and it isn't a NOT gate, proceed.
                    if ((!currentTile.getInput(0).equals("NULL")) && (!currentTile.getInput(1).equals("NULL")) && (!currentTile.getValue().equals("NOT"))) {
                        Tile parentA = tileManager.findTileById(tiles[i].getInput(0));
                        Tile parentB = tileManager.findTileById(tiles[i].getInput(1));

                        //If 2 of its parents are evaluated, use 2 input values and its gate to Evaluate.
                        if ((!parentA.getEvalValue().equals("NULL")) && (!parentA.getEvalValue().equals("NULL"))) {
                            String evaluated = evalNode(parentA.getEvalValue(), parentB.getEvalValue(), currentTile.getValue());
                            currentTile.setEvalValue(evaluated);
                        }
                    }

                    //If it is a NOT gate, and it has only one input (the first input), proceed.
                    else if ((!currentTile.getInput(0).equals("NULL")) && (currentTile.getInput(1).equals("NULL")) && (currentTile.getValue().equals("NOT"))) {
                        Tile parentA = tileManager.findTileById(tiles[i].getInput(0));

                        //If its input has evaluated, do NOT(input)
                        if (!parentA.getEvalValue().equals("NULL"))  {
                            String evaluated = evalNode(parentA.getEvalValue(), "OFF", currentTile.getValue());
                            currentTile.setEvalValue(evaluated);
                        }
                    }

                    //If it is a NOT gate, and it has only one input (the second input), proceed.
                    else if ((currentTile.getInput(0).equals("NULL")) && (!currentTile.getInput(1).equals("NULL"))) {
                        Tile parentB = tileManager.findTileById(tiles[i].getInput(1));

                        //If its input has evaluated, do NOT(input)
                        if (!parentB.getEvalValue().equals("NULL"))  {
                            String evaluated = evalNode(parentB.getEvalValue(), "OFF", currentTile.getValue());
                            currentTile.setEvalValue(evaluated);
                        }
                    }
                }
            }

            //If it is light
            else if (currentTile.getType().equals("light")) {

                //If there is something links to a light
                if (!currentTile.getInput(0).equals("NULL")){

                    //Get that Node
                    Tile parentA = tileManager.findTileById(currentTile.getInput(0));

                    //If it is ON, return true
                    if (parentA.getEvalValue().equals("ON")) {
                        finalValue = true;
                    }
                }
            }
        }

        //Restore original switches
        for (int i=0;i<4;i++) {
            tiles[i] = originalSwitches[i];
        }

        //After all, return the final evaluated value
        return finalValue;
    }

    /*=========================   evalNode()   =========================
    To evaluate two inputs and one operator
    ex. A = "ON" B = "OFF" Operator = "AND" -> eval to "OFF"
        ON AND OFF = OFF

    Logic:

    Convert A & B from ON, OFF to 1,0
    Call GATE FUNCTIONS to evaluate

    if anything is not right, return "NULL" (When BUGGED OUT)

    ====================================================================*/
    public String evalNode(String A, String B, String Operator) {
        int numA = 0, numB = 0, numC = 0;
        boolean result = false;
        String resultStr;

        boolean buggedOut = false;
        switch (A) {
            case "ON":
                numA = 1;
                break;
            case "OFF":
                numA = 0;
                break;
            default:
                say(":: evalNode() :: Invalid Value: " + A, true);
                buggedOut = true;
                break;
        }
        switch (B) {
            case "ON":
                numB = 1;
                break;
            case "OFF":
                numB = 0;
                break;
            default:
                say(":: evalNode() :: Invalid Value: " + B, true);
                buggedOut = true;
                break;
        }

        if (!buggedOut) {
            switch (Operator) {
                case "AND":
                    numC = Logic.AND(numA, numB);
                    break;
                case "OR":
                    numC = Logic.OR(numA, numB);
                    break;
                case "NOT":
                    numC = Logic.NOT(numA);
                    break;
                case "NAND":
                    numC = Logic.NAND(numA, numB);
                    break;
                case "NOR":
                    numC = Logic.NOR(numA, numB);
                    break;
                case "XOR":
                    numC = Logic.XOR(numA, numB);
                    break;
                case "XNOR":
                    numC = Logic.XNOR(numA, numB);
                    break;
                default:
                    say(":: evalNode() :: Invalid GATE: " + Operator, true);
                    buggedOut = true;
                    break;
            }
            result = Logic.getBoolean(numC);
        }

        if (buggedOut) {
            resultStr = "NULL";
        } else if (result) {
            resultStr = "ON";
        } else {
            resultStr = "OFF";
        }

        return resultStr;
    }

    //======================================   GRAPHICS RENDER   ======================================


    /*========================  renderGraphics  =======================
    When graphic is need, this function kicks off.

    Logic:

    Evaluate all Tiles, assign boolean result for each tile "ON", "OFF" "NULL"

    Get tiles array from Engine
       Traverse all tiles
           if it is light, determine it is ON or OFF
           draw lines between a tile and its input tiles.
                Color that line
           render all Graphics

    =============================================================*/
    public void renderGraphics() {

        //Initialization
        lineDrawer.newPaint();
        Tile tiles[] = tileManager.getAllTiles();

        //Get 4 switches'status to Boolean array
        boolean switches[] = {Logic.toBool(tiles[0].getValue()),
                Logic.toBool(tiles[1].getValue()),
                Logic.toBool(tiles[2].getValue()),
                Logic.toBool(tiles[3].getValue())};

        //Evaluate whole gameMap with 4 switches' value
        boolean lightValue = evalMap(switches);

        //Traverse all Nodes
        for (int i=0;i<tiles.length;i++) {
            for (int j=0;j<2;j++) {

                //If Node'has a input
                if (!tiles[i].getInput(j).equals("NULL")) {

                    //Get Input's XY and CurrentNode's XY
                    Tile srcTile = tiles[i]; //
                    Tile tgtTile = tileManager.findTileById(tiles[i].getInput(j));

                    int srcXY[] = srcTile.getTopXY();
                    int tgtXY[] = tgtTile.getBottomXY();

                    //Determine line color
                    int drawColor;

                    //If Node is not evaluated, color = WHITE
                    if (tgtTile.getEvalValue().equals("NULL")) {
                        drawColor = Color.WHITE;
                    }

                    //If Node is evaluated to TRUE, color = YELLOW
                    else if (tgtTile.getEvalValue().equals("ON")) {
                        drawColor = Color.argb(255,217,186,72);
                    }

                    //If Node is evaluated to FALSE, color = PURPLE
                    else {
                        drawColor = Color.argb(255,152,120,219);
                    }

                    //Draw a Line
                    lineDrawer.drawLine(srcXY[0],srcXY[1],tgtXY[0],tgtXY[1], drawColor);
                }
            }
        }

        //Grab light, update lights ON\OFF
        //var lightValue gets when evalMap happens
        Tile light = tileManager.findTileById("light_1");
        if (lightValue) {
            if (light.getValue().startsWith("ON")) {
                light.setValue("ON+");
            } else {
                light.setValue("OFF-");
            }
        } else {
            if (light.getValue().startsWith("ON")) {
                light.setValue("ON-");
            } else {
                light.setValue("OFF+");
            }
        }

        //If light hasn't been connected, or connected tile is NULL, ON-, OFF-
        if (light.getInput(0).equals("NULL") || tileManager.findTileById(light.getInput(0)).getEvalValue().equals("NULL")){
            if (light.getValue().startsWith("ON")) {
                light.setValue("ON-");
            } else {
                light.setValue("OFF-");
            }
        }

        //Draw active outline for tile, if there exists one.
        if(this.linkingMode){
            Tile activeTile = tileManager.findTileById(clickedTile_ID);
            if(activeTile.getType().equals("switch")){

                int drawColor = Color.WHITE;
                int offset = 20;
                int width = 110;
                int height = 90;

                int[] middleXY = activeTile.getOriginXY();
                int[] savedBottomXY = activeTile.getBottomXY();

                int[] bottomXY = new int[] {savedBottomXY[0], savedBottomXY[1] + 130};

                int[] topLineLeftXY = new int[] {middleXY[0] - width + offset, middleXY[1]};
                int[] topLineRightXY = new int[] {middleXY[0] + width - offset, middleXY[1]};

                int[] topRightXY = new int[] {middleXY[0] + width + 30, middleXY[1] + 40};
                int[] topLeftXY = new int[] {middleXY[0] - width - 30, middleXY[1] + 40};
                int[] bottomRightXY = new int[] {bottomXY[0] + width + offset + 10, bottomXY[1] - height};
                int[] bottomLeftXY = new int[] {bottomXY[0] - width - offset - 10, bottomXY[1] - height};

                lineDrawer.drawLine(topLineLeftXY[0], topLineLeftXY[1], topLineRightXY[0], topLineRightXY[1], drawColor);
                lineDrawer.drawLine(topLineLeftXY[0], topLineLeftXY[1], topLeftXY[0], topLeftXY[1], drawColor);
                lineDrawer.drawLine(topLineRightXY[0], topLineRightXY[1], topRightXY[0], topRightXY[1], drawColor);
                lineDrawer.drawLine(bottomXY[0], bottomXY[1], bottomRightXY[0], bottomRightXY[1], drawColor);
                lineDrawer.drawLine(bottomXY[0], bottomXY[1], bottomLeftXY[0], bottomLeftXY[1], drawColor);
                lineDrawer.drawLine(bottomRightXY[0], bottomRightXY[1], topRightXY[0], topRightXY[1], drawColor);
                lineDrawer.drawLine(bottomLeftXY[0], bottomLeftXY[1], topLeftXY[0], topLeftXY[1], drawColor);

            }else if(activeTile.getType().equals("tile")){
                if(activeTile != null){
                    int drawColor = Color.WHITE;
                    int[] savedTopXY = activeTile.getTopXY();
                    int[] savedBottomXY = activeTile.getBottomXY();

                    int offset = 10;

                    int[] topXY = new int[] {savedTopXY[0], savedTopXY[1] - offset};
                    int[] bottomXY = new int[]{savedBottomXY[0], savedBottomXY[1] + offset};
                    int width = 142;
                    int height = 90;
                    int[] topRightXY = new int[] {topXY[0] + width, topXY[1] + height};
                    int[] bottomRightXY = new int[] {bottomXY[0] + width, bottomXY[1] - height};
                    int[] topLeftXY = new int[] {topXY[0] - width, topXY[1] + height};
                    int[] bottomLeftXY = new int[] {bottomXY[0] - width, bottomXY[1] - height};

                    lineDrawer.drawLine(topXY[0], topXY[1], topRightXY[0], topRightXY[1], drawColor);
                    lineDrawer.drawLine(topXY[0], topXY[1], topLeftXY[0], topLeftXY[1], drawColor);
                    lineDrawer.drawLine(bottomXY[0], bottomXY[1], bottomLeftXY[0], bottomLeftXY[1], drawColor);
                    lineDrawer.drawLine(bottomXY[0], bottomXY[1], bottomRightXY[0], bottomRightXY[1], drawColor);
                    lineDrawer.drawLine(bottomRightXY[0], bottomRightXY[1], topRightXY[0], topRightXY[1], drawColor);
                    lineDrawer.drawLine(bottomLeftXY[0], bottomLeftXY[1], topLeftXY[0], topLeftXY[1], drawColor);


                }
            }
        }

        //Pen down all those lines
        lineDrawer.renderLines();
    }
}