package com.farias.games;

import static com.farias.rengine.GameEngine.*;
import java.util.HashSet;
import java.util.Set;

import com.farias.games.Enemy.EnemyType;
import com.farias.rengine.Game;
import com.farias.rengine.System;

public class EnemySystem extends System {

    private static final int enemy_screen_limit_left = 20;
    private static final int enemy_screen_limit_right = 620;
    private static final int enemy_velocity = 16;

    private Set<Enemy> enemies = new HashSet<>();

    public EnemySystem(Game g) {
        super(g);
    }

    @Override
    public void update(float deltaTime) {
        this.spawnEnemies(deltaTime);
        for (Enemy enemy : enemies) {
            if (enemy.getVelX() == 0)
                enemy.setVelX(enemy_velocity);
            if (enemy.getVelX() > 0 && enemy.getPosX() > enemy_screen_limit_right) {
                enemy.setVelX(enemy_velocity * -1);
                enemy.getAnimation().play(1);
                enemy.getAnimation().playNext(2);
            }
            else if (enemy.getVelX() < 0 && enemy.getPosX() < enemy_screen_limit_left) {
                enemy.setVelX(enemy_velocity);
                enemy.getAnimation().play(3);
                enemy.getAnimation().playNext(4);
            }
            enemy.setPosX(enemy.getPosX() + enemy.getVelX() * deltaTime);
            if (enemy.getVelY() == 0)
                enemy.setVelY(enemy_velocity * -1);
            enemy.setPosY(enemy.getPosY() + enemy.getVelY() * deltaTime);
            enemy.update(deltaTime);
        }
    }

    public void draw() {
        for (Enemy enemy : enemies) {
            drawSprite(enemy.getSprite(), enemy.getAnimation().currentFrame, 128, 128, (int) enemy.getPosX(), (int) enemy.getPosY(), (int) enemy.getScale().x, (int) enemy.getScale().y);    
        }
    }

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
        enemies.add(enemy);
    }

    float lastSpawn;
    float spawnRate = 5;

    public boolean shouldSpawnEnemy(float deltaTime) {
        lastSpawn+=deltaTime;
        if (lastSpawn < spawnRate)
            return false;
        if (enemies.size() >= 10)
            return false;
        return true;
    }
    
}
