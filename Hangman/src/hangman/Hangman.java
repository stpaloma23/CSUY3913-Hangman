/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package hangman;

import java.awt.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.*;

import java.awt.BasicStroke;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Line2D;

/**
 *
 * @author palomast.clair nathan.atherley
 */

/*What we have left to do: 
- draw the hanging man 
- make the timer coordinate with the hang man 
- check to see if the right word was guessed
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

// launches the opening screen, user picks the category ad level they want
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
            frame.setSize(1300,750);
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
    //protected Hangman hang;
    //protected CreateGame gc = new CreateGame();
        
    
    
    @Override
    protected void paintComponent(Graphics g) {
        
        
        super.paintComponent(g);
        this.setBackground(Color.white);
        
            Graphics2D g2 = (Graphics2D)g;
            g2.setStroke(new BasicStroke(9));
            g2.draw(new Line2D.Float(250,0,250,445)); // main, long vertical line
            g2.draw(new Line2D.Float(105,0,250,0)); // top horizontal line 
            g2.setStroke(new BasicStroke(5));              
            g2.draw(new Line2D.Float(100,0,100,50));
            g2.draw(new Line2D.Float(0,450,450,450));
            g2.setStroke(new BasicStroke(1));
            
          /*  
            // head/face
            g.drawOval(55,51,85,85);
            g.fillOval(70,80,15,15);
            g.fillOval(115,80,15,15);
            g.drawLine(70, 110, 90, 110);
            
            // torso
            g.drawRoundRect(65,135,70,100,30,30);
            
            // left arm
            g.drawLine(65, 150, 5, 175);
            
            // right arm
            g.drawLine(135, 150, 205, 175);
            
            // left leg
            g.drawLine(65, 190, 65, 315);
            
            // right leg
            g.drawLine(135, 190, 135, 315);
            
            // left foot
            g.drawRoundRect(40,315,27,10,10,10);
            
            // right foot
            g.drawRoundRect(133,315,27,10,10,10);
*/
    }
    
}

// created an int error variable within the keyboardactionlistener class
// to count the number of errors then going to create an object from the keyboardactionlistener class
// in the DrawMan class then create if statements (based on the number of errors certain body parts a drawn)
// wasn't working though
class DrawHead extends JPanel {
    //protected Hangman hang;
    //protected CreateGame gc = new CreateGame();
   
    @Override
    protected void paintComponent(Graphics g) {
   
        super.paintComponent(g);
        this.setBackground(Color.white);
        
            Graphics2D g2 = (Graphics2D)g;
         
            // head/face
            g.drawOval(55,51,85,85);
            g.fillOval(70,80,15,15);
            g.fillOval(115,80,15,15);
            g.drawLine(70, 110, 90, 110);
            
           
    }
    
}

class DrawTorso extends JPanel {
    //protected Hangman hang;
    //protected CreateGame gc = new CreateGame();
   
    @Override
    protected void paintComponent(Graphics g) {
   
        super.paintComponent(g);
        this.setBackground(Color.white);
        
            Graphics2D g2 = (Graphics2D)g;
            
            // torso
            g.drawRoundRect(65,135,70,100,30,30);
            
         
    }
    
}

class DrawLeftArm extends JPanel {
    //protected Hangman hang;
    //protected CreateGame gc = new CreateGame();
   
    @Override
    protected void paintComponent(Graphics g) {
   
        super.paintComponent(g);
        this.setBackground(Color.white);
        
            Graphics2D g2 = (Graphics2D)g;
           
            // left arm
            g.drawLine(65, 150, 5, 175);
    }
    
}

class DrawRightArm extends JPanel {
    //protected Hangman hang;
    //protected CreateGame gc = new CreateGame();
   
    @Override
    protected void paintComponent(Graphics g) {
   
        super.paintComponent(g);
        this.setBackground(Color.white);
        
            Graphics2D g2 = (Graphics2D)g;
         
            // right arm
            g.drawLine(135, 150, 205, 175);

    }
    
}

class DrawLeftLeg extends JPanel {
    //protected Hangman hang;
    //protected CreateGame gc = new CreateGame();
   
    @Override
    protected void paintComponent(Graphics g) {
   
        super.paintComponent(g);
        this.setBackground(Color.white);
        
            Graphics2D g2 = (Graphics2D)g;
         
            
            // left leg
            g.drawLine(65, 190, 65, 315);
            
          

    }
    
}

class DrawRightLeg extends JPanel {
    //protected Hangman hang;
    //protected CreateGame gc = new CreateGame();
   
    @Override
    protected void paintComponent(Graphics g) {
   
        super.paintComponent(g);
        this.setBackground(Color.white);
        
           
            
            // right leg
            g.drawLine(135, 190, 135, 315);
            
            

    }
    
}

class DrawLeftFoot extends JPanel {
    //protected Hangman hang;
    //protected CreateGame gc = new CreateGame();
   
    @Override
    protected void paintComponent(Graphics g) {
   
        super.paintComponent(g);
        this.setBackground(Color.white);
        
            Graphics2D g2 = (Graphics2D)g;
         
            
            // left foot
            g.drawRoundRect(40,315,27,10,10,10);
            
          
    }
    
}

