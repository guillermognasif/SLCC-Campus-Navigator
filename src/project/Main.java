package project;


/**
 * Main part of the final project, designed to help navigate the
 * SLCC Taylorsville Campus based on time and distance.
 * This projects uses StdDraw from Algs4 as a GUI to show a map to 
 * navigate the campus based on where you are and where you want to go.
 * 
 * @author Cameron Dickinson + Guillermo Nasif
 */
public class Main {
	private static MapConstructor map;
	private static Student user;
	
	public static void main(String[] args) {

        // Load maps
        map = new MapConstructor("TaylorsvilleCampusMapDistance.txt",
                                 "TaylorsvilleCampusMapTime.txt");

        // GUI Start Screen
        CampusMapGUI.init();
        CampusMapGUI.drawMap(map.getCampusMapDistance()); //draws map
        CampusMapGUI.showMessage("Click your starting building"); //display msg
        String start = CampusMapGUI.waitForBuildingClick();
        user = new Student(start);

        for (;;) { // infinite loop

            // Ask user shortest or fastest
        	
            CampusMapGUI.drawMap(map.getCampusMapDistance());
            CampusMapGUI.showMessage("Choose shortest or fastest");

            // Draw buttons
            CampusMapGUI.drawButton(30, 10, 12, 5, "Shortest");
            CampusMapGUI.drawButton(70, 10, 12, 5, "Fastest");

            boolean shortest = false;
            boolean fastest = false;

            // Wait for user click
            while (!shortest && !fastest) {
                if (CampusMapGUI.buttonClicked(30, 10, 12, 5)) //shortest button clicked
                    shortest = true;

                if (CampusMapGUI.buttonClicked(70, 10, 12, 5)) //fastest button clicked
                    fastest = true;
            }
            

            // Ask destination
            CampusMapGUI.drawMap(map.getCampusMapDistance());
            CampusMapGUI.showMessage("Click your destination");
            String dest = CampusMapGUI.waitForBuildingClick();

            // Based on user selection, draw path
            if (shortest) {
                new FindShortestPath(dest, map, user).find();  
            } else {
                new FindFastestPath(dest, map, user).find();
            }
        }
		
	}

}
