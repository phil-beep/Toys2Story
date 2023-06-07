package com.example.toys2story;

public class Analyzer {
    public static void analyze() {
        switch(Story.toys.size()) {
            case 0:
                Story.toys.add("Plane");
                break;
            case 1:
                Story.toys.add("Teddy");
                break;
            case 2:
                Story.toys.add("Ball");
                break;
            default:
                break;
        }
    }
}
