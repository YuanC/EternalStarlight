
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.WindowConstants;


public class menuTest {
	public static void main(String args[]){
		myFrame f = new myFrame();
		
	}
}

//Main frame holding all panels includeing gamepanel menupanel mappanel
class myFrame extends JFrame {
	
	//Declarations, most of these are static so they can be accessed by panels to deal with switching between panels
	static Container c;
	static menuPanel main;
	loadPanel load;
	static GamePanel game;
	static mapPanel map;
	
	myFrame(){
		setLayout(new BorderLayout());
		setSize(new Dimension(1280, 720));
		setResizable(false);
		
		
		
		c = getContentPane();
		//loading screen
		load = new loadPanel();
		c.add(load,BorderLayout.CENTER);
		setVisible(true);
		//load menupanel and map during loading screen
		main = new menuPanel();
		map = new mapPanel();
		//After menupanel is done loading remove loading screen and start menu screen
		c.remove(load);
		c.add(main,BorderLayout.CENTER);
		
		
		
		
		setVisible(true);
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		
	}

}

//Menu Panel added to main frame, selection between characters, load(not working), and exit
class menuPanel extends JPanel implements ActionListener, KeyListener{
	@SuppressWarnings("unchecked")	//suppresses warnings
	
	Clip clip; //music
	public static Timer myTimer;
	BufferedImage[] bg = new BufferedImage[171];
	BufferedImage current,currentMenu,currentChar;
	BufferedImage startPICK,startNOT,loadPICK,loadNOT,exitPICK,exitNOT,galacticianNOT,starchildNOT;
	ImageIcon galacticianPICK,starchildPICK;
	int bgIndex=0;
	int menuIndex1=0, menuIndex2=0;
	boolean enter = false;

	menuPanel(){
		setLayout(null);  //Personal layout manager
		setPreferredSize(new Dimension(1280, 720));
		try{ //Import all the pictures for the animations
			for (int i = 1; i<171; i++){
				bg[i-1]=ImageIO.read(new File("BgJpeg\\bg"+i+".jpg"));
			}
			
			//Image importation
			startPICK = ImageIO.read(new File("Characters\\Select\\startPICK.png"));
			startNOT = ImageIO.read(new File("Characters\\Select\\startNOT.png"));
			loadPICK = ImageIO.read(new File("Characters\\Select\\loadPICK.png"));
			loadNOT = ImageIO.read(new File("Characters\\Select\\loadNOT.png"));
			exitPICK = ImageIO.read(new File("Characters\\Select\\exitPICK.png"));
			exitNOT = ImageIO.read(new File("Characters\\Select\\exitNOT.png"));
			galacticianPICK = (new ImageIcon("Characters\\Select\\galacticianSelect.gif"));
			galacticianNOT = ImageIO.read(new File("Characters\\Select\\galacticianNOT.png"));
			starchildPICK = new ImageIcon("Characters\\Select\\starchildSelect.gif");
			starchildNOT = ImageIO.read(new File("Characters\\Select\\starchildNOT.png"));
			
		}catch (Exception e){
			System.out.println("didn't work boys");
		}
		
		try {
			LoopSound();
		} catch (Exception e) {
			System.out.println("Didnt work boys");
		}
		
		//myFrame.map = new mapPanel();
		
		current = bg[bgIndex];
		
		//setOriginal Main menu pictures
		
		addKeyListener(this);
		setFocusable(true);
		
		myTimer = new Timer(50,this);
		myTimer.start();
		
		repaint();
		
		
		
	}
	
	public void actionPerformed(ActionEvent e){
		if (e.getSource().equals(myTimer)){  //runs the background
			if (bgIndex==170){
				bgIndex=0;
			}else
				bgIndex++;
			current = bg[bgIndex];
			
			this.requestFocus();
			
			repaint();
		}
	}
	
