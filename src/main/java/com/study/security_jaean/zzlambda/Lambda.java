package com.study.security_jaean.zzlambda;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class Lambda {
    public static void main(String[] args) {
        //Custom Interface
        CustomLambda<String> lambda1 = new CustomLambda<String>() {
            @Override
            public String test(String s) {
                return "data:" + s;
            }
        };

        String data = lambda1.test("springboot");

        System.out.println(data);

        CustomLambda<String> lambda2 = str -> "data: " + str;

        System.out.println(lambda2.test("spring"));
        System.out.println();

        // Runnable interface
        Runnable runnable = () -> {
            System.out.println("Runnable");
        };
        runnable.run();
        System.out.println();

        // Supplier Interface
        Supplier<String> supplier = () -> "Supplier를 통해 공급";
        System.out.println(supplier.get());
        System.out.println();

        //Consumer Interface
        Consumer<String> consumer = d -> {System.out.println("consumed" + d);};
        consumer.accept("hi");

        //Function Interface
        Function<Integer, String> function = i -> String.valueOf(i*2);
        System.out.println(function.apply(10));
        System.out.println();

        // Predict interface
        Predicate<Integer> predicate = num -> num % 2 == 0;
        System.out.println(predicate.test(20));
        System.out.println();
    }
}
