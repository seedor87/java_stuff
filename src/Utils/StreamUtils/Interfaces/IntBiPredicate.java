package Utils.StreamUtils.Interfaces;

import java.util.function.IntPredicate;

public interface IntBiPredicate extends IntPredicate {
    boolean test(int prev, int curr);

    @Override
    default boolean test(int value) {
        return true;
    }
}
