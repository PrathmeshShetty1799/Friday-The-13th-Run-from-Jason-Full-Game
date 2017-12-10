import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;
/**
 *  @author user Prathmesh Shetty(717937)
 *  The Item class is a object class with unique fields that are used in the game.  The items are spawned at random across the map
 */
public class Item {

	/**
	 * Stats
	 *  - Size: 50 by 50
	 */
	//Fields
	private float locX,locY;
	private Sprite itemImage;
	private Rectangle boundary;
	private int itemNumber;

	/**Item Number List
	 * 1 = Keys
	 * 2 = Batteries
	 * 3 = Vehicle
	 * 4 = Shotgun(Optional)
	*/	
	/**
	 * Initializes the item attributes 
	 * @param x
	 * @param y
	 * @param imagePath
	 * @param number
	 */
	Item(int x, int y, String imagePath, int number)
	{
		locX = x;
		locY = y;		
		
		itemNumber = number;
		
		itemImage = new Sprite( new Texture(Gdx.files.internal(imagePath)));
		itemImage.setPosition(x,y);	
		
		boundary = new Rectangle (locX,locY, 50,100);	
	}	
	
	 
	Item(String imagePath, int number)
	{	
		//Temporary Locations(Changes with Item Spawner)
		locX = Gdx.graphics.getWidth()/3;
		locY = Gdx.graphics.getHeight()/2;	
		
		itemNumber = number;
		
		itemImage = new Sprite( new Texture(Gdx.files.internal(imagePath)));
		itemImage.setPosition(locX,locY);	
		
		boundary = new Rectangle (locX,locY, 50,50);	
	}	
	
	//List of Info Methods(Getters and Setters)	
	public int getItemNumber()
	{
		return itemNumber;
	}		
	public float getX()
	{
		return locX;
	}	
	public float getY()
	{
		return locY;
	}	
	public void setX(float x)
	{
		locX = x;
	}	
	public void setY(float y)
	{
		locY = y;
	}	
	public Rectangle getRectangle()
	{
		return boundary;
	}	
	public void setRectangle(float x, float y)
	{
		boundary = new Rectangle(x,y,30,30);
	}
	public Sprite getSprite()
	{
		return itemImage;
	}	
}
