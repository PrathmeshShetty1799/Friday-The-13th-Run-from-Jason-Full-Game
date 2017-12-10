import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;
/**
 * The Button is an customized class for this game that is linked to a particular screen
 * @author Prathmesh Shetty(717937)
 */
public class Button {

	//Fields
	private Sprite originalButton;//Before the Button is Pressed
	private Sprite buttonPressed;//After The Button is Pressed
	private float locX, locY;
	private float width,height;
	private boolean isPressed;
	private Rectangle buttonBoundary;		
	
	/**
	 * Initializes the aspects for a generic button
	 * @param x - The x-coordinate of the button(lower-left corner)
	 * @param y - The y-coordinate of the button(lower-left corner)
	 * @param imagePath - The image path of teh button 
	 * @param imagePathPressed
	 * @param width - The width of the image
	 * @param height - The width of the image
	 */
	Button(float x, float y, String imagePath, String imagePathPressed, float picWidth, float picHeight)
	{
		locX = x;
		locY = y;
		width = picWidth;
		height = picHeight;
		
		originalButton= new Sprite(new Texture(Gdx.files.internal(imagePath)));
		originalButton.setPosition(locX,locY);	
		buttonPressed= new Sprite(new Texture(Gdx.files.internal(imagePathPressed)));
		buttonPressed.setPosition(locX,locY);			
	
		buttonBoundary = new Rectangle(x,y,width,height);
		isPressed = false;
	}
	
	/**
	 * The isOver method checks for the mouse location and whether or not, it is hovering over the button
	 * @return If the mouse is over the button or not
	 */
	public boolean isOver()
	{
		if(buttonBoundary.contains(Gdx.input.getX(), 900 - Gdx.input.getY()))
			return true;
		else
			return false;
	}
	/**
	 * The setIsPressed changes the value of isPressed to indicate if the button is pressed or not
	 * @param result The value of result is passed to the boolean value isPressed
	 */
	
	public void setIsPressed(boolean result)
	{	
		isPressed = result;
	}
	//List of Info Methods(Getters and Setters)
	public boolean getIsPressed()
	{
		return isPressed;
	}
	public float getButtonX()
	{
		return locX;
	}
	
	public float getButtonY()
	{
		return locY;
	}
	
	public float getWidth()
	{
		return width;
	}
	
	public float getHeight()
	{
		return height;
	}
	
	public Sprite getSpriteOriginal()
	{
		return originalButton;
	}
	
	public Sprite getSpritePressed()
	{
		return buttonPressed;
	}	
	
}
