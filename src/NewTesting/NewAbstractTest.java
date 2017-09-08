package NewTesting;

import myUtils.Tuple;

public abstract class NewAbstractTest<T extends Object> {

    protected interface VarArgs<T> {
        Tuple<T> varArgs(T... params);
    }
    public VarArgs<Object> exe = (Object...params) -> runThis(params);

    public abstract <T> Tuple<T> test(T... args);
    public abstract <T> Tuple<T> test(VarArgs<T> lam, T... args);
    public abstract <T> Tuple<T> runThis(T... params);
}
