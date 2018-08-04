/**
 * George
 * Game1b.java
 * this is game
 */
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.Map;
import javax.sound.sampled.*;

public class Game1b extends JPanel{
	int range = 8, move;
	int[] dp = new int[1000000]; // fake 4-d array
	int[] ch = new int[1000000];
	int[][] arr = new int[3*range+1][4];
	int[][] visible = new int[3*range+1][4];
	HashMap<Integer,Integer> id = new HashMap<Integer,Integer>();
	
	int[] i1 = new int[2], i2 = new int[2];
	int sc1 = 0, sc2 = 0, nxt = 0;
	
	boolean flag = false;
	
	JFrame f = new JFrame();
	JLabel p1 = new JLabel("");
	JLabel p2 = new JLabel("");
	JLabel r1 = new JLabel("");
	JLabel r2 = new JLabel("");
	
	JButton a = new JButton("<<<");
	JButton b = new JButton(">>>");
	JButton c = new JButton("<<<");
	JButton d = new JButton(">>>");
	public JButton exit = new JButton("Back");

	Clip music;
	
	//***************** gets index ***************/
	
	public static int rand(int x,int y){
		return (int)(Math.random()*(y-x+1)+x);
	}
	
	public static int max(int x, int y){
		return Math.max(x,y);
	}
	
	public static int min(int x, int y){
		return Math.min(x,y);
	}
	
	public static void sleep(int m){
		try{Thread.sleep(m);}
		catch(InterruptedException e){}
	}
	
	public  int f(int[] l,int[] r){
		int h = ((l[0]*range+l[1])*range+r[0])*range+r[1];
		if(id.containsKey(h)) return id.get(h);
		id.put(h,++nxt);
		return nxt;
		// returns corresponding index
	}
	
	public int Score(int[] l,int[] r){
		// java passes a pointer to array l and r
		// don't actually want to modify array
		// so make a new array
		int[] x=l, y=r;
		
		if(x[0]>y[0]&&x[1]>y[1]) return 0;
		int h = f(x,y);
		if(ch[h]==0) x[0]++;
		else if(ch[h]==1) y[0]--;
		else if(ch[h]==2) x[1]++;
		else y[1]--;
		return dp[f(x,y)];
	}
	
	public static void r(int[] x, int[] y, int[] l, int []r){
		x[0] = l[0]; x[1] = l[1];
		y[0] = r[0]; y[1] = r[1];
	}
	
	public  void AI_Solve(int[] l, int[] r){
		
		if(l[0]>r[0]&&l[1]>r[1]) return; // both rows are empty
		if(dp[f(l,r)] != -1) return; // already found the answer
		
		int[] x = new int[2], y = new int[2];
		r(x, y, l, r);
		
		if(x[0]<=y[0]&&x[1]<=y[1]){ // both rows are not empty
			int tmp = -1<<30; // negative infinity
			int[] a = new int[4];
			
			// getting optimal score out of all possible moves
			for(int i=0;i<4;i++){
				if(i%2==0){
					x[i/2]++; AI_Solve(x,y);
					r(x,y,l,r); x[i/2]++;
					a[i] = arr[x[i/2]-1][i/2]+Score(x,y);
				}
				else{
					y[i/2]--; AI_Solve(x,y);
					r(x,y,l,r); y[i/2]--;
					a[i] = arr[y[i/2]+1][i/2]+Score(x,y);
				}
				tmp = max(tmp, a[i]);
				r(x,y,l,r);
			}
			
			if(a[0]==tmp) ch[f(l,r)]=0;
			else if(a[1]==tmp) ch[f(l,r)]=1;
			else if(a[2]==tmp) ch[f(l,r)]=2;
			else ch[f(l,r)]=3;
			
			dp[f(l,r)] = tmp;
		}
		else if(x[0]<=y[0]){ // row 1 is not empty
			int a, b;
			
			// take left
			x[0]++; AI_Solve(x,y); r(x,y,l,r);
			x[0]++; a=arr[x[0]-1][0]+Score(x,y);
			r(x, y, l, r);
			
			// take right
			y[0]--; AI_Solve(x,y); r(x,y,l,r);
			y[0]--; b=arr[y[0]+1][0]+Score(x,y);
			r(x, y, l, r);
			
			if(a > b) ch[f(l,r)]=0;
			else ch[f(l,r)]=1;
			
			dp[f(l,r)] = Math.max(a,b);
		}
		else if(x[1]<=y[1]){ // row 2 isn't empty
			int a, b;
			
			// take left
			x[1]++; AI_Solve(x,y); r(x,y,l,r);
			x[1]++; a=arr[x[1]-1][1]+Score(x,y);
			r(x, y, l, r);
			
			// take right
			y[1]--; AI_Solve(x,y); r(x,y,l,r);
			y[1]--; b=arr[y[1]+1][1]+Score(x,y);
			r(x, y, l, r);
			
			if(a > b) ch[f(l,r)]=2;
			else ch[f(l,r)]=3;
			
			dp[f(l,r)] = max(a,b);
		}
	}
	
