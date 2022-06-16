package com.farias.games;

import static com.farias.rengine.GameEngine.*;

import java.util.HashSet;
import java.util.Set;

public class Spaceship extends GameObject {
    boolean moving;
    boolean accelerating;
    boolean breaking;
    boolean turning_right;
    boolean turning_left;
    float turningSpeed = 3.0f;
    float thrustSpeed = 3.5f;
    float brakeStrength = 3.5f;
    int spriteSize;
    Set<Gun> guns;

    public Spaceship() {
        this.guns = new HashSet<>();
    }

    public void onShoot() {
        guns.stream()
            .filter(g->!g.isShooting())
            .forEach(Gun::pullTrigger);
    }

    public void onStopShooting() {
        guns.stream()
            .filter(Gun::isShooting)
            .forEach(Gun::releaseTrigger);
    }

    public void stop() {
        turning_right = false;
        turning_left = false;
    }

    public void turnLeft() {
        moving = true;
        turning_left = true;
    }

    public void turnRight() {
        moving = true;
        turning_right = true;
    }

    public void update(float deltaTime) {
        if (turning_left) {
            velocity.x -= turningSpeed;
        }
        if (turning_right) {
            velocity.x += turningSpeed;
        }
        if (accelerating) {
            velocity.y += thrustSpeed;
        }
        if (breaking) {
            velocity.y -= brakeStrength;
        }
        if(!moving) {
            if (velocity.x > 0) {
                velocity.x -= 0.5f;
            } else if (velocity.x < 0) {
                velocity.x += 0.5f;
            }
            if (velocity.y > 0) {
                velocity.y -= 0.5f;
            } else if (velocity.y < 0) {
                velocity.y += 0.5f;
            }
        }
        position.x += velocity.x * deltaTime * 4f;
        position.y += velocity.y * deltaTime * 3f;
        this.guns.forEach(g -> g.update(deltaTime));
    }

    @Override
    public void draw() {
        drawSprite(sprite, animation.currentFrame, spriteSize, spriteSize, (int) position.x, (int) position.y, (int) scale.x, (int) scale.y, rotation.z);
    }

    public void addGun(Gun gun) {
        this.guns.add(gun);
    }
    
}
