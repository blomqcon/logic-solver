
public class Solver {

	public static void main(String[] args) {
		Formula a = new Formula("((PvQ)>(R&P))vR");
		Formula b = new Formula("P&R");
		Formula c = new Formula("(R&Q)v(P&Q)");
		Formula d = new Formula("P>Q");
		Formula e = new Formula("-(PvQ)");
		Formula f = new Formula("(Pv-Q)v-(PvQ)");
		Formula g = new Formula("Pv-Q");
		Formula p = new Formula("P");
		Formula q = new Formula("Q");
		Formula r = new Formula("R");
		
		Formula[] assump = {p, q};
		System.out.println(g);
		Sequent s = new Sequent(assump, g);
		
		System.out.println(s.variables);
		
		int rows = s.truthTable.length;
		int cols = s.truthTable[0].length;
		for(int i = 0; i < rows; i++) {
			for(int j = 0; j < cols; j++) {
				System.out.print(s.truthTable[i][j] + " ");
			}
			System.out.println("");
		}
		
		System.out.println(s.isValidSequent());
	}

}
