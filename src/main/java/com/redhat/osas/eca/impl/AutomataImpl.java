package com.redhat.osas.eca.impl;

import com.redhat.osas.eca.Automata;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class AutomataImpl implements Automata {
    Map<Integer, BitSet> cachedRules = new ConcurrentHashMap<>();

    @Override
    public List<BitSet> run(int startValue, int rule, int width, int generations) {
        List<BitSet> automata = new ArrayList<>();
        BitSet set = generateStart(startValue);
        automata.add(set);
        while (generations-- > 0) {
            BitSet generation = new BitSet();
            for (int pos = 0; pos < width; pos++) {
                BitSet pattern = set.get(pos, pos + 3);
                generation.set(pos, evaluate(rule, pattern));
            }
            automata.add(generation);
            set = generation;
        }
        return automata;
    }

    @Override
    public boolean evaluate(int rule, BitSet bitSet) {
        BitSet ruleSet = cachedRules.get(rule);
        if (ruleSet == null) {
            ruleSet = BitSet.valueOf(new long[]{rule});
            cachedRules.put(rule, ruleSet);
        }
        long[] l = bitSet.toLongArray();
        boolean result = false;
        if (l.length > 0) {
            result = ruleSet.get((int) (l[0] - 1));
        }
        return result;
    }

    @Override
    public void display(PrintStream out, List<BitSet> result) {
        for (BitSet set : result) {
            for (int i = 0; i < set.length(); i++) {
                out.print(set.get(i) ? "1" : ".");
            }
            out.println();
        }
    }

    public BitSet generateStart(int startValue) {
        return BitSet.valueOf(new long[]{startValue});
    }
}