	public void disp(int sc1, int sc2){
		p1.setText("Player: "+Integer.toString(sc1));
		p2.setText("AI: "+Integer.toString(sc2));

		String txt = "";
		
		for(int j=0;j<2;j++){
			for(int i=1;i<=range;i++){
				if(visible[i][j]==1){
					txt += Integer.toString(arr[i][j]);
					while(txt.length()<8*i) txt+=" ";
				}
				else txt += "        ";
			}
			if(j==0) r1.setText(txt);
			else r2.setText(txt);
			txt = "";
		}
		f.repaint();
	}
	
	Game1b() throws Exception{
		
		setLayout(null);
		
		// add components to JPanel 
		
		add(p1);
		p1.setFont(new Font(p1.getFont().getName(), Font.PLAIN, 30));
		p1.setBounds(0,0,300,50);
		
		add(p2);
		p2.setFont(new Font(p2.getFont().getName(), Font.PLAIN, 30));
		p2.setBounds(800,0,200,50);
		
		add(r1);
		r1.setBounds(200,180,800,100);
		r1.setFont(new Font(r1.getFont().getName(), Font.PLAIN, 30));
		
		add(r2);
		r2.setBounds(200,280,800,100);
		r2.setFont(new Font(r2.getFont().getName(), Font.PLAIN, 30));
		
		add(a);
		a.setBounds(0,200,80,50);
		
		add(b);
		b.setBounds(920,200,80,50);
		
		add(c);
		c.setBounds(0,300,80,50);
		
		add(d);
		d.setBounds(920,300,80,50);	
		
		add(exit);
		exit.setBounds(410,600,200,50);
		exit.setVisible(false);

		a.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				if(i1[0]<=i2[0]){
					sc1 += arr[i1[0]][0]; visible[i1[0]][0]=0; i1[0]++;
					AI_Move();
				}
			}
		});
		
		b.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				if(i1[0]<=i2[0]){
					sc1 += arr[i2[0]][0]; visible[i2[0]][0]=0; i2[0]--;
					AI_Move();
				}
			}
		});
		
		c.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				if(i1[1]<=i2[1]){
					sc1 += arr[i1[1]][1]; visible[i1[1]][1]=0; i1[1]++;
					AI_Move();
				}
			}
		});
		
		d.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				if(i1[1]<=i2[1]){
					sc1 += arr[i2[1]][1]; visible[i2[1]][1]=0; i2[1]--;
					AI_Move();
				}
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
		// computer moves
		int choice = ch[f(i1,i2)]+1;
			
		if(choice < 1 || choice > 4) choice = 1;
		if((choice==1||choice==2)&&i1[0]>i2[0])
			choice = 3;
		if((choice==3||choice==4)&&i1[1]>i2[1])
			choice = 1;
			
		if(choice == 1){sc2+=arr[i1[0]][0]; visible[i1[0]][0]=0; i1[0]++;}
		else if(choice == 2){sc2+=arr[i2[0]][0]; visible[i2[0]][0]=0; i2[0]--;}
		else if(choice == 3){sc2+=arr[i1[1]][1]; visible[i1[1]][1]=0; i1[1]++;}
		else if(choice == 4){sc2+=arr[i2[1]][1]; visible[i2[1]][1]=0; i2[1]--;}
		disp(sc1, sc2);
		
		if(i1[0]>i2[0]&&i1[1]>i2[1]) end();
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

		sc1 = sc2 = 0;
		Arrays.fill(dp, -1);
		Arrays.fill(ch, -1);
		int[] x = new int[2], y = new int[2];
		int sc1 = 0, sc2 = 0;
		
		for(int j=0;j<2;j++){ 
			for(int i=1;i<=range;i++)
				arr[i][j] = rand(0, 2*range);
		}
		for(int j=0;j<=3*range;j++)
			Arrays.fill(visible[j], 1);
		
		AI_Solve(i1, i2);
		disp(0,0);
		
		i1[0] = i1[1] = 1;
		i2[0] = i2[1] = range;	
		
		disp(sc1, sc2);
	}
	
	public void end(){
		Menu.isG1B = true;
		exit.setVisible(true);
		a.setVisible(false);
		b.setVisible(false);
		c.setVisible(false);
		d.setVisible(false);
		if(sc1>sc2) r1.setText("                                       You Win!");
		else if(sc1==sc2) r1.setText("                                           Tie!");
		else r1.setText("                                       You Lost!");
		repaint();
		sleep(1000);
	}
}