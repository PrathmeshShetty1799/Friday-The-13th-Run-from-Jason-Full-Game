import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
/**
 * The How To Play Menu Screen will be the location for the objective of the game as well as the controls needed and used
 * @author Prathmesh Shetty(717937)
 */

public class CharacterSelectScreen implements Screen {

	//Fields
	private Sprite backgroundPage1;
	private Sprite characterHover;
	private SpriteBatch batch;

	//Pages
	private Rectangle characterSelectOne;
	private Rectangle characterSelectTwo;

	private int characterNo;

	//List of Buttons(Public Only so that the value of these buttons can be checked by Screen Manager)
	Button startButton;	
	Button returnToMainMenuButton;	

	/**
	 * Constructor: Initializes the Background and the Button
	 */
	CharacterSelectScreen()
	{		
		batch = new SpriteBatch();

		backgroundPage1 = new Sprite( new Texture(Gdx.files.internal("F13-Assets/Buttons and Menus/CharacterSelect.jpg")));
		backgroundPage1.setPosition(0,0);

		characterHover = new Sprite( new Texture(Gdx.files.internal("F13-Assets/Characters/CharacterSelect.png")));
		characterHover.setPosition(0,0);

		//Buttons
		startButton = new Button(Gdx.graphics.getWidth() - 142,25,"F13-Assets/Buttons and Menus/Start.png","F13-Assets/Buttons and Menus/Start - Highlighted.png",142,50);	
		returnToMainMenuButton =  new Button(0,25,"F13-Assets/Buttons and Menus/Return to Main Menu.png","F13-Assets/Buttons and Menus/Return to Main Menu - Highlighted.png",443,50);	
		
		characterSelectOne = new Rectangle(281,324,360,360);
		characterSelectTwo = new Rectangle(994,314,360,360);
	}	
	/**
	 * The render method constantly renders when this screen becomes the main screen
	 */
	public void render(float arg0) {

		batch.begin();
		//First Page		
		//Background
		batch.draw(backgroundPage1, 0,0,1664,900);

		//Buttons
		batch.draw(startButton.getSpriteOriginal(), startButton.getButtonX(),startButton.getButtonY(),startButton.getWidth(),startButton.getHeight());	
		if(startButton.isOver())
			batch.draw(startButton.getSpritePressed(),startButton.getButtonX(), startButton.getButtonY(),startButton.getWidth(),startButton.getHeight());	
		
		batch.draw(returnToMainMenuButton.getSpriteOriginal(), returnToMainMenuButton.getButtonX(),returnToMainMenuButton.getButtonY(),returnToMainMenuButton.getWidth(),returnToMainMenuButton.getHeight());	
		if(returnToMainMenuButton.isOver())
			batch.draw(returnToMainMenuButton.getSpritePressed(),returnToMainMenuButton.getButtonX(), returnToMainMenuButton.getButtonY(),returnToMainMenuButton.getWidth(),returnToMainMenuButton.getHeight());	
		
		//Character Selections
		if(characterSelectOne.contains(Gdx.input.getX(),Gdx.input.getY())|| characterNo ==1)
			batch.draw(characterHover,281,322,characterHover.getWidth(),characterHover.getHeight());

		if(characterSelectTwo.contains(Gdx.input.getX(),Gdx.input.getY())|| characterNo == 2)
			batch.draw(characterHover,994,312, characterHover.getWidth()-3,characterHover.getHeight());		

		batch.end();
		isButtonPressed();//Checks The Mouse Input

	}
	/**
	 * The isButtonPressed method updates the value of the buttons which helps to change the screens
	 */	
	public void isButtonPressed()
	{	
		if(Gdx.input.isButtonPressed(Buttons.LEFT)&& returnToMainMenuButton.isOver())
			returnToMainMenuButton.setIsPressed(true);	
		else if(Gdx.input.isButtonPressed(Buttons.LEFT)&& startButton.isOver())
			startButton.setIsPressed(true);					
		if (Gdx.input.isButtonPressed(Buttons.LEFT) && characterSelectOne.contains(Gdx.input.getX(),Gdx.input.getY()))
			characterNo = 1;
		else if (Gdx.input.isButtonPressed(Buttons.LEFT) && characterSelectTwo.contains(Gdx.input.getX(),Gdx.input.getY()))
			characterNo = 2;
	}	
	/**
	 * The whichCharacter method passes on the value which indicates which Character is chosen
	 */
	public int whichCharacter()
	{
		return characterNo;
	}
	//List of Other Methods
	public void dispose() {
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