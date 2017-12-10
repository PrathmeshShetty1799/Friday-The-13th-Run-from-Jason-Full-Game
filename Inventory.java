import java.util.ArrayList;

/**
 * @author user Prathmesh Shetty(717937)
 * The Inventory class allows the Counselor to pick items and store them to either escape or use at a later time
 */
public class Inventory {
	
	//Fields
	private ArrayList<Item> items = new ArrayList<Item>();
	//Constructor
	Inventory()
	{
		items = new ArrayList<Item>();
	}	
	/**
	 * The howManyBatteries method checks and returns the amount of batteries in the player Inventory
	 * @return The amount of batteries in the inventory
	 */
	public int howManyBatteries()
	{	
		int count = 0;
		for(int i = 0; i < items.size(); i++)
		{
			if(items.get(i).getItemNumber()==2)
				count++;
		}
		return count;
	}
	/**
	 * The addItem method adds an item to the inventory
	 * @param item The item to be added
	 */
	public void addItem(Item item)
	{
		items.add(item);
	}
	//List of Info Methods(Getters and Setters)
	public int getInventorySize()
	{
		return items.size();
	}
	public Item getItem(int indexNumber)
	{
		return items.get(indexNumber);
	}
	public int getItemNumber(int indexNumber)
	{
		return items.get(indexNumber).getItemNumber();
	}
	
}
