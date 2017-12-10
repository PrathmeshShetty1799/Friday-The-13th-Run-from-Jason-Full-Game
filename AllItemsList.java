import java.util.ArrayList;
import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Rectangle;
/** 
 * @author Prathmesh Shetty (717937)
 * The allItems list is a portable class that contains an arrayList of all the available items within the game. It can used in multiple classes
 *  - With Counselor: It can help Counselor to pick up this items and add them to his inventory 
 *  - With FridayThe13: It can help with itemSpawn to spawn items across the map
 *  
 * Item Number List
 * 1 = Keys
 * 2 = Batteries
 * 3 = Vehicle
 * 4 = Gas Can
 */	
public class AllItemsList {

	//Fields
	public ArrayList<Item> allItems;
	//Declaration of the All the Items within the Game
	private Item carKeys;
	private Item battery1;
	private Item battery2;
	private Item battery3;
	private Item battery4;
	private Item vehicle;
	private Item gasCan;

	//For the Vehicle
	Random coordGen;		

	//Constructor
	AllItemsList()
	{
		allItems = new ArrayList<Item>();	
		coordGen = new Random();
	}

	/**
	 * addAllItems helps create a master list of items that is used by many aspects of the game as stated above
	 * **/
	public void addAllItems ()
	{		
		//Adding All Items to List
		carKeys = new Item("F13-Assets/Objects/CarKey.png", 1);
		battery1 = new Item("F13-Assets/Objects/Battery.png", 2);
		battery2 = new Item("F13-Assets/Objects/Battery.png", 2);
		battery3 = new Item("F13-Assets/Objects/Battery.png", 2);
		battery4 = new Item("F13-Assets/Objects/Battery.png", 2);		
		gasCan = new Item("F13-Assets/Objects/GasCan.png", 4);
		vehicle = new Item(Gdx.graphics.getWidth()/2+38 ,coordGen.nextInt(700)+128,"F13-Assets/Objects/Vehicle.png",3);

		allItems.add(carKeys);
		allItems.add(battery1);
		allItems.add(battery2);
		allItems.add(battery3);		
		allItems.add(battery4);	
		allItems.add(vehicle);
		allItems.add(gasCan);
	}

	//List of Info Methods(Getters and Setters)
	public Item getItem(int indexNumber)
	{
		return allItems.get(indexNumber);
	}

	public Rectangle getRectangle(int indexNumber)
	{
		return allItems.get(indexNumber).getRectangle();
	}

	public int getSize()
	{
		return allItems.size();
	}

	public void removeItem(int indexNumber)
	{
		allItems.remove(indexNumber);
	}
	/**
	 * The setBoundaries method allows to set the boundaries around the items for item spawn
	 */
	public void setBoundaries()
	{
		for(int i = 0; i < allItems.size(); i++)
		{
			if(allItems.get(i).getItemNumber()!=3)
				allItems.get(i).setRectangle(allItems.get(i).getX(), allItems.get(i).getY());
		}
	}
	/**				
	 * The getVehicleRectangle method returns the rectangle around the vehicle
	 * @return The rectangle around the vehicle on the map
	 */
	public Rectangle getVehicleRectangle()
	{
		Rectangle rectangle = new Rectangle(vehicle.getX(),vehicle.getY(),50,100);
		return rectangle;
	}	
}
