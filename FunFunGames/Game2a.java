/**
 * Kevin
 * Game2a.java
 * this is game
 */
import java.awt.*;
import javax.imageio.*;
import java.io.*;
import java.util.*;
import java.awt.event.*;
import javax.swing.*;
import javax.sound.sampled.*;
public class Game2a extends JPanel implements ActionListener 
{
     int length=10;
     boolean[]vis=new boolean[length];
     int best,moves=0;
	 JButton[] buttons = new JButton[length];
	 ImageIcon red=new ImageIcon("red_light.png"),green=new ImageIcon("green_light.png");
	 int w=1080,h=720;
	 boolean end=false;
	 JLabel congrats,movel,left,title,score;
	 JButton cont;
	 Clip music;
	//changes when a button is pressed
	@Override
	public void actionPerformed(ActionEvent ae){
		if(end){
			if(ae.getActionCommand().equals("continue")) {
				for(int i=0;i<length;i++)buttons[i].setVisible(false);
				cont.setActionCommand("flipped");
				score.setVisible(true);
				score.setText("<html><center>Moves: "+Integer.toString(moves)+" / "+Integer.toString(best)+"                     Score: "+Integer.toString((int)(Main.score2a))+"%<center><html>");
			}
			if(ae.getActionCommand()=="flipped"){
				Main.change("menu");
				music.stop();
			}
			return;
		}
		int id=Integer.parseInt(ae.getActionCommand());
		move(id);
		moves++;
		update();
		if(eval()){
			end=true;
			congrats.setVisible(true);
			cont.setVisible(true);
			Main.score2a=Math.pow((double)best/moves,1)*100;
			title.setForeground(Color.green);
		}
	}
	//rescales an imageicon
	public ImageIcon scale(ImageIcon ic,int w,int h) throws Exception
	{
		Image im=ic.getImage();
		im=im.getScaledInstance(w,h,Image.SCALE_SMOOTH);
		return new ImageIcon(im);
	}
	Game2a() throws Exception{
		int size=80;
		int spacing=95;
		int upmarg=200;
		int leftmarg=55;
		red=scale(red,size,size);
		green=scale(green,size,size);
		setLayout(null);
		setBackground(Color.white);
		int i,j;
		//congrats label
		congrats=new JLabel("CONGRATULATIONS!!");
		congrats.setFont(new Font("Consolas",Font.BOLD,40));
		congrats.setBounds(0270,100,500,100);
		add(congrats);
		//score label
		score=new JLabel("");
		score.setFont(new Font("Consolas",Font.BOLD,30));
		score.setBounds(400,400,1080,100);
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

		//initialization and display of the buttons array
		for(i=0;i<length;i++){
			buttons[i]=new JButton();
			buttons[i].setOpaque(false);
			buttons[i].setActionCommand(Integer.toString(i));
			buttons[i].addActionListener(this);
			buttons[i].setContentAreaFilled(false);
			buttons[i].setBorderPainted(false);
			buttons[i].setIcon(red);
			buttons[i].setBounds(leftmarg+spacing*i,h/2-size/2,size,size);
			add(buttons[i]);
		}
		//continue with the main or caller
		setVisible(false);
	}
	public  boolean eval ()
	{
		boolean b = true;
		for (int i = 0 ; i < length ; i++)
			b &= vis [i];
		return b;
	}

	public  int solve ()
	{
		int i;
		boolean[] sim=new boolean[length];
		for(i=0;i<length;i++) sim[i]=vis[i];
		int count=0;
		for(i=1;i<length;i++) {
			if(!sim[i-1]) {
				count++;
				sim[i-1]=true;
				sim[i]=!sim[i];
				if(i+1<length)sim[i+1]=!sim[i+1];
			}
		}
		if(sim[length-1]) return count;
		count=1;
		for(i=0;i<length;i++) sim[i]=vis[i];
		sim[0]=!sim[0];
		sim[1]=!sim[1];
		for(i=1;i<length;i++) {
			if(!sim[i-1]) {
				count++;
				sim[i-1]=true;
				sim[i]=!sim[i];
				if(i+1<length)sim[i+1]=!sim[i+1];
			}
		}
		return count;
	}
	public  void gen ()
	{
		for (int i = 0 ; i < length ; i++) {
			vis [i] = (Math.random()<0.5);
		}
		best=solve();
		best=best;
	}

	public  void move(int tar) {
		if(tar>0) vis[tar-1]=!vis[tar-1];
		vis[tar]=!vis[tar];
		if(tar<length-1) vis[tar+1]=!vis[tar+1];
	}
	public  void update(){
		for(int i=0;i<length;i++){
			if(vis[i]) buttons[i].setIcon(green);
			else buttons[i].setIcon(red);
		}
		movel.setText("Moves: "+moves);
		left.setText("Moves to Finish: "+solve());
	}

    public void start ()
    {
		//Music
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
		"Clicking on a box changes all the lights in a 3 x 1 area from red to green or from green to red.\n" + 
		"The move count at the top shows how many moves have been made");

		//clears the screen
		moves=0;
		Menu.isG2A = true;
        //show labels and buttons
		congrats.setVisible(false);
		left.setVisible(true);
		cont.setVisible(false);
		for(int i=0;i<length;i++)buttons[i].setVisible(true);
		end=false;
		//generates a solvable state that requires at least 18 moves
		do
		{
			gen ();
		}
		while (solve() < 5);
		best=solve();
		update();
		//show this panel
		Main.change("g2a");
    }
}