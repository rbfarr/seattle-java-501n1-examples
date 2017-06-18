package com.codefellows;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.NumberFormat;
import java.util.Locale;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.concurrent.*;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class App {
    public static void main(String[] args) throws IOException {
        Path p1 = Paths.get(args[0]);

        System.out.format("getFileName: %s%n", p1.getFileName());
        System.out.format("getParent: %s%n", p1.getParent());
        System.out.format("getNameCount: %d%n", p1.getNameCount());
        System.out.format("getRoot: %s%n", p1.getRoot());
        System.out.format("isAbsolute: %b%n", p1.isAbsolute());
        System.out.format("toAbsolutePath: %s%n", p1.toAbsolutePath());
        System.out.format("toURI: %s%n", p1.toUri());

        System.out.format("normalized path: %s%n", p1.normalize());
        System.out.format("sub path: %s%n", p1.subpath(1, 3));
        System.out.format("sub directory: %s%n", p1.resolve("more-resources"));
        System.out.format("sub directory (absolute): %s%n", p1.resolve("D:\\src"));

        //Files.createSymbolicLink(p1.getParent().resolve("input-link.txt"), p1);

        System.out.format("isReadable: %s%n", Files.isReadable(p1));
        System.out.format("isWritable: %s%n", Files.isWritable(p1));

        if (Files.notExists(p1.getParent().resolve("foo"))) {
            Files.createDirectory(p1.getParent().resolve("foo"));
        }

        try {
            Files.createDirectory(p1.getParent().resolve("foo"));
        } catch (FileAlreadyExistsException e) {
            System.out.println("foo already exists");
        }

        Files.createDirectories(p1.getParent().resolve("bar/a/b/c/d/e/f/g"));
        Files.deleteIfExists(p1.getParent().resolve("bar/a/b/c/d/e/f/g"));

        System.out.println();
        recursiveListFiles(p1.getParent());
        System.out.println();

        try (Stream<Path> files = Files.walk(p1.getParent())) {
            files.distinct().forEach(f -> {
                System.out.println(f);

                if (Files.isDirectory(f)) {
                    try {
                        recursiveListFiles(f);
                    } catch (IOException e) {
                        System.out.println("IOException occurred");
                    }
                }
            });
        }

        System.out.println();

        try (BufferedReader br = new BufferedReader(new FileReader(p1.toString()))) {
//            String line;
//
//            while ((line = br.readLine()) != null) {
//                System.out.println(line);
//            }

            br.lines().forEach(System.out::println);
        }

        System.out.println();

        try (Stream<String> lines = Files.lines(p1)) {
            lines.forEach(System.out::println);
        }

        System.out.println("--concurrency--");

        ExecutorService es = Executors.newCachedThreadPool();
        CyclicBarrier cb = new CyclicBarrier(2);

        es.execute(() -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                System.out.println(e.getMessage());
            }

            IntStream.rangeClosed(1, 10).forEach(System.out::println);

            try {
                cb.await();
            } catch (InterruptedException e) {
                System.out.println(e.getMessage());
            } catch (BrokenBarrierException e) {
                System.out.println(e.getMessage());
            }
        });

        es.execute(() -> {
            try {
                cb.await();
            } catch (InterruptedException e) {
                System.out.println(e.getMessage());
            } catch (BrokenBarrierException e) {
                System.out.println(e.getMessage());
            }

            IntStream.rangeClosed(11, 20).forEach(System.out::println);
        });

        Future<Integer> integerFuture = es.submit(() -> {
//            try {
//                Thread.sleep(5000);
//            } catch (InterruptedException e) {
//                System.out.println(e.getMessage());
//            }

            if ((new Random()).nextBoolean())
                throw new Exception("Something went wrong");

            return IntStream.rangeClosed(1, 100).reduce(0, (a, b) -> a + b);
        });

        SharedResource sharedResource = new SharedResource();
        CyclicBarrier cb2 = new CyclicBarrier(5);

        System.out.println();
        es.execute(() -> {
            try {
                cb2.await();
            } catch (InterruptedException e) {
                System.out.println(e.getMessage());
            } catch (BrokenBarrierException e) {
                System.out.println(e.getMessage());
            }

            for (int i = 0; i < 10; i++) {
                System.out.println(sharedResource.getAndIncrementI());
            }
        });
        es.execute(() -> {
            try {
                cb2.await();
            } catch (InterruptedException e) {
                System.out.println(e.getMessage());
            } catch (BrokenBarrierException e) {
                System.out.println(e.getMessage());
            }

            for (int i = 0; i < 10; i++) {
                System.out.println(sharedResource.getAndIncrementI());
            }
        });
        es.execute(() -> {
            try {
                cb2.await();
            } catch (InterruptedException e) {
                System.out.println(e.getMessage());
            } catch (BrokenBarrierException e) {
                System.out.println(e.getMessage());
            }

            for (int i = 0; i < 10; i++) {
                System.out.println(sharedResource.getAndIncrementI());
            }
        });
        es.execute(() -> {
            try {
                cb2.await();
            } catch (InterruptedException e) {
                System.out.println(e.getMessage());
            } catch (BrokenBarrierException e) {
                System.out.println(e.getMessage());
            }

            for (int i = 0; i < 10; i++) {
                System.out.println(sharedResource.getAndIncrementI());
            }
        });
        es.execute(() -> {
            try {
                cb2.await();
            } catch (InterruptedException e) {
                System.out.println(e.getMessage());
            } catch (BrokenBarrierException e) {
                System.out.println(e.getMessage());
            }

            for (int i = 0; i < 10; i++) {
                System.out.println(sharedResource.getAndIncrementI());
            }
        });
        System.out.println();

        SharedResource sr = new SharedResource();

        es.execute(() -> {
            System.out.println("y: " + sr.getAndIncrementY());
        });

        es.execute(() -> {
            System.out.println("x: " + sr.getAndIncrementX());
        });

        System.out.println();
        CyclicBarrier cb3 = new CyclicBarrier(2);

        es.execute(() -> {
            try {
                cb3.await();
            } catch (InterruptedException e) {
                System.out.println(e.getMessage());
            } catch (BrokenBarrierException e) {
                System.out.println(e.getMessage());
            }

            for (int i = 0; i < 10; i++) {
                System.out.println("z: " + sr.getAndIncrementZ());
            }
        });

        es.execute(() -> {
            try {
                cb3.await();
            } catch (InterruptedException e) {
                System.out.println(e.getMessage());
            } catch (BrokenBarrierException e) {
                System.out.println(e.getMessage());
            }

            for (int i = 0; i < 10; i++) {
                System.out.println("z: " + sr.getAndIncrementZ());
            }
        });

        System.out.println();

        Future downloadImageFuture = es.submit(() -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                System.out.println(e.getMessage());
            }

            //download image, make network call, etc.
            //return new Image(imageData);
        });

        ForkJoinPool pool = new ForkJoinPool();
        int[] data = FindMaxTask.getData();

        FindMaxTask task = new FindMaxTask(0, data.length-1, 10);
        Integer result = pool.invoke(task);

        System.out.println("fork-join result: " + result);

