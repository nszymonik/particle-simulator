import java.awt.Color;

/**
 * Describes a liquid's behavior in the simulation.
 * @author Nicholas Szymonik
 *
 */
public class Water extends Element {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Color getColor() {
		return new Color(0, 128, 255);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public int getWeight() {
		return 15;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void fall(DynamicArray<DynamicArray<Element>> grid, int row, int col) {
		double dir = Math.random() * 3.0;
		if (dir < 1.0) { // Left
			if(col > 0) {
				Element left = grid.get(row).get(col-1);
				
				//If that element is lighter than this element
				//try to push it, and if successful, move this element
				//into that vacated space (swap with the element that
				//is now there).
				if(this.compareTo(left) > 0) {
					grid.get(row).set(col, grid.get(row).get(col-1));
					grid.get(row).set(col-1, this);
				}
					
			}
		} else if (dir < 2.0) { //Down
			if (row < grid.size() - 1) {
				Element down = grid.get(row+1).get(col);
				if (this.compareTo(down) > 0) {
					swap(grid, row, col, row+1, col);
				}
			}
				
		} else { //Right
			if (col < grid.get(0).size() - 1) {
				Element right = grid.get(row).get(col+1);
				if (this.compareTo(right) > 0) {
					swap(grid, row, col, row, col+1);
				}
			}
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean push(DynamicArray<DynamicArray<Element>> grid, int row, int col) {
		if (Math.random() < 0.5) {// try up, then try left
			return pushUp(grid, row, col) || pushLeft(grid, row, col);
		}
		else { //try left, then try up
			return pushLeft(grid, row, col) || pushUp(grid, row, col);
		}
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