class DrawRightFoot extends JPanel {
    //protected Hangman hang;
    //protected CreateGame gc = new CreateGame();
   
    @Override
    protected void paintComponent(Graphics g) {
   
        super.paintComponent(g);
        this.setBackground(Color.white);
        
            Graphics2D g2 = (Graphics2D)g;
         
            
            // right foot
            g.drawRoundRect(133,315,27,10,10,10);

    }
    
}

class CreateGame{
    //int errors; 
    String category;
    String difficulty;
    JFrame frame;
    JPanel gamePanel;
    // the word the user must guess
    String wordToGuess; 
    ArrayList<JButton> keyboardArr = new ArrayList<JButton>();

    GenerateData file = new GenerateData(); 
    
    CreateGame(String cat, String diff, JFrame f) {
        category = cat;
        difficulty = diff;
        frame = f;
        wordToGuess = file.getRandomWord(category);
    }
    
    private static final String[][] letter = {
        {"Q", "W", "E", "R", "T", "Y", "U", "I", "O", "P"},
        {"A", "S", "D", "F", "G", "H", "J", "K", "L"},
        {"Z", "X", "C", "V", "B", "N", "M"},
    };
    
    public void launchGame(){
        System.out.println(category+" "+ difficulty);
        gamePanel = new JPanel();
        //gamePanel.setBackground(Color.GRAY);
        JPanel header = new JPanel();
        header.setPreferredSize(new Dimension(1300, 100));
        //header.setBackground(Color.PINK);
        JLabel headerLabel = new JLabel(category + " Hangman!");
        headerLabel.setFont(new Font("Comic Sans MS", Font.PLAIN, 50));
        header.add(headerLabel);
        gamePanel.add(header);
        JPanel keyboard = new JPanel(new GridBagLayout());
        JPanel panRow;
        GridBagConstraints c = new GridBagConstraints();
        c.anchor = GridBagConstraints.SOUTH;
        
        for (int row = 0; row < letter.length; ++row){
            panRow = new JPanel(new GridBagLayout());
            
            c.gridy = row;
            
            for (int col = 0; col < letter[row].length; ++col){
                JButton keyLetter = new JButton(letter[row][col]);
                Font keyFont = new Font("Comic Sans MS", Font.PLAIN, 30);
                keyLetter.setFont(keyFont);
                keyboardArr.add(keyLetter);
                panRow.add(keyLetter);
                keyboard.add(panRow, c);
            
            }
        }
        DrawMan hp = new DrawMan();
        hp.setPreferredSize(new Dimension(300,600));
        
        
        
        WordDisplay wd = new WordDisplay(wordToGuess);
        //KeyBoardButtonActionListener key = new KeyBoardButtonActionListener(wd);
        
        
        
        
        
        JPanel wordPan = wd.generatePanel();
        wordPan.setPreferredSize(new Dimension(1300,100));
        for(JButton button : keyboardArr){
            button.addActionListener(new KeyBoardButtonActionListener(wd, err));
            
        }
        gamePanel.add(wordPan);
        //keyboard.setBackground(Color.magenta);
        gamePanel.add(keyboard);
        JPanel clockPanel = new JPanel();
        JLabel countdownTimer = new JLabel();
        Font timerFont = new Font("Comic Sans MS", Font.PLAIN, 20);
        CountdownTimer countdown = new CountdownTimer(difficulty, countdownTimer);
        countdown.start();
     
        countdownTimer.setFont(timerFont);
        JLabel timeKeeper = new JLabel();
        timeKeeper.setFont(timerFont);
        Timer totalTime = new Timer(timeKeeper);
        totalTime.start();
        clockPanel.add(countdownTimer);
        clockPanel.add(timeKeeper);
        //clockPanel.setBackground(Color.red);
        header.add(clockPanel);
        gamePanel.add(hp);
        
        frame.add(gamePanel); 
        
        System.out.println("Checking category: " + category);
    }  
    class KeyBoardButtonActionListener implements ActionListener {
        int errors;
        WordDisplay wd; 
        ArrayList<String> guessedWordAsList = new ArrayList<String>(); 
        KeyBoardButtonActionListener(WordDisplay w, int err){
            errors = err;
            wd = w; 
            for(int i =0; i<wordToGuess.length();i++){
                char letter = wordToGuess.charAt(i);
                letter = Character.toLowerCase(letter);
                guessedWordAsList.add(Character.toString(letter));
            }
        }
        @Override
        public void actionPerformed(ActionEvent arg0){
            JButton jb = (JButton) arg0.getSource();
            String letter = jb.getText().toLowerCase();
            jb.setOpaque(true);
            jb.setBorderPainted(false);
            System.out.println(guessedWordAsList);
            ArrayList<JButton> wordPanelArray = wd.guessedWordButtons;
            if (guessedWordAsList.contains(letter)){
                
                jb.setBackground(Color.GREEN);
                int index = guessedWordAsList.indexOf(letter);
 
                while (index >= 0) {
                    System.out.println(index);
                    JButton changeTextofButton = wordPanelArray.get(index);
                    changeTextofButton.setText(letter.toUpperCase());
                    index = wordToGuess.indexOf(letter, index + 1);
                    
                }
                int i = guessedWordAsList.indexOf(letter);
            }
            else{
                jb.setBackground(Color.RED);
                errors++;
            }
            jb.setEnabled(false);
            //return errors; 
        }
    }
}

