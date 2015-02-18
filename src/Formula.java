import java.util.*;


public class Formula {
	public Formula left;
	public Formula right;
	public char connector;
	public Boolean not;
	//public List<Character> variables;
	
	public Formula(String f) {
		int openParens = 0;
		int index = 0;
		if(f.length() != 1) {
			while(openParens != 0 || index == 0) {
				if(f.charAt(index) == '(') {
					openParens++;
				} else if(f.charAt(index) == ')') {
					openParens--;
				}
				index++;
			}
			
			String sLeft = f.substring(0, index);
			if(sLeft.charAt(0) == '(') {
				sLeft = sLeft.substring(1, sLeft.length() - 1);
			}
			
			String sRight = f.substring(index + 1, f.length());
			if(sRight.charAt(0) == '(') {
				sRight = sRight.substring(1, sRight.length() - 1);
			}
			
			left = new Formula(sLeft);
			right = new Formula(sRight);
		}
		connector = f.charAt(index);
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
	
	public int getNumNodes() {
		return getNumNodes(this);
	}
	
	private int getNumNodes(Formula f) {
		if(this.left == null || this.right == null) {
			return 1;
		} else {
			return getNumNodes(left) + getNumNodes(right);
		}
	}
	
	public Boolean getTruthTableValue(Map<Character, Boolean> variableValues) {
		if(this.left == null || this.right == null) {
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
			return !this.right.getTruthTableValue(variableValues);
		}
		
		return null;
	}
	
	public String toString() {
		if(left == null || right == null) {
			return "" + connector;
		}
		return "(" + left.toString() + connector + right.toString() + ")";
	}
}
