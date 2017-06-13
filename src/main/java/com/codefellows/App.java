package com.codefellows;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        GenericList<Mammal> myGenericList = new GenericList<>(new ArrayList<>());
        processList(myGenericList);

        Person p = new Person.Builder("Brad")
                             .age(25)
                             .homeTown("Knoxville, TN")
                             .build();

        List<Mammal> mammals = new ArrayList<>();

        mammals.add(new Mammal("Brad"));
        mammals.add(new Mammal("Kevin"));
        mammals.add(new Mammal("Cathy"));

        Stream<Mammal> mammalStream = mammals.stream();

        mammalStream = mammalStream.filter(m -> m.getName().equals("Brad"));
        mammalStream.forEach(App::printAnimal);

        Map<String, Integer> nameAgeMap = new HashMap<>();

        nameAgeMap.put("Brad", 25);
        nameAgeMap.put("Cathy", 25);

        List<Object> names = nameAgeMap.entrySet().stream().collect(Collectors.toList());

        nameAgeMap.entrySet().stream()
                  .filter(h -> h.getValue() == 25)
                  .forEach(h -> System.out.println(String.format("key: %s, value: %d", h.getKey(), h.getValue())));

        Stream.of("Brad", "Cathy", "Kevin")
                .filter(n -> n.equals("Cathy"))
                .forEach(n -> System.out.println(String.format("My name is %%s %s %d", n, 25)));

        StringBuilder sb = new StringBuilder("Hi, ");
        sb.append("my name is");
        sb.append(" Brad" + " Farr");

        System.out.println(sb);
    }



    public static void printAnimal(Animal a) {
        System.out.println(a);
    }

    public static void processList(GenericList<? super Mammal> list) {

    }
}
