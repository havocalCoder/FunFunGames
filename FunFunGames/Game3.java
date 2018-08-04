/**
 * EVERYONE
 * Game3.java
 * This is THE GAMUUUUUUUUU
 * Have a nice time
 * The greatest FUN/RAGE
 */
import java.util.*;
import java.awt.event.*;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import javax.imageio.*;
import java.io.*;
import javax.sound.sampled.*;
public class Game3 extends JPanel implements KeyListener
{
	int view=3,total_moves=0;
	boolean hunger=false;
	final int size = 62;
	double hallucination;
	int[][] arr = new int[size+5][size+5];
	int[][] vis = new int[size+5][size+5];
	int[][] adj = new int[size+5][size+5];
	int[][] solve = new int[size+5][size+5];
	// possible moves
	int[] dx = {1, -1, 0, 0};
	int[] dy = {0, 0, 1, -1};
	int x = 1, y = 1, next_change, lx=1, ly=1;
	//changes when a relevent key is pressed
	Image wall,grass,flag;
	BufferedImage im = new BufferedImage (1080,720, BufferedImage.TYPE_INT_ARGB); //BufferedImage
	Graphics2D g;
	JLabel hallu,sight,hung;
	boolean dead = false;
	Clip music;
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(im, 0, 0, null);           
	}
	@Override
	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();
		char ch=0;
		if(x==size-1&&y==size-1) {end();return;}
		if(total_moves>=500) view=2;
		if(total_moves>=1000) hunger=true;
		if(total_moves>=2000) view=1;
		if(total_moves>=2700){dead=true; die(); return;}
		hallucination=0.005*(total_moves-2500);
		if(key==KeyEvent.VK_A||key==KeyEvent.VK_LEFT)ch='a';
		if(key==KeyEvent.VK_W||key==KeyEvent.VK_UP)ch='w';
		if(key==KeyEvent.VK_D||key==KeyEvent.VK_RIGHT)ch='d';
		if(key==KeyEvent.VK_S||key==KeyEvent.VK_DOWN)ch='s';
		if(hunger&&Math.random()<=0.25){
			int r=rand(0,3);
			char[] randchars={'a','w','s','d'};
			ch=randchars[r];
		}
		if(ch == 'a' && x != 1 && arr[x-1][y]==0){x--;  total_moves+=1; }
		else if(ch == 'w' && y != 1 && arr[x][y-1]==0){y--;  total_moves+=1;}
		else if(ch == 's' && y != size && arr[x][y+1]==0){y++;  total_moves+=1;}
		else if(ch == 'd' && x != size && arr[x+1][y]==0){x++;  total_moves+=1;}
		else if(ch == 'a' || ch == 'w' || ch == 'd' || ch == 's')total_moves+=2;
		disp(x,y);
		if(total_moves >= next_change && total_moves%2==0 && dist(x,lx,y,ly) >= 6){
			for(int i=0;i<20;i++){
				random_text();
				sleep(50);
				repaint();
			}
			init(x,y); disp(x,y);
			next_change = 2*rand(30,50)+total_moves;
			lx = x; ly = y;
		}
	}

	@Override
	public void keyReleased(KeyEvent e){
		
	}
	@Override
	public void keyTyped(KeyEvent e){
		
	}
	public static Image loadImage (String name) throws Exception
    {
		return ImageIO.read (new File (name));
    }
	public void end() {
		JOptionPane win = new JOptionPane("Congratulations");
		JOptionPane.showMessageDialog(win, "You have survived the maze...\nYou took " + total_moves + " moves.");
		JOptionPane thanks = new JOptionPane("Victory!");
		JOptionPane.showMessageDialog(thanks, "Thanks for playing our game!");
		Main.frame.dispose();
		music.stop();
	}
	public void die() {
		JOptionPane dead = new JOptionPane("Too Bad!");
		JOptionPane.showMessageDialog(dead, "You have died a horrible death...\nPlease retry.");
		Main.frame.dispose();
		music.stop();
	}
	public int rand(int x,int y)
	{
		return (int)(Math.random()*(y-x+1)+x);
	}
	
	public int min(int x, int y)
	{
		return Math.min(x,y);
	}
	
	public int max(int x, int y)
	{
		return Math.max(x,y);
	}
	
	public void sleep(int m)
	{
		try{Thread.sleep(m);}
		catch(InterruptedException e){}
	}
	
	Game3() throws Exception{

		hunger=false;
		view=3;
		hallucination=0;
		init(x, y); view = 3;
		total_moves=0;
		next_change = 2*rand(50,100);
		g=im.createGraphics();
		addKeyListener(this);
		flag=loadImage("flag.png");
		grass=loadImage("grass.jpg");
		wall=loadImage("wall.jpg");
		setBackground(Color.black);
		setFocusable(true);
		requestFocus();
		//hallucination label
		hallu=new JLabel("");
		hallu.setForeground(Color.white);
		hallu.setFont(new Font("Consolas",Font.BOLD,20));
		add(hallu);
		//sight label
		sight=new JLabel("");
		sight.setForeground(Color.white);
		sight.setFont(new Font("Consolas",Font.BOLD,20));
		add(sight);
		//hung label
		hung=new JLabel("");
		hung.setForeground(Color.white);
		hung.setFont(new Font("Consolas",Font.BOLD,20));
		add(hung);
	}
	
	public boolean isInGrid(int x, int y)
	{
		return (x>=1&&x<=size&&y>=1&&y<=size);
	}
	
	public void build(int x, int y)
	{
		arr[x][y] = 0;
		
		Integer[] array = new Integer[]{0, 1, 2, 3};
		Collections.shuffle(Arrays.asList(array));
		
		for(int i=0;i<4;i++){
			int dir = array[i];
			
			// prevents rebuilding an existing path or cycle
			int newX = x+2*dx[dir], newY = y+2*dy[dir];
			int passX = x+dx[dir], passY = y+dy[dir];
			if(!isInGrid(newX,newY)) continue;
			if(arr[newX][newY]==1&&arr[passX][passY]==1){
				arr[passX][passY]=0;
				build(newX, newY);
			}
		}
	}
	
	public void init(int x, int y)
	{
		for(int i=0;i<=size;i++){
			Arrays.fill(arr[i], 1);
		}
		build(x,y);
	}
	
	public void disp(int x, int y)
	{
		g.setColor(Color.black);
		g.fillRect(0,0,1080,720);		
		g.setColor(Color.red);
		for(int i=y-view;i<=y+view;i++)
		{
			for(int j=x-view;j<=x+view;j++)
			{
				g.drawImage(grass,440+80*(j-x),310+80*(i-y),80,80,null);
				if(j==x&&i==y) g.fillOval(440,310,80,80);
				else if(i==size-1&&j==size-1) g.drawImage(flag,440+80*(j-x),310+80*(i-y),80,80,null);
				else if(Math.random()<hallucination){
					if(Math.random()<0.5)g.drawImage(wall,440+80*(j-x),310+80*(i-y),80,80,null);
					else g.drawImage(grass,440+80*(j-x),310+80*(i-y),80,80,null);
				}
				else if(i<0||i>size||j<0||j>size) g.drawImage(wall,440+80*(j-x),310+80*(i-y),80,80,null);
				else if(arr[j][i] != 0) g.drawImage(wall,440+80*(j-x),310+80*(i-y),80,80,null);
				else g.drawImage(grass,440+80*(j-x),310+80*(i-y),80,80,null);
			}
		}
		if (view==2) sight.setText("     You start feeling tired. You can't see as clearly     ");
		if (hunger) hung.setText("     You are starting to starve. You start to lose control     ");
		if (view==1) sight.setText("     You are gasping for every breath. You can barely open your eyes     ");
		if(hallucination>0) hallu.setText("    You start to se? th?ng? C?n y?u st??? r??d?????????     ");
		repaint();
	}
	
	public void random_text()
	{
		for(int i=0;i<=view*2;i++){
			for(int j=0;j<=view*2;j++){
				g.drawImage(grass,440+80*(j-x),310+80*(i-y),80,80,null);
				repaint();
				if(j==x&&i==y) g.fillOval(440,310,80,80);
				else if(Math.random()<0.5) {g.drawImage(wall,440+80*(j-x),310+80*(i-y),80,80,null); repaint();}
			}
		}
		repaint();
	}
	
	public int dist(int x, int px, int y, int py)
	{
		return Math.abs(x-px)+Math.abs(y-py);
	}

	public void start()
	{
		//Music
		try {
			AudioInputStream in = AudioSystem.getAudioInputStream(this.getClass().getResource("lavender.wav"));
			music = AudioSystem.getClip();
			music.open(in);
			music.start();
			music.loop(Clip.LOOP_CONTINUOUSLY);
		} catch(Exception e) {

		}

		JOptionPane instructions = new JOptionPane("Instructions");
		JOptionPane.showMessageDialog(instructions, "Use arrow keys to move around\n" +
		"The start is at the upper left corner and the end is at the lower right hand corner");

		disp(x,y);
		requestFocus();
		Main.change("g3");
		/*
		disp(x,y);
		if(dead){
			c.println("You have died a horrible death...");
			sleep(5000);
		}
		else{
			c.println("You survived the maze...");
			c.println("You took "+total_moves+" moves to escape...");
		}
		map.close();
		c.getChar();*/
	}
}