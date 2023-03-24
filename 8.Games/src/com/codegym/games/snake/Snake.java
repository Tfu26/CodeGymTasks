package com.codegym.games.snake;

import com.codegym.engine.cell.*;

import java.util.ArrayList;
import java.util.List;

public class Snake {

    private static String HEAD_SIGN = "\u263A";
    private final static String BODY_SIGN = "\u26AB";

    private List<GameObject> snakeParts = new ArrayList<>();
    public boolean isAlive = true;
    private Direction direction = Direction.LEFT;

    public Snake(int x, int y) {
        snakeParts.add(new GameObject(x, y));
        snakeParts.add(new GameObject(x + 1, y));
        snakeParts.add(new GameObject(x + 2, y));
    }
    
    public int getLength(){
        return snakeParts.size();
    }
    
    public boolean checkCollision(GameObject gObj){
        for(GameObject parts : snakeParts){
            if(parts.x == gObj.x && parts.y == gObj.y){
                return true;
            }
        }
        return false;
    }

    public void draw(Game game) {
        Color color = isAlive ? Color.BLACK : Color.RED;
        if(!isAlive){
            HEAD_SIGN = "\u2639";
        }
        for (int i = 0; i < snakeParts.size(); i++) {
            GameObject part = snakeParts.get(i);
            String sign = (i != 0) ? BODY_SIGN : HEAD_SIGN;
            game.setCellValueEx(part.x, part.y, Color.NONE, sign, color, 75);
        }
    }

    public void setDirection(Direction direction) {
        
        if((this.direction == Direction.UP || this.direction == Direction.DOWN)
            && snakeParts.get(0).y == snakeParts.get(1).y){
            return;
        }
        
        if((this.direction == Direction.LEFT || this.direction == Direction.RIGHT)
            && snakeParts.get(0).x == snakeParts.get(1).x){
            return;
        }
        
        if((direction == Direction.LEFT && this.direction == Direction.RIGHT)
            || (direction == Direction.RIGHT && this.direction == Direction.LEFT)
            || (direction == Direction.UP && this.direction == Direction.DOWN)
            || (direction == Direction.DOWN && this.direction == Direction.UP))
            return;
        this.direction = direction;
    }
    
    public void move(Apple apple){
        GameObject newH = createNewHead();
        
        if(newH.x >= SnakeGame.WIDTH || newH.x < 0 ||
        newH.y >= SnakeGame.HEIGHT || newH.y < 0){
            isAlive = false;
            return;
        }
        if(checkCollision(newH)){
            isAlive = false;
            return;
        }
        snakeParts.add(0, newH);
        
        if(newH.x == apple.x && newH.y == apple.y){
            apple.isAlive = false;
        } else{
            removeTail();
        }
    }
    
    public GameObject createNewHead(){
        GameObject oldHead = snakeParts.get(0);
        if(direction == Direction.LEFT){
            return new GameObject(oldHead.x -1, oldHead.y);
        }else if(direction == Direction.RIGHT){
            return new GameObject(oldHead.x+1, oldHead.y);
        }else if(direction == Direction.UP){
            return new GameObject(oldHead.x, oldHead.y-1);
        }else{
            return new GameObject(oldHead.x, oldHead.y+1);
        }
    }
    
    public void removeTail(){
        snakeParts.remove(snakeParts.size() -1);
    }
}
