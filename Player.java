import java.io.*; 
import java.util.*;
public class Player {
	private ArrayList<Card> hand=new ArrayList<Card>(5);
	private String name; 
	private int cardsDiscarded;
	private int highCardValue;
	private int kickerValue;
	private int handScore; 
	
	public Player(String n){
		name=n;
		cardsDiscarded=0;
		handScore=0;
	}
	
	public String getName(){
		return name;
	}
	
	public Card getCardAt(int i){
		return hand.get(i);
	}
	
	public ArrayList<Card> getHand(){
		return hand;
	}
	
	public void setCardAt(int i, Card c){
		hand.set(i,c);
	}
	
	public int getHandSize(){
		return hand.size();
	}
	public String toString(){
		return getName()+": "+prettyHand();
	}
	
	public String prettyHand(){
		String output="";
		for(int i=0; i<5; i++)	{
			output+=getCardAt(i).shortForm()+"; ";
		}
		return output;
	}
	
	public void setHandScore(int i){
		handScore=i;
	}
	
	public int getHandScore(){
		return handScore;
	}
	
	public int getDiscs(){
		return cardsDiscarded;
	}
	
	public void takeTurn(){
		cardsDiscarded=0;
		String response="";
		System.out.println("");
		System.out.println("Your hand is: "+prettyHand());
		System.out.println("Enter [Y] to keep or [N] to discard.");
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		Iterator it = hand.iterator();
		for (int i=0;i<5;i++){
			System.out.print(getCardAt(i)+" ");
			try{
			response=(String)br.readLine();
			} catch (IOException ioe) {
				System.out.println("IO error");
			}
			if (response.equals("y")||response.equals("Y")){
				System.out.println("Card kept");
			}
			else if (response.equals("n")||response.equals("N")){
				System.out.println("Card discarded"); 
				discardCard(i);
			}
		}
		System.out.println("You have discarded "+getDiscs()+" cards this round. You will now draw to fill your hand.");
	while (it.hasNext()){
			if (it.next()==null){
				it.remove();
			}
		}
	}	
	
	public void discardCard(int i){
		getHand().set(i,null);
		cardsDiscarded++;
	}
	
	public void setHighCard(Card c){
		highCardValue=c.getNumValue();
	}
	
	public void setKicker(Card c){
		kickerValue=c.getNumValue();
	}
	
	public String scoreHand(){
		System.out.println("Scoring hand now.");
		Collections.sort(getHand());
		setHighCard(getCardAt(4));
		setKicker(getCardAt(3));
		String handSet="No Pair";
		boolean straight=false;
		boolean flush=false;
		
		//flush check
		for (int i=1; i<5; i++)
		{
			if (getCardAt(0).getSuitValue()==getCardAt(i).getSuitValue()){
				flush=true;
				handSet="Flush";
			}
			else{
				flush=false;
				handSet="No Pair";
				break;
			}
		}
		//straight check
		for (int i=1;i<5;i++){
			if (getCardAt(i).getNumValue()==getCardAt(i-1).getNumValue()+1){
				straight = true;
				handSet="Straight";
				
			}
			else {
				straight=false;
				handSet="No Pair";
				break;
			}
		}
		// as only one deck is being used, straights and flushes preclude any paired hands
		//checks royal flush and straight flush; 
		if (straight&&flush){
			if (highCardValue==14){
				handSet="Royal Flush";
				flush=false;
				straight=false;
			}
			
			else {
				setHighCard(getCardAt(4));
				setKicker(getCardAt(3));
				handSet="Straight Flush";
				flush=false;
				straight=false;
			}
		}
		else {
			//4-kind, 3-kind, full house and pair check
			if (getCardAt(0).getNumValue()==getCardAt(1).getNumValue()&&getCardAt(0).getNumValue()==getCardAt(2).getNumValue()&&getCardAt(0).getNumValue()==getCardAt(3).getNumValue()){
				handSet="Four-of-a-Kind";
				setHighCard(getCardAt(0));
				setKicker(getCardAt(4));
			}
			else if (getCardAt(1).getNumValue()==getCardAt(2).getNumValue()&&getCardAt(1).getNumValue()==getCardAt(3).getNumValue()&&getCardAt(1).getNumValue()==getCardAt(4).getNumValue()){
				handSet="Four-of-a-Kind";
				setHighCard(getCardAt(4));
				setKicker(getCardAt(0));
			}
			else if (getCardAt(0).getNumValue()==getCardAt(1).getNumValue()&&getCardAt(0).getNumValue()==getCardAt(2).getNumValue()){
				handSet="Three-of-a-Kind";
				if (getCardAt(3).getNumValue()==getCardAt(4).getNumValue()){
					handSet="Full House";
					setHighCard(getCardAt(0));
					setKicker(getCardAt(3));
				}
			}
			else if (getCardAt(1).getNumValue()==getCardAt(1).getNumValue()&&getCardAt(1).getNumValue()==getCardAt(3).getNumValue()){
				handSet="Three-of-a-Kind";
				setHighCard(getCardAt(1));
				setKicker(getCardAt(4));
			}
			else if (getCardAt(2).getNumValue()==getCardAt(3).getNumValue()&&getCardAt(2).getNumValue()==getCardAt(4).getNumValue()){
				handSet="Three-of-a-Kind";
				if (getCardAt(0).getNumValue()==getCardAt(1).getNumValue()){
					handSet="Full House";
					setHighCard(getCardAt(2));
					setKicker(getCardAt(0));
				}
			}
			else if (getCardAt(0).getNumValue()==getCardAt(1).getNumValue()){
				handSet="Pair";
				if (getCardAt(2).getNumValue()==getCardAt(3).getNumValue()){
					handSet="Two Pair";
				}
				else if (getCardAt(3).getNumValue()==getCardAt(4).getNumValue()){
					handSet="Two Pair";
				}
			}
			else if (getCardAt(1).getNumValue()==getCardAt(2).getNumValue()){
				handSet="Pair";
				if (getCardAt(3).getNumValue()==getCardAt(4).getNumValue()){
					handSet="Two Pair";
				}
			}
			else if (getCardAt(2).getNumValue()==getCardAt(3).getNumValue()){
				handSet="Pair";
			}
			else if (getCardAt(3).getNumValue()==getCardAt(4).getNumValue()){
				handSet="Pair";
			}
		}
		//generates numerical value using "american" values of hands
		switch (handSet) {
			case "No Pair": setHandScore(0); 
				break;
			case "Pair": setHandScore(2); 
				break;
			case "Two Pair": setHandScore(5); 
				break;
			case "Three Of A Kind": setHandScore(10); 
				break;
			case "Straight": setHandScore(15); 
				break;
			case "Flush": setHandScore(20); 
				break;
			case "Full House": setHandScore(25); 
				break;
			case "Four Of A Kind": setHandScore(50); 
				break;
			case "Straight Flush": setHandScore(75); 
				break;
			case "Royal Flush": setHandScore(100); 
				break;
		}
		return handSet;
	}
}