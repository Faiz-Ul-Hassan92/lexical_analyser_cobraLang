package cobra_compiler;

import java.io.*;
import java.util.*;

public class RegexToDFA {

    static class TransTable {
        String patt;
        List<String> states = new ArrayList<>();
        List<String> alph = new ArrayList<>();
        Map<String, Map<String, String>> trans = new HashMap<>();
        Set<String> finalStates = new HashSet<>();

        TransTable(String p) {
            this.patt = p;
        }

        void show() {
            System.out.println("\nDFA for: " + patt);
            System.out.println("States: " + states);
            System.out.println("Alphabet: " + alph);
            System.out.println("Final States: " + finalStates);

            System.out.print("\ndelta | ");
            for (String inp : alph) System.out.printf(" %-4s |", inp);
            System.out.println("\n" + "-".repeat(6 + alph.size() * 7));

            for (String s : states) {
                System.out.printf("%-3s|", s);
                for (String inp : alph) {
                    System.out.printf(" %-4s |", trans.getOrDefault(s, new HashMap<>()).getOrDefault(inp, "-"));
                }
                System.out.println();
            }
            System.out.println();
        }
    }

    public static void makeDFAs(String f) {
        try (BufferedReader r = new BufferedReader(new FileReader(f))) {
            String l;
            while ((l = r.readLine()) != null) {
                String[] parts = l.split(":");
                if (parts.length != 2) continue;

                String tokType = parts[0].trim();
                String patt = parts[1].trim();
                TransTable t = makeTransTable(tokType, patt);
                t.show();
            }
        } catch (IOException e) {
            System.out.println("Uh-oh, couldn't read file: " + e.getMessage());
        }
    }

    private static TransTable makeTransTable(String type, String patt) {
        TransTable tab = new TransTable(type + ": " + patt);
        switch (type) {
            case "identifier": idDFA(tab); break;
            case "operator": opDFA(tab); break;
            case "constant": numDFA(tab); break;
            case "literal": litDFA(tab); break;
            case "punctuator": puncDFA(tab); break;
            case "exponent": expDFA(tab); break;
        }
        return tab;
    }

    private static void idDFA(TransTable t) {
        t.states.addAll(Arrays.asList("S", "ID"));
        t.alph.addAll(Arrays.asList("a-z", "other"));
        t.trans.put("S", Map.of("a-z", "ID", "other", "-"));
        t.trans.put("ID", Map.of("a-z", "ID", "other", "-"));
        t.finalStates.add("ID");
    }

    private static void opDFA(TransTable t) {
        t.states.addAll(Arrays.asList("S", "OP", "EQ"));
        t.alph.addAll(Arrays.asList("+-*/%^{}[]", "=><", "=", "other"));
        t.trans.put("S", Map.of("+-*/%^{}[]", "OP", "=><", "OP"));
        t.trans.put("OP", Map.of("=", "EQ"));
        t.finalStates.addAll(Arrays.asList("OP", "EQ"));
    }

    private static void numDFA(TransTable t) {
        t.states.addAll(Arrays.asList("S", "NUM", "DOT", "DEC", "CHAR1", "CHAR2"));
        t.alph.addAll(Arrays.asList("0-9", ".", "'", "char", "other"));
        t.trans.put("S", Map.of("0-9", "NUM", "'", "CHAR1"));
        t.trans.put("NUM", Map.of("0-9", "NUM", ".", "DOT"));
        t.trans.put("DOT", Map.of("0-9", "DEC"));
        t.trans.put("CHAR1", Map.of("char", "CHAR2"));
        t.trans.put("CHAR2", Map.of("'", "NUM"));
        t.finalStates.addAll(Arrays.asList("NUM", "DEC"));
    }

    private static void litDFA(TransTable t) {
        t.states.addAll(Arrays.asList("S", "STR", "END"));
        t.alph.addAll(Arrays.asList("\"", "char", "other"));
        t.trans.put("S", Map.of("\"", "STR"));
        t.trans.put("STR", Map.of("char", "STR", "\"", "END"));
        t.finalStates.add("END");
    }

    private static void puncDFA(TransTable t) {
        t.states.addAll(Arrays.asList("S", "P"));
        t.alph.addAll(Arrays.asList(",;", "other"));
        t.trans.put("S", Map.of(",;", "P"));
        t.finalStates.add("P");
    }

    private static void expDFA(TransTable t) {
        t.states.addAll(Arrays.asList("S", "EXP", "SIGN", "NUM"));
        t.alph.addAll(Arrays.asList("@", "+-", "1-9", "0-9"));
        t.trans.put("S", Map.of("@", "EXP"));
        t.trans.put("EXP", Map.of("+-", "SIGN", "1-9", "NUM"));
        t.trans.put("SIGN", Map.of("1-9", "NUM"));
        t.trans.put("NUM", Map.of("0-9", "NUM"));
        t.finalStates.add("NUM");
    }
}
