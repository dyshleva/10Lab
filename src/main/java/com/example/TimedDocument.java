package com.example;

public class TimedDocument implements Document {
    private String path;

    @Override
    public String parse() {
        SmartDocument smartDocument = new SmartDocument(path);
        long start = System.currentTimeMillis();
        smartDocument.parse();
        long end = System.currentTimeMillis();
        return Long.toString(end - start);
    }
}