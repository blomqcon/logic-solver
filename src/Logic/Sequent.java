package Logic;


import java.util.*;

public class Sequent {
	Formula[] assumptions;
	Formula conclusion;
	public List<Character> variables;
	
	public Sequent(String f) {
		this(getAssumptions(f), getConclusion(f));
	}
	
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
	}
	
	public int getNumNodes() {
		int nodes = 0;
		for(Formula assumption : assumptions) {
			nodes += assumption.getNumNodes();
		}
		return nodes + conclusion.getNumNodes();
	}

	public boolean isValidSequent() {
		Boolean[][] truthTable = getShortTruthTable();
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
	
	//Time complexity: O((2^v)n) where v: number of variables, n: number of nodes.
	public Boolean[][] getShortTruthTable() {
		Boolean[][] truthTable;
		int numVariables = variables.size();
		int rows = (int) Math.pow(2, numVariables);

		truthTable = new Boolean[rows][assumptions.length + 1];
		
		for(int i = 0; i < rows; i++) {
			String binaryRow = Integer.toString(i, 2);
			Map<Character, Boolean> variableValues = Util.getVariableValues(variables, binaryRow);
			for(int j = 0; j < assumptions.length; j++) {
				truthTable[i][j] = assumptions[j].getTruthTableValue(variableValues);
			}
			truthTable[i][assumptions.length] = conclusion.getTruthTableValue(variableValues);
		}
		return truthTable;
	}
	
	private static Formula[] getAssumptions(String f) {
		String[] sA = f.split("⊢")[0].split(",");
		
		Formula[] assumptions = new Formula[sA.length];
		for(int i = 0; i < sA.length; i++) {
			assumptions[i] = new Formula(sA[i]);
		}
		return assumptions;
	}
	private static Formula getConclusion(String f) {
		String sC = f.split("⊢")[1];
		return new Formula(sC);
	}
}
