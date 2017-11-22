package Pkm;

import Utils.Collections.Table;
import Utils.ConsolePrinting;

import java.util.HashMap;
import java.util.Map;

import static Utils.ConsolePrinting.*;

public class Type {

    public static String RESET =             "\u001B[0m";
    public static String BOLD =              "\u001B[1m";
    public static String UNDER =             "\u001B[4m";
    public static String INVER =             "\u001B[7m";
    public static String FG_BLACK =          "\u001B[30m";
    public static String FG_RED =            "\u001B[31m";
    public static String FG_GREEN =          "\u001B[32m";
    public static String FG_YELLOW =         "\u001B[33m";
    public static String FG_BLUE =           "\u001B[34m";
    public static String FG_MAGENTA =        "\u001B[35m";
    public static String FG_CYAN =           "\u001B[36m";
    public static String FG_GRAY =           "\u001B[37m";
    public static String BG_BLACK =          "\u001B[40m";
    public static String BG_RED =            "\u001B[41m";
    public static String BG_GREEN =          "\u001B[42m";
    public static String BG_YELLOW =         "\u001B[43m";
    public static String BG_BLUE =           "\u001B[44m";
    public static String BG_MAGENTA =        "\u001B[45m";
    public static String BG_CYAN =           "\u001B[46m";
    public static String BG_GRAY =           "\u001B[47m";
    public static String FG_DARK_GRAY =      "\u001B[90m";
    public static String FG_BRIGHT_RED =     "\u001B[91m";
    public static String FG_BRIGHT_GREEN =   "\u001B[92m";
    public static String FG_BRIGHT_YELLOW =  "\u001B[93m";
    public static String FG_BRIGHT_BLUE =    "\u001B[94m";
    public static String FG_BRIGHT_MAGENTA = "\u001B[95m";
    public static String FG_BRIGHT_CYAN =    "\u001B[96m";
    public static String FG_WHITE =          "\u001B[97m";
    public static String BG_DARK_GRAY =      "\u001B[100m";
    public static String BG_BRIGHT_RED =     "\u001B[101m";
    public static String BG_BRIGHT_GREEN =   "\u001B[102m";
    public static String BG_BRIGHT_YELLOW =  "\u001B[103m";
    public static String BG_BRIGHT_BLUE =    "\u001B[104m";
    public static String BG_BRIGHT_MAGENTA = "\u001B[105m";
    public static String BG_BRIGHT_CYAN =    "\u001B[106m";
    public static String BG_WHITE =          "\u001B[107m";
    
    private Abbrv abbrv;
    private String name;
    private String fgColor;
    private String bgColor;

    private static final Type bug = new Type("Bug", Abbrv.BUG, BG_BRIGHT_GREEN, FG_BLACK);
    private static final Type dark = new Type("Dark", Abbrv.DRK, BG_WHITE, FG_BLACK);
    private static final Type dragon = new Type("Dragon", Abbrv.DRG, BG_CYAN, FG_BLACK);
    private static final Type earth = new Type("Earth", Abbrv.ERT, BG_YELLOW, FG_BLACK);
    private static final Type electric = new Type("Electric", Abbrv.ELE, BG_BRIGHT_YELLOW, FG_BLACK);
    private static final Type fairy = new Type("Fairy", Abbrv.FRY, BG_BRIGHT_RED, FG_BLACK);
    private static final Type fighting = new Type("Fighting", Abbrv.FGT, BG_BLACK, FG_WHITE);
    private static final Type fire = new Type("Fire", Abbrv.FIR, BG_RED, FG_BLACK);
    private static final Type flying = new Type("Flying", Abbrv.FLY, BG_BRIGHT_BLUE, FG_BLACK);
    private static final Type ghost = new Type("Ghost", Abbrv.GST, RESET, FG_BLACK);
    private static final Type grass = new Type("Grass", Abbrv.GRS, BG_GREEN, FG_BLACK);
    private static final Type ice = new Type("Ice", Abbrv.ICE, BG_BRIGHT_CYAN, FG_BLACK);
    private static final Type normal = new Type("Normal", Abbrv.NRM, RESET, RESET);
    private static final Type poison = new Type("Poison", Abbrv.POI, BG_MAGENTA, FG_BLACK);
    private static final Type psychic = new Type("Psychic", Abbrv.PSY, BG_BRIGHT_MAGENTA, FG_BLACK);
    private static final Type rock = new Type("Rock", Abbrv.RCK, BG_DARK_GRAY, FG_BLACK);
    private static final Type steel = new Type("Steel", Abbrv.STL, BG_GRAY, FG_BLACK);
    private static final Type water = new Type("Water", Abbrv.WAT, BG_BLUE, FG_BLACK);

    public enum Abbrv {
        BUG, DRK, DRG, ERT,
        ELE, FRY, FGT, FIR,
        FLY, GST, GRS, ICE, NRM,
        POI, PSY, RCK, STL, WAT
    }

    public static Table<Type, Type, Double> TABLE = new Table<>();
    public static Table<Type, Type, Double> DEFEND = new Table<>();

    Type(String name, Abbrv abbrv, String bgColor, String fgColor) {
        this.name = name;
        this.abbrv = abbrv;
        this.bgColor = bgColor;
        this.fgColor = fgColor;
    }

    public boolean equals(Object obj) {
        return this.abbrv.equals(((Type) obj).abbrv);
    }

    @Override
    public String toString() {
        return "" + bgColor + fgColor + " " + abbrv + " " + RESET;
    }

    public static void main(String[] args) {
        String[] att = new String[]{"A","T","T","A","C","K"};
        int t = 0;
        String out;
        for (int i = 0; i < 17; i++) {
            out = "   ";
            if(i % 2 == 0 && i > 0 && i < 15 && i != 8) {
                out = " " + att[t] + " ";
                t++;
            }
            println(ConsolePrinting.BG_RED, ConsolePrinting.BOLD, out);
        }
        println(bug);
        println(dark);
        println(dragon);
        println(earth);
        println(electric);
        println(fairy);
        println(fighting);
        println(fire);
        println(flying);
        println(ghost);
        println(grass);
        println(ice);
        println(normal);
        println(poison);
        println(psychic);
        println(rock);
        println(steel);
        println(water);

        Map<Type, Double> bug_ = new HashMap<>();
        bug_.put(bug, 1.0);
        bug_.put(dark, 2.0);
        bug_.put(dragon, 1.0);
        bug_.put(earth, 1.0);
        bug_.put(electric, 1.0);
        bug_.put(fairy, 0.5);
        TABLE.put(bug, bug_);


        print("     ");
//        for (Map.Entry<Type, Map<Type, Double>> p1 : TABLE.entrySet()) {
//            print(p1.getKey());
//
//        }
        for (Map.Entry<Type, Double> p2 : ((Map<Type, Double>) (TABLE.get(TABLE.keySet().toArray()[0]))).entrySet()) {
            print(p2.getKey());
        }
        println();
        for (Map.Entry<Type, Map<Type, Double>> p1 : TABLE.entrySet()) {
            print(p1.getKey());
            for(Map.Entry<Type, Double> p2 : p1.getValue().entrySet()) {
                Double atk = p2.getValue();
                String str = "  " + atk.intValue() + "  ";
                if (atk > 0 && atk < 1) {
                    str =  " " + FG_RED + atk + RESET + " ";
                }
                if( atk == 0) {
                    str = "  " + FG_WHITE + BG_BLACK + atk.intValue() + RESET + "  ";
                }
                if( atk > 1) {
                    str = "  " + FG_GREEN + atk.intValue() + RESET + "  ";
                }
                print(str);
            }
            println();
        }
    }
}
