package com.bupt.read.Config;

import com.bupt.read.service.MusicPlayService;

import java.io.Serializable;

/**
 * Created by xs on 2016/7/7.
 */
public class PlayState implements Serializable{
    private int currentPosition = 0;
    private boolean isPlaying = false;
    private long progress = 0;
    private int mode = MusicPlayService.MODE_IN_ORDER;
    private long duration = 0;

    @Override
    public String toString() {
        return "PlayState{" +
                "currentPosition=" + currentPosition +
                ", isPlaying=" + isPlaying +
                ", progress=" + progress +
                ", mode=" + mode +
                ", duration=" + duration +
                '}';
    }

    public int getCurrentPosition() {
        return currentPosition;
    }

    public void setCurrentPosition(int currentPosition) {
        this.currentPosition = currentPosition;
    }

    public boolean isPlaying() {
        return isPlaying;
    }

    public void setIsPlaying(boolean isPlaying) {
        this.isPlaying = isPlaying;
    }

    public long getProgress() {
        return progress;
    }

    public void setProgress(long progress) {
        this.progress = progress;
    }

    public int getMode() {
        return mode;
    }

    public void setMode(int mode) {
        this.mode = mode;
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

}
