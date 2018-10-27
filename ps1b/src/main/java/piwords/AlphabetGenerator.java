package piwords;

import java.util.*;

public class AlphabetGenerator {
    /**
     * Given a numeric base, return a char[] that maps every digit that is
     * representable in that base to a lower-case char.
     * 
     * This method will try to weight each character of the alphabet
     * proportional to their occurrence in words in a training set.
     * 
     * This method should do the following to generate an alphabet:
     *   1. Count the occurrence of each character a-z in trainingData.
     *   2. Compute the probability of each character a-z by taking
     *      (occurrence / total_num_characters).
     *   3. The output generated in step (2) is a PDF of the characters in the
     *      training set. Convert this PDF into a CDF for each character.
     *   4. Multiply the CDF value of each character by the base we are
     *      converting into.
     *   5. For each index 0 <= i < base,
     *      output[i] = (the first character whose CDF * base is > i)
     * 
     * A concrete example:
     * 	 0. Input = {"aaaaa..." (302 "a"s), "bbbbb..." (500 "b"s),
     *               "ccccc..." (198 "c"s)}, base = 93
     *   1. Count(a) = 302, Count(b) = 500, Count(c) = 198
     *   2. Pr(a) = 302 / 1000 = .302, Pr(b) = 500 / 1000 = .5,
     *      Pr(c) = 198 / 1000 = .198
     *   3. CDF(a) = .302, CDF(b) = .802, CDF(c) = 1
     *   4. CDF(a) * base = 28.086, CDF(b) * base = 74.586, CDF(c) * base = 93
     *   5. Output = {"a", "a", ... (29 As, indexes 0-28),
     *                "b", "b", ... (46 Bs, indexes 29-74),
     *                "c", "c", ... (18 Cs, indexes 75-92)}
     * 
     * The letters should occur in lexicographically ascending order in the
     * returned array.
     *   - {"a", "b", "c", "c", "d"} is a valid output.
     *   - {"b", "c", "c", "d", "a"} is not.
     *   
     * If base >= 0, the returned array should have length equal to the size of
     * the base.
     * 
     * If base < 0, return null.
     * 
     * If a String of trainingData has any characters outside the range a-z,
     * ignore those characters and continue.
     * 
     * @param base A numeric base to get an alphabet for.
     * @param trainingData The training data from which to generate frequency
     *                     counts. This array is not mutated.
     * @return A char[] that maps every digit of the base to a char that the
     *         digit should be translated into.
     */
    public static char[] generateFrequencyAlphabet(int base,
                                                   String[] trainingData) {
        // TODO: Implement (Problem f)
        // variables: base, trainingData, char[] output
        if (base < 0) {
            return null;
        }

        Map<Character, Integer> frequencyMap = new HashMap<Character, Integer>();

        char[] trainingChars = Arrays.toString(trainingData).toCharArray();

        double totalNumValidChars = trainingChars.length;

        for (Character c : trainingChars) {
            Integer count = frequencyMap.get(c);

            if (count != null) {
                frequencyMap.put(c, count + 1);
            } else if (count == null && c >= 'a' && c <= 'z') {
                frequencyMap.put(c, 1);
            } else {
                totalNumValidChars--;
            }
        }

        char[] output = distribute(frequencyMap, totalNumValidChars, base);

        return output;
    }

    /*
    int i = (Integer) object;

    Beware, it can throw a ClassCastException if your object isn't an Integer and a NullPointerException if your object is null.


    If your object is a String, then you can use the Integer.valueOf() method to convert it into a simple int :
    int i = Integer.valueOf((String) object);
     */


    private static char[] distribute(Map < Character, Integer > frequencyMap,
                                          double totalNumValidChars, int base) {

        SortedSet<Character> sortedKeys = new TreeSet<Character>(frequencyMap.keySet());

        double size = sortedKeys.size();

        double[] CDF = new double[size];

        char[] output = new char[base];

        Iterator<Character> iter = sortedKeys.iterator();

        Character next  = iter.next();

        double currentProb = (double) frequencyMap.get(next) / totalNumValidChars;
        CDF[0] = currentProb;

        int max = (int) (currentProb * base);
        int index = max; //largest index to previous index

        for (int i = 0; i < max; i++) {
            output[i] = next;
        }

        for (int i = 1; i < CDF.length; i++) {

            Character c = iter.next();

            double probCurrent = (double) frequencyMap.get(c) / totalNumValidChars;

            CDF[i] = CDF[i-1] + probCurrent;

            for (int j = index; j < (int) (CDF[i] * base); j++) {
                output[j] = c;
            }

            index = max;
        }

        return output;
    }
}
