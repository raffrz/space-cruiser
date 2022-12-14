package com.farias.games;

import static com.farias.rengine.GameEngine.*;

import org.joml.Vector2f;
import org.joml.Vector3f;

class Bullet extends GameObject{
    private static final int BULLET_SCALE = 32;
    float speed;
    int animation;
    float angle;

    public Bullet(Vector2f position, float angle) {
        //TODO Sprite reutilization
        this.sprite = createSprite("resources/bullet/spritesheet_bullet.png", 0, 128, 128);
        this.position = position;
        this.rotation = new Vector3f(0, 0, angle);
        this.scale = new Vector2f(BULLET_SCALE, BULLET_SCALE);
        this.speed = 16f;
        this.angle = (float) Math.toRadians(angle);
    }

    void nextFrame() {
        animation++;
    }

    @Override
    public void update(float deltaTime) {
        this.position.add((float) Math.sin(angle) * speed ,(float) Math.cos(angle) * this.speed);
        this.nextFrame();
    }

    @Override
    public void draw() {
        drawSprite(this.sprite, this.animation, 128, 128, (int) this.position.x, (int) this.position.y, (int) this.scale.x, (int) this.scale.y, -rotation.z);
    }
}