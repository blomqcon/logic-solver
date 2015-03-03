package Logic;

import java.util.*;


public class Formula {
	public Formula left;
	public Formula right;
	public char connector;
	
	public Formula(String f) {
		validateFormula(f);
		int openParens = 0;
		int index = 0;
		int leadingNots = 0;
		
		while(f.charAt(index) == '-') {
			if(f.charAt(index + 1) == '(' || f.length() == 2) {
				right = new Formula(Util.removeOutsideParens(f.substring(index + 1)));
				break;
			}
			index++;
			leadingNots++;
			
			if(f.length() == leadingNots + 1) {
				index -= (leadingNots);
				right = new Formula(Util.removeOutsideParens(f.substring(index + 1)));
				break;
			}
		}
		
		if(f.length() != 1 && right == null) {
			while(openParens != 0 || index == leadingNots) {
				if(f.charAt(index) == '(') {
					openParens++;
				} else if(f.charAt(index) == ')') {
					openParens--;
				}
				index++;
			}
			
			left = new Formula(Util.removeOutsideParens(f.substring(0, index)));
			right = new Formula(Util.removeOutsideParens(f.substring(index + 1, f.length())));
		}
		connector = f.charAt(index);
		
		if(!f.equals(this.toString())) {
			throw new IllegalArgumentException("Formula is not well formed.");
		}
	}
		
	public String toString() {
		return Util.removeOutsideParens(this.toStringParen());
	}
	
	public String toStringParen() {
		if(this.right == null) {
			return "" + connector;
		} else if(this.left == null) {
			return connector + right.toStringParen();
		}
		return "(" + left.toStringParen() + connector + right.toStringParen() + ")";
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
		if(this.right == null) {
			return 1;
		} else if(this.left == null) {
			return 1 + this.right.getNumNodes();
		} else {
			return 1 + this.left.getNumNodes() + this.right.getNumNodes();
		}
	}
	
	public Boolean getTruthTableValue(Map<Character, Boolean> variableValues) {
		if(this.left == null && this.right == null) {
			return variableValues.get(this.connector);
		} else if(this.connector == 'v') {
			return this.left.getTruthTableValue(variableValues) || this.right.getTruthTableValue(variableValues);
		} else if(this.connector == '&') {
			return this.left.getTruthTableValue(variableValues) && this.right.getTruthTableValue(variableValues);
		} else if(this.connector == '→') {
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
	
	public Boolean[] getTruthTableRow(Map<Character, Boolean> variableValues) {
		if(this.right == null) {
			Boolean[] conn = {this.getTruthTableValue(variableValues)};
			return conn;
		} else if(this.left == null) {
			Boolean[] conn = {this.getTruthTableValue(variableValues)};
			return Util.concatenate(conn, this.right.getTruthTableRow(variableValues));
		} else {
			Boolean[] conn = {this.getTruthTableValue(variableValues)};
			return Util.concatenate(Util.concatenate(this.left.getTruthTableRow(variableValues), conn), this.right.getTruthTableRow(variableValues));
		}
	}
	public static void validateFormula(String f) {
		if(f.contains("[^A-Z()v&→↔\\-]")) {
			throw new IllegalArgumentException("Invalid character in formula construction");
		}
	}
}
