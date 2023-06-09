package com.codegym.games.snake;

import com.codegym.engine.cell.*;

public class SnakeGame extends Game {

    public static final int WIDTH = 15;
    public static final int HEIGHT = 15;
    
    private static final int GOAL = 30;
    
    private int score;
    
    private boolean isGameStopped;

    private Snake snake;
    private Apple apple;
    
    private int turnDelay;

    @Override
    public void initialize() {
        setScreenSize(WIDTH, HEIGHT);
        createGame();
    }
    
    @Override
    public void onTurn(int step){
        snake.move(apple);
        if(!apple.isAlive){
            createNewApple();
            score = score+5;
            setScore(score);
            turnDelay = turnDelay-10;
            setTurnTimer(turnDelay);
        }
        if(!snake.isAlive){
            gameOver();
        }
        if(snake.getLength() > GOAL){
            win();
        }
        drawScene();
    }
    
    @Override
    public void onKeyPress(Key key){
        if(key == Key.LEFT){
            snake.setDirection(Direction.LEFT);
        }else if(key == Key.RIGHT){
            snake.setDirection(Direction.RIGHT);
        }else if(key == Key.UP){
            snake.setDirection(Direction.UP);
        }else if(key == Key.DOWN){
            snake.setDirection(Direction.DOWN);
        }else if(key == Key.SPACE && isGameStopped == true){
            createGame();
        }
    }
    
    private void createNewApple(){
        Apple newApple;
        do{
            int x = getRandomNumber(WIDTH);
            int y = getRandomNumber(HEIGHT);
            newApple = new Apple(x,y);
        }
        
        while(snake.checkCollision(newApple));
        
        apple = newApple;
        
    }

    private void createGame() {
        score = 0;
        setScore(score);
        turnDelay = 300;
        setTurnTimer(turnDelay);
        snake = new Snake(WIDTH / 2, HEIGHT / 2);
        createNewApple();
        isGameStopped = false;
        drawScene();
    }
    
    
    private void drawScene() {
        for (int i = 0; i < WIDTH; i++) {
            for (int j = 0; j < HEIGHT; j++) {
                setCellValueEx(i, j, Color.MEDIUMSPRINGGREEN, "");
            }
        }
        snake.draw(this);
        apple.draw(this);
    }
    
    private void win(){
        stopTurnTimer();
        isGameStopped = true;
        showMessageDialog(Color.NONE, "YOU WIN", Color.GREEN, 50);
    }
    
    private void gameOver(){
        stopTurnTimer();
        isGameStopped = true;
        showMessageDialog(Color.NONE, "GAME OVER", Color.RED, 50);
    }
}
