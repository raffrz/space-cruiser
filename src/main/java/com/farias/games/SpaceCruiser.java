package com.farias.games;

import static com.farias.rengine.GameEngine.*;

import java.util.HashSet;
import java.util.Set;

import com.farias.rengine.Game;
import com.farias.rengine.GameEngine;
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

public class SpaceCruiser extends Game {
    private Player player;
    private float lastShot;
    private float fireRate = 1f / 60f * 10;
    private Set<Bullet> bullets = new HashSet<>();
    private EnemySystem enemySystem;

    public SpaceCruiser(String title, Window window) {
        super(title, window, true);
    }

    @Override
    public void onUserCreate() {
        orthographicMode(640, 480);
        player = new Player();
        this.enemySystem = new EnemySystem(this);
    }

    @Override
    public void onUserUpdate(float deltaTime) {
        this.checkInput();
        this.enemySystem.update(deltaTime);
        //collisionSystem.update();
        this.player.update(deltaTime);
        this.resolveGameState(deltaTime);
    }

    @Override
    public void onGfxUpdate(float deltaTime) {
        drawSprite(player.sprite, player.animation.currentFrame, 64, 64, (int) player.position.x, (int) player.position.y,
         (int) player.scale.x, (int) player.scale.y);
        for (Bullet bullet : bullets) {
            drawSprite(bullet.sprite, bullet.animation, 128, 128, (int) bullet.position.x, (int) bullet.position.y, (int) bullet.scale.x, (int) bullet.scale.y);
        }
        enemySystem.draw();
        drawText("current sprite: " + player.animation.currentFrame, 340, -10, 8, 8);
    }

    private void checkInput() {
        player.moving = false;
        if (getInput().isKeyPressed(GLFW.GLFW_KEY_LEFT)) {
            player.turnLeft();
        }
        if (getInput().isKeyReleased(GLFW.GLFW_KEY_LEFT)) {
            player.stop();
        } 
        if (getInput().isKeyPressed(GLFW.GLFW_KEY_RIGHT)) {
            player.turnRight();
        }
        if (getInput().isKeyReleased(GLFW.GLFW_KEY_RIGHT)) {
            player.stop();
        } 
        if (getInput().isKeyPressed(GLFW.GLFW_KEY_UP)) {
            player.accelerating = true;
            player.moving = true;
        }
        if (getInput().isKeyReleased(GLFW.GLFW_KEY_UP)) {
            player.accelerating = false;
        }
        if (getInput().isKeyDown(GLFW.GLFW_KEY_DOWN)) {
            player.breaking = true;
            player.moving = true;
        }
        if (getInput().isKeyReleased(GLFW.GLFW_KEY_DOWN)) {
            player.breaking = false;
        }
        if (getInput().isKeyPressed(GLFW.GLFW_KEY_SPACE)) {
            player.shooting = true;
        }
        if (getInput().isKeyReleased(GLFW.GLFW_KEY_SPACE)) {
            player.shooting = false;
        }
    }

    public void resolveGameState(float deltaTime) {
        lastShot+=deltaTime;
        if (player.shooting) {
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
        bullets.add(new Bullet(new Vector2f(player.position.x + 16, player.position.y), 1));
    }  
}
