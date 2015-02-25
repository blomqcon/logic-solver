package Logic;


import java.util.List;


public class Proof {
	Formula conclusion;
	Formula[] assumptions;
	List<String[]> proofLines;
	
	public Proof(Sequent s) {
		assumptions = s.assumptions;
		conclusion = s.conclusion;
	}
}