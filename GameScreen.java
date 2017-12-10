import java.util.Random;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.utils.ScissorStack;
/**
 * @author Prathmesh Shetty(717937)
 * The GameScreen displays the game screen where the counselor is trying to survive. It handles all the spawning, collisions, and rendering game aspects
 */
public class GameScreen implements Screen {

	//Game HUD Aspects
	private SpriteBatch batch;	
	private Sprite gameHUD1,gameHUD2;
	private Sprite blackRect, counselorHighlight;
	private Sprite keyFoundOutline;
	private Sprite gasFoundOutline;
	private Sprite batteryCount;
	private Sprite fearBar,staminaBar;
	private Sprite playerCaught, playerWon;

	//Characters
	private Counselor player;
	private Jason enemy;
	private int characterNo;

	// Player/Enemy Aspects	
	private boolean keyFound;	
	private boolean gasFound;
	private AllItemsList itemList;	

	//Game Aspects
	private boolean gameEnd;
	Button playAgainButton; //(Public Only so that the value of these buttons can be checked by Screen Manager)
	Button returnMainMenuButton;
	private boolean musicOff;

	private Music stalkMusic;
	private Music chaseMusic;
	private Music endGameMusic;

	//Map Aspects
	private TiledMap map1;
	private OrthographicCamera camera;
	private TiledMapRenderer tiledMapRenderer;

	//Other Aspects
	Random generator;

