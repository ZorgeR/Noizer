package com.zlab.noizer.app;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.AssetManager;
import android.media.MediaPlayer;
import android.os.Build;
import android.util.Log;

import java.io.IOException;

@SuppressWarnings("unused")
class ListViewItem {

    private String Title;
    private String Description;
    private int SoundResID;
    private boolean IsPlaying;
    private int Volume;
    private int ImageResID;
    private String SoundName;

    private MediaPlayer mediaPlayer;
    private MediaPlayer mCurrentPlayerLoop;
    private MediaPlayer mNextPlayerLoop;
    private Context mContext;

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

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            mCurrentPlayerLoop = MediaPlayer.create(mContext, SoundResID);
            mCurrentPlayerLoop.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mediaPlayer) {
                    mCurrentPlayerLoop.start();
                }
            });
            createNextMediaPlayer();
        } else {
            mediaPlayer = MediaPlayer.create(mContext, SoundResID);
            mediaPlayer.setLooping(true);
            mediaPlayer.start();
        }
        setPlayerVolume();
        setIsPlaying(true);
    }

    void stopPlaying() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            if (mCurrentPlayerLoop != null) {
                mCurrentPlayerLoop.release();
            }
            if (mNextPlayerLoop != null) {
                mNextPlayerLoop.release();
            }
        } else {
            if (mediaPlayer != null) {
                mediaPlayer.release();
            }
        }
        setIsPlaying(false);
    }

    private void setPlayerVolume() {
        float floatVolume = Volume / 100f;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            if (mCurrentPlayerLoop != null) {
                mCurrentPlayerLoop.setVolume(floatVolume, floatVolume);
            }
            if (mNextPlayerLoop != null) {
                mNextPlayerLoop.setVolume(floatVolume, floatVolume);
            }
        } else {
            if (mediaPlayer != null) {
                mediaPlayer.setVolume(floatVolume, floatVolume);
            }
        }
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

    @SuppressLint("NewApi")
    private void createNextMediaPlayer() {
        mNextPlayerLoop = MediaPlayer.create(mContext, SoundResID);
        mCurrentPlayerLoop.setNextMediaPlayer(mNextPlayerLoop);
        mCurrentPlayerLoop.setOnCompletionListener(onCompletionListener);
        setPlayerVolume();
    }

    private MediaPlayer.OnCompletionListener onCompletionListener = new MediaPlayer.OnCompletionListener() {
        @Override
        public void onCompletion(MediaPlayer mediaPlayer) {
            mediaPlayer.release();
            mCurrentPlayerLoop = mNextPlayerLoop;
            createNextMediaPlayer();
        }
    };
}
