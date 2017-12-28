package Pkm;

import Utils.Console.Special;

public enum Type {
    bug(        "Bug",      Abbrv.BUG,      Special.BG_BRIGHT_GREEN,    Special.FG_BLACK),
    dark(       "Dark",     Abbrv.DARK,     Special.BG_BLACK,           Special.FG_WHITE),
    dragon(     "Dragon",   Abbrv.DRAGN,    Special.BG_CYAN,            Special.FG_BLACK),
    earth(      "Earth",    Abbrv.EARTH,    Special.BG_YELLOW,          Special.FG_BLACK),
    electric(   "Electric", Abbrv.ELECT,    Special.BG_BRIGHT_YELLOW,   Special.FG_BLACK),
    fairy(      "Fairy",    Abbrv.FAIRY,    Special.BG_BRIGHT_RED,      Special.FG_BLACK),
    fighting(   "Fighting", Abbrv.FIGHT,    Special.BG_WHITE,           Special.FG_BLACK),
    fire(       "Fire",     Abbrv.FIRE,     Special.BG_RED,             Special.FG_BLACK),
    flying(     "Flying",   Abbrv.FLYNG,    Special.BG_BRIGHT_BLUE,     Special.FG_BLACK),
    ghost(      "Ghost",    Abbrv.GHOST,    Special.RESET,              Special.FG_DARK_GRAY),
    grass(      "Grass",    Abbrv.GRASS,    Special.BG_GREEN,           Special.FG_BLACK),
    ice(        "Ice",      Abbrv.ICE,      Special.BG_BRIGHT_CYAN,     Special.FG_BLACK),
    normal(     "Normal",   Abbrv.NORM,     Special.RESET,              Special.RESET),
    poison(     "Poison",   Abbrv.POISN,    Special.BG_MAGENTA,         Special.FG_BLACK),
    psychic(    "Psychic",  Abbrv.PSYCH,    Special.BG_BRIGHT_MAGENTA,  Special.FG_BLACK),
    rock(       "Rock",     Abbrv.ROCK,     Special.BG_DARK_GRAY,       Special.FG_BLACK),
    steel(      "Steel",    Abbrv.STEEL,    Special.BG_GRAY,            Special.FG_BLACK),
    water(      "Water",    Abbrv.WATER,    Special.BG_BLUE,            Special.FG_BLACK);

    public enum Abbrv {
        BUG, DARK, DRAGN, EARTH,
        ELECT, FAIRY, FIGHT, FIRE,
        FLYNG, GHOST, GRASS, ICE, NORM,
        POISN, PSYCH, ROCK, STEEL, WATER
    }
    
    private Abbrv abbrv;
    private String name;
    private Special fgColor;
    private Special bgColor;
    Type(String name, Abbrv abbrv, Special bgColor, Special fgColor) {
        this.name = name;
        this.abbrv = abbrv;
        this.bgColor = bgColor;
        this.fgColor = fgColor;
    }
    @Override
    public String toString() {
        if(abbrv.toString().length() < 4) {
            return "" + bgColor + fgColor + "  " + abbrv + "  " + Special.RESET;
        }
        if(abbrv.toString().length() < 5) {
            return "" + bgColor + fgColor + "  " + abbrv + " " + Special.RESET;
        }
        return "" + bgColor + fgColor + " " + abbrv + " " + Special.RESET;
    }
}
