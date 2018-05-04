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

        Level level_1 = new Level();
        level_1.setLevelName("Connor's random level (1 Switch)");
        level_1.setSwitchesAmount(1);
        level_1.setTimeLimit(0);
        level_1.setTruthTable(Logic.randLevel(1));

        Level level_2 = new Level();
        level_2.setLevelName("Connor's random level (2 Switch)");
        level_2.setSwitchesAmount(2);
        level_2.setTimeLimit(0);
        level_2.setTruthTable(Logic.randLevel(2));

        Level level_3 = new Level();
        level_3.setLevelName("Connor's random level (3 Switch)");
        level_3.setSwitchesAmount(3);
        level_3.setTimeLimit(0);
        level_3.setTruthTable(Logic.randLevel(3));

        Level level_4 = new Level();
        level_4.setLevelName("Connor's random level (4 Switch)");
        level_4.setSwitchesAmount(4);
        level_4.setTimeLimit(0);
        level_4.setTruthTable(Logic.randLevel(4));

        Level level_5 = new Level();
        level_5.setLevelName("Limitations");
        level_5.setSwitchesAmount(1);
        level_5.setTimeLimit(0);
        level_5.disableTile(2,1);
        level_5.disableTile(4,1);
        level_5.disableTile(1,4);
        level_5.disableTile(5,4);
        level_5.setDefaultGate(3,2,"NOT");
        level_5.setAvailableGates(3,2,new String[] {"NOT","AND","NULL"});
        level_5.setDefaultGate(4,3,"AND");
        level_5.setAvailableGates(4,3,new String[] {"AND"});
        level_5.setTruthTable(new boolean[] {true,false});

        Level level_6 = new Level();
        level_6.setLevelName("Bomb Squad - NOT Gate");
        level_6.setSwitchesAmount(1);
        level_6.setTimeLimit(10);
        level_6.setTruthTable(new boolean[] {true,false});

        Level level_7 = new Level();
        level_7.setLevelName("Bomb Squad - Elite");
        level_7.setSwitchesAmount(1);
        level_7.setTimeLimit(30);
        level_7.setTruthTable(Logic.randLevel(1));

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


        /*
        for (int x=1;x<=5;x++){
            for (int y=1;y<=4;y++) {
                level_7.disableTile(x,y);
            }
        }
        */
        for(int y=1; y<=4; y++){
            if((y % 2) == 0){
                level_7.disableTile(1,y);
                level_7.disableTile(3,y);
                level_7.disableTile(5,y);
            }else{
                level_7.disableTile(2,y);
                level_7.disableTile(4,y);
            }
        }

        level_7.enableTile(x1,y1);
        level_7.enableTile(x2,y2);

        levels.add(level_1);
        levels.add(level_2);
        levels.add(level_3);
        levels.add(level_4);
        levels.add(level_5);
        levels.add(level_6);
        levels.add(level_7);
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
}
