import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;
/**
 * @author Prathmesh Shetty(717937)
 * The Counselor is the survivor controlled by User
 * Objective: The Counselor needs to find a pair of car keys and a gas can while running from Jason(Enemy)
 * 
 * Features: 
 *  - Item PickUp System
 *  - Inventory System
 *  - Fear System
 *  - Movement and Sprint System
 *  
 *  Stats:
 *  - Size: 50 by 50
 **/
public class Counselor {

	//Character Stats
	private float locX, locY;
	private float lastLocX,lastLocY;
	private Sprite counselorImage;

	private Sprite counselorLeft;
	private Sprite counselorRight;
	private Sprite counselorUp;
	private Sprite counselorDown;

	private Rectangle boundary; 
	private double sprintAmount,fearAmount;

	//Character Abilities
	private Inventory playerInventory;
	private AllItemsList itemList;

	private float velocityX, velocityY;
	private float brightRadius;
	private double distance;

	//Movement Limit
	private boolean playerNearObstacle;
	private double sprintMax;
	private float WALK_UP, WALK_DOWN,WALK_LEFT,WALK_RIGHT;	
	private float SPRINT_UP,SPRINT_DOWN,SPRINT_LEFT,SPRINT_RIGHT;

	Counselor(int whichCharacter)
	{	
		//Temporary Coordinates - Changes with Spawn Points
		locX = Gdx.graphics.getWidth()/3;
		locY = Gdx.graphics.getHeight()/2;			

		lastLocX = locX;
		lastLocY = locY;

		if(whichCharacter ==1)
		{
			counselorImage = new Sprite(new Texture(Gdx.files.internal("F13-Assets/Characters/Tommy/left1.png")));
			counselorImage.setPosition(locX,locY);

			//The Current Image will change depending on the Movement
			counselorLeft = new Sprite(new Texture(Gdx.files.internal("F13-Assets/Characters/Tommy/left1.png")));
			counselorLeft.setPosition(locX,locY);
			counselorRight = new Sprite(new Texture(Gdx.files.internal("F13-Assets/Characters/Tommy/right1.png")));
			counselorRight.setPosition(locX,locY);
			counselorUp = new Sprite(new Texture(Gdx.files.internal("F13-Assets/Characters/Tommy/top1.png")));
			counselorUp.setPosition(locX,locY);
			counselorDown = new Sprite(new Texture(Gdx.files.internal("F13-Assets/Characters/Tommy/down1.png")));
			counselorDown.setPosition(locX,locY);
		}
		else //If Character Chosen is Vanessa
		{
			counselorImage = new Sprite(new Texture(Gdx.files.internal("F13-Assets/Characters/Vanessa/left1.png")));
			counselorImage.setPosition(locX,locY);

			//The Current Image will change depending on the Movement
			counselorLeft = new Sprite(new Texture(Gdx.files.internal("F13-Assets/Characters/Vanessa/left1.png")));
			counselorLeft.setPosition(locX,locY);
			counselorRight = new Sprite(new Texture(Gdx.files.internal("F13-Assets/Characters/Vanessa/right1.png")));
			counselorRight.setPosition(locX,locY);
			counselorUp = new Sprite(new Texture(Gdx.files.internal("F13-Assets/Characters/Vanessa/top1.png")));
			counselorUp.setPosition(locX,locY);
			counselorDown = new Sprite(new Texture(Gdx.files.internal("F13-Assets/Characters/Vanessa/down1.png")));
			counselorDown.setPosition(locX,locY);
		}
		boundary = new Rectangle(locX,locY,50,50);

		playerInventory = new Inventory();
		itemList = new AllItemsList();	
		itemList.addAllItems();

		playerNearObstacle = false;
		fearAmount = 301;

		//Character Attributes
		if(whichCharacter ==1)
		{
			sprintAmount = 150;
			sprintMax = 150;
			brightRadius = 25;			
		}
		else
		{
			sprintAmount = 100;
			sprintMax = 100;
			brightRadius = 750;
		}

		//Movement Helpers
		WALK_UP = 2;
		WALK_DOWN = -2;
		WALK_LEFT = -2;
		WALK_RIGHT = 2;

		SPRINT_UP = 5;
		SPRINT_DOWN = -5;
		SPRINT_LEFT = -5;
		SPRINT_RIGHT = 5;
	}
	/**
	 * Updates Info based on Player Input
	 * @throws InterruptedException 
	 */
	public void updateCounselor(float killerLocX, float killerLocY)
	{	
		//To Update the Fear Bar(Tells If Jason is Close)
		distance = Math.sqrt(Math.pow((locY- killerLocY), 2) + Math.pow((locX- killerLocX), 2)); 
		if(distance < 300)
			fearAmount = distance;
		else 
			fearAmount = 301;	

		//Every time, it resets to 0 to help with no movement
		velocityX = 0;
		velocityY = 0;		

		//Movement and Sprint System
		if(Gdx.input.isKeyPressed(Keys.RIGHT))
		{			
			counselorImage = counselorRight;
			if(Gdx.input.isKeyPressed(Keys.SHIFT_LEFT)&& sprintAmount > 0)
				velocityX = SPRINT_RIGHT;				
			else			
				velocityX = WALK_RIGHT;	
		}
		if(Gdx.input.isKeyPressed(Keys.LEFT))
		{			
			counselorImage = counselorLeft;
			if(Gdx.input.isKeyPressed(Keys.SHIFT_LEFT)&& sprintAmount > 0)
				velocityX = SPRINT_LEFT;				
			else			
				velocityX = WALK_LEFT;	
		}
		if(Gdx.input.isKeyPressed(Keys.UP))//Top Edge due to the HUD
		{
			counselorImage = counselorUp;
			if(locY > Gdx.graphics.getHeight())
				velocityY = 0;
			else if(Gdx.input.isKeyPressed(Keys.SHIFT_LEFT)&& sprintAmount > 0)
				velocityY = SPRINT_UP;				
			else			
				velocityY = WALK_UP;
		}
		if(Gdx.input.isKeyPressed(Keys.DOWN))
		{	
			counselorImage = counselorDown;
			if(locY < 128)//Bottom Edge due to the HUD
				velocityY = 0;			
			else if(Gdx.input.isKeyPressed(Keys.SHIFT_LEFT)&& sprintAmount > 0)
				velocityY = SPRINT_DOWN;				
			else			
				velocityY = WALK_DOWN;	
		}

		//Update Movement
		if(playerNearObstacle)
		{
			locX = lastLocX;
			locY = lastLocY;
			playerNearObstacle = false;
		}
		else
		{	
			lastLocX = locX;
			lastLocY = locY;

			locX += velocityX;
			locY += velocityY;			
		}

		//Updating Stamina Bar
		if((Math.abs(velocityX)== 5 || Math.abs(velocityY)== 5)&& sprintAmount > 0)
			sprintAmount--;
		else if(sprintAmount < sprintMax && !Gdx.input.isKeyPressed(Keys.SHIFT_LEFT))
		{
			if (Math.abs(velocityX) == 1 || Math.abs(velocityY) == 1)
				sprintAmount+=0.25;
			else if(velocityX == 0 && velocityY == 0)
				sprintAmount+=0.35;			
		}		

		//Boundary is Constantly Updated for Win Condition
		boundary = new Rectangle(locX,locY, 50,50);		
		if(Gdx.input.isKeyPressed(Keys.Z))
		{					
			if(itemUnderneath())
				pickUpItem();
		}		
	}
	/**
	 * hasKeys checks the inventory to check if the counselor has found the pair of keys
	 * This will help identify a win when the Counselor is near the escape vehicle
	 * @return If carKeys is in the player inventory 
	 */
	public boolean hasKeys()
	{
		for(int i = 0; i < playerInventory.getInventorySize(); i++)
		{
			if(playerInventory.getItem(i).getItemNumber() == 1)
				return true;
		}	
		return false;
	}

