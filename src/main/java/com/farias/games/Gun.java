package com.farias.games;

import org.joml.Vector2f;

import com.farias.rengine.GameEngine;

public class Gun extends GameObject{

    protected boolean shooting;
    protected float lastShot;
    protected float fireRate = 1f / 60f * 4;
    protected GameObject user;

    public Gun(GameObject user) {
        this.user = user;
    }

    @Override
    public void update(float deltaTime) {
        this.lastShot+=deltaTime;
        if (this.shooting) {
            if (this.lastShot > this.fireRate) {
                this.shoot();
                lastShot = 0;
            }
        }
    }

    public void pullTrigger() {
        this.shooting = true;
    }

    public void releaseTrigger() {
        this.shooting = false;
    }

    public boolean isShooting() {
        return shooting;
    }

    protected void shoot() {
        SpaceCruiser gameInstance = (SpaceCruiser) GameEngine.getGameInstance();
        Bullet b = new Bullet(new Vector2f(this.user.position.x + 16, this.user.position.y), 1);
        gameInstance.addGameObject(b);
    }
    
}
