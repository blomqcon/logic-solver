import java.util.Arrays;

import Logic.Sequent;
import Logic.TruthTable;


public class GetTruthTable {

	public static void main(String[] args) {
		
		if(args.length == 0) {
			throw new IllegalArgumentException("Empty parameter");
		}
		
		Sequent sequent = new Sequent(args[0]);
		TruthTable table = new TruthTable(sequent);
		System.out.println(Arrays.deepToString(table.getHeaders()));
		System.out.println(Arrays.deepToString(table.getTable()));
		
		System.exit(0);
	}

}
