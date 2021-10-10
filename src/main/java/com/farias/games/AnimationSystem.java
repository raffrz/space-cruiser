package com.farias.games;

import static com.farias.rengine.GameEngine.*;
import com.farias.rengine.GameEngine.Sprite;
import com.farias.rengine.System;

import org.joml.Vector2f;

public class AnimationSystem extends System {

    private static final int idle_anim = 0;
    private int turning_right_anim = 1;
    private static final int turning_right_idle_anim = 2;
    private static final int turning_left_anim = 3;
    private static final int turning_left_idle_anim = 4;

    public AnimationSystem(SpaceCruiser game) {
        super(game);
    }

    @Override
    public void update(float deltaTime) {
        SpaceCruiser game = (SpaceCruiser) super.game;
        for (GameObject gameObject : game.getObjects()) {
            if (!gameObject.isAnimated())
                continue;
            Animation a = gameObject.getAnimation();
            if (gameObject instanceof Spaceship) {
                Spaceship s = (Spaceship) gameObject;
                if (!s.turning_left && !s.turning_right && (a.currentAnimation == turning_left_anim || a.currentAnimation == turning_left_idle_anim) && !a.reverse) {
                    a.reverse();
                    a.play(turning_left_anim);
                    a.playNext(idle_anim);
                } else if (!s.turning_left && !s.turning_right && (a.currentAnimation == turning_right_anim || a.currentAnimation == turning_right_idle_anim) && !a.reverse) {
                    a.reverse();
                    a.play(turning_right_anim);
                    a.playNext(idle_anim);
                } else if (s.turning_right && a.currentAnimation != turning_right_anim && a.currentAnimation != turning_right_idle_anim) {
                    a.normal();
                    a.play(turning_right_anim);
                    a.playNext(turning_right_idle_anim);
                } else if (s.turning_left && a.currentAnimation != turning_left_anim && a.currentAnimation !=turning_left_idle_anim) {
                    a.normal();
                    a.play(turning_left_anim);
                    a.playNext(turning_left_idle_anim);
                }
            }
            a.nextFrame(deltaTime);
        }
    }
}

class Spaceship extends GameObject {
    boolean moving;
    boolean accelerating;
    boolean breaking;
    boolean turning_right;
    boolean turning_left;
    boolean shooting;

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
            velocity.x -= 3.0f;
        }
        if (turning_right) {
            velocity.x += 3.0f;
        }
        if (accelerating) {
            velocity.y += 3.5f;
        }
        if (breaking) {
            velocity.y -= 3.5f;
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
    }

    @Override
    public void draw() {
        drawSprite(sprite, animation.currentFrame, 128, 128, (int) position.x, (int) position.y, (int) scale.x, (int) scale.y);
    }
    
}

abstract class GameObject {
    protected Vector2f position;
    protected Vector2f scale;
    protected Vector2f rotation;
    protected Vector2f velocity;
    protected Sprite sprite;
    protected Animation animation;

    public abstract void draw();

    public void update() {}

    public boolean isAnimated() {
        return animation != null;
    }

    public Animation getAnimation() {
        return animation;
    }

    public void setAnimation(Animation animation) {
        this.animation = animation;
    }
    
    public Vector2f getPosition() {
        return position;
    }

    public void setPosition(Vector2f position) {
        this.position = position;
    }

    public Vector2f getScale() {
        return scale;
    }

    public void setScale(Vector2f scale) {
        this.scale = scale;
    }

    public Vector2f getRotation() {
        return rotation;
    }

    public void setRotation(Vector2f rotation) {
        this.rotation = rotation;
    }

    public Vector2f getVelocity() {
        return velocity;
    }

    public void setVelocity(Vector2f velocity) {
        this.velocity = velocity;
    }

    public Sprite getSprite() {
        return sprite;
    }

    public void setSprite(Sprite sprite) {
        this.sprite = sprite;
    }
}
