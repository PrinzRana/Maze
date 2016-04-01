
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;

/**
* <h1>Course Work 1.</h1>
* This system takes a file name as an argument, and it generates the solutions to the maze.
* The system is only able to take files which are described/represented by numbered nodes in a pairwise connection.
* 
* @author  Safeer Rana 
* 
*/


public class MazeRunner {						

    final private String StartNodes="0"; 	// Final methods cannot be overridden by subclasses
    final private String EndNodes="1";
    private static boolean echo;				
    MazeRunner draw;
    long startTime;
    long stopTime;
    private Map<String, LinkedHashSet<String>> map = new HashMap<String, LinkedHashSet<String>>();

    public void mazeReader() throws FileNotFoundException, IOException {	// Public members are visible to all other classes
    	
        String line = null;					    // This will reference one line at a time
        String fileName = ("test1.txt");		// Takes User input and reads the maze file.
        								// Available files "maze1.txt" , "maze2.txt" , "maze3.txt"
        System.out.println("File: " + fileName);		
        FileReader fileReader = new FileReader(fileName);	//FileReader, given the File to read from.
        BufferedReader bufferedReader		// Always wrap FileReader in BufferedReader.
                = new BufferedReader(fileReader);
        while ((line = bufferedReader.readLine()) != null) {
            String[] locations = line.split(" ");
            draw.Array(locations[0], locations[1]);
        }
        bufferedReader.close();		// Always close files.
    }
    
    public void addVertexNode(String node1, String node2) {

        LinkedHashSet<String> adding = map.get(node1);

        if (adding == null) {
            adding = new LinkedHashSet<String>(); 
            map.put(node1, adding);
        }
        adding.add(node2);
    }

    public void Array(String node1, String node2) {
        addVertexNode(node1, node2);
        addVertexNode(node2, node1);
    }

    public boolean connect(String node1, String node2) {

        Set<?> adding = map.get(node1);  // ? is used to evaluate Boolean expressions
        if (adding == null) {
            return false;
        }
        return adding.contains(node2);
    }

    public LinkedList<String> addingNodes(String last) {

        LinkedHashSet<String> adding = map.get(last);
        if (adding == null) {
            return new LinkedList<String>();
        }
        return new LinkedList<String>(adding);
    }


    private void breadthFirstSearch(MazeRunner draw, LinkedList<String> PlacesBeen) {
    	
        LinkedList<String> nodes = draw.addingNodes(PlacesBeen.getLast());
        for (String node : nodes) {
        // the For loop combines three essentials initialisation statement, Boolean expression, increment statement
            if (PlacesBeen.contains(node)) {
                continue;
            }
            if (node.equals(EndNodes)) {
                PlacesBeen.add(node);
                printPath(PlacesBeen);
                echo = true;
                PlacesBeen.removeLast();
                break;
            }
        }

        for (String node : nodes) {
            if (PlacesBeen.contains(node) || node.equals(EndNodes)) {
                continue;
            }
            PlacesBeen.addLast(node);
            breadthFirstSearch(draw, PlacesBeen);
            PlacesBeen.removeLast();
        }

    }

    private void printPath(LinkedList<String> PlacesBeen) {

        for (String node : PlacesBeen) {
            System.out.print(node);
            System.out.print(" ");
        }
        System.out.println();
        
    }

    	// Main method
    public static void main(String[] args) throws FileNotFoundException, IOException {
    	//Auto-generated method stub
    	MazeRunner ai = new MazeRunner();

        ai.draw = new MazeRunner();
        ai.mazeReader();
        ai.startTime= System.nanoTime();  // Starts the Timer
        LinkedList<String> PlacesBeen = new LinkedList<String>();
        PlacesBeen.add(ai.StartNodes);
        new MazeRunner().breadthFirstSearch(ai.draw, PlacesBeen);
        ai.stopTime=System.nanoTime();	// Stops the Timer
        if (!echo) { 	// If echo is false print No Path Found 
        	// ! checks if the its equal or not, if it is the condition becomes true.
            System.out.println("NO path Found " + ai.StartNodes + " and " + ai.EndNodes);
        } else{			// otherwise print Path Found
        	 System.out.println("Path Found Between " + ai.StartNodes + " and " + ai.EndNodes);
        }
        System.out.println("It took "+(ai.stopTime - ai.startTime) + " NanoSeconds to solve the Maze." );	// Displays the String
    }  
}