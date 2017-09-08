package Testing;

import Utils.Collections.Tuple;

import java.lang.reflect.Method;

public abstract class AbstractTest<T extends Object> {

    protected Class cl;
    protected String method;
    protected Object obj;
    protected Method m;

    protected AbstractTest(Class cl, String method) {
        this.cl = cl;
        this.method = method;
    }

    protected  <T> Tuple exe(T... params) throws Exception{
        try {
            this. obj = this.cl.newInstance();
        } catch (Exception ex) {
            throw ex;
        }
        for (Method m : this.cl.getMethods()) {
            if (m.getName().equals(this.method)) {
                try {
                    this.m = m;
                    return wrapperFunc(params);
                }
                catch(IllegalArgumentException ex) {} // try next overload
            }
        }
        throw new IllegalArgumentException("no matching argument list found");
    }

    abstract <T> Tuple wrapperFunc(T... params) throws Exception;

    protected <T> Object myMethod(T... params) throws Exception{
        return m.invoke(obj, params);
    }
}
