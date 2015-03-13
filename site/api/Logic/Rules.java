package Logic;

import java.util.Arrays;
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
		RAA,
		DfDoubleArrow
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
		} else if(s.equals("vE")) {
			return Rules.Rule.vE;
		} else if(s.equals("CP")) {
			return Rules.Rule.CP;
		} else if(s.equals("RAA")) {
			return Rules.Rule.RAA;
		} else if(s.equals("DfDoubleArrow") || s.equals("Df↔")) {
			return Rules.Rule.DfDoubleArrow;
		} else {
			return null;
		}
	}

	public static Boolean checkA(List<ProofLine> proof, ProofLine line) {
		if(line.justificationLines.length != 0) {
			throw new IllegalArgumentException("A requires no justification");
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
	
	public static Boolean checkAmpI(List<ProofLine> proof, ProofLine line) {
		if(line.justificationLines.length != 2) {
			throw new IllegalArgumentException("&I requires two justifications");
		}
		
		Formula possibility1 = new Formula("X");
		Formula possibility2 = new Formula("X");
		
		Formula andFormula1 = proof.get(line.justificationLines[0]).formula;
		Formula andFormula2 = proof.get(line.justificationLines[1]).formula;
		
		possibility1.connector = '&';
		possibility1.left = andFormula1;
		possibility1.right = andFormula2;
		possibility2.connector = '&';
		possibility2.left = andFormula2;
		possibility2.right = andFormula1;
		
		if(!(line.formula.toString().equals(possibility1.toString()) || 
				line.formula.toString().equals(possibility2.toString()))) {
			throw new IllegalArgumentException("Formula is not the conjunction of the two justifications");
		}
		
		return true;
	}
	
	public static Boolean checkAmpE(List<ProofLine> proof, ProofLine line) {
		if(line.justificationLines.length != 1) {
			throw new IllegalArgumentException("&E requires a single justification");
		}
		
		Formula andFormula = proof.get(line.justificationLines[0]).formula;
		
		if(andFormula.connector != '&') {
			throw new IllegalArgumentException("Justification reference main connector is not an 'and'");
		}
		
		if(!(andFormula.left.toString().equals(line.formula.toString()) ||
				andFormula.right.toString().equals(line.formula.toString()))) {
			throw new IllegalArgumentException("Formula is not one of the and terminals");
		}
		
		return true;
	}
	
	public static Boolean checkvI(List<ProofLine> proof, ProofLine line) {
		if(line.justificationLines.length != 1) {
			throw new IllegalArgumentException("vI requires a single justification");
		}
		
		Formula vFormulaPart = proof.get(line.justificationLines[0]).formula;
		Formula vFormula = line.formula;
		
		if(vFormula.connector != 'v') {
			throw new IllegalArgumentException("Main connector is not a 'or'");
		}
		
		if(!(vFormula.left.toString().equals(vFormulaPart.toString()) ||
				vFormula.right.toString().equals(vFormulaPart.toString()))) {
			throw new IllegalArgumentException("Formula disjunction does not contain justification");
		}
		
		return true;
	}
	
	public static Boolean checkvE(List<ProofLine> proof, ProofLine line) {
		if(line.justificationLines.length != 5) {
			throw new IllegalArgumentException("vE requires five justifications");
		}
		
		if(proof.get(line.justificationLines[1]).justification != Rules.Rule.A) {
			throw new IllegalArgumentException("CP: second justification must be an assumption");
		} else if(proof.get(line.justificationLines[3]).justification != Rules.Rule.A) {
			throw new IllegalArgumentException("CP: fourth justification must be an assumption");
		}
		
		
		Formula vFormula = proof.get(line.justificationLines[0]).formula;
		Formula vFormulaAssum1 = proof.get(line.justificationLines[1]).formula;
		Formula vFormulaConcl1 = proof.get(line.justificationLines[2]).formula;
		Formula vFormulaAssum2 = proof.get(line.justificationLines[3]).formula;
		Formula vFormulaConcl2 = proof.get(line.justificationLines[4]).formula;
		
		if(vFormula.connector != 'v') {
			throw new IllegalArgumentException("vE requires the first justification main connector to be a 'or'");
		}
		
		if(!(vFormula.left.toString().equals(vFormulaAssum1.toString()) ||
				vFormula.right.toString().equals(vFormulaAssum1.toString()))) {
			throw new IllegalArgumentException("First justification disjunction does not contain assumption 1");
		}
		
		if(!(vFormula.left.toString().equals(vFormulaAssum2.toString()) ||
				vFormula.right.toString().equals(vFormulaAssum2.toString()))) {
			throw new IllegalArgumentException("First justification disjunction does not contain assumption 2");
		}
		
		//Makes sure the conclusions are independent of each other
		Integer[] vFormulaConcl1Assumptions = proof.get(line.justificationLines[2]).restingAssumptions;
		if(Arrays.asList(vFormulaConcl1Assumptions).contains(line.justificationLines[4])) {
			throw new IllegalArgumentException("vE: First conclusion rests on second assumption, must be independent");
		}
		Integer[] vFormulaConcl2Assumptions = proof.get(line.justificationLines[4]).restingAssumptions;
		if(Arrays.asList(vFormulaConcl2Assumptions).contains(line.justificationLines[2])) {
			throw new IllegalArgumentException("vE: Second conclusion rests on first assumption, must be independent");
		}
		
		if(!vFormulaConcl1.toString().equals(vFormulaConcl2.toString())) {
			throw new IllegalArgumentException("Conclusions based on assumptions do not match");
		}
		
		if(!vFormulaConcl1.toString().equals(line.formula.toString())) {
			throw new IllegalArgumentException("Formula is not the same as the independently proven conclusions");
		}
		
		return true;
	}
	
	public static Boolean checkCP(List<ProofLine> proof, ProofLine line) {
		if(line.justificationLines.length != 2) {
			throw new IllegalArgumentException("CP requires two justifications");
		}
		
		if(proof.get(line.justificationLines[0]).justification != Rules.Rule.A) {
			throw new IllegalArgumentException("CP: first justification must be an assumption");
		}
		
		Formula cpAssumption = proof.get(line.justificationLines[0]).formula;
		Formula cpConsequent = proof.get(line.justificationLines[1]).formula;
		
		if(line.formula.connector != '→') {
			throw new IllegalArgumentException("Formula main connector is not a '→'");
		} else if (!(line.formula.left.toString().equals(cpAssumption.toString()))) {
			throw new IllegalArgumentException("Formula antecedent is not equal to the first justification");
		} else if (!(line.formula.right.toString().equals(cpConsequent.toString()))) {
			throw new IllegalArgumentException("Formula consequent is not equal to the second justification");
		}
		
		return true;
	}
	
	public static Boolean checkRAA(List<ProofLine> proof, ProofLine line) {
		if(line.justificationLines.length != 2) {
			throw new IllegalArgumentException("RAA requires two justifications");
		}
		
		if(proof.get(line.justificationLines[0]).justification != Rules.Rule.A) {
			throw new IllegalArgumentException("RAA: first justification must be an assumption");
		}
		
		Formula raaAssumption = proof.get(line.justificationLines[0]).formula;
		Formula raaContradiction = proof.get(line.justificationLines[1]).formula;
		
		if(raaContradiction.connector != '&') {
			throw new IllegalArgumentException("RAA: second justification, the contradiction, must be an 'and'");
		}
		
		if(!(raaContradiction.left.negatedFormula().toString().equals(raaContradiction.right.toString()))) {
			throw new IllegalArgumentException("RAA: the second disjunction of the contradiction must be the negation of the first");
		}
		
		if(!(line.formula.toString().equals(raaAssumption.negatedFormula().toString()))) {
			throw new IllegalArgumentException("RAA: Formula should be the negation of the first justification, the assumption");
		}
		
		return true;
	}
	
	public static Boolean checkDfDoubleArrow(List<ProofLine> proof, ProofLine line) {
		if(line.justificationLines.length != 1) {
			throw new IllegalArgumentException("Df ↔ requires a single justification");
		} else if(proof.get(line.justificationLines[0]).formula.connector != '↔') {
			throw new IllegalArgumentException("Df ↔: Main connector of justification must be a '↔'");
		}
		
		Formula leftTerminal = proof.get(line.justificationLines[0]).formula.left;
		Formula rightTerminal = proof.get(line.justificationLines[0]).formula.right;
		Formula firstArrow = new Formula("X");
		Formula secondArrow = new Formula("X");
		firstArrow.connector = '→';
		firstArrow.left = leftTerminal;
		firstArrow.right = rightTerminal;
		secondArrow.connector = '→';
		secondArrow.left = rightTerminal;
		secondArrow.right = leftTerminal;
		
		
		if(line.formula.connector != '&') {
			throw new IllegalArgumentException("Df ↔: result main connector must be an 'and'");
		} else if(!((line.formula.left.toString().equals(firstArrow.toString()) && line.formula.right.toString().equals(secondArrow.toString())) ||
				(line.formula.left.toString().equals(secondArrow.toString()) && line.formula.right.toString().equals(firstArrow.toString())))) {
			throw new IllegalArgumentException("Df ↔: The result is not a conjunction made up of both possible '→' combinations");
		}
		
		return true;
	}
}