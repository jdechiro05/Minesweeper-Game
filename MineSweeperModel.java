
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.util.Random;
import javax.swing.JButton;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author 22dechjack
 */
public class MineSweeperModel {

    int[][] mines;
    int[][] flags;
    boolean[][] uncovered;
    int size;
    JButton[][] buts;
    int bombAmt;
    boolean firstClick = true;
    int dubChecker = 0;
    int flagCount = 0;
    boolean flagRemove;
    int[][] mineNums;

    public MineSweeperModel(int sizeIn, int bombs) {
        //Everything getting the proper measurements 
        mines = new int[sizeIn][sizeIn];
        flags = new int[sizeIn][sizeIn];
        size = sizeIn;
        bombAmt = bombs;
        mineNums = new int[sizeIn][sizeIn];
        uncovered = new boolean[sizeIn][sizeIn];
        
        //-99 value means uncovered and a zero in numbers, makes it rather convient in the recursion uncovering to set the background colors for only the discovered 0s 
        for (int rr = 0; rr < size; rr++) {
            for (int cc = 0; cc < size; cc++) {
                mineNums[rr][cc] = -99;
            }
        }

    }

    public int bombChecker(int clickR, int clickC) { //Checks the clicked square if its a bomb
        if (mines[clickR][clickC] == 1) { //1 means theres a bomb 
            return 1; //for bomb 
        }
        return 0;   //no bomb 
    }

    public int aroundTheBlock(int r, int c) {
        int bombCounter = 0; //this variable is used to make the variable for the label of how many bombs are around
        //For getting rid of out of bounce errors
        boolean topMiddle = false, topRight = false, topLeft = false, middleLeft = false, middleRight = false, bottomLeft = false, bottomMiddle = false, bottomRight = false;

        if (r - 1 < 0) {
            topLeft = true;
            topMiddle = true;
            topRight = true;
        }
        if (c - 1 < 0) {
            topLeft = true;
            middleLeft = true;
            bottomLeft = true;
        }
        if (r + 1 > size - 1) {
            bottomLeft = true;
            bottomMiddle = true;
            bottomRight = true;
        }
        if (c + 1 > size - 1) {
            topRight = true;
            middleRight = true;
            bottomRight = true;
        }

        //--------------------------------------------------------------------------------------------------------------------------------------------------------------
        
        //Okay this section counts the bombs while making sure it doesn't run into any out of bounds errors 
        
        //Top left 
        if (topLeft == false) {  
            if (mines[r - 1][c - 1] == 1) {
                bombCounter++;
            }
        }
        //Top Middle 
        if (topMiddle == false) {
            if (mines[r - 1][c] == 1) {
                bombCounter++;
            }
        }
        //Top right 
        if (topRight == false) {
            if (mines[r - 1][c + 1] == 1) {
                bombCounter++;
            }
        }
        //Middle Left 
        if (middleLeft == false) {
            if (mines[r][c - 1] == 1) {
                bombCounter++;
            }
        }
        //Middle Right 
        if (middleRight == false) {
            if (mines[r][c + 1] == 1) {
                bombCounter++;
            }
        }
        //Bottom left
        if (bottomLeft == false) {
            if (mines[r + 1][c - 1] == 1) {
                bombCounter++;
            }
        }
        //Bottom Middle
        if (bottomMiddle == false) {
            if (mines[r + 1][c] == 1) {
                bombCounter++;
            }
        }
        //Bottom Right 
        if (bottomRight == false) {
            if (mines[r + 1][c + 1] == 1) {
                bombCounter++;
            }
        }
        return bombCounter;

    }

