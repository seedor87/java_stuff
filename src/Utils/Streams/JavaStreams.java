package Utils.Streams;

import Utils.Console.Printing;

import java.io.Console;
import java.util.*;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static Utils.Console.Printing.*;

public class JavaStreams {

    public static void main(String args[]) {
        List<Integer> numbers = Arrays.asList(3, 2, 2, 3, 7, 3, 5);
        IntSummaryStatistics stats = numbers.stream().mapToInt((x) -> x).summaryStatistics();

        println(stats.getMax());
        println(stats.getMin());
        println(stats.getAverage());
        println(stats.getCount());
        println(stats.getSum());

        List<String> strings = Arrays.asList("a", "b", "c");
        strings
                .stream()
                .map((x) -> x.toUpperCase())
                .map(s -> " " + s + " ")
                .forEachOrdered(Printing::print);

        strings.stream()
                .findFirst()
                .ifPresent(Printing::print);

        strings.stream()
                .filter(s -> {
                    Printing.println("filter: " + s);
                    return true;
                })
                .forEachOrdered(s -> println(s));

        List<Person> persons =
                Arrays.asList(
                        new Person("Max", 18),
                        new Person("Peter", 23),
                        new Person("Pamela", 23),
                        new Person("David", 12));

        Map<Integer, List<Person>> personsByAge = persons
                .stream()
                .collect(Collectors.groupingBy(p -> p.age));

        personsByAge
                .forEach((age, p) -> Printing.format("age %s: %s\n", age, p));

        Double averageAge = persons
                .stream()
                .collect(Collectors.averagingInt(p -> p.age));
        println(averageAge);

        IntSummaryStatistics ageSummary =
                persons
                    .stream()
                    .collect(Collectors.summarizingInt(p -> p.age));
        println(ageSummary);

        String phrase = persons
                .stream()
                .filter(p -> p.age >= 18)
                .map(p -> p.name)
                .collect(Collectors.joining( " and ", "In Germany ", " are of legal age."));
        println(phrase);

        Map<Integer, String> map = persons
                .stream()
                .collect(Collectors.toMap(
                        p -> p.age,
                        p -> p.name,
                        (name1, name2) -> name1 + ";" + name2));
        println(map);

        Collector<Person, StringJoiner, String> personNameCollector =
                Collector.of(
                        () -> new StringJoiner(" | "),  //supplier
                        (j, p) -> j.add(p.name.toUpperCase()),  //accumulator
                        (j1, j2) -> j1.merge(j2),               //combiner
                        StringJoiner::toString);                //finisher
        String names = persons
                .stream()
                .collect(personNameCollector);
        println(names);

        persons
                .stream()
                .reduce((p1, p2) -> p1.age > p2.age ? p1 : p2)
                .ifPresent(Printing::println);

        Integer ageSum = persons
                .stream()
                .reduce(0,
                        (sum, p) -> sum += p.age, (sum1, sum2) -> sum1 + sum2);
        println(ageSum);

        ageSum = persons
                .stream()
                .reduce(0,
                        (sum, p) -> {
                            Printing.format("accumulator: sum=%s; person=%s\n", sum, p);
                            return sum += p.age;
                        },
                        (sum1, sum2) -> {
                            Printing.format("combiner: sum1=%s; sum2=%s\n", sum1, sum2);
                            return sum1 + sum2;
                        });

        persons
                .parallelStream()
                .reduce(0,
                        (sum, p) -> {
                            Utils.Console.Printing.format("accumulator: sum=%s; person=%s [%s]\n",
                                    sum, p, Thread.currentThread().getName());
                            return sum += p.age;
                        },
                        (sum1, sum2) -> {
                            Utils.Console.Printing.format("combiner: sum1=%s; sum2=%s [%s]\n",
                                    sum1, sum2, Thread.currentThread().getName());
                            return sum1 + sum2;
                        });


        IntStream.iterate(1, i -> i *2)
                .limit(4)
                .forEach(Utils.Console.Printing::println);


        Stream<Color> s = IntStream.range(0,5)
                .mapToObj(i -> Color.values()[i%3]);
        s.forEach(Utils.Console.Printing::println);

        Stream<Double> ds = IntStream.range(0,5).mapToDouble(Math::sin).boxed();
        ds.forEach(Utils.Console.Printing::println);
    }
}

enum Color {
    RED, GREEN, BLUE;
    static public Color getColor(int i) {
        return Color.values()[i%3];
    }
}

class Person {
    String name;
    int age;

    Person(String name, int age) {
        this.name = name;
        this.age = age;
    }

    @Override
    public String toString() {
        return name;
    }
}




