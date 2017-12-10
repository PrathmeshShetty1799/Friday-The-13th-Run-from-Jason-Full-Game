import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;
/**
 * @author Prathmesh Shetty(717937)
 * The Jason is the enemy that is computer controlled(Basic AI Established)
 * Objective: Jason has to capture the counselor before they escape the map;
 * 
 * Features: 
 *  - Attack System(When Counselor is in proximity)
 *  - Stealth System(When the Counselor is hidden from Jason
 *  - Movement System with Teleport - He is able to teleport/morph through obstacle if the Counselor is getting far away
 **/
public class Jason {

	//Character Stats
	private float locX, locY;
	private Sprite jasonImage;
	private Rectangle jasonBoundary;
	private boolean jasonOverObstacle;

	//Movement Values
	private float MORPH_UP, MORPH_DOWN,MORPH_LEFT,MORPH_RIGHT;	
	private float WALK_UP, WALK_DOWN,WALK_LEFT,WALK_RIGHT;	
	private float SPRINT_UP,SPRINT_DOWN,SPRINT_LEFT,SPRINT_RIGHT;

	/**
	 * Initializes the aspects of Jason
	 */
	Jason()
	{		
		locX = Gdx.graphics.getWidth()/4;
		locY = Gdx.graphics.getHeight()/2;	

		jasonImage = new Sprite(new Texture(Gdx.files.internal("F13-Assets/Characters/Jason(Delirious).png")));
		jasonImage.setPosition(locX,locY);	

		jasonBoundary = new Rectangle(locX,locY,50,50);		
		jasonOverObstacle = false;

		//Movement Values
		MORPH_UP = 1;
		MORPH_DOWN = -1;
		MORPH_LEFT = -1;
		MORPH_RIGHT = 1;		

		WALK_UP = 2;
		WALK_DOWN = -2;
		WALK_LEFT = -2;
		WALK_RIGHT = 2;

		SPRINT_UP = 3;
		SPRINT_DOWN = -3;
		SPRINT_LEFT = -3;
		SPRINT_RIGHT = 3;
	}

	/**
	 * The updates Jason updates Info and Allows Jason to either stalk or stealthily approach the counselor
	 */
	public void updateJason(float targetX, float targetY)
	{
		jasonBoundary = new Rectangle(locX,locY,50,50);
		//To Change the Tactic (Tells If Target is Close)
		double distance = Math.sqrt(Math.pow(Math.abs(locY- targetY), 2) + Math.pow(Math.abs(locX- targetX), 2)); 
		if(distance < 300)
			attack(targetX,targetY);
		else 
			stalk(targetX,targetY);
	}
	/**
	 * The attack Method allows Jason to target and follow to attack the Counselor when he/she is within proximity
	 * @param targetX - The x-coordinate of the Counselor
	 * @param targetY - The y-coordinate of the Counselor
	 */
	public void attack(float targetX, float targetY)
	{	
		//If he is overlapping an obstacle
		if(jasonOverObstacle)
		{				
			if(targetY < locY - 5)
				locY +=MORPH_DOWN;
			else if(targetY > locY + 55)
				locY +=MORPH_UP;   
			else if(targetX >= locX)					
				locX +=MORPH_RIGHT; 
			else if(targetX < locX)				
				locX +=MORPH_LEFT;
			
		}

		//Normal Sprinting Movement
		else
		{
			if(targetY < locY - 5)
				locY +=SPRINT_DOWN;  
			else if(targetY > locY + 55)
				locY +=SPRINT_UP;    
			else if(targetX >= locX)			
				locX +=SPRINT_RIGHT; 
			else if(targetX < locX)				
				locX +=SPRINT_LEFT;
			
		}	          
	}
	/**
	 * The stalk method allows Jason to search for the Counselor when he/she is not within proximity
	 * @param targetX - The x-coordinate of the Counselor
	 * @param targetY - The y-coordinate of the Counselor
	 */
	public void stalk(float targetX, float targetY)
	{	
		//If he is overlapping an obstacle
		if(jasonOverObstacle)
		{
			if(targetY < locY - 5)
				locY +=MORPH_DOWN;                             
			else if(targetY > locY + 55)
				locY +=MORPH_UP;   
			else if(targetX >= locX)					
				locX +=MORPH_RIGHT; 
			else if(targetX < locX)				
				locX +=MORPH_LEFT;
			
		}
		//Normal Walking Movement
		else
		{
			if(targetY < locY - 5)
				locY += WALK_DOWN;                             
			else if(targetY > locY + 55)
				locY += WALK_UP;       
			else if(targetX >= locX)					
				locX +=WALK_RIGHT; 
			else if(targetX < locX)				
				locX +=WALK_LEFT;
		
		}	    
	}
	//List of Info Methods(Getters and Setters)
	public Rectangle getJasonRectangle()
	{
		return jasonBoundary;
	}
	public Sprite getSprite()
	{
		return jasonImage;
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
	public boolean getJasonOverObstacle()
	{
		return jasonOverObstacle;
	}
	public void setJasonObstacle(boolean result)
	{
		jasonOverObstacle = result;
	}
}

