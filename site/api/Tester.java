import java.util.HashMap;
import java.util.Map;

import Logic.Formula;
import Logic.Sequent;
import Logic.TruthTable;

import java.util.Arrays;

public class Tester {

	public static void main(String[] args) {
		Formula a = new Formula("((PvQ)→(R&P))vR");
		Formula b = new Formula("P&R");
		Formula c = new Formula("(R&Q)v(P&-Q)");
		Formula d = new Formula("P→Q");
		Formula e = new Formula("-(PvQ)");
		Formula f = new Formula("(P→-Q)v-(PvQ)");
		Formula g = new Formula("(-P&R)v-(Q&P)");
		Formula p = new Formula("P");
		Formula q = new Formula("Q");
		Formula r = new Formula("R");
		
		//Formula[] assump = {c, q};
		//Sequent s1 = new Sequent(assump, q);
		
		Formula t = new Formula("-P→-Q");
		//Formula t = new Formula("(-(PvQ))→---P");
		System.out.println(t);
		
		//Sequent s2 = new Sequent("P→(PvQ)⊢(-(PvQ))→---P");
		//TruthTable t2 = new TruthTable(s2);
		//System.out.println(Arrays.toString(t2.getHeaders()));
		//System.out.println(s2 + ": " + s2.isValidSequent());
		//System.out.println(Arrays.deepToString(t2.getTable()).replaceAll(", \\[", "\n"));
		//System.out.println(Arrays.deepToString(s2.getShortTruthTable()).replaceAll(", \\[", "\n"));
	}

}