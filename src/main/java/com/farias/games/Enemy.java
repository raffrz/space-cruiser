package com.farias.games;

import static com.farias.rengine.GameEngine.*;
import com.farias.rengine.GameEngine.Sprite;
import org.joml.Vector2f;
import org.joml.Vector3f;

public class Enemy extends Spaceship {
    public enum EnemyType {
        ENEMY_01("resources/spaceships/enemy/enemy_01.png"),
        ENEMY_02("resources/spaceships/enemy/enemy_02.png"),
        ENEMY_03("resources/spaceships/enemy/enemy_03.png"),
        ENEMY_04("resources/spaceships/enemy/enemy_04.png"),
        ENEMY_05("resources/spaceships/enemy/enemy_05.png");

        private String sprite;

        EnemyType(String sprite) {
            this.sprite = sprite;
        }
    }

    private static final int[] ENEMY_ANIMATION = new int[] {
        0 , 14, //stand idle
        60, 74, //turning left
        75, 89, //turning left idle
        15, 29, //turning right
        30, 44, //turning right idle
    };

    public Enemy(EnemyType type, float x, float y, float sx, float sy) {
        this.position = new Vector2f(x, y);
        this.scale = new Vector2f(sx, sy);
        this.rotation = new Vector3f(0f, 0f, 180f);
        this.velocity = new Vector2f();
        this.spriteSize = 128;
        this.sprite = createSprite(type.sprite, 0, spriteSize, spriteSize);
        this.animation = new Animation(ENEMY_ANIMATION);
        this.animation.play(0);
    }

    public void update(float deltaTime) {

    }
    
    public void shoot() {}
    public void turnLeft() {}
    public void turnRight() {}
    public void stop() {}
    public void damage() {}

    public Sprite getSprite() {
        return sprite;
    }

    public float getPosX() {
        return position.x;
    }

    public void setPosX(float x) {
        position.x = x;
    }

    public float getPosY() {
        return position.y;
    }

    public void setPosY(float y) {
        position.y = y;
    }

    public float getVelX() {
        return velocity.x;
    }

    public void setVelX(float x) {
        velocity.x = x;
    }

    public float getVelY() {
        return velocity.y;
    }

    public void setVelY(float y) {
        velocity.y = y;
    }

    public Vector2f getScale() {
        return scale;
    }

    public Animation getAnimation() {
        return animation;
    }
}
