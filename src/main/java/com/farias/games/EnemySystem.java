package com.farias.games;

import static com.farias.rengine.GameEngine.*;

import com.farias.games.Enemy.EnemyType;
import com.farias.rengine.System;

public class EnemySystem extends System {

    private static final int enemy_screen_limit_left = 20;
    private static final int enemy_screen_limit_right = 620;
    private static final int enemy_velocity = 64;

    private int numberOfEnemies;

    public EnemySystem(SpaceCruiser g) {
        super(g);
    }

    @Override
    public void update(float deltaTime) {
        this.spawnEnemies(deltaTime);
        for (GameObject object : ((SpaceCruiser)game).getObjects()) {
            if (object instanceof Enemy) {
                Enemy enemy = (Enemy) object;
                if (enemy.getVelX() == 0)
                    enemy.setVelX(enemy_velocity);
                    //enemy.turning_right = true;
                if (enemy.getVelX() > 0 && enemy.getPosX() > enemy_screen_limit_right) {
                    enemy.setVelX(enemy_velocity * -1);
                    enemy.turning_right = true;
                    enemy.turning_left = false;
                }
                else if (enemy.getVelX() < 0 && enemy.getPosX() < enemy_screen_limit_left) {
                    enemy.setVelX(enemy_velocity);
                    enemy.turning_left = true;
                    enemy.turning_right = false;
                }
                enemy.setPosX(enemy.getPosX() + enemy.getVelX() * deltaTime);
                if (enemy.getVelY() == 0)
                    enemy.setVelY(enemy_velocity * -1);
                enemy.setPosY(enemy.getPosY() + enemy.getVelY() * deltaTime);
                enemy.update(deltaTime);
            }
        }
    }

    // public void draw() {
    //     for (GameObject object : ((SpaceCruiser)game).getObjects()) {
    //         if (object instanceof Enemy) {
    //             Enemy enemy = (Enemy) object;
    //             drawSprite(enemy.getSprite(), enemy.getAnimation().currentFrame, 128, 128, (int) enemy.getPosX(), (int) enemy.getPosY(), (int) enemy.getScale().x, (int) enemy.getScale().y);
    //         }
    //     }
    // }

    public void spawnEnemies(float deltaTime) {
        if(this.shouldSpawnEnemy(deltaTime)) {
            this.spawnEnemy(deltaTime);
            lastSpawn = 0;
        }
    }

    public void spawnEnemy(float deltaTime) {
        float sx = 64;
        float sy = 64;
        int x = (int) (Math.random() * 640);
        int y = 40 - (int) sx;
        int type = (int) (Math.random() * 4) + 1;
        Enemy enemy = new Enemy(EnemyType.valueOf("ENEMY_0" + type), x, y, sx, sy);
        // Enemy enemy = Enemy.builder()
        // .randomVariant()
        // .withLevelBetween(1,3)
        // .atPosition()
        // .build();
        ((SpaceCruiser)game).getObjects().add(enemy);
        numberOfEnemies++;
    }

    float lastSpawn;
    float spawnRate = 5;

    public boolean shouldSpawnEnemy(float deltaTime) {
        lastSpawn+=deltaTime;
        if (lastSpawn < spawnRate)
            return false;
        if (numberOfEnemies >= 10)
            return false;
        return true;
    }
    
}
