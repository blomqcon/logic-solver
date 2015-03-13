package Logic;

public class ProofLine {

	Integer[] restingAssumptions;
	Formula formula;
	Rules.Rule justification;
	Integer[] justificationLines;
	
	public ProofLine(Integer[] rA, Formula f, Rules.Rule j, Integer[] jL) {
		this.restingAssumptions = rA;
		this.formula = f;
		this.justification = j;
		this.justificationLines = jL;
	}
	
}
