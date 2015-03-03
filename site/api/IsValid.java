import Logic.Formula;
import Logic.Sequent;


public class IsValid {

	public static void main(String[] args) {
		
		if(args.length == 0) {
			throw new IllegalArgumentException("Empty parameter");
		}
		
		Sequent sequent = new Sequent(args[0]);
		System.out.println(sequent.isValidSequent());
		System.exit(0);
	}
}