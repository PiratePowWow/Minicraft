package com.theironyard.minicraft;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Minicraft extends ApplicationAdapter {
    final int WIDTH = 40;
    final int HEIGHT = 40;
    static final float MAX_VELOCITY = 175;

    SpriteBatch batch;
    TextureRegion down, up, right, left, direction, upReversed, downReversed;
    Animation walkingUp, walkingDown;

    float position;
    float x = 0;
    float y = 0;
    float xv, yv;
    float time;
    float timeLastMoved = 0;
    boolean canMove = true;

    @Override
    public void create () {
        batch = new SpriteBatch();

        Texture tiles = new Texture("tiles.png");
        TextureRegion[][] grid = TextureRegion.split(tiles, 16, 16);
        down = grid[6][0];
        up = grid[6][1];
        right = grid[6][3];
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

    @Override
    public void render () {
        time += Gdx.graphics.getDeltaTime();

        move();

        //Sets walking animation
        if (yv>0) {
            direction = walkingUp.getKeyFrame(time, true);
        }
        else if (yv < 0) {
            direction = walkingDown.getKeyFrame(time,true);
        }
        //End walking animation

        Gdx.gl.glClearColor(0, 0.5f, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        batch.draw(direction, x, y, WIDTH, HEIGHT);
        batch.end();
    }

    void move(){
        //Else if statements allow character to only move in one direction at a time
        if (canMove) {
            if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
                position = y;
                timeLastMoved = time;
                yv = MAX_VELOCITY;
                direction = up;
                canMove = !canMove;
            }
            else if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
                position = y;
                timeLastMoved = time;
                yv = -1*MAX_VELOCITY;
                direction = down;
                canMove = !canMove;
            }
            else if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
                position = x;
                xv = -1*MAX_VELOCITY;
                timeLastMoved = time;
                direction = left;
                canMove = !canMove;
            }
            else if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
                position = x;
                timeLastMoved = time;
                xv = MAX_VELOCITY;
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
