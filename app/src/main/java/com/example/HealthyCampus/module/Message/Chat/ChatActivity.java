package com.example.HealthyCampus.module.Message.Chat;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.HealthyCampus.R;
import com.example.HealthyCampus.common.adapter.ChatRecyclerAdapter;
import com.example.HealthyCampus.common.adapter.EmojiPagerAdapter;
import com.example.HealthyCampus.common.data.Bean.ChatItemBean;
import com.example.HealthyCampus.common.data.form.MapForm;
import com.example.HealthyCampus.common.data.form.RequestForm;
import com.example.HealthyCampus.common.helper.SPHelper;
import com.example.HealthyCampus.common.network.vo.DefaultResponseVo;
import com.example.HealthyCampus.common.network.vo.UserVo;
import com.example.HealthyCampus.common.record.MediaManager;
import com.example.HealthyCampus.common.record.RecordTextView;
import com.example.HealthyCampus.common.utils.JsonUtil;
import com.example.HealthyCampus.common.utils.PackageManagerUtil;
import com.example.HealthyCampus.common.utils.PictureUtil;
import com.example.HealthyCampus.common.utils.StatusBarUtil;
import com.example.HealthyCampus.common.utils.ToastUtil;
import com.example.HealthyCampus.common.widgets.chat.ChatStroke;
import com.example.HealthyCampus.common.widgets.pullrecycler.layoutmanager.MyLinearLayoutManager;
import com.example.HealthyCampus.framework.BaseActivity;
import com.example.HealthyCampus.module.Message.Address_list.AddressListActivity;
import com.example.HealthyCampus.module.Message.Chat.Map.MapActivity;
import com.example.HealthyCampus.module.Message.Chat.imageselect.ImageSelectorActivity;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import butterknife.BindView;
import butterknife.OnClick;
import retrofit2.Response;
import retrofit2.adapter.rxjava.HttpException;

import static com.example.HealthyCampus.common.constants.ConstantValues.MAP_LOCATION;
import static com.example.HealthyCampus.common.constants.ConstantValues.PICK_PHOTO;
import static com.example.HealthyCampus.common.constants.ConstantValues.PICTURE_PATH;
import static com.example.HealthyCampus.common.constants.ConstantValues.TAKE_PHOTO;
import static com.example.HealthyCampus.common.constants.ConstantValues.USER_CARD;


public class ChatActivity extends BaseActivity<ChatContract.View, ChatContract.Presenter> implements ChatContract.View, ChatRecyclerAdapter.onItemClick {


    @BindView(R.id.tvTitle)
    TextView tvTitle;
    @BindView(R.id.ivBack)
    ImageView ivBack;
    @BindView(R.id.btnSend)
    Button btnSend;
    @BindView(R.id.tvSend)
    TextView tvSend;
    @BindView(R.id.etChat)
    EditText etChat;
    @BindView(R.id.rvChatting)
    RecyclerView rvChatting;

    @BindView(R.id.mEmojiLayout)
    LinearLayout mEmojiLayout;
    @BindView(R.id.mPlusLayout)
    LinearLayout mPlusLayout;
    @BindView(R.id.mRecordText)
    RecordTextView mRecordText;
    @BindView(R.id.vpChat)
    ViewPager vpChat;
    @BindView(R.id.ivEmoji)
    ImageView ivEmoji;
    @BindView(R.id.ivRecord)
    ImageView ivRecord;
    @BindView(R.id.ivPlus)
    ImageView ivPlus;
    @BindView(R.id.CameraLayout)
    LinearLayout CameraLayout;
    @BindView(R.id.AlbumLayout)
    LinearLayout AlbumLayout;
    @BindView(R.id.chatLayout)
    LinearLayout chatLayout;
    @BindView(R.id.PositionLayout)
    LinearLayout PositionLayout;
    @BindView(R.id.CardLayout)
    LinearLayout CardLayout;

    private InputMethodManager mImm;
    private boolean first = true;
    private String roomId;
    private StringBuilder stringBuilder = new StringBuilder();
    private Uri imageUri;
    private List<ChatItemBean> chatItemBeanList = new ArrayList<>();
    private ChatRecyclerAdapter mAdapter;
    private Animation mHiddenAction;
    private MediaManager mediaManager;
    private Integer row = 0;

    @Override
    protected void setUpContentView() {
        setContentView(R.layout.message_chat);
    }

    @Override
    protected ChatContract.Presenter createPresenter() {
        return new ChatPresenter();
    }

