/**
 * George
 * Game1a.java
 * this is game
 */
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;

import javax.swing.*;
import javax.sound.sampled.*;

public class Game1a extends JPanel {
    
    public JLabel p1 = new JLabel("Player Score: ");
    public JLabel p2 = new JLabel("AI Score: ");
	public JButton a = new JButton("Left");
    public JButton b = new JButton("Right");
	public JLabel num = new JLabel("");
	public JButton exit = new JButton("Back");
	
	public int left, right;
	public int sc1 = 0, sc2 = 0;
	public boolean flag = false;
	
	final int range = 12;
	int[][] dp = new int[range+2][range+2], ch = new int[range+2][range+2];
	int[] visible = new int[range+2], arr = new int[range+2];

	Clip music;
	
	//********************* general functions *********************//
	
	public static int rand(int x,int y){
		return (int)(Math.random()*(y-x+1)+x);
	}
	
	public static void sleep(int m){
		try{Thread.sleep(m);}
		catch(InterruptedException e){}
	}
	
	// ******************** game 1 ********************************//
	
	public int Score(int l,int r){
		if(ch[l][r]==0) l++;
		else r--;
		if(l>r) return 0;
		return dp[l][r];
	}
	
	public void AI_Solve(int l,int r){
		if(l > r) return;
		if(dp[l][r] != -1) return;
		AI_Solve(l+1,r); AI_Solve(l,r-1);
		if(arr[l]+Score(l+1,r)>arr[r]+Score(l,r-1))
			ch[l][r] = 0;
		else ch[l][r] = 1;
		dp[l][r] = Math.max(arr[l]+Score(l+1,r),arr[r]+Score(l,r-1));
	}
	
	public void disp(int sc1,int sc2){
		p1.setText("Player: "+Integer.toString(sc1));
		p2.setText("AI:"+Integer.toString(sc2));
		
		String txt="";
		for(int i=1;i<=range;i++){
			if(visible[i]==1) txt += Integer.toString(arr[i]);
			else{
				for(int j=0;j<=Integer.toString(arr[i]).length();j++)
					txt += " ";
			}
			txt += "     ";
		}
		num.setText(txt);
		repaint();
	}
	
	Game1a() throws Exception{		
	
		// adding components to JPanel
		setLayout(null);
		
		add(p1);
		p1.setFont(new Font(p1.getFont().getName(), Font.PLAIN, 30));
		p1.setBounds(0,0,300,50);
		
		add(p2);
		p2.setFont(new Font(p2.getFont().getName(), Font.PLAIN, 30));
		p2.setBounds(860,0,200,50);
		
		add(num);
		num.setBounds(100,400,890,100);
		num.setFont(new Font(num.getFont().getName(), Font.PLAIN, 30));
		
		add(a);
		a.setBounds(0,600,200,50);
		
		add(b);
		b.setBounds(820,600,200,50);
		
		add(exit);
		exit.setBounds(410,600,200,50);
		exit.setVisible(false);
		
		// what to do if buttons are clicked
		a.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				sc1 += arr[left]; visible[left]=0; left++;
				disp(sc1,sc2); AI_Move();
			}
		});
		
		b.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				sc1 += arr[right]; visible[right]=0; right--;
				disp(sc1,sc2); AI_Move();
			}
		});
		
		exit.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				Main.change("menu");
				music.stop();
			}
		});

	}
	
	public void AI_Move(){
		
		// computer's turn
		if(ch[left][right]==0){sc2+=arr[left]; left++; visible[left-1]=0;}
		else{sc2+=arr[right]; right--; visible[right+1]=0;}
		disp(sc1,sc2);
		
		
		if(left > right) end();
	}
	
	public void start(){
		try {
			AudioInputStream in = AudioSystem.getAudioInputStream(this.getClass().getResource("puzzle_music.wav"));
			music = AudioSystem.getClip();
			music.open(in);
			music.start();
			music.loop(Clip.LOOP_CONTINUOUSLY);
		} catch(Exception e) {

		}

		JOptionPane instructions = new JOptionPane("Instructions");
		JOptionPane.showMessageDialog(instructions, "Compete against the AI to get the higher score\n" +
		"Take numbers from the left or right of the row of numbers\n" +
		"The number you take is added to your score");

		Menu.isG1A = true;

	
		// initialize
		for(int i=1;i<=range;i++){
			for(int j=1;j<=range;j++){
				dp[i][j] = -1;
				ch[i][j] = -1;
			}
		}
		for(int i=1;i<=range;i++)
			visible[i] = 1;
		
		// generate random values for array
		for(int i=1;i<=range;i++)
			arr[i] = rand(0,range);
		
		// calculate answer for every situation
		AI_Solve(1,range);
		
		left = 1; right = range;	
		
		disp(0,0);
	}
	
	public void end(){
		exit.setVisible(true);
		p1.setText(""); 
		p2.setText("");
		if(sc1 > sc2) num.setText("                                       You Win!");
		else if(sc1 == sc2) num.setText("                                           Tie!");
		else num.setText("                                       You Lost!");
		repaint();
		sleep(1000);
	}

}