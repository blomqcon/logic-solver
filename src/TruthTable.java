import java.util.List;


public class TruthTable {
	Sequent sequent;
	List<Character> variables;
	int rows;
	Boolean[][] table;
	public TruthTable(Sequent s) {
		this.sequent = s;
		this.variables = sequent.variables;
		this.rows = (int) Math.pow(2, variables.size());
		table = new Boolean[rows][];
		
		fillInTable();
	}
	
	private void fillInTable() {
		for(int i = 0; i < rows; i++) {
			
		}
	}
}
