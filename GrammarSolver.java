import java.util.*;

public class GrammarSolver {
   // Holds a list of string arrays that correspond to a non-terminal key
   private SortedMap<String, List<String[]>> mapGrammar;
   
   // This method takes a grammar as a list of strings.
   // The method stores this in a convenient way so that it can
   // later generate parts of the grammar.It throws an IllegalArgumentException
   // if the grammar is empty or if there are two or more entries in the grammar for the
   // same nonterminal. This method does not change the list of strings.
   public GrammarSolver(List<String> grammar) {
      if (grammar.isEmpty()) {
         throw new IllegalArgumentException();
      }
      mapGrammar = new TreeMap<String, List<String[]>>();
      for (String s : grammar) {
         String[] seperation = s.split("::=");
         String nonTerminal = seperation[0];
         String[] rules = seperation[1].split("[|]");
         List<String[]> terminal = new ArrayList<String[]>();
         for (int i = 0; i < rules.length; i++) {
            rules[i] = rules[i].trim();
            terminal.add(rules[i].split("[ \t]+"));
         }
         mapGrammar.put(nonTerminal, terminal);
      }
   }
   
   // Returns true if the given symbol is a nonterminal of the grammar;
   // returns false otherwise.
   public boolean grammarContains(String symbol) {
      return mapGrammar.containsKey(symbol);
   }
   
   // This method uses the grammar to randomly generate the given number of occurrences
   // of the given symbol and returns the result as an array of strings.
   // For any given nonterminal symbol, each of its rules are applied
   // with equal probability. This method throws an IllegalArgumentException if the grammar
   // does not contain the given nonterminal symbol or if the number of times is less than 0.
   public String[] generate(String symbol, int times) {
      if (!grammarContains(symbol) || times < 0) {
         throw new IllegalArgumentException();
      }
      String[] grammarString = new String[times];
      for (int i = 0; i < times; i++) {
         grammarString[i] = generateString(symbol);
      }
      return grammarString;
   }
   
   // Uses grammar to generate random occurences of a given symbol
   // returns random strings of non-terminal symbols from the
   // grammar if the symbol string is passed.
   private String generateString(String symbol) {
      String finalGrammar = "";
      if (!grammarContains(symbol)) {
         return symbol;
      } else {
         List<String[]> values = mapGrammar.get(symbol);
         int randomValue = (int) (Math.random() * values.size());
         for (int i = 0; i < values.get(randomValue).length; i++) {
            finalGrammar += generateString(values.get(randomValue)[i]) + " ";
         }
      }
      return finalGrammar.trim();
   }
   
   // This method returns a string representation of the various nonterminal symbols
   // from the grammar as a sorted, comma-separated list enclosed in square brackets, as in
   // “[<np>, <s>, <vp>]”
   public String getSymbols() {
      return mapGrammar.keySet().toString();
   }
}