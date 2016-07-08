/**
 * @author Ivan Meyerovich
 */
import java.lang.StringBuilder;

public class Permute 
{
	private int initialCity; //initial city represented numerically 
	private int[] numCities; //array of cities represented numerically
	private int[] numSO; //special order cities represented numerically
	
	private int sDist; //shortest distance
	private int[] sPath; //shortest path
	
	private FileReader Input = null;
	
	/**
	 * Constructor for Permute class
	 * @param Input; Input file from FileReader
	 * @param inCity; Initial City called from Main
	 */
	public Permute(FileReader Input, String inCity)
	{
		this.Input = Input;
		
		initialCity = InitialNum(inCity);
		numCities = NumArrayBuilder(Input.citiesArray);
		numSO = NumSOBuilder(Input.specOrder);
		
		sPath = new int[Input.numCities+1];
		sDist = -1;
		
		int[] currCities = new int[numCities.length-1];
		for(int i = 0; i<currCities.length; i++)
		{
			if(i<initialCity)
				currCities[i] = i;
			else
				currCities[i] = i+1;
		}
		Permutator(currCities, 0);
	}
	
	/**
	 * Recursive permutation of the cities, not including the starting city
	 * @param currPerm - the current permutation being edited
	 * @param sIndex - current position being edited
	 */
	private void Permutator(int[] currPerm, int sIndex)
	{
		if(sIndex == currPerm.length)
		{
			ShortestPathMonitor(currPerm);
		}
		else
		{
			for (int i = sIndex; i < currPerm.length; i++) 
            {
                int temp = currPerm[sIndex];
                currPerm[sIndex] = currPerm[i];
                currPerm[i] = temp;
 
                Permutator(currPerm, sIndex + 1);
 
                temp = currPerm[sIndex];
                currPerm[sIndex] = currPerm[i];
                currPerm[i] = temp;
            }
		}
	}
	
	
	/**
	 * Adds the initial city back into the beginning and end of the array and calculates
	 * 	the distance. If it is less than the current shortest, changes the shortest distance
	 * 	value and the shortest path array.
	 * @param currPerm - current permutation being calculated
	 */
	private void ShortestPathMonitor(int[] currPerm)
	{
		int[] path = new int[Input.numCities+1];
		System.arraycopy(currPerm, 0, path, 1, currPerm.length);
		path[0] = initialCity;
		path[path.length-1] = initialCity;
		
		boolean test = PathCheck(path);
		
		if(test == true)
		{
			int dist = 0;
			
			for(int i = 0; i<Input.numCities; i++)
			{
				dist=dist+Input.adjMatrix[path[i]][path[i+1]];
			}
			
			if(dist<sDist || sDist==-1)
			{
				sDist = dist;
				sPath = path.clone();
			}
		}
	}
	
	/**
	 * Tests path to ensure it is viable. 
	 * @param currPath - current permutation being verified
	 * @return - true if path is viable, false if it is not
	 */
	private boolean PathCheck(int[] currPath)
	{
		//Checks that paths between consecutive cities in the permutation are valid
		for(int i = 0; i<Input.numCities; i++)
		{
			if(Input.adjMatrix[currPath[i]][currPath[i+1]] == -1)
			{
				return false;
			}
		}
		
		//Checks that the special order rules are followed
		if(!Input.specOrder[0].equals("none") || !Input.specOrder[0].equals("None"))
		{
			boolean ordercheck = false;
			for(int i = 0; i<Input.numCities; i++)
			{
				if(currPath[i] == numSO[0])
				{
					ordercheck = true;
				}
				if(currPath[i] == numSO[1] && ordercheck == false)
				{
					return false;
				}
			}
		}
		
		return true;
	}
	
	/**
	 * Sets the Cities array to a numeric array
	 * @param inArr - citiesArray
	 * @return numeric index array of citiesArray
	 */
	private int[] NumArrayBuilder(String[] inArr)
	{
		int[] outArr = new int[inArr.length];
		
		for(int i = 0; i < outArr.length; i++)
		{
			outArr[i] = i;
		}
		
		return outArr;
	}
	
	/**
	 * Goes through citiesArray and matches the values of specOrder array
	 * @param inArr - specOrder
	 * @return the index numbers of the two special order cities as an array
	 */
	private int[] NumSOBuilder(String[] inArr)
	{
		int[] SOnum = new int[inArr.length];
		
		for(int i = 0; i < Input.numCities; i++)
		{
			if(Input.citiesArray[i].equals(inArr[0]))
			{
				SOnum[0] = i;
			}
			if(Input.citiesArray[i].equals(inArr[1]))
			{
				SOnum[1] = i;
			}
		}
		
		return SOnum;
	}
	
	/**
	 * Goes through citiesArray and matches the value to inCity
	 * @param inCity - starting city
	 * @return numeric index value for the starting city
	 */
	private int InitialNum(String inCity)
	{
		for(int i = 0; i < Input.numCities; i++)
			if(Input.citiesArray[i].equals(inCity))
				return i;
		return -1;
	}
	
	/**
	 * Returns the result of Permute as a string
	 */
	public String toString()
	{
		StringBuilder planBuilder = new StringBuilder();
		
		//If no path found or the initial city violates the special order
		if(sDist==-1)
		{
			String noPath = "There are no possible paths from "+Input.citiesArray[initialCity]+".\n";
			
			try
			{
				if(initialCity == numSO[1])
				{
					noPath = "Starting in "+Input.citiesArray[numSO[1]]+" violates the special order.";
				}
			}
			catch(IndexOutOfBoundsException e)
			{
				return noPath;
			}
			return noPath;
		}
		//If a path is found
		else
		{
			planBuilder.append("The shortest path from "+
					Input.citiesArray[initialCity]+" is:\n");
			for(int i = 0; i<sPath.length; i++)
			{
				if(i == sPath.length-1)
				{
					planBuilder.append(Input.citiesArray[sPath[i]]+" "+
							"Total Distance: "+sDist+" miles.");
				}
				else
				{
					planBuilder.append(Input.citiesArray[sPath[i]]+" -"+
							Input.adjMatrix[sPath[i]][sPath[i+1]]+"mi-> ");
				}
			}
		}
		
		return planBuilder.toString();
	}
}
