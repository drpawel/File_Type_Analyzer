package analyzer;

import java.util.ArrayList;
import java.util.Collections;

public interface SearchAlgorithm {
    boolean search(ArrayList<String> data, String pattern);
}

class NaiveSearch implements SearchAlgorithm{
    @Override
    public boolean search(ArrayList<String> data, String pattern) {
        for (String text : data) {
            if(text.contains(pattern)){
                return true;
            }
        }
        return false;
    }
}

class KMPSearch implements SearchAlgorithm{
    @Override
    public boolean search(ArrayList<String> data, String pattern) {
        for (String text : data) {
            ArrayList <Integer> occurrences = KMPAlgorithm(text,pattern);
            if(occurrences.size() != 0){
                return true;
            }
        }
        return false;
    }

    public static int[] prefixFunction(String str) {
        int[] prefixFunc = new int[str.length()];
        for (int i = 1; i < str.length(); i++) {
            int j = prefixFunc[i - 1];

            while (j > 0 && str.charAt(i) != str.charAt(j)) {
                j = prefixFunc[j - 1];
            }
            if (str.charAt(i) == str.charAt(j)) {
                j += 1;
            }
            prefixFunc[i] = j;
        }
        return prefixFunc;
    }
    public static ArrayList<Integer> KMPAlgorithm(String text, String pattern) {
        int[] prefixFunc = prefixFunction(pattern);
        ArrayList<Integer> occurrences = new ArrayList<>();
        int j = 0;
        for (int i = 0; i < text.length(); i++) {
            while (j > 0 && text.charAt(i) != pattern.charAt(j)) {
                j = prefixFunc[j - 1];
            }
            if (text.charAt(i) == pattern.charAt(j)) {
                j += 1;
            }
            if (j == pattern.length()) {
                occurrences.add(i - j + 1);
                j = prefixFunc[j - 1];
            }
        }
        return occurrences;
    }
}

class RKSearch implements SearchAlgorithm{
    @Override
    public boolean search(ArrayList<String> data, String pattern) {
        for (String text : data) {
            ArrayList <Integer> occurrences = RabinKarp(text,pattern);
            if(occurrences.size() != 0){
                return true;
            }
        }
        return false;
    }

    public static long charToLong(char ch) {
        return (long)(ch - 'A' + 1);
    }

    public static ArrayList<Integer> RabinKarp(String text, String pattern) {
        int a = 53;
        long m = 1_000_000_000 + 9;

        long patternHash = 0;
        long currSubstrHash = 0;
        long pow = 1;

        for (int i = 0; i < pattern.length(); i++) {
            patternHash += charToLong(pattern.charAt(i)) * pow;
            patternHash %= m;

            currSubstrHash += charToLong(text.charAt(text.length() - pattern.length() + i)) * pow;
            currSubstrHash %= m;

            if (i != pattern.length() - 1) {
                pow = pow * a % m;
            }
        }
        ArrayList<Integer> occurrences = new ArrayList<>();
        for (int i = text.length(); i >= pattern.length(); i--) {
            if (patternHash == currSubstrHash) {
                boolean patternIsFound = true;

                for (int j = 0; j < pattern.length(); j++) {
                    if (text.charAt(i - pattern.length() + j) != pattern.charAt(j)) {
                        patternIsFound = false;
                        break;
                    }
                }

                if (patternIsFound) {
                    occurrences.add(i - pattern.length());
                }
            }

            if (i > pattern.length()) {
                currSubstrHash = (currSubstrHash - charToLong(text.charAt(i - 1)) * pow % m + m) * a % m;
                currSubstrHash = (currSubstrHash + charToLong(text.charAt(i - pattern.length() - 1))) % m;
            }
        }

        Collections.reverse(occurrences);
        return occurrences;
    }
}