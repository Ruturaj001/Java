/*
 * MyBoardC.java
 *
 * Version:$Id: Dots.java,v 1.7 2013/11/26  $
 *     
 *
 * Revisions:25
 *     
 */
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JPanel;
/**
 * This class creates view for Dots game.
 *
 * @author      Ruturaj
 * @author      Shivangi
 *
 */
public class MyBoardC extends JPanel{
	DotsC controler;

	/**
	 * Constructor for MyBoard
	 *
	 * @param    game    reference to controller
	 *           
	 */
	public MyBoardC(DotsC game) { 
		addMouseListener(new ML());
		controler=game;
	}

	/**
	 * This method presents the view of game
	 *
	 * @param     g   object of Graphics 
	 */
	public void paintComponent(Graphics g){
		this.setForeground(Color.WHITE);
		g.setColor(Color.white);
		g.fillRect(0, 0, 430, 550);
		String str="Player1: "+controler.data.player1Points;
		String str2="Player2: "+controler.data.player2Points;
		//Color of dot to determine active player
		if(controler.data.currentPlayer==controler.data.player1){   
			g.setColor(Color.red);
			g.fillOval(30, 435, 10, 10);
		}else{
			g.setColor(Color.blue);
			g.fillOval(30, 485, 10, 10);
		}
		g.setColor(Color.black);
		g.setFont(new Font("TimesRoman",Font.PLAIN, 30)); 
		g.drawString(str, 50, 450);
		g.drawString(str2, 50, 500);
		//declares the winner
		if(controler.data.finished()){
			if(controler.data.player1Points>controler.data.player2Points){
				str="Player 1";   //player 1 won
				g.setColor(Color.RED);
			}
			else {
				str="Player2"; //player 2 won
				g.setColor(Color.BLUE);
			}
			str2="has WON";
			g.drawString(str, 270, 450);
			g.drawString(str2, 250, 500);
		}

		//fills the block if four of its borders completes
		for(int i=0;i<3;i++){
			for(int j=0;j<3;j++){
				if(controler.data.wonBlock[i][j]==1){
					g.setColor(Color.red);
					g.fillRect((j*100)+65, (i*100)+65, 90, 90);
				}

				else if(controler.data.wonBlock[i][j]==2){
					g.setColor(Color.blue);
					g.fillRect((j*100)+65, (i*100)+65, 90, 90);
				}
			}
		}
		//draws lines between two dots, as per mouse click
		g.setColor(Color.black);
		for(int i=0;i<3;i++){
			for(int j=0;j<4;j++){
				if(controler.data.horizontalLines[j][i])
					g.fillRect((i*100)+60, (j*100)+55,100,10);
				if(controler.data.verticalLines[i][j])
					g.fillRect((j*100)+55, (i*100)+55,10,100);
			}
		}
		for(int i=0;i<4;i++){// intial dots game structure
			for(int j=0;j<4;j++){
				g.fillOval(50+(i*100), 50+(j*100), 20, 20);
			}
		}
	}

	/**
	 * This class implements the mouse listener to get user input.
	 *
	 * @author      Ruturaj
	 * @author      Shivangi
	 *
	 */
	class ML implements MouseListener{
		/**
		 * This method performs operations when mouse click event
		 *  happens on board, draws a line b/w corresonding points 
		 *
		 * @param     MouseEvent   passes the source to calling method
		 * 
		 */

		@Override
		public void mouseClicked(MouseEvent e) {
			controler.p=e.getPoint();
			if(controler.isValid()){
				controler.points();
				repaint();
			}
		}
		@Override
		public void mouseEntered(MouseEvent e) {}
		@Override
		public void mouseExited(MouseEvent e) {}
		@Override
		public void mousePressed(MouseEvent e) {}
		@Override
		public void mouseReleased(MouseEvent e) {}
	}

}

