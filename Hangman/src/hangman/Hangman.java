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
/* to do: 
fix the timers: make them start and stop at the right times, 
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
            frame = new JFrame("Hangman by Paloma and Nathan");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(1300,800);
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
                if(dificulty != null && category!=null){
                    CreateGame cg = new CreateGame(category, dificulty, frame);
                    cg.launchGame();
                    wrapper.setVisible(false);
                }
            }
        }
        
}

class CreateGame{
    String category;
    String difficulty;
    JFrame frame;
    JPanel gamePanel;
    
    WordDisplay wd;
    DrawMan hp;
    GameStatus gStatus = new GameStatus();
    // the word the user must guess
    String wordToGuess; 
    ArrayList<JButton> keyboardArr = new ArrayList<>();
    //int wrongGuesses = 0; 
    CountdownTimer countdown;
    Timer totalTime;
    JLabel countdownTimer;
   
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
   
   class LuckyGuess implements ActionListener {
       JTextField txtField = new JTextField(20);
       JFrame jf = new JFrame("Lucky Guess");

       LuckyGuess(){
           
           jf.setSize(400, 100);
           JPanel bottom = new JPanel();
           JButton luckyButton = new JButton("Lucky Guess");
          
           luckyButton.addActionListener(this);
           
           bottom.add(txtField);
           bottom.add(luckyButton);
           
           jf.add(bottom);
           jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
           jf.setVisible(true);
           
       }
       
       @Override
       public void actionPerformed(ActionEvent arg0){
           JButton jb = (JButton) arg0.getSource();
           String wordInput = txtField.getText();
           if(wordToGuess.strip().toLowerCase().equals(wordInput.strip().toLowerCase())){
               gStatus.gameOver(true);
           }
           else{
               gStatus.gameOver(false);
           }
           jf.setVisible(false);
       }
       
    }
    
    public void launchGame(){
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
        hp = new DrawMan();
        hp.setPreferredSize(new Dimension(300,600));
      
        wd = new WordDisplay(wordToGuess);
     
        JPanel wordPan = wd.generatePanel();
        wordPan.setPreferredSize(new Dimension(1300,100));
        for(JButton button : keyboardArr){
            button.addActionListener(new KeyBoardButtonActionListener(wd, hp)); 
        }
        gamePanel.add(wordPan);
        JPanel guessP = new JPanel();
        guessP.setPreferredSize(new Dimension(1300,50));
        JButton takeAGuess = new JButton("Feeling Lucky?");
        takeAGuess.setFont(new Font("Comic Sans MS", Font.PLAIN, 19));
        guessP.add(takeAGuess);
        gamePanel.add(guessP);

        gamePanel.add(keyboard);
        JPanel clockPanel = new JPanel();
        countdownTimer = new JLabel();
        Font timerFont = new Font("Comic Sans MS", Font.PLAIN, 20);
        countdown = new CountdownTimer(difficulty);
        countdown.start();
     
        countdownTimer.setFont(timerFont);
        JLabel timeKeeper = new JLabel();
        timeKeeper.setFont(timerFont);
        totalTime = new Timer(timeKeeper);
        totalTime.start();
        clockPanel.add(countdownTimer);
        clockPanel.add(timeKeeper);
        header.add(clockPanel);
        gamePanel.add(hp);
        

        takeAGuess.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent arg0){
                LuckyGuess lg = new LuckyGuess();
            }
        });
      
        frame.add(gamePanel); 
        
    }  
    
    //the keyboard you use to play the game
    class KeyBoardButtonActionListener implements ActionListener {
        int errors;
        WordDisplay wd; 
        ArrayList<String> guessedWordAsList = new ArrayList<String>(); 
        DrawMan dm;

        KeyBoardButtonActionListener(WordDisplay w, DrawMan d){
            wd = w; 
            for(int i =0; i<wordToGuess.length();i++){
                char letter = wordToGuess.charAt(i);
                letter = Character.toLowerCase(letter);
                guessedWordAsList.add(Character.toString(letter));
            }
            dm = d;
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
                    JButton changeTextofButton = wordPanelArray.get(index);
                    changeTextofButton.setText(letter.toUpperCase());
                    index = wordToGuess.indexOf(letter, index + 1);
                    
                }
                gStatus.continueGame(wordPanelArray, guessedWordAsList);
            }
            else{
                jb.setBackground(Color.RED);
                dm.errorMade();
            }
            jb.setEnabled(false);
            countdown.paused = true;
            countdown = new CountdownTimer(difficulty);
            countdown.start();
            
        }
        
    }
     
    // checks if the game should continue or not 
    class GameStatus{
        
        // use this to check if the game should go on 
        public void continueGame(ArrayList<JButton> wordPanel, ArrayList<String> guessedW){
            String wordPanelWord = "";
            for(JButton button: wordPanel){
                wordPanelWord += button.getText();
            }
            
            System.out.println(wordPanelWord +" "+ wordToGuess);
            System.out.println(wordPanelWord.toLowerCase().strip()+" "+ wordToGuess.toLowerCase().strip());
            if(wordPanelWord.toLowerCase().strip().equals(wordToGuess.toLowerCase().strip())){
                gameOver(true);
            };
        }
        // when the game is over exit out of panel, you either won or lost
        public void gameOver(boolean winStatus){
            totalTime.paused = true;
            JPanel closingPanel = new JPanel(new GridLayout(8,4)); 
            
            closingPanel.setPreferredSize(new Dimension(1300,750));
            gamePanel.setVisible(false);
            closingPanel.setVisible(true);
            if(winStatus){
                closingPanel.setBackground(Color.GREEN);
                JLabel winLabel = new JLabel("Congratulations you won!"); 
                JLabel timeLabel = new JLabel(timeConvertion(totalTime.timer));
                
                winLabel.setFont(new Font("Comic Sans MS", Font.PLAIN, 50));
                timeLabel.setFont(new Font("Comic Sans MS", Font.PLAIN, 50));
                
                winLabel.setHorizontalAlignment(JLabel.CENTER);
                timeLabel.setHorizontalAlignment(JLabel.CENTER);
                          
                closingPanel.add(winLabel);
                closingPanel.add(timeLabel);
                frame.add(closingPanel);
            }
            else{
                JLabel rightAnsw= new JLabel("The word was: "+wordToGuess);
                JLabel closeLabel = new JLabel("Better luck next time :/"); 
                
                closeLabel.setFont(new Font("Comic Sans MS", Font.PLAIN, 50));
                rightAnsw.setFont(new Font("Comic Sans MS", Font.PLAIN, 50));
                
                closeLabel.setHorizontalAlignment(JLabel.CENTER);
                rightAnsw.setHorizontalAlignment(JLabel.CENTER);
                
                closingPanel.add(rightAnsw);
                closingPanel.add(closeLabel);
                
                closingPanel.setBackground(Color.RED);
                frame.add(closingPanel);
            }
            
            JButton playAgain = new JButton("Play Again");
            playAgain.setFont(new Font("Comic Sans MS", Font.PLAIN, 50));
     
            playAgain.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent arg0){
                OpeningPage op = new OpeningPage();
                op.launch();
                frame.setVisible(false);
            }
            });
            closingPanel.add(playAgain);
            
        }
        
        private String timeConvertion(int sec){
            int mins = (sec/60)%60;
            int seconds = sec %60;
            String time = mins+ " minutes and "+seconds+ " seconds";
            return time;
        }
    }
    
    // how much time you have to guess the letter before losing a life 
    class CountdownTimer extends Thread {
        //String dificulty; 
        int countdown;
        //JLabel clockTime; 
        boolean paused;
       // DrawMan dm;
        CountdownTimer(String diff){
            difficulty = diff;
            //clockTime = timer;
            paused = false;
            setTimer();
            //dm = d;
        } 
        private void setTimer(){
            if ("easy".equals(difficulty)){
                countdown = 20;
            }
            if ("medium".equals(difficulty)){
                countdown = 13;
            }
            if ("hard".equals(difficulty)){
                countdown = 7;
            }
        }
        public void run(){
            do{
                try {
                    countdown -=1;
                    countdownTimer.setText("Time Remaining: "+Integer.toString(countdown));
                    Thread.currentThread().sleep(1000);
                } 
                catch(InterruptedException e){return;};
                if(countdown ==0){
                    setTimer();
                    hp.errorMade();
                    // also have to remove a life;
                }
            } while(countdown != 0 && !paused);

        }
    }

    // keeps track of total time running 
    class Timer extends Thread {
        JLabel timeLabel;
        int timer = 0;
        boolean paused; 

        Timer(JLabel t){
            timeLabel = t;
            paused = false;
        }

        public void run(){
            do{
               try {

                    timer +=1;
                    timeLabel.setText("Time: "+Integer.toString(timer));
                    Thread.currentThread().sleep(1000);
                } 
                catch(InterruptedException e){return;} 
            } while(true && !paused);

        }
    }
    
    // displays the words that are in the guessed word
    class WordDisplay{
        // private GenerateData guessingW ;
        private JPanel wordSlotPan = new JPanel();
        //private String wordToGuess;
        ArrayList<JButton> guessedWordButtons = new ArrayList<>();

        WordDisplay(String word){
            wordToGuess = word;
            makeWordSlots();
        }

        public JPanel generatePanel(){

            return wordSlotPan;
        }

        private void makeWordSlots(){
            //add(wordSlotPan, BorderLayout.CENTER);
            // String guessWord = guessingW.getRandomWord(game.category);
            // JButton[] wordSlots = new JButton[wordToGuess.length()];

            for (int ind = 0; ind < wordToGuess.length(); ind++){
                JButton button;
                Font displayWordF = new Font("Comic Sans MS", Font.PLAIN, 15);
                if(wordToGuess.charAt(ind)== ' '){
                    button = new JButton(" ");
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
    
    // draws the foundation and the body parts 
    class DrawMan extends JPanel {
        int error = 9;    
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D)g;
            g2.setStroke(new BasicStroke(9));
            g2.draw(new Line2D.Float(250,0,250,445)); // main, long vertical line
            g2.draw(new Line2D.Float(105,0,250,0)); // top horizontal line 
            g2.setStroke(new BasicStroke(5));              
            g2.draw(new Line2D.Float(100,0,100,50));
            g2.draw(new Line2D.Float(0,450,450,450));
            g2.setStroke(new BasicStroke(1));

            if(error<=8){
               // head/face
                g.drawOval(55,51,85,85);
                g.fillOval(70,80,15,15);
                g.fillOval(115,80,15,15);
                g.drawLine(70, 110, 90, 110);
            }
            if(error <=7){
                // torso
                g.drawRoundRect(65,135,70,100,30,30);
            }
            if(error <=6){
                // left arm
                g.drawLine(65, 150, 5, 175);
            }
            if(error <=5){
                // right arm
                g.drawLine(135, 150, 205, 175);
            }
            if(error <=4){
                // left leg
                g.drawLine(65, 190, 65, 315);
            }
            if(error <=3){
                // right leg
                g.drawLine(135, 190, 135, 315);
            }
            if(error <=2){
                // left foot
                g.drawRoundRect(40,315,27,10,10,10);
            }
            if(error <=1){
                 // right foot
                g.drawRoundRect(133,315,27,10,10,10);
                gStatus.gameOver(false);

            }
        }
        public void errorMade(){
            error-=1;
            this.repaint();
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
        singerCategory  = new ArrayList<>();
        cityCategory = new ArrayList<>();
        foodCategory = new ArrayList<>();
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
        catch(IOException e){
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
        if ("singer".equals(category)){
            int ind = getRandomNumber(singerCategory.size()-1);
            return singerCategory.get(ind);
        }
        if ("food".equals(category)){
            int ind = getRandomNumber(foodCategory.size()-1);
            return foodCategory.get(ind);
        }
        if ("city".equals(category)){
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
