package com.farias.games;

import static com.farias.rengine.GameEngine.*;

import org.joml.Vector2f;

class Bullet extends GameObject{
    private static final int BULLET_SCALE = 32;
    float speed;
    int animation;

    public Bullet(Vector2f position, int direction) {
        //TODO Sprite reutilization
        this.sprite = createSprite("resources/bullet/spritesheet_bullet.png", 0, 128, 128);
        this.position = position;
        this.scale = new Vector2f(BULLET_SCALE, BULLET_SCALE);
        this.speed = 16f;
    }

    void nextFrame() {
        animation++;
    }

    @Override
    public void update(float deltaTime) {
        this.position.add(0, 1 * this.speed);
        this.nextFrame();
    }

    @Override
    public void draw() {
        drawSprite(this.sprite, this.animation, 128, 128, (int) this.position.x, (int) this.position.y, (int) this.scale.x, (int) this.scale.y);
    }
}