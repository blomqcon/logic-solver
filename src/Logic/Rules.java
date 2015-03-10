package Logic;

import java.util.List;

public final class Rules {
	public enum Rule {
		A,
		MPP,
		MTT,
		DN,
		AmpI,
		AmpE,
		vI,
		vE,
		CP,
		RAA
	}
	
	public static Rules.Rule getRule(String s) {
		if(s.equals("A")) {
			return Rules.Rule.A;
		} else if(s.equals("MPP")) {
			return Rules.Rule.MPP;
		} else if(s.equals("MTT")) {
			return Rules.Rule.MTT;
		} else if(s.equals("DN")) {
			return Rules.Rule.DN;
		} else if(s.equals("AmpI") || s.equals("&I")) {
			return Rules.Rule.AmpI;
		} else if(s.equals("AmpE") || s.equals("&E")) {
			return Rules.Rule.AmpE;
		} else if(s.equals("vI")) {
			return Rules.Rule.vI;
		} else if(s.equals("VE")) {
			return Rules.Rule.vE;
		} else if(s.equals("CP")) {
			return Rules.Rule.CP;
		} else if(s.equals("RAA")) {
			return Rules.Rule.RAA;
		} else {
			return null;
		}
	}

	public static Boolean checkA(List<ProofLine> proof, ProofLine line) {
		if(line.justificationLines.length != 0) {
			throw new IllegalArgumentException("A requires a no justification");
		}
		return true;
	}
	
	public static Boolean checkMPP(List<ProofLine> proof, ProofLine line) {
		if(line.justificationLines.length != 2) {
			throw new IllegalArgumentException("MPP requires two justifications");
		}

		ProofLine conditional = proof.get(line.justificationLines[0]);
		ProofLine antecedent = proof.get(line.justificationLines[1]);
		
		if (conditional.formula.connector != '→') {
			throw new IllegalArgumentException("Justification does not contain a conditional");
		} else if(! conditional.formula.left.toString().equals(antecedent.formula.toString())) {
			throw new IllegalArgumentException("Antecedent of condtional does not contain antecedent");
		} else if(! line.formula.toString().equals(conditional.formula.right.toString())) {
			throw new IllegalArgumentException("Consequent of conditional does not equal what is trying to be proven");
		}
		
		return true;
	}
	
	public static Boolean checkMTT(List<ProofLine> proof, ProofLine line) {
		if(line.justificationLines.length != 2) {
			throw new IllegalArgumentException("MPP requires two justifications");
		}
		
		ProofLine conditional = proof.get(line.justificationLines[0]);
		ProofLine negatedConsequent = proof.get(line.justificationLines[1]);
		
		Formula negatedConditionalConsequent = conditional.formula.right.negatedFormula();
		Formula negatedConditionalAntecedent = conditional.formula.left.negatedFormula();
		
		if (conditional.formula.connector != '→') {
			throw new IllegalArgumentException("Justification does not contain a conditional");
		} else if(! negatedConditionalConsequent.toString().equals(negatedConsequent.formula.toString())){
			throw new IllegalArgumentException("Conditional consequent contain negated consquent");
		} else if(! line.formula.toString().equals(negatedConditionalAntecedent.toString())){
			throw new IllegalArgumentException("Negated antecedent does not equal what is trying to be proven");
		}
		
		return true;
	}
	
	public static Boolean checkDN(List<ProofLine> proof, ProofLine line) {
		if(line.justificationLines.length != 1) {
			throw new IllegalArgumentException("DN requires a single justification");
		}
		
		Formula dnFormula = proof.get(line.justificationLines[0]).formula;
		
		if(!dnFormula.negatedFormula().negatedFormula().toString().equals(line.formula.toString()) && 
				!dnFormula.toString().equals(line.formula.negatedFormula().negatedFormula().toString())) {
			throw new IllegalArgumentException("Formula is not an instance of double negation.");
		}
		return true;
	}
}