//        System.out.println();
//
//        (new Thread(() -> {
//            IntStream.rangeClosed(21, 30).forEach(System.out::println);
//        })).run();

        CyclicBarrier cb4 = new CyclicBarrier(2);

        es.execute(() -> {
            try {
                cb4.await();
            } catch (InterruptedException e) {
                System.out.println(e.getMessage());
            } catch (BrokenBarrierException e) {
                System.out.println(e.getMessage());
            }

            IntStream.range(1, 1000000000).parallel().reduce(0, (x, y) -> x + y);
            System.out.println("parallel reduce finished");
        });

        es.execute(() -> {
            try {
                cb4.await();
            } catch (InterruptedException e) {
                System.out.println(e.getMessage());
            } catch (BrokenBarrierException e) {
                System.out.println(e.getMessage());
            }

            IntStream.range(1, 1000000000).map(e -> 1).sum();
            System.out.println("map reduce finished");
        });

        try {
            es.shutdown();
            //es.awaitTermination(5, TimeUnit.SECONDS);

            int val = integerFuture.get();
            System.out.println(val);

            downloadImageFuture.get();
            System.out.println("Image downloaded");
        } catch (InterruptedException | ExecutionException e){
            System.out.println(e.getMessage());
        }

        Locale usLocale = Locale.US;
        Locale frLocale = Locale.FRANCE;
        Locale zhLocale = new Locale("zh", "CN");
        Locale ruLocale = new Locale("ru", "RU");
        Locale currentLocale = Locale.getDefault();

        ResourceBundle messages = ResourceBundle.getBundle("MessageBundle", frLocale);
        System.out.println(messages.getString("menu1"));

        Locale loc = Locale.UK;
        NumberFormat nf = NumberFormat.getCurrencyInstance(loc);
        double money = 1_000_000.00d;

        System.out.println("Money: " + nf.format(money) + " in Locale: " + loc);
    }

    private static void recursiveListFiles(Path p) throws IOException {
        try (Stream<Path> files = Files.list(p)) {
            files.forEach(f -> {
                System.out.println(f);

                if (Files.isDirectory(f)) {
                    try {
                        recursiveListFiles(f);
                    } catch (IOException e) {
                        System.out.println("IOException occurred");
                    }
                }
            });
        }
    }
}
