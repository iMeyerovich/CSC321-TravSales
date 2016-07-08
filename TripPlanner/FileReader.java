/**
 * @author Ivan Meyerovich
 */

/**
 * @author Ivan Meyerovich
 */
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class FileReader 
{
	private String DataFileName = null; //Name of Data File
	
	String[] citiesArray; //Cities to visit
	String[] specOrder; //Two cities to visit in a specific order
	int[][] adjMatrix; //The Adjacency Matrix
	int numCities = 0;
	
	public FileReader(String MapIn)
	{
		 DataFileName = MapIn;
		 readKeys();
	}
	
	private void readKeys()
	{
		File file = new File(DataFileName);

	    try 
	    {
	    	int xIndex = 0;
	    	int yIndex = 0;
	        Scanner citiesFile = new Scanner(file);
	        
	        String cityCheck = citiesFile.nextLine(); //Finds number of cities being compared
	        citiesArray = cityCheck.split("\\s+");
	        numCities = citiesArray.length;
	        adjMatrix = new int[numCities][numCities];
	        
	        String orderCheck = citiesFile.nextLine(); //Sets the special ordered cities
	        specOrder = orderCheck.split("\\s+");

	        while (citiesFile.hasNext()) 
	        {
	        	int num = tryParse(citiesFile.next()); //parse Adjacency Matrix to remove "inf"
	        		
	        	adjMatrix[yIndex][xIndex] = num;
	        	if(xIndex == numCities-1)
	        	{
	        		xIndex = 0;
	        		yIndex++;
	        	}
	        	else
	        	{
	        		xIndex++;
	        	}
	        }
	        citiesFile.close();
	    } 
	    catch (FileNotFoundException e) 
	    {
	        e.printStackTrace();
	    }
	}
	
	private static Integer tryParse(String alpha) //Sets "inf" values in array to int values
	{
		try 
		{
			return Integer.parseInt(alpha);
		} 
		catch (NumberFormatException e) 
		{
			return -1; //All "inf" set to -1 for ease of locating
		}
	}
}
