package Logic;

import java.util.Map;

public class TruthTable {
	Sequent sequent;
	String[] headers;
	Boolean[][] table;
	
	//Copy Constructor
	private TruthTable(TruthTable original) {
		this.sequent = original.sequent;
		this.headers = original.headers;
		this.table = original.table;
	}
	
	//Time complexity: O((2^v)nlog(n)) where v: number of variables, n: number of nodes.
	public TruthTable(Sequent s) {
		this.sequent = s;
		
		int numVariables = sequent.variables.size();
		int rows = (int) Math.pow(2, numVariables);
		int cols = sequent.getNumNodes();
		
		table = new Boolean[rows][cols];
		for(int i = 0; i < rows; i++) {
			String binaryRow = Integer.toString(i, 2);
			Map<Character, Boolean> variableValues = Util.getVariableValues(sequent.variables, binaryRow);
			
			Boolean row[] = {};
			for(Formula assumption : sequent.assumptions) {
				row = Util.concatenate(row, assumption.getTruthTableRow(variableValues));
			}
			row = Util.concatenate(row, sequent.conclusion.getTruthTableRow(variableValues));
			table[i] = row;
		}
	}
	
	//Returns a copy of the table
	public Boolean[][] getTable() {
		return new TruthTable(this).table;
	}
}
