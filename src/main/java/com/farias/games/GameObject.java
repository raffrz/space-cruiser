package com.farias.games;

import com.farias.rengine.GameEngine.Sprite;

import org.joml.Vector2f;
import org.joml.Vector3f;

public abstract class GameObject {
    protected Vector2f position;
    protected Vector2f scale;
    protected Vector3f rotation;
    protected Vector2f velocity;
    protected Sprite sprite;
    protected Animation animation;

    public void draw() {};

    public abstract void update(float deltaTime);

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

    public Vector3f getRotation() {
        return rotation;
    }

    public void setRotation(Vector3f rotation) {
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
