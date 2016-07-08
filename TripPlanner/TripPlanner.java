/**
 * @author Ivan Meyerovich
 */

public class TripPlanner 
{
	public static void main(String[] args)
	{
		String mapIn = args[0];
		String cityStart = args[1];
		
		FileReader Input = new FileReader(mapIn);
		Permute Planner = new Permute(Input, cityStart);
		
		System.out.print(Planner.toString());
	}
}
