package Pkm;

import Utils.Collections.Table;
import Utils.Console.Printing;
import Utils.Console.Special;

import java.util.HashMap;
import java.util.Map;

import static Pkm.Type.*;
import static Utils.Console.Printing.print;
import static Utils.StringUtils.padCenter;
import static Utils.StringUtils.padToLength;

public class Main {
    public static void main(String[] args) {

        Table<Type, Type, Double> TABLE = new Table<>();

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
//            Printing.print(p1.getKey());
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
//            println(Special.BG_RED, Special.BOLD, out);
//        }
        print(Special.BG_BLUE, Special.FG_BLACK, padCenter(156, ' ', new String[]{"D","E","F","E","N","D"}));
        Printing.println();
        out = "   ";
        print(Special.BG_RED, Special.BOLD, out);
        print("        |");
        for (Map.Entry<Type, Double> p2 : ((Map<Type, Double>) (TABLE.get(TABLE.keySet().toArray()[0]))).entrySet()) {
            print(p2.getKey() + "|");
        }
        Printing.println();
        int i = 0;
        for (Map.Entry<Type, Map<Type, Double>> p1 : TABLE.entrySet()) {
            out = "   ";
            print(Special.BG_RED, Special.BOLD, out);
            Printing.println("-" + padToLength(152, "-------+"));
            if(i % 6 == 0) {
                out = " " + att[t] + " ";
                t++;
            }
            print(Special.BG_RED, Special.FG_WHITE, out);
            print(" " + p1.getKey() + "|");
            for(Map.Entry<Type, Double> p2 : p1.getValue().entrySet()) {
                Double atk = p2.getValue();
                String str = "  x" + atk.intValue() + ".  ";
                if (atk > 0 && atk < 1) {
                    str =  "  " + Special.FG_RED + "x" + ("" + atk).replace("0", "") + Special.RESET + "  ";
                }
                if( atk == 0) {
                    str = "  " + Special.FG_WHITE + Special.BG_BLACK + "x" + atk.intValue() + "." + Special.RESET + "  ";
                }
                if( atk > 1) {
                    str = "  " + Special.FG_GREEN + "x" + atk.intValue() + "." + Special.RESET + "  ";
                }
                print(str + "|");
            }
            Printing.println();
            i+=2;
        }
        print(Special.BG_RED, Special.BOLD, out);
        Printing.println("-" + padToLength(152, "-------+"));
    }
}
