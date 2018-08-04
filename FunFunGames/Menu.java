/**
 * Edward, Kevin, George, Xingming
 * Menu.java
 * Menu for the Game
 */

import javax.swing.*;
import java.awt.image.*;
import java.awt.*;
import java.io.*;
import javax.imageio.*;
import java.awt.event.*;
import java.util.*;
import javax.sound.sampled.*;
/*
 * Menu
 */
public class Menu extends JPanel {
    public static boolean isVisible = false;
    private BufferedImage image;
    private Graphics2D g;
    public static boolean isG1A = false;
    public static boolean isG1B = false;
    public static boolean isG2A = false;
    public static boolean isG2B = false;
    Menu() throws Exception {
        image = new BufferedImage(1080, 720, BufferedImage.TYPE_INT_ARGB);
        g = image.createGraphics();
        setVisible(true);
        setFocusable(true);
        requestFocus();
        setLayout(null);
        //Title screen
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, 1080, 720);

        Image titleImage = loadImage("titleScreen.jpg");
        g.drawImage(titleImage, 0, 0, 1080, 720, null);

        g.setColor(Color.WHITE);
        Font regFont = new Font("Courier New", Font.PLAIN, 30);
        g.setFont(regFont);
        g.drawString("Press Enter to Continue", 350, 500);

        //All dose buttons
        JButton game_1a_button = new JButton("Greedy For Numbers");
        JButton game_1b_button = new JButton("Greedy For Numbers HARD");
        JButton game_2a_button = new JButton("Flashing Lights");
        JButton game_2b_button = new JButton("Flashing Lights HARD");

        game_1a_button.setLocation(50, 500);
        game_1a_button.setSize(200, 100);
        game_1a_button.setVisible(false);
        add(game_1a_button);

        game_1b_button.setLocation(300, 500);
        game_1b_button.setSize(200, 100);
        game_1b_button.setVisible(false);
        add(game_1b_button);

        game_2a_button.setLocation(550, 500);
        game_2a_button.setSize(200, 100);
        game_2a_button.setVisible(false);
        add(game_2a_button);

        game_2b_button.setLocation(800, 500);
        game_2b_button.setSize(200, 100);
        game_2b_button.setVisible(false);
        add(game_2b_button);
        repaint();

        JButton game_3_btn = new JButton("The Maze");
        game_3_btn.setLocation(50, 500);
        game_3_btn.setSize(950, 100);
        game_3_btn.setVisible(false);
        add(game_3_btn);
        repaint();

        // A bunch of event listeners
        game_1a_button.addActionListener(new ActionListener(){
        
            @Override
            public void actionPerformed(ActionEvent e) {
                if(isG1A) {
                    JOptionPane isPlayed = new JOptionPane();
                    JOptionPane.showMessageDialog(isPlayed, "You have already played this game.");
                } else {
                    Main.change("g1a");
                    Main.g1a.start();
                }
                if(isG1A && isG1B && isG2A && isG2B) {
                    game_1a_button.setVisible(false);
                    game_1b_button.setVisible(false);
                    game_2a_button.setVisible(false);
                    game_2b_button.setVisible(false);
                    game_3_btn.setVisible(true);
                } else {
                    game_1a_button.setVisible(true);
                    game_1b_button.setVisible(true);
                    game_2a_button.setVisible(true);
                    game_2b_button.setVisible(true);
                    game_3_btn.setVisible(false);
                }
            }
        });

        game_1b_button.addActionListener(new ActionListener(){
        
            @Override
            public void actionPerformed(ActionEvent e) {
                if(isG1B) {
                    JOptionPane isPlayed = new JOptionPane();
                    JOptionPane.showMessageDialog(isPlayed, "You have already played this game.");
                } else {
                    Main.change("g1b");
                    Main.g1b.start();
                }
                if(isG1A && isG1B && isG2A && isG2B) {
                    game_1a_button.setVisible(false);
                    game_1b_button.setVisible(false);
                    game_2a_button.setVisible(false);
                    game_2b_button.setVisible(false);
                    game_3_btn.setVisible(true);
                } else {
                    game_1a_button.setVisible(true);
                    game_1b_button.setVisible(true);
                    game_2a_button.setVisible(true);
                    game_2b_button.setVisible(true);
                    game_3_btn.setVisible(false);
                }
            }
        });

        game_2a_button.addActionListener(new ActionListener(){
        
            @Override
            public void actionPerformed(ActionEvent e) {
                if(isG2A) {
                    JOptionPane isPlayed = new JOptionPane();
                    JOptionPane.showMessageDialog(isPlayed, "You have already played this game.");
                } else {
                    Main.change("g2a");
                    Main.g2a.start();
                }
                if(isG1A && isG1B && isG2A && isG2B) {
                    game_1a_button.setVisible(false);
                    game_1b_button.setVisible(false);
                    game_2a_button.setVisible(false);
                    game_2b_button.setVisible(false);
                    game_3_btn.setVisible(true);
                } else {
                    game_1a_button.setVisible(true);
                    game_1b_button.setVisible(true);
                    game_2a_button.setVisible(true);
                    game_2b_button.setVisible(true);
                    game_3_btn.setVisible(false);
                }
            }
        });

        game_2b_button.addActionListener(new ActionListener(){
        
            @Override
            public void actionPerformed(ActionEvent e) {
                if(isG2B) {
                    JOptionPane isPlayed = new JOptionPane();
                    JOptionPane.showMessageDialog(isPlayed, "You have already played this game.");
                } else {
                    Main.change("g2b");
                    Main.g2b.start();
                }
                if(isG1A && isG1B && isG2A && isG2B) {
                    game_1a_button.setVisible(false);
                    game_1b_button.setVisible(false);
                    game_2a_button.setVisible(false);
                    game_2b_button.setVisible(false);
                    game_3_btn.setVisible(true);
                } else {
                    game_1a_button.setVisible(true);
                    game_1b_button.setVisible(true);
                    game_2a_button.setVisible(true);
                    game_2b_button.setVisible(true);
                    game_3_btn.setVisible(false);
                }
            }
        });

        game_3_btn.addActionListener(new ActionListener(){
        
            @Override
            public void actionPerformed(ActionEvent e) {
                Main.change("g3");
                Main.g3.start();
            }
        });

        addKeyListener(new KeyListener(){
            @Override
            public void keyPressed(KeyEvent e) {
                g.setColor(Color.BLACK);
                g.fillRect(0, 0, 1080, 720);
                g.setColor(Color.WHITE);
                g.setFont(new Font("Courier New", Font.PLAIN, 32));
                g.drawString("Pick A Game", 400, 100);
                    game_1a_button.setVisible(true);
                    game_1b_button.setVisible(true);
                    game_2a_button.setVisible(true);
                    game_2b_button.setVisible(true);
                    game_3_btn.setVisible(false);
                repaint();
            }
            @Override
            public void keyReleased(KeyEvent e) {

            }
            @Override
            public void keyTyped(KeyEvent e) {

            }
        }); 

    }
    //load image method
    public static Image loadImage (String name) throws Exception
    {
        return ImageIO.read (new File (name));
    }
    //paint method
    @Override
    protected void paintComponent(Graphics g_image) {
        super.paintComponent(g_image);
        Graphics2D graphics = (Graphics2D) g_image.create();
        graphics.drawImage(image, 0, 0, this);
        graphics.dispose();
    }
    //start method just because
    public void start() {

    }
}