package edu.qc.seclass;

public class main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		MyCustomString z = new MyCustomString();
		
		z.setString("I'd b3tt3r put s0me d161ts in this 5tr1n6, right?");
		z.convertDigitsToNamesInSubstring(17, 23);
		System.out.println(z.getString());

	}

}
