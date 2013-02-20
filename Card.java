public class Card implements Comparable<Card>{
	private String dispValue; //When the card is displayed, value that is displayed. (i.e. A234...JQK)
	private String dispSuit; //When the card is displayed, suit that is displayed (i.e. Spades)
	private int numValue; // The numerical value of the card (A=1||14, 2-10=self, J=11, Q=12, K=13)
	private int suitValue; //The numerical value of the card's suit (Spades=4, Hearts=3, Clubs=2, Diamonds=1)
	private int absoluteRank; 	//  an absolute value used to order cards (ace of spades =52, 2 of diamonds = 1)
	
	public Card(int v, int s, int abs) {
		numValue=v;
		suitValue=s; 
		absoluteRank=abs;
		switch (s) {
			case 1: s=4;
				dispSuit="Spades";
				break;
			case 2: s=3;
				dispSuit="Hearts";
				break;
			case 3: s=2;
				dispSuit="Clubs";
				break;
			case 4: s=1;
				dispSuit="Diamonds";
				break;
			default: 
				System.out.println("Invalid suit input");
		}
		switch (v) {
			case 1: dispValue="2";
				break;
			case 2: dispValue="3";
				break;
			case 3: dispValue="4";
				break;
			case 4:dispValue="5";
				break;
			case 5: dispValue="6";
				break;
			case 6: dispValue="7";
				break;
			case 7: dispValue="8";
				break;
			case 8: dispValue="9";
				break;
			case 9: dispValue="10";
				break;
			case 10: dispValue="Jack";
				break;
			case 11: dispValue="Queen";
				break;
			case 12:dispValue="King";
				break;
			case 13: dispValue="Ace";
				break;
			default: 
				System.out.println("Invalid valid input");
		}
	}
	
	/**toString displays a single card in the format "NUM of SUIT" */
	public String toString(){ 
		return getDispValue()+" of "+getDispSuit();
	}
	
	public String shortForm(){
		return getDispValue()+" "+getDispSuit();
	}
	public int getNumValue(){
		return numValue;
	}
	
	public String getDispValue(){
		return dispValue;
	}
	
	public int getSuitValue(){
		return suitValue;
	}
	
	public String getDispSuit(){
		return dispSuit;
	}
	
	public int getAbsRank(){
		return absoluteRank;
	}
	
	public int compareTo(Card c){
		return getNumValue()-c.getNumValue();
	}
}