package Specialty;

public class myInt implements Arithmetic {
    int val;
    public myInt(int val) {
        this.val = val;
    }

    @Override
    public Arithmetic add(Arithmetic o) {
        return new myInt(this.getVal() + o.getVal());
    }

    @Override
    public Arithmetic sub(Arithmetic o) {
        return new myInt(this.getVal() - o.getVal());
    }

    @Override
    public int compareTo(Arithmetic o) {
        return (this.getVal() > o.getVal()) ? -1 : 1;
    }

    @Override
    public int getVal() {
        return this.val;
    }

    @Override
    public void setval(int val) {
        this.val = val;
    }

    public String toString() {
        return "" + this.getVal();
    }
}
