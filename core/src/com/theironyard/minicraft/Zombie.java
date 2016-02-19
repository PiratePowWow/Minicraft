package com.theironyard.minicraft;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import java.util.Random;
import java.util.stream.IntStream;


/**
 * Created by PiratePowWow on 2/19/16.
 */
public class Zombie {
    TextureRegion down, up, right, left, direction, upReversed, downReversed;
    Animation walkingUp, walkingDown;

    float position;
    float x = 0;
    float y = 0;
    float xv, yv;
    float time;
    boolean canMove = true;
    Random randomDir = new Random();
    int dir;

    public void create () {
        Texture tiles = new Texture("tiles.png");
        TextureRegion[][] grid = TextureRegion.split(tiles, 16, 16);
        down = grid[6][4];
        up = grid[6][5];
        right = grid[6][7];
        left = new TextureRegion(right);
        left.flip(true, false);
        upReversed = new TextureRegion(up);
        upReversed.flip(true, false);
        direction = down;
        walkingUp = new Animation(0.1f, up, upReversed );
        downReversed = new TextureRegion(down);
        downReversed.flip(true, false);
        walkingDown = new Animation(0.1f, down, downReversed);
    }
    void move(){
        //Else if statements allow character to only move in one direction at a time
        if (canMove) {
            dir = randomDir.nextInt(4);
            if ( dir == 0) {
                position = y;
                yv = Minicraft.MAX_VELOCITY;
                direction = up;
                canMove = !canMove;
            }
            else if (dir == 1) {
                position = y;
                yv = -1*Minicraft.MAX_VELOCITY;
                direction = down;
                canMove = !canMove;
            }
            else if (dir == 2) {
                position = x;
                xv = -1*Minicraft.MAX_VELOCITY;
                direction = left;
                canMove = !canMove;
            }
            else if (dir == 3) {
                position = x;
                xv = Minicraft.MAX_VELOCITY;
                direction = right;
                canMove = !canMove;
            }
        }
        // This section teleports the player to the opposite side
        if (x < 0){
            x = 600;
        }
        if (x > 600){
            x = 0;
        }
        if (y < 0) {
            y = 440;
        }
        if (y > 440){
            y = 0;
        }
        //This section stops the player movement if the player has moved 40 pixels, resets canMove back to true
        if (((Math.abs(xv) > 0) && (Math.abs(position - x) >= 40)) || ((Math.abs(yv) > 0) && (Math.abs(position - y) >= 40))){
            if (xv > 0){
                x = position + 40;
            }else if (xv < 0){
                x = position - 40;
            }else if (yv > 0){
                y = position + 40;
            }else if (yv < 0){
                y = position - 40;
            }
            yv = 0;
            xv = 0;
            canMove = true;
        }
        //This is the movement of the character
        x += xv*Gdx.graphics.getDeltaTime();
        y += yv*Gdx.graphics.getDeltaTime();
    }
}
