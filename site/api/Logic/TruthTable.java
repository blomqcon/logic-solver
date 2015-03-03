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
		setHeaders();
		
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
	
	private void setHeaders() {
		this.headers = new String[sequent.getNumNodes()];
		String sSequent = sequent.toString().replaceAll("\\s","");
		
		for(int i = 0; i < headers.length; i++) {
			headers[i] = "";
		}
		
		int offset = 0;
		for(int i = 0; i < sSequent.length(); i++) {
			headers[i - offset] += sSequent.charAt(i);
			if(sSequent.charAt(i) == '(' || sSequent.charAt(i) == ')' || sSequent.charAt(i) == ',' || sSequent.charAt(i) == '⊢' ) {
				headers[i - offset] += " ";
				offset++;
			}
		}
	}
	
	//Returns a copy of the table
	public Boolean[][] getTable() {
		return new TruthTable(this).table;
	}
	
	public String[] getHeaders() {
		String[] headersInQuotes = new String[headers.length];
		for(int i = 0; i < headersInQuotes.length; i++) {
			headersInQuotes[i] = "\"" + headers[i] + "\"";
		}
		return headersInQuotes;
	}
}
