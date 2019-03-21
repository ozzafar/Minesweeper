import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;


@SuppressWarnings("serial")
public class Board extends JFrame  {
	
	public static Board board;
	public static int SIZE ;
	public static int NUM_OF_BOMBS ;

	
	public JPanel panel = new JPanel();
	public  MS_Button buttons[][] = new MS_Button[SIZE][SIZE];
	public Random random1=new Random(),random2=new Random();
	public boolean over=false;
	public int gamecounter=0;
	
	public Board() {
		super("Board");
		setSize(600, 600);
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		panel.setLayout(new GridLayout(SIZE, SIZE));
		
		for (int i = 0; i < SIZE; i++) {
			for (int j = 0; j < SIZE; j++) {
				buttons[i][j] = new MS_Button(i,j);
				buttons[i][j].addActionListener(new Action());
				buttons[i][j].addMouseListener(new Action());
				panel.add(buttons[i][j]);
			}
		}
		
		add(panel);
	
		for (int i = 0; i < NUM_OF_BOMBS; i++) {
			int x=random1.nextInt(SIZE-1);
			int y=random2.nextInt(SIZE-1);
			if (buttons[x][y].IsABomb == false) {
				buttons[x][y].IsABomb =true;
				StartSurroundedButtonsUltimate(x,y);
			}
			else
				i--;
		}
		setVisible(true);

	}
	
	public void StartSurroundedButtons(int i, int j ) {
		try {
			buttons[i][j].counter++;
	
		}
		catch (IndexOutOfBoundsException e){
		}
	}

	
	public void StartSurroundedButtonsUltimate(int x, int y ) {
		StartSurroundedButtons(x+1,y+1);
		StartSurroundedButtons(x+1,y);
		StartSurroundedButtons(x+1,y-1);
		StartSurroundedButtons(x,y+1);
		StartSurroundedButtons(x,y-1);
		StartSurroundedButtons(x-1,y+1);
		StartSurroundedButtons(x-1,y);
		StartSurroundedButtons(x-1,y-1);
	}
	
	public void PushSurroundedButtons(int i, int j ) {
		try {
			MS_Button bt = buttons[i][j];
			if (!bt.Clicked)
				bt.doClick();
	
		}
		catch (IndexOutOfBoundsException e){
		}
	}
	
	public void PushSurroundedButtonsUltimate(int x, int y) {
		PushSurroundedButtons(x+1,y+1);
		PushSurroundedButtons(x+1,y);
		PushSurroundedButtons(x+1,y-1);
		PushSurroundedButtons(x,y+1);
		PushSurroundedButtons(x,y-1);
		PushSurroundedButtons(x-1,y+1);
		PushSurroundedButtons(x-1,y);
		PushSurroundedButtons(x-1,y-1);
	}
	
	private class Action implements ActionListener, KeyListener , MouseListener {
	
		public void SendText(JLabel lb) {
			JFrame f = new JFrame("");
			f.setSize(400, 100);
			f.setLocationRelativeTo(null);
			JPanel panel = new JPanel();
			f.add(panel);
			panel.add(lb,CENTER_ALIGNMENT);
			f.addKeyListener(this);
			f.setVisible(true);
		}
		
		@Override
		public void actionPerformed(ActionEvent e)  {
			MS_Button MyButton = (MS_Button) (e.getSource());
			MyButton.Clicked=true;
			MyButton.setBackground(Color.GRAY);
			if (!MyButton.IsABomb) {
				if (MyButton.Flagged)
					MyButton.setIcon(null);
				gamecounter++;
				if (MyButton.counter==0) {
					int x = MyButton.x;
					int y = MyButton.y;
					PushSurroundedButtonsUltimate(x, y);
				}
				else
					MyButton.setText("" + MyButton.counter);
				if (gamecounter == SIZE*SIZE - NUM_OF_BOMBS) {
					over=true;
					JLabel l = new JLabel("YOU WIN! Enter SPACE to start again!");
					SendText(l);
				}
					
			}

			else {
				MyButton.setIcon(new ImageIcon("src/resources/bomb.png"));
				over=true;
				JLabel l = new JLabel("YOU LOSE! Enter SPACE to start again!");
				SendText(l);
				}
			}

		@Override
		public void keyTyped(KeyEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void keyPressed(KeyEvent e) {
			int i = e.getKeyCode();
			if (i == KeyEvent.VK_SPACE && over) {
				((JFrame)e.getSource()).setVisible(false);
				board.setVisible(false);
				board = new Board();			
			}
		}

		@Override
		public void keyReleased(KeyEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseClicked(MouseEvent e) {
			MS_Button bt = (MS_Button) e.getSource();
			if (!bt.Clicked) {
				if (e.getButton() == 3) { // if right click
					if (!bt.Flagged) {
						bt.setIcon(new ImageIcon("src/resources/flag.png"));
						bt.Flagged = true;
					} else {
						bt.setIcon(null);
						bt.Flagged = false;

					}
				}
			}
		}
	

		@Override
		public void mousePressed(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}
	}
		
	public static void main(String[] args) {
		SIZE = Integer.parseInt(JOptionPane.showInputDialog(null,"Enter Size of the board :"));
		NUM_OF_BOMBS = Integer.parseInt(JOptionPane.showInputDialog(null,"Enter number of bombs (between 1 to " + SIZE + "):"));
		if (NUM_OF_BOMBS>=1 && NUM_OF_BOMBS<=SIZE)
			board = new Board();
		else
			System.out.println("Wrong values!");
	}

	
}
