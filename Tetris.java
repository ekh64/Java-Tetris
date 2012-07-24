import java.awt.*;

import javax.swing.*;

import java.awt.event.*;

import java.util.Scanner;
import java.io.*;

public class Tetris extends JApplet{
	
	
	public void init()
	{
		setLayout(new FlowLayout());
		JPanel panel = new TetrisPanel();
		panel.setLayout(null);
		add(panel);
	}
	
	public static void main(String[] args) {
		JFrame frame = new JFrame();
		frame.setTitle("Tetris");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		JApplet applet = new Tetris();
		applet.init();
		frame.getContentPane().add(applet);
		//frame.setSize(250,450);
		frame.pack();
		frame.setVisible(true);
	}

	class TetrisPanel extends JPanel implements ActionListener {
		Timer falling;
		Tetromino currentTetromino;
		int[][] grid = new int[12][22];
		int[][] newgrid = new int[12][22];
		int[][] border = new int[12][22];
		int pwidth = 400;
		int pheight = 450;
		boolean landed = false;
		String letter = "";
		int level = 1;
		int score = 0;
		boolean isStopped = false;
		boolean end = false;
		JButton newgame,highscores;
		String[] name = new String[10];
		int[] topscore = new int[10];
		int stop = 0;
		
		
		public Dimension getMiniumSize(){return getPreferredSize();}
		
		public Dimension getPreferredSize(){
			return new Dimension(pwidth,pheight);
		}
		
		public TetrisPanel(){
			setPreferredSize(new Dimension(pwidth,pheight));
			currentTetromino = new Tetromino();
			setBackground(Color.BLACK);
			
			highscorelist();
		
			newgame = new JButton("New Game");
			newgame.addActionListener(this);
			add(newgame);
			newgame.setBounds(260, 100, 120, 20);
			
			highscores = new JButton("High Scores");
			highscores.addActionListener(this);
			add(highscores);
			highscores.setBounds(260, 300, 120, 20);
			
			falling = new Timer(600,this);
			//falling.start();
			
			addKeyListener(new KeyHandler());
			setFocusable(true);
			
			
		}
		
		public void paintComponent(Graphics g){
			
			if(landed){
				rowCheck();
				currentTetromino = new Tetromino(randomTetromino());
				landed = false;
				score+=25;
				end = gameOver();
			}
			if(score>=500 && score<1000){
				level = 2;
				falling.setDelay(550);
			}
			if(score>=1000 && score<2000){
				level = 3;
				falling.setDelay(400);
			}
			if(score>=2000 && score<7000){
				level = 4;
				falling.setDelay(300);
			}
			if(score>=7000){
				level = 5;
				falling.setDelay(100);
			}
			if(!end)
				merge(currentTetromino);
			super.paintComponent(g);
				for(int i = 0; i<240;i+=20){
					for(int j = 0;j<440;j+=20){
						int k = i/20;
						int m = j/20;
						grid[0][m] = 8;
						grid[11][m] = 8;
						grid[k][0] = 8;
						grid[k][21] = 8;
						Color color = ColorCheck(k,m);
						g.setColor(color);
						g.fillRect(i,j,20,20);
						g.setColor(Color.BLACK);
						g.drawRect(i, j, 20, 20);
					}
				}
				Font f = new Font("monospaced",Font.BOLD,18);
				g.setFont(f);
				g.setColor(Color.lightGray);
				g.drawString("Level "+level, 260, 220);
				g.drawString("Score "+score, 260, 200);
				Font dead = new Font("monospaced",Font.BOLD,24);
				Font small = new Font("monospaced",Font.PLAIN,14);
				if(end){
					falling.stop();
					g.setColor(Color.WHITE);
					g.fillRect(15, 150, 210, 130);
					g.setColor(Color.RED);
					g.drawRect(15, 150, 210, 130);
					g.setColor(Color.BLACK);
					g.setFont(dead);
					g.drawString("GAME OVER", 60, 200);
					g.setFont(small);
					g.drawString("Press New Game", 50, 240);
					g.drawString("to try again", 65, 255);
					if(stop == 0)
						reviseList();
					stop++;
				}	
				if(isStopped){
					g.setColor(Color.WHITE);
					g.fillRect(15, 150, 210, 130);
					g.setColor(Color.RED);
					g.drawRect(15, 150, 210, 130);
					g.setColor(Color.BLACK);
					g.setFont(dead);
					g.drawString("PAUSED", 80, 200);
					g.setFont(small);
					g.drawString("Press P to resume", 50, 245);
				}

		}
		
