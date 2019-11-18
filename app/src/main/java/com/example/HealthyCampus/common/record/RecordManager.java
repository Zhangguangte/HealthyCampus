package com.example.HealthyCampus.common.record;

import android.media.MediaRecorder;
import android.os.Environment;

import com.example.HealthyCampus.common.utils.LogUtil;

import java.io.File;


public class RecordManager {

    private static RecordManager mInstance;
    private MediaRecorder mMediaRecorder;
    private String mCurrentFilePath;


    private boolean isPrepare;

    public static RecordManager getInstance() {
        if (null == mInstance) {
            synchronized (RecordManager.class) {
                if (null == mInstance) {
                    mInstance = new RecordManager();
                }
            }
        }
        return mInstance;
    }


    /**
     * 使用接口 用于回调
     */
    public interface OnAudioStateListener {
        void wellPrepared();
    }

    public OnAudioStateListener mAudioStateListener;

    /**
     * 回调方法
     */
    public void setOnAudioStateListener(OnAudioStateListener listener) {
        mAudioStateListener = listener;
    }

    // 去准备
    public void prepareAudio() {
        try {
            String dirStr = Environment.getExternalStorageDirectory() + "/health/record";
            isPrepare = false;
            File dir = new File(dirStr);
            if (!dir.exists()) {
                LogUtil.logE("RecordManager123456", "" + dir.mkdirs());
            }
            String fileName = generateFileName();
            File file = new File(dir, fileName);

            mCurrentFilePath = file.getAbsolutePath();
            //   LogUtil.d("RecordManager123", mDir);
            LogUtil.logE("RecordManager123456", "fileName：" + fileName);
            LogUtil.logE("RecordManager123456", "mCurrentFilePath：" + mCurrentFilePath);
            LogUtil.logE("RecordManager123456", "getAbsolutePath：" + file.getAbsolutePath());

            mMediaRecorder = new MediaRecorder();
            // 设置输出文件
            mMediaRecorder.setOutputFile(mCurrentFilePath);
            // 设置MediaRecorder的音频源为麦克风
            mMediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
            // 设置音频格式
            mMediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.RAW_AMR);
            // 设置音频编码
            mMediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);

            // 准备录音
            mMediaRecorder.prepare();
            // 开始
            mMediaRecorder.start();
            // 准备结束
            isPrepare = true;
            if (mAudioStateListener != null) {
                mAudioStateListener.wellPrepared();
            }
            LogUtil.logE("RecordManager123456", "调用789");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 随机生成文件的名称
     */
    private String generateFileName() {
        return String.valueOf(System.currentTimeMillis()) + ".amr";
    }

    public int getVoiceLevel(int maxlevel) {
        if (isPrepare) {
            // mMediaRecorder.getMaxAmplitude() 1~32767
            return maxlevel * mMediaRecorder.getMaxAmplitude() / 32768 + 1;
        }
        return 1;
    }

    /**
     * 释放资源
     */
    public void release() {
        //mMediaRecorder.stop();
        if (null != mMediaRecorder) {
            mMediaRecorder.reset();
            mMediaRecorder = null;
        }
    }

    /**
     * 取消录音
     */
    public void cancel() {
        release();
        if (mCurrentFilePath != null) {
            File file = new File(mCurrentFilePath);
            file.delete();
            mCurrentFilePath = null;
        }
        LogUtil.logE("RecordManager123456", "取消789");

    }

    public String getCurrentFilePath() {

        return mCurrentFilePath;
    }

}
