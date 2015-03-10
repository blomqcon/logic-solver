package Logic;


import java.util.List;


public class Proof {
	Formula conclusion;
	Formula[] assumptions;
	List<ProofLine> proofLines;
	
	public Proof(Sequent s) {
		assumptions = s.assumptions;
		conclusion = s.conclusion; 
	}
	
	public Proof(Sequent s, List<ProofLine> pL) {
		this.assumptions = s.assumptions;
		this.conclusion = s.conclusion;
		this. proofLines = pL;
	}
	
	public int validProof() {
		for(int i = 0; i < proofLines.size(); i++) {
			if(!checkLine(i)) {
				return i;
			}
		}
		
		if(!checkFinalAssumptions()) {
			System.out.println("Conclusion rests on assumptions not included in the sequent");
			return proofLines.size() - 1;
		} else if(!checkFinalConclusion()) {
			System.out.println("Conclusion does not match the sequents.");
			return proofLines.size() - 1;
		}
		System.out.println("Valid");
		return -1;
	}
	
	public boolean checkLine(int i) {
		ProofLine line = proofLines.get(i);
		try {
			if(line.justification == Rules.Rule.A) {
				Rules.checkA(proofLines, line);
			} else if(line.justification == Rules.Rule.MPP) {
				Rules.checkMPP(proofLines, line);
			} else if(line.justification == Rules.Rule.MTT) {
				Rules.checkMTT(proofLines, line);
			}
		} catch (IllegalArgumentException error) {
			System.out.println(error.getMessage());
			return false;
		}
		
		return true;
	}
	
	private boolean checkFinalAssumptions() {
		int[] finalAssumptions = proofLines.get(proofLines.size() - 1).restingAssumptions;
		for(int i = 0; i < finalAssumptions.length; i++) {
			Formula assump = proofLines.get(finalAssumptions[i]).formula;
			boolean restsOnAssump = false;
			for(Formula a : this.assumptions) {
				if(a.toString().equals(assump.toString())) {
					restsOnAssump = true;
					break;
				}
			}
			if(!restsOnAssump) {
				return false;
			}
		}
		return true;
	}
	
	private boolean checkFinalConclusion() {
		Formula finalConclusion = proofLines.get(proofLines.size() - 1).formula;
		return finalConclusion.toString().equals(this.conclusion.toString());
	}
}