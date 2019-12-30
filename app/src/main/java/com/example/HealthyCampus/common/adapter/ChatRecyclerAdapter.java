package com.example.HealthyCampus.common.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.HealthyCampus.R;
import com.example.HealthyCampus.common.data.Bean.ChatItemBean;
import com.example.HealthyCampus.common.record.MediaManager;
import com.example.HealthyCampus.common.utils.LogUtil;
import com.example.HealthyCampus.common.utils.SpanStringUtils;
import com.example.HealthyCampus.common.widgets.pullrecycler.BaseViewHolder;
import com.example.HealthyCampus.module.Message.Chat.Vedio.VedioActivity;
import com.example.HealthyCampus.module.Message.Chat.imageactivity.ImageActivity;
import com.example.HealthyCampus.module.Message.Chat.imageselect.ImageLoaderManager;
import com.example.HealthyCampus.module.Mine.User.UserInformationActivity;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.HealthyCampus.common.constants.ConstantValues.PICTURE_SDK_PATH;
import static com.example.HealthyCampus.common.constants.ConstantValues.PICTURE_SHOW_PATH;
import static com.example.HealthyCampus.common.constants.ConstantValues.VEDIO_SDK_PATH;
import static com.example.HealthyCampus.common.constants.ConstantValues.VEDIO_SHOW_THUMBNAIL_PATH;

/**
 * 聊天项适配器
 * created by Zhangguangte
 */

public class ChatRecyclerAdapter extends RecyclerView.Adapter<BaseViewHolder> {

    private Context context;
    private List<ChatItemBean> mData;
    private boolean finish = true, isPause = false;
    private int pos = -1;
    private ImageView audioImage;
    private onItemClick onItemClick;

    //文本
    private static final int SEND_TEXT = R.layout.chats_item_sender_text;
    private static final int RECEIVE_TEXT = R.layout.chats_item_receiver_text;
    //图片
    private static final int SEND_PICTURE = R.layout.chats_item_sender_picture;
    private static final int RECEIVE_PICTURE = R.layout.chats_item_receiver_picture;
    //视频
    private static final int SEND_VIDEO = R.layout.chats_item_sender_vedio;
    private static final int RECEIVE_VIDEO = R.layout.chats_item_receiver_vedio;
    //文件
    private static final int SEND_FILE = R.layout.chats_item_sender_file;
    private static final int RECEIVE_FILE = R.layout.chats_item_receiver_file;
    //录音
    private static final int SEND_RECORD = R.layout.chats_item_sender_record;
    private static final int RECEIVE_RECORD = R.layout.chats_item_receiver_record;
    //名片
    private static final int SEND_CARD = R.layout.chats_item_sender_card;
    private static final int RECEIVE_CARD = R.layout.chats_item_receiver_card;
    //地图
    private static final int SEND_MAP = R.layout.chats_item_sender_map;
    private static final int RECEIVE_MAP = R.layout.chats_item_receiver_map;

    public void clear() {
        mData.clear();
        notifyDataSetChanged();
    }


    public ChatRecyclerAdapter(List<ChatItemBean> data, Context context, onItemClick onItemClick) {
        this.mData = data;
        this.context = context;
        this.onItemClick = onItemClick;
    }


    @Override
    public int getItemViewType(int position) {
        if (mData.get(position).getType().equals("TEXT")) {                                           //文本
            return mData.get(position).isSelf() ? SEND_TEXT : RECEIVE_TEXT;
        } else if (mData.get(position).getType().equals("PICTURE")) {                              //图片
            return mData.get(position).isSelf() ? SEND_PICTURE : RECEIVE_PICTURE;
        } else if (mData.get(position).getType().equals("RECORD")) {                                  //录音
            return mData.get(position).isSelf() ? SEND_RECORD : RECEIVE_RECORD;
        } else if (mData.get(position).getType().equals("MAP")) {                                   //地图
            return mData.get(position).isSelf() ? SEND_MAP : RECEIVE_MAP;
        } else if (mData.get(position).getType().equals("CARD")) {                                 //名片
            return mData.get(position).isSelf() ? SEND_CARD : RECEIVE_CARD;
        } else if (mData.get(position).getType().equals("VEDIO")) {                                //视频
            return mData.get(position).isSelf() ? SEND_VIDEO : RECEIVE_VIDEO;
        } else {
            return mData.get(position).isSelf() ? SEND_FILE : RECEIVE_FILE;                             //文件
        }

    }

