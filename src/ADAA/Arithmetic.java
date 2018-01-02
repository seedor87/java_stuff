package ADAA;

public interface Arithmetic {

    Arithmetic add(Arithmetic o);
    Arithmetic sub(Arithmetic o);
    int compareTo(Arithmetic o);
    int getVal();
    void setval(int val);
}