	/**
	 * Constructor: Initializes all the aspects of the gameScreen 
	 */
	GameScreen(int whichOne, boolean musicToggle)
	{
		//Characters
		player = new Counselor(whichOne);
		enemy = new Jason();

		//Which Character It Is - Updates the GameHUD
		characterNo = whichOne;

		//Map Aspects
		float w = Gdx.graphics.getWidth();
		float h = Gdx.graphics.getHeight();

		camera = new OrthographicCamera();
		camera.setToOrtho(false,w,h);
		camera.update();

		map1 = new TmxMapLoader().load("F13-Assets/Tiled Maps/F13 - Map - Crystal Lake.tmx");
		tiledMapRenderer = new OrthogonalTiledMapRenderer(map1);

		//Music
		stalkMusic = Gdx.audio.newMusic(Gdx.files.internal("F13-Assets/Music/Stalk Music.mp3"));
		chaseMusic = Gdx.audio.newMusic(Gdx.files.internal("F13-Assets/Music/Jason Chase Music.mp3"));
		endGameMusic = Gdx.audio.newMusic(Gdx.files.internal("F13-Assets/Music/End Game Music.mp3"));

		//Sprites/Images
		batch = new SpriteBatch();	

		blackRect = new Sprite(new Texture(Gdx.files.internal("F13-Assets/Characters/Black Rectangle.jpg")));
		blackRect.setPosition(0,128);	

		counselorHighlight = new Sprite(new Texture(Gdx.files.internal("F13-Assets/Characters/CharacterHighlight.png")));
		counselorHighlight.setPosition(0,0);

		gameHUD1 = new Sprite( new Texture(Gdx.files.internal("F13-Assets/GameHUD/GameHUD - Jarvis.png")));
		gameHUD1.setPosition(0,0);	

		gameHUD2= new Sprite( new Texture(Gdx.files.internal("F13-Assets/GameHUD/GameHUD - Vanessa.png")));
		gameHUD2.setPosition(0,0);	

		batteryCount = new Sprite(new Texture(Gdx.files.internal("F13-Assets/GameHUD/1Battery.png")));
		batteryCount.setPosition(0,0);

		keyFoundOutline = new Sprite(new Texture(Gdx.files.internal("F13-Assets/GameHUD/Key Found.png")));
		keyFoundOutline.setPosition(0,0);

		gasFoundOutline = new Sprite(new Texture(Gdx.files.internal("F13-Assets/GameHUD/Gas Found.png")));
		gasFoundOutline.setPosition(0,0);

		fearBar = new Sprite(new Texture(Gdx.files.internal("F13-Assets/GameHUD/Fear Bar.png")));
		fearBar.setPosition(0,0);
		staminaBar = new Sprite(new Texture(Gdx.files.internal("F13-Assets/GameHUD/Stamina Bar.png")));
		staminaBar.setPosition(0,0);

		playerWon = new Sprite(new Texture(Gdx.files.internal("F13-Assets/Logo and Titles/You Survived.png")));
		playerWon.setPosition(0,0);
		playerCaught = new Sprite(new Texture(Gdx.files.internal("F13-Assets/Logo and Titles/You Died.png")));
		playerCaught.setPosition(0,0);

		playAgainButton = new Button(1664/2-250,450-85,"F13-Assets/Buttons and Menus/Play Again.png","F13-Assets/Buttons and Menus/Play Again - Highlighted.png",239,50);		
		returnMainMenuButton = new Button(1664/2 + 15,450-85,"F13-Assets/Buttons and Menus/Return To Main Menu.png","F13-Assets/Buttons and Menus/Return To Main Menu - Highlighted.png",443,50);

		//Character Aspects
		gameEnd = false;
		keyFound = false;
		gasFound = false;
		itemList = new AllItemsList ();	
		itemList.addAllItems();		

		//Music Toggle
		musicOff = musicToggle;

		generator = new Random();
		//Sets the Locations of Characters and Items according to the spawn locations set on the Tiled Map
		characterSpawner();
		itemSpawner();
	}	
	/**
	 * The itemSpawner method sets the locations for the items according to the appropriate spawn points set on the Tiled Map
	 */
	public void itemSpawner()
	{
		//Gets the ID of the "Obstacles Layer" in the Tiled Map
		int objectLayerId = map1.getLayers().getCount()-1;
		MapLayer collisionObjectLayer = map1.getLayers().get(objectLayerId);
		MapObjects objects = collisionObjectLayer.getObjects();		

		//Chooses a Random Spawn Point for Each Item
		for(int i = 0; i < itemList.getSize(); i++)
		{
			int whichSpawn = generator.nextInt(objects.getCount()-1);
			Rectangle rectangle = ((RectangleMapObject) objects.get(whichSpawn)).getRectangle();	

			if(itemList.getItem(i).getItemNumber() != 3)
			{
				itemList.getItem(i).setX(rectangle.getX());
				itemList.getItem(i).setY(rectangle.getY());	
			}
		}			
		//Updates the locations of the items on Universal Items List 
		player.setItemsList(itemList);
		itemList.setBoundaries();
	}
	/**
	 * The characterSpawner method sets the locations for the characters according to the appropriate spawn points
	 */
	public void characterSpawner()
	{
		//Gets the ID of the "Obstacles Layer" in the Tiled Map
		int objectLayerId = map1.getLayers().getCount()-2;
		MapLayer collisionObjectLayer = map1.getLayers().get(objectLayerId);
		MapObjects objects = collisionObjectLayer.getObjects();		

		//Chooses a Random Character Spawn Point - For the Counselor
		int whichSpawn = generator.nextInt(objects.getCount()-1);
		Rectangle rectangle = ((RectangleMapObject) objects.get(whichSpawn)).getRectangle();		
		player.setX(rectangle.getX());
		player.setY(rectangle.getY());

		//Chooses a Random Character Spawn Point - For Jason(Enemy) 
		rectangle = ((RectangleMapObject) objects.get(whichSpawn+1)).getRectangle();	
		enemy.setX(rectangle.getX());
		enemy.setY(rectangle.getY());
	}
	/**
	 * The collisionAhead method checks for any objects that will collide with the counselor or enemy and informs them to move
	 */
	public void collisionDetector()
	{		
		boolean playerIsOverObstacle = false;// Resets the Variable To Check Each Time
		boolean jasonIsOverObstacle = false;// Resets the Variable To Check Each Time
		//Gets the ID of the "Obstacles Layer" in the Tiled Map
		int objectLayerId = map1.getLayers().getCount()-3;
		MapLayer collisionObjectLayer = map1.getLayers().get(objectLayerId);
		MapObjects objects = collisionObjectLayer.getObjects();		

		//Checks if the Player Rectangle overlaps with the objects' rectangles.
		for (RectangleMapObject rectangleObject : objects.getByType(RectangleMapObject.class)) {
			Rectangle rectangle = rectangleObject.getRectangle();
			//If Player Overlaps
			if (rectangle.overlaps(player.getPlayerRectangle())||player.getPlayerRectangle().overlaps(itemList.getVehicleRectangle())) 
				playerIsOverObstacle = true;
			player.setPlayerNearObstacle(playerIsOverObstacle);

		}
		for (RectangleMapObject rectangleObject : objects.getByType(RectangleMapObject.class)) {
			Rectangle rectangle = rectangleObject.getRectangle();
			//If Enemy Overlaps
			if (rectangle.overlaps(enemy.getJasonRectangle())||enemy.getJasonRectangle().overlaps(itemList.getVehicleRectangle()))
				jasonIsOverObstacle = true;
			enemy.setJasonObstacle(jasonIsOverObstacle);
		}

	}
	/**
	 * The updateGameScreen is one of the methods that is called in the render method of this screen
	 * The method updates all the aspects of the gameScreen
	 */
	public void updateGameScreen()
	{	
		System.out.println(enemy.getJasonOverObstacle());
		//Updates the Map	
		camera.update();		
		tiledMapRenderer.setView(camera);
		tiledMapRenderer.render();				

		//Updates the Players and their Aspects		
		collisionDetector();

		//Renders the Characters if the Game has not ended
		if(!gameEnd)
		{				
			player.updateCounselor(enemy.getX(),enemy.getY());
			enemy.updateJason(player.getX(), player.getY());	
		}				
		//Updates Music
		if(gameEnd && !musicOff)
		{
			chaseMusic.stop();
			stalkMusic.stop();			
			endGameMusic.play();
			endGameMusic.setLooping(true);			
		}
		else if(player.jasonClose()&& !musicOff)
		{
			chaseMusic.setVolume((float)(((300 - player.getJasonDistance())/300) * 100));
			chaseMusic.play();
			stalkMusic.stop();
			chaseMusic.setLooping(true);
		}
		else if(!musicOff)
		{
			chaseMusic.stop();
			stalkMusic.play();
			stalkMusic.setLooping(true);
		}

		//Updating Character Aspects
		itemList = player.getItemList();
		keyFound = player.hasKeys();
		gasFound = player.hasGas();
	}
	/**
	 * The drawGameElements is one of the methods that is called in the render method of this screen
	 * The method draws all the aspects of the gameScreen
	 * @throws InterruptedException 
	 */
	public void drawGameElements()
	{	
		batch.begin();		

		//Prints the End Messages depending on the result
		if(player.playerWon())
		{
			batch.draw(playerWon, Gdx.graphics.getWidth()/2-142 ,Gdx.graphics.getHeight()/2-43,482,85);
			gameEnd = true;			

		}
		else if (player.playerCaught(enemy.getJasonRectangle()))
		{
			batch.draw(playerCaught, Gdx.graphics.getWidth()/2-142 ,Gdx.graphics.getHeight()/2-43,307,85);
			gameEnd = true;		
		}

		//The Buttons After the Game has Ended
		if(gameEnd)
		{
			batch.draw(playAgainButton.getSpriteOriginal(), playAgainButton.getButtonX(), playAgainButton.getButtonY(),playAgainButton.getWidth(),playAgainButton.getHeight());	
			if(playAgainButton.isOver())
				batch.draw(playAgainButton.getSpritePressed(),playAgainButton.getButtonX(), playAgainButton.getButtonY(),playAgainButton.getWidth(),playAgainButton.getHeight());	

			batch.draw(returnMainMenuButton.getSpriteOriginal(),returnMainMenuButton.getButtonX(), returnMainMenuButton.getButtonY(),returnMainMenuButton.getWidth(),returnMainMenuButton.getHeight());
			if(returnMainMenuButton.isOver())
				batch.draw(returnMainMenuButton.getSpritePressed(),returnMainMenuButton.getButtonX(), returnMainMenuButton.getButtonY(),returnMainMenuButton.getWidth(),returnMainMenuButton.getHeight());	
		}
		else
		{	
			//Dark Surroundings - The Scissor Stack allows so that the batch can only draw within a certain section. The batch.flush ends that change so that after the batch is still able to draw anywhere on the screen
			// Due to the Scissor Stack, the items and Jason are the only things that can be seen within the clipBounds(The highlight around the counselor
			batch.setColor(batch.getColor().r, batch.getColor().g, batch.getColor().b, 0.9f);
			batch.draw (counselorHighlight,player.getX()-player.getBrightRadius(),player.getY()-player.getBrightRadius(),player.getBrightRadius()*2 + 50,player.getBrightRadius()*2 + 50);
			batch.setColor(batch.getColor().r, batch.getColor().g, batch.getColor().b, 1.0f);

			Rectangle scissors = new Rectangle();
			Rectangle clipBounds =  new Rectangle(player.getX()-player.getBrightRadius(),player.getY()-player.getBrightRadius(),player.getBrightRadius()*2 + 50,player.getBrightRadius()*2 + 50);

			ScissorStack.calculateScissors(camera, batch.getTransformMatrix(), clipBounds, scissors);
			ScissorStack.pushScissors(scissors);	

			//Item Spawn on Map			
			for(int i = 0; i < itemList.getSize(); i++)
			{
				batch.draw(itemList.getItem(i).getSprite(), itemList.getItem(i).getX(), itemList.getItem(i).getY(), 50,50);
				if(itemList.getItem(i).getItemNumber()== 3)
					batch.draw(itemList.getItem(i).getSprite(), itemList.getItem(i).getX(), itemList.getItem(i).getY(), 50,100);
			}

			//Counselor
			if(characterNo ==1)
				batch.draw(player.getSprite(), player.getX(), player.getY(), 50, 50);		
			else 
				batch.draw(player.getSprite(), player.getX(), player.getY(), 27, 50);

			//Enemy(Jason)
			if(enemy.getJasonOverObstacle())//If he is in morph mode
			{
				batch.setColor(batch.getColor().r, batch.getColor().g, batch.getColor().b, 0.5f);
				batch.draw(enemy.getSprite(), enemy.getX(), enemy.getY(), 50, 50);	
				batch.setColor(batch.getColor().r, batch.getColor().g, batch.getColor().b, 1.0f);
			}
			else 
				batch.draw(enemy.getSprite(), enemy.getX(), enemy.getY(), 50, 50);	

			batch.flush();
			ScissorStack.popScissors();	

			drawDarkSurroundings();//Prints the Black Rectangles
			drawGameHUD();//Prints the GameHUD
		}			
		isButtonPressed();// Checks for Mouse Input
		batch.end();
	}
	//Sub Drawing Methods

