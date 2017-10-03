package ATOC;

import static Utils.ConsolePrinting.print;

public class Variable {
    public int value;

    Variable(int val) {
        this.value = val;
    }

    Variable() {
        this(0);
    }

    Variable(Variable v) {
        this(v.value);
    }

    public boolean incr() {
        this.value += 1;
        return true;
    }

    public boolean decr() {
        if (this.value - 1 > -1) {
            this.value -= 1;
            return true;
        }
        return false;
    }

    public String toString() {
        return "" + this.value;
    }
}


