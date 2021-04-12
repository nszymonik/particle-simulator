//TO DO: Add your name (as an author), complete Resize().
/**
 *  The simulator. This tracks the elements in a grid
 *  and coordinates that with the display.
 *  
 *  @author Dave Feinberg, K. Raven Russell, and Nicholas Szymonik
 */
public class Simulation {
	
	//******************************************************
	//*******  DO NOT EDIT ANYTHING IN THIS SECTION  *******
	//*******        But do read this section!       *******
	//******************************************************
	
	/**
	 *  The default number of rows the grid should have.
	 */
	public static final int INIT_ROWS = 120;
	
	/**
	 *  The default number of columns the grid should have.
	 */
	public static final int INIT_COLS = 80;

	/**
	 *  The grid that represents the location of each element.
	 */
	private final DynamicArray<DynamicArray<Element>> grid;
	
	/**
	 *  The GUI display.
	 */
	private final Display display;
	
	//******************************************************
	//* END DO-NOT-EDIT SECTION -- DO NOT ADD MORE FIELDS! *
	//******************************************************
	
	/**
	 *  Sets up the instance variables (grid and display).
	 *  
	 *  @param withDisplay whether or not the display should be created (for testing purposes)
	 */
	public Simulation(boolean withDisplay) {
		// Initialize the grid (see above) to the default size (see above).
		// Fill the grid with empty void.
		
		// If the simulation should be created with a display, then set display
		// (see above) to a new display. Use the title "Project 1 Simulation",
		// the default number of rows and columns (see above), and the display
		// constructor (see Display.java) to do this.
		
		// If the simulation should be created without a display, then initialize
		// the display to null (or Java will yell at you).
		grid = new DynamicArray<DynamicArray<Element>>(INIT_ROWS);
		for (int i = 0; i < grid.capacity(); ++i) {
			DynamicArray<Element> de = new DynamicArray<Element>(INIT_COLS);
			for (int j = 0; j < de.capacity(); ++j) {
				de.add(new Empty());
			}
			grid.add(de);
		}
		display = withDisplay ? new Display("Project 1 Simulation", INIT_ROWS, INIT_COLS) : null;
	}

	/**
	 *  This is called when the user clicks on a location using the given tool.
	 *  
	 *  @param row the row where the action happened
	 *  @param col the column where the action happened
	 *  @param newElem the element the user has created to put there
	 */
	public void locationClicked(int row, int col, Element newElem) {
		grid.get(row).set(col, newElem);
	}

	/**
	 *  Copies each element of grid's color into the display.
	 */
	public void updateDisplay() {
		for(int row = 0; row < grid.size(); ++row) {
			for (int col = 0; col < grid.get(row).size(); ++col) {
				display.setColor(row, col, grid.get(row).get(col).getColor());
			}
		}
		// Update each cell of the display (see above) with
		// the correct color for that cell. The display has one
		// cell per grid element (one-to-one) and the color of
		// the cell is just the color of the element from the grid.
		
		// Remember: Display has a setColor(row, col, color) method,
		// and elements have a getColor() method.
	}
	
	/**
	 *  Resizes the grid (if needed) to a bigger or smaller size.
	 *  
	 *  @param numRows the new number of rows the grid should have
	 *  @param numCols the new number of columns the grid should have
	 *  @return whether or not anything was changed
	 */
	public boolean resize(int numRows, int numCols) {
		if(numRows < 1 || numCols < 1) {
			return false;
		}
		//Adding Rows
		while (numRows > grid.size()) {
			int width = grid.get(0).size();
			DynamicArray<Element> de = new DynamicArray<Element>(width);
			for (int i = 0; i < width; ++i) {
				de.add(new Empty());
			}
			grid.add(0, de);
		}
		//Removing Rows
		while (numRows < grid.size()) {
			int width = grid.get(0).size();
			grid.remove(0);
		}
		//Adding Cols
		while (numCols > grid.get(0).size()) {
			int height = grid.size();
			for (int i = 0; i < height; ++i) {
				grid.get(i).add(new Empty());
			}
		}
		//Removing Cols
		while (numCols < grid.get(0).size()) {
			int height = grid.size();
			for (int i = height-1; i >= 0; --i) {
				DynamicArray<Element> currentRow = grid.get(i);
				int last = currentRow.size()-1; 
				currentRow.get(last).pushLeft(grid, i, last);
				currentRow.remove(last);
			}
		}
		// This method might be called when there is no change... careful!
		
		// Parameter values are validated by display, but you
		// should double check the numbers of rows/columns is
		// at least 1 here. Ignore the resize request if an
		// invalid value is given (don't crash the program...).
		
		// Add/remove all of _one_ column or row at a time until
		//    the correct size is achieved. For example, if you
		//    need to add 6 columns, DO NOT add six elements to
		//    row 1, then 6 elements to row 2. Instead, add one
		//    element to each row to complete the column, then
		//    start on the next column.
		// Multiple things might change! (Happens when dragging
		//    the corners of the window...). In that case,
		//    add/remove all rows before any columns
		
		// Rows are added to (and removed from) the top, while
		// columns are added to (and removed from) on the right.
		
		// Added space is just filled with empty void.
		
		// Removed rows are just removed.
		
		// Removed columns work as follows:
		//    If the removed element is as heavy (or heavier)
		//        than the element to the left, it will try
		//        to push that element to the left. If it
		//        can push it, then it takes its place.
		//        Remember: the push() method will move the
		//        element if possible, don't reinvent the wheel!
		//    If the removed element is lighter or can't
		//        be pushed, the removed element is just lost.
		
		// It would be very, very wise to make yourself some private
		// helper methods to do this part of the project.
		return true;
	}
	
	/**
	 *  Indicates the private post where you have shown off your
	 *  new element(s).
	 *  
	 *  @return the post where you describe your new element
	 */
	public static String newElementPost() {
		//[GUI:Advanced] change this to return the FULL URL of
		//the post where the grader can find your new element
		//demo, but ONLY if you completed the [GUI:Advanced] part
		//of the project.
		return null;
	}
	
	//******************************************************
	//*******  DO NOT EDIT ANYTHING BELOW THIS LINE  *******
	//*******        But do read this section!       *******
	//******************************************************

	/**
	 *  Causes one random particle to maybe do something. Called
	 *  repeatedly.
	 */
	public void step() {
		int row = (int)(Math.random()*grid.size());
		int col = (int)(Math.random()*grid.get(row).size()) ;
		
		grid.get(row).get(col).fall(grid, row, col);
	}
	
	/**
	 *  Game loop of the program (step, redraw, handlers, etc.).
	 */
	public void run() {
		while (true) {
			//step
			for (int i = 0; i < display.getSpeed(); i++) step();
			
			//redraw everything
			updateDisplay();
			display.repaint();
			
			//wait for redrawing and for mouse
			display.pause(1);
			
			//handle person actions (resize and tool usage)
			if(display.handle(this) && display.pauseMode()) {
				//for debugging
				updateDisplay();
				display.repaint();
				display.pause(5000);
			}
		}
	}
	
	/**
	 *  Convenience method for GTA testing. Do not use this in
	 *  your code... for testing purposes only.
	 *  
	 *  @return the private grid element
	 */
	public DynamicArray<DynamicArray<Element>> getGrid() {
		return grid;
	}
	
	/**
	 *  Main method that kicks off the simulator.
	 *  
	 *  @param args not used
	 */
	public static void main(String[] args) {
		(new Simulation(true)).run();
	}
}