	/**
	 * The drawDarkSurroundings will be responsible to draw the rectangles around the center of the character
	 */
	public void drawDarkSurroundings()
	{	//Black Rectangles	
		batch.setColor(batch.getColor().r, batch.getColor().g, batch.getColor().b, 0.9f);//Making More Transparent for the Night Effect

		//Top Rectangle
		batch.draw(blackRect, 0, (player.getY() + 50 + player.getBrightRadius()), Gdx.graphics.getWidth(), 900 - (counselorHighlight.getX() + counselorHighlight.getHeight()));	

		//Bottom Rectangle
		batch.draw(blackRect,0, 128, Gdx.graphics.getWidth(), player.getY()-player.getBrightRadius() - 128);	

		//Left Rectangle
		batch.draw(blackRect,0 , player.getY() - player.getBrightRadius(), player.getX() - player.getBrightRadius(), player.getBrightRadius()*2 + 50);	

		//Right Side 
		batch.draw(blackRect,player.getX() + 50 + player.getBrightRadius() , player.getY() - player.getBrightRadius(), Gdx.graphics.getWidth() - player.getX() + 50 + player.getBrightRadius(), (player.getBrightRadius()*2 + 50));	

		batch.setColor(batch.getColor().r, batch.getColor().g, batch.getColor().b, 1f);//Changing the Opacity to Normal		
	}

