/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package hangman;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import java.awt.BasicStroke;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Line2D;

/**
 *
 * @author palomast.clair nathan.atherley
 */

public class Hangman {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        OpeningPage op = new OpeningPage();
        op.launch();

    }
    
}

class OpeningPage{
        protected String dificulty;
        protected String category;
        JButton[] buttonArr = new JButton[6];
        JFrame frame;
        JPanel wrapper;
        OpeningPage(){}
        public void launch(){
            frame = new JFrame("Hangman");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(1150,750);
            frame.setResizable(false);  
            wrapper = new JPanel();
            wrapper.setLayout(new BoxLayout(wrapper, BoxLayout.Y_AXIS));
            frame.add(wrapper);
            
            JPanel wordsPanel = new JPanel();
            Font headerFont = new Font("Comic Sans MS", Font.BOLD, 100);
            wordsPanel.setBackground(Color.decode("#FFFDD0"));
            JLabel label1 = new JLabel("HangMan");
            label1.setHorizontalAlignment(JLabel.CENTER);
            label1.setFont(headerFont);
            Font subLabel = new Font("Comic Sans MS", Font.PLAIN, 50);
            JLabel label2 = new JLabel("Category:"); 
            label2.setHorizontalAlignment(JLabel.CENTER);
            label2.setFont(subLabel);
            wordsPanel.setLayout(new BoxLayout(wordsPanel, BoxLayout.Y_AXIS));
            wordsPanel.add(label1); wordsPanel.add(label2);
            wrapper.add(wordsPanel);
            wrapper.setBackground(Color.decode("#FFFDD0"));
            
            JPanel categoryButtonPanel = new JPanel();
            categoryButtonPanel.setBackground(Color.decode("#FFFDD0"));
            JButton singer = new JButton("Singer");
            JButton city = new JButton("City");
            JButton food = new JButton("Food");
            singer.setFont(subLabel);city.setFont(subLabel);food.setFont(subLabel);
            singer.addActionListener(new CategoryButtonListener(buttonArr, this));
            city.addActionListener(new CategoryButtonListener(buttonArr, this));
            food.addActionListener(new CategoryButtonListener(buttonArr, this)); 
            buttonArr[0] = singer; buttonArr[1] = city; buttonArr[2] = food;
            
            categoryButtonPanel.add(singer); categoryButtonPanel.add(city); categoryButtonPanel.add(food);
            wrapper.add(categoryButtonPanel);
            JLabel dificultyLabel = new JLabel("Dificulty:");
            dificultyLabel.setFont(subLabel);
            dificultyLabel.setHorizontalAlignment(JLabel.CENTER);
            JPanel dificultyButtonPanel = new JPanel();
            dificultyButtonPanel.setBackground(Color.decode("#FFFDD0"));
            JButton easy = new JButton("Easy");
            JButton medium = new JButton("Medium");
            JButton hard = new JButton("Hard");
            easy.setFont(subLabel);medium.setFont(subLabel);hard.setFont(subLabel);
            easy.addActionListener(new CategoryButtonListener(buttonArr, this));
            medium.addActionListener(new CategoryButtonListener(buttonArr, this));
            hard.addActionListener(new CategoryButtonListener(buttonArr, this));
            food.addActionListener(new CategoryButtonListener(buttonArr, this)); 
            buttonArr[3] = easy; buttonArr[4] = medium; buttonArr[5] = hard;
            
            dificultyButtonPanel.add(easy); dificultyButtonPanel.add(medium); dificultyButtonPanel.add(hard);
            
            JButton playButton = new JButton("Play!");
            playButton.setFont(subLabel);
            playButton.addActionListener(new PlayButtonListener());
            
            wrapper.add(categoryButtonPanel);
            wrapper.add(dificultyLabel);
            wrapper.add(dificultyButtonPanel);
            wrapper.add(playButton);
            frame.add(wrapper);
            frame.show();   
        }
        
