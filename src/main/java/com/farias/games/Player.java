package com.farias.games;

import static com.farias.rengine.GameEngine.createSprite;

import static com.farias.rengine.GameEngine.*;
import org.joml.Vector2f;
import org.joml.Vector3f;

public class Player extends Spaceship {
    public Player() {
        this.scale = new Vector2f(64f, 64f);
        this.position = new Vector2f(320 - scale.x/2, -460 + scale.y/2);
        this.rotation = new Vector3f(0f, 0f, 0f);
        this.velocity = new Vector2f();
        this.spriteSize = 64;
        this.sprite = createSprite("resources/spaceships/enemy/enemy_02-02.png", 0, spriteSize, spriteSize);
        this.animation = Animation.PLAYER_ANIMATION;
    }
}