package edu.qc.seclass;

public class MyCustomString  implements MyCustomStringInterface{
	
	private String data;
	
	public MyCustomString(){
		this.data = null;
	}
	
	
	@Override
	public String getString() {
		// TODO Auto-generated method stub
		if (this.data.equals(null))
				return null;
		return this.data;
	}

	@Override
	public void setString(String string) {
		// TODO Auto-generated method stub
		this.data = string;
		
	}

	@Override
	public int countNumbers() {
		// TODO Auto-generated method stub
		if(this.data.equals(null))
			throw new NullPointerException("Null String");
		
		int count = 0;
		boolean isLastNotDigit = true;
		
		for(int i = 0; i< this.data.length(); i++) {
			if(Character.isDigit(this.data.charAt(i))) {
				if(isLastNotDigit) {
					count++;
					isLastNotDigit = false;
				}
			}else {
				isLastNotDigit = true;
			}
		}
		return count;
	}

	@Override
	public String getEveryNthCharacterFromBeginningOrEnd(int n, boolean startFromEnd) {
		// TODO Auto-generated method stub
		if(this.data.equals(null))
			throw new NullPointerException("Null String");
		
		if(n<= 0)
			throw new IllegalArgumentException("N must be greater than 0");
		
		if(this.data.length() < n)
			return "";
		
		String temp = "";
		char[] s = this.data.toCharArray();
		
		if(startFromEnd) {
			for(int i = this.data.length() %n; i< this.data.length(); i += n) {
				temp += s[i];
				
			}
		}else
		{
			for(int i = n-1; i <this.data.length(); i +=n) {
			temp += s[i];
			}
		}
		return temp;

	}

	@Override
	public void convertDigitsToNamesInSubstring(int startPosition, int endPosition) {
		// TODO Auto-generated method stub
		
		if(startPosition > endPosition) {
			throw new IllegalArgumentException("Invalid start and end position.");
		}
		
		if((startPosition > this.data.length() || startPosition < 1) || (endPosition > this.data.length() || endPosition < 1)) {
			throw new MyIndexOutOfBoundsException("Index out of Bounds.");
		}
		
		if((startPosition > 0 || endPosition > 0) && this.data == null) {
			throw new NullPointerException("Null String.");
		}
		
		int digit = 0;
		String result = "";
		String digitName = "";
		
		char[] s = this.data.toCharArray();
		
		for(int i = 0; i < startPosition -1;i++) {
			result += s[i];
		}
		
		for(int i = startPosition-1; i< endPosition; i++) {
			if(Character.isDigit(this.data.charAt(i))) {
				
				digit = Character.getNumericValue((this.data.charAt(i)));
				
				switch(digit) {
				
				case 0: digitName = "Zero";
					break;
					
				case 1: digitName = "One";
				break;
				
				case 2: digitName = "Two";
				break;
				
				case 3: digitName = "Three";
				break;
				
				case 4: digitName = "Four";
				break;
				
				case 5: digitName = "Five";
				break;
				
				case 6: digitName = "Six";
				break;
			
				case 7: digitName = "Seven";
				break;
			
				case 8: digitName = "Eight";
				break;
			
				case 9: digitName = "Nine";
				break;
				}
				result += digitName;
			}else result += s[i];
			
		}
		
		for(int i = endPosition; i < this.data.length(); i++) {
			result += s[i];
		}
		
		this.setString(result);
				
	}

}
