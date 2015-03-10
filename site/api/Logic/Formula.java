package Logic;

import java.util.*;


public class Formula {
	public Formula left;
	public Formula right;
	public char connector;
	private int mainConnectorIndex;
	
	public Formula(String f) {
		if(f.contains("[^A-Z()v&→↔\\-]")) {
			throw new IllegalArgumentException("Invalid character in formula construction");
		}
		
		mainConnectorIndex = 0;
		if(f.length() > 1) {
			if(mainConnectorIsNot(f)) {
				right = new Formula(Util.removeOutsideParens(f.substring(mainConnectorIndex + 1)));
			} else {
				mainConnectorIndex = getMainConnectorIndex(f);
				left = new Formula(Util.removeOutsideParens(f.substring(0, mainConnectorIndex)));
				right = new Formula(Util.removeOutsideParens(f.substring(mainConnectorIndex + 1, f.length())));
			}
		}
		connector = f.charAt(mainConnectorIndex);
		
		if(!f.equals(this.toString())) {
			throw new IllegalArgumentException("Formula is not well formed.");
		}
	}
	
	private Boolean mainConnectorIsNot(String f) {
		int index = 0;
		if(f.charAt(index) == '-') {
			while(f.charAt(index) == '-') {
				index++;
			} 
			Character cIndex = f.charAt(index);
			if(Character.isUpperCase(cIndex) && index == (f.length() - 1)) {
				return true;
			} else if(cIndex == '(' && f.charAt(f.length() - 1) == ')') {
				return true;
			}	
		}
		return false;
	}
	
	private int getMainConnectorIndex(String f) {
		int index = 0;
		while(f.charAt(index) == '-') {
			index++;
		}
		Character cIndex = f.charAt(index);
		if(Character.isUpperCase(cIndex)) {
			return index + 1;
		} else {
			int openParens = 1;
			index++;
			while(openParens != 0) {
				if(f.charAt(index) == '(') {
					openParens++;
				} else if(f.charAt(index) == ')') {
					openParens--;
				}
				index++;
			}
			return index;
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
		} else if(this.connector == '↔') {
			return this.left.getTruthTableValue(variableValues) == this.right.getTruthTableValue(variableValues);
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
	
	public int getMainConnectorIndex() {
		return mainConnectorIndex;
	}
	
	public Formula negatedFormula() {
		String f = this.toString();
		if(f.length() == 1 || mainConnectorIsNot(f)) {
			return new Formula("-" + f);
		} else {
			return new Formula("-(" + f + ")");
		}
	}
}
