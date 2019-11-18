package com.example.HealthyCampus.common.adapter;

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
import android.widget.TextView;

import com.example.HealthyCampus.R;
import com.example.HealthyCampus.common.data.Bean.ChatItemBean;
import com.example.HealthyCampus.common.record.MediaManager;
import com.example.HealthyCampus.common.utils.DateUtils;
import com.example.HealthyCampus.common.utils.SpanStringUtils;
import com.example.HealthyCampus.common.utils.ToastUtil;
import com.example.HealthyCampus.common.widgets.chat.ChatStroke;
import com.example.HealthyCampus.common.widgets.custom_image.CircleImageView;
import com.example.HealthyCampus.common.widgets.pullrecycler.BaseViewHolder;
import com.example.HealthyCampus.module.Message.Chat.imageactivity.ImageActivity;
import com.example.HealthyCampus.module.Mine.User.UserInformationActivity;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.HealthyCampus.common.constants.ConstantValues.PICTURE_PATH;
import static com.example.HealthyCampus.common.constants.ConstantValues.PICTURE_SDK_PATH;
import static com.example.HealthyCampus.common.constants.ConstantValues.PICTURE_SHOW_PATH;


public class ChatRecyclerAdapter extends RecyclerView.Adapter<BaseViewHolder> {
    private Context context;
    private List<ChatItemBean> mData = new ArrayList<ChatItemBean>();
    private boolean finish = true, isPause = false;
    private int pos = -1;
    private ImageView view;
    private onItemClick onItemClick;
    private DisplayImageOptions options;
    private MediaManager mediaManager;

    public void addList(List<ChatItemBean> newList) {
        if (null != newList && newList.size() > 0) {
            mData.addAll(newList);
            notifyDataSetChanged();
        }
    }

    public void clear() {
        mData.clear();
        notifyDataSetChanged();
    }


    public void add(ChatItemBean chatItemBean) {
        if (null != chatItemBean) {
            mData.add(chatItemBean);
            notifyItemInserted(mData.size());
        }
    }

    public ChatRecyclerAdapter(List<ChatItemBean> data, Context context, onItemClick onItemClick) {
        this.mData = data;
        this.context = context;
        this.onItemClick = onItemClick;
    }

    public ChatRecyclerAdapter(Context context, MediaManager mediaManager, onItemClick onItemClick) {
        this.context = context;
        this.mediaManager = mediaManager;
        this.onItemClick = onItemClick;
        options = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.mipmap.picture_loading)
                .imageScaleType(ImageScaleType.EXACTLY_STRETCHED)
                .showImageForEmptyUri(R.mipmap.chatting_picture)
                .showImageOnFail(R.mipmap.chatting_picture)
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .considerExifParams(true)
                .build();
    }


    @Override
    public int getItemViewType(int position) {
        return mData.get(position).getDirection();
    }

    @Override
    public int getItemCount() {
        if (null != mData && mData.size() > 0) {
            return mData.size();
        }
        return 0;
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        if (viewType == ChatStroke.DIR_LEFT) {
            view = LayoutInflater.from(context).inflate(R.layout.chats_item_chatleft, parent, false);
        } else {
            view = LayoutInflater.from(context).inflate(R.layout.chats_item_chatright, parent, false);
        }
        return new ItemViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder holder, int position) {
        holder.onBindViewHolder(position);
    }

    public interface onItemClick {
        void loadPicture(String belongId, String filename, ImageView sivPicture);

        void btnDetail(String address, String location);

        void btnMap(String address, String location);
    }

    //展示的item
    class ItemViewHolder extends BaseViewHolder {
        //聊天总布局
        @BindView(R.id.chatLayout)
        LinearLayout chatLayout;
        //基本数据
        @BindView(R.id.chat_icon)
        CircleImageView chat_icon;               //用户头像
        @BindView(R.id.chat_time)
        TextView chat_time;                     //时间
        //文字与录音图片的布局
        @BindView(R.id.chat_cntlayout)
        LinearLayout chat_cntlayout;
        //文本
        @BindView(R.id.chat_text)
        TextView chat_text;                     //文本
        //录音图片
        @BindView(R.id.chat_image)
        ImageView chat_image;                   //播放录音图片
        //相片
        @BindView(R.id.chat_picture)
        ImageView chat_picture;                 //上传图片
        @BindView(R.id.csChat)
        ChatStroke csChat;
        //地图
        @BindView(R.id.mapLayout)
        LinearLayout mapLayout;                 //地图布局
        @BindView(R.id.tvAddress)
        TextView tvAddress;                     //地址
        @BindView(R.id.btnDetail)
        Button btnDetail;                       //查看详细
        @BindView(R.id.btnMap)
        Button btnMap;                          //调用高德
        //名片
        @BindView(R.id.cardLayout)
        LinearLayout cardLayout;                //名片布局
        @BindView(R.id.recommendIcon)
        CircleImageView recommendIcon;          //头像
        @BindView(R.id.recommendName)
        TextView recommendName;                 //名称
        @BindView(R.id.recommendAccount)
        TextView recommendAccount;              //账号


        public ItemViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

        @Override
        public void onBindViewHolder(int position) {
            Log.e("ChatAdapter" + "123456", "mData.get(position)：" + mData.get(position).toString());
            if (!TextUtils.isEmpty(mData.get(position).getTime()) && !mData.get(position).getTime().equals("刚刚")) {
                chat_time.setVisibility(View.VISIBLE);
                chat_time.setText(DateUtils.getTimeString(DateUtils.string2Date(mData.get(position).getTime()).getTime()));
            } else {
                chat_time.setVisibility(View.GONE);
            }
            switch (mData.get(position).getType()) {
                case "PICTURE":
                    csChat.setVisibility(View.GONE);
                    chat_picture.setVisibility(View.VISIBLE);
                    chat_picture.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            new ImagesCount().execute(position);
                        }
                    });

                    Log.e("MapActivity" + "123456", "mData.get(position).getFile_path()" + mData.get(position).getFile_path());

                    if (mData.get(position).getFile_path().contains("file:///"))
                        ImageLoader.getInstance().displayImage(mData.get(position).getFile_path(), chat_picture, options);
                    else {
                        File file = new File(PICTURE_SDK_PATH + mData.get(position).getFile_path());
                        Log.e("MapActivity" + "123456", "file" + file.exists());
                        Log.e("MapActivity" + "123456", "PICTURE_PATH" + PICTURE_PATH);
                        if (file.exists())
                            ImageLoader.getInstance().displayImage(PICTURE_SHOW_PATH + mData.get(position).getFile_path(), chat_picture, options);
                        else {
                            onItemClick.loadPicture(mData.get(position).getBelongId(), mData.get(position).getFile_path(), chat_picture);
                        }
//                        ImageLoader.getInstance().displayImage("file:///storage/emulated/0/health/photo/93ba7b60-05d0-4229-ac35-b6ad06c4bf31.jpg", chat_picture, options);
                    }
                    break;
                case "VOICE":
                    chat_picture.setVisibility(View.GONE);
                    chat_image.setVisibility(View.VISIBLE);
                    chat_image.setImageDrawable(context.getResources().getDrawable(R.drawable.sound_all));
                    chat_cntlayout.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (TextUtils.isEmpty(mData.get(position).getFile_path())) {
                                ToastUtil.show(context, "资源丢失");
                                return;
                            }
