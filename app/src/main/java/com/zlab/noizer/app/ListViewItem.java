package com.zlab.noizer.app;

import android.content.Context;
import android.media.MediaPlayer;

import java.util.Timer;
import java.util.TimerTask;

public class ListViewItem {

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

    private long mkLoopingPreview = 1;
    private Timer loopTimer;
    private TimerTask loopTask;

    public ListViewItem (Context c, String title, String description, int soundResID, String soundName, boolean isPlaying, int volume, int imageResID){
        Title = title;
        Description = description;
        SoundResID = soundResID;
        this.SoundName = soundName;
        IsPlaying = isPlaying;
        Volume = volume;
        ImageResID = imageResID;
        mContext = c;
    }

    public void startPlaying() {
        stopPlaying();
        mediaPlayer1 = MediaPlayer.create(mContext, SoundResID);
        mediaPlayer2 = MediaPlayer.create(mContext, SoundResID);

        loopTimer = new Timer();
        loopTask = new TimerTask() {
            @Override public void run() {
                if(mediaPlayer1 !=null && mediaPlayer2 !=null) {
                    if (mediaPlayer1.isPlaying()) {
                        mediaPlayer2.start();
                    } else if (mediaPlayer2.isPlaying()) {
                        mediaPlayer1.start();
                    }
                }
            }
        };

        long waitingTime = mediaPlayer1.getDuration()-mkLoopingPreview;
        loopTimer.schedule(loopTask, waitingTime, waitingTime);

        setPlayerVolume();
        mediaPlayer1.start();
        setIsPlaying(true);
    }

    public void stopPlaying() {
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

    private void setPlayerVolume(){
        float floatVolume = Volume / 100f;
        mediaPlayer1.setVolume(floatVolume,floatVolume);
        mediaPlayer2.setVolume(floatVolume,floatVolume);
    }

    public String getTitle() {
        return Title;
    }
    public void setTitle(String title) {
        Title = title;
    }
    public String getDescription() {
        return Description;
    }
    public void setDescription(String description) {
        Description = description;
    }
    public int getSoundResID() {
        return SoundResID;
    }
    public void setSoundResID(int sound_res_ID) {
        SoundResID = sound_res_ID;
    }
    public boolean getIsPlaying() {
        return IsPlaying;
    }
    public void setIsPlaying(boolean is_playing) {
        IsPlaying = is_playing;
    }
    public int getVolume() {
        return Volume;
    }
    public void setVolume(int volume) {
        Volume = volume;
        setPlayerVolume();
    }
    public int getImageResID() {
        return ImageResID;
    }
    public void setImageResID(int image_res_ID) {
        ImageResID = image_res_ID;
    }

}