    public int[][] recursion(int r, int c) {
        mineNums[r][c] = -98; //made for the uncovered zeros, makes the uncovered zeros be able to set to a specific color 
        //loop for the bombs 
        for (int rr = 0; rr < size; rr++) {
            for (int cc = 0; cc < size; cc++) {
                if (mines[rr][cc] == 1) {
                    mineNums[rr][cc] = -1;    //marks the bombs with a seperate number then 1 because 1 represents 1 bomb around 
                }
            }
        }
        //For getting rid of out of bounce errors
        //Top Three From left to right 
        if (r - 1 >= 0 && c - 1 >= 0 && uncovered[r - 1][c - 1] == false && mines[r - 1][c - 1] != 1) {
            uncovered[r - 1][c - 1] = true;
            mineNums[r - 1][c - 1] = aroundTheBlock(r - 1, c - 1);
            if (mineNums[r - 1][c - 1] == 0) {
                mineNums = recursion(r - 1, c - 1);  //reruns the recursion with the new zero
            } else {
                recursionEnd(r - 1, c - 1); //runs the method of making the final line of the recursion the number of bombs around it 
            }
        }

        if (r - 1 >= 0 && c >= 0 && uncovered[r - 1][c] == false && mines[r - 1][c] != 1) {
            uncovered[r - 1][c] = true;
            mineNums[r - 1][c] = aroundTheBlock(r - 1, c);
            if (mineNums[r - 1][c] == 0) {
                mineNums = recursion(r - 1, c); //reruns the recursion with the new zero
            } else {
                recursionEnd(r - 1, c); //runs the method of making the final line of the recursion the number of bombs around it 
            }
        }

        //If you need commetns for these next 6 recursion feelers then just look at the previous two my G 
        
        if (r - 1 >= 0 && c + 1 <= size - 1 && uncovered[r - 1][c + 1] == false && mines[r - 1][c + 1] != 1) {
            uncovered[r - 1][c + 1] = true;
            mineNums[r - 1][c + 1] = aroundTheBlock(r - 1, c + 1);
            if (mineNums[r - 1][c + 1] == 0) {
                mineNums = recursion(r - 1, c + 1);
            } else {
                recursionEnd(r - 1, c + 1);
            }
        }
        // Two Middle One left and right 
        if (r >= 0 && c - 1 >= 0 && uncovered[r][c - 1] == false && mines[r][c - 1] != 1) {
            uncovered[r][c - 1] = true;
            mineNums[r][c - 1] = aroundTheBlock(r, c - 1);
            if (mineNums[r][c - 1] == 0) {
                mineNums = recursion(r, c - 1);
            } else {
                recursionEnd(r, c - 1);
            }
        }

        if (r >= 0 && c + 1 < size - 1 && uncovered[r][c + 1] == false && mines[r][c + 1] != 1) {
            uncovered[r][c + 1] = true;
            mineNums[r][c + 1] = aroundTheBlock(r, c + 1);
            if (mineNums[r][c + 1] == 0) {
                mineNums = recursion(r, c + 1);
            } else {
                recursionEnd(r, c + 1);
            }
        }

        //Bottom three from left to right 
        if (r + 1 < size && c - 1 >= 0 && uncovered[r + 1][c - 1] == false && mines[r + 1][c - 1] != 1) {
            uncovered[r + 1][c - 1] = true;
            mineNums[r + 1][c - 1] = aroundTheBlock(r + 1, c - 1);
            if (mineNums[r + 1][c - 1] == 0) {
                mineNums = recursion(r + 1, c - 1);
            } else {
                mineNums = recursionEnd(r + 1, c - 1);
            }
        }

        if (r + 1 < size && c >= 0 && uncovered[r + 1][c] == false && mines[r + 1][c] != 1) {
            uncovered[r + 1][c] = true;
            mineNums[r + 1][c] = aroundTheBlock(r + 1, c);
            if (mineNums[r + 1][c] == 0) {
                mineNums = recursion(r + 1, c);
            } else {
                recursionEnd(r + 1, c);
            }
        }

        if (r + 1 < size && c + 1 <= size - 1 && uncovered[r + 1][c + 1] == false && mines[r + 1][c + 1] != 1) {
            uncovered[r + 1][c + 1] = true;
            mineNums[r + 1][c + 1] = aroundTheBlock(r + 1, c + 1);
            if (mineNums[r + 1][c + 1] == 0) {
                mineNums = recursion(r + 1, c + 1);
            } else {
                recursionEnd(r + 1, c + 1);
            }
        }
        return mineNums;  //returns the method with the uncovered 0s and the number of bombs around numbers other than 0 
    }

