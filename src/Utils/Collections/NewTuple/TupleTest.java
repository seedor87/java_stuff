package Utils.Collections.NewTuple;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static Utils.Console.Printing.*;

public class TupleTest {

    @Test
    public void testName() throws Exception {

        Tuple2<? extends Number, int[]> t = Tuple.valueOf(0, new int[]{0,0,0});

        t = Tuple.valueOf(0f, new int[]{1,2,3});
        println(t.apply(1));

        Tuple4<Integer, Character, String, Boolean> u = Tuple.valueOf(1, 'b', "three", true);
        int i0 = u._0;
        char c1 = u._1;
        String s2 = u._2;
        boolean s3 = u._3;

        Tuple2<Integer, Boolean> a = Tuple.valueOf(0, false);
        Tuple2<? extends Number, Boolean> b = a;
        assertEquals("a == b", a, b);

        Tuple2<Object, Object> z = Tuple.valueOf(null, null);
        Object t0 = z.apply(0);
        Tuple m = Tuple.valueOf(0, false);
        Object t1 = z.apply(0);
        println(m);

        Tuple2<Integer, Boolean> x = Tuple.valueOf(0, false);
        println(x);
        Tuple3<Integer, Float, Boolean> y = Tuple.valueOf(0, 0f, false);
        println(y);
        Tuple4<Integer, Integer, Integer, Integer> tup4 = new Tuple4(1,2,3,4);
        println(tup4);

        println(tup4 ._0 + tup4._1 + tup4._2 + tup4._3);
        int i = (Integer) tup4.apply(0);

        Tuple5<? extends Number,
                ? extends Number,
                ? extends Number,
                ? extends Number,
                ? extends Number> five = Tuple.valueOf(1, 2f, 3d, 4L, null);
        println(five);
    }
}
