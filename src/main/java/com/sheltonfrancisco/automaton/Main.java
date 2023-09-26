package com.sheltonfrancisco.automaton;

import java.util.HashMap;

public class Main {
    public static void main(String[] args) {

        Automaton<String, Character> automaton = new Automaton<>();



        automaton.setInitialState("q0");
        automaton.setSates("q1", "q2", "q3");
        automaton.setAcceptingStates("q0");

        automaton.setAlphabet('0', '1');


        automaton.setTransition("q0", '0', "q2");
        automaton.setTransition("q1", '0', "q3");
        automaton.setTransition("q2", '0', "q0");
        automaton.setTransition("q3", '0', "q1");

        automaton.setTransition("q0", '1', "q1");
        automaton.setTransition("q1", '1', "q0");
        automaton.setTransition("q2", '1', "q3");
        automaton.setTransition("q3", '1', "q2");


        boolean isAccepted = automaton.exec('1', '1', '0', '0');

//        automaton.executeWithDetails('1', '1', '0', '0');

        System.out.println(isAccepted);


    }
}