	//changes menu index number, which changes the image being drawn for each menu item
	public void keyPressed(KeyEvent k){
		
		if (k.getKeyCode()==(KeyEvent.VK_DOWN) && enter){
			if (menuIndex2!=1)
				menuIndex2+=1;
		}
		
		else if (k.getKeyCode()==(KeyEvent.VK_UP) && enter){
			if (menuIndex2!=0)
				menuIndex2-=1;
		}
		
		
		else if (k.getKeyCode()==(KeyEvent.VK_UP)){
			if (menuIndex1!=0)
				menuIndex1-=1;
			else
				menuIndex1=2;
		}
		
		
		else if (k.getKeyCode()==(KeyEvent.VK_DOWN)){
			if (menuIndex1!=2)
				menuIndex1+=1;
			else
				menuIndex1=0;
		}
		
		else if (k.getKeyCode()==(KeyEvent.VK_ENTER)&&enter){
			setVisible(false);
			myTimer.stop();
			myFrame.c.add(myFrame.map,BorderLayout.CENTER);
			myFrame.c.remove(this);
		}
		
	}
	
	
	//Checking if enter is pressed to go to that specific function, or exit.
	public void keyReleased(KeyEvent k){
		if(k.getKeyCode()==(KeyEvent.VK_ENTER)){
			enter=true;
			if (menuIndex1==2){
				System.exit(0);
			}
		}else if (k.getKeyCode()==(KeyEvent.VK_ESCAPE)){
			enter=false;
		}
	}

	public void keyTyped(KeyEvent k) {
		
	}
	
	//Override
	public void paintComponent(Graphics g){
		g.drawImage(current,0,0,this); //Draws the background
		menuSelect(g); //draws the menu buttons
		if (menuIndex1==0) //draws the character selection buttons
			charSelect(g);
	}
	
	//Changes menu button selection
	public void menuSelect(Graphics g){
		if (menuIndex1==0){
			g.drawImage(startPICK,50, 100,this);
			g.drawImage(loadNOT,50, 300,this);
			g.drawImage(exitNOT,50,500,this);
		}else if(menuIndex1==1){
			g.drawImage(startNOT,50,100,this);
			g.drawImage(loadPICK,50,300,this);
			g.drawImage(exitNOT,50,500,this);
		}else if(menuIndex1==2){
			g.drawImage(startNOT,50,100,this);
			g.drawImage(loadNOT,50,300,this);
			g.drawImage(exitPICK,50,500,this);
		}
	}
	
	//changes character selection
	public void charSelect(Graphics g){
		Graphics2D g2d = (Graphics2D) g.create();
		
		//If etner was pressed on character selection...
		if (menuIndex2==0&&enter){
			galacticianPICK.paintIcon(this, g2d,200,140);
			g.drawImage(starchildNOT,200,242,this);
		}else if(menuIndex2==1&&enter){
			starchildPICK.paintIcon(this,g2d,200,242);
			g.drawImage(galacticianNOT,200,140,this);
		}
	}
	
	//Music
	public void LoopSound() throws Exception {
        File bgMenu = new File("Music\\menuMusic.wav");
        clip = AudioSystem.getClip();
        AudioInputStream yes = AudioSystem.getAudioInputStream( bgMenu );
        clip.open(yes);
        clip.loop(Clip.LOOP_CONTINUOUSLY);
    }
}

//Loading screen while menupanel loads all the pictures
class loadPanel extends JPanel{
	
	ImageIcon loading;
	
	loadPanel(){
		try{
			loading = new ImageIcon("Characters\\Select\\loading.gif");
		}catch(Exception e){
			System.out.println("didnt work boys");
		}
	}
	
	//ovaride
	public void paintComponent(Graphics g){
		g.drawImage(loading.getImage(), 0, 0, this);
	}
}




class mapPanel extends JPanel implements KeyListener, ActionListener, MouseListener{
	
	//variable declaration
	enemy temp;
	Timer mapTimer;
	BufferedImage starchildUPSTAND, starchildDOWNSTAND, starchildLEFTSTAND, starchildRIGHTSTAND,tabPIC;
	BufferedImage desolationBG;
	BufferedImage photoCutscene;
	Image current;
	ImageIcon starUP,starDOWN,starLEFT,starRIGHT;
	static int x,y;
	int key,keyUP;
	
	ArrayList<enemy> enemyArray = new ArrayList<enemy>();
	
	int cameraX,cameraY;
	int mouseX,mouseY;
	
