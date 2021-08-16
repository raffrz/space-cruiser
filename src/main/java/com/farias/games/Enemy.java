package com.farias.games;

import com.farias.rengine.GameEngine.Sprite;
import org.joml.Vector2f;

public abstract class Enemy {
    private static final Animation ENEMY_ANIMATION = new Animation(new int[] {
        0 , 14, //stand idle
        60, 74, //turning left
        75, 89, //turning left idle
        15, 29, //turning right
        30, 44, //turning right idle
    });

    private Vector2f position;
    private Vector2f scale;
    private Vector2f rotation;
    private Sprite sprite;
    private Animation animation;

    public Enemy(Sprite sprite, float x, float y, float sx, float sy) {
        this.sprite = sprite;
        this.position = new Vector2f(x, y);
        this.scale = new Vector2f(sx, sy);
        this.rotation = new Vector2f();
        this.animation = ENEMY_ANIMATION;
    }

    public abstract void update(float deltaTime);
    public abstract void shoot();
    public abstract void turnLeft();
    public abstract void turnRight();
    public abstract void stop();
    public abstract void damage();
}
