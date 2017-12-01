package Pkm;

import Utils.Collections.Table;
import Utils.ConsolePrinting;

import java.util.HashMap;
import java.util.Map;

import static Utils.ConsolePrinting.print;
import static Utils.ConsolePrinting.println;
import static Utils.StringUtils.StringUtils.padCenter;
import static Utils.StringUtils.StringUtils.padToLength;

public class Type {

    public static String RESET =             "\u001B[0m";
    public static String BOLD =              "\u001B[1m";
    public static String UNDER =             "\u001B[4m";
    public static String INVER =             "\u001B[7m";
    public static String FG_WHITE =          "\u001B[30m";
    public static String FG_RED =            "\u001B[31m";
    public static String FG_GREEN =          "\u001B[32m";
    public static String FG_YELLOW =         "\u001B[33m";
    public static String FG_BLUE =           "\u001B[34m";
    public static String FG_MAGENTA =        "\u001B[35m";
    public static String FG_CYAN =           "\u001B[36m";
    public static String FG_GRAY =           "\u001B[37m";
    public static String BG_WHITE =          "\u001B[40m";
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
    public static String FG_BLACK =          "\u001B[97m";
    public static String BG_DARK_GRAY =      "\u001B[100m";
    public static String BG_BRIGHT_RED =     "\u001B[101m";
    public static String BG_BRIGHT_GREEN =   "\u001B[102m";
    public static String BG_BRIGHT_YELLOW =  "\u001B[103m";
    public static String BG_BRIGHT_BLUE =    "\u001B[104m";
    public static String BG_BRIGHT_MAGENTA = "\u001B[105m";
    public static String BG_BRIGHT_CYAN =    "\u001B[106m";
    public static String BG_BLACK =          "\u001B[107m";
    
    private Abbrv abbrv;
    private String name;
    private String fgColor;
    private String bgColor;

    private static final Type bug = new Type("Bug", Abbrv.BUG, BG_BRIGHT_GREEN, FG_WHITE);
    private static final Type dark = new Type("Dark", Abbrv.DARK, BG_WHITE, FG_BLACK);
    private static final Type dragon = new Type("Dragon", Abbrv.DRAGN, BG_CYAN, FG_WHITE);
    private static final Type earth = new Type("Earth", Abbrv.EARTH, BG_YELLOW, FG_WHITE);
    private static final Type electric = new Type("Electric", Abbrv.ELECT, BG_BRIGHT_YELLOW, FG_WHITE);
    private static final Type fairy = new Type("Fairy", Abbrv.FAIRY, BG_BRIGHT_RED, FG_WHITE);
    private static final Type fighting = new Type("Fighting", Abbrv.FIGHT, BG_BLACK, FG_WHITE);
    private static final Type fire = new Type("Fire", Abbrv.FIRE, BG_RED, FG_WHITE);
    private static final Type flying = new Type("Flying", Abbrv.FLYNG, BG_BRIGHT_BLUE, FG_WHITE);
    private static final Type ghost = new Type("Ghost", Abbrv.GHOST, RESET, FG_BLACK);
    private static final Type grass = new Type("Grass", Abbrv.GRASS, BG_GREEN, FG_WHITE);
    private static final Type ice = new Type("Ice", Abbrv.ICE, BG_BRIGHT_CYAN, FG_WHITE);
    private static final Type normal = new Type("Normal", Abbrv.NORM, RESET, RESET);
    private static final Type poison = new Type("Poison", Abbrv.POISN, BG_MAGENTA, FG_WHITE);
    private static final Type psychic = new Type("Psychic", Abbrv.PSYCH, BG_BRIGHT_MAGENTA, FG_WHITE);
    private static final Type rock = new Type("Rock", Abbrv.ROCK, BG_DARK_GRAY, FG_WHITE);
    private static final Type steel = new Type("Steel", Abbrv.STEEL, BG_GRAY, FG_WHITE);
    private static final Type water = new Type("Water", Abbrv.WATER, BG_BLUE, FG_WHITE);

    public enum Abbrv {
        BUG, DARK, DRAGN, EARTH,
        ELECT, FAIRY, FIGHT, FIRE,
        FLYNG, GHOST, GRASS, ICE, NORM,
        POISN, PSYCH, ROCK, STEEL, WATER
    }

