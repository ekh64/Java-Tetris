
public class Tetromino {
	public int[][] a = new int[12][22];
	private int x = 0;
	private int y = 0;
	private boolean alreadyRotated = false;
	private int position = 0;
	public Tetromino(String s){
		Tet(s);
	}
	public Tetromino(){}
	
	private void Tet(String s){
		if(s.compareTo("L")==0){
			a = L();
		}
		
		if(s.compareTo("J")==0){
			a = J();
		}
		
		if(s.compareTo("I")==0){
			a = I();
		}
		
		if(s.compareTo("O")==0){
			a = O();
		}
		
		if(s.compareTo("S")==0){
			a = S();
		}
		
		if(s.compareTo("T")==0){
			a = T();
		}
		
		if(s.compareTo("Z")==0){
			a = Z();
		}
	}
	
	private int[][] J(){
		int[][] g = new int [12][22];
		g[5][1] = 2;
		g[6][1] = 2;
		g[7][1] = 2;
		g[5][0] = 2;
		return g;
	}
	
	private int[][] L(){
		int[][] g = new int[12][22];
		g[5][1] = 1;
		g[6][1] = 1;
		g[7][1] = 1;
		g[7][0] = 1;
		return g;
	}
	
	private int[][] I(){
		int[][] g = new int[12][22];
		g[4][0] = 3;
		g[5][0] = 3;
		g[6][0] = 3;
		g[7][0] = 3;
		return g;
	}
	
	private int[][] O(){
		int[][] g = new int[12][22];
		g[5][0] = 4;
		g[6][0] = 4;
		g[5][1] = 4;
		g[6][1] = 4;
		return g;
	}
	
	private int[][] S(){
		int[][] g = new int[12][22];
		g[5][1] = 5;
		g[6][1] = 5;
		g[6][0] = 5;
		g[7][0] = 5;
		return g;
	}
	
	private int[][] T(){
		int[][] g = new int[12][22];
		g[5][1] = 6;
		g[6][1] = 6;
		g[6][0] = 6;
		g[7][1] = 6;
		return g;
	}
	
	private int[][] Z(){
		int[][] g = new int[12][22];
		g[5][0] = 7;
		g[6][0] = 7;
		g[6][1] = 7;
		g[7][1] = 7;
		return g;
	}
	
	private int[][] copy(int[][]original){
		int[][] dupe = new int [12][22];
		for(int i = 0; i < original.length; i++){
			for(int j = 0; j < original[0].length; j++){
				dupe[i][j] = original[i][j];
			}
		}
		return dupe;
	}
	
	public int[][] drop(){
		int[][] temp = new int[a.length][a[0].length];
		for(int i = 0; i < temp.length - 1; i++){
			for(int j = 0; j < temp[0].length - 1; j++){
				temp[i][j+1] = a[i][j];
			}
		}
		a = copy(temp);
		return a;
	}
	
	public void getLocation(){
		for(int i = 0; i <a.length; i++){
			for(int j = 0; j<a[0].length;j++){
				if(a[i][j]>0){
					x = i;
					y= j;
					return;
				}
			}
		}
	}
	
	public void rotate(String s){
		int[][] temp = new int[a.length][a[0].length];
		
		
		if(s.compareTo("L")==0){
			rotateL();
		}
		
		if(s.compareTo("J")==0){
			rotateJ();
		}
		
		if(s.compareTo("I")==0){
			rotateI();
		}
		
		if(s.compareTo("O")==0){
			return;
		}
		
		if(s.compareTo("S")==0){
			rotateS();
		}
		
		if(s.compareTo("T")==0){
			rotateT();
		}
		
		if(s.compareTo("Z")==0){
			rotateZ();
		}
		
		
		
	}
	
	private void rotateT(){
		if(!alreadyRotated){
			alreadyRotated = !alreadyRotated;
			position = 0;
		}	
		int[][] temp = new int[a.length][a[0].length];
		if(position % 4 == 0){
			x = x+1;
			temp[x][y] = a[x][y];
			temp[x][y+1] = a[x-1][y];
			temp[x][y-1] = a[x+1][y];
			temp[x+1][y] = a[x][y-1];
		}
		else if(position % 4 == 1){
			if(x-1==0){
				return;
			}
			else{
				y = y+1;
				temp[x][y] = a[x][y];
				temp[x+1][y] = a[x][y-1];
				temp[x-1][y] = a[x][y+1];
				temp[x][y+1] = a[x+1][y];
			}
		}
		else if(position % 4 == 2){
			x = x+1;
			temp[x][y] = a[x][y];
			temp[x][y+1] = a[x-1][y];
			temp[x][y-1] = a[x+1][y];
			temp[x-1][y] = a[x][y+1];
		}
		else if(position % 4 == 3){
			x = x+1;
			if(x+1==a.length-1){
				return;
			}
			else{
				temp[x][y] = a[x][y];
				temp[x+1][y] = a[x][y-1];
				temp[x-1][y] = a[x][y+1];
				temp[x][y-1] = a[x-1][y];
			}
		}
		position++;
		a = copy(temp);
	}
	
