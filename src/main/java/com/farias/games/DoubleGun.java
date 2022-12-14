package com.farias.games;

import org.joml.Vector2f;

import com.farias.rengine.GameEngine;

public class DoubleGun extends Gun {

    public DoubleGun(GameObject user) {
        super(user);
    }

    @Override
    protected void shoot() {
        SpaceCruiser gameInstance = (SpaceCruiser) GameEngine.getGameInstance();
        
        Bullet b1 = new Bullet(new Vector2f(this.user.position.x + 20, this.user.position.y + 4 + (int) (Math.random() * 32)), 5);
        Bullet b2 = new Bullet(new Vector2f(this.user.position.x + 12, this.user.position.y + 4 + (int) (Math.random() * 32)), -5);
        gameInstance.addGameObject(b1);
        gameInstance.addGameObject(b2);
    }
    
}