		private boolean gameOver(){
			boolean temp = false;
			if(grid[5][1] > 0){
				temp = true;
			}
			return temp;
		}
		
		public Color ColorCheck(int i,int j){
			Color c = Color.BLACK;
			if(newgrid[i][j] == 0 && grid[i][j]!=8){
				c = Color.BLACK;
			}
			else if(newgrid[i][j] == 1 && grid[i][j]!=8){
				c = new Color(255,115,0);
			}
			else if(newgrid[i][j] == 2 && grid[i][j]!=8){
				c = Color.BLUE;
			}
			else if(newgrid[i][j] == 3 && grid[i][j]!=8){
				c = Color.CYAN;
			}
			else if(newgrid[i][j] == 4 && grid[i][j]!=8){
				c = Color.YELLOW;
			}
			else if(newgrid[i][j] == 5 && grid[i][j]!=8){
				c = Color.GREEN;
			}
			else if(newgrid[i][j] == 6 && grid[i][j]!=8){
				c = Color.MAGENTA;
			}
			else if(newgrid[i][j] == 7 && grid[i][j]!=8){
				c = Color.RED;
			}
			else if(grid[i][j] == 8){
				c = Color.GRAY;
			}
			return c;
		}
		
		public void reviseList(){
			Scanner s = new Scanner(System.in);
			String[] temp = new String[10];
			int[] tempS = new int[10];
			int stoppoint = -1;
			Out myoutfile = new Out("highscores.txt");
			
			for(int i=0;i<10;i++){
				temp[i] = name[i];
				tempS[i] = topscore[i];
				if(score > topscore[i]){
					System.out.println("High Score! Enter your initials!");
					temp[i] = s.next();
					tempS[i] = score;
					stoppoint = i;
					i = 10;
				}
			}
			if(stoppoint < 0){
				return;
			}
			else if(stoppoint<9){
				for(int i = stoppoint; i<9;i++){
					temp[i+1] = name[i]; 
					tempS[i+1] = topscore[i];
				}
			}
			for(int i = 0; i<10;i++){
				name[i] = temp[i];
				topscore[i] = tempS[i];
				System.out.printf("%s ",name[i]);
				System.out.printf("%d\n",topscore[i]);
				myoutfile.println(name[i] + " " + topscore[i]);
			}
		}
		
		public void highscorelist(){
			Scanner fin = null;
			try{
				fin = new Scanner(new File("highscores.txt"));
				for(int i = 0;i<10;i++){
					name[i] = fin.next();
					topscore[i] = fin.nextInt();
				}
			}
			catch(FileNotFoundException e) {
				System.out.println ("File not found!");
				// Stop program if no file found
				System.exit (0);
			}
		}
		
		public String randomTetromino(){
			int num = (int) Math.round(Math.random()*10);
			while(num >= 8 || num == 0){
				num = (int) Math.round(Math.random()*10);
			}
			if(num == 1){
				letter = "L";
			}
			else if(num == 2){
				letter = "J";
			}
			else if(num == 3){
				letter = "I";
			}
			else if(num == 4){
				letter = "O";
			}
			else if(num == 5){
				letter = "S";
			}
			else if(num == 6){
				letter = "T";
			}
			else if(num == 7){
				 letter = "Z";
			}
			return letter;
		}
		
		public void rowCheck(){
			
			int rowcount = 0;
			for(int j = 1; j<grid[0].length-1;j++){
				int bonus = 1;
				rowcount = 0;
				for(int i = 1; i<grid.length-1; i++){
					if(grid[i][j]>0){
						rowcount++;
					}
				}
				if(rowcount == 10){
					chain(j);
					score += bonus*50;
					bonus++;
					
				}
			}
			
			
			
		}
		
		public void chain(int row){
			int [][] temp = copy(grid);
			for(int i = 1; i<grid.length-1;i++){
				temp[i][row] = 0; 
				int k = row;
				while(grid[i][k-1] > 0){
					temp[i][k] = grid[i][k-1];
					temp[i][k-1] = 0;
					k--;
				}
			}
			int [][] temp2 = copy(temp);
			for(int i = 1; i<grid.length-1;i++){
				for(int j = 1; j<row;j++){
					if(temp[i][j]>0 && temp[i][j+1] == 0){
						temp2[i][j] = 0;
						temp2[i][j+1] = temp[i][j];
					}
				}
			}
			grid = copy(temp2);
		}
		
