// A class for the GUI to represent a space


public class Space {
	
	int x_location;
	int y_location;
	
	Space(int x, int y) {
		x_location=x;
		y_location=y;
	}
	
	// Accessor Methods
	
	int get_x(){
		return x_location;
	}
	
	int get_y() {
		return y_location;
	}
}
