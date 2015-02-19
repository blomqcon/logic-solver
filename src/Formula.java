import java.util.*;


public class Formula {
	public Formula left;
	public Formula right;
	public char connector;
	//public List<Character> variables;
	
	public Formula(String f) {
		int openParens = 0;
		int index = 0;
		if(f.charAt(index) == '-') {
			right = new Formula(Formula.removeOutsideParens(f.substring(1)));
		} else if(f.length() != 1) {
			while(openParens != 0 || index == 0) {
				if(f.charAt(index) == '(') {
					openParens++;
				} else if(f.charAt(index) == ')') {
					openParens--;
				}
				index++;
			}
			
			left = new Formula(Formula.removeOutsideParens(f.substring(0, index)));
			right = new Formula(Formula.removeOutsideParens(f.substring(index + 1, f.length())));
		}
		connector = f.charAt(index);
	}
	
	private static String removeOutsideParens(String f) {
		if(f.charAt(0) == '(') {
			f = f.substring(1, f.length() - 1);
		}
		return f;
	}
	
	private int getNumNodes(Formula f) {
		if(this.right == null) {
			return 1;
		} else if(this.left == null) {
			return 1 + getNumNodes(right);
		} else {
			return getNumNodes(left) + getNumNodes(right);
		}
	}
	
	public int getNumNodes() {
		return getNumNodes(this);
	}
	
	public Boolean getTruthTableValue(Map<Character, Boolean> variableValues) {
		if(this.left == null && this.right == null) {
			return variableValues.get(this.connector);
		} else if(this.connector == 'v') {
			return this.left.getTruthTableValue(variableValues) || this.right.getTruthTableValue(variableValues);
		} else if(this.connector == '&') {
			return this.left.getTruthTableValue(variableValues) && this.right.getTruthTableValue(variableValues);
		} else if(this.connector == '>') {
			if(this.left.getTruthTableValue(variableValues)) {
				return this.right.getTruthTableValue(variableValues);
			} else {
				return true;
			}
		} else if(this.connector == '-') {
			//System.out.println(this.right);
			//System.out.println(variableValues.get('Q'));
			//return true;
			return !this.right.getTruthTableValue(variableValues);
		}
		
		return null;
	}
	
	public List<Character> getVariables() {
		List<Character> vars = new ArrayList<Character>();
		String f = this.toString();
		for(int i = 0; i < f.toString().length(); i++) {
			if(Character.isUpperCase(f.charAt(i)) && !vars.contains(f.charAt(i))) {
				vars.add(f.charAt(i));
			}
		}
		return vars;
	}
	
	public String toStringHelp() {
		if(this.right == null) {
			return "" + connector;
		} else if(this.left == null) {
			return connector + right.toStringHelp();
		}
		return "(" + left.toStringHelp() + connector + right.toStringHelp() + ")";
	}
	
	public String toString() {
		String f = toStringHelp();
		return Formula.removeOutsideParens(f);
	}
}
