import java.util.HashMap;
import java.util.Map;

import Logic.Formula;
import Logic.Sequent;

public class Tester {

	public static void main(String[] args) {
		Formula a = new Formula("((PvQ)>(R&P))vR");
		Formula b = new Formula("P&R");
		Formula c = new Formula("(R&Q)v(P&Q)");
		Formula d = new Formula("P>Q");
		Formula e = new Formula("-(PvQ)");
		Formula f = new Formula("(Pv-Q)v-(PvQ)");
		Formula g = new Formula("(-P&R)v-(Q&P)");
		Formula p = new Formula("P");
		Formula q = new Formula("Q");
		Formula r = new Formula("R");
		
		/*Map<Character, Boolean> variableValues = new HashMap<Character, Boolean>(); 
		variableValues.put('P', false);
		variableValues.put('Q', false);
		variableValues.put('R', false);
		System.out.println(g);
		System.out.println(g.getNumNodes());
		System.out.println(g.getTruthTable(variableValues));
		System.out.println(g.getTruthTableValue(variableValues));*/
		
		Formula[] assump = {p, q};
		Sequent s = new Sequent(assump, q);
		
		System.out.println(s.variables);
		
		Boolean[][] tTable = s.getSmallTruthTable();
		int rows = tTable.length;
		int cols = tTable[0].length;
		for(int i = 0; i < rows; i++) {
			for(int j = 0; j < cols; j++) {
				System.out.print(tTable[i][j] + " ");
			}
			System.out.println("");
		}
		
		System.out.println(s.isValidSequent());
		
		System.out.println("");
		System.out.println(p + " " + q + " = " + q);
		String[] fullTable = s.getFullTruthTable();
		for(String row : fullTable) {
			System.out.println(row);
		}
		
	}

}
