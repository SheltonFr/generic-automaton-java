package com.sheltonfrancisco.automaton;

import java.util.*;

public class Automaton<State, Symbol> implements IAutomaton{

    private Set<State> states;
    private Set<Symbol> alphabet;
    private State initialState;
    private Set<State> acceptingState;
    private Map<State, Map<Symbol, State>> transitions;

    public Automaton() {
        this.states = new HashSet<>();
        this.acceptingState = new HashSet<>();
        this.alphabet = new HashSet<>();
        this.transitions = new HashMap<>();
    }

    @SafeVarargs
    public final void setStates(State... states) {
        this.states.addAll(Arrays.stream(states).toList());
    }

    public void setInitialState(State initialState) {
        this.initialState = initialState;
        this.states.add(initialState);
    }

    @SafeVarargs
    public final void setAlphabet(Symbol... symbols) {
        this.alphabet.addAll(Arrays.stream(symbols).toList());
    }

    @SafeVarargs
    public final void setAcceptingStates(State... states) {
        Arrays.stream(states).toList().forEach(state -> {
            if (!this.states.contains(state)) throw new RuntimeException("This state does not exist");
            else this.acceptingState.add(state);
        });
    }

    public void setTransition(State state, Symbol symbol, State destination) {
        if (!this.states.contains(state) || !this.states.contains(destination) || !this.alphabet.contains(symbol))
            throw new RuntimeException("Invalid entry");

        if(!this.transitions.containsKey(state)) {
            this.transitions.put(state, new HashMap<>() {{
                put(symbol, destination);
            }});
        } else {
            this.transitions.get(state).put(symbol, destination);
        }


    }

    @SafeVarargs
    public  final boolean exec(Symbol... input) {

        if (initialState == null || acceptingState.isEmpty())
            throw new RuntimeException("You must provide an initial state, and, at least one accepting state");

        State currentState = initialState;
        for (Symbol symbol : input) {
            if (!this.alphabet.contains(symbol)) throw new RuntimeException(input + " is an invalid symbol");

            if (!transitions.containsKey(currentState)
                    || !transitions.get(currentState).containsKey(symbol)) {
                return false; // No transitions available
            }

            currentState = transitions.get(currentState).get(symbol);
        }


        return acceptingState.contains(currentState);
    }

    @SafeVarargs
    public final void executeWithDetails(Symbol... input) {

        if (initialState == null || acceptingState.isEmpty())
            throw new RuntimeException("You must provide an initial state, and, at least one accepting state");

        State currentState = initialState;
        for (Symbol symbol : input) {

            if (!this.alphabet.contains(symbol)) throw new RuntimeException(input + " is an invalid symbol");

            if (!transitions.containsKey(currentState)
                    || !transitions.get(currentState).containsKey(symbol)) {

                throw new RuntimeException("No transitions available!");
            }

            System.out.print("(" + currentState + ", " + symbol + ") = ");

            currentState = transitions.get(currentState).get(symbol);

            System.out.println(currentState);
        }


         if (acceptingState.contains(currentState)) System.out.println("Valid Entry"); else System.out.println("Invalid Entry");
    }
    public void details() {
        System.out.println("States: ");
        this.states.forEach(System.out::println);

        System.out.println("Inistial State: " + this.initialState);

        System.out.print("Accepting States: ");
        this.acceptingState.forEach(state -> System.out.print(state + " "));

        System.out.println("");

        System.out.println("Alphabet: ");
        this.alphabet.forEach(System.out::println);

        System.out.println("Transitions: ");
        this.transitions.forEach((state, symbolStateMap) -> {
            System.out.print(state + ": (");
            symbolStateMap.keySet().forEach(key -> System.out.print(key + ", " + symbolStateMap.get(key)));
            System.out.println(")");
        });
    }
}

