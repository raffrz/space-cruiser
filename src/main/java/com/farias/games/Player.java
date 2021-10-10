package com.farias.games;

import static com.farias.rengine.GameEngine.createSprite;

import com.farias.rengine.GameEngine.Sprite;

import static com.farias.rengine.GameEngine.*;
import org.joml.Vector2f;

public class Player extends Spaceship {
    public Player() {
        this.scale = new Vector2f(64f, 64f);
        this.position = new Vector2f(320 - scale.x/2, -460 + scale.y/2);
        this.velocity = new Vector2f();
        this.sprite = createSprite("resources/spaceships/enemy/enemy_02-02.png", 0, 64, 64);
        this.animation = Animation.PLAYER_ANIMATION;
    }

    @Override
    public void draw() {
        drawSprite(sprite, animation.currentFrame, 64, 64, (int) position.x, (int) position.y, (int) scale.x, (int) scale.y);
    }
}