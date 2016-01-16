/*
 * DotsC.java
 *
 * Version:$Id: Dots.java,v 1.7 2013/11/26  $
 *     
 *
 * Revisions:25
 *     
 */

import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.JButton;
import javax.swing.JFrame;
/**
 * This class implements client(and Player2) of two player dots game.
 *
 * @author      Ruturaj
 * @author      Shivangi
 *
 */

public class DotsC{
	Data data= new Data();
	MyBoardC view = new MyBoardC(this);
	Point p=null;
	Socket s;
	ObjectInputStream read;
	ObjectOutputStream write;
	JButton button=new JButton("New Game");

	DotsC(String ip){
		Runnable input=new IncomingReader(ip);
		Thread name=new Thread(input);
		name.start();	
	}


	/**
	 * The main program.
	 *
	 * @param    args    command line arguments (ignored)
	 */
	public static void main(String[] args) {
		DotsC controler=new DotsC(args[0]);
		controler.go();
	}

	/**
	 * This method gets elements on the frame
	 *
	 */
	void go(){
		JFrame frame = new JFrame("Dots(Player2)");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		view.add(button);
		button.addMouseListener(new ML(this));
		frame.getContentPane().add(view);
		frame.setSize(430,550);
		frame.setResizable(false);
		frame.setVisible(true);
	}

	/**
	 * This method restarts the game when new game button is clicked
	 *
	 */
	public void newGame(){
		data=new Data();
	}

	/**
	 * This method checks if player has scored something
	 *
	 */
	public void points() {
		if(!data.calculatePoints()) //if  no one scored
			data.currentPlayer=data.player1;
		try {
			write.reset();
			write.writeObject(data);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}

	/**
	 * This method checks if click is in valid
	 *  range 
	 *
	 */
	public boolean isValid(){
		int i,j;
		if(data.currentPlayer==data.player1)//player1's turn
			return false;
		for(i=0;i<3;i++){
			for(j=0;j<4;j++){
				//horizontal line ((y1*100)+70, (x1*100)+50,80,20);
				//if click is in range of horizontal line
				if(p.x>((i*100)+70) && p.x<((i*100)+150) && p.y>((j*100)+50) && p.y<((j*100)+70)){
					if(!data.horizontalLines[j][i]){
						data.horizontalLines[j][i]=true;
						return true;
					}else
						return false;
				}
				// vertical line((y1*100)+50, (x1*100)+70,20,80)
				//if click is in range of vertical line
				if(p.x>((j*100)+50) && p.x<((j*100)+70) && p.y>((i*100)+70) && p.y<((i*100)+150)){
					if(!data.verticalLines[i][j]){
						data.verticalLines[i][j]=true;
						return true;
					}else
						return false;
				}
			}
		}
		//if invalid, no line drawn
		return false;
	}	

	/**
	 * This class implements the mouse listener to get user input for new game.
	 *
	 * @author      Ruturaj
	 * @author      Shivangi
	 *
	 */
	public class ML implements MouseListener{
		DotsC controler;
		ML(DotsC d){
			controler=d;
		}
		/**
		 * This method performs operations when mouse
		 * click event happens on button
		 *
		 * @param     MouseEvent   passes the source to calling method
		 * 
		 */
		@Override
		public void mouseClicked(MouseEvent arg0) {
			controler.newGame();
			try {
				controler.write.reset();
				controler.write.writeObject(controler.data);
				System.out.println("Client sent the data");
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			controler.view.repaint();
		}
		@Override
		public void mouseEntered(MouseEvent arg0) {}
		@Override
		public void mouseExited(MouseEvent arg0) {}
		@Override
		public void mousePressed(MouseEvent arg0) {}
		@Override
		public void mouseReleased(MouseEvent arg0) {}

	}
	public class IncomingReader implements Runnable{
        
        String ip;
        
        IncomingReader(String ip){
            this.ip = ip;
        }
        
		public void run(){
			try {
				s = new Socket(ip,7689);
				read = new ObjectInputStream(s.getInputStream());
				write = new ObjectOutputStream(s.getOutputStream());
				while(true){
					Data test = null;
					test=(Data) read.readObject();
					if(test!=null){
						data=test;
						view.repaint();
					}
				}
			} catch (UnknownHostException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		}
	}
}
