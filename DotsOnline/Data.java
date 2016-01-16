/*
 * Data.java
 *
 * Version:$Id: Dots.java,v 1.7 2013/11/26  $
 *     
 *
 * Revisions:25
 *     
 */
import java.io.Serializable;

/**
 * This class represents status of board.
 *
 * @author      Ruturaj
 * @author      Shivangi
 *
 */
public class Data implements Serializable{
	boolean horizontalLines[][]= new boolean[4][3];
	boolean verticalLines[][]= new boolean[3][4];
	final boolean player1=true,player2=false;
	boolean currentPlayer=player1;
	int wonBlock[][]=new int[3][3];
	int player1Points,player2Points;
	
	/**
	 * This method determines whether all 
	 * the blocks of game are completed
	 *
	 */
	public boolean finished(){
		for(int i=0;i<3;i++){
			for(int j=0;j<3;j++){
				if(wonBlock[i][j]==0) //if any block left
					return false;
			}
		}
		return true; //game finished
	}

	public boolean calculatePoints() {
		boolean scored=false;
		for(int i=0;i<3;i++){
			for(int j=0;j<3;j++){
				//if four borders of block completed
				if(wonBlock[i][j]==0 && horizontalLines[i][j] && horizontalLines[i+1][j] && verticalLines[i][j] && verticalLines[i][j+1]){
					if(currentPlayer==player1){
						player1Points++; //player2 points +1
						wonBlock[i][j]=1; 
					}else{
						player2Points++; //player2 points +1
						wonBlock[i][j]=2; 
					}
					scored=true;
				}
			}
		}
		return scored;
	}
	
}
