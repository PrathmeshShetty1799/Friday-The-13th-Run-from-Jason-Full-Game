import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
/**
 * @author Prathmesh Shetty(717937)
 * The Main Menu Screen displays the main menu of the gmae which consists of links to Character Select, How to Play, and Credits
 */
public class MainMenuScreen implements Screen {

	//List of Fields
	private Sprite mainMenuBackground;	
	private SpriteBatch batch;

	//List of Buttons (Public Only so that the value of these buttons can be checked by Screen Manager)	 
	Button playButton;		
	Button howToPlayButton;		
	Button creditsButton;

	Button soundEffectsOn,soundEffectsOff;	
	Button musicOn,musicOff;

	/**
	 * Constructor: Initializes the Background and the Button
	 */
	MainMenuScreen()
	{
		batch = new SpriteBatch();
		mainMenuBackground = new Sprite( new Texture(Gdx.files.internal("F13-Assets/Buttons and Menus/MainMenuBackground.jpg")));
		mainMenuBackground.setPosition(0,0);		

		//Buttons
		playButton = new Button(100,100,"F13-Assets/Buttons and Menus/Play.png","F13-Assets/Buttons and Menus/Play - Highlighted.png",114,50);		
		howToPlayButton = new Button(300,100,"F13-Assets/Buttons and Menus/How To Play.png","F13-Assets/Buttons and Menus/How To Play - Highlighted.png",286,50);
		creditsButton = new Button(700,100,"F13-Assets/Buttons and Menus/Credits.png","F13-Assets/Buttons and Menus/Credits - Highlighted.png",172,50);

		//Music On/Off
		musicOn = new Button(1375,40,"F13-Assets/Buttons and Menus/On.png","F13-Assets/Buttons and Menus/On - Highlighted.png",83,60);	
		musicOff =  new Button(1475,40,"F13-Assets/Buttons and Menus/Off.png","F13-Assets/Buttons and Menus/Off - Highlighted.png",103,60);	

		//Sound On/Off/
		soundEffectsOn= new Button(1375,175,"F13-Assets/Buttons and Menus/On.png","F13-Assets/Buttons and Menus/On - Highlighted.png",83,60);	
		soundEffectsOff =  new Button(1475,175,"F13-Assets/Buttons and Menus/Off.png","F13-Assets/Buttons and Menus/Off - Highlighted.png",103,60);

		musicOn.setIsPressed(true);
		soundEffectsOn.setIsPressed(true);
	}			
	/**
	 * The render method renders to draw the aspects of the Main Menu such as buttons and background
	 */
	public void render(float arg0)
	{
		batch.begin();
		batch.draw(mainMenuBackground,0,0,1664,900);		
		//Drawing Texts
		batch.draw(playButton.getSpriteOriginal(), playButton.getButtonX(), playButton.getButtonY(),playButton.getWidth(),playButton.getHeight());	
		if(playButton.isOver())
			batch.draw(playButton.getSpritePressed(),playButton.getButtonX(), playButton.getButtonY(),playButton.getWidth(),playButton.getHeight());	

		batch.draw(howToPlayButton.getSpriteOriginal(),howToPlayButton.getButtonX(), howToPlayButton.getButtonY(),howToPlayButton.getWidth(),howToPlayButton.getHeight());
		if(howToPlayButton.isOver())
			batch.draw(howToPlayButton.getSpritePressed(),howToPlayButton.getButtonX(), howToPlayButton.getButtonY(),howToPlayButton.getWidth(),howToPlayButton.getHeight());	

		batch.draw(creditsButton.getSpriteOriginal(),creditsButton.getButtonX(), creditsButton.getButtonY(),creditsButton.getWidth(),creditsButton.getHeight());
		if(creditsButton.isOver())
			batch.draw(creditsButton.getSpritePressed(),creditsButton.getButtonX(), creditsButton.getButtonY(),creditsButton.getWidth(),creditsButton.getHeight());			 

		//Music On/Off Buttons
		batch.draw(musicOn.getSpriteOriginal(), musicOn.getButtonX(),musicOn.getButtonY(),musicOn.getWidth(),musicOn.getHeight());	
		if(musicOn.isOver() || musicOn.getIsPressed())
			batch.draw(musicOn.getSpritePressed(),musicOn.getButtonX(), musicOn.getButtonY(),musicOn.getWidth(),musicOn.getHeight());	

		batch.draw(musicOff.getSpriteOriginal(), musicOff.getButtonX(),musicOff.getButtonY(),musicOff.getWidth(),musicOff.getHeight());	
		if(musicOff.isOver() || musicOff.getIsPressed())
			batch.draw(musicOff.getSpritePressed(),musicOff.getButtonX(), musicOff.getButtonY(),musicOff.getWidth(),musicOff.getHeight());	

		//Sound Effects On/Off Buttons
		batch.draw(soundEffectsOn.getSpriteOriginal(), soundEffectsOn.getButtonX(),soundEffectsOn.getButtonY(),soundEffectsOn.getWidth(),soundEffectsOn.getHeight());	
		if(soundEffectsOn.isOver() || soundEffectsOn.getIsPressed())
			batch.draw(soundEffectsOn.getSpritePressed(),soundEffectsOn.getButtonX(), soundEffectsOn.getButtonY(),soundEffectsOn.getWidth(),soundEffectsOn.getHeight());	

		batch.draw(soundEffectsOff.getSpriteOriginal(), soundEffectsOff.getButtonX(),soundEffectsOff.getButtonY(),soundEffectsOff.getWidth(),soundEffectsOff.getHeight());	
		if(soundEffectsOff.isOver() || soundEffectsOff.getIsPressed())
			batch.draw(soundEffectsOff.getSpritePressed(),soundEffectsOff.getButtonX(), soundEffectsOff.getButtonY(),soundEffectsOff.getWidth(),soundEffectsOff.getHeight());	

		batch.end();
		isButtonPressed();
	}	
	/**
	 * The isButtonPressed method updates the value of the buttons which helps to change the screens
	 */	
	public void isButtonPressed()
	{
		if(Gdx.input.isButtonPressed(Buttons.LEFT)&& playButton.isOver())
			playButton.setIsPressed(true);
		if(Gdx.input.isButtonPressed(Buttons.LEFT)&& howToPlayButton.isOver())
			howToPlayButton.setIsPressed(true);
		if(Gdx.input.isButtonPressed(Buttons.LEFT)&& creditsButton.isOver())
			creditsButton.setIsPressed(true);		

		//Adjusts Music
		if(Gdx.input.isButtonPressed(Buttons.LEFT)&& musicOn.isOver())
		{
			musicOn.setIsPressed(true);		
			musicOff.setIsPressed(false);	
		}
		else if(Gdx.input.isButtonPressed(Buttons.LEFT)&& musicOff.isOver())
		{
			musicOff.setIsPressed(true);		
			musicOn.setIsPressed(false);	
		}	

		//Adjusts Sound Effects
		if(Gdx.input.isButtonPressed(Buttons.LEFT)&& soundEffectsOn.isOver())
		{
			soundEffectsOn.setIsPressed(true);		
			soundEffectsOff.setIsPressed(false);	
		}		
		else if(Gdx.input.isButtonPressed(Buttons.LEFT)&& soundEffectsOff.isOver())
		{
			soundEffectsOff.setIsPressed(true);		
			soundEffectsOn.setIsPressed(false);	
		}		
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
