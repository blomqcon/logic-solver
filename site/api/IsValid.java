
public class IsValid {

	public static void main(String[] args) {
		
		if(args.length == 0) {
			throw new IllegalArgumentException("Empty parms");
		}
		
		String[] s = args[0].split("=");
		String[] aS = s[0].split(",");
		String aC = s[1];
		

		Formula conclusion = new Formula(aC);
		Formula[] assumptions = new Formula[aS.length];
		for(int i = 0; i < aS.length; i++) {
			assumptions[i] = new Formula(aS[i]);
		}
		
		Sequent sequent = new Sequent(assumptions, conclusion);
		System.out.println(sequent.isValidSequent());
		System.exit(0);
	}

}
