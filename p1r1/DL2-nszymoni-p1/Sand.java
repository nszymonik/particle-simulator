import java.awt.Color;

/**
 * Defines a grain of sand's behavior in the simulation.
 * @author Nicholas Szymonik
 *
 */
public class Sand extends Element {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Color getColor() {
		return Color.YELLOW;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int getWeight() {
		return 30;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void fall(DynamicArray<DynamicArray<Element>> grid, int row, int col) {
		if (row == grid.size()-1) { 
			return;
		}
		//fall straight down if possible.
		Element below = grid.get(row+1).get(col);
		if (this.compareTo(below) > 0) {
			swap(grid, row, col, row+1, col);
		}
		//fall diagonally down
		else 
		{
			//Try to get diagonal elements
			Element belowLeft = (col > 0 ? grid.get(row+1).get(col-1) : null);
			Element belowRight = (col < grid.get(row).size()-1 ? grid.get(row+1).get(col+1) : null);
			//Booleans for if the item could fall left or right
			boolean canLeft = (belowLeft != null && this.compareTo(belowLeft) > 0);
			boolean canRight = (belowRight != null && this.compareTo(belowRight) > 0);
			//both options are available
			if (canLeft && canRight) {
				if(Math.random() <= 0.5) {
					swap(grid, row, col, row+1, col-1);
				}
				else {
					swap(grid, row, col, row+1, col+1);
				}
			}
			//Only one option is available, or none of them are
			else {
				if (canLeft) {
					swap(grid, row, col, row+1, col-1);
				}
				else if (canRight) {
					swap(grid, row, col, row+1, col+1);
				}
			}
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean push(DynamicArray<DynamicArray<Element>> grid, int row, int col) {
		return pushUp(grid, row, col) || pushLeft(grid, row, col) || true; //Will try to push up, or left; compresses if neither of these work
	}
	
	/**
	 * Swaps this element with one in another position.
	 * @param grid the grid to change
	 * @param row the row of this element
	 * @param col the col of this element
	 * @param newRow the col of the element to swap with
	 * @param newCol the col of the element to swap with
	 */
	private void swap(DynamicArray<DynamicArray<Element>> grid, int row, int col, int newRow, int newCol) {
		grid.get(row).set(col, grid.get(newRow).get(newCol));
		grid.get(newRow).set(newCol, this);
	}

}
