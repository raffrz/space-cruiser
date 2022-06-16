package com.farias.games;

import com.farias.rengine.System;

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
        for (GameObject gameObject : game.getGameObjects()) {
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
