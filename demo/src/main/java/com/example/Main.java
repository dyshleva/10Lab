package com.example;

public class Main {
    public static void main(String[] args) {
        Document sd = new SmartDocument("/Users/dyshleva/Desktop/lab10/demo/src/main/java/com/example/my.png");
        System.out.println(sd.parse());
    }
}
