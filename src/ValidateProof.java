import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import Logic.*;


public class ValidateProof {
	public static void main(String[] args) {
		if(args.length != 2) {
			throw new IllegalArgumentException("Requires two parameters");
		}
			
		Sequent sequent = new Sequent(args[0]);
		JSONArray json = new JSONArray(args[1]);

		List<ProofLine> pL = new ArrayList<ProofLine>();
		for(int i = 0; i < json.length(); i++) {
			try {
				JSONObject jsonProofLine = json.getJSONObject(i);
				String sAssumptions = jsonProofLine.getString("assumptions").replaceAll("\\s+","");
				Integer[] rA;
				if(sAssumptions.equals("")) {
					rA = new Integer[0];
				} else {
					rA = new Integer[sAssumptions.split(",").length];
				}
				for(int j = 0; j < rA.length; j++) {
					rA[j] = Integer.parseInt(sAssumptions.split(",")[j]) - 1;
				}
				
				Formula f = new Formula(jsonProofLine.getString("formula").replaceAll("\\s+",""));
				
				Rules.Rule just = Rules.getRule(jsonProofLine.getString("justificationRule").replaceAll("\\s+",""));
				
				String sJL = jsonProofLine.getString("justificationLines").replaceAll("\\s+","");
				Integer[] jL;
				if(sJL.equals("")) {
					jL = new Integer[0];
				} else {
					jL = new Integer[sJL.split(",").length];
				}
				for(int j = 0; j < jL.length; j++) {
					jL[j] = Integer.parseInt(sJL.split(",")[j]) - 1;
				}
				
				pL.add(new ProofLine(rA, f, just, jL));
			} catch (org.json.JSONException error){ }
		}
		
		Proof proof = new Proof(sequent, pL);
		System.out.println(proof.validProof());
		
		System.exit(0);
	}
}