	//spawnlocations
	int slimeSpawn[] = {1440,1624,3150,2190};
	int golemSpawn[] = {15,607,1311,800};
	int dragonSpawn[] = {3560,1445,3561,1446};
	int alpha = 0, alpha2=255;
	//stats
	public static int strength = 1, intellect = 1, agility = 1,ap=50;
	
	
	double loopcount = 0;//counts executions for storyline text
	
	boolean stillCameraX = false, stillCameraY = false;
	boolean up,down,left,right,c;
	boolean intersection = false;
	boolean fadeTo = true, fadeFrom=false,fadeText=false,fadeText2=true;
	boolean photo;
	boolean photoDropped;
	
	mapPanel(){
		
		//Image loading
		try{
			photoCutscene = ImageIO.read(new File("Characters\\Level\\family.jpg"));
			starchildUPSTAND = ImageIO.read(new File("Characters\\starchildWalk\\starchildUP.png"));
			starchildDOWNSTAND = ImageIO.read(new File("Characters\\starchildWalk\\starchildDOWN.png"));
			starchildRIGHTSTAND = ImageIO.read(new File("Characters\\starchildWalk\\starchildRIGHT.png"));
			starchildLEFTSTAND = ImageIO.read(new File("Characters\\starchildWalk\\starchildLEFT.png"));
			starUP = (new ImageIcon("Characters\\starchildWalk\\up.gif"));
			starDOWN = (new ImageIcon("Characters\\starchildWalk\\down.gif"));
			starRIGHT =(new ImageIcon("Characters\\starchildWalk\\right.gif"));
			starLEFT = (new ImageIcon("Characters\\starchildWalk\\left.gif"));
			tabPIC = ImageIO.read(new File("Characters\\finishedTAB.png"));
			desolationBG = ImageIO.read(new File("Characters\\Level\\desolationBG.jpg"));
			
		}catch (Exception e){
			System.out.println("didn't work boys");
		}
		
		current = starchildDOWNSTAND; //setting default
		setLayout(null);  //Personal layout manager, but only one panel anyway
		setPreferredSize(new Dimension(1280, 720));
		setVisible(true);
		addKeyListener(this);
		setFocusable(true);
		addMouseListener(this);
		
		
		//Generating All enemies
		for (int i = 0; i<20; i++){
			enemyArray.add(new slime(1,slimeSpawn));
		}
		
		for (int i = 20; i<30; i++){
			enemyArray.add(new golem(2,golemSpawn));
		}
		
		enemyArray.add(new bossDragon(3, dragonSpawn));
		enemyArray.add(new photoGolem(2, golemSpawn));
		
		x=430;
		y=1960;
		
		mapTimer = new Timer(20,this);
		mapTimer.start();
	}
	
	public void cameraUpdate(){
		//Setting the character the center of the screen unless camera is on edge and limiting camera x,y
		
		cameraY = y-360;
		cameraX = x-640;
		
		if (x<640){
			cameraX = 0;
			stillCameraX=true;
		}
		else if (x>3360){
			cameraX = 2720;
			stillCameraX=true;
		}else
			stillCameraX=false;
		
		
		if (y<360){
			cameraY = 0;
			stillCameraY=true;
		}else if (y>1910){
			cameraY = 1550; 
			stillCameraY=true;
		}else
			stillCameraY=false;

	}

