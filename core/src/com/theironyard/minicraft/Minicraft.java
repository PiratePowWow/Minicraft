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

    SpriteBatch batch;
    TextureRegion down, up, right, left, direction;

    float x = 0;
    float y = 0;
    float time;
    float timeLastMoved = 0;

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

        if (time - timeLastMoved > 0.15f) {
            if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
                y += 40;
                timeLastMoved = time;
                direction = up;
            }
            if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
                y -= 40;
                timeLastMoved = time;
                direction = down;
            }
            if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
                x -= 40;
                timeLastMoved = time;
                direction = left;
            }
            if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
                x += 40;
                timeLastMoved = time;
                direction = right;
            }
        }
//        y += yv*Gdx.graphics.getDeltaTime();
//        x += xv*Gdx.graphics.getDeltaTime();
//
//        yv = decelerate(yv);
//        xv = decelerate(xv);

    }
}
