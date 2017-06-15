package com.codefellows;

import java.io.*;
import java.util.*;
import java.util.function.DoubleFunction;
import java.util.function.Supplier;
import java.util.function.ToDoubleFunction;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        List<Person> people = new ArrayList<>();

        people.add(new Person.Builder("Brad").age(25).build());
        people.add(new Person.Builder("Luay").age(26).build());
        people.add(new Person.Builder("Cathy").age(25).build());
        people.add(new Person.Builder("Cathy").age(31).build());
        people.add(new Person.Builder("Brian").age(28).build());
        people.add(new Person.Builder("Mikleane").age(29).build());
        people.add(new Person.Builder("Liz").age(30).build());

        people.stream().filter(App::testPeople).forEach(p -> System.out.println(p));

        people.stream().forEach(App::celebrateBirthday);

        people.stream().forEach(p -> {
            System.out.println(p);
        });

        //Stream<Integer> ages = people.stream().map(App::getAge);

        List<Integer> ages = people.stream().map(App::getAge).collect(Collectors.toList());

        for (int a : ages) {
            System.out.println(a);
        }

        Supplier<Person> personSupplier = () -> createPerson("Kevin", 30);
        people.add(personSupplier.get());

        ToDoubleFunction<Person> getTripleAge = p -> triplePersonAge(p);
        DoubleFunction<String> getAgeDivideByThree = d -> divideAgeByThree(d);

        people.stream().forEach(p -> {
            double tAge = getTripleAge.applyAsDouble(p);
            System.out.println(tAge);

            String age = getAgeDivideByThree.apply(tAge);
            System.out.println(age);
        });

        UnaryOperator<String> appendDashes = s -> "-" + s + "-";

        List<String> nameDashes = people.stream()
                .map(p -> p.getName())
                .map(appendDashes)
                .peek(p -> System.out.println(p))
                .collect(Collectors.toList());

        long numPeople = people.stream().count();

        Optional<Person> cathy = people.stream().filter(p -> p.getName().equals("Cathy")).findFirst();

        if (cathy.isPresent()) {
            //System.out.println(cathy.get());
        }

        Optional<Person> parallelCathy = people.parallelStream()
                .filter(p -> p.getName().equals("Cathy")).peek(p -> {
                    p.setName("Cathy");
                }).findAny();

        if (parallelCathy.isPresent()) {
            System.out.println(parallelCathy.get());
        }

        Optional<Person> brad = Optional.of(new Person.Builder("Brad").build());

        if (brad.isPresent()) {
            System.out.println(brad.get());
        }

        OptionalInt nullSafeInt = OptionalInt.of(5);
        Optional<Integer> nullSafeInteger = Optional.of(5);

        System.out.println();
        people.stream().sorted(App::comparePeople).forEach(p -> System.out.println(p));

        Comparator<Person> nameComparator = Comparator.comparing(p -> p.getName());
        Comparator<Person> ageComparator = Comparator.comparing(p -> p.getAge(), Comparator.reverseOrder());

        System.out.println();
        people.stream().sorted(nameComparator.thenComparing(ageComparator)).forEach(p -> System.out.println(p));

        //System.out.println();
        //people.stream().sorted().forEach(p -> System.out.println(p));

//        String str = "my string";
//        char[] cstr = "my c string".toCharArray();
//
//        char c = 'c';
//        //Character npc = c;
//
//        Stack<Character> characterStack = new Stack<>();
//        characterStack.push(c);
//
//        int x = 5;
//        List<Integer> myInts = new ArrayList<>();
//
//        Float f = new Float(5.0);
//
//        myInts.add(x);

        try (BufferedReader br = new BufferedReader(new FileReader("src/main/resources/input.txt"));
             BufferedWriter bw = new BufferedWriter(new FileWriter("src/main/resources/output.txt"))) {

            String line;

            while ((line = br.readLine()) != null) {
                System.out.println(line);

                bw.write(line);
                bw.newLine();
            }
        } catch (FileNotFoundException ex) {
            System.out.println("input.txt not found");
        } catch (IOException ex) {
            System.out.println("i/o error: " + ex.getMessage());
        }
    }

    private static int comparePeople(Person p1, Person p2) {
        return p1.getName().compareTo(p2.getName());
    }

    private static String divideAgeByThree(double age) {
        return String.valueOf(age / 3.1);
    }

    private static double triplePersonAge(Person p) {
        return 3.1 * p.getAge();
    }

    //Supplier example
    private static Person createPerson(String name, int age) {
        return new Person.Builder(name).age(age).build();
    }

    //Function example
    private static Integer getAge(Person p) {
        return p.getAge();
    }

    //Consumer example
    private static void celebrateBirthday(Person p) {
        p.celebrateBirthday();
    }

    //Predicate example
    private static boolean testPeople(Person p) {
        return p.getName().equals("Cathy");
    }
}