	/**
	 * The drawGameHUD will be responsible to draw the Game HUD and all the indicators along with it
	 */
	public void drawGameHUD()
	{		
		//GameHUD and Aspects
		if(characterNo == 1)		
			batch.draw(gameHUD1,0,0);//GameHUD			
		else
			batch.draw(gameHUD2,0,0);

		if(keyFound)
			batch.draw(keyFoundOutline,1210, 5 , 128,120);

		if(gasFound)
			batch.draw(gasFoundOutline,1362,5,128,120);

		//Batteries On HUD
		int amountBatteries = player.howManyBatteries();
		if(amountBatteries ==1)
		{
			batteryCount = new Sprite(new Texture(Gdx.files.internal("F13-Assets/GameHUD/1Battery.png")));
			batteryCount.setPosition(0,0);
		}
		else if(amountBatteries ==2)
		{
			batteryCount = new Sprite(new Texture(Gdx.files.internal("F13-Assets/GameHUD/2Battery.png")));
			batteryCount.setPosition(0,0);
		}
		else if(amountBatteries ==3){
			batteryCount = new Sprite(new Texture(Gdx.files.internal("F13-Assets/GameHUD/3Battery.png")));
			batteryCount.setPosition(0,0);
		}
		else if(amountBatteries ==4){
			batteryCount = new Sprite(new Texture(Gdx.files.internal("F13-Assets/GameHUD/4Battery.png")));
			batteryCount.setPosition(0,0);
		}
		if(player.howManyBatteries() > 0)
			batch.draw(batteryCount, 1515, 4 , 128,120);


		//Fear Bar
		if (player.getFear() > 0){
			batch.draw(fearBar,690,55,40,7);
			if(player.getFear() > 33){
				batch.draw(fearBar,730,55,40,7);
				if(player.getFear() > 66)
					batch.draw(fearBar,770,55,40,7);
			}
		}
		//Stamina Bar
		if (player.getStamina() > 0){
			batch.draw(staminaBar,925,53,40,7);
			if(player.getStamina() > 33){
				batch.draw(staminaBar,965,53,40,7);
				if(player.getStamina() > 66)
					batch.draw(staminaBar,1005,53,40,7);
			}
		}
	}

