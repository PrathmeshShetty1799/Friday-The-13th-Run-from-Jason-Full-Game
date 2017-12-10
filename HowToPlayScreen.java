import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
/**
 * The How To Play Menu Screen will be the location for the objective of the game as well as the controls needed and used
 * @author Prathmesh Shetty(717937)
 */

public class HowToPlayScreen implements Screen {

	//Fields
	private Sprite backgroundPage1;
	private Sprite backgroundPage2;
	private SpriteBatch batch;

	//List of Buttons (Public Only so that the value of these buttons can be checked by Screen Manager)
	Button nextButton;
	Button previousButton;
	Button returnToMainMenuButton;

	//Pages
	private boolean firstPage;
	private boolean secondPage;

	/**
	 * Constructor: Initializes the Background and the Button
	 */
	HowToPlayScreen()
	{		
		batch = new SpriteBatch();

		backgroundPage1 = new Sprite( new Texture(Gdx.files.internal("F13-Assets/Buttons and Menus/HowToPlayBackground.jpg")));
		backgroundPage1.setPosition(0,0);			
		backgroundPage2 = new Sprite( new Texture(Gdx.files.internal("F13-Assets/Buttons and Menus/HowToPlayBackground2.jpg")));
		backgroundPage2.setPosition(0,0);		

		firstPage = true;
		secondPage = false;

		nextButton = new Button(Gdx.graphics.getWidth() - 120,25,"F13-Assets/Buttons and Menus/Next.png","F13-Assets/Buttons and Menus/Next - Highlighted.png",110,50);		
		previousButton = new Button(15,25,"F13-Assets/Buttons and Menus/Previous.png","F13-Assets/Buttons and Menus/Previous - Highlighted.png",194,50);
		returnToMainMenuButton =  new Button(Gdx.graphics.getWidth()/2 - 443/2,25,"F13-Assets/Buttons and Menus/Return to Main Menu.png","F13-Assets/Buttons and Menus/Return to Main Menu - Highlighted.png",443,50);	
	}	
	/**
	 * The render method constantly renders when this screen becomes the main screen
	 */
	public void render(float arg0) {
		//Changes with Buttons
		if(nextButton.getIsPressed())
		{			
			firstPage = false;
			secondPage = true;
			nextButton.setIsPressed(false);
		}
		else if(previousButton.getIsPressed())
		{
			firstPage = true;
			secondPage = false;
			previousButton.setIsPressed(false);
		}
		batch.begin();
		
		//First Page		
		if(firstPage)
		{
			//Background
			batch.draw(backgroundPage1, 0,0,1664,900);
			batch.draw(nextButton.getSpriteOriginal(), nextButton.getButtonX(),nextButton.getButtonY(),nextButton.getWidth(),nextButton.getHeight());	
			if(nextButton.isOver())
				batch.draw(nextButton.getSpritePressed(),nextButton.getButtonX(), nextButton.getButtonY(),nextButton.getWidth(),nextButton.getHeight());	
		}
		else
		{
			//Background
			batch.draw(backgroundPage2,  0, 0, 1664, 900);
			batch.draw(previousButton.getSpriteOriginal(), previousButton.getButtonX(), previousButton.getButtonY(),previousButton.getWidth(),previousButton.getHeight());	
			if(previousButton.isOver())
				batch.draw(previousButton.getSpritePressed(),previousButton.getButtonX(), previousButton.getButtonY(),previousButton.getWidth(),previousButton.getHeight());	
		}	

		//On Both Pages
		batch.draw(returnToMainMenuButton.getSpriteOriginal(), returnToMainMenuButton.getButtonX(),returnToMainMenuButton.getButtonY(),returnToMainMenuButton.getWidth(),returnToMainMenuButton.getHeight());	
		if(returnToMainMenuButton.isOver())
			batch.draw(returnToMainMenuButton.getSpritePressed(),returnToMainMenuButton.getButtonX(), returnToMainMenuButton.getButtonY(),returnToMainMenuButton.getWidth(),returnToMainMenuButton.getHeight());	

		batch.end();
		isButtonPressed();//Checks Any Mouse Input
	}
	/**
	 * The isButtonPressed method updates the value of the buttons which helps to change the screens
	 */	
	public void isButtonPressed()
	{
		if(Gdx.input.isButtonPressed(Buttons.LEFT)&& returnToMainMenuButton.isOver())
			returnToMainMenuButton.setIsPressed(true);	
		else if(Gdx.input.isButtonPressed(Buttons.LEFT)&& nextButton.isOver()&& firstPage)
			nextButton.setIsPressed(true);
		else if(Gdx.input.isButtonPressed(Buttons.LEFT)&& previousButton.isOver()&&secondPage)
			previousButton.setIsPressed(true);		
	}
	//List of Other Methods	
	public void dispose() 
	{
		batch.dispose();
	}	
	public void hide() {
	}
	public void pause() {
	}
	public void resize(int arg0, int arg1) {
	}
	public void resume() {
	}
	public void show() {
	}
}