        class CategoryButtonListener implements ActionListener{
            JButton[] bArr;
            OpeningPage op;
            CategoryButtonListener(JButton[] arr, OpeningPage o){
                bArr = arr;
                op = o;
            }
            @Override
            public void actionPerformed(ActionEvent arg0) {
                JButton jb = (JButton) arg0.getSource();
                jb.setOpaque(true);
                jb.setBorderPainted(false);
                if(jb == bArr[0]){
                    category = "singer";  
                    jb.setBackground(Color.CYAN);
                    disableOtherButtonsInRow(1);
                }
                if(jb == bArr[1]){
                    category = "city";
                    jb.setBackground(Color.CYAN);
                    disableOtherButtonsInRow(1);
                }
                if(jb == bArr[2]){
                    category = "food";
                    jb.setBackground(Color.CYAN);
                    disableOtherButtonsInRow(1);
                }
                if(jb == bArr[3]){
                    dificulty = "easy";
                    jb.setBackground(Color.GREEN);
                    disableOtherButtonsInRow(2);
                }
                if(jb == bArr[4]){
                    dificulty = "medium";
                    jb.setBackground(Color.orange);
                    disableOtherButtonsInRow(2);

                }
                if(jb == bArr[5]){
                    dificulty = "hard";
                    jb.setBackground(Color.red);
                    disableOtherButtonsInRow(2);
                }
            }
            
            private void disableOtherButtonsInRow(int row){
                if(row ==1){
                    for(int i =0; i<3; i++){
                        JButton jb = bArr[i];
                        jb.setEnabled(false);
                    }
                }else{
                    for(int i =3; i<bArr.length; i++){
                        JButton jb = bArr[i];
                        jb.setEnabled(false);
                    }
                }
                
            }
        }
        
        class PlayButtonListener implements ActionListener{
            @Override
            public void actionPerformed(ActionEvent arg0){
                JButton jb = (JButton) arg0.getSource();
                if(dificulty != null && category!=null){
                    CreateGame cg = new CreateGame(category, dificulty, frame);
                    cg.launchGame();
                    wrapper.setVisible(false);
                }
            }
        }
        
    }

class DrawMan extends JPanel {
    protected Hangman hang;
    
    public void setHangman(Hangman hang){
        this.hang = hang;
    }
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        this.setBackground(Color.white);
        
            System.out.println("in if statement");
            Graphics2D g2 = (Graphics2D)g;
            g2.setStroke(new BasicStroke(9));
            g2.draw(new Line2D.Float(250,0,250,445)); // main, long vertical line
            g2.draw(new Line2D.Float(105,0,250,0)); // top horizontal line 
            g2.setStroke(new BasicStroke(5));              
            g2.draw(new Line2D.Float(100,0,100,50));
            g2.draw(new Line2D.Float(0,450,450,450));
            //g2.draw(new Line2D.Float(150,350,150,450));
            //g2.draw(new Line2D.Float(297,350,297,450));
            //g2.draw(new Line2D.Float(0,350,150,450));
            //g2.draw(new Line2D.Float(150,351,297,450));                
            //g2.draw(new Line2D.Float(150,351,0,450));
            //g2.draw(new Line2D.Float(150,450,300,350));
            g2.setStroke(new BasicStroke(1));
        
        
    }
}
         
    

class CreateGame{
    String category;
    String difficulty;
    JFrame frame;
    JPanel gamePanel;
    //Graphics2D g1;
    //int errors = 0; 
    
    CreateGame(String cat, String diff, JFrame f){
        category = cat;
        difficulty = diff;
        frame = f;
    }
    
    private static final String[][] letter = {
        {"Q", "W", "E", "R", "T", "Y", "U", "I", "O", "P"},
        {"A", "S", "D", "F", "G", "H", "J", "K", "L"},
        {"Z", "X", "C", "V", "B", "N", "M"},
    };
    
    
  
    
    public void launchGame(){
        //Graphics g; 
        System.out.println(category+" "+ difficulty);
        gamePanel = new JPanel();
        gamePanel.setBackground(Color.white);
        
        JPanel keyboard = new JPanel(new GridBagLayout());
        JPanel panRow;
        GridBagConstraints c = new GridBagConstraints();
        c.anchor = GridBagConstraints.SOUTH;
        //c.weightx = 1d;
        
        for (int row = 0; row < letter.length; ++row){
            panRow = new JPanel(new GridBagLayout());
            
            c.gridy = row;
            
            for (int col = 0; col < letter[row].length; ++col)
                panRow.add(new JButton(letter[row][col]));
            
            keyboard.add(panRow, c);
        }
        DrawMan hp = new DrawMan();
        hp.setPreferredSize(new Dimension(300,500));
        gamePanel.add(keyboard);
        gamePanel.add(hp);
        frame.add(gamePanel); 
        
        
      
    }
     
}
    