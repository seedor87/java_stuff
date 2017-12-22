package Utils.Timers;

public enum TimeUnit {
    /**
     * Enum for usable units of time that can be registered, the abstract methods be aware of this
     */
    NANO(       "nanoseconds",  "###,###,###,###"),
    MICRO(      "microseconds", "###,###,###.#"),
    MILLI(      "milliseconds", "###,###.##"),
    SECONDS(    "seconds",      "#,###.###"),
    MINUTES(    "mins",         "###.####"),
    HOURS(      "hrs",          "##.################");
    public String name;
    public String stringFormat;
    TimeUnit(String name, String format) {
        this.name = name;
        this.stringFormat = format;
    }
}
