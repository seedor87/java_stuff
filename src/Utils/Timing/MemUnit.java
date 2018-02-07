package Utils.Timing;

public enum MemUnit {
    BYTE("Bytes",   Math.pow(1024, 0),  "##,###,###,###.#"),
    KILO("KB",      Math.pow(1024, 1),  "##,###,###.####"),
    MEGA("MB",      Math.pow(1024, 2),  "##,###.########"),
    GIGA("GB",      Math.pow(1024, 3),  "###.############"),
    TERA("TB",      Math.pow(1024, 4),  "##.################");
    public String name;
    public double factor;
    public String stringFormat;
    MemUnit(String name, double factor, String stringFormat) {
        this.name = name;
        this.factor = factor;
        this.stringFormat = stringFormat;
    }
}