    public int[][] recursionEnd(int r, int c) {
        int temp = aroundTheBlock(r, c);  //makes a temp int that holds the num of bombs around the final square in the recursion method 
        mineNums[r][c] = temp; //marks the square with the final amount of bombs around it 
        return mineNums; //returns the array to be altered with the recursion 
    }

    public int[][] bombsBeginning(int r, int c) {   //initializes the bombs 
        Random rand = new Random();
        boolean topLeft = false, topMiddle = false, topRight = false, middleLeft = false, middleRight = false, bottomLeft = false, bottomMiddle = false, bottomRight = false; //feelers for making sure u always land on a zero first time 

        for (int amount = 0; amount < bombAmt; amount++) {  //sets bombs 
            
            //Starts the loop of 
            int tempR = rand.nextInt(size - 1);
            int tempC = rand.nextInt(size - 1);
            if (tempR - 1 == r && tempC - 1 == c) {
                topLeft = true;
            }
            if (tempR - 1 == r && tempC == c) {
                topMiddle = true;
            }
            if (tempR - 1 == r && tempC + 1 == c) {
                topRight = true;
            }
            if (tempR == r && tempC - 1 == c) {
                middleLeft = true;
            }
            if (tempR == r && tempC + 1 == c) {
                middleRight = true;
            }
            if (tempR + 1 == r && tempC - 1 == c) {
                bottomLeft = true;
            }
            if (tempR + 1 == r && tempC == c) {
                bottomMiddle = true;
            }
            if (tempR + 1 == r && tempC + 1 == c) {
                bottomRight = true;
            }

            while ((tempR == r && tempC == c) || mines[tempR][tempC] == 1 || (topLeft == true || topMiddle == true || topRight == true || middleLeft == true || middleRight == true || bottomLeft == true || bottomMiddle == true || bottomRight == true)) {
                topLeft = topMiddle = topRight = middleLeft = middleRight = bottomLeft = bottomMiddle = bottomRight = false; 
                tempR = rand.nextInt(size - 1);
                tempC = rand.nextInt(size - 1);
                if (tempR - 1 == r && tempC - 1 == c) {
                    topLeft = true;
                }
                if (tempR - 1 == r && tempC == c) {
                    topMiddle = true;
                }
                if (tempR - 1 == r && tempC + 1 == c) {
                    topRight = true;
                }
                if (tempR == r && tempC - 1 == c) {
                    middleLeft = true;
                }
                if (tempR == r && tempC + 1 == c) {
                    middleRight = true;
                }
                if (tempR + 1 == r && tempC - 1 == c) {
                    bottomLeft = true;
                }
                if (tempR + 1 == r && tempC == c) {
                    bottomMiddle = true;
                }
                if (tempR + 1 == r && tempC + 1 == c) {
                    bottomRight = true;
                }

            }
            mines[tempR][tempC] = 1;
        }

        return mines;
    }

    public int flagPlace(int r, int c) {
        flagRemove = false;
        if (flags[r][c] != 1) {
            flags[r][c] = 1;
            flagCount++;
        } else {
            flags[r][c] = 0;
            flagCount--;
            flagRemove = true;
        }
        int checkBomb = 0;
        for (int rc = 0; rc < size; rc++) {
            for (int cc = 0; cc < size; cc++) {
                if (flags[rc][cc] == mines[rc][cc] && mines[rc][cc] == 1) {
                    checkBomb++;
                }
            }
        }
        if (checkBomb == bombAmt && flagCount == bombAmt) {
            return 1;
        } else if (flagRemove == true) {
            return 2;
        }
        return 0;

    }


    public void reset() {
        for (int r = 0; r < size; r++) {
            for (int c = 0; c < size; c++) {
                mines[r][c] = 0;
                flags[r][c] = 0;
                mineNums[r][c] = 0;
                uncovered[r][c] = false;
            }
        }
        flagCount = 0; 
    }

    public int click(int clickR, int clickC) {
        int checked = 0;
        checked = bombChecker(clickR, clickC);
        uncovered[clickR][clickC] = true;
        if (checked == 1) {
            return 1; //bomb 
        } else {
            return 0; //no bomb 
        }

    }
}