	private void rotateZ(){
		if(!alreadyRotated){
			alreadyRotated = !alreadyRotated;
			position = 0;
		}
		int[][] temp = new int[a.length][a[0].length];
		if(position % 2 == 0){
			x = x+1;
			temp[x][y] = a[x][y];
			temp[x-1][y] = a[x-1][y];
			temp[x-1][y+1] = a[x][y+1];
			temp[x][y-1] = a[x+1][y+1];
		}
		else if(position % 2 == 1){
			x = x+1;
			if(x+1 == a.length-1){
				return;
			}
			else{
				temp[x][y] = a[x][y];
				temp[x-1][y] = a[x-1][y];
				temp[x][y+1] = a[x][y-1];
				temp[x+1][y+1] = a[x-1][y+1];
			}
		}
		position++;
		a = copy(temp);
	}
	
	private void rotateS(){
		if(!alreadyRotated){
			alreadyRotated = !alreadyRotated;
			position = 0;
		}
		int[][] temp = new int[a.length][a[0].length];
		if(position % 2 == 0){
			try{
				x = x+1;
				y = y-1;
				temp[x][y] = a[x][y];
				temp[x+1][y] = a[x+1][y];
				temp[x+1][y+1] = a[x][y+1];
				temp[x][y-1] = a[x-1][y+1];
			}
			catch(ArrayIndexOutOfBoundsException e){
				return;
			}
		}
		else if(position % 2 == 1){
			y = y+1;
			if(x-1==0){
				return;
			}
			else{
				temp[x][y] = a[x][y];
				temp[x+1][y] = a[x+1][y];
				temp[x][y+1] = a[x][y-1];
				temp[x-1][y+1] = a[x+1][y+1];
			}
		}
		position++;
		a = copy(temp);
	}	
	
	private void rotateL(){
		if(!alreadyRotated){
			alreadyRotated = !alreadyRotated;
			position = 0;
		}
		int[][] temp = new int[a.length][a[0].length];
		if(position % 4 == 0){
			x = x+1;
			temp[x][y] = a[x][y];
			temp[x][y-1] = a[x-1][y];
			temp[x][y+1] = a[x+1][y];
			temp[x+1][y+1] = a[x+1][y-1];
		}
		else if(position % 4 == 1){
			y = y+1;
			if(x-1 == 0){
				return;
			}
			else{
				temp[x][y] = a[x][y];
				temp[x+1][y] = a[x][y-1];
				temp[x-1][y] = a[x][y+1];
				temp[x-1][y+1] = a[x+1][y+1];
			}
		}
		else if(position % 4 == 2){
			x = x+1;
			temp[x][y] = a[x][y];
			temp[x][y+1] = a[x+1][y];
			temp[x][y-1] = a[x-1][y];
			temp[x-1][y-1] = a[x-1][y+1];
		}
		else if(position % 4 == 3){
			x = x+1;
			y = y+1;
			if(x+1==a.length-1){
				return;
			}
			else{
				temp[x][y] = a[x][y];
				temp[x+1][y] = a[x][y-1];
				temp[x-1][y] = a[x][y+1];
				temp[x+1][y-1] = a[x-1][y-1];
			}	
		}
		position++;
		a = copy(temp);
	}
	
	private void rotateJ(){
		if(!alreadyRotated){
			alreadyRotated = !alreadyRotated;
			position = 0;
		}
		
		int[][] temp = new int[a.length][a[0].length];
		if(position % 4 == 0){
			y = y+1;
			x = x+1;
			temp[x][y] = a[x][y];
			temp[x+1][y-1] = a[x-1][y-1];
			temp[x][y-1] = a[x-1][y];
			temp[x][y+1] = a[x+1][y];
		}
		else if(position % 4 == 1){
			y = y+1;
			if(x-1==0){
				return;
			}
			else{
				temp[x][y] = a[x][y];
				temp[x+1][y+1] = a[x+1][y-1];
				temp[x+1][y] = a[x][y-1];
				temp[x-1][y] = a[x][y+1];
			}
		}
		else if(position % 4 == 2){
			
			x = x+1;
			temp[x][y] = a[x][y];
			temp[x][y-1] = a[x-1][y];
			temp[x][y+1] = a[x+1][y];
			temp[x-1][y+1] = a[x+1][y+1];
		}
		else if(position % 4 == 3){
			
			y = y-1;
			x = x+1;
			if(x+1==a.length-1){
				return;
			}
			else{
				temp[x][y] = a[x][y];
				temp[x+1][y] = a[x][y-1];
				temp[x-1][y] = a[x][y+1];
				temp[x-1][y-1] = a[x-1][y+1];
			}	
		}
		position++;
		a = copy(temp);
	}
	
	private void rotateI(){
		if(!alreadyRotated){
			alreadyRotated = !alreadyRotated;
			position = 0;
		}
		int[][] temp = new int[a.length][a[0].length];
		if(position % 2 == 0){
			x = x+1;
			temp[x][y] = a[x][y];
			temp[x][y-1] = a[x-1][y];
			temp[x][y+1] = a[x+1][y];
			temp[x][y+2] = a[x+2][y];
		}
		else if(position % 2 == 1){
			if(x-1 == 0 || x+1 == a.length-1 || x+2==a.length-1){
				return;
			}
			else{
				y = y+1;
				temp[x][y] = a[x][y];
				temp[x-1][y] = a[x][y-1];
				temp[x+1][y] = a[x][y+1];
				temp[x+2][y] = a[x][y+2];
			}
		}
		position++;
		a = copy(temp);
	}
}