//                            Log.e("ChatAdapter" + "123456", "mData.get(position).getFile_path()：" + mData.get(position).getFile_path());

                            if (!finish && pos == position) {
                                if (isPause) {
                                    chat_image.setImageResource(R.drawable.media_play);
                                    AnimationDrawable animation = (AnimationDrawable) chat_image.getDrawable();
                                    animation.start();
                                    mediaManager.resume();
                                    isPause = false;
                                } else {
                                    mediaManager.pause();
                                    view.setImageDrawable(context.getResources().getDrawable(R.drawable.sound_2));
                                    isPause = true;
                                }
                            } else {
                                mediaManager.release();
                                if (view != null)
                                    view.setImageDrawable(context.getResources().getDrawable(R.drawable.sound_all));
                                mediaManager.playSound(mData.get(position).getFile_path(), new MediaPlayer.OnCompletionListener() {
                                    @Override
                                    public void onCompletion(MediaPlayer mp) {
                                        chat_image.setImageDrawable(context.getResources().getDrawable(R.drawable.sound_all));
                                        finish = true;
                                    }
                                });
                                chat_image.setImageResource(R.drawable.media_play);
                                AnimationDrawable animation = (AnimationDrawable) chat_image.getDrawable();
                                animation.start();
                                view = chat_image;
                                finish = false;
                                pos = position;
                            }

                        }
                    });
                    chat_text.setText(mData.get(position).getContent());
                    break;
                case "WORDS":
                    csChat.setVisibility(View.VISIBLE);
                    chat_picture.setVisibility(View.GONE);
                    chat_image.setVisibility(View.GONE);
                    chat_text.setText(SpanStringUtils.getEmojiContent(1, context, (int) (chat_text.getTextSize() * 15 / 10), mData.get(position).getContent()));
                    break;
                case "MAP":
                    csChat.setVisibility(View.GONE);
                    chat_picture.setVisibility(View.GONE);
                    mapLayout.setVisibility(View.VISIBLE);
                    tvAddress.setText(mData.get(position).getContent());
                    chat_text.setText(SpanStringUtils.getEmojiContent(1, context, (int) (chat_text.getTextSize() * 15 / 10), mData.get(position).getContent()));
                    btnDetail.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            onItemClick.btnDetail(mData.get(position).getContent(), mData.get(position).getFile_path());
                        }
                    });
                    btnMap.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            onItemClick.btnMap(mData.get(position).getContent(), mData.get(position).getFile_path());
                        }
                    });
                    break;
                case "CARD":
                    csChat.setVisibility(View.GONE);
                    cardLayout.setVisibility(View.VISIBLE);
                    String[] data = mData.get(position).getContent().split(",");    //数据分别为：0：头像；1：用户名；2：用户账号
                    recommendName.setText(data[1]);
                    recommendAccount.setText("账号:" + data[2]);

                    cardLayout.setOnClickListener(new View.OnClickListener() {  //跳转至用户信息界面
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(context, UserInformationActivity.class);
                            intent.putExtra("ACCOUNT", data[2]);
                            context.startActivity(intent);
                        }
                    });


                    break;
                default:
                    chat_time.setText(mData.get(position).getContent());
                    chat_time.setVisibility(View.VISIBLE);
                    chatLayout.setVisibility(View.GONE);
                    break;
            }

        }

        @Override
        public void onItemClick(View view, int position) {
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
        }
    }

    private class ImagesCount extends AsyncTask<Integer, Void, ArrayList<String>> {
        private int cur = 0;

        @Override
        protected ArrayList<String> doInBackground(Integer... params) {
            if (params.length >= 1) {
                cur = params[0];
//                for (Integer pa : params) {
//                    Log.v("ChatAdapter123456", "\npa：" + pa);
//                }
            }
            ArrayList<String> images = new ArrayList<String>();
            images.add(mData.get(cur).getFile_path());
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

