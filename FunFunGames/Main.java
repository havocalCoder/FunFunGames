/**
 * Kevin George Edward Xingming
 * Main.java for FunFunGames
 * Contains main method and executes all games
 * Contains all global variables
 */
import java.awt.*;
import sun.audio.*;
import javax.imageio.*;
import java.io.*;
import java.util.*;
import java.awt.event.*;
import java.awt.event.ActionEvent;
import javax.swing.*;
public class Main {
	//GLOBAL VARIABLES
	static Menu menu;
	static Game1a g1a;
	static Game1b g1b;
	static Game2a g2a;
	static Game2b g2b;
	static Game3 g3;
	static CardLayout cl;
	static JPanel cards;
	static JFrame frame;
	static double score2a,score2b,score3;
	public static void change(String str){
		cl.show(cards,str);
	}
	public static void main(String[] args) throws Exception{
		frame = new JFrame("WK CG SE XX Summative");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		cards=new JPanel(new CardLayout());
		cards.setVisible(false);
		menu=new Menu();
		g1a=new Game1a();
		g1b=new Game1b();
		g2a=new Game2a();
		g2b=new Game2b();
		g3=new Game3();
		cards.add("g1a",g1a);
		cards.add("g1b",g1b);
		cards.add("g2a",g2a);
		cards.add("g2b",g2b);
		cards.add("g3",g3);
		cards.add("menu",menu);
		cl=(CardLayout)cards.getLayout();
		frame.getContentPane().add(cards);
		frame.pack();
		frame.setSize(1080,720);
		cards.setVisible(true);
		frame.setVisible(true);
		frame.repaint();
		change("menu");
	}
}