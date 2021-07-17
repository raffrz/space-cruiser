package com.farias.games;

import static com.farias.rengine.GameEngine.*;

import java.util.HashSet;
import java.util.Set;

import com.farias.rengine.Game;
import com.farias.rengine.GameEngine;
import com.farias.rengine.GameEngine.Sprite;
import com.farias.rengine.io.InputSystem;
import com.farias.rengine.io.Window;
import com.farias.rengine.render.RenderSystem;

import org.joml.Vector2f;
import org.lwjgl.glfw.GLFW;

class Main {
    public static void main(String[] args) {
        Window window = new Window(1280, 960);
        long windowId = window.create();
        SpaceCruiser game = new SpaceCruiser("Space Cruiser", window);
        game.addSystem(new InputSystem(game, windowId));
        game.addSystem(new RenderSystem(game) {
        });
        GameEngine.initGame(game);
    }
}

class Bullet {
    private static final int BULLET_SCALE = 64;
    Sprite sprite;
    Vector2f position;
    Vector2f scale;
    float speed;
    int animation;

    public Bullet(Vector2f position, int direction) {
        this.sprite = createSprite("resources/bullet/spritesheet_bullet.png", 0, 128, 128);
        this.position = position;
        this.scale = new Vector2f(BULLET_SCALE, BULLET_SCALE);
        this.speed = 16f;
    }

    void nextFrame() {
        animation++;
    }
}

public class SpaceCruiser extends Game {

    Sprite spaceship_spr;
    Sprite enemy_spr;
    int scale = 64;

    int spaceship_anim = 0;
    int idle_anim = 0;
    int turning_right_anim = 15;
    int turning_left_anim = 60;
    
    float accx = 1;
    float accy = 1;
    float vx = 0;
    float vy = 0;
    float x = 320 - scale/2;
    float y = -460 + scale/2;
    
    boolean moving;
    boolean accelerating;
    boolean breaking;
    boolean turning_right;
    boolean turning_left;
    boolean shooting;
    float lastShot;
    float fireRate = 1f / 60f * 15;
    Set<Bullet> bullets = new HashSet<>(); 

    public SpaceCruiser(String title, Window window) {
        super(title, window, true);
    }

    @Override
    public void onUserCreate() {
        orthographicMode(640, 480);
        spaceship_spr = createSprite("resources/spaceship/spritesheet_nave_green.png", 0, 128, 128);
        enemy_spr = createSprite("resources/spaceship/spritesheet_nave_green_pixelated.png", 0, 128, 128);
    }

    @Override
    public void onUserUpdate(float deltaTime) {
        moving = false;
        turning_right = false;
        turning_left = false;

        checkInput();

        resolveGameState(deltaTime);

        if (turning_right) {
            if (++turning_right_anim > 44)
                turning_right_anim = 29;
            spaceship_anim = turning_right_anim;
        }
        if (turning_left) {
            if (++turning_left_anim > 89)
                turning_left_anim = 74;
            spaceship_anim = turning_left_anim;
        }
        if ((accelerating || breaking && !turning_left && !turning_right) || !moving) {
            //idle animation
            if (++idle_anim >= 15)
                idle_anim = 0;
            spaceship_anim = idle_anim;
        }

        if (!turning_left) {
            if (turning_left_anim > 60) {
                turning_left_anim--;
            }
        }

        if (!turning_right) {
            if (turning_right_anim > 15) {
                turning_right_anim--;
            }
        }

        if(!moving) {
            //spaceship_anim = 0;
            if (turning_right_anim > 15) {
                spaceship_anim = turning_right_anim;
            } else if (turning_left_anim > 60) {
                spaceship_anim = turning_left_anim;
            }
            if (vx > 0) {
                vx -= 0.6f;
            } else if (vx < 0) {
                vx += 0.6f;
            }
            if (vy > 0) {
                vy -= 0.6f;
            } else if (vx < 0) {
                vy += 0.6f;
            }
        }
        x += vx * deltaTime * 4f;
        y += vy * deltaTime * 3f;
    }

    private void checkInput() {
        if (getInput().isKeyDown(GLFW.GLFW_KEY_LEFT)) {
            moving = true;
            turning_left = true;
            vx -= 2.8f;
        } 
        if (getInput().isKeyDown(GLFW.GLFW_KEY_RIGHT)) {
            moving = true;
            turning_right = true;
            vx += 2.8f;
        } 
        
        if (getInput().isKeyDown(GLFW.GLFW_KEY_UP)) {
            accelerating = true;
            moving = true;
            vy += 3.6f;
        }

        if (getInput().isKeyReleased(GLFW.GLFW_KEY_UP)) {
            accelerating = false;
        }
        
        if (getInput().isKeyDown(GLFW.GLFW_KEY_DOWN)) {
            breaking = true;
            moving = true;
            vy -= 3.6f;
        }

        if (getInput().isKeyReleased(GLFW.GLFW_KEY_DOWN)) {
            breaking = false;
        }

        if (getInput().isKeyPressed(GLFW.GLFW_KEY_SPACE)) {
            shooting = true;
        }

        if (getInput().isKeyReleased(GLFW.GLFW_KEY_SPACE)) {
            shooting = false;
        }
    }

    public void resolveGameState(float deltaTime) {
        lastShot+=deltaTime;
        if (shooting) {
            if (lastShot > fireRate) {
                createBullet();
                lastShot = 0;
            }
        }

        for (Bullet bullet : bullets) {
            bullet.position.add(0, 1 * bullet.speed);
            bullet.nextFrame();
        }
    }

    public void createBullet() {
        bullets.add(new Bullet(new Vector2f(x, y + 48), 1));
    }

    @Override
    public void onGfxUpdate(float deltaTime) {
        drawSprite(enemy_spr, spaceship_anim, 128, 128, (int) x, (int) y, scale, scale);
        drawSprite(enemy_spr, spaceship_anim, 128, 128, (int) x, -96, -scale, -scale);
        for (Bullet bullet : bullets) {
            drawSprite(bullet.sprite, bullet.animation, 128, 128, (int) bullet.position.x, (int) bullet.position.y, (int) bullet.scale.x, (int) bullet.scale.y);
        }
        // drawSprite(spaceship_spr, (int) x, (int) y, 64, 64);
        drawText("current sprite: " + spaceship_anim, 340, -10, 8, 8);
    }
    
}