    @Override
    protected void initView() {
        setCustomActionBar();
        initRecyclerView();     //初始化聊天项
        initEdit();             //编辑
        initEmoji();            //表情面板
        initRecord();           //录音
        initPlus();             //更多,未来添加文件传输
    }

    //状态栏为青色
    //重写去除沉浸式，因为沉浸式和键盘等会冲突
    @Override
    protected void initImmersionBar() {
        StatusBarUtil.setStatusBarColor(this);
    }

    private void initRecyclerView() {
        //音频
        mediaManager = new MediaManager();
        mAdapter = new ChatRecyclerAdapter(this, mediaManager, this);
        rvChatting.setLayoutManager(new MyLinearLayoutManager(getContext()));
        rvChatting.setHasFixedSize(true);
        rvChatting.setItemViewCacheSize(15);
        rvChatting.setAdapter(mAdapter);
        roomId = getIntent().getExtras().getString("roomid");
        if (TextUtils.isEmpty(roomId)) {
            String uid = getIntent().getExtras().getString("uid");
            RequestForm requestForm = new RequestForm(String.valueOf(uid), row++);
            mPresenter.searchRoomid(requestForm);
        } else {
            RequestForm requestForm = new RequestForm(String.valueOf(roomId), row++);
            mPresenter.allChatByRoomId(requestForm);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        mediaManager.release();
    }

    //设置房间号
    @Override
    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    //返回数据：成功：加载图片
    @Override
    public void loadPictureSuccess(ImageView sivPicture, String result) {
        List<Object> list = new ArrayList<>();
        Message message = mHandler.obtainMessage();
        list.add(sivPicture);
        list.add(result);
        message.what = 1;
        message.obj = list;
        mHandler.sendMessage(message);
    }

    //返回数据：失败：加载图片
    @Override
    public void loadPictureFail(ImageView sivPicture) {
        Message message = mHandler.obtainMessage();
        message.what = 2;
        message.obj = sivPicture;
        mHandler.sendMessage(message);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        mHiddenAction = AnimationUtils.loadAnimation(this, R.anim.push_up_out);
        //软键盘
        mImm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
    }

    //editView获得聚焦，防止软键盘和底部控件所造成的布局变化冲突
    private void setEditFocus() {
        etChat.setFocusable(true);
        etChat.setFocusableInTouchMode(true);
        etChat.requestFocus();
    }

    //隐藏软键盘
    private void softInputHide() {
        setEditFocus();
        mImm.hideSoftInputFromWindow(etChat.getWindowToken(), 0);
    }

    //显示软键盘
    private void softInputShow() {
        setEditFocus();
        mImm.showSoftInput(etChat, InputMethodManager.SHOW_FORCED);
    }

    @Override
    public Context getContext() {
        return this;
    }

    //返回数据：失败
    @Override
    public void showError(Throwable throwable) {
        if (throwable instanceof HttpException) {
            Response httpException = ((HttpException) throwable).response();
            try {
                DefaultResponseVo response = JsonUtil.format(httpException.errorBody().string(), DefaultResponseVo.class);
                if (response.code == 1006) {
                    Log.e("ChatActivity" + "123456", "response.toString:" + response.toString());
                    Log.e("ChatActivity" + "123456", "response.code" + response.code);
                    ToastUtil.show(this, "无数据");
                } else {
                    ToastUtil.show(this, "未知错误1:" + throwable.getMessage());
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            ToastUtil.show(this, "未知错误2:" + throwable.getMessage());
        }
        Log.e("ChatActivity" + "123456", "throwable.getMessage:" + throwable.getMessage());
        Log.e("ChatActivity" + "123456", "throwable.toString:" + throwable.toString());
        Log.e("ChatActivity" + "123456", "throwable.getCause:" + throwable.getCause());
    }

    @Override
    public void showRecyclerView(List<ChatItemBean> messageListVos) {
        chatItemBeanList = messageListVos;
        mAdapter.addList(messageListVos);
        rvChatting.smoothScrollToPosition(chatItemBeanList.size() > 0 ? chatItemBeanList.size() - 1 : 0);
    }

    private void initEdit() {
        //内容变化
        etChat.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                showBtnOrIv(s.length() > 0);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        //点击事件
        etChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideAll();
                etChat.setVisibility(View.VISIBLE);
                softInputShow();
                rvChatting.smoothScrollToPosition(chatItemBeanList.size() > 0 ? chatItemBeanList.size() - 1 : 0);

            }
        });

        //点击RecycleView，隐藏软键盘和底部布局
//        rvChatting.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                hideAll();
//            }
//        });

//        chatLayout.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
//            @Override
//            public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
//                if (oldBottom != -1 && oldBottom > bottom) {
//                    rvChatting.requestLayout();
//                    rvChatting.post(new Runnable() {
//                        @Override
//                        public void run() {
//                            Log.e("ChatActivity" + "123456", "bottom" + bottom);
//                            Log.e("ChatActivity" + "123456", "oldBottom" + oldBottom);
//                            rvChatting.smoothScrollToPosition(chatItemBeanList.size() > 0 ? chatItemBeanList.size() - 1 : 0);
//                        }
//                    });
//                }
//            }
//        });
        //聊天RecycleView布局变化的监听，数据项移动到底部，解决底部布局的遮盖
        rvChatting.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
            @Override
            public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
                if (oldBottom != -1 && oldBottom > bottom) {
                    rvChatting.requestLayout();
                    rvChatting.smoothScrollToPosition(chatItemBeanList.size() > 0 ? chatItemBeanList.size() - 1 : 0);
                }
            }
        });

        setEditFocus();
    }

    //更多和发送显隐
    private void showBtnOrIv(boolean val) {
        if (val) {
            ivPlus.setVisibility(View.GONE);
            btnSend.setVisibility(View.VISIBLE);
        } else {
            ivPlus.setVisibility(View.VISIBLE);
            btnSend.setAnimation(mHiddenAction);
            btnSend.setVisibility(View.GONE);
        }
    }

    //隐藏所有底部数据
    public void hideAll() {
//        softInputHide();
        mEmojiLayout.setVisibility(View.GONE);
        mRecordText.setVisibility(View.GONE);
        etChat.setVisibility(View.GONE);
        mPlusLayout.setVisibility(View.GONE);
    }

    private void setCustomActionBar() {
        tvTitle.setText(getIntent().getExtras().getString("anotherName"));
        ivBack.setVisibility(View.VISIBLE);
    }

    @OnClick(R.id.ivBack)
    public void ivBack(View view) {
        finish();
    }

    //添加文字项
    public void addChatItem(String text) {
        addRecordItem(text, null);
    }

    //添加录音项
    public void addRecordItem(String text, String filePath) {
        ChatItemBean chatItem = new ChatItemBean();
        chatItem.setContent(text);
        chatItem.setDirection(ChatStroke.DIR_RIGHT);
        chatItem.setType("WORDS");
        if (null != filePath) {
            chatItem.setType("VOICE");
            chatItem.setFile_path(filePath);
        }
        if (first) {
            chatItem.setTime("刚刚");
            first = false;
        }
        mAdapter.add(chatItem);
        chatItemBeanList.add(chatItem);
        rvChatting.smoothScrollToPosition(chatItemBeanList.size() > 0 ? chatItemBeanList.size() - 1 : 0);
    }

    private void initEmoji() {
        vpChat.setAdapter(new EmojiPagerAdapter(this, etChat));
        ivEmoji.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mEmojiLayout.getVisibility() == View.GONE) {
                    hideAll();
                    etChat.setVisibility(View.VISIBLE);
                    mEmojiLayout.setVisibility(View.VISIBLE);
                    softInputHide();
                } else {
                    hideAll();
                    etChat.setVisibility(View.VISIBLE);
                    mEmojiLayout.setVisibility(View.GONE);
                }
                etChat.setVisibility(View.VISIBLE);
                rvChatting.smoothScrollToPosition(chatItemBeanList.size() > 0 ? chatItemBeanList.size() - 1 : 0);
            }
        });
    }

    //发送消息
    @OnClick({R.id.btnSend, R.id.tvSend})
    public void sendMessage(View view) {
        String str = etChat.getText().toString().trim();
        if (str == null || "".equals(str)) {
            Toast.makeText(ChatActivity.this, "请输入内容", Toast.LENGTH_SHORT).show();
            return;
        }
        addChatItem(str);
        mPresenter.insertText(str, roomId);
        etChat.setText("");
    }

    //初始化录音
    private void initRecord() {
        mRecordText.setOnFinishRecordListener(new RecordTextView.OnFinishRecordListener() {
            @Override
            public void onFinish(int time, String filePath) {
                addRecordItem(time + "s", filePath);
                mPresenter.insertRecord(time + "s", roomId, filePath);
            }
        });
        ivRecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mRecordText.getVisibility() == View.GONE) {
                    hideAll();                                                  //隐藏所有底部视图
                    ivRecord.setImageResource(R.mipmap.chatting_softkeyboard);
                    mRecordText.setVisibility(View.VISIBLE);
                    btnSend.setVisibility(View.GONE);
                    ivPlus.setVisibility(View.VISIBLE);
                    softInputHide();                                            //隐藏软键盘
                } else {
                    hideAll();
                    etChat.setVisibility(View.VISIBLE);
                    ivRecord.setImageResource(R.mipmap.chatting_vodie);
                    etChat.setVisibility(View.VISIBLE);
                    btnSend.setVisibility(etChat.getText().toString().trim().length() > 0 ? View.VISIBLE : View.GONE);
                    ivPlus.setVisibility(etChat.getText().toString().trim().length() > 0 ? View.GONE : View.VISIBLE);
                }
            }
        });
    }


    private void initPlus() {
        //更多点击事件
        ivPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //注意：
                // 不要取出来放前面或后面。
//                hideAll();                  隐藏了判断语句
//                etChat.setVisibility(View.VISIBLE);       //对于软键盘，会把editView的焦距去除，导致之后软键盘和底部视图一起出现

                if (mPlusLayout.getVisibility() == View.GONE) {
                    hideAll();
                    etChat.setVisibility(View.VISIBLE);
                    mPlusLayout.setVisibility(View.VISIBLE);
                    softInputHide();
//                    showPlusLayout();
                } else {
                    hideAll();
                    etChat.setVisibility(View.VISIBLE);
                    mPlusLayout.setVisibility(View.GONE);
                }
                rvChatting.smoothScrollToPosition(chatItemBeanList.size() > 0 ? chatItemBeanList.size() - 1 : 0);
            }
        });
        //照相机
        CameraLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                takePhoto(v);
            }
        });
        //相册
        AlbumLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickPhoto(v);
            }
        });
        //定位
        PositionLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(ChatActivity.this, MapActivity.class), MAP_LOCATION);
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            }
        });
        //名片
        CardLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ChatActivity.this, AddressListActivity.class);
                Bundle bundle = new Bundle();
                bundle.putBoolean("chat", true);
                intent.putExtras(bundle);
                startActivityForResult(intent, USER_CARD);
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            }
        });
    }

    //拍照
    private void takePhoto(View view) {
        String dirStr = PICTURE_PATH;
        File dir = new File(dirStr);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        File outputImage = new File(dirStr, getFileName());
        if (outputImage.exists()) {
            outputImage.delete();
        }
        try {
            outputImage.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
//        Log.e("ChatActivity123456", "imageUri:" + imageUri + "\n|outputImage:" + outputImage + "\n|getFileName():" + getFileName() + "\n|dirStr:" + dirStr);
        imageUri = Uri.fromFile(outputImage);
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        startActivityForResult(intent, TAKE_PHOTO);
    }

    //随机相册名
    public String getFileName() {
        return UUID.randomUUID().toString().substring(5) + ".jpg";
    }

    //跳转：挑选相册
    public void pickPhoto(View view) {
        Intent intent = new Intent(this, ImageSelectorActivity.class);
        startActivityForResult(intent, PICK_PHOTO);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case TAKE_PHOTO:        //拍照回调
                if (RESULT_OK == resultCode) {
                    addPictureItem(imageUri.toString());
                    String[] filename = imageUri.toString().split("/");
                    mPresenter.insertPicture("", roomId, filename[filename.length - 1]);
                    mPresenter.upPicture(imageUri.toString(), filename[filename.length - 1], SPHelper.getString(SPHelper.USER_ID));
                }
                break;
            case PICK_PHOTO:       //相册回调
                if (RESULT_OK == resultCode) {
                    List<String> images = data.getStringArrayListExtra("images");
                    String content;
                    String names[];
                    for (String uri : images) {
                        addPictureItem(uri);
                        content = UUID.randomUUID().toString().substring(0, 5);
                        names = uri.split("/");
                        content += "_" + names[names.length - 1];
                        PictureUtil.saveBmpToSd(PictureUtil.fileTobitmap(uri), content, 80, true);
                        mPresenter.insertPicture("", roomId, content);
                        mPresenter.upPicture(uri, content, SPHelper.getString(SPHelper.USER_ID));
                    }
                }
                break;
            case MAP_LOCATION:      //定位回调
                if (RESULT_OK == resultCode) {
                    MapForm mapForm = (MapForm) data.getParcelableExtra("map");
                    Log.e("ChatActivity" + "123456", "mapForm.toString " + mapForm.toString());
                    addMapItem(mapForm);
                    stringBuilder.setLength(0);
                    mPresenter.insertMap(mapForm.address, roomId, stringBuilder.append(mapForm.latLng.latitude + "," + mapForm.latLng.longitude).toString());
                }
                break;
            case USER_CARD:      //名片回调
                if (RESULT_OK == resultCode) {
                    String account = data.getStringExtra("ACCOUNT");
                    Log.e("ChatActivity" + "123456", "account" + account);
                    mPresenter.insertCard(account, roomId);
                }
                break;
            default:
                Log.e("ChatActivity" + "123456", "requestCode = " + requestCode);
                break;
        }
        mPlusLayout.setVisibility(View.GONE);
        super.onActivityResult(requestCode, resultCode, data);
    }

    //添加：图片项
    public void addPictureItem(String imageUri) {
        ChatItemBean chatItem = new ChatItemBean();
        chatItem.setDirection(ChatStroke.DIR_RIGHT);
        chatItem.setFile_path(imageUri);
        chatItem.setType("PICTURE");
        mAdapter.add(chatItem);
        chatItemBeanList.add(chatItem);
        rvChatting.smoothScrollToPosition(chatItemBeanList.size() > 0 ? chatItemBeanList.size() - 1 : 0);
    }

    //添加：图片项
    @Override
    public void addCardItem(UserVo userVo) {
        ChatItemBean chatItem = new ChatItemBean();
        chatItem.setDirection(ChatStroke.DIR_RIGHT);
        chatItem.setType("CARD");
        chatItem.setContent(userVo.avatar + "," + userVo.nickname + "," + userVo.account);
        mAdapter.add(chatItem);
        chatItemBeanList.add(chatItem);
        rvChatting.smoothScrollToPosition(chatItemBeanList.size() > 0 ? chatItemBeanList.size() - 1 : 0);
    }

    //添加：地图项
    public void addMapItem(MapForm mapForm) {
        ChatItemBean chatItem = new ChatItemBean();
        chatItem.setDirection(ChatStroke.DIR_RIGHT);
        chatItem.setContent(mapForm.address);
        stringBuilder.setLength(0);
        chatItem.setFile_path(stringBuilder.append(mapForm.latLng.latitude + "," + mapForm.latLng.longitude).toString());
        chatItem.setType("MAP");
        mAdapter.add(chatItem);
        chatItemBeanList.add(chatItem);
        rvChatting.smoothScrollToPosition(chatItemBeanList.size() > 0 ? chatItemBeanList.size() - 1 : 0);
    }

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:             //成功：图片显示
                    List<Object> list = (List<Object>) msg.obj;
                    ImageView sivPicture = (ImageView) list.get(0);
                    sivPicture.setImageBitmap(PictureUtil.stringToBitmap(String.valueOf(list.get(1))));
                    break;
                case 2:             //失败：图片显示
                    ImageView sivPicture1 = (ImageView) msg.obj;
                    sivPicture1.setImageDrawable(getResources().getDrawable(R.mipmap.picture_lose));
                    break;
            }
        }
    };

    //回调：加载图片
    @Override
    public void loadPicture(String belongId, String filename, ImageView sivPicture) {
        mPresenter.loadPicture(belongId, filename, sivPicture);
    }

    //回调：地图详细
    @Override
    public void btnDetail(String address, String location) {
        Intent intent = new Intent(this, MapActivity.class);
        MapForm mapForm = new MapForm(address, location);
        Log.e("ChatActivity" + "123456", "mapForm" + mapForm.toString());
        Bundle bundle = new Bundle();
        bundle.putParcelable("map", mapForm);
        intent.putExtras(bundle);
        startActivityForResult(intent, 0);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }

    //回调：调用：高德地图（经纬度，地址）
    @Override
    public void btnMap(String address, String location) {
        String loca[] = location.split(",");
//        Log.e("ChatActivity" + "123456", "loca[0]" + loca[0]);
//        Log.e("ChatActivity" + "123456", "loca[1]" + loca[1]);
//        Log.e("ChatActivity" + "123456", "location" + location);
        float j, w;
        j = Float.valueOf(loca[0]);
        w = Float.valueOf(loca[1]);
        try {
            // 高德地图 先维度——后经度
            Intent intent = Intent
                    .getIntent("androidamap://viewMap?sourceApplication=校园健康"
                            + "&poiname="
                            + address
                            + "&lat="
                            + j
                            + "&lon="
                            + w
                            + "&dev=0");
            if (PackageManagerUtil.haveGaodeMap()) {
                startActivity(intent);
                ToastUtil.show(getContext(), "高德地图正在启动");
            } else {
                ToastUtil.show(getContext(), "高德地图没有安装");
                Intent i = new Intent();
                i.setData(Uri.parse("http://daohang.amap.com/index.php?id=201&CustomID=C021100013023"));
                i.setAction(Intent.ACTION_VIEW);
                this.startActivity(i); //启动浏览器
            }
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }


}