	public void render(float arg0) {

		updateGameScreen();
		drawGameElements();
	}
	/**
	 * The dispose method allows to dispose of any aspects that are done 
	 */
	public void dispose() {

		batch.dispose();	
		stalkMusic.dispose();
		chaseMusic.dispose();
		endGameMusic.dispose();
	}
	/**
	 * The isButtonPressed method updates the value of the buttons which helps to change the screens
	 */	
	public void isButtonPressed()
	{
		//Master Escape Button
		if(Gdx.input.isKeyPressed(Keys.ESCAPE))
		{
			chaseMusic.stop();
			stalkMusic.stop();
			endGameMusic.stop();
		}
		//Registered Any Mouse Input when the Game has Ended
		if(gameEnd)
		{
			if(Gdx.input.isButtonPressed(Buttons.LEFT)&& playAgainButton.isOver())
			{
				endGameMusic.stop();
				playAgainButton.setIsPressed(true);
			}

			else if(Gdx.input.isButtonPressed(Buttons.LEFT)&& returnMainMenuButton.isOver())
			{
				endGameMusic.stop();
				returnMainMenuButton.setIsPressed(true);
			}

			stalkMusic.stop();			
			chaseMusic.stop();
		}
	}
	/**
	 * The getCharacterNumber method returns the character now which indicates the chracter chosen
	 * @return The character number 
	 */
	public int getCharacterNumber()
	{
		return characterNo;
	}
	//List of Other Methods
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