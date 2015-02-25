import java.util.*;

public class Sequent {
	Formula[] assumptions;
	Formula conclusion;
	List<Character> variables;
	Boolean[][] truthTable;
	
	public Sequent(Formula[] a, Formula c) {
		assumptions = a;
		conclusion = c;
		variables = new ArrayList<Character>();
		
		for(Formula assumption : assumptions) {
			for(Character var : assumption.getVariables()) {
				if(!variables.contains(var)) {
					variables.add(var);
				}
			}
		}
		for(Character var : conclusion.getVariables()) {
			if(!variables.contains(var)) {
				variables.add(var);
			}
		}
		
		initTruthTable();
	}
	
	public int getNumNodes() {
		int nodes = 0;
		for(Formula assumption : assumptions) {
			nodes += assumption.getNumNodes();
		}
		return nodes + conclusion.getNumNodes();
	}

	public boolean isValidSequent() {
		for(int i = 0; i < truthTable.length; i++) {
			boolean allTrue = true;
			for(int j = 0; j < truthTable[i].length - 1; j++) {
				if(!truthTable[i][j])
					allTrue = false;
			}
			if(allTrue && !truthTable[i][truthTable[i].length - 1])
				return false;
		}
		return true;
	}
	
	public Boolean[][] getFullTruthTable() {
		int numVariables = variables.size();
		int rows = (int) Math.pow(2, numVariables);
		int conclusionCols = conclusion.getNumNodes();
		int assumptionCols = 0;
		for(Formula assumption : assumptions) {
			assumptionCols += assumption.getNumNodes();
		}
		
		Boolean[][] tTable = new Boolean[rows][conclusionCols + assumptionCols];
		for(int i = 0; i < rows; i++) {
			String binaryRow = Integer.toString(i, 2);
			Map<Character, Boolean> variableValues = getVariableValues(numVariables, binaryRow);
			
			for(int j = 0; j < assumptionCols; j++) {
				truthTable[i][j] = assumptions[j].getTruthTableValue(variableValues);
			}
			truthTable[i][conclusionCols] = conclusion.getTruthTableValue(variableValues);
		}
		
		return null;
	}
	
	private void initTruthTable() {
		int numVariables = variables.size();
		int rows = (int) Math.pow(2, numVariables);

		truthTable = new Boolean[rows][assumptions.length + 1];
		
		for(int i = 0; i < rows; i++) {
			String binaryRow = Integer.toString(i, 2);
			Map<Character, Boolean> variableValues = getVariableValues(numVariables, binaryRow);
			for(int j = 0; j < assumptions.length; j++) {
				truthTable[i][j] = assumptions[j].getTruthTableValue(variableValues);
			}
			//System.out.println(conclusion.right.getTruthTableValue(variableValues));
			truthTable[i][assumptions.length] = conclusion.getTruthTableValue(variableValues);
		}
	}
	
	private Map<Character, Boolean> getVariableValues(int numVariables, String binaryRow) {
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
}
