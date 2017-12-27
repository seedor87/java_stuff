package RandomStuff;

import java.util.Arrays;
import java.util.IntSummaryStatistics;
import java.util.List;

import static Utils.ConsolePrinting.*;

public class JavaStreams {

    public static void main(String args[]) {
        List<Integer> numbers = Arrays.asList(3,2,2,3,7,3,5);
        IntSummaryStatistics stats = numbers.stream().mapToInt((x) -> x).summaryStatistics();

        println(stats.getMax());
        println(stats.getMin());
        println(stats.getAverage());
        println(stats.getCount());
        println(stats.getSum());

        List<String> strings = Arrays.asList("a","b","c");
        strings
            .stream()
            .map((x) ->x.toUpperCase())
            .map(s -> " " + s + " ")
            .forEachOrdered(System.out::print);

        strings.stream()
                .findFirst()
                .ifPresent(System.out::print);

        strings.stream()
                .filter(s -> {
                    System.out.println("filter: " + s);
                    return true;
                })
        .forEachOrdered(s -> println(s));
    }
}
