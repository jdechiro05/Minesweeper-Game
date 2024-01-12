
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JTabbedPane;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.SwingUtilities;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Jack
 */
public class MineSweeperFrame extends javax.swing.JFrame implements MouseListener {

    ImageIcon flag;

    int timeCount = 1;
    int Florence = 1;
    Timer timerOne = new Timer();
    TimerTask task = new TimerTask() {
        public void run() {
            timeCount++;
            Florence = timeCount;
            timer.setText("Timer: " + timeCount);
        }

    };

    boolean timerOn = false;
    JButton[][] levelOneButs = new JButton[9][9]; //Makes the buttons 
    int[][] levelOneBombs = new int[9][9];   //If value of an array cell is 1 it is a bomb 
    int[][] levelOneBombNum = new int[9][9];
    int[][] levelOneFlags = new int[9][9];   //for flags
    int levelOneSize = 9;
    boolean firstClickLevelOne = true;

    JButton[][] levelTwoButs = new JButton[16][16]; //Makes the buttons 
    int[][] levelTwoBombs = new int[16][16]; //If value of an array cell is 1 it is a bomb
    int[][] levelTwoBombNum = new int[16][16];
    int[][] levelTwoFlags = new int[16][16];   //for flags
    int levelTwoSize = 16;
    boolean firstClickLevelTwo = true;

    JButton[][] levelThreeButs = new JButton[25][25];  //Makes the buttons 
    int[][] levelThreeBombs = new int[25][25];//If value of an array cell is 1 it is a bomb 
    int[][] levelThreeBombNum = new int[16][16];
    int[][] levelThreeFlags = new int[25][25];   //for flags
    int levelThreeSize = 25;
    boolean firstClickLevelThree = true;

    int which = 0;
    boolean clickedOne, clickedTwo, clickedThree;

    boolean flagMode = false;

    MineSweeperModel levelOneData = new MineSweeperModel(levelOneSize, 10);
    MineSweeperModel levelTwoData = new MineSweeperModel(levelTwoSize, 32);
    MineSweeperModel levelThreeData = new MineSweeperModel(levelThreeSize, 60);

