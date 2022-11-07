/*
 * Todd J Myers
 * 06/3/20
 * CIS 1181 
 * Project 2: Build your own game 
 */

package project2;



import java.util.Random;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;


	@SuppressWarnings("unused")
	public class Game extends Application 
	
	{

	private enum User 
	
	{

	NONE, LEFT, RIGHT

	}

	private static final int W = 800;  // width 
	private static final int H = 600;   // height 
	private static final int B_RADIUS = 10;   // ball radius 
	private static final int B_W = 100;  // ball width 
	private static final int B_H = 20; // ball height 
	private Circle ball = new Circle(B_RADIUS);  // radius of the ball 
	private Rectangle paddle = new Rectangle(B_W, B_H);  // 
	private Rectangle enemypaddle = new Rectangle(300, 0, 200, B_H);  // enemy paddle size 
	private boolean b_Up = true, b_Left = false;   // ball up or left 
	private User action = User.NONE;
	private Timeline time = new Timeline();  //
	private boolean running = true;


    
    // score text 
    int scoreint = 0;
    Text score = new Text(10, 10, "Score: " + scoreint);
	
	
	private Parent createContent()
	
	{

	Pane root = new Pane();
	
// paddle color height width 
	root.setPrefSize(W, H);
	paddle.setTranslateX(W / 2);
	paddle.setTranslateY(H - B_H);
	paddle.setFill(Color.BLUE);    // color for user paddle 
	

	KeyFrame frame = new KeyFrame(Duration.seconds(0.016), event -> {

	if (!running)

	return;

	switch (action) {

	case LEFT:

	if (paddle.getTranslateX() - 5 >= 0)

		paddle.setTranslateX(paddle.getTranslateX() - 5);
		
		enemypaddle.setTranslateX(enemypaddle.getTranslateX()+ 5);
		
	break;

	case RIGHT:

	if (paddle.getTranslateX() + B_W + 5 <= W)

	paddle.setTranslateX(paddle.getTranslateX() + 5);
	
	enemypaddle.setTranslateX(enemypaddle.getTranslateX()- 5);
	
	
	break;

	case NONE:

	break;

	}

	ball.setTranslateX(ball.getTranslateX() + (b_Left ? -5 : 5));
	ball.setTranslateY(ball.getTranslateY() + (b_Up ? -5 : 5));

	if (ball.getTranslateX() - B_RADIUS == 0)

	b_Left = false;

	else if (ball.getTranslateX() + B_RADIUS == W)

	b_Left = true;

	if (ball.getTranslateY() - B_RADIUS == 0)

	b_Up = false;

	//paddle
	if (ball.getTranslateY() + B_RADIUS == H - B_H

	&& ball.getTranslateX() + B_RADIUS >= paddle.getTranslateX()
	&& ball.getTranslateX() - B_RADIUS <= paddle.getTranslateX() + B_W) 
	{
		
	b_Up = true;
	score.setText("Score: " + scoreint++);
	}
	
	
	if (ball.getTranslateY() + B_RADIUS == H)	
		
		
	restartGame();
	});
//adding items 
	time.getKeyFrames().add(frame);
	time.setCycleCount(Timeline.INDEFINITE);
	root.getChildren().addAll(ball, paddle);
	root.getChildren().addAll(score);
	//root.getChildren().addAll(menuButton);

	return root;

	}

	private void restartGame() 
	
	{

	stopGame();
	startGame();

	}

	private void stopGame() 
	
	{

	running = false;
	time.stop();

	}

	private void startGame()
	// holding score based on ball connections 
	{
	scoreint = 0;
	score.setText("Score: "  + scoreint);
	b_Up = true;
	ball.setTranslateX(W / 2);
	ball.setTranslateY(H / 2);
	time.play();
	running = true;
	}

	@SuppressWarnings("incomplete-switch")
	@Override
	public void start(Stage primaryStage) throws Exception
	{
		
		 Group groot = new Group();
	     VBox layoutMenu= new VBox(10);
	     layoutMenu.setPrefWidth(100);
		//Declaring scenes
        Scene menu = new Scene(layoutMenu,W,H);
	    Scene scene = new Scene(createContent());
	

    //Creating Buttons
    Button playGameButton= new Button("Play");
   // Button exitButton = new Button("Exit"); // not working properly does not exit program
    Button menuButton = new Button("Menu");
    
    // Adding items to the layoutMenu
    layoutMenu.getChildren().addAll(playGameButton);
    groot.getChildren().addAll(menuButton);   // menu button not being shown 
    
    


    //Align buttons and bar
    layoutMenu.setAlignment(Pos.CENTER);
    playGameButton.setMinWidth(layoutMenu.getPrefWidth());
  
  //  exitButton.setMinWidth(layoutMenu.getPrefWidth());
    menuButton.setAlignment(Pos.TOP_LEFT);

    layoutMenu.setStyle("-fx-background-color: #2C3539;");
    primaryStage.setScene(menu);
	
	scene.setOnKeyPressed(event -> {
// makes what keys to press to the user for the paddle 
	switch (event.getCode()) {

	case A:

	action = User.LEFT;

	break;

	case D:

	action = User.RIGHT;

	break;
	default:
		break;
		
	}

	});

	scene.setOnKeyReleased(event -> 
	{

	switch (event.getCode()) {

	case A:

	action = User.NONE;

	break;

	case D:

	action = User.NONE;

	break;
	}});

	
	//title
	primaryStage.setTitle(" Wall Pong");
	//Events of buttons
    playGameButton.setOnAction(e -> primaryStage.setScene(scene));
    menuButton.setOnAction(e -> primaryStage.setScene(menu));
	primaryStage.show();
	  // primaryStage.setScene(menu);
	startGame();
	}

	public static void main(String[] args)
	{
		
	launch(args);
	
	} }

