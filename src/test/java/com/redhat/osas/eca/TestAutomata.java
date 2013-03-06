package com.redhat.osas.eca;

import com.redhat.osas.eca.impl.AutomataImpl;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.List;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;

public class TestAutomata {
    @Test
    public void testAutomataCreation() {
        Automata automata = new AutomataImpl();
        List<BitSet> result = automata.run(1, 1, 8, 2);
        assertNotNull(result);

        assertEquals(result.size(), 3); // two generations + start
        assertEquals(result.get(1).get(0), true);
        assertEquals(result.get(1).get(1), false);
        assertEquals(result.get(2).get(0), true);
        assertEquals(result.get(2).get(1), false);

        result = automata.run(1, 30, 15, 5);
        automata.display(System.out, result);
    }

    @Test
    public void testGenerateStart() {
        AutomataImpl i = new AutomataImpl();
        BitSet set = i.generateStart(1);
        assertEquals(set.cardinality(), 1);
        set = i.generateStart(11);
        System.out.println(set);
        System.out.println(set.get(0));
        assertEquals(set.get(0), true, "0");
        assertEquals(set.get(1), true, "1");
        assertEquals(set.get(2), false, "2");
        assertEquals(set.get(3), true, "3");
        assertEquals(set.get(4), false, "4");
    }

    @Test
    void testNextCellGeneration() {
        Automata a = new AutomataImpl();
        List<BitSet> patterns = new ArrayList<>();
        for (int i = 0; i < 9; i++) {
            patterns.add(BitSet.valueOf(new long[]{i}));
        }

        // rule 110
        boolean[] results = new boolean[]{false, false, true, true, true, false, true, true, false};
        testGeneration(a, patterns, results, 110);

        // rule 1
        results = new boolean[]{false, true, false, false, false, false, false, false, false};
        testGeneration(a, patterns, results, 1);

        testGeneration(a, patterns, new boolean[]{false, false, true, false, false, false, false, false, false}, 2);
    }

    private void testGeneration(Automata a, List<BitSet> patterns, boolean[] results, int rule) {
        for (int i = 0; i < 9; i++) {
            BitSet pattern = patterns.get(i);
            assertEquals(a.evaluate(rule, pattern), results[i], "failed evaluating bit " + i + ", rule " + rule);
        }
    }
}
