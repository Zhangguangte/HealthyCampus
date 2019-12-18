package com.example.HealthyCampus.module.Message.Chat.Vedio;


import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.RelativeLayout;
import android.widget.VideoView;

import com.example.HealthyCampus.R;
import com.example.HealthyCampus.common.utils.LogUtil;
import com.example.HealthyCampus.common.utils.StatusBarUtil;
import com.example.HealthyCampus.module.Message.Chat.imageselect.ImageLoaderManager;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.HealthyCampus.common.constants.ConstantValues.VEDIO_SDK_PATH;
import static com.example.HealthyCampus.common.constants.ConstantValues.VEDIO_SHOW_THUMBNAIL_PATH;

public class VedioActivity extends AppCompatActivity {

    private static final String TAG = "VedioActivity" + "123456";

    @BindView(R.id.video_view)
    VideoView videoView;
    @BindView(R.id.placeholder)
    ImageView placeholder;
    @BindView(R.id.ivPlay)
    ImageView ivPlay;
    @BindView(R.id.firstLayout)
    RelativeLayout firstLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chats_play_vedio);
        ButterKnife.bind(this);
        initMediaPlayer();
        initFirstFrame();
        initImmersionBar();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    private void initImmersionBar() {
        StatusBarUtil.setTranslucentStatus(this);   //设置状态栏透明
        StatusBarUtil.setStatusBarDarkTheme(this, false);        //白色字体
    }

    private void initMediaPlayer() {
        try {
            File file = new File(VEDIO_SDK_PATH + getIntent().getStringExtra("FILEPATH"));
            Log.e("VedioActivity" + "123456", " file.exists():" + file.exists());
            if (!file.exists())
                finish();
            else {
                Log.e("VedioActivity" + "123456", "file.getPath():" + file.getPath());
                videoView.setVideoPath(file.getPath());
                MediaController mediaController = new MediaController(this);            //创建MediaController对象
                videoView.setMediaController(mediaController);           //VideoView与MediaController建立关联
            }

        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    private void initFirstFrame() {
        File file = new File(VEDIO_SDK_PATH + File.separator + getIntent().getStringExtra("CONTENTPATH"));
        if (file.exists())
            ImageLoader.getInstance().displayImage(VEDIO_SHOW_THUMBNAIL_PATH + getIntent().getStringExtra("CONTENTPATH"), placeholder, ImageLoaderManager.getImageOptions());
        else {
            try {
                MediaMetadataRetriever mediaMetadataRetriever = new MediaMetadataRetriever();
                mediaMetadataRetriever.setDataSource(VEDIO_SDK_PATH + getIntent().getStringExtra("FILEPATH"));
                Bitmap bitmap = mediaMetadataRetriever.getFrameAtTime();
                placeholder.setImageBitmap(bitmap);
            } catch (RuntimeException e) {
                ivPlay.setImageResource(R.mipmap.vedio_lose);
                firstLayout.setClickable(false);
                firstLayout.setEnabled(false);
                LogUtil.logE("FunctionUtils" + "123456", "视频文件已损坏");
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        firstLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firstLayout.setVisibility(View.GONE);
                videoView.start();
            }
        });
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (null != videoView) {
            videoView.suspend();
        }
    }
}