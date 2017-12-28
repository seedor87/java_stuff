package Utils.Console;

public enum Special implements PrintableSpecial {
    NEW_LINE(           "\n"),
    CARR_RET(           "\r"),

    RESET(              "\u001B[0m"),
    BOLD(               "\u001B[1m"),
    ITAL(               "\u001B[3m"),
    UNDER(              "\u001B[4m"),
    BLINK(              "\u001B[5m"),
    RAPID_BLINK(        "\u001B[6m"),
    INVER(              "\u001B[7m"),

    FG_BLACK(           "\u001B[30m"),
    FG_RED(             "\u001B[31m"),
    FG_GREEN(           "\u001B[32m"),
    FG_YELLOW(          "\u001B[33m"),
    FG_BLUE(            "\u001B[34m"),
    FG_MAGENTA(         "\u001B[35m"),
    FG_CYAN(            "\u001B[36m"),
    FG_GRAY(            "\u001B[37m"),

    BG_BLACK(           "\u001B[40m"),
    BG_RED(             "\u001B[41m"),
    BG_GREEN(           "\u001B[42m"),
    BG_YELLOW(          "\u001B[43m"),
    BG_BLUE(            "\u001B[44m"),
    BG_MAGENTA(         "\u001B[45m"),
    BG_CYAN(            "\u001B[46m"),
    BG_GRAY(            "\u001B[47m"),

    FG_DARK_GRAY(       "\u001B[90m"),
    FG_BRIGHT_RED(      "\u001B[91m"),
    FG_BRIGHT_GREEN(    "\u001B[92m"),
    FG_BRIGHT_YELLOW(   "\u001B[93m"),
    FG_BRIGHT_BLUE(     "\u001B[94m"),
    FG_BRIGHT_MAGENTA(  "\u001B[95m"),
    FG_BRIGHT_CYAN(     "\u001B[96m"),
    FG_WHITE(           "\u001B[97m"),

    BG_DARK_GRAY(       "\u001B[100m"),
    BG_BRIGHT_RED(      "\u001B[101m"),
    BG_BRIGHT_GREEN(    "\u001B[102m"),
    BG_BRIGHT_YELLOW(   "\u001B[103m"),
    BG_BRIGHT_BLUE(     "\u001B[104m"),
    BG_BRIGHT_MAGENTA(  "\u001B[105m"),
    BG_BRIGHT_CYAN(     "\u001B[106m"),
    BG_WHITE(           "\u001B[107m");

    private String str;
    Special(String str) { this.str = str; }
    @Override
    public String toString() { return this.str; }
}