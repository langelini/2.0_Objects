//Basic Game Application
//Version 2
// Basic Object, Image, Movement
// Astronaut moves to the right.
// Threaded

//K. Chun 8/2018

//*******************************************************************************
//Import Section
//Add Java libraries needed for the game
//import java.awt.Canvas;

//Graphics Libraries
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;
import java.awt.*;
import javax.swing.JFrame;
import javax.swing.JPanel;


//*******************************************************************************
// Class Definition Section

//step 1: add keylistner to implements
public class BasicGameApp implements Runnable, KeyListener {

   //Variable Definition Section
   //Declare the variables used in the program 
   //You can set their initial values too
   
   //Sets the width and height of the program window
	final int WIDTH = 1000;
	final int HEIGHT = 700;

	//Declare the variables needed for the graphics
	public JFrame frame;
	public Canvas canvas;
   public JPanel panel;
   
	public BufferStrategy bufferStrategy;
	public Image astroPic;
	public Image astro2pic;
	public Image backgroundpic;

   //Declare the objects used in the program
   //These are things that are made up of more than one variable type
	private Astronaut astro;
	private Astronaut astro2;


   // Main method definition
   // This is the code that runs first and automatically
	public static void main(String[] args) {
		BasicGameApp ex = new BasicGameApp();   //creates a new instance of the game
		new Thread(ex).start();                 //creates a threads & starts up the code in the run( ) method  
	}


   // Constructor Method
   // This has the same name as the class
   // This section is the setup portion of the program
   // Initialize your variables and construct your program objects here.
	public BasicGameApp() {
      
      setUpGraphics();
       
      //variable and objects
      //create (construct) the objects needed for the game and load up 
		astroPic = Toolkit.getDefaultToolkit().getImage("astro.jpg");
		astro2pic = Toolkit.getDefaultToolkit().getImage("astronaut.png");
		backgroundpic = Toolkit.getDefaultToolkit().getImage("");
		astro = new Astronaut(20,200);
		astro2 = new Astronaut(100,150);



	}// BasicGameApp()

   
//*******************************************************************************
//User Method Section
//
// put your code to do things here.

   // main thread
   // this is the code that plays the game after you set things up
	public void run() {

      //for the moment we will loop things forever.
		while (true) {

         moveThings();  //move all the game objects
         render();  // paint the graphics
         pause(20); // sleep for 10 ms
		}
	}


	public void moveThings()
	{
      //calls the move( ) code in the objects
		collision();
		astro.wrap();
		astro2.wrap();

	}

	public void collision(){
		if(astro.rec.intersects(astro2.rec) && astro.crash == false && astro2.isAlive == true && astro.isAlive == true){
			System.out.println("splat");
			astro.isAlive = false;
			astro.dx=-astro.dx;
			astro.dy = -astro.dy;
			astro2.dx=-astro2.dx;
			astro2.dy = -astro2.dy;
			astro.dx=astro.dx*2;
			astro.dy=astro.dy*2;
			astro2.width=astro2.width+10;
			astro2.height=astro2.height+1;
			astro.crash = true;
		}
		if(!astro.rec.intersects(astro2.rec)){
			astro.crash = false;
		}
	}

	
   //Pauses or sleeps the computer for the amount specified in milliseconds
   public void pause(int time ){
   		//sleep
			try {
				Thread.sleep(time);
			} catch (InterruptedException e) {
			}
   }

   //Graphics setup method
   private void setUpGraphics() {
      frame = new JFrame("Application Template");   //Create the program window or frame.  Names it.
   
      panel = (JPanel) frame.getContentPane();  //sets up a JPanel which is what goes in the frame
      panel.setPreferredSize(new Dimension(WIDTH, HEIGHT));  //sizes the JPanel
      panel.setLayout(null);   //set the layout
   
      // creates a canvas which is a blank rectangular area of the screen onto which the application can draw
      // and trap input events (Mouse and Keyboard events)
      canvas = new Canvas();  
      canvas.setBounds(0, 0, WIDTH, HEIGHT);
      canvas.setIgnoreRepaint(true);
   // step 2: add key listner to canvas as this
	   canvas.addKeyListener(this);
      panel.add(canvas);  // adds the canvas to the panel.
   
      // frame operations
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  //makes the frame close and exit nicely
      frame.pack();  //adjusts the frame and its contents so the sizes are at their default or larger
      frame.setResizable(false);   //makes it so the frame cannot be resized
      frame.setVisible(true);      //IMPORTANT!!!  if the frame is not set to visible it will not appear on the screen!
      
      // sets up things so the screen displays images nicely.
      canvas.createBufferStrategy(2);
      bufferStrategy = canvas.getBufferStrategy();
      canvas.requestFocus();
      System.out.println("DONE graphic setup");
   
   }


	//paints things on the screen using bufferStrategy
	private void render() {
		Graphics2D g = (Graphics2D) bufferStrategy.getDrawGraphics();
		g.clearRect(0, 0, WIDTH, HEIGHT);
		g.drawImage(backgroundpic,0,0, WIDTH, HEIGHT, null);
		g.drawImage(astroPic, astro.xpos, astro.ypos, astro.width, astro.height, null);
		if(astro.isAlive == true && astro2.isAlive == true) {
			g.drawImage(astro2pic, astro2.xpos, astro2.ypos, astro2.width, astro2.height, null);
		}
		g.dispose();
		bufferStrategy.show();
	}


	@Override
	public void keyTyped(KeyEvent e) {//dont touch

	}

	@Override
	public void keyPressed(KeyEvent e) {
		System.out.println("key pressed");
		System.out.println(e.getKeyChar());
		System.out.println(e.getKeyCode());

		if(e.getKeyCode() == 38){
			System.out.println("going up");
			astro.up = true;

		}
		if(e.getKeyCode() == 37){
			System.out.println("going left");
			astro.left = true;


		}
		if(e.getKeyCode() == 40){
			System.out.println("going down");
			astro.down = true;

		}
		if(e.getKeyCode() == 39){
			System.out.println("going right");
			astro.right = true;
//
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		if(e.getKeyCode() == 38){
			System.out.println("not going up");
			astro.up = false;

		}
		if(e.getKeyCode() == 37){
			System.out.println("not going left");
			astro.left = false;


		}
		if(e.getKeyCode() == 40){
			System.out.println("not going down");
			astro.down = false;

		}
		if(e.getKeyCode() == 39){
			System.out.println("not going right");
			astro.right = false;
//
		}


    }
}