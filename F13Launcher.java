import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
/**
 * @author Prathmesh Shetty(717937)
 * The Launcher executes the initial and final commands in order to execute the game
 * 
 * List of Known Bugs:
 *  The Button Click Sound Effect will lag if used in quick succession
 *
 * Future Plans:
 * 	New Maps
 * 	More Advanced Jason AI
 *  Sprite Animations
 *
 *  Game-Related Jargon
 *  - Counselor: Human Controlled Player
 *  - Jason: The Enemy (Artificial Intelligence)
 *  - Car Keys: One Item Needed to Escape
 *  - Gas Can: The Other Item Needed to Escape
 *  - Batteries: As a means to increase the radius of visibility
 *  
 *  Item Number List
 *  - 1 = Keys
 *  - 2 = Batteries
 *  - 3 = Vehicle 
 *  - 4 = Gas Can	 
 *  
 *  Character Numbers
 *  - 1 - Tommy Jarvis
 *  - 2 - Vanessa Jones
 */
public class F13Launcher 
{
	public static void main (String[] args)
	{
		FridayThe13 game= new FridayThe13();//The Game
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration(); 
			
		//Ideal Dimensions
		//w = 1664, h = 900
		config.width = 1664;
		config.height = 900;
		config.resizable = false;
			
		new LwjglApplication(game,config);//Executes the Game
	}
}