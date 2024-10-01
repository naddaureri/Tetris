package main;

import java.awt.Color;
import java.util.Random;
import java.util.Scanner;



public class Tetris {
    public static final int GRID_HEIGHT = 20;
    public static final int GRID_WIDTH = 20;
    private char[][] board = new char[GRID_HEIGHT][GRID_WIDTH];
    private Tetrimono currentTetris;
    private int currX, currY;
   static Scanner scan = new Scanner(System.in);

    public Tetris() {
    
        init();
        spawnTetris();
    }
    
    public void init() {
    	// to init the value - and . to the map
        for (int i = 0; i < GRID_HEIGHT; i++) {
            for (int j = 0; j < GRID_WIDTH; j++) {
            	if(i==0 || j==0 || i== GRID_HEIGHT-1 || j == GRID_WIDTH-1) {
            		board[i][j]= '-';
            	}else {
            		board[i][j] = '.'; // Empty space
            	}
                    
                
            }
        }
    }

    public void spawnTetris() {
    	// we create 7 shapes and randomize all of thata 
        char[][] shape1 = {{'#', '#', '#', '#'}};
        char[][] shape2 = {{'#', '#', '#'}, {' ', '#', ' '}};
        char[][] shape3 = {{'#', '#'}, {'#', '#'}};
        char[][] shape4 = {{'#', '#', ' '}, {' ', '#', '#'}};
        char[][] shape5 = {{' ', '#', '#'}, {'#', '#', ' '}};
        char[][] shape6 = {{'#', '#', '#'}, {' ', ' ', '#'}};
        char[][] shape7 = {{'#', ' ', ' '}, {'#', '#', '#'}};
        
        char[][][] shapeVariants = {shape1, shape2, shape3, shape4, shape5, shape6, shape7};
        Random ran = new Random();
        int randIndex = ran.nextInt(shapeVariants.length);
        char[][] randomShape = shapeVariants[randIndex];
        currentTetris = new Tetrimono(randomShape, Color.red);
        currX = GRID_WIDTH / 2 - 1; // Centered
        currY = 1; // Start one row below the top
    }

    public boolean canMove(int deltaX, int deltaY) {
        char[][] shape = currentTetris.getShape();
        for (int i = 0; i < shape.length; i++) {
            for (int j = 0; j < shape[i].length; j++) {
            	if (shape[i][j] == '#') {
                    int newX = currX + j + deltaX;
                    int newY = currY + i + deltaY;
                    // ini kalau melebihi batas pagar 
                    if (newX < 0 || newX >= GRID_WIDTH || newY<0 || newY >= GRID_HEIGHT  ) {
                        System.out.println("karena 1");
                        freezeTetrimino();
                    	return false; 
                    	
                    }
                    //ini kalau dia adalah shape lain
                    if (board[newY][newX] != '.' && board[newY][newX] != shape[i][j] ) {
                    	if(newX != 0+1 && newX != GRID_WIDTH) {
                    		System.out.println("karena 2");
                        	return false;  // Collision with another piece or block
                    	}
                    	freezeTetrimino();
                    	
                    }
                    // ini jika menabrak tetrimonos lain
                    if(board[newY][newX]=='#' && newX!= 0 &&newX!=GRID_WIDTH) {
                    	System.out.println("Sudah tidak bisa jalan bro");
                    	freezeTetrimino();
                    	return false;
                    }
                    // karena menabrak pagar bawah 
                    if(board[newY][newX]=='-' && newY==GRID_HEIGHT) {
                    	System.out.println("karena 3");
                    
                    	return false;
                    }
                }
            }
        }
        
        return true; // Valid move
    }



    public static void main(String[] args) {
    	
    	//main function dimana kita akan start game
        Tetris newTetris = new Tetris();
        while(true) {
        	  System.out.println("Hai, welcome to the Tetris Game ! What you want to do ?");
              System.out.println("=".repeat(36));
              System.out.println("1. Play a game ");
              System.out.println("2 . View Score ");
              int input = scan.nextInt();scan.nextLine();
              switch (input) {
      		case 1:
      			 newTetris.startGame();
      			 newTetris.enter();
      		case 2:
     			newTetris.viewScore();
     			newTetris.enter();
      			break;

      		default:
      			break;
      		}
             
        }
      
    }
    
    public void enter () {
    	System.out.println("Press enter to continue ");
    	scan.nextLine();
    }
    