	/**
	 * hasGas checks the inventory to check if the counselor has found the gas can
	 * This will help identify a win when the Counselor is near the escape vehicle
	 * @return If gasCan is in the player inventory 
	 */
	public boolean hasGas()
	{
		for(int i = 0; i < playerInventory.getInventorySize(); i++)
		{
			if(playerInventory.getItem(i).getItemNumber() == 4)
				return true;
		}	
		return false;
	}
	/**
	 * itemUnderneath checks if there is an item underneath the counselor	
	 * @return If an item is underneath the counselor
	 * **/

	public boolean itemUnderneath()
	{
		for(int i = 0; i < itemList.getSize(); i++)
		{			
			if(boundary.overlaps(itemList.getRectangle(i)))
				return true;
		}
		return false;
	}
	/**
	 * pickUpItem adds the pick up item to its inventory	
	 **/
	public void pickUpItem()
	{
		for(int i = 0; i < itemList.getSize(); i++)
		{
			if(boundary.overlaps(itemList.getRectangle(i)))
			{
				//If the item is a pair of Car Keys
				if(itemList.getItem(i).getItemNumber() == 1 && !hasKeys())
				{
					playerInventory.addItem(itemList.getItem(i));
					itemList.removeItem(i);
					break;
				}
				//If the item is a battery
				else if(itemList.getItem(i).getItemNumber() == 2)
				{
					playerInventory.addItem(itemList.getItem(i));	
					brightRadius += 25;//Increses the radius of visibility
					itemList.removeItem(i);
					break;
				}	
				//If the item is a gas can
				else if(itemList.getItem(i).getItemNumber() == 4)
				{
					playerInventory.addItem(itemList.getItem(i));								
					itemList.removeItem(i);
					break;
				}
			}
		}
	}
	/**
	 * The playerWon checks for the win conditions to end the game	
	 **/
	public boolean playerWon()
	{	
		if(hasKeys()&& hasGas() && boundary.overlaps(itemList.getVehicleRectangle()))
			return true;
		else
			return false;
	}	
	/**
	 * The playerCaught checks if the counselor is caught by the enemy(Jason)
	 **/
	public boolean playerCaught(Rectangle enemyBoundary)
	{	
		if(boundary.overlaps(enemyBoundary))
			return true;
		else
			return false;
	}
	/**
	 * The jasonClose checks if the counselor is near the enemy(Jason)
	 **/
	public boolean jasonClose()
	{
		if(distance < 300)
			return true;
		return false;
	}
	//List of Info Methods(Getters and Setters)	
	/**
	 * The setItemsList updates the checks if the counselor is near the enemy(Jason)
	 **/
	public void setItemsList(AllItemsList items)
	{
		itemList = items;
	}
	public double getJasonDistance()
	{
		return distance;
	}
	public double getStamina()
	{
		return (sprintAmount/sprintMax) *100;
	}
	public double getFear()
	{
		return ((300 - fearAmount)/300)*100;
	}	
	public void setPlayerNearObstacle(boolean result)
	{
		playerNearObstacle = result;
	}
	public int howManyBatteries()
	{
		return playerInventory.howManyBatteries();
	}
	public AllItemsList getItemList()
	{
		return itemList;
	}
	public float getBrightRadius ()
	{
		return brightRadius;
	}
	public Rectangle getPlayerRectangle()
	{
		return boundary;
	}
	public Sprite getSprite()
	{
		return counselorImage;
	}	
	public float getX()
	{
		return locX;
	}
	public void setX(float x)
	{
		locX = x;
	}
	public float getY()
	{
		return locY;
	}
	public void setY(float y)
	{
		locY = y;
	}	
}