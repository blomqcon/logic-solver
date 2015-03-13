package Logic;


import java.lang.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


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
			if(!checkLine(i) || !checkAssumptions(i)) {
				return i;
			}
		}
		
		if(!checkFinalAssumptions()) {
			System.out.println("Conclusion rests on assumptions not included in the sequent");
			return proofLines.size() - 1;
		} else if(!checkFinalConclusion()) {
			System.out.println("Conclusion does not match the sequent");
			return proofLines.size() - 1;
		}
		System.out.println("Valid");
		return -1;
	}
	
	public boolean checkLine(int i) {
		ProofLine line = proofLines.get(i);
		try {
			if(line.justification == Rules.Rule.A) {
				return Rules.checkA(proofLines, line);
			} else if(line.justification == Rules.Rule.MPP) {
				return Rules.checkMPP(proofLines, line);
			} else if(line.justification == Rules.Rule.MTT) {
				return Rules.checkMTT(proofLines, line);
			} else if(line.justification == Rules.Rule.DN) {
				return Rules.checkDN(proofLines, line);
			} else if(line.justification == Rules.Rule.AmpI) {
				return Rules.checkAmpI(proofLines, line);
			} else if(line.justification == Rules.Rule.AmpE) {
				return Rules.checkAmpE(proofLines, line);
			} else if(line.justification == Rules.Rule.vI) {
				return Rules.checkvI(proofLines, line);
			} else if(line.justification == Rules.Rule.vE) {
				return Rules.checkvE(proofLines, line);
			} else if(line.justification == Rules.Rule.CP) {
				return Rules.checkCP(proofLines, line);
			} else if(line.justification == Rules.Rule.RAA) {
				return Rules.checkRAA(proofLines, line);
			} else if(line.justification == Rules.Rule.DfDoubleArrow) {
				return Rules.checkDfDoubleArrow(proofLines, line);
			} else {
				throw new IllegalArgumentException("Not a valid rule");
			}
		} catch (IllegalArgumentException error) {
			System.out.println(error.getMessage());
			return false;
		}
	}
	
	public boolean checkAssumptions(int index) {
		ProofLine line = proofLines.get(index);
		Rules.Rule rule = line.justification;
		if(line.justificationLines.length == 0) {
			if(line.restingAssumptions.length != 1 || line.restingAssumptions[0] != index) {
				System.out.println("Assumptions should only rest on themselves");
				return false;
			} else {
				return true;
			}
		} else {			
			Set<Integer> afterDischargedAssumptions = new HashSet<Integer>();
			
			ArrayList justifications = new ArrayList(Arrays.asList(line.justificationLines));

			for(int i = 0; i < justifications.size(); i++) {
				Integer justificationLine = (Integer) justifications.get(i);
				for(int j = 0; j < proofLines.get(justificationLine).restingAssumptions.length; j++) {
					afterDischargedAssumptions.add(proofLines.get(justificationLine).restingAssumptions[j]);
				}				
			}
			
			if(rule == Rules.Rule.vE) {
				afterDischargedAssumptions.remove(proofLines.get(line.justificationLines[1]).restingAssumptions[0]);
				afterDischargedAssumptions.remove(proofLines.get(line.justificationLines[3]).restingAssumptions[0]);
			} else if(rule == Rules.Rule.CP) {
				afterDischargedAssumptions.remove(proofLines.get(line.justificationLines[0]).restingAssumptions[0]);
			} else if (rule == Rules.Rule.RAA) {
				afterDischargedAssumptions.remove(proofLines.get(line.justificationLines[0]).restingAssumptions[0]);
			}
			
			Integer[] recursiveAssumptions = afterDischargedAssumptions.toArray(new Integer[afterDischargedAssumptions.size()]);
			Arrays.sort(recursiveAssumptions);
			Arrays.sort(line.restingAssumptions);
			
			
			
			if(Arrays.deepEquals(recursiveAssumptions, line.restingAssumptions)) {
				return true;
			} else {
				System.out.println("Resting assumptions do not match justification assumptions");
				return false;
			}
		}
	}
	
	private boolean checkFinalAssumptions() {
		Integer[] finalAssumptions = proofLines.get(proofLines.size() - 1).restingAssumptions;
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