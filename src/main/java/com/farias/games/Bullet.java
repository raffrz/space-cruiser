package com.farias.games;

import static com.farias.rengine.GameEngine.createSprite;

import com.farias.rengine.GameEngine.Sprite;

import org.joml.Vector2f;

class Bullet {
    private static final int BULLET_SCALE = 32;
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