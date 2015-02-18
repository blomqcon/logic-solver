
public class Solver {

	public static void main(String[] args) {
		Formula a = new Formula("((PvQ)>(R&P))vR");
		Formula b = new Formula("P&R");
		Formula c = new Formula("(R&Q)v(P&Q)");
		Formula d = new Formula("P>Q");
		Formula p = new Formula("P");
		Formula q = new Formula("Q");
		Formula r = new Formula("Q");
		
		Formula[] assump = {d, p};
		Sequent s = new Sequent(assump, q);
		
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
	
		
		/*System.out.println(s.truthTable[0][0] + " " + s.truthTable[0][1] + " " + s.truthTable[0][2] + " " + s.truthTable[0][3] + " " + s.truthTable[0][4]);
		System.out.println(s.truthTable[1][0] + " " + s.truthTable[1][1] + " " + s.truthTable[1][2] + " " + s.truthTable[1][3] + " " + s.truthTable[1][4]);
		System.out.println(s.truthTable[2][0] + " " + s.truthTable[2][1] + " " + s.truthTable[2][2] + " " + s.truthTable[2][3] + " " + s.truthTable[2][4]);
		System.out.println(s.truthTable[3][0] + " " + s.truthTable[3][1] + " " + s.truthTable[3][2] + " " + s.truthTable[3][3] + " " + s.truthTable[3][4]);
		System.out.println(s.truthTable[4][0] + " " + s.truthTable[4][1] + " " + s.truthTable[4][2] + " " + s.truthTable[4][3] + " " + s.truthTable[4][4]);
		System.out.println(s.truthTable[5][0] + " " + s.truthTable[5][1] + " " + s.truthTable[5][2] + " " + s.truthTable[5][3] + " " + s.truthTable[5][4]);
		System.out.println(s.truthTable[6][0] + " " + s.truthTable[6][1] + " " + s.truthTable[6][2] + " " + s.truthTable[6][3] + " " + s.truthTable[6][4]);
		System.out.println(s.truthTable[7][0] + " " + s.truthTable[7][1] + " " + s.truthTable[7][2] + " " + s.truthTable[7][3] + " " + s.truthTable[7][4]);
		System.out.println("");*/
	}

}
