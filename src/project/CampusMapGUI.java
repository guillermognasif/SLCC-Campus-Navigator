package project;

import java.awt.Color;
import java.util.HashMap;
import java.util.Map;

import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.Edge;
import edgeWeightedSymbolGraph.EdgeWeightedSymbolGraph;
/**
 * Class that helps setting up the GUI using StdDraw from Algs4
 * @author Guillermo Nasif
 */
public class CampusMapGUI {

    // Building coordinates
    private static final Map<String, Double> x = new HashMap<>();
    private static final Map<String, Double> y = new HashMap<>();

    static { // coordinates of each building
        x.put("LAC", 30.0);  y.put("LAC", 75.0);
        x.put("LIB", 40.0);  y.put("LIB", 75.0);
        x.put("SI",  32.0);  y.put("SI", 55.0);
        x.put("STC", 50.0);  y.put("STC", 55.0);
        x.put("GMBB",58.0); y.put("GMBB",78.0);
        x.put("TB",  70.0);  y.put("TB", 60.0);
        x.put("CT",  70.0);  y.put("CT", 35.0);
        x.put("TAB", 50.0); y.put("TAB", 35.0);
        x.put("AAB", 42.0); y.put("AAB", 35.0);
        x.put("LDS", 30.0); y.put("LDS", 40.0);
    }

    // colors for the map
    private static final Color BG = new Color(245, 245, 245);       // light background
    private static final Color ROAD = new Color(180, 180, 180);     // soft gray edges
    private static final Color NODE_BLUE = new Color(30, 136, 229); // light blue
    private static final Color LABEL = new Color(50, 50, 50);       // dark gray
    private static final Color INFO_BG = new Color(255, 255, 255);  // white bar
    private static final Color BUTTON_BG = new Color(230, 230, 230);
    private static final Color BUTTON_BORDER = new Color(120, 120, 120);

    public static void init() { // setting the window
        StdDraw.setCanvasSize(600, 600);
        StdDraw.setXscale(0, 100);
        StdDraw.setYscale(0, 110);
        StdDraw.clear(BG); 
        StdDraw.enableDoubleBuffering();
    }

    /**
     * Method in charge of drawing the map in a simple but elegant way.
     * @param sg weighted graph
     */
    public static void drawMap(EdgeWeightedSymbolGraph sg) {
        init();

        // Edges (roads)
        StdDraw.setPenColor(ROAD);
        StdDraw.setPenRadius(0.004);
        for (Edge e : sg.graph().edges()) {
            String a = sg.nameOf(e.either());
            String b = sg.nameOf(e.other(e.either()));
            StdDraw.line(x.get(a), y.get(a), x.get(b), y.get(b)); //line between two vertices
        }

        // Vertices (buildings)
        for (String b : x.keySet()) {
            StdDraw.setPenColor(Color.WHITE); // white outline
            StdDraw.filledCircle(x.get(b), y.get(b), 2.6);

            StdDraw.setPenColor(NODE_BLUE);// blue center
            StdDraw.filledCircle(x.get(b), y.get(b), 2);

            StdDraw.setPenColor(LABEL); // label
            StdDraw.text(x.get(b), y.get(b) + 3.5, b);
        }

        StdDraw.show();
    }

    /**
     * Method in charge of drawing the Red line between the vertices(edges)
     * @param path
     * @param sg weighted graph
     */
    public static void highlightPath(Iterable<Edge> path, EdgeWeightedSymbolGraph sg) {

        StdDraw.setPenColor(new Color(220, 0, 0)); // red
        StdDraw.setPenRadius(0.012);

        for (Edge e : path) {
            String a = sg.nameOf(e.either());
            String b = sg.nameOf(e.other(e.either()));
            StdDraw.line(x.get(a), y.get(a), x.get(b), y.get(b));
        }

        StdDraw.show();
    }

    /**
     * Method for Showing the top messages
     * @param msg
     */
    public static void showMessage(String msg) {
        StdDraw.setPenColor(INFO_BG);
        StdDraw.filledRectangle(50, 105, 50, 5);

        StdDraw.setPenColor(LABEL);
        StdDraw.text(50, 105, msg);

        StdDraw.show();
    }

    /**
     * Method for showing the information below the top message
     * @param msg
     */
    public static void showInfo(String msg) {

        StdDraw.setPenColor(INFO_BG);
        StdDraw.filledRectangle(50, 95, 50, 4);

        StdDraw.setPenColor(LABEL);
        StdDraw.text(50, 95, msg);

        StdDraw.show();
    }

    /**
     * Method for the bottom Buttons
     * @param cx  x coordinate
     * @param cy  y coordinate
     * @param w  width
     * @param h  height
     * @param text containing text of the button
     */
    public static void drawButton(double cx, double cy, double w, double h, String text) {
        StdDraw.setPenColor(BUTTON_BG);
        StdDraw.filledRectangle(cx, cy, w, h);

        StdDraw.setPenColor(BUTTON_BORDER);
        StdDraw.rectangle(cx, cy, w, h);

        StdDraw.setPenColor(LABEL);
        StdDraw.text(cx, cy, text);

        StdDraw.show();
    }

    /**
     * Method that checks if a button was clicked
     * @param cx x coordinate of button
     * @param cy y coordinate of button
     * @param w width of button
     * @param h height of button
     * @return true if button was clicked, false if not
     */
    public static boolean buttonClicked(double cx, double cy, double w, double h) {
        if (!StdDraw.isMousePressed()) return false;

        double mx = StdDraw.mouseX();
        double my = StdDraw.mouseY();

        boolean inside = (mx >= cx - w && mx <= cx + w &&
                          my >= cy - h && my <= cy + h); //checking that the mouse was clicked where the buttons are

        if (inside) { // if clicked
            StdDraw.pause(200);
            return true;
        }
        return false;
    }

    /**
     * This method waits for the user to click on a building, until the user does, then the method will keep looping
     * @return the building's name
     */
    public static String waitForBuildingClick() {
        while (true) { 
            if (StdDraw.isMousePressed()) {
                double mx = StdDraw.mouseX();//keeping track of users click
                double my = StdDraw.mouseY();

                for (String b : x.keySet()) { // loop through the building's names until the one clicked by the user matches
                    if (Math.hypot(mx - x.get(b), my - y.get(b)) <= 3) {
                        StdDraw.pause(200);
                        return b;
                    }
                }
            }
        }
    }
}