    public static Table<Type, Type, Double> TABLE = new Table<>();

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
        if(abbrv.toString().length() < 4) {
            return bgColor + fgColor + "  " + abbrv + "  " + RESET;
        }
        if(abbrv.toString().length() < 5) {
            return "" + bgColor + fgColor + "  " + abbrv + " " + RESET;
        }
        return "" + bgColor + fgColor + " " + abbrv + " " + RESET;
    }

    public static void main(String[] args) {
        
        Map<Type, Double> bug_ = new HashMap<>();
        bug_.put(bug, 1.0);
        bug_.put(dark, 2.0);
        bug_.put(dragon, 1.0);
        bug_.put(earth, 1.0);
        bug_.put(electric, 1.0);
        bug_.put(fairy, 0.5);
        bug_.put(fighting, 0.5);
        bug_.put(fire, 0.5);
        bug_.put(flying, 0.5);
        bug_.put(ghost, 0.5);
        bug_.put(grass, 2.0);
        bug_.put(ice, 1.0);
        bug_.put(normal, 1.0);
        bug_.put(poison, 0.5);
        bug_.put(psychic, 2.0);
        bug_.put(rock, 1.0);
        bug_.put(steel, 0.5);
        bug_.put(water, 1.0);
        TABLE.put(bug, bug_);

        Map<Type, Double> dark_ = new HashMap<>();
        dark_.put(bug, 1.0);
        dark_.put(dark, 0.5);
        dark_.put(dragon, 1.0);
        dark_.put(earth, 1.0);
        dark_.put(electric, 1.0);
        dark_.put(fairy, 0.5);
        dark_.put(fighting, 0.5);
        dark_.put(fire, 1.0);
        dark_.put(flying, 1.0);
        dark_.put(ghost, 2.0);
        dark_.put(grass, 1.0);
        dark_.put(ice, 1.0);
        dark_.put(normal, 1.0);
        dark_.put(poison, 1.0);
        dark_.put(psychic, 2.0);
        dark_.put(rock, 1.0);
        dark_.put(steel, 1.0);
        dark_.put(water, 1.0);
        TABLE.put(dark, dark_);

        Map<Type, Double> dragon_ = new HashMap<>();
        dragon_.put(bug, 1.0);
        dragon_.put(dark, 1.0);
        dragon_.put(dragon, 2.0);
        dragon_.put(earth, 1.0);
        dragon_.put(electric, 1.0);
        dragon_.put(fairy, 0.0);
        dragon_.put(fighting, 1.0);
        dragon_.put(fire, 1.0);
        dragon_.put(flying, 1.0);
        dragon_.put(ghost, 1.0);
        dragon_.put(grass, 1.0);
        dragon_.put(ice, 1.0);
        dragon_.put(normal, 1.0);
        dragon_.put(poison, 1.0);
        dragon_.put(psychic, 1.0);
        dragon_.put(rock, 1.0);
        dragon_.put(steel, 0.5);
        dragon_.put(water, 1.0);
        TABLE.put(dragon, dragon_);

        Map<Type, Double> earth_ = new HashMap<>();
        earth_.put(bug, 0.5);
        earth_.put(dark, 1.0);
        earth_.put(dragon, 1.0);
        earth_.put(earth, 1.0);
        earth_.put(electric, 2.0);
        earth_.put(fairy, 1.0);
        earth_.put(fighting, 1.0);
        earth_.put(fire, 2.0);
        earth_.put(flying, 0.0);
        earth_.put(ghost, 1.0);
        earth_.put(grass, 0.5);
        earth_.put(ice, 1.0);
        earth_.put(normal, 1.0);
        earth_.put(poison, 2.0);
        earth_.put(psychic, 1.0);
        earth_.put(rock, 2.0);
        earth_.put(steel, 2.0);
        earth_.put(water, 1.0);
        TABLE.put(earth, earth_);

        Map<Type, Double> electric_ = new HashMap<>();
        electric_.put(bug, 1.0);
        electric_.put(dark, 1.0);
        electric_.put(dragon, 0.5);
        electric_.put(earth, 1.0);
        electric_.put(electric, 0.5);
        electric_.put(fairy, 1.0);
        electric_.put(fighting, 1.0);
        electric_.put(fire, 1.0);
        electric_.put(flying, 2.0);
        electric_.put(ghost, 1.0);
        electric_.put(grass, 0.5);
        electric_.put(ice, 1.0);
        electric_.put(normal, 1.0);
        electric_.put(poison, 1.0);
        electric_.put(psychic, 1.0);
        electric_.put(rock, 1.0);
        electric_.put(steel, 1.0);
        electric_.put(water, 2.0);
        TABLE.put(electric, electric_);

        Map<Type, Double> fairy_ = new HashMap<>();
        fairy_.put(bug, 1.0);
        fairy_.put(dark, 2.0);
        fairy_.put(dragon, 2.0);
        fairy_.put(earth, 1.0);
        fairy_.put(electric, 1.0);
        fairy_.put(fairy, 1.0);
        fairy_.put(fighting, 2.0);
        fairy_.put(fire, 0.5);
        fairy_.put(flying, 1.0);
        fairy_.put(ghost, 1.0);
        fairy_.put(grass, 1.0);
        fairy_.put(ice, 1.0);
        fairy_.put(normal, 1.0);
        fairy_.put(poison, 0.5);
        fairy_.put(psychic, 1.0);
        fairy_.put(rock, 1.0);
        fairy_.put(steel, 0.5);
        fairy_.put(water, 1.0);
        TABLE.put(fairy, fairy_);

        Map<Type, Double> fighting_ = new HashMap<>();
        fighting_.put(bug, 0.5);
        fighting_.put(dark, 2.0);
        fighting_.put(dragon, 1.0);
        fighting_.put(earth, 1.0);
        fighting_.put(electric, 1.0);
        fighting_.put(fairy, 0.5);
        fighting_.put(fighting, 1.0);
        fighting_.put(fire, 1.0);
        fighting_.put(flying, 0.5);
        fighting_.put(ghost, 0.0);
        fighting_.put(grass, 1.0);
        fighting_.put(ice, 2.0);
        fighting_.put(normal, 2.0);
        fighting_.put(poison, 2.0);
        fighting_.put(psychic, 0.5);
        fighting_.put(rock, 2.0);
        fighting_.put(steel, 2.0);
        fighting_.put(water, 1.0);
        TABLE.put(fighting, fighting_);

        Map<Type, Double> fire_ = new HashMap<>();
        fire_.put(bug, 2.0);
        fire_.put(dark, 1.0);
        fire_.put(dragon, 0.5);
        fire_.put(earth, 0.5);
        fire_.put(electric, 1.0);
        fire_.put(fairy, 1.0);
        fire_.put(fighting, 1.0);
        fire_.put(fire, 0.5);
        fire_.put(flying, 1.0);
        fire_.put(ghost, 1.0);
        fire_.put(grass, 2.0);
        fire_.put(ice, 2.0);
        fire_.put(normal, 1.0);
        fire_.put(poison, 1.0);
        fire_.put(psychic, 1.0);
        fire_.put(rock, 0.5);
        fire_.put(steel, 2.0);
        fire_.put(water, 0.5);
        TABLE.put(fire, fire_);

        Map<Type, Double> flying_ = new HashMap<>();
        flying_.put(bug, 2.0);
        flying_.put(dark, 1.0);
        flying_.put(dragon, 1.0);
        flying_.put(earth, 1.0);
        flying_.put(electric, 0.5);
        flying_.put(fairy, 1.0);
        flying_.put(fighting, 2.0);
        flying_.put(fire, 1.0);
        flying_.put(flying, 1.0);
        flying_.put(ghost, 1.0);
        flying_.put(grass, 2.0);
        flying_.put(ice, 1.0);
        flying_.put(normal, 1.0);
        flying_.put(poison, 1.0);
        flying_.put(psychic, 1.0);
        flying_.put(rock, 0.5);
        flying_.put(steel, 0.5);
        flying_.put(water, 1.0);
        TABLE.put(flying, flying_);

        Map<Type, Double> ghost_ = new HashMap<>();
        ghost_.put(bug, 1.0);
        ghost_.put(dark, 0.5);
        ghost_.put(dragon, 1.0);
        ghost_.put(earth, 1.0);
        ghost_.put(electric, 1.0);
        ghost_.put(fairy, 1.0);
        ghost_.put(fighting, 1.0);
        ghost_.put(fire, 1.0);
        ghost_.put(flying, 1.0);
        ghost_.put(ghost, 2.0);
        ghost_.put(grass, 1.0);
        ghost_.put(ice, 1.0);
        ghost_.put(normal, 0.0);
        ghost_.put(poison, 1.0);
        ghost_.put(psychic, 2.0);
        ghost_.put(rock, 1.0);
        ghost_.put(steel, 1.0);
        ghost_.put(water, 1.0);
        TABLE.put(ghost, ghost_);

        Map<Type, Double> grass_ = new HashMap<>();
        grass_.put(bug, 0.5);
        grass_.put(dark, 1.0);
        grass_.put(dragon, 1.0);
        grass_.put(earth, 2.0);
        grass_.put(electric, 1.0);
        grass_.put(fairy, 1.0);
        grass_.put(fighting, 1.0);
        grass_.put(fire, 0.5);
        grass_.put(flying, 0.5);
        grass_.put(ghost, 1.0);
        grass_.put(grass, 0.5);
        grass_.put(ice, 1.0);
        grass_.put(normal, 1.0);
        grass_.put(poison, 0.5);
        grass_.put(psychic, 1.0);
        grass_.put(rock, 2.0);
        grass_.put(steel, 0.5);
        grass_.put(water, 2.0);
        TABLE.put(grass, grass_);

        Map<Type, Double> ice_ = new HashMap<>();
        ice_.put(bug, 1.0);
        ice_.put(dark, 1.0);
        ice_.put(dragon, 2.0);
        ice_.put(earth, 2.0);
        ice_.put(electric, 1.0);
        ice_.put(fairy, 1.0);
        ice_.put(fighting, 1.0);
        ice_.put(fire, 0.5);
        ice_.put(flying, 2.0);
        ice_.put(ghost, 1.0);
        ice_.put(grass, 2.0);
        ice_.put(ice, 0.5);
        ice_.put(normal, 1.0);
        ice_.put(poison, 1.0);
        ice_.put(psychic, 1.0);
        ice_.put(rock, 2.0);
        ice_.put(steel, 0.5);
        ice_.put(water, 1.0);
        TABLE.put(ice, ice_);

        Map<Type, Double> normal_ = new HashMap<>();
        normal_.put(bug, 1.0);
        normal_.put(dark, 1.0);
        normal_.put(dragon, 1.0);
        normal_.put(earth, 1.0);
        normal_.put(electric, 1.0);
        normal_.put(fairy, 1.0);
        normal_.put(fighting, 1.0);
        normal_.put(fire, 1.0);
        normal_.put(flying, 1.0);
        normal_.put(ghost, 0.0);
        normal_.put(grass, 1.0);
        normal_.put(ice, 1.0);
        normal_.put(normal, 1.0);
        normal_.put(poison, 1.0);
        normal_.put(psychic, 1.0);
        normal_.put(rock, 0.5);
        normal_.put(steel, 0.5);
        normal_.put(water, 1.0);
        TABLE.put(normal, normal_);

        Map<Type, Double> poison_ = new HashMap<>();
        poison_.put(bug, 1.0);
        poison_.put(dark, 1.0);
        poison_.put(dragon, 1.0);
        poison_.put(earth, 0.5);
        poison_.put(electric, 1.0);
        poison_.put(fairy, 2.0);
        poison_.put(fighting, 1.0);
        poison_.put(fire, 1.0);
        poison_.put(flying, 1.0);
        poison_.put(ghost, 0.5);
        poison_.put(grass, 2.0);
        poison_.put(ice, 1.0);
        poison_.put(normal, 1.0);
        poison_.put(poison, 0.5);
        poison_.put(psychic, 1.0);
        poison_.put(rock, 1.0);
        poison_.put(steel, 0.0);
        poison_.put(water, 1.0);
        TABLE.put(poison, poison_);

        Map<Type, Double> psychic_ = new HashMap<>();
        psychic_.put(bug, 1.0);
        psychic_.put(dark, 0.0);
        psychic_.put(dragon, 1.0);
        psychic_.put(earth, 1.0);
        psychic_.put(electric, 1.0);
        psychic_.put(fairy, 1.0);
        psychic_.put(fighting, 2.0);
        psychic_.put(fire, 1.0);
        psychic_.put(flying, 1.0);
        psychic_.put(ghost, 1.0);
        psychic_.put(grass, 1.0);
        psychic_.put(ice, 1.0);
        psychic_.put(normal, 1.0);
        psychic_.put(poison, 2.0);
        psychic_.put(psychic, 0.5);
        psychic_.put(rock, 1.0);
        psychic_.put(steel, 0.5);
        psychic_.put(water, 1.0);
        TABLE.put(psychic, psychic_);

        Map<Type, Double> rock_ = new HashMap<>();
        rock_.put(bug, 2.0);
        rock_.put(dark, 1.0);
        rock_.put(dragon, 1.0);
        rock_.put(earth, 0.5);
        rock_.put(electric, 1.0);
        rock_.put(fairy, 1.0);
        rock_.put(fighting, 0.5);
        rock_.put(fire, 2.0);
        rock_.put(flying, 2.0);
        rock_.put(ghost, 1.0);
        rock_.put(grass, 1.0);
        rock_.put(ice, 2.0);
        rock_.put(normal, 1.0);
        rock_.put(poison, 1.0);
        rock_.put(psychic, 1.0);
        rock_.put(rock, 1.0);
        rock_.put(steel, 0.5);
        rock_.put(water, 1.0);
        TABLE.put(rock, rock_);

        Map<Type, Double> steel_ = new HashMap<>();
        steel_.put(bug, 1.0);
        steel_.put(dark, 1.0);
        steel_.put(dragon, 1.0);
        steel_.put(earth, 1.0);
        steel_.put(electric, 0.5);
        steel_.put(fairy, 2.0);
        steel_.put(fighting, 1.0);
        steel_.put(fire, 0.5);
        steel_.put(flying, 1.0);
        steel_.put(ghost, 1.0);
        steel_.put(grass, 1.0);
        steel_.put(ice, 2.0);
        steel_.put(normal, 1.0);
        steel_.put(poison, 1.0);
        steel_.put(psychic, 1.0);
        steel_.put(rock, 2.0);
        steel_.put(steel, 0.5);
        steel_.put(water, 0.5);
        TABLE.put(steel, steel_);

        Map<Type, Double> water_ = new HashMap<>();
        water_.put(bug, 1.0);
        water_.put(dark, 1.0);
        water_.put(dragon, 0.5);
        water_.put(earth, 2.0);
        water_.put(electric, 1.0);
        water_.put(fairy, 1.0);
        water_.put(fighting, 1.0);
        water_.put(fire, 2.0);
        water_.put(flying, 1.0);
        water_.put(ghost, 1.0);
        water_.put(grass, 0.5);
        water_.put(ice, 1.0);
        water_.put(normal, 1.0);
        water_.put(poison, 1.0);
        water_.put(psychic, 1.0);
        water_.put(rock, 2.0);
        water_.put(steel, 1.0);
        water_.put(water, 0.5);
        TABLE.put(water, water_);


//        for (Map.Entry<Type, Map<Type, Double>> p1 : TABLE.entrySet()) {
//            ConsolePrinting.print(p1.getKey());
//
//        }
        String[] att = new String[]{"A","T","T","A","C","K"};
        int t = 0;
        String out;
//        for (int i = 0; i < 17; i++) {
//            out = "   ";
//            if(i % 2 == 0 && i > 0 && i < 15 && i != 8) {
//                out = " " + att[t] + " ";
//                t++;
//            }
//            println(ConsolePrinting.BG_RED, ConsolePrinting.BOLD, out);
//        }
        print(ConsolePrinting.BG_BLUE, ConsolePrinting.FG_BLACK, padCenter(156, ' ', new String[]{"D","E","F","E","N","D"}));
        ConsolePrinting.println();
        out = "   ";
        print(ConsolePrinting.BG_RED, ConsolePrinting.BOLD, out);
        print("        |");
        for (Map.Entry<Type, Double> p2 : ((Map<Type, Double>) (TABLE.get(TABLE.keySet().toArray()[0]))).entrySet()) {
            print(p2.getKey() + "|");
        }
        ConsolePrinting.println();
        int i = 0;
        for (Map.Entry<Type, Map<Type, Double>> p1 : TABLE.entrySet()) {
            out = "   ";
            print(ConsolePrinting.BG_RED, ConsolePrinting.BOLD, out);
            ConsolePrinting.println("-" + padToLength(152, "-------+"));
            if(i % 6 == 0) {
                out = " " + att[t] + " ";
                t++;
            }
            print(ConsolePrinting.BG_RED, ConsolePrinting.FG_WHITE, out);
            print(" " + p1.getKey() + "|");
            for(Map.Entry<Type, Double> p2 : p1.getValue().entrySet()) {
                Double atk = p2.getValue();
                String str = "  x" + atk.intValue() + ".  ";
                if (atk > 0 && atk < 1) {
                    str =  "  " + FG_RED + "x" + ("" + atk).replace("0", "") + RESET + "  ";
                }
                if( atk == 0) {
                    str = "  " + FG_WHITE + BG_BLACK + "x" + atk.intValue() + "." + RESET + "  ";
                }
                if( atk > 1) {
                    str = "  " + FG_GREEN + "x" + atk.intValue() + "." + RESET + "  ";
                }
                print(str + "|");
            }
            ConsolePrinting.println();
            i+=2;
        }
        print(ConsolePrinting.BG_RED, ConsolePrinting.BOLD, out);
        ConsolePrinting.println("-" + padToLength(152, "-------+"));
        
    }
}
