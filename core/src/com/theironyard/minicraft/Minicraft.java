package com.theironyard.minicraft;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Minicraft extends ApplicationAdapter {
    final int WIDTH = 40;
    final int HEIGHT = 40;
    static final float MAX_VELOCITY = 175;

    SpriteBatch batch;
    TextureRegion down, up, right, left, direction;

    float position;
    float x = 0;
    float y = 0;
    float xv, yv;
    float time;
    float timeLastMoved = 0;
    boolean walkingUpOrDown = true;
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
        direction = down;
    }

    @Override
    public void render () {
        time += Gdx.graphics.getDeltaTime();

        move();

        Gdx.gl.glClearColor(0, 0.5f, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        batch.draw(direction, x, y, WIDTH, HEIGHT);
        batch.end();
    }

    void move(){

        if (canMove) {
            if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
                position = y;
                timeLastMoved = time;
                yv = MAX_VELOCITY;
                walkingUpOrDown = true;
                direction = up;
                canMove = !canMove;
            }
            else if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
                position = y;
                timeLastMoved = time;
                yv = -1*MAX_VELOCITY;
                walkingUpOrDown = true;
                direction = down;
                canMove = !canMove;
            }
            else if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
                position = x;
                xv = -1*MAX_VELOCITY;
                timeLastMoved = time;
                walkingUpOrDown = false;
                direction = left;
                canMove = !canMove;
            }
            else if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
                position = x;
                timeLastMoved = time;
                xv = MAX_VELOCITY;
                walkingUpOrDown = false;
                direction = right;
                canMove = !canMove;
            }

        }
//        if ((walkingUpOrDown) && (time - timeLastMoved > 0.03) && (!canMove)){
//            up.flip(true, false);
//            down.flip(true, false);
//
//        }
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


        x += xv*Gdx.graphics.getDeltaTime();
        y += yv*Gdx.graphics.getDeltaTime();

    }
}
