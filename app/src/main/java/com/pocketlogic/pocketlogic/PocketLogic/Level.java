package com.pocketlogic.pocketlogic.PocketLogic;

/*======================================   USER MANUAL    ==========================================
 *                                                                                                 *
 * This is a standard level file.                                                                  *
 * Configure this file and feed it to the gameDirector to render scene.                            *
 *                                                                                                 *
 *==================================================================================================

Declaration
Level levelA = new Level();

Functions

//=====     LEVEL DESIGN     =====
void setLevelName(String levelname)                      <- Setup Level name like "level_1"
void setSwitchesAmount(int switchesAmount)               <- Setup how many switches are used like [1-4]
void setTimeLimit(int seconds)                           <- Setup a time limit on current level
void disableTile(int x, int y)                           <- Disable certain tile in the gameboard
void enableTile(int x, int y)                            <- Enable certain tile in the gameboard
void setDefaultGate(int x, int y,String value)           <- Choose default gate for certain tile ex."XOR"
void setAvailableGates(int x, int y, String[] gatesSet)  <- Setup a set of gates the tile can select ex. {"AND", "OR", "NULL"}
void setTruthTable(boolean truthtable[][])               <- Put a end game conditions truthtable into level
String[] getAllGates()                                   <- Get a example gate sets: {"AND", "OR", "NOT", "NAND", "NOR", "XOR", "XNOR", "NULL"}

//===== ACCESSOR for DIRECTOR =====
String getLevelName()                                    <- Get level name like "level_1"
int getSwitchesAmount()                                  <- Get how many switches are used like [1-4]
int getTimeLimit()                                       <- Get how many seconds must the level finished in
boolean isDisabled(String tileID)                        <- See if the tile is redacted
String getDefaultGate(String tileID)                     <- Get the tile's default gate ex. "AND"
String[] getAvailableGates(String tileID)                <- Get the tile's all available gates
boolean[][] getTruthTable()                              <- Get the game finish conditions.
*/

public class Level {

    //===============================       PRIVATE MEMBERS      ===================================
    final public int X_Max=5, Y_Max=4;                                              //GameBoard Size
    private String levelName;                                                       //ex. "level_01"
    private boolean[] truthTable;                      //[[true,true,false][false, false true]...]
    private int switchUsed;                                                                    //1-4
    private int timeLimit;                                                        //Measured in secs
    private ElementConfig[][] tileConfigs;                                            //Tiles config

    //===============================================
    // A config is containing 3 properties
    // It will be assigned to each tile
    // redacted     <- if tile has disabled.
    // allowGates   <- the gates it is allowed to be
    // defaultValue <- default gate of it
    private class ElementConfig {
        public Boolean redacted;      //If tile can't be clicked
        public String[] allowGates;   //Ex. {"XOR","OR","AND"}
        public String defaultValue;   //Default value of the tile
        public ElementConfig(){
            this.defaultValue = "NULL";
            this.redacted = false;
            String [] allGates = {"AND", "OR", "NOT", "NAND", "NOR", "XOR", "XNOR", "NULL"};
            this.allowGates = allGates;
        }
    }

    //=====================================    CONSTRUCTOR    ======================================
    public Level() {

        this.levelName = "default_level";
        this.switchUsed = 4;
        timeLimit = 0;
        tileConfigs = new ElementConfig[X_Max+1][Y_Max+1];
        for (int i=1;i<X_Max+1;i++) {
            for (int j=1;j<Y_Max+1;j++) {
                tileConfigs[i][j] = new ElementConfig();
            }
        }
        this.truthTable = new boolean[] {
                true,true,true,true,true,true,true,true,false,false,false,false,false,false,false,false,
        };
    }

    //=============================            ACCESSORS           =================================
    public void setLevelName(String levelname) {
        this.levelName = levelname;
    }
    public String getLevelName() {
        return this.levelName;
    }
    public void setSwitchesAmount(int switchesAmount) {
        this.switchUsed = switchesAmount;
    }
    public int getSwitchesAmount() {
        return this.switchUsed;
    }
    public void setTimeLimit(int seconds) {
        this.timeLimit = seconds;
    }
    public int getTimeLimit() {
        return this.timeLimit;
    }
    public void disableTile(int x, int y) {
        this.tileConfigs[x][y].redacted = true;
    }
    public void enableTile(int x, int y) {
        this.tileConfigs[x][y].redacted = false;
    }
    public boolean isDisabled(String tileID) {
        int x,y;
        x = IDtoXY(tileID)[0];
        y = IDtoXY(tileID)[1];
        return (this.tileConfigs[x][y].redacted == true);
    }
    public void setDefaultGate(int x, int y,String value) {
        this.tileConfigs[x][y].defaultValue = value;
    }
    public String getDefaultGate(String tileID) {
        int x,y;
        x = IDtoXY(tileID)[0];
        y = IDtoXY(tileID)[1];
        return this.tileConfigs[x][y].defaultValue;
    }
    public void setAvailableGates(int x, int y, String[] gatesSet) {
        this.tileConfigs[x][y].allowGates = gatesSet;
    }
    public String[] getAvailableGates(String tileID) {
        int x,y;
        x = IDtoXY(tileID)[0];
        y = IDtoXY(tileID)[1];
        return this.tileConfigs[x][y].allowGates;
    }
    public void setTruthTable(boolean truthtable[]) {
        this.truthTable = truthtable;
    }
    public boolean[] getTruthTable() {
        return this.truthTable;
    }
    public String[] getAllGates() {
        String[] gates = {"AND", "OR", "NOT", "NAND", "NOR", "XOR", "XNOR", "NULL"};
        return gates;
    }

    //===================================== INTERNAL TOOLS =========================================
    // Convert tile ID to XY;
    // "tile_25" -> int [2,5];
    //
    // If Compiler says this function went wrong, that means you feed wrong ID
    // use "Log.d("IDtoXY-DEBUG",tileID);" to display the tileID
    private int[] IDtoXY(String tileID){
        String tileXY = tileID.split("_")[1];
        int[] XY = {0,0};
        XY[0] = tileXY.charAt(0) - '0';
        XY[1] = tileXY.charAt(1) - '0';
        return XY;
    }

    //=====================================    EXAMPLE CODE    ======================================
	/*
	 *
	Level levelA = new Level();

	levelA.setLevelName("level_1");

	levelA.setTimeLimit(30);

	levelA.disableTile(2, 2);

	levelA.setDefaultGate(2, 3, "AND");
	String gates[] = {"AND"};
	levelA.setAvailableGates(2, 3, gates);

	String gates2[] = {"AND", "OR", "NULL"};
	levelA.setAvailableGates(5, 4, gates2);

	levelA.setSwitchAmount = 1;

	boolean truthtable[][] = {true,true}

	levelA.setTruthTable(truthtable);


	cout(levelA.getLevelName());
	cout(Integer.toString(levelA.getSwitchesAmount()));
	cout(Integer.toString(levelA.getTimeLimit()));
	cout(Boolean.toString(levelA.isDisabled(2, 2)));
	cout(Boolean.toString(levelA.isDisabled(2, 3)));
	cout(levelA.getDefaultGate(2, 3));
	cout(levelA.getDefaultGate(2, 4));
	cout(Arrays.toString(levelA.getAvailableGates(2, 3)));
	cout(Arrays.toString(levelA.getAvailableGates(5, 4)));
	cout(Arrays.toString(levelA.getAvailableGates(1, 1)));

	function cout(String s) {
		Log.d("DRIVER", s);
	}

	*/
}
