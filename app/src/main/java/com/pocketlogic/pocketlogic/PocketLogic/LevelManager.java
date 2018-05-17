package com.pocketlogic.pocketlogic.PocketLogic;

/*======================================   USER MANUAL    ==========================================
Usage: store all information for one certain tile.
A tile could be a switch, a gate, a light.

Declaration
LevelManager levelManager = new LevelManager();

Available Functions
ArrayList<String> getLevelNames();                                          <- Get Level Name List
Level getLevel(String Name);                                                <- Get Level by name

*/
import android.util.Log;
import com.pocketlogic.pocketlogic.PLEngine.Logic;

import java.util.ArrayList;
import java.util.Random;

public class LevelManager {

    //===============================       PRIVATE MEMBERS      ===================================

    private ArrayList<Level> levels = new ArrayList<Level>();

    //=====================================    CONSTRUCTOR    ======================================
    public LevelManager() {

        /****define pocket logic 10 levels here *****/
        /*
        Level level_1 = new Level();
        level_1.setLevelName("Level 1");
        level_1.setSwitchesAmount(1);
        level_1.setTimeLimit(0);
        level_1.setTruthTable(new boolean[]{false, true});

        Level level_1 = new Level();
        level_2.setLevelName("Level 2");
        level_2.setSwitchesAmount(1);
        level_2.setTimeLimit(0);
        level_2.setTruthTable(new boolean[]{false, false});

        levels.add(level_1);
        levels.add(level_2);
*/
        /********************************************/
        boolean[] level_0_table = new boolean[] {false, true};
        boolean[] level_1_table = new boolean[] {false, false};
        boolean[] level_2_table = new boolean[] {false, true, false, false};
        boolean[] level_3_table = new boolean[] {true, false, true, true, true, false, false, false};
        boolean[] level_4_table = new boolean[] {true, true, true, true, true, true, true, true, false, false, false, false, true, true, true, true};
        boolean[] level_5_table = new boolean[] {true, true, true, true, true, true, true, true, true, true, true, true, false, true, false, false};
        boolean[] level_6_table = new boolean[] {true, true, true, true, false, false, true, true, true, true, true, true, true, true, false, false};



        /****examples***/

        Level level_0_easy = new Level();
        //level_0.setLevelName("Connor's random level (1 Switch)");
        level_0_easy.setLevelName("Level 0 Easy");
        level_0_easy.setSwitchesAmount(1);
        level_0_easy.setTimeLimit(0);
        //level_0_easy.setTruthTable(Logic.randLevel(1));
        level_0_easy.setTruthTable(level_0_table);

        Level level_0_hard = new Level();
        //level_0.setLevelName("Connor's random level (1 Switch)");
        level_0_hard.setLevelName("Level 0 Hard");
        level_0_hard.setSwitchesAmount(1);
        level_0_hard.setTimeLimit(60);
        //level_0_hard.setTruthTable(Logic.randLevel(1));
        level_0_hard.setTruthTable(level_0_table);

        Level level_1_easy = new Level();
        //level_1.setLevelName("Connor's random level (2 Switch)");
        level_1_easy.setLevelName("Level 1 Easy");
        level_1_easy.setSwitchesAmount(1);
        level_1_easy.setTimeLimit(0);
        //level_1_easy.setTruthTable(Logic.randLevel(2));
        level_1_easy.setTruthTable(level_1_table);

        Level level_1_hard = new Level();
        //level_1.setLevelName("Connor's random level (2 Switch)");
        level_1_hard.setLevelName("Level 1 Hard");
        level_1_hard.setSwitchesAmount(1);
        level_1_hard.setTimeLimit(10);
        //level_1_hard.setTruthTable(Logic.randLevel(2));
        level_1_hard.setTruthTable(level_1_table);

        Level level_2_easy = new Level();
        //level_2.setLevelName("Connor's random level (3 Switch)");
        level_2_easy.setLevelName("Level 2 Easy");
        level_2_easy.setSwitchesAmount(2);
        level_2_easy.setTimeLimit(0);
        //level_2_easy.setTruthTable(Logic.randLevel(3));
        level_2_easy.setTruthTable(level_2_table);

        Level level_2_hard = new Level();
        //level_2.setLevelName("Connor's random level (3 Switch)");
        level_2_hard.setLevelName("Level 2 Hard");
        level_2_hard.setSwitchesAmount(2);
        level_2_hard.setTimeLimit(10);
        //level_2_hard.setTruthTable(Logic.randLevel(3));
        level_2_hard.setTruthTable(level_2_table);

        Level level_3_easy = new Level();
        //level_3.setLevelName("Connor's random level (4 Switch)");
        level_3_easy.setLevelName("Level 3 Easy");
        level_3_easy.setSwitchesAmount(3);
        level_3_easy.setTimeLimit(0);
        //level_3_easy.setTruthTable(Logic.randLevel(4));
        level_3_easy.setTruthTable(level_3_table);

        Level level_3_hard = new Level();
        //level_3.setLevelName("Connor's random level (4 Switch)");
        level_3_hard.setLevelName("Level 3 Hard");
        level_3_hard.setSwitchesAmount(3);
        level_3_hard.setTimeLimit(60);
        //level_3_hard.setTruthTable(Logic.randLevel(4));
        level_3_hard.setTruthTable(level_3_table);

        Level level_4_easy = new Level();
        //level_4.setLevelName("Limitations");
        level_4_easy.setLevelName("Level 4 Easy");
        level_4_easy.setSwitchesAmount(4);
        level_4_easy.setTimeLimit(0);
    //    level_4_easy.setAvailableGates(3,2,new String[] {"NOT","AND","NULL"});
    //    level_4_easy.setAvailableGates(4,3,new String[] {"AND"});
        //level_4_easy.setTruthTable(new boolean[] {true,false});
        level_4_easy.setTruthTable(level_4_table);

        Level level_4_hard = new Level();
        //level_4.setLevelName("Limitations");
        level_4_hard.setLevelName("Level 4 Hard");
        level_4_hard.setSwitchesAmount(4);
        level_4_hard.setTimeLimit(60);
      //  level_4_hard.setAvailableGates(3,2,new String[] {"NOT","AND","NULL"});
      //  level_4_hard.setAvailableGates(4,3,new String[] {"AND"});
        //level_4_hard.setTruthTable(new boolean[] {true,false});
        level_4_hard.setTruthTable(level_4_table);

        Level level_5_easy = new Level();
        //level_5.setLevelName("Bomb Squad - NOT Gate");
        level_5_easy.setLevelName("Level 5 Easy");
        level_5_easy.setSwitchesAmount(4);
        level_5_easy.setTimeLimit(0);
        //level_5_easy.setTruthTable(new boolean[] {true,false});
        level_5_easy.setTruthTable(level_5_table);
        setGatesForAllTiles(level_5_easy, new String[] {"NAND", "NULL"});

        Level level_5_hard = new Level();
        //level_5.setLevelName("Bomb Squad - NOT Gate");
        level_5_hard.setLevelName("Level 5 Hard");
        level_5_hard.setSwitchesAmount(4);
        level_5_hard.setTimeLimit(10);
        //level_5_hard.setTruthTable(new boolean[] {true,false});
        level_5_hard.setTruthTable(level_5_table);
        setGatesForAllTiles(level_5_hard, new String[] {"NAND", "NULL"});

        Level level_6_easy = new Level();
        //level_6.setLevelName("Bomb Squad - Elite");
        level_6_easy.setLevelName("Level 6 Easy");
        level_6_easy.setSwitchesAmount(4);
        level_6_easy.setTimeLimit(0);
        //level_6_easy.setTruthTable(Logic.randLevel(1));
        level_6_easy.setTruthTable(level_6_table);
        setGatesForAllTiles(level_6_easy, new String[] {"NAND", "NOR", "NOT", "XNOR", "XOR", "NULL"});

        Level level_6_hard = new Level();
        //level_6.setLevelName("Bomb Squad - Elite");
        level_6_hard.setLevelName("Level 6 Hard");
        level_6_hard.setSwitchesAmount(4);
        level_6_hard.setTimeLimit(30);
        //level_6_hard.setTruthTable(Logic.randLevel(1));
        level_6_hard.setTruthTable(level_6_table);
        setGatesForAllTiles(level_6_hard, new String[] {"NAND", "NOR", "NOT", "XNOR", "XOR", "NULL"});


        /*
        //Code to select one of the possible tiles randomly:
        //21, 41
        //12, 32, 52
        //23, 43
        //14, 34, 54
        int y1 = randInt(1,4);
        int y2;
        do{
            y2=randInt(1,4);
        }while(y2==y1);

        int x1;
        if((y1 == 1) || (y1 == 3)){
            do{
                x1= randInt(1,5);
            } while((x1 != 2) && (x1!=4));
        }else{
            do{
                x1= randInt(1,5);
            } while((x1 != 1) && (x1 != 3) && (x1 != 5));
        }
        int x2;
        if((y2 == 1) || (y2 == 3)){
            do{
                x2= randInt(1,5);
            } while((x2 != 2) && (x2!=4));
        }else{
            do{
                x2= randInt(1,5);
            } while((x2 != 1) && (x2 != 3) && (x2 != 5));
        }



//        for (int x=1;x<=5;x++){
//            for (int y=1;y<=4;y++) {
//                level_6.disableTile(x,y);
//            }
//        }
*/

//        level_6_easy.enableTile(x1,y1);
//        level_6_easy.enableTile(x2,y2);
//        level_6_hard.enableTile(x1,y1);
//        level_6_hard.enableTile(x2,y2);

        /**/

        levels.add(level_0_easy);
        levels.add(level_0_hard);
        levels.add(level_1_easy);
        levels.add(level_1_hard);
        levels.add(level_2_easy);
        levels.add(level_2_hard);
        levels.add(level_3_easy);
        levels.add(level_3_hard);
        levels.add(level_4_easy);
        levels.add(level_4_hard);
        levels.add(level_5_easy);
        levels.add(level_5_hard);
        levels.add(level_6_easy);
        levels.add(level_6_hard);

    }