    public void viewScore() {
    	System.out.println(" Hai, your score is ..." + currentTetris.getScore());
    }
    public void placeTetrimono() {
    	
    	// kita mendapatkan shapenya terlebih dahulu
        char[][] shape = currentTetris.getShape();
        
        for (int i = 0; i < shape.length; i++) {
            for (int j = 0; j < shape[i].length; j++) {
                if (shape[i][j] == '#') {
                    int newY = currY + i;
                    int newX = currX + j;
                    if (newY >= 0 && newY <= GRID_HEIGHT && newX >= 0 && newX <= GRID_WIDTH ) {
                        // Cek apakah sel kosong (' '), baru kemudian isi dengan Tetrimino
                        if (board[newY][newX] == '.' || board[newY][newX]== '-' || board[newY][newX] == '#') {
                        	System.out.println("apasi");
                            board[currY][currX] = '#'; // Place the Tetrimino on the board
                        }
                    }
                }
            }
        }
    }


    public void clearLine() {
        for (int i = 1; i < GRID_HEIGHT - 1; i++) { // Avoid the top and bottom borders
            boolean isFull = true;
            for (int j = 1; j < GRID_WIDTH - 1; j++) {
                if (board[i][j] == '.') {
                    isFull = false;
                    break;
                }
            }
            if (isFull) {
                // Shift down all rows above the cleared line
                for (int k = i; k > 1; k--) {
                    board[k] = board[k - 1];
                }
                // Clear the top row (or new row at the bottom)
                for (int j = 1; j < GRID_WIDTH - 1; j++) {
                    board[1][j] = '.';
                }
                i++;
            }
        }
    }
    
    public void printTempBoard(char[][] tempBoard) {
        for (int i = 0; i < GRID_HEIGHT; i++) {
            for (int j = 0; j < GRID_WIDTH; j++) {
                System.out.print(tempBoard[i][j] + " ");
            }
            System.out.println();
        }
    }
    public void freezeTetrimino() {
        char[][] shape = currentTetris.getShape();
        
        for (int i = 0; i < shape.length; i++) {
            for (int j = 0; j < shape[i].length; j++) {
                if (shape[i][j] == '#') {
                    int newX = currX + j;
                    int newY = currY + i;

                    // Validasi apakah koordinat masih dalam batas grid
                    if (newX >= 0 && newX < GRID_WIDTH && newY >= 0 && newY < GRID_HEIGHT) {
                        board[newY][newX] = '#';  // Bekukan Tetrimino di papan utama
                    }
                }
            }
        }
        clearLine();
        int score = currentTetris.getScore()+1;
        currentTetris.setScore(score);
    }
    // Fungsi untuk mencetak papan utama
    public void printBoard() {
        for (int i = 0; i < GRID_HEIGHT; i++) {
            for (int j = 0; j < GRID_WIDTH; j++) {
                System.out.print(board[i][j] + " ");
            }
            System.out.println();
        }
    } 
    public void drawTetrimino() {
        // Buat buffer sementara dari papan utama
        char[][] tempBoard = new char[GRID_HEIGHT][GRID_WIDTH];
        
        // Salin semua konten papan utama ke buffer sementara
        for (int i = 0; i < GRID_HEIGHT; i++) {
            for (int j = 0; j < GRID_WIDTH; j++) {
                tempBoard[i][j] = board[i][j];  // Menyalin isi papan asli
            }
        }

        // Gambar Tetrimino saat ini ke buffer sementara
        char[][] shape = currentTetris.getShape();
        for (int i = 0; i < shape.length; i++) {
            for (int j = 0; j < shape[i].length; j++) {
                if (shape[i][j] == '#') {
                    tempBoard[currY + i][currX + j] = '#';  // Tampilkan Tetrimino yang bergerak
                }
            }
        }

        // Cetak buffer sementara ke layar
        printTempBoard(tempBoard);  // Cetak papan sementara untuk tampilan
    }
    
    public void startGame() {
        Scanner scan = new Scanner(System.in);

        while (true) {
            // Gambar tetrimino saat ini dan print papan
        	drawTetrimino();
           
            // Check if Tetrimino can move down
            if (canMove(0, 1)) {
            	
            	 System.out.println("Press 'D' for DOWN, 'L' for LEFT, 'R' for RIGHT ");
                 String input = scan.nextLine().toUpperCase();

                 // Mengecek pergerakan berdasarkan input
                 switch (input) {
                     case "R": // Move Right
                         if (canMove(1, 0)) {
                             currX++;
                         }
                         break;
                     case "L": // Move Left
                         if (canMove(-1, 0)) {
                             currX--;
                         }
                         break;
                     case "D": // Move Down
                         if (canMove(0, 1)) {
                             currY++;
                         }
                         break;
                     default:
                         System.out.println("Invalid input. Use 'R', 'L', or 'D'.");
                         break;
                 }

            } else {
                // Jika tidak bisa bergerak ke bawah, tempatkan tetrimino di papan
            	 freezeTetrimino();
                spawnTetris();
                
            }

            // Menerima input dari pengguna
           
            // Delay 500ms
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    
}
