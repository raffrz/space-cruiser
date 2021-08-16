package com.farias.games;

import static com.farias.rengine.GameEngine.createSprite;

import com.farias.rengine.GameEngine.Sprite;

import org.joml.Vector2f;

public class Player {
    Sprite sprite;
    Vector2f position;
    Vector2f scale;
    Vector2f velocity;
    Animation animation;

    boolean moving;
    boolean accelerating;
    boolean breaking;
    boolean turning_right;
    boolean turning_left;
    boolean stop_turning_left;
    boolean shooting;

    int idle_anim = 0;
    int turning_right_anim = 1;
    int turning_right_idle_anim = 2;
    int turning_left_anim = 3;
    int turning_left_idle_anim = 4;
    
    public Player() {
        this.scale = new Vector2f(64f, 64f);
        this.position = new Vector2f(320 - scale.x/2, -460 + scale.y/2);
        this.velocity = new Vector2f();
        this.sprite = createSprite("resources/spaceships/enemy/enemy_02-02.png", 0, 64, 64);
        this.animation = Animation.PLAYER_ANIMATION;
    }

    public void stop() {
        if (turning_left) {
            animation.reverse();
            animation.play(turning_left_anim);
            animation.playNext(idle_anim);
        }
        if (turning_right) {
            animation.reverse();
            animation.play(turning_right_anim);
            animation.playNext(idle_anim);
        }
        turning_right = false;
        turning_left = false;
    }

    public void turnLeft() {
        moving = true;
        turning_left = true;
        animation.normal();
        animation.play(turning_left_anim);
        animation.playNext(turning_left_idle_anim);
    }

    public void turnRight() {
        moving = true;
        turning_right = true;
        animation.normal();
        animation.play(turning_right_anim);
        animation.playNext(turning_right_idle_anim);
    }

    public void update(float deltaTime) {
        if (turning_left) {
            velocity.x -= 2.8f;
        }
        if (turning_right) {
            velocity.x += 2.8f;
        }
        if (accelerating) {
            velocity.y += 3.5f;
        }
        if (breaking) {
            velocity.y -= 3.5f;
        }
        if(!moving) {
            if (velocity.x > 0) {
                velocity.x -= 0.7f;
            } else if (velocity.x < 0) {
                velocity.x += 0.7f;
            }
            if (velocity.y > 0) {
                velocity.y -= 0.7f;
            } else if (velocity.y < 0) {
                velocity.y += 0.7f;
            }
        }
        position.x += velocity.x * deltaTime * 4f;
        position.y += velocity.y * deltaTime * 3f;
        animation.nextFrame(deltaTime);
    }
}