// class reads the data from the file, add it to dictionary, and picks a random word
// from the list to play hangman
class GenerateData {
    ArrayList<String> singerCategory ;
    ArrayList<String> cityCategory ;
    ArrayList<String> foodCategory ;
    // reads file and puts the category and words in 
    GenerateData(){
        singerCategory  = new ArrayList<String>();
        cityCategory = new ArrayList<String>();
        foodCategory = new ArrayList<String>();
        String fileName = "../Hangman/src/hangman/HangmanData.csv";
        BufferedReader reader = null;
        String csvLine;
        try{
            reader = new BufferedReader(new FileReader(fileName));
            // skipping first row 
            reader.readLine(); 
            while((csvLine = reader.readLine() )!= null){
                String[] csvToArr = csvLine.split(",");
                String category = csvToArr[1].strip();
                String word = csvToArr[0].strip();
                if("singer".equals(category)){
                    singerCategory.add(word);
                }
                if("food".equals(category)){
                    foodCategory.add(word);
                }
                if("city".equals(category)){
                    cityCategory.add(word);
                }
            }

        }
        catch(Exception e){
            System.out.println("cannot open file"); 
        }
        if(reader !=null){
            try{
            reader.close();
            } 
            catch (IOException e){
                System.out.println("cannot close file");
            }
        }
    }
    // returns the random word from the selected category for the user to guess
    public String getRandomWord(String category){
        category = category.strip();
        if (category == "singer"){
            int ind = getRandomNumber(singerCategory.size()-1);
            return singerCategory.get(ind);
        }
        if (category == "food"){
            int ind = getRandomNumber(foodCategory.size()-1);
            return foodCategory.get(ind);
        }
        if (category == "city"){
            int ind = getRandomNumber(cityCategory.size()-1);
            return cityCategory.get(ind);
        }
        return "hey girl something's wrong";
    }
    
    // generated a random index
    private int getRandomNumber(int arrLen){
        Random random = new Random();
        return random.nextInt(arrLen);
    }
}

class WordDisplay{
    // private GenerateData guessingW ;
    private JPanel wordSlotPan = new JPanel();
    private CreateGame game;
    private String wordToGuess;
    ArrayList<JButton> guessedWordButtons = new ArrayList<JButton>();
    
    WordDisplay(String word){
        wordToGuess = word;
        makeWordSlots();
    }
    
    public JPanel generatePanel(){
        
        return wordSlotPan;
    }
    
    
    // word.split(" ")
    private void makeWordSlots(){
        //add(wordSlotPan, BorderLayout.CENTER);
        // String guessWord = guessingW.getRandomWord(game.category);
        // JButton[] wordSlots = new JButton[wordToGuess.length()];
        
        for (int ind = 0; ind < wordToGuess.length(); ind++){
            JButton button;
            Font displayWordF = new Font("Comic Sans MS", Font.PLAIN, 15);
            if(wordToGuess.charAt(ind)== ' '){
                button = new JButton();
            }
            else{
                button = new JButton("____");
            }
            button.setFont(displayWordF);
            button.setOpaque(true);
            button.setBorderPainted(false);
            button.setFocusPainted(false);
            button.setContentAreaFilled(false);
            //wordSlots[ind] = button;
            guessedWordButtons.add(button);
            wordSlotPan.add(button);
            
        }
        System.out.println("Check word: " + wordToGuess);
    
    }
}
 
    
class CountdownTimer extends Thread {
    String dificulty; 
    int countdown;
    JLabel clockTime; 
    CountdownTimer(String diff, JLabel timer){
        dificulty = diff;
        clockTime = timer;
        if (diff == "easy"){
            countdown = 20;
        }
        if (diff == "medium"){
            countdown = 13;
        }
        if (diff == "hard"){
            countdown = 7;
        }
    }
    public void run(){
        do{
            try {
                countdown -=1;
                clockTime.setText("Time Remaining: "+Integer.toString(countdown));
                System.out.println(countdown);
                Thread.currentThread().sleep(1000);
            } 
            catch(InterruptedException e){return;};
        } while(countdown != 0);
        
    }
}

class Timer extends Thread {
    JLabel timeLabel;
    int timer = 0;
    
    Timer(JLabel t){
        timeLabel = t;
    }
    
    public void run(){
        do{
           try {
               
                timer +=1;
                timeLabel.setText("Time: "+Integer.toString(timer));
                Thread.currentThread().sleep(1000);

                System.out.println(timer);
            } 
            catch(InterruptedException e){return;} 
        } while(true);
        
    }
}

