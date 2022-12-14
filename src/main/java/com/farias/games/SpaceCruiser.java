package com.farias.games;

import static com.farias.rengine.GameEngine.*;

import java.util.HashSet;
import java.util.Set;

import com.farias.rengine.Game;
import com.farias.rengine.GameEngine;
import com.farias.rengine.io.InputSystem;
import com.farias.rengine.io.Window;
import com.farias.rengine.render.RenderSystem;

import org.lwjgl.glfw.GLFW;

class Main {
    public static void main(String[] args) {
        Window window = new Window(1280, 960);
        long windowId = window.create();
        SpaceCruiser game = new SpaceCruiser("Space Cruiser", window);
        game.addSystem(new InputSystem(game, windowId));
        game.addSystem(RenderSystem.renderSystem2D(game));
        GameEngine.initGame(game);
    }
}

public class SpaceCruiser extends Game {
    private Player player;
    private Set<GameObject> gameObjects;
    private Set<GameObject> gameObjectsToAdd;

    public SpaceCruiser(String title, Window window) {
        super(title, window, false);
    }

    @Override
    public void onUserCreate() {
        orthographicMode(640, 480);
        this.addSystem(new EnemySystem(this));
        this.addSystem(new AnimationSystem(this));
        this.addSystem(new BulletSystem(this, 640, 0, -480, 0));
        this.gameObjects = new HashSet<>();
        this.gameObjectsToAdd = new HashSet<>();
        this.player = new Player();
        Gun playerDoubleCannon = new DoubleGun(this.player);
        this.player.addGun(playerDoubleCannon);
        this.gameObjects.add(player);
    }

    @Override
    public void onUserUpdate(float deltaTime) {
        this.checkInput();
        for (GameObject g: gameObjects) {
            g.update(deltaTime);
        }
        this.gameObjects.addAll(gameObjectsToAdd);
        this.gameObjectsToAdd.clear();
    }

    @Override
    public void onGfxUpdate(float deltaTime) {
        for (GameObject g: gameObjects) {
            g.draw();
        }
        drawText("hp: " + player.health, 340, -10, 8, 8);
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
            player.onShoot();
        }
        if (getInput().isKeyReleased(GLFW.GLFW_KEY_SPACE)) {
            player.onStopShooting();
        }
    }

    public Set<GameObject> getGameObjects() {
        return this.gameObjects;
    }

    public void addGameObject(GameObject gameObject) {
        this.gameObjectsToAdd.add(gameObject);
    }
}
