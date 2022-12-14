package com.farias.games;

import java.util.Set;
import java.util.stream.Collectors;

import com.farias.rengine.Game;
import com.farias.rengine.System;

public class BulletSystem extends System {

    private int rightEdge;
    private int leftEdge;
    private int topEdge;
    private int bottomEdge;

    public BulletSystem(Game g, int rightEdge, int leftEdge, int topEdge, int bottomEdge) {
        super(g);
        this.rightEdge = rightEdge;
        this.leftEdge = leftEdge;
        this.topEdge = topEdge;
        this.bottomEdge = bottomEdge;
    }

    @Override
    public void update(float deltaTime) {
        SpaceCruiser spaceCruiserGame = (SpaceCruiser) game;
        //remove the bullet entities that are outside of the screen
        Set<GameObject> bulletsToDelete = spaceCruiserGame.getGameObjects().stream()
                .filter(obj -> obj instanceof Bullet)
                .filter(bullet -> !isInsideBulletBox(bullet))
                .collect(Collectors.toSet());
        if (!bulletsToDelete.isEmpty()) {
            // java.lang.System.out.println("removing the bullet entities that are outside of the screen: " + bulletsToDelete);
            spaceCruiserGame.getGameObjects().removeAll(bulletsToDelete);
        }

    }

    private boolean isInsideBulletBox(GameObject bullet) {
        return bullet.position.x > leftEdge && bullet.position.x < rightEdge && bullet.position.y > topEdge
                && bullet.position.y < bottomEdge;
    }
}