    @Override
    public int getItemCount() {
        if (null != mData && mData.size() > 0) {
            return mData.size();
        }
        return 0;
    }

    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(viewType, parent, false);
//        if (viewType == SEND_TEXT || viewType == SEND_PICTURE || viewType == SEND_RECORD || viewType == SEND_MAP || viewType == SEND_VIDEO || viewType == SEND_FILE)
//            new ProcessViewHolder(view);
        if (viewType == SEND_TEXT || viewType == RECEIVE_TEXT) {
            return new TextItemViewHolder(view);
        } else if (viewType == SEND_PICTURE || viewType == RECEIVE_PICTURE) {
            return new PictureItemViewHolder(view);
        } else if (viewType == SEND_RECORD || viewType == RECEIVE_RECORD) {
            return new RecordItemViewHolder(view);
        } else if (viewType == SEND_MAP || viewType == RECEIVE_MAP) {
            return new MapItemViewHolder(view);
        } else if (viewType == SEND_VIDEO || viewType == RECEIVE_VIDEO) {
            return new VedioItemViewHolder(view);
        } else if (viewType == SEND_FILE || viewType == RECEIVE_FILE) {
            return new FileItemViewHolder(view);
        } else if (viewType == SEND_CARD || viewType == RECEIVE_CARD) {
            return new CardItemViewHolder(view);
        }
        return new TextItemViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder holder, int position) {
        holder.onBindViewHolder(position);
        setStatus(holder, position);
    }

    private void setStatus(BaseViewHolder helper, int position) {
        if (mData.get(position).isSelf()) {
            if (mData.get(position).getSentstatus().equals("SENDING")) {
                helper.itemView.findViewById(R.id.chat_item_progress).setVisibility(View.VISIBLE);
                helper.itemView.findViewById(R.id.chat_item_fail).setVisibility(View.GONE);
            } else if (mData.get(position).getSentstatus().equals("FAILED")) {
                helper.itemView.findViewById(R.id.chat_item_progress).setVisibility(View.GONE);
                helper.itemView.findViewById(R.id.chat_item_fail).setVisibility(View.VISIBLE);
            } else if (mData.get(position).getSentstatus().equals("SENT")) {
                helper.itemView.findViewById(R.id.chat_item_progress).setVisibility(View.GONE);
                helper.itemView.findViewById(R.id.chat_item_fail).setVisibility(View.GONE);
            }
        }
    }

    public interface onItemClick {
        void loadPicture(String belongId, String filename, ImageView sivPicture);       //加载图片

        void btnDetail(String address, String location);                //查看定位详细

        void btnMap(String address, String location);                   //调用高德

        void loadMoreMessage();                   //加载更多消息

        void showToast(String message);

        void openFile(String path);

    }

    class TextItemViewHolder extends BaseViewHolder {       //文本
        @BindView(R.id.chat_text)
        TextView chat_text;

        TextItemViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

        @Override
        public void onBindViewHolder(int position) {
            chat_text.setText(SpanStringUtils.getEmojiContent(1, context, (int) (chat_text.getTextSize() * 12 / 10), mData.get(position).getContent()));

        }

        @Override
        public void onItemClick(View view, int position) {

        }
    }

    class PictureItemViewHolder extends BaseViewHolder {       //图片
        @BindView(R.id.chat_picture)
        ImageView chat_picture;

        PictureItemViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

        @Override
        public void onBindViewHolder(int position) {
            chat_picture.setOnClickListener(v -> {
                ArrayList<String> images = new ArrayList<>();                //以后修改为可以滑动，预先加载
                images.add(mData.get(position).getFile_path());
                Intent intent = new Intent(context, ImageActivity.class);
                intent.putExtra("curPosition", 0);
                intent.putStringArrayListExtra("images", images);
                context.startActivity(intent);
            });
            String url = mData.get(position).getFile_path();
            chat_picture.setTag(url);
            if (mData.get(position).getFile_path().contains("file:///"))
                ImageLoader.getInstance().displayImage(mData.get(position).getFile_path(), chat_picture, ImageLoaderManager.getImageOptions());
            else {
                File file = new File(PICTURE_SDK_PATH + mData.get(position).getFile_path());
                if (file.exists())
                    ImageLoader.getInstance().displayImage(PICTURE_SHOW_PATH + mData.get(position).getFile_path(), chat_picture, ImageLoaderManager.getImageOptions());
//                    new ImageShow().execute(PICTURE_SHOW_PATH + mData.get(position).getFile_path());
                else {
                    onItemClick.loadPicture(mData.get(position).getBelongId(), mData.get(position).getFile_path(), chat_picture);
                }
            }

        }

        @Override
        public void onItemClick(View view, int position) {

        }

//        class ImageShow extends AsyncTask<String, Void, Bitmap> {
//            @Override
//            protected Bitmap doInBackground(String... params) {
//                if (params.length > 0)
//                    return ImageLoader.getInstance().loadImageSync(params[0], options);
//                else
//                    return null;
//            }
//
//            @Override
//            protected void onPostExecute(Bitmap bitmap) {
//                super.onPostExecute(bitmap);
//                if (url.equals(chat_picture.getTag())) {
//                    chat_picture.setImageBitmap(bitmap);
//                }
//            }
//        }


    }

    class RecordItemViewHolder extends BaseViewHolder {       //图片
        @BindView(R.id.ivAudio)
        ImageView ivAudio;
        @BindView(R.id.tvDuration)
        TextView tvDuration;

        RecordItemViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

        @Override
        public void onBindViewHolder(int position) {
            tvDuration.setText(mData.get(position).getContent());
            ivAudio.setOnClickListener(v -> {
                if (TextUtils.isEmpty(mData.get(position).getFile_path())) {
                    onItemClick.showToast("录音资源丢失");
                    return;
                }
                if (!finish && pos == position) {
                    if (isPause) {
                        ivAudio.setImageResource(R.drawable.media_play);
                        AnimationDrawable animation = (AnimationDrawable) ivAudio.getDrawable();
                        animation.start();
                        MediaManager.resume();
                        isPause = false;
                    } else {
                        MediaManager.pause();
                        audioImage.setImageDrawable(context.getResources().getDrawable(R.drawable.sound_2));
                        isPause = true;
                    }
                } else {
                    MediaManager.release();
                    MediaManager.playSound(mData.get(position).getFile_path(), mp -> {
                        ivAudio.setImageDrawable(context.getResources().getDrawable(R.drawable.sound_all));
                        finish = true;
                        isPause = false;
                    });
                    ivAudio.setImageResource(R.drawable.media_play);
                    AnimationDrawable animation = (AnimationDrawable) ivAudio.getDrawable();
                    animation.start();
                    audioImage = ivAudio;
                    finish = false;
                    pos = position;
                }

            });
        }

        @Override
        public void onItemClick(View view, int position) {

        }
    }

    class MapItemViewHolder extends BaseViewHolder {       //地图
        @BindView(R.id.tvAddress)
        TextView tvAddress;
        @BindView(R.id.btnMap)
        Button btnMap;
        @BindView(R.id.btnDetail)
        Button btnDetail;

        MapItemViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

        @Override
        public void onBindViewHolder(int position) {
            tvAddress.setText(mData.get(position).getContent());
            btnMap.setOnClickListener(v -> onItemClick.btnDetail(mData.get(position).getContent(), mData.get(position).getFile_path()));
            btnDetail.setOnClickListener(v -> onItemClick.btnMap(mData.get(position).getContent(), mData.get(position).getFile_path()));
        }

        @Override
        public void onItemClick(View view, int position) {

        }
    }

    class CardItemViewHolder extends BaseViewHolder {
        @BindView(R.id.recommendIcon)
        ImageView recommendIcon;
        @BindView(R.id.recommendName)
        TextView recommendName;
        @BindView(R.id.recommendAccount)
        TextView recommendAccount;
        @BindView(R.id.cardLayout)
        LinearLayout cardLayout;

        CardItemViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

        @SuppressLint("SetTextI18n")
        @Override
        public void onBindViewHolder(int position) {
            String[] data = mData.get(position).getContent().split(",");    //数据分别为：0：头像；1：用户名；2：用户账号
            recommendName.setText(data[1]);
            recommendAccount.setText("账号:" + data[2]);
            //跳转至用户信息界面
            cardLayout.setOnClickListener(v -> {
                Intent intent = new Intent(context, UserInformationActivity.class);
                intent.putExtra("ACCOUNT", data[2]);
                context.startActivity(intent);
            });
        }

        @Override
        public void onItemClick(View view, int position) {

        }
    }

    class VedioItemViewHolder extends BaseViewHolder {
        @BindView(R.id.bivPic)
        ImageView bivPic;
        @BindView(R.id.chat_item_layout_content)
        RelativeLayout contentLayout;

        VedioItemViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

        @Override
        public void onBindViewHolder(int position) {
            ImageLoader.getInstance().displayImage(VEDIO_SHOW_THUMBNAIL_PATH + mData.get(position).getContent(), bivPic, ImageLoaderManager.getImageOptions());
            LogUtil.logE("ChatRecyclerAda" + "123456", "mData.get(position).getFile_path():" + mData.get(position).getFile_path());


            contentLayout.setOnClickListener(v -> {
                File file = new File(VEDIO_SDK_PATH + mData.get(position).getFile_path());
                if (!file.exists()) {
                    onItemClick.showToast("视频资源丢失");
                } else {
                    Intent intent = new Intent(context, VedioActivity.class);
                    intent.putExtra("FILEPATH", mData.get(position).getFile_path());
                    intent.putExtra("CONTENTPATH", mData.get(position).getContent());
                    context.startActivity(intent);
                }
            });


        }

        @Override
        public void onItemClick(View view, int position) {

        }
    }

    class FileItemViewHolder extends BaseViewHolder {
        @BindView(R.id.msg_tv_file_name)
        TextView tvfileName;
        @BindView(R.id.msg_tv_file_size)
        TextView tvFileSize;
        @BindView(R.id.rc_msg_iv_file_type_image)
        ImageView ivFileIcon;

        FileItemViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

        @Override
        public void onBindViewHolder(int position) {
            Log.e("ChatRecyclerA" + "123456", "mData.get(position):" + mData.get(position).toString());
            String[] data = mData.get(position).getContent().split(",");    //数据分别为：0：文件名；1：文件大小
            tvfileName.setText(data[0]);
            tvFileSize.setText(data[1]);
            ivFileIcon.setOnClickListener(v -> onItemClick.openFile(mData.get(position).getFile_path()));

        }

        @Override
        public void onItemClick(View view, int position) {

        }
    }

    @SuppressLint("StaticFieldLeak")
    class ImagesCount extends AsyncTask<String, Void, ArrayList<String>> {
        private int cur = 0;

        @Override
        protected ArrayList<String> doInBackground(String... params) {
            ArrayList<String> images = new ArrayList<>();
            images.add(params[0]);
            return images;
        }

        @Override
        protected void onPostExecute(ArrayList<String> strings) {
            super.onPostExecute(strings);
            Intent intent = new Intent(context, ImageActivity.class);
            intent.putExtra("curPosition", cur);
            intent.putStringArrayListExtra("images", strings);
            context.startActivity(intent);
        }
    }


}

