package Utils;

public class Tuple {

    public Object one;
    public Object two;

    public Tuple(Object one, Object two) {
        this.one = one;
        this.one = two;
    }

    public Object getOne() {
        return this.one;
    }

    public Object getTwo() {
        return this.two;
    }

    public void setOne(Object one) {
        this.one = one;
    }

    public void setTwo(Object two) {
        this.two = two;
    }
}
