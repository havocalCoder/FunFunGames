/**
 * Kevin
 * Game2b.java
 * this is game
 */
import java.awt.*;
import javax.imageio.*;
import java.io.*;
import java.util.*;
import java.awt.event.*;
import javax.swing.*;
import javax.sound.sampled.*;

public class Game2b extends JPanel implements ActionListener 
{
     int width=10,height=5;
     boolean[][]vis=new boolean[width][height];
     int best,moves=0;
	 JButton[][] buttons = new JButton[width][height];
	 ImageIcon red=new ImageIcon("red_light.png"),green=new ImageIcon("green_light.png");
	 int w=1080,h=720;
	 boolean end;
	 JLabel congrats,movel,left,title,info,score;
	 JButton cont,act;
	 Clip music;
	//changes when a button is pressed
	@Override
	public void actionPerformed(ActionEvent ae){
		if(end){
			if(ae.getActionCommand().equals("continue")) {
				for(int i=0;i<width;i++)for(int j=0;j<height;j++) buttons[i][j].setVisible(false);
				cont.setActionCommand("flipped");
				score.setVisible(true);
				score.setText("Moves: "+Integer.toString(moves)+" / "+Integer.toString(best)+"                     Score: "+Integer.toString((int)(Main.score2b))+"%");
			}
			if(ae.getActionCommand()=="flipped"){
				Main.change("menu");
				music.stop();
			}
			return;
		}
		if(ae.getActionCommand().equals("activate")){
			left.setVisible(true);
			act.setVisible(false);
			moves+=20;
			update();
			return;
		}
		String[] s;
		s=ae.getActionCommand().split(" ");
		int x=Integer.parseInt(s[0]),y=Integer.parseInt(s[1]);
		change(vis,x,y);
		moves++;
		update();
		if(moves==10) act.setVisible(true);
		if(eval(vis)){
			end=true;
			congrats.setVisible(true);
			cont.setVisible(true);
			act.setVisible(false);
			Main.score2b=Math.pow((double)best/moves,0.6)*100;
			title.setForeground(Color.green);
		}
	}
	//updates the graphics of the panel
	public void update(){
		for(int i=0;i<width;i++){
			for(int j=0;j<height;j++){
				if(vis[i][j]) buttons[i][j].setIcon(green);
				else buttons[i][j].setIcon(red);
			}
		}
		movel.setText("Moves: "+moves);
		left.setText("Moves to Finish: "+solve());
	}
	//rescales an imageicon
	public ImageIcon scale(ImageIcon ic,int w,int h) throws Exception
	{
		Image im=ic.getImage();
		im=im.getScaledInstance(w,h,Image.SCALE_SMOOTH);
		return new ImageIcon(im);
	}
	Game2b() throws Exception{
		int size=80;
		int spacing=95;
		int upmarg=200;
		int leftmarg=55;
		red=scale(red,size,size);
		green=scale(green,size,size);
		setLayout(null);
		setBackground(new Color(150,150,150));
		int i,j;
		//congrats label
		congrats=new JLabel("CONGRATULATIONS!!");
		congrats.setFont(new Font("Consolas",Font.BOLD,40));
		congrats.setBounds(0270,100,500,100);
		add(congrats);
		//score label
		score=new JLabel("");
		score.setFont(new Font("Consolas",Font.BOLD,30));
		score.setBounds(0,400,1080,100);
		add(score);
		//moves label
		movel=new JLabel("Moves: ");
		movel.setFont(new Font("Consolas",Font.BOLD,20));
		movel.setBounds(100,000,300,100);
		add(movel);
		//left label
		left=new JLabel("Moves to Finish: ");
		left.setFont(new Font("Consolas",Font.BOLD,20));
		left.setBounds(830,20,250,75);
		add(left);
		//title label
		title=new JLabel("Flashing Lights");
		title.setForeground(Color.red);
		title.setFont(new Font("Consolas",Font.BOLD,65));
		title.setBounds(270,000,540,100);
		add(title);
		//continue button
		cont=new JButton("Continue");
		cont.setFont(new Font("Consolas",Font.BOLD,20));
		cont.setBounds(850,100,150,75);
		cont.setActionCommand("continue");
		cont.addActionListener(this);
		add(cont);
		//activation button
		act=new JButton("<html><center>Unlock <br />\"Moves to Finish\"<br />Waste 20 Moves<center>");
		act.setFont(new Font("Consolas",Font.BOLD,15));
		act.setBounds(830,20,200,75);
		act.setActionCommand("activate");
		act.addActionListener(this);
		add(act);
        
		//initialization and display of the buttons array
		for(i=0;i<width;i++){
			for(j=0;j<height;j++){
				buttons[i][j]=new JButton();
				buttons[i][j].setOpaque(false);
				buttons[i][j].setActionCommand(Integer.toString(i)+" "+Integer.toString(j));
				buttons[i][j].addActionListener(this);
				buttons[i][j].setContentAreaFilled(false);
				buttons[i][j].setBorderPainted(false);
				buttons[i][j].setIcon(red);
				buttons[i][j].setBounds(leftmarg+spacing*i,upmarg+spacing*j,size,size);
				add(buttons[i][j]);
			}
		}
		do
		{
			gen ();
		}
		while (solve() < 18);
		//continue with the main or caller
		setVisible(false);
	}
	//checks if the array is a solved state
    public boolean eval (boolean[] [] arr)
    {
        boolean b = true;
        for (int i=0;i<width;i++)
            for (int j = 0 ; j < height ; j++)
                b &= arr [i] [j];
        return b;
    }