    //===================================      GETTERS      ========================================
    public ArrayList<String> getLevelNames() {
        ArrayList<String> names = new ArrayList<String>();
        for (int i=0;i<levels.size();i++) {
            names.add(levels.get(i).getLevelName());
        }
        return names;
    }

    public Level getLevel(String Name) {

        boolean found = false;
        Level targetLevel = new Level();

        for (int i=0;i<levels.size();i++) {
            if (levels.get(i).getLevelName().equals(Name)){
                targetLevel = levels.get(i);
                found = true;
            }
        }

        if (!found)
            Log.d("PLEngine ERROR","Level " + Name + " doesn't exists!");

        return targetLevel;
    }

    public static int randInt(int min, int max) {

        Random rand = new Random();
        int randomNum = rand.nextInt((max - min) + 1) + min;

        return randomNum;
    }

    private void setGatesForAllTiles(Level levelToSet, String[] gateTypes){
        for(int y=1; y<=4; y++){
            if((y % 2) == 0){
                levelToSet.setAvailableGates(1,y, gateTypes);
                levelToSet.setAvailableGates(3,y, gateTypes);
                levelToSet.setAvailableGates(5,y, gateTypes);
                levelToSet.setAvailableGates(1,y, gateTypes);
                levelToSet.setAvailableGates(3,y, gateTypes);
                levelToSet.setAvailableGates(5,y, gateTypes);
            }else{
                levelToSet.setAvailableGates(2,y, gateTypes);
                levelToSet.setAvailableGates(4,y, gateTypes);
                levelToSet.setAvailableGates(2,y, gateTypes);
                levelToSet.setAvailableGates(4,y, gateTypes);
            }
        }
    }
}