    public MineSweeperFrame() {
        initComponents();
        initialize();
        addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                
            }
        });

    }

    private void initialize() {

        levelOne.setBackground(new Color(32, 117, 35));
        levelTwo.setBackground(new Color(32, 117, 35));
        levelThree.setBackground(new Color(32, 117, 35));
        tabs.setBackground(new Color(32, 117, 35));
        getContentPane().setBackground(new Color(127, 255, 226));
        resetBut.setBackground(Color.BLACK);

        levelOne.setLayout(new GridLayout(levelOneSize, levelOneSize));
        for (int r = 0; r < levelOneSize; r++) {
            for (int c = 0; c < levelOneSize; c++) {
                levelOneButs[r][c] = new JButton();
                levelOneButs[r][c].setBackground(new Color(0, 128, 0));
                levelOneButs[r][c].addMouseListener(this);
                levelOneButs[r][c].setSize(50, 50);
                levelOne.add(levelOneButs[r][c]);
            }
        }
        validate();

        levelTwo.setLayout(new GridLayout(levelTwoSize, levelTwoSize));
        for (int r = 0; r < levelTwoSize; r++) {
            for (int c = 0; c < levelTwoSize; c++) {
                levelTwoButs[r][c] = new JButton();
                levelTwoButs[r][c].setBackground(new Color(0, 128, 0));
                levelTwoButs[r][c].addMouseListener(this);
                levelTwoButs[r][c].setSize(50, 50);
                levelTwo.add(levelTwoButs[r][c]);
            }
        }
        validate();

        levelThree.setLayout(new GridLayout(levelThreeSize, levelThreeSize));
        for (int r = 0; r < levelThreeSize; r++) {
            for (int c = 0; c < levelThreeSize; c++) {
                levelThreeButs[r][c] = new JButton();
                levelThreeButs[r][c].setBackground(new Color(0, 128, 0));
                levelThreeButs[r][c].addMouseListener(this);
                levelThreeButs[r][c].setSize(50, 50);
                levelThree.add(levelThreeButs[r][c]);
            }
        }
        validate();
    }

    @Override
    public void mouseClicked(MouseEvent me) {
        int clickedR = 0, clickedC = 0, check = 0;
        switch (which) { //Checks for the screen and level it is on 

            case 0: //level One 

                for (int r = 0; r < levelOneSize; r++) { //this is to decern where the click is 
                    for (int c = 0; c < levelOneSize; c++) {
                        if (me.getSource() == levelOneButs[r][c]) {
                            clickedR = r;
                            clickedC = c;
                        }
                    }
                }
                //------------------------------------------------------------------------------------------------------------------------------         
                if (SwingUtilities.isRightMouseButton(me) && firstClickLevelOne == false) {
                    int oneDub = levelOneData.flagPlace(clickedR, clickedC); //cehcsk to see if its a normal flag placement, win or a removal of a flag with no win 
                    System.out.println(oneDub);
                    if (oneDub == 0) { //normal flag placement
                        levelOneButs[clickedR][clickedC].setBackground(Color.PINK);
                    } else if (oneDub == 1) { //win 
                        JOptionPane.showMessageDialog(null, "You WON!", "Dub City population you", JOptionPane.INFORMATION_MESSAGE);
                        levelOneButs[clickedR][clickedC].setBackground(new Color(0, 128, 0));
                    } else if (oneDub == 2) { //removal of a flag 
                        levelOneButs[clickedR][clickedC].setBackground(new Color(0, 128, 0));
                    }
                }
                
                if (firstClickLevelOne == true) { //first click 
                    levelOneBombs = levelOneData.bombsBeginning(clickedR, clickedC); //sets the beginning bombs 
                    if (timerOn == false) {
                        timerOne.scheduleAtFixedRate(task, Florence, 1000);
                        timerOn = true;
                    }
                    //CHEAT MODE 
                    for (int r = 0; r < levelOneSize; r++) {
                       for (int c = 0; c < levelOneSize; c++) {
                           if (levelOneBombs[r][c] == 1) {
                              levelOneButs[r][c].setBackground(Color.BLUE);
                           }
                       }
                   }
                    //------------------------------------------------------------------------------------------------------------------------------      
                    levelOneBombNum = levelOneData.recursion(clickedR, clickedC);

                    for (int r = 0; r < levelOneSize; r++) {
                        for (int c = 0; c < levelOneSize; c++) {
                            if (levelOneBombNum[r][c] == -98) { //checks for uncovered zeros 
                                levelOneButs[r][c].setBackground(new Color(158, 158, 158));
                            }
                            if (levelOneBombNum[r][c] != -98 && levelOneBombNum[r][c] != 0 && levelOneBombNum[r][c] > -99 && levelOneBombNum[r][c] != -1) { //In order it makes sure it isnt an uncovered 0, an uncheceked number, an unchecked 0, or a bomb  
                                levelOneButs[r][c].setBackground(new Color(158, 158, 158));
                                levelOneButs[r][c].setLabel(String.valueOf(levelOneBombNum[r][c])); //sets the number
                            }
                        }
                    }
                }

                if (firstClickLevelOne == false && !SwingUtilities.isRightMouseButton(me)) { //normal clcik 
                    check = levelOneData.click(clickedR, clickedC); //checks for a loss, or a regular click 
                    if (check == 1) { //loss 
                        levelOneButs[clickedR][clickedC].setBackground(Color.RED); //shows the location of the L 
                        JOptionPane.showMessageDialog(null, "You Suck", "Outplayed pussy", JOptionPane.INFORMATION_MESSAGE); //pops up L message
                        for (int r = 0; r < levelOneSize; r++) {
                            for (int c = 0; c < levelOneSize; c++) {
                                levelOneButs[r][c].setEnabled(false);
                            }
                        }
                    } else {
                        int bomb = levelOneData.aroundTheBlock(clickedR, clickedC); //checks the bomb amount 
                        if (bomb == 0) { //recursion for the no bombs around the button 
                            levelOneBombNum = levelOneData.recursion(clickedR, clickedC); //gest the array of all the data 

                            for (int r = 0; r < levelOneSize; r++) {
                                for (int c = 0; c < levelOneSize; c++) {
                                    if (levelOneBombNum[r][c] == -98) {  //for uncovered zeros 
                                        levelOneButs[r][c].setBackground(new Color(158, 158, 158));
                                    }
                                    if (levelOneBombNum[r][c] != -98 && levelOneBombNum[r][c] != 0 && levelOneBombNum[r][c] > -99 && levelOneBombNum[r][c] != -1) {  //In order it makes sure it isnt an uncovered 0, an uncheceked number, an unchecked 0, or a bomb
                                        levelOneButs[r][c].setBackground(new Color(158, 158, 158));
                                        levelOneButs[r][c].setLabel(String.valueOf(levelOneBombNum[r][c]));
                                    }
                                }
                            }

                        } else { //uncovers the square and labels if it wasn't a zero 
                            levelOneButs[clickedR][clickedC].setLabel(String.valueOf(bomb));
                            levelOneButs[clickedR][clickedC].setBackground(new Color(158, 158, 158));
                        }
                    }
                }
                firstClickLevelOne = false; //makes the first
                break;

//------------------------------------------------------------------------------------------------------------------------------------------
            case 1:

                for (int r = 0; r < levelTwoSize; r++) { //this is to decern where the click is 
                    for (int c = 0; c < levelTwoSize; c++) {
                        if (me.getSource() == levelTwoButs[r][c]) {
                            clickedR = r;
                            clickedC = c;
                        }
                    }
                }
                //------------------------------------------------------------------------------------------------------------------------------         
                if (SwingUtilities.isRightMouseButton(me) && firstClickLevelTwo == false) {
                    int twoDub = levelTwoData.flagPlace(clickedR, clickedC); //cehcsk to see if its a normal flag placement, win or a removal of a flag with no win 
                    System.out.println(twoDub);
                    if (twoDub == 0) { //normal flag placement
                        levelTwoButs[clickedR][clickedC].setBackground(Color.PINK);
                    } else if (twoDub == 1) { //win 
                        JOptionPane.showMessageDialog(null, "You WON!", "Dub City population you", JOptionPane.INFORMATION_MESSAGE);
                        levelTwoButs[clickedR][clickedC].setBackground(new Color(0, 128, 0));
                    } else if (twoDub == 2) { //removal of a flag 
                        levelTwoButs[clickedR][clickedC].setBackground(new Color(0, 128, 0));
                    }
                }
                
                if (firstClickLevelTwo == true) { //first click 
                    levelTwoBombs = levelTwoData.bombsBeginning(clickedR, clickedC); //sets the beginning bombs 
                    if (timerOn == false) {
                        timerOne.scheduleAtFixedRate(task, Florence, 1000);
                        timerOn = true;
                    }
                    //CHEAT MODE 
//                    for (int r = 0; r < levelTwoSize; r++) {
//                        for (int c = 0; c < levelTwoSize; c++) {
//                            if (levelTwoBombs[r][c] == 1) {
//                                levelTwoButs[r][c].setBackground(Color.BLUE);
//                            }
//                        }
//                    }
                    //------------------------------------------------------------------------------------------------------------------------------      
                    levelTwoBombNum = levelTwoData.recursion(clickedR, clickedC);

                    for (int r = 0; r < levelTwoSize; r++) {
                        for (int c = 0; c < levelTwoSize; c++) {
                            if (levelTwoBombNum[r][c] == -98) { //checks for uncovered zeros 
                                levelTwoButs[r][c].setBackground(new Color(158, 158, 158));
                            }
                            if (levelTwoBombNum[r][c] != -98 && levelTwoBombNum[r][c] != 0 && levelTwoBombNum[r][c] > -99 && levelTwoBombNum[r][c] != -1) { //In order it makes sure it isnt an uncovered 0, an uncheceked number, an unchecked 0, or a bomb  
                                levelTwoButs[r][c].setBackground(new Color(158, 158, 158));
                                levelTwoButs[r][c].setLabel(String.valueOf(levelTwoBombNum[r][c])); //sets the number
                            }
                        }
                    }
                }

                if (firstClickLevelTwo == false && !SwingUtilities.isRightMouseButton(me)) { //normal clcik 
                    check = levelTwoData.click(clickedR, clickedC); //checks for a loss, or a regular click 
                    if (check == 1) { //loss 
                        levelTwoButs[clickedR][clickedC].setBackground(Color.RED); //shows the location of the L 
                        JOptionPane.showMessageDialog(null, "You Suck", "Outplayed pussy", JOptionPane.INFORMATION_MESSAGE); //pops up L message
                        for (int r = 0; r < levelTwoSize; r++) {
                            for (int c = 0; c < levelTwoSize; c++) {
                                levelTwoButs[r][c].setEnabled(false);
                            }
                        }
                    } else {
                        int bomb = levelTwoData.aroundTheBlock(clickedR, clickedC); //checks the bomb amount 
                        if (bomb == 0) { //recursion for the no bombs around the button 
                            levelTwoBombNum = levelTwoData.recursion(clickedR, clickedC); //gest the array of all the data 

                            for (int r = 0; r < levelTwoSize; r++) {
                                for (int c = 0; c < levelTwoSize; c++) {
                                    if (levelTwoBombNum[r][c] == -98) {  //for uncovered zeros 
                                        levelTwoButs[r][c].setBackground(new Color(158, 158, 158));
                                    }
                                    if (levelTwoBombNum[r][c] != -98 && levelTwoBombNum[r][c] != 0 && levelTwoBombNum[r][c] > -99 && levelTwoBombNum[r][c] != -1) {  //In order it makes sure it isnt an uncovered 0, an uncheceked number, an unchecked 0, or a bomb
                                        levelTwoButs[r][c].setBackground(new Color(158, 158, 158));
                                        levelTwoButs[r][c].setLabel(String.valueOf(levelTwoBombNum[r][c]));
                                    }
                                }
                            }

                        } else { //uncovers the square and labels if it wasn't a zero 
                            levelTwoButs[clickedR][clickedC].setLabel(String.valueOf(bomb));
                            levelTwoButs[clickedR][clickedC].setBackground(new Color(158, 158, 158));
                        }
                    }
                }
                firstClickLevelTwo = false; //makes the first
                break;

//------------------------------------------------------------------------------------------------------------------------------------------
            case 2:

               for (int r = 0; r < levelThreeSize; r++) { //this is to decern where the click is 
                    for (int c = 0; c < levelThreeSize; c++) {
                        if (me.getSource() == levelThreeButs[r][c]) {
                            clickedR = r;
                            clickedC = c;
                        }
                    }
                }
                //------------------------------------------------------------------------------------------------------------------------------         
                if (SwingUtilities.isRightMouseButton(me) && firstClickLevelThree == false) {
                    int threeDub = levelThreeData.flagPlace(clickedR, clickedC); //cehcsk to see if its a normal flag placement, win or a removal of a flag with no win 
                    System.out.println(threeDub);
                    if (threeDub == 0) { //normal flag placement
                        levelThreeButs[clickedR][clickedC].setBackground(Color.PINK);
                    } else if (threeDub == 1) { //win 
                        JOptionPane.showMessageDialog(null, "You WON!", "Dub City population you", JOptionPane.INFORMATION_MESSAGE);
                        levelThreeButs[clickedR][clickedC].setBackground(new Color(0, 128, 0));
                    } else if (threeDub == 2) { //removal of a flag 
                        levelThreeButs[clickedR][clickedC].setBackground(new Color(0, 128, 0));
                    }
                }
                
                if (firstClickLevelThree == true) { //first click 
                    levelThreeBombs = levelThreeData.bombsBeginning(clickedR, clickedC); //sets the beginning bombs 
                    if (timerOn == false) {
                        timerOne.scheduleAtFixedRate(task, Florence, 1000);
                        timerOn = true;
                    }
                    //CHEAT MODE 
//                    for (int r = 0; r < levelThreeSize; r++) {
//                        for (int c = 0; c < levelThreeSize; c++) {
//                            if (levelThreeBombs[r][c] == 1) {
//                                levelThreeButs[r][c].setBackground(Color.BLUE);
//                            }
//                        }
//                    }
                    //------------------------------------------------------------------------------------------------------------------------------      
                    levelThreeBombNum = levelThreeData.recursion(clickedR, clickedC);

                    for (int r = 0; r < levelThreeSize; r++) {
                        for (int c = 0; c < levelThreeSize; c++) {
                            if (levelThreeBombNum[r][c] == -98) { //checks for uncovered zeros 
                                levelThreeButs[r][c].setBackground(new Color(158, 158, 158));
                            }
                            if (levelThreeBombNum[r][c] != -98 && levelThreeBombNum[r][c] != 0 && levelThreeBombNum[r][c] > -99 && levelThreeBombNum[r][c] != -1) { //In order it makes sure it isnt an uncovered 0, an uncheceked number, an unchecked 0, or a bomb  
                                levelThreeButs[r][c].setBackground(new Color(158, 158, 158));
                                levelThreeButs[r][c].setLabel(String.valueOf(levelThreeBombNum[r][c])); //sets the number
                            }
                        }
                    }
                }

                if (firstClickLevelThree == false && !SwingUtilities.isRightMouseButton(me)) { //normal clcik 
                    check = levelThreeData.click(clickedR, clickedC); //checks for a loss, or a regular click 
                    if (check == 1) { //loss 
                        levelThreeButs[clickedR][clickedC].setBackground(Color.RED); //shows the location of the L 
                        JOptionPane.showMessageDialog(null, "You Suck", "Outplayed pussy", JOptionPane.INFORMATION_MESSAGE); //pops up L message
                        for (int r = 0; r < levelThreeSize; r++) {
                            for (int c = 0; c < levelThreeSize; c++) {
                                levelThreeButs[r][c].setEnabled(false);
                            }
                        }
                    } else {
                        int bomb = levelThreeData.aroundTheBlock(clickedR, clickedC); //checks the bomb amount 
                        if (bomb == 0) { //recursion for the no bombs around the button 
                            levelThreeBombNum = levelThreeData.recursion(clickedR, clickedC); //gest the array of all the data 

                            for (int r = 0; r < levelThreeSize; r++) {
                                for (int c = 0; c < levelThreeSize; c++) {
                                    if (levelThreeBombNum[r][c] == -98) {  //for uncovered zeros 
                                        levelThreeButs[r][c].setBackground(new Color(158, 158, 158));
                                    }
                                    if (levelThreeBombNum[r][c] != -98 && levelThreeBombNum[r][c] != 0 && levelThreeBombNum[r][c] > -99 && levelThreeBombNum[r][c] != -1) {  //In order it makes sure it isnt an uncovered 0, an uncheceked number, an unchecked 0, or a bomb
                                        levelThreeButs[r][c].setBackground(new Color(158, 158, 158));
                                        levelThreeButs[r][c].setLabel(String.valueOf(levelThreeBombNum[r][c]));
                                    }
                                }
                            }

                        } else { //uncovers the square and labels if it wasn't a zero 
                            levelThreeButs[clickedR][clickedC].setLabel(String.valueOf(bomb));
                            levelThreeButs[clickedR][clickedC].setBackground(new Color(158, 158, 158));
                        }
                    }
                }
                firstClickLevelThree = false; //makes the first
            }
    }

    @Override
    public void mousePressed(MouseEvent me) {

    }

    @Override
    public void mouseReleased(MouseEvent me) {

    }

    @Override
    public void mouseEntered(MouseEvent me) {

    }

    @Override
    public void mouseExited(MouseEvent me) {

    }

    //Checks the bombs around the clicked square
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        tabs = new javax.swing.JTabbedPane();
        levelOne = new javax.swing.JPanel();
        levelTwo = new javax.swing.JPanel();
        levelThree = new javax.swing.JPanel();
        resetBut = new javax.swing.JButton();
        timer = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setPreferredSize(new java.awt.Dimension(1100, 1100));
        setResizable(false);

        tabs.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tabsMouseClicked(evt);
            }
        });

        levelOne.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                levelOneMouseClicked(evt);
            }
        });
        levelOne.setLayout(new java.awt.GridLayout(9, 9, 10, 10));
        tabs.addTab("EASY", levelOne);

        levelTwo.setLayout(null);
        tabs.addTab("MEDIUM", levelTwo);

        levelThree.setLayout(null);
        tabs.addTab("HARD", levelThree);

        resetBut.setBackground(new java.awt.Color(0, 0, 0));
        resetBut.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        resetBut.setForeground(new java.awt.Color(51, 255, 51));
        resetBut.setText("RESET CURRENT LEVEL");
        resetBut.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                resetButActionPerformed(evt);
            }
        });

        timer.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        timer.setForeground(new java.awt.Color(51, 255, 51));
        timer.setText("Timer: ");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(tabs)
            .addGroup(layout.createSequentialGroup()
                .addGap(155, 155, 155)
                .addComponent(resetBut, javax.swing.GroupLayout.PREFERRED_SIZE, 464, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(timer)
                .addGap(0, 397, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(resetBut)
                    .addComponent(timer))
                .addGap(9, 9, 9)
                .addComponent(tabs, javax.swing.GroupLayout.DEFAULT_SIZE, 716, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void tabsMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabsMouseClicked
        JTabbedPane j = (JTabbedPane) evt.getSource();
        which = j.getSelectedIndex();
    }//GEN-LAST:event_tabsMouseClicked

    private void levelOneMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_levelOneMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_levelOneMouseClicked

    private void resetButActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_resetButActionPerformed
        int screen = which;
        switch (screen) {

            case 0:
                levelOneData.reset(); //resets everything in the model 
                for (int r = 0; r < levelOneSize; r++) {  //this goes through each button and cell in the array and resets to its original value
                    for (int c = 0; c < levelOneSize; c++) {
                        levelOneBombs[r][c] = 0;
                        levelOneBombNum[r][c] = 0;
                        levelOneFlags[r][c] = 0;
                        firstClickLevelOne = true;
                        levelOneButs[r][c].setBackground(new Color(0, 128, 0));
                        levelOneButs[r][c].setLabel("");
                        levelOneButs[r][c].setEnabled(true);
                        timeCount = 0;
                    }
                }
                break;
            case 1:
                levelTwoData.reset();
                for (int r = 0; r < levelTwoSize; r++) { //this goes through each button and cell in the array and resets to its original value
                    for (int c = 0; c < levelTwoSize; c++) {
                        levelTwoBombs[r][c] = 0;
                        levelTwoBombNum[r][c] = 0;
                        levelTwoFlags[r][c] = 0;
                        firstClickLevelTwo = true;
                        levelTwoButs[r][c].setBackground(new Color(0, 128, 0));
                        levelTwoButs[r][c].setLabel("");
                        levelTwoButs[r][c].setEnabled(true);
                        timeCount = 0;
                    }
                }
                break;
            case 2:
                levelThreeData.reset();
                for (int r = 0; r < levelThreeSize; r++) { //this goes through each button and cell in the array and resets to its original value
                    for (int c = 0; c < levelThreeSize; c++) {
                        levelThreeBombs[r][c] = 0;
                        levelThreeBombNum[r][c] = 0;
                        levelThreeFlags[r][c] = 0;
                        firstClickLevelThree = true;
                        levelThreeButs[r][c].setBackground(new Color(0, 128, 0));
                        levelThreeButs[r][c].setEnabled(true);
                        levelThreeButs[r][c].setLabel("");
                        timeCount = 0;
                    }
                }
        }
    }//GEN-LAST:event_resetButActionPerformed

    /**
     * @param args the command line arguments
     */
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel levelOne;
    private javax.swing.JPanel levelThree;
    private javax.swing.JPanel levelTwo;
    private javax.swing.JButton resetBut;
    private javax.swing.JTabbedPane tabs;
    private javax.swing.JLabel timer;
    // End of variables declaration//GEN-END:variables

}