	//applies one move at a certain location (flip all cells adjacent to it
    public void change (boolean[] [] arr, int x, int y)
    {
        for (int i = -1 ; i <= 1 ; i++)
            for (int j = -1 ; j <= 1 ; j++)
                if (x + i >= 0 && x + i < width && y + j >= 0 && y + j < height)
                    arr [x + i] [y + j] = !arr [x + i] [y + j];
    }

	//calculates the optimal number of moves to solve
    public int solve ()
    {
        int i, j, k, count,minimum=0x3f3f3f3f;
        boolean[] [] sim = new boolean [width] [height];
		//guess the outer edges of the grid and then bitmask to store in one variable
        for (i = 0 ; i < 1<<(width+height-1) ; i++)
        {
			//iterate through each combination
        	for(j=0;j<width;j++) for(k=0;k<height;k++) sim[j][k]=vis[j][k];
            count = 0;
			//solve as if the guessed moves were all necessary
            for (j = 0 ; j < width+height-1 ; j++)
            {
				//only one move can affect each cell after the heuristic
                if ((i >> j) % 2 == 1)
                {
                    if (j < height) change (sim, 0, j);
                    if (j >= height) change (sim, j - height+1, 0);
                    count++;
                }
            }
			//count the necessary switches
            for (j = 1 ; j < width; j++)
            {
                for (k = 1 ; k < height ; k++)
                {
                    if (!sim [j - 1] [k - 1])
                    {
                        count++;
                        change (sim, j, k);
                    }
                }
            }
			//finds the minimum of all the iterations
            if (eval (sim)) minimum=Math.min(minimum, count);
        }
        return minimum;
    }
	//generates the initial position
    public void gen ()
    {
		for (int i=0;i<100;i++)change(vis,(int)(Math.random()*width),(int)(Math.random()*height));
    }

    public void start ()
    {
		try {
			AudioInputStream in = AudioSystem.getAudioInputStream(this.getClass().getResource("puzzle_music.wav"));
			music = AudioSystem.getClip();
			music.open(in);
			music.start();
			music.loop(Clip.LOOP_CONTINUOUSLY);
		} catch(Exception e) {

		}

		JOptionPane instructions = new JOptionPane("Instructions");
		JOptionPane.showMessageDialog(instructions, "Your objective is to turn all lights from red to green.\n" +
		"Clicking on a box changes all the lights in a 3 x 3 area from red to green or from green to red.\n" + 
		"The move count at the top shows how many moves have been made\n" +
		"You can choose to pay 20 moves to show the optimal amount of moves to solving the puzzle");

		Main.change("g2b");
		Menu.isG2B = true;
		//clears the screen
		moves=0;
        //show labels and buttons
		congrats.setVisible(false);
		left.setVisible(false);
		cont.setVisible(false);
		act.setVisible(false);
		for(int i=0;i<width;i++)for(int j=0;j<height;j++) buttons[i][j].setVisible(true);
		score.setVisible(false);
		end=false;
		//generates a solvable state that requires at least 18 moves
		best=solve();
		update();
		//show this panel
    }
}