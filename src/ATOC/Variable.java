package ATOC;

public class Variable {
    public int value;

    Variable(int val) {
        this.value = val;
    }

    Variable(boolean val) {
        this.value = (val) ? 1 : 0;
    }

    Variable() {
        this(0);
    }

    Variable(Variable v) {
        this(v.value);
    }

    public Variable incr() {
        this.value += 1;
        return new Variable(this.value);
    }

    public Variable decr() {
        if (this.value - 1 > -1) {
            this.value -= 1;
        }
        return new Variable(this.value);
    }

    public boolean equals(Object o) {
        return this.value == ((Variable) o).value;
    }

    public String toString() {
        return "" + this.value;
    }
}


