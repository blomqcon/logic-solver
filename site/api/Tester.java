import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import Logic.Formula;
import Logic.Proof;
import Logic.ProofLine;
import Logic.Rules;
import Logic.Sequent;
import Logic.TruthTable;

import java.util.Arrays;

public class Tester {

	public static void main(String[] args) {
		/*Formula a = new Formula("((PvQ)→(R&P))vR");
		Formula b = new Formula("P&R");
		Formula c = new Formula("(R&Q)v(P&-Q)");
		Formula d = new Formula("P→Q");
		Formula e = new Formula("-(PvQ)");
		Formula f = new Formula("(P→-Q)v-(PvQ)");
		Formula g = new Formula("(-P&R)v-(Q&P)");
		Formula h = new Formula("-(--Pv-(Q→-P))&-R");
		Formula p = new Formula("P");
		Formula q = new Formula("Q");
		Formula r = new Formula("R");
		
		//Formula[] assump = {c, q};
		//Sequent s1 = new Sequent(assump, q);
		
		//Formula t = new Formula("-P→-Q");
		Formula t = new Formula("-P&-Q");
		//System.out.println(t.right);
		
		//Sequent s2 = new Sequent("P→(PvQ)⊢-(PvQ)→---P");
		Sequent s2 = new Sequent("⊢(P→P)vQ");
		TruthTable t2 = new TruthTable(s2);
		System.out.println(Arrays.toString(t2.getHeaders()));
		System.out.println(s2 + ": " + s2.isValidSequent());
		System.out.println(Arrays.deepToString(t2.getTable()).replaceAll(", \\[", "\n"));
		System.out.println(Arrays.deepToString(s2.getShortTruthTable()).replaceAll(", \\[", "\n"));*/
		
		/*Sequent s3 = new Sequent("PvQ,(PvQ)→R⊢R");
		List<ProofLine> proofLines = new ArrayList<ProofLine>();
		int[] a1 = {0};
		int[] a2 = {1};
		int[] a3 = {0, 1};
		proofLines.add(new ProofLine(a2, new Formula("(PvQ)→R"), Rules.Rule.A, a2));
		proofLines.add(new ProofLine(a1, new Formula("PvQ"), Rules.Rule.A, a1));
		proofLines.add(new ProofLine(a3, new Formula("R"), Rules.Rule.MPP, a3));
		
		Proof proof = new Proof(s3, proofLines);
		System.out.println(proof.validProof());*/
		
		/*Sequent s3 = new Sequent("PvQ,(PvQ)→R⊢R");
		List<ProofLine> proofLines = new ArrayList<ProofLine>();
		int[] a1 = {0};
		int[] a2 = {1};
		int[] a3 = {0, 1};
		proofLines.add(new ProofLine(a2, new Formula("(PvQ)→R"), Rules.Rule.A, a2));
		proofLines.add(new ProofLine(a1, new Formula("PvQ"), Rules.Rule.A, a1));
		proofLines.add(new ProofLine(a3, new Formula("R"), Rules.Rule.MPP, a3));
		
		Proof proof = new Proof(s3, proofLines);
		System.out.println(proof.validProof());*/
		
		/*Sequent s3 = new Sequent("-R,(PvQ)→R⊢-(PvQ)");
		List<ProofLine> proofLines = new ArrayList<ProofLine>();
		int[] a1 = {0};
		int[] a2 = {1};
		int[] a3 = {0, 1};
		proofLines.add(new ProofLine(a2, new Formula("(PvQ)→R"), Rules.Rule.A, a2));
		proofLines.add(new ProofLine(a1, new Formula("-R"), Rules.Rule.A, a1));
		proofLines.add(new ProofLine(a3, new Formula("-(PvQ)"), Rules.Rule.MTT, a3));
		
		Proof proof = new Proof(s3, proofLines);
		System.out.println(proof.validProof());*/
		
		Sequent s3 = new Sequent("--(PvQ)⊢PvQ");
		List<ProofLine> proofLines = new ArrayList<ProofLine>();
		int[] a1 = {0};
		int[] a2 = {1};
		int[] a3 = {0, 1};
		proofLines.add(new ProofLine(a2, new Formula("--(PvQ)"), Rules.Rule.A, a2));
		proofLines.add(new ProofLine(a1, new Formula("PvQ"), Rules.Rule.DN, a1));
		
		Proof proof = new Proof(s3, proofLines);
		System.out.println(proof.validProof());
	}

}