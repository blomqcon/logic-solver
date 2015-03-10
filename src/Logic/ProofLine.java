package Logic;

public class ProofLine {

	int[] restingAssumptions;
	Formula formula;
	Rules.Rule justification;
	int[] justificationLines;
	
	public ProofLine(int[] rA, Formula f, Rules.Rule j, int[] jL) {
		this.restingAssumptions = rA;
		this.formula = f;
		this.justification = j;
		this.justificationLines = jL;
	}
	
}
