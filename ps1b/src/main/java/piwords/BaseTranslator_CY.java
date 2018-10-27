package piwords;

public class BaseTranslator {
    public static int[] convertBase(int[] digits, int baseA,
                                    int baseB, int precisionB) {
    	if ((baseA <2 || baseB <2 || precisionB < 1)){
            return null;
        }
    	else {
    		int[] output = new int[precisionB];

            for (int i = 0; i < precisionB ; i++) {

                int carry = 0;
                int len = digits.length;
                
                for (int j = len-1 ; j >= 0 ; j--) {
                    
                    if ((digits[j]<0) || (digits[j] >= baseA)) {
                        return null;
                    } 
                    
                    else {
                    int x = digits[j] * baseB + carry;

                    digits[j] = x % baseA;
                    carry = x / baseA;
                    
                    }
                
                }
                output[i] = carry;
            }

            return output;
        }
    }
}
