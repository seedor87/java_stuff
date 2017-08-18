import java.lang.reflect.Method;

import static myUtils.ConsolePrinting.println;

public class test {
    static class A {
        private final Class cl;

        A(Class cl) {
            this.cl = cl;
        }

        void foo(Object ... params) throws Exception {
            Object obj = cl.newInstance();
            for (Method m : cl.getMethods()) {
                if (m.getName().equals("bar")) {
                    try {
                        m.invoke(obj, params);
                        return;
                    }
                    catch(IllegalArgumentException ex) {} // try next overload
                }
            }
            throw new IllegalArgumentException();
        }
    }

    static class B {
        public void bar() {
            System.out.println("Got nothing");
        }

        public void bar(double a, double b) {
            System.out.println("Got doubles");
        }

        public void bar(int a, int b) {
            System.out.println("Got: " + a + " and " + b);
        }

        public void bar(String word) {
            System.out.println(word);
        }
        public void bar(int a, String b) {
            for (int i = 0; i < a; i++) {
                println(b);
            }
        }
    }

    public static void main(String argv[]) throws Exception {
        new A(B.class).foo(1,2);
        new A(B.class).foo();
        new B().bar(1,2);
        new A(B.class).foo("Hello");
        new A(B.class).foo(3, "hooray");
    }
}