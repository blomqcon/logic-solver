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
		
		Formula[] assump = {c, q};
		Sequent s = new Sequent(assump, q);
		TruthTable tt = new TruthTable(s);
		System.out.println(Arrays.deepToString(tt.getTable()));
		
		Sequent s2 = new Sequent("P→Q,P⊢Q");
		System.out.println(s2.isValidSequent());
		//Byte[] turnstile = (byte) 8866;
		//Character turnstile = Character.toChars(0x22A2)[0];
		//System.out.println("");
		
	}

}