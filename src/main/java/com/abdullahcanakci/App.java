package com.abdullahcanakci;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class App {
    static final int NUMBER_OF_THREADS = 8;
    // No checks for negative numbers.
    // No odd numbers, Problems might occur.
    static final int NUMBER_OF_ELEMENTS = 10000000;

    public static void main(String[] args) {
        System.out.println("Thread performance checker");

        ArrayList<Node> list = new ArrayList<Node>(NUMBER_OF_ELEMENTS);
        fillArray(list);

        List<List<Node>> divs = divideArray(list, NUMBER_OF_THREADS);

        runEquality(divs);
        runPrime(divs);

    }

    public static void runEquality(List<List<Node>> list) {
        ArrayList<EqualityRunner> er = new ArrayList<>();
        ArrayList<Integer> runtimes = new ArrayList<>();
        int max = 0;

        for (int j = 0; j < 10; j++) {
            for (int i = 0; i < list.size(); i++) {
                er.add(new EqualityRunner(list.get(i)));
            }

            ExecutorService es = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

            long startTime = System.nanoTime();
            for (int i = 0; i < list.size(); i++) {
                es.execute(er.get(i));
            }

            es.shutdown();
            try {
                if (es.awaitTermination(1, TimeUnit.MINUTES)) {

                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            
            //max = Collections.max(er.stream().map(e -> e.result.value).collect(Collectors.toList()));
            // Bu satır her ne kadar PrimeRunner'da hata vermiyor olsada burada veriyor.

            int runtime = (int) ((System.nanoTime() - startTime) / 1000000);
            runtimes.add(runtime);
            System.out.print(".");
        }
        System.out.println("");

        printResult("Largest Test", runtimes);
    }

    public static void runPrime(List<List<Node>> list) {
        ArrayList<PrimeRunner> pr = new ArrayList<>();
        ArrayList<Integer> runtimes = new ArrayList<>();
        int max = 0;
        
        for (int j = 0; j < 10; j++) {
            for (int i = 0; i < list.size(); i++) {
                pr.add(new PrimeRunner(list.get(i)));
            }

            ExecutorService es = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

            long startTime = System.nanoTime();
            for (int i = 0; i < list.size(); i++) {
                es.execute(pr.get(i));
            }

            es.shutdown();
            try {
                if (es.awaitTermination(1, TimeUnit.MINUTES)) {

                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            max = Collections.max(pr.stream().map(e -> e.result.value).collect(Collectors.toList()));

            int runtime = (int) ((System.nanoTime() - startTime) / 1000000);
            runtimes.add(runtime);
            System.out.print(".");
        }
        System.out.println("");

        printResult("Prime Test", runtimes);
    }
    
    public static void printResult(String title, List<Integer> runtimes) {
        int avg = 0;
        for (int i = 0; i < runtimes.size(); i++) {
            avg += runtimes.get(i);
        }
        avg = avg / runtimes.size();

        System.out.println(title);
        System.out.println("Minimum time: " + Collections.min(runtimes) + "ms");
        System.out.println("Maximum time: " + Collections.max(runtimes) + "ms");
        System.out.println("Average time: " + avg + "ms");
    }

    public static void fillArray(ArrayList<Node> protoList) {
        Random r = new Random();
        for (int i = 0; i < NUMBER_OF_ELEMENTS; i++) {
            protoList.add(new Node(r.nextInt(50000000)));
        }
    }

    /**
     * Divides provided list into sublists.
     */
    public static List<List<Node>> divideArray(List<Node> protoList, int number) {

        if (number == 1) {
            return Arrays.asList(protoList);
        }

        return Stream
                .concat(divideArray(protoList.subList(0, protoList.size() / 2), number / 2).stream(),
                        divideArray(protoList.subList(protoList.size() / 2, protoList.size()), number / 2).stream())
                .collect(Collectors.toList());
    }
}
