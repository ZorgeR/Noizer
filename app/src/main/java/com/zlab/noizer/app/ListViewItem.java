package com.zlab.noizer.app;

import android.content.Context;
import android.media.MediaPlayer;

import java.util.Timer;
import java.util.TimerTask;

@SuppressWarnings("unused")
class ListViewItem {

    private String Title;
    private String Description;
    private int SoundResID;
    private boolean IsPlaying;
    private int Volume;
    private int ImageResID;
    private String SoundName;

    private MediaPlayer mediaPlayer1;
    private MediaPlayer mediaPlayer2;
    private Context mContext;

    private Timer loopTimer;

    ListViewItem(Context c, String title, String description, int soundResID, String soundsName, boolean isPlaying, int volume, int imageResID) {
        Title = title;
        Description = description;
        SoundResID = soundResID;
        SoundName = soundsName;
        IsPlaying = isPlaying;
        Volume = volume;
        ImageResID = imageResID;
        mContext = c;
    }

    void startPlaying() {
        stopPlaying();

        mediaPlayer1 = MediaPlayer.create(mContext, SoundResID);
        mediaPlayer2 = MediaPlayer.create(mContext, SoundResID);

        loopTimer = new Timer();
        TimerTask loopTask = new TimerTask() {
            @Override
            public void run() {
                if (mediaPlayer1 != null && mediaPlayer2 != null) {
                    if (mediaPlayer1.isPlaying()) {
                        mediaPlayer2.start();
                    } else if (mediaPlayer2.isPlaying()) {
                        mediaPlayer1.start();
                    }
                }
            }
        };

        long mkLoopingPreview = 1;
        long waitingTime = mediaPlayer1.getDuration() - mkLoopingPreview;
        loopTimer.schedule(loopTask, waitingTime, waitingTime);

        setPlayerVolume();
        mediaPlayer1.start();
        setIsPlaying(true);
    }

    void stopPlaying() {
        if (mediaPlayer1 != null && mediaPlayer2 != null) {
            mediaPlayer1.release();
            mediaPlayer2.release();
            mediaPlayer1 = null;
            mediaPlayer2 = null;
            loopTimer.cancel();
            loopTimer.purge();
        }
        setIsPlaying(false);
    }

    private void setPlayerVolume() {
        float floatVolume = Volume / 100f;
        mediaPlayer1.setVolume(floatVolume, floatVolume);
        mediaPlayer2.setVolume(floatVolume, floatVolume);
    }

    String getTitle() {
        return Title;
    }

    void setTitle(String title) {
        Title = title;
    }

    String getDescription() {
        return Description;
    }

    void setDescription(String description) {
        Description = description;
    }

    int getSoundResID() {
        return SoundResID;
    }

    void setSoundResID(int sound_res_ID) {
        SoundResID = sound_res_ID;
    }

    boolean getIsPlaying() {
        return IsPlaying;
    }

    private void setIsPlaying(boolean is_playing) {
        IsPlaying = is_playing;
    }

    int getVolume() {
        return Volume;
    }

    void setVolume(int volume) {
        Volume = volume;
        setPlayerVolume();
    }

    int getImageResID() {
        return ImageResID;
    }

    void setImageResID(int image_res_ID) {
        ImageResID = image_res_ID;
    }

    String getSoundName() {
        return SoundName;
    }

    void setSoundName(String soundName) {
        SoundName = soundName;
    }
}