		public int[][] copy(int[][]original){
			int[][] dupe = new int [original.length][original[0].length];
			for(int i = 0; i < original.length; i++){
				for(int j = 0; j < original[0].length; j++){
					dupe[i][j] = original[i][j];
				}
			}
			return dupe;
		}
		
		public void merge(Tetromino t){
			for(int j = 0; j < t.a[0].length - 1; j++){
				for(int i = 0; i < t.a.length - 1; i++){
					if(t.a[i][j]>0 && grid[i][j+1] > 0){
						landed = true;
						newgrid[i][j] = t.a[i][j] + grid[i][j];
					}
					else{
						newgrid[i][j] = t.a[i][j] + grid[i][j];
					}
				}
			}
			
			if(landed){
				grid = copy(newgrid);
			}
		}
		public void actionPerformed(ActionEvent e){
			
			if(e.getSource().equals(newgame)){
				currentTetromino = new Tetromino(randomTetromino());
				isStopped = false;
				end = false;
				stop = 0;
				score = 0;
				level = 1;
				for(int i = 1;i<grid.length-1;i++){
					for(int j = 1; j<grid[0].length-1;j++){
						grid[i][j]=0;
					}
				}
				grabFocus();
				falling.start();
			}
			if(e.getSource().equals(highscores)){
				grabFocus();
				for(int i = 0; i< 10; i++){
					System.out.println(name[i]+"\t"+topscore[i]);
				}
				repaint();
			}
			else{
				currentTetromino.drop();
				repaint();
			}	
		}	
	
	private class KeyHandler implements KeyListener{
		int[][] temp = new int[12][22];
		
		
		public void reset(){
			for(int i = 0; i<temp.length;i++){
				for(int j = 0; j<temp[0].length;j++){
					temp[i][j] = 0;
				}
			}
		}
		
		public void keyPressed(KeyEvent event) {
			boolean ok = true;
			
		    switch (event.getKeyCode()) {
		    case KeyEvent.VK_LEFT:
		    	reset();
		    	if(!isStopped || !end){
			    	for(int i = 0; i<currentTetromino.a.length; i++){
			    		for(int j = 0; j<currentTetromino.a[0].length-1;j++){
			    			if(currentTetromino.a[i][j] > 0){
			    				temp[i-1][j] = currentTetromino.a[i][j];
			    			}
			    			if (currentTetromino.a[i][j] > 0 && grid[i-1][j] != 0) ok = false;
			    		}
			    	}
			    	if (ok)
			    		currentTetromino.a = copy(temp);
		    	}
		    	break;

		    case KeyEvent.VK_RIGHT:
		    	reset();
		    	if(!isStopped || !end){
		    		for(int i = 0; i<currentTetromino.a.length-1; i++){
		    			for(int j = 0; j<currentTetromino.a[0].length-1;j++){
		    				if(currentTetromino.a[i][j] > 0 && grid[i+1][j]==0){
		    					temp[i+1][j] = currentTetromino.a[i][j];
		    				} 
		    				if (currentTetromino.a[i][j] > 0 && grid[i+1][j] != 0) ok = false;


		    			}
		    		}
		    		if (ok)
		    			currentTetromino.a = copy(temp);
		    	}
		    	break;

		    case KeyEvent.VK_DOWN:
		    	reset();
		    	//System.out.println(isStopped);
		    	if(!isStopped ||!end){
		    		for(int i = 0; i<currentTetromino.a.length; i++){
		    			for(int j = 0; j<currentTetromino.a[0].length-1;j++){
		    				if(currentTetromino.a[i][j] > 0 && grid[i][j+1]==0){
		    					temp[i][j+1] = currentTetromino.a[i][j];
		    				}
		    			}
		    		}	
		    		currentTetromino.a = copy(temp);
		    	}
		    	break;
		    	
		    case KeyEvent.VK_P:
		    	if(isStopped == false && !end){
		    		falling.stop();
		    		isStopped = true;
		    	}
		    	else if(isStopped == true){
		    		isStopped = false;
		    		falling.start();
		    		
		    	}
		    	
		    	break;
		    	
		    case KeyEvent.VK_UP:
		    	if(!isStopped || !end){
		    		currentTetromino.getLocation();
		    		currentTetromino.rotate(letter);
		    	}
		    	break;	
		    	
		    }
		    
		    
		    	

		    repaint();

		}
		public void keyReleased(KeyEvent event) {}

		public void keyTyped(KeyEvent event) {}
	    }
	}
}