	public void playerUpdate(Graphics g){
		
		//general character nagivation around maps
		
		if (loopcount>600){
			if (up){
				y-=2;
				current = starUP.getImage();
			}
			if (down){
				y+=2;
				current = starDOWN.getImage();
			}
			if (left){
				x-=2;
				current = starLEFT.getImage();
			}
			if (right){
				x+=2;
				current = starRIGHT.getImage();
			}
		}
		//makes it so the character doesn't go out of bounds;
		if (x<0)
			x=0;
		if (x>3950)
			x=3950;
		
		//line boundary
		if (y<420)
			y = 420;
		if (y>2190)
			y = 2190;
		
		
		//diagonal line boundary for mountains using equation of a line
		if (x>(int)((y+179)/0.6) && y<(int)(0.6*x)-179 && x<2655){
			x = (int)((y+179)/0.6);
			y=(int)(0.6*x)-179;
		}
		
		
		
		//Text prompts
		if (y<620)
			text(g,"The stars are beautiful...");
			
		
		
		//line boundary
		
		if (x>=2655 && y<1414)
			y = 1414;
		
		//Box boundary around boss unless photo was already dropped
		if (y<1700&&x>3480 && !photoDropped){
			y=1700;
			x=3480;
		}
		
		//promting instructions around that area
		if (y<2200&&x>3350 &&!photoDropped){
			text(g,"Psh that looks too dangerous, no point putting myself in danger.");
		}
		
		
		//lets the character continue changing x,y walk but camera stop moving
		
		//if both x and y camera are still etc...
		if (stillCameraX && stillCameraY && x<1000 && y<1000)
			g.drawImage(current,x,y,this);
	
		else if (stillCameraX && stillCameraY && x>1000 && y>1000)
			g.drawImage(current,Math.abs(cameraX-x),Math.abs(y-cameraY),this);
		
		else if (stillCameraX && stillCameraY && x<1000 && y>1000)
			g.drawImage(current,x,Math.abs(y-cameraY),this);
		
		else if (stillCameraX && stillCameraY && x>1000 && y<1000)
			g.drawImage(current,Math.abs(cameraX-x),y,this);
		
		else if (stillCameraX && !stillCameraY && x<1000)
			g.drawImage(current,x,360,this);
		
		else if (stillCameraX && !stillCameraY && x>1000)
			g.drawImage(current,Math.abs(cameraX-x),360,this);
		
		else if (!stillCameraX && stillCameraY && y<1000)
			g.drawImage(current,640,y,this);
		
		else if (!stillCameraX && stillCameraY && y>1000)
			g.drawImage(current,640,Math.abs(y-cameraY),this);
		
		else
			g.drawImage(current,640,360,this);
		
	}
	
	
	//character stat page
	public void characterPage (Graphics g){
		g.drawImage(tabPIC,280,120,this);
		System.out.println(mouseX + " " + mouseY);
		
		g.setColor(Color.WHITE);
		g.drawString(strength+"",450,545);
		g.drawString(intellect+"",685,545);
		g.drawString(agility+"",925,541);
		g.drawString(ap+"",463,481);
		
		
		
	}
	
	public void enemyUpdate(Graphics g){
		//updating and drawing enemy characters only if it isn't during cutscenes
		
		if (!photo){
			for (enemy en: enemyArray){
				en.random(); //random direction
				en.travel(); //changes x,y
					
				//only paints sprite if it is in the camera range.
				if (en.x>cameraX && en.x<cameraX+1300 && en.y+100>cameraY && en.y<cameraY+800){
					g.drawImage(en.current.getImage(),en.x-cameraX,en.y-cameraY,this);
				}
			}
		}
	}
	
	public void collides(){
		//collison check obv
		for (enemy e:enemyArray){
			if (new Rectangle(x,y,38,52).intersects(new Rectangle(e.x+10,e.y+10,e.width-20,e.height-20))){
				
				
				temp = e;
				intersection = true;
				fadeTo=true;
			}
		}
		
		
	}
	
	//Text printer
	public void text(Graphics g,String s){
		g.setFont(new Font("Helvetica", Font.PLAIN, 18));
		g.setColor(Color.YELLOW);
		g.drawString(s, x-cameraX-100,y-cameraY);
	}
	
	//fades text out
	public void fadeText(Graphics g){
		System.out.println(alpha2);
		g.setFont(new Font("Tahoma", Font.PLAIN, 50));
		g.setColor(new Color(255,255,255,alpha2));
		g.drawString("\"Desolation\"",540 , 100);
	}
	
	//fades text in
	public void fadeText2(Graphics g){
		System.out.println(alpha);
		g.setFont(new Font("Tahoma", Font.PLAIN, 50));
		g.setColor(new Color(255,255,255,alpha));
		g.drawString("\"Desolation\"",540 , 100);
	}
	
