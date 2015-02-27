package Logic;

import java.lang.reflect.Array;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class Util {
	private Util() {}

	public static Map<Character, Boolean> getVariableValues(List<Character> variables, String binaryRow) {
		int numVariables = variables.size();
		while(numVariables > binaryRow.length()) {
			binaryRow = "0" + binaryRow;
		}
		Map<Character, Boolean> variableValues = new HashMap<Character, Boolean>();
		for(int i = 0; i < numVariables; i++) {
			if(binaryRow.charAt(i) == '1') {
				variableValues.put(variables.get(i), true);
			} else {
				variableValues.put(variables.get(i), false);
			}
		}
		return variableValues;
	}
	
	public static String removeOutsideParens(String f) {
		if(f.charAt(0) == '(') {
			f = f.substring(1, f.length() - 1);
		}
		return f;
	}
	
	public static <T> T[] concatenate (T[] a, T[] b) {
	    int aLen = a.length;
	    int bLen = b.length;

	    @SuppressWarnings("unchecked")
	    T[] c = (T[]) Array.newInstance(a.getClass().getComponentType(), aLen+bLen);
	    System.arraycopy(a, 0, c, 0, aLen);
	    System.arraycopy(b, 0, c, aLen, bLen);

	    return c;
	}
	
}
