import java.util.*;

public class Game {
	private Card[] deck=new Card[52];
	private Random rgen=new Random();
	private ArrayList<Player> players = new ArrayList<Player>();
	private int currentTopIndex; 
	
	public Game() {
		currentTopIndex=0;
	}

	
	public static void main(String[] args){
		//Set-up
		Game g = new Game();
		g.newDeck();
		g.shuffleDeck();
		Player Bob = new Player("Bob");
		Player Sally = new Player("Sally");
		g.addPlayer(Bob);
		g.addPlayer(Sally);
		g.dealCards();
		
		//Gameplay
		g.takeTurn(Bob);
		g.takeTurn(Sally);
		
		//Aftermath

		System.out.println(g.determineWinner());
	}
	
	public void printDeck(){
		for(int i=0; i<getDeck().length;i++)
		{
			System.out.println(getCardAt(i));
		}
	}
	
	public void newDeck(){
		System.out.println("A deck has been generated.");
		for(int i=0; i<52;i++){
			if(i<13){
				int temp=(i*4)+4;
				deck[i]=new Card(i+1,1,temp);
			}			
			else if(i<26){
				int temp1=i-13;
				int temp2=(temp1*4)+3;
				deck[i]=new Card(temp1+1,2,temp2);
			}			
			else if(i<39){
				int temp1=i-26;
				int temp2=(temp1*4)+2;
				deck[i]=new Card(temp1+1,3,temp2);
			}			
			else if(i<52){
				int temp1=i-39;
				int temp2=(temp1*4)+1;
				deck[i]=new Card(temp1+1,4,temp2);
			}
		}
	}
	
	public Card[] getDeck(){
		return deck;
	}
	
	public Card getCardAt(int i){
		return deck[i];
	}
	
	public void shuffleDeck(){
		System.out.println("");
		System.out.println("The deck has been shuffled.");
		for(int i=0;i<getDeck().length;i++){
			int r=rgen.nextInt(getDeck().length);
			Card temp=deck[i];
			deck[i]=deck[r];
			deck[r]=temp;
		}
	}
	
	public void addPlayer(Player p){
		System.out.println("A new player has been added.");
		getPlayers().add(p);
	}
	
	public ArrayList<Player> getPlayers(){
		return players;
	}
	
	public int getNumPlayers(){
		return players.size();
	}
	
	public int getCurrentTopI(){
		return currentTopIndex;
	}
	
	public void advanceCurrentTopI(int i){
		currentTopIndex=currentTopIndex+i; 
	}
	
	public void takeTurn(Player p){
		System.out.println("");
		System.out.println(p.getName().toUpperCase());
		p.takeTurn();
		while(p.getHandSize()<5)
		{
			p.getHand().add(drawNextCard());
		}
		System.out.println(p.prettyHand());
	}
	public Card drawNextCard(){
		int temp=getCurrentTopI();
		advanceCurrentTopI(1);
		return deck[temp];
	}
		
	public void dealCards(){
		int p=getNumPlayers();
		for(int i=0;i<p;i++){
			getPlayers().get(i).getHand().add(getCardAt(i));
			getPlayers().get(i).getHand().add(getCardAt(i+p));
			getPlayers().get(i).getHand().add(getCardAt(i+2*p));
			getPlayers().get(i).getHand().add(getCardAt(i+3*p));
			getPlayers().get(i).getHand().add(getCardAt(i+4*p));
		}
		
		advanceCurrentTopI(10);
		System.out.println("The cards have been dealt.");
	}
	
	public String determineWinner(){
		for (int i=0;i<players.size();i++){
			players.get(i).scoreHand();
		}
		int temp=0;
		String winner="";
		for (int i=0;i<players.size();i++)
		{
			int temp2=players.get(i).getHandScore();
			if (temp2>temp){
				temp=temp2;
				winner=players.get(i).getName();
			}
			else if (temp2==temp){
				winner="TIE";
			}
		}
		//this function evaluates solely on hand score
		//therefore a pair of aces is considered to tie with a pair of twos
		return winner;
	}	
}