package com.farias.games;

import java.util.LinkedList;
import java.util.Queue;

class Animation {
    int[] animations;
    int currentFrame;
    Queue<Integer> animationQueue = new LinkedList<>();
    int currentAnimation;
    int nextAnimation;
    boolean reverse = false;
    float lastFrame;
    float framesPerSec = 0;

    public Animation(int[] animations) {
        this.animations = animations;
    }
    public static final Animation PLAYER_ANIMATION = new Animation(new int[]{
        0 , 14, //stand idle
        60, 74, //turning left
        75, 89, //turning left idle
        15, 29, //turning right
        30, 44, //turning right idle
    });

    public void play(int animation1, int animation2) {
        this.play(animation1);
        this.playNext(animation2);
    }

    public void playNext(int animation) {
        if (animation > animations.length / 2)
            throw new ArrayIndexOutOfBoundsException();
        this.animationQueue.add(animation);
    }

    public void play(int animation) {
        if (animation > animations.length / 2)
            throw new ArrayIndexOutOfBoundsException();
        this.currentAnimation = animation;
        if (!reverse) {
            this.currentFrame = animations[currentAnimation * 2];
        } else {
            this.currentFrame = animations[currentAnimation * 2 + 1];
        }
        this.animationQueue.clear();
    }

    public void reverse() {
        this.reverse = true;
    }

    public void normal() {
        this.reverse = false;
    }

    public void nextFrame(float deltaTime) {
        lastFrame+=deltaTime;
        if (framesPerSec == 0 || lastFrame > 1f / framesPerSec) {
            lastFrame = 0;
            if (reverse) {
                this.previousFrame(deltaTime);
                return;
            }
            if (++currentFrame > animations[currentAnimation * 2 + 1]) {
                if (!animationQueue.isEmpty()) {
                    currentAnimation = animationQueue.remove();
                }
                currentFrame = animations[currentAnimation * 2];
            }
        }
    }

    public void previousFrame(float deltaTime) {
        if (--currentFrame < animations[currentAnimation * 2])
            if (!animationQueue.isEmpty()) {
                currentAnimation = animationQueue.remove();
                normal();
                currentFrame = animations[currentAnimation * 2];
            } else {
                currentFrame = animations[currentAnimation * 2 + 1];
            }
            
    }
}