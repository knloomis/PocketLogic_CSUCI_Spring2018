package com.pocketlogic.pocketlogic.PLEngine;

/*======================================   USER MANUAL    ==========================================
    Usage: LOGIC Controllers
    Owner: Connor

    Declaration:
        All static, use it directly
        ex. Logic.AND(1,0);

    static int AND(int A, int B)
    static int OR(int A, int B)
    static int NOT(int A)
    static int NAND(int A, int B)
    static int NOR(int A, int B)
    static int XOR(int A, int B)
    static int XNOR(int A, int B)

    static boolean[] randLevel(int switchCount)               ->Generate a random level

    static boolean getBoolean(int number)                     ->Convert int to Bool
    static boolean toBool(String value)                       ->"ON","OFF" to true, false
    static String toStr(Boolean value)                        ->true,false to "ON""OFF"
*/

public class Logic {
    public static int AND(int A, int B)
    {
        if((A == -1) || (B == -1)) {
            return -1;
        }else{
            boolean boolA = getBoolean(A);
            boolean boolB = getBoolean(B);

            if(boolA && boolB) {
                return 1;
            }else {
                return 0;
            }
        }

    }
    public static int OR(int A, int B)
    {
        if((A == -1) || (B == -1)) {
            return -1;
        }else {
            boolean boolA = getBoolean(A);
            boolean boolB = getBoolean(B);

            if (boolA || boolB) {
                return 1;
            }else {
                return 0;
            }
        }
    }

    public static int NOT(int A)
    {
//        int result = -1;
//        if(A == -1) {
//            return -1;
//        }else {
//            switch(A) {
//                case 0: result = 1;
//                case 1: result = 0;
//            }
//            return result;
//        }
        return Math.abs(A-1);
    }

    public static int NAND(int A, int B)
    {
        int value = AND(A, B);
        switch(value) {
            case 0: return 1;
            case 1: return 0;
            default: return -1;
        }
    }
    public static int NOR(int A, int B)
    {
        int value = OR(A, B);
        switch(value) {
            case 0: return 1;
            case 1: return 0;
            default: return -1;
        }
    }
    public static int XOR(int A, int B)
    {
        if(A == -1 || B == -1) {
            return -1;
        } else if(A == B) {
            return 0;
        }else {
            return 1;
        }
    }
    public static int XNOR(int A, int B)
    {
        int xorValue = XOR(A, B);
        switch(xorValue) {
            case 0: return 1;
            case 1: return 0;
            default: return -1;
        }
    }

    public static boolean getBoolean(int number) {
        boolean result;
        switch(number) {
            case 0: result = false; break;
            case 1: result = true; break;
            default: result = false; break;
        }

        return result;
    }

    public static boolean toBool(String value) {
        if (value.equals("ON"))
            return true;
        else
            return false;
    }

    public static boolean[] randLevel(int switchCount)
    {
        if (switchCount < 1) switchCount = 1;
        if (switchCount > 4) switchCount = 4;

        boolean[] truthtable = new boolean[(int)Math.pow(2.0, switchCount)];
        for (int index = 0; index < (Math.pow(2.0, switchCount)); index++)
        {
            truthtable[index] = (Math.random() > 0.5);
        }
        return truthtable;
    }

    public static String toStr(Boolean value) {
        if (value)
            return "ON";
        else
            return "OFF";
    }
}
