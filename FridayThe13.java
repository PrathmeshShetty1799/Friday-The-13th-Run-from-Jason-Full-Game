import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;

/**
 * @author Prathmesh Shetty(717937)
 *  The FridayThe13 is the game that runs the current screen which is passed from the Screen Manager
 *  It uses information from the the current screen to handle the render and the graphics.
 **/
public class FridayThe13 extends Game {

	//Fields
	private Screen currentScreen;
	private ScreenManager screens;
	
	/**
	 * The create method initializes the fields with information from other classes
	 */
	public void create() 
	{		
		screens = new ScreenManager();//Handles and Passes on the Current Screen	
	}
	/**
	 * The render method allows to update information for section such as graphics for each part of the program
	 * Constantly runs at 60 times per second(default) to get accurate information 
	 */
	public void render() 
	{			
		screens.updateScreen();//Updates which screen needs to be shown
		currentScreen = screens.getCurrentScreen();
		setScreen(currentScreen);//Sets the most recent screen as the current screen of the game			
		currentScreen.render(0);
	}
	/**
	 * The dispose method disposes any aspects of the game when not in use
	 */
	public void dispose() {	
		currentScreen.dispose();
	}	
	//Other Methods(Yet to Use)
	public void resize(int width, int height) {	
	}
	public void resume() {	

	}
	public void pause() {	

	}	

}