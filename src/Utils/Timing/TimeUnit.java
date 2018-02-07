package Utils.Timing;

public enum TimeUnit {
    /**
     * Enum for usable units of time that can be registered, the abstract methods be aware of this
     */
    NANOSECONDS(    "nanoseconds",  "###,###,###,###"),
    MICROSECONDS(   "microseconds", "###,###,###.#"),
    MILLISECONDS(   "milliseconds", "###,###.##"),
    SECONDS(        "seconds",      "#,###.###"),
    MINUTES(        "mins",         "###.####"),
    HOURS(          "hrs",          "##.################");
    public String name;
    public String stringFormat;
    TimeUnit(String name, String format) {
        this.name = name;
        this.stringFormat = format;
    }
}
