package project;

/**
 * The student class stores the location of the user
 * 
 * @author Cameron Dickinson
 */
public class Student {
	private String location = "";
	
	/**
	 * Constructs new student class with given location.
	 * 
	 * @param location
	 */
	public Student(String location) {
		this.location = location;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}
}
