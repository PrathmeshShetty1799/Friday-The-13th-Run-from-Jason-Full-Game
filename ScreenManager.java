import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
/**
 * @author Prathmesh Shetty - 717937
 * - ScreenManager Class handles the which screen is to be current. Gets info from other screens to pass on the correct screen to Game to display
 *  */
public class ScreenManager {

	//Fields
	private Screen currentScreen;
	private Music backgroundMusic;
	private Music buttonClick;
	private boolean getBackToMainMenu;
	private boolean soundEffectsOff;
	private boolean musicOff;
	//List Of Menu Screens
	private MainMenuScreen mainMenu;
	private GameScreen gameScreen;
	private HowToPlayScreen howToPlayScreen;
	private CreditsScreen creditsScreen;
	private CharacterSelectScreen characterSelectScreen;

	//Creates and Initializes All Screens that Are Used During the Game
	ScreenManager()
	{
		getBackToMainMenu = false;
		mainMenu = new MainMenuScreen();
		howToPlayScreen = new HowToPlayScreen();
		creditsScreen = new CreditsScreen();
		characterSelectScreen = new CharacterSelectScreen();
		currentScreen = mainMenu;
		backgroundMusic = Gdx.audio.newMusic(Gdx.files.internal("F13-Assets/Music/Menu Music.mp3"));
		buttonClick = Gdx.audio.newMusic(Gdx.files.internal("F13-Assets/Music/Button Click Sound Effect.mp3"));

		musicOff = false;
		soundEffectsOff = false;
	}
	/**
	 * The updateScreen method updates the most currentScreen to the Game
	 **/	
	public void updateScreen()
	{	
		//Music
		if(currentScreen != gameScreen && !musicOff)
		{
			backgroundMusic.play();
			backgroundMusic.setLooping(true);
		}
		else 
			backgroundMusic.stop();

		if(getBackToMainMenu)	
		{
			currentScreen = mainMenu;	
			getBackToMainMenu = false;			
		}		
		//Main Menu Selection Options
		if(currentScreen == mainMenu)
		{			
			if(mainMenu.playButton.getIsPressed())
			{	
				if(!soundEffectsOff)
					buttonClick.play();

				currentScreen = characterSelectScreen;				
				mainMenu.playButton.setIsPressed(false);				
			}
			else if(mainMenu.howToPlayButton.getIsPressed())
			{
				if(!soundEffectsOff)
					buttonClick.play();

				currentScreen = howToPlayScreen;	
				mainMenu.howToPlayButton.setIsPressed(false);
			}					
			else if(mainMenu.creditsButton.getIsPressed())
			{
				if(!soundEffectsOff)
					buttonClick.play();

				currentScreen = creditsScreen;	
				mainMenu.creditsButton.setIsPressed(false);
			}	
			if(mainMenu.musicOn.getIsPressed())				
				musicOff = false;			
			else if(mainMenu.musicOff.getIsPressed())
				musicOff = true;		
			if(mainMenu.soundEffectsOn.getIsPressed())
				soundEffectsOff = false;	
			else if(mainMenu.soundEffectsOff.getIsPressed())
				soundEffectsOff = true;
			
		}	
		//Character Selection Screen Options
		else if(currentScreen == characterSelectScreen)
		{	
			if(characterSelectScreen.returnToMainMenuButton.getIsPressed())
			{
				if(!soundEffectsOff)
					buttonClick.play();
				getBackToMainMenu = true;
				characterSelectScreen.returnToMainMenuButton.setIsPressed(false);
			}

			else if(characterSelectScreen.startButton.getIsPressed())
			{	
				if(!soundEffectsOff)
					buttonClick.play();

				gameScreen = new GameScreen(characterSelectScreen.whichCharacter(), musicOff);
				currentScreen = null;
				currentScreen = gameScreen;
				characterSelectScreen.startButton.setIsPressed(false);				
			}				

		}
		//How To Play Screen Options
		else if(currentScreen == howToPlayScreen)
		{
			if (howToPlayScreen.returnToMainMenuButton.getIsPressed())
			{
				if(!soundEffectsOff)
					buttonClick.play();
				
				getBackToMainMenu = true;
				howToPlayScreen.nextButton.setIsPressed(false);
				howToPlayScreen.previousButton.setIsPressed(false);
				howToPlayScreen.returnToMainMenuButton.setIsPressed(false);
			}	
			else if(howToPlayScreen.nextButton.getIsPressed())
			{
				if(!soundEffectsOff)
					buttonClick.play();
			}
			else if	(howToPlayScreen.previousButton.getIsPressed())
			{
				if(!soundEffectsOff)
					buttonClick.play();
			}
		}
		//Credits Screen Options
		else if(currentScreen == creditsScreen)
		{
			if (creditsScreen.returnToMainMenuButton.getIsPressed())
			{
				if(!soundEffectsOff)
					buttonClick.play();
				
				getBackToMainMenu = true;
				creditsScreen.nextButton.setIsPressed(false);
				creditsScreen.previousButton.setIsPressed(false);
				creditsScreen.returnToMainMenuButton.setIsPressed(false);
			}			
			else if(creditsScreen.nextButton.getIsPressed())
			{
				if(!soundEffectsOff)
					buttonClick.play();
			}

			else if	(creditsScreen.previousButton.getIsPressed())
			{
				if(!soundEffectsOff)
					buttonClick.play();
			}
		}
		//Game Screen Options
		else if(currentScreen == gameScreen)
		{
			if(Gdx.input.isKeyPressed(Keys.ESCAPE))
			{
				getBackToMainMenu = true;
				gameScreen.playAgainButton.setIsPressed(false);
				gameScreen.returnMainMenuButton.setIsPressed(false);

			}
			else if (gameScreen.playAgainButton.getIsPressed())
			{		
				if(!soundEffectsOff)
					buttonClick.play();	

				currentScreen = characterSelectScreen;
				gameScreen.playAgainButton.setIsPressed(false);
			}
			else if (gameScreen.returnMainMenuButton.getIsPressed())
			{
				if(!soundEffectsOff)
					buttonClick.play();

				getBackToMainMenu = true;
				gameScreen.returnMainMenuButton.setIsPressed(false);				
			}
		}
	}
	/**
	 * The getCurrentScreen passes the most current screen to Game
	 * @return the Current Screen 
	 */
	public Screen getCurrentScreen()
	{
		return currentScreen;
	}

}
