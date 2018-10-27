package piwords;

import java.util.*;
import java.util.Map.Entry;


public class AlphabetGenerator {
    public static char[] generateFrequencyAlphabet(int base, String[] trainingData) {

		Map<Character, Integer> frequencyMap = new HashMap<Character, Integer>();
		char [] output = new char[base];   
                
        int count = 0;
		for (int i = 0; i < trainingData.length; i++) {
			for (Character c : trainingData[i].toCharArray()) {
				count++;
				if (frequencyMap.containsKey(c))
                    frequencyMap.put(c, frequencyMap.get(c) + 1);
				else
                    frequencyMap.put(c, 1);
			}
		}

        		
		TreeMap<Character, Integer> sortedMap = new TreeMap<Character, Integer>(frequencyMap);
		HashMap<Character, Double> cdfmap = new HashMap<Character, Double >();

		int out = 0;
        for ( Entry<Character, Integer> en: sortedMap.entrySet()) {
            
            char ch = en.getKey();
			int  freq = en.getValue();
			double prob = (double) freq/count;
			double cdf = base * prob;
			long cdfRounded =  Math.round(cdf);
			for ( int index = out; index < out + cdfRounded ; index++) {
				output[index] = ch;
			}
			out += cdfRounded;
		}

		return output;
	}
}

/*
CY

import java.util.*;
import java.util.Map.*;

public class AlphabetGenerator {
    public static char[] generateFrequencyAlphabet(int base, String[] trainingData) {

		Map<Character, Integer> frequencyMap = new HashMap<Character, Integer>();
		char [] output = new char[base];   
                
        int count = 0;
		for (int i = 0; i < trainingData.length; i++) {
			for (Character c : trainingData[i].toCharArray()) {
				count++;
				if (frequencyMap.containsKey(c))
                    frequencyMap.put(c, frequencyMap.get(c) + 1);
				else
                    frequencyMap.put(c, 1);
			}
		}

        		
		TreeMap<Character, Integer> sortedMap = new TreeMap<Character, Integer>(frequencyMap);
		HashMap<Character, Double> cdfmap = new HashMap<Character, Double >();

		int out = 0;
        for ( Entry<Character, Integer> en: sortedMap.entrySet()) {
            
            char ch = en.getKey();
			int  freq = en.getValue();
			double prob = (double) freq/count;
			double cdf = base * prob;
			long cdfRounded =  Math.round(cdf);
			for ( int index = out; index < out + cdfRounded ; index++) {
				output[index] = ch;
			}
			out += cdfRounded;
		}

		return output;
	}
}
*/


