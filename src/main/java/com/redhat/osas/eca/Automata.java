package com.redhat.osas.eca;

import java.io.PrintStream;
import java.util.BitSet;
import java.util.List;

public interface Automata {
    List<BitSet> run(int startValue, int rule, int width, int generations);

    boolean evaluate(int rule, BitSet bitSet);


    void display(PrintStream out, List<BitSet> result);
}
