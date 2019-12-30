package com.example.HealthyCampus.common.record;

import android.media.AudioManager;
import android.media.MediaPlayer;


public class MediaManager {

    private static MediaPlayer mMediaPlayer;
    private static boolean isPause;

    /**
     * 播放音乐
     */
    public static void playSound(String filePath, MediaPlayer.OnCompletionListener onCompletionListener) {
        if (mMediaPlayer == null) {
            mMediaPlayer = new MediaPlayer();
            //设置一个error监听器
            mMediaPlayer.setOnErrorListener((arg0, arg1, arg2) -> {
                mMediaPlayer.reset();
                //        Log.e("MediaManager123456","VOICE:1");
                return false;
            });
            //        Log.e("MediaManager123456","VOICE:2");
        } else {
            mMediaPlayer.reset();
//            Log.e("MediaManager123456","VOICE:3");
        }

        try {
            //   Log.e("MediaManager123456","filePath"+filePath);
            mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mMediaPlayer.setOnCompletionListener(onCompletionListener);
            mMediaPlayer.setDataSource(filePath);
            mMediaPlayer.prepare();
            mMediaPlayer.start();
            //    Log.e("MediaManager123456","VOICE:4");
        } catch (Exception e) {
            //  Log.e("MediaManager123456","VOICE:5");
        }
    }

    /**
     * 暂停播放
     */
    public static void pause() {
        if (mMediaPlayer != null && mMediaPlayer.isPlaying()) { //正在播放的时候
            mMediaPlayer.pause();
            isPause = true;
        }
    }

    /**
     * 当前是继续播放
     */
    public static void resume() {
        if (mMediaPlayer != null && isPause) {
            mMediaPlayer.start();
            isPause = false;
        }
    }


    /**
     * 释放资源
     */
    public static void release() {
        if (mMediaPlayer != null) {
            mMediaPlayer.release();
            mMediaPlayer = null;
        }
    }
}