	public void paintComponent(Graphics g){
		//background
		
		//Fades to and from black
		if (fadeTo){
			if (photo){
				g.drawImage(photoCutscene,0,0,this);
			}
			alpha ++;
			fadeToBlack(g);
			
			//if the alpha value is too high reset it for next fade animation
			if (alpha>254){
				System.out.println(alpha);
				fadeTo=false;
				alpha=0;
				fadeFrom=true;
				photo=false;
				
				//If intersected, fade to black and start gamepanel and remove the enemy that was encountered.
				if (intersection){
					setVisible(false);
					mapTimer.stop();
					myFrame.c.removeAll();
					try {
						myFrame.game = new GamePanel(strength,intellect,agility,temp.type);
						myFrame.c.add(myFrame.game,BorderLayout.CENTER);
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
				
				
				//Unless it is the photogolem remove the enemy
				if (intersection && !(temp instanceof photoGolem)){
					enemyArray.remove(temp);
					intersection = false;
				}if (intersection && (temp instanceof photoGolem)){
					enemyArray.remove(temp);
					photoDropped = true;
					photo = true;
					intersection = false;
					fadeTo = true;
				}
				
			}
		}
		else if (fadeFrom){
			//putting the picture in the background so the fade will appear with it
			if (photo){
				g.drawImage(photoCutscene,0,0,this);
			}else{
				g.drawImage(desolationBG,-cameraX,-cameraY,this);
		
				enemyUpdate(g);
				playerUpdate(g);
				if (c){
					characterPage(g);
				}
			}

			
			
			alpha2 --; 
			fadeFromBlack(g);
			
			//alpha too low reset it for next fade animation
			if (alpha2<1){
				fadeFrom=false;
				alpha2=255;
			}
		}
		
		
		else{
			
			
			loopcount++;
			//System.out.println(loopcount);

			
			
			
			//background
			g.drawImage(desolationBG,-cameraX,-cameraY,this);
	
			enemyUpdate(g);
			playerUpdate(g);
			if (c){
				characterPage(g);
			}
			
			
			
			
			//First text lines, only allows character to move after the cutscene.
			if (loopcount<200){
				text(g, "ww..ww.where am I? My SHIP?! I'M STRANDED!");
				if (loopcount%2==0)
					x++;
				else
					x--;
			}
			else if(loopcount<400)
				text(g, "Mm..my family... friends? They're all g..gone?!");
			else if (loopcount<600)
				text(g, "Whatever, no one cares about me anyway.");
			
			
			else{ //same fading idea except for text
				if (fadeText2){
					alpha ++;
					fadeText2(g);
					
					if (alpha>254){
						fadeText2=false;
						alpha = 0;
						fadeText=true;
					}
				}
				
				if (fadeText){
					alpha2 --;
					fadeText(g);
					
					if (alpha2<1){
						fadeText=false;
						alpha2=255;
					}
				}
			}

		}
		
	}
	
	//fade  to black
	public void fadeToBlack(Graphics g){
		g.setColor(new Color(0,0,0,alpha));
		g.fillRect(0,0,1280,720);
	}
	
	
	//fade screen from black
	public void fadeFromBlack(Graphics g){
		g.setColor(new Color(0,0,0,alpha2));
		g.fillRect(0,0,1280,720);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource().equals(mapTimer)){
			//System.out.println(cameraX+ " " + cameraY + " " + x + " " + y + " " + stillCameraX + " " + stillCameraY);
			cameraUpdate();
			collides();
			repaint();
			this.requestFocus();
			
		}
	}

	@Override//Changes movement direction with booleans and then changes movement sprite picture
	public void keyPressed(KeyEvent k) {
		key = k.getKeyCode();
		
		if (key==KeyEvent.VK_UP)
			up = true;
		else if (key==KeyEvent.VK_DOWN)
			down = true;
		else if (key==KeyEvent.VK_LEFT)
			left = true;
		else if (key==KeyEvent.VK_RIGHT)
			right = true;
		else if (key==KeyEvent.VK_C){
			c = true;
		}
	}

	//Changes movement direction with booleans and then changes movement sprite picture
	@Override
	public void keyReleased(KeyEvent k) {
		keyUP = k.getKeyCode();
		if (keyUP==KeyEvent.VK_UP){
			up = false;
			current = starchildUPSTAND;
		}
		else if (keyUP==KeyEvent.VK_DOWN){
			down = false;
			current = starchildDOWNSTAND;
		}
		else if (keyUP==KeyEvent.VK_LEFT){
			left = false;
			current = starchildLEFTSTAND;
		}
		else if (keyUP==KeyEvent.VK_RIGHT){
			right = false;
			current = starchildRIGHTSTAND;
		}else if (keyUP==KeyEvent.VK_C){
			c = false;
		}
	}

	@Override
	public void keyTyped(KeyEvent k) {
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void mousePressed(MouseEvent e) {
		mouseX = e.getX();
		mouseY = e.getY();
		//strength
		if (mouseX > 435 && mouseY >530 && mouseX < 470 && mouseY < 575 && ap>0 &&c){
			strength++;
			ap--;
		}
		//Intellect
		if (mouseX > 675 && mouseY >530 && mouseX < 710 && mouseY < 575 && ap>0&&c) {
			intellect++;
			ap--;
		}
		//Agility
		if (mouseX > 915 && mouseY >530 && mouseX < 960 && mouseY < 575 && ap>0 &&c) {
			agility++;
			ap--;
		}
	}


	@Override
	public void mouseReleased(MouseEvent e) {
	}
	
	
	
}

//parent class of all enemies in the game
class enemy{
	int loopcounter=0; //used to change random movement after a specific delay
	int type;
	int x,y,width,height;
	int random;
	int[] spawnArea;
	public ImageIcon up,down,left,right;
	public ImageIcon current;
	enemy(int type, int[] spawnArea){
		this.spawnArea = spawnArea;
		this.type = type;
		try{
			current = new ImageIcon("Characters\\sprites\\slime1.png");
		}catch(Exception e){
			System.out.println("Didn't work boys");
		}
		
		spawnAreaSet();
	}
	
	//Random Travel generator
	public void travel(){
		
		if (random == 1)
			x++;
		if (random ==2 )
			x--;
		if (random == 3)
			y++;
		if (random == 4)
			y--;
		if (random == 5){
			x++;
			y++;
		}if (random == 6){
			x++;
			y--;
		}if (random == 7){
			x--;
			y++;
		}if (random == 8){
			x--;
			y--;
		}
		
		
		
		if (x<0)
			x=0;
		if (x>3950)
			x=3950;
		
		//line boundary
		if (y<420)
			y = 420;
		if (y>2190)
			y = 2190;
		
		
		//diagonal line boundary for mountains
		if (x>(int)((y+179)/0.6) && y<(int)(0.6*x)-179 && x<2655){
			x = (int)((y+179)/0.6);
			y=(int)(0.6*x)-179;
		}
		
		//line boundary
		
		if (x>=2655 && y<1414)
			y = 1414;
		
		
	}
	
	//choices a random number to generate random movement every 50 ticks of the loop
	public void random(){
		
		loopcounter++;
		if (loopcounter>50){
			random = (int)(Math.random()*9);
			loopcounter=0;
		}
	}
	
	//setting spawn area for each type of enemy.
	public void spawnAreaSet(){
		x = (int)((Math.random()*(spawnArea[2]-spawnArea[0])) + spawnArea[0]);
		y = (int)((Math.random()*(spawnArea[3]-spawnArea[1])) + spawnArea[1]);
	}
	
	
}


//slime enemy
class slime extends enemy{
	slime(int type, int[] spawnArea){
		super(type, spawnArea);
		width = 50;
		height = 50;
		

	}
	
}


//golem enemy
class golem extends enemy{
	
	golem(int type, int[] spawnArea){
		super(type, spawnArea);
		
		width = 100;
		height = 83;
		
		try{
			current = new ImageIcon("Characters\\sprites\\golem.png");
		}catch(Exception e){
			System.out.println("Didn't work boys");
		}
	}
	
}

//specific golem with the photo frame
class photoGolem extends golem{
	
	photoGolem(int type, int[] spawnArea){
		super(type, spawnArea);

		try{
			current = new ImageIcon("Characters\\sprites\\golemItem.png");
		}catch(Exception e){
			System.out.println("Didn't work boys");
		}
	}
	
}

//boss dragon enemy
class bossDragon extends enemy{
	
	bossDragon(int type, int[] spawnArea){
		super(type, spawnArea);
		width = 143;
		height = 140;
		try{
			current = new ImageIcon("Characters\\sprites\\dragon.gif");
		}catch(Exception e){
			System.out.println("Didn't work boys");
		}
	}
	
	//Ovverride main travel and makes it stationary.
	public void travel(){
		
	}
}
