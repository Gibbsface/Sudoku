package sudoku;

public class Solver {
	
	public boolean possible;
	private static int[][] matrix;
	
    public Solver(int[][] puzzle) {
		
    	possible = solve(0,0,puzzle);
    	matrix = puzzle;    	
	}

    static boolean solve(int i, int j, int[][] grid) {
    	
    	//end condition
    	if (i == 9) {
            i = 0;
            if (++j == 9) {
                return true;
            }
        }
        
    	//if you find a nonzero cell, skip it and attempt to solve the next one
        if (grid[i][j] != 0) {
            return solve(i+1,j,grid);
        }
        
        //Assuming this is an unsolved cell, we will guess different values till we find one that works
        for (int val = 1; val <= 9; ++val) {
            if (legal(i,j,val,grid)) {
                grid[i][j] = val;
                //We found a legal answer, so we will proceed with recursion until we find a contradiction or a solution
                if (solve(i+1,j,grid)) {
                    return true;
                }
            }
        }
        
        //if we make it to this point, then we've found a contradiction and we need to reset this cell and backtrack
        grid[i][j] = 0;
        return false;
    }

    static boolean legal(int r, int c, int val, int[][] grid) {
        
    	//test rows for legality
    	for (int i = 0; i < 9; ++i) {
    		if (val == grid[i][c]) {
    			return false;
    		}
    	}
            
    	//test columns for legality
        for (int j = 0; j < 9; ++j) {
        	if (val == grid[r][j]) {
        		//Contradiction found, return to recursion and reset guess
        		return false;
        	}
        }
        
        //test box for legality
        int boxRow = (r / 3)*3;
        int boxCol = (c / 3)*3;
        for (int i = 0; i < 3; ++i) {
        	for (int j = 0; j < 3; ++j) {
        		if (val == grid[boxRow + i][boxCol + j]) {
        			//Contradiction found, return to recursion and reset guess
                	return false;
                }
        	}
        }
        
        //if we get to this point, we have found no contradictions and our current guess is legal
        return true; 
    }
    
    public int[][] getAnswer() {
    	return matrix;
    }
	
}
