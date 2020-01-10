package com.example.HealthyCampus.module.Message.Chat;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SimpleItemAnimator;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.HealthyCampus.R;
import com.example.HealthyCampus.application.HealthApp;
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
import com.example.HealthyCampus.common.utils.DensityUtil;
import com.example.HealthyCampus.common.utils.FileUtils;
import com.example.HealthyCampus.common.utils.FunctionUtils;
import com.example.HealthyCampus.common.utils.JsonUtil;
import com.example.HealthyCampus.common.utils.PictureUtil;
import com.example.HealthyCampus.common.utils.StatusBarUtil;
import com.example.HealthyCampus.common.utils.ToastUtil;
import com.example.HealthyCampus.common.widgets.chat.ChatStroke;
import com.example.HealthyCampus.common.widgets.custom_linearlayout.IndicatorView;
import com.example.HealthyCampus.framework.BaseActivity;
import com.example.HealthyCampus.module.Message.Address_list.AddressListActivity;
import com.example.HealthyCampus.module.Message.Chat.Map.MapActivity;
import com.example.HealthyCampus.module.Message.Chat.imageselect.ImageSelectorActivity;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.entity.LocalMedia;
import com.nbsp.materialfilepicker.ui.FilePickerActivity;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import butterknife.BindView;
import butterknife.OnClick;
import retrofit2.Response;
import retrofit2.adapter.rxjava.HttpException;

import static com.example.HealthyCampus.common.constants.ConstantValues.FILE_SDK_PATH;
import static com.example.HealthyCampus.common.constants.ConstantValues.MAP_LOCATION;
import static com.example.HealthyCampus.common.constants.ConstantValues.PICK_FILE;
import static com.example.HealthyCampus.common.constants.ConstantValues.PICK_PHOTO;
import static com.example.HealthyCampus.common.constants.ConstantValues.PICK_VEDIO;
import static com.example.HealthyCampus.common.constants.ConstantValues.PICTURE_PATH;
import static com.example.HealthyCampus.common.constants.ConstantValues.TAKE_PHOTO;
import static com.example.HealthyCampus.common.constants.ConstantValues.USER_CARD;
import static com.example.HealthyCampus.common.helper.SPHelper.IS_EXCEED_SCEEN;
import static com.example.HealthyCampus.common.helper.SPHelper.SOFT_INPUT_HEIGHT;
import static com.example.HealthyCampus.common.record.MediaManager.release;


public class ChatActivity extends BaseActivity<ChatContract.View, ChatContract.Presenter> implements ChatContract.View, ChatRecyclerAdapter.onItemClick, SwipeRefreshLayout.OnRefreshListener {

    //自定义标题栏
    @BindView(R.id.tvTitle)
    TextView tvTitle;
    @BindView(R.id.ivBack)
    ImageView ivBack;
    //发送
    @BindView(R.id.btnSend)
    Button btnSend;
    @BindView(R.id.tvSend)
    TextView tvSend;
    //编辑框
    @BindView(R.id.etChat)
    EditText etChat;

    @BindView(R.id.contentLayout)
    LinearLayout mContentLayout;

    //聊天视图
    @BindView(R.id.swipe_chat)
    SwipeRefreshLayout mSwipeRefresh;//下拉刷新
    @BindView(R.id.rvChatting)
    RecyclerView rvChatting;
    //底部视图
    @BindView(R.id.BottomLayout)
    RelativeLayout mBottomLayout;
    @BindView(R.id.mEmojiLayout)
    LinearLayout mEmojiLayout;              //表情面板
    @BindView(R.id.mPlusLayout)
    GridLayout mPlusLayout;              //更多面板
    @BindView(R.id.mRecordText)
    RecordTextView mRecordText;           //录音按键
    //表情视图
    @BindView(R.id.vpEmoji)
    ViewPager vpEmoji;
    @BindView(R.id.ind_emoji)
    IndicatorView indEmoji;
    //底部图标
    @BindView(R.id.ivEmoji)
    ImageView ivEmoji;
    @BindView(R.id.ivRecord)
    ImageView ivRecord;
    @BindView(R.id.ivPlus)
    ImageView ivPlus;
    //更多面板内功能
    @BindView(R.id.CameraLayout)
    LinearLayout CameraLayout;              //相机
    @BindView(R.id.AlbumLayout)
    LinearLayout AlbumLayout;               //相册
    @BindView(R.id.PositionLayout)
    LinearLayout PositionLayout;            //定位
    @BindView(R.id.CardLayout)
    LinearLayout CardLayout;                //名片
    @BindView(R.id.FileLayout)
    LinearLayout FileLayout;                //文件
    @BindView(R.id.VedioLayout)
    LinearLayout VedioLayout;                //视频
    private InputMethodManager mImm;        //软键盘
    private boolean isMoreLoad = true;      //头部加载更多
    private String roomId;
    private StringBuilder stringBuilder = new StringBuilder();
    private Uri imageUri;
    private Animation mHiddenAction;
    //聊天数据、适配器
    private List<ChatItemBean> chatItemBeanList = new ArrayList<>();
    private ChatRecyclerAdapter mAdapter;
    private Integer row = 0;                //加载数量
    private LinearLayoutManager manager;

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
        setCustomActionBar();    //标题
        initRecyclerView();     //初始化聊天项
        initEdit();             //编辑
        initEmoji();            //表情面板
        initRecord();           //录音
        initPlus();             //更多面板
    }

    //状态栏为青色
    //重写去除沉浸式，因为沉浸式和键盘等会冲突
    @Override
    protected void initImmersionBar() {
        StatusBarUtil.setStatusBarColor(this);
    }

    @SuppressLint("ClickableViewAccessibility")
    private void initRecyclerView() {
        //音频
        //用于activity结束时
        mSwipeRefresh.setOnRefreshListener(this);
        mAdapter = new ChatRecyclerAdapter(chatItemBeanList, HealthApp.getAppContext(), this);
        manager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
//        manager.setStackFromEnd(true);
        rvChatting.setLayoutManager(manager);
        rvChatting.setHasFixedSize(true);
        rvChatting.setItemViewCacheSize(15);
        ((SimpleItemAnimator) Objects.requireNonNull(rvChatting.getItemAnimator())).setSupportsChangeAnimations(false);
        rvChatting.setAdapter(mAdapter);
        if (null == getIntent().getExtras())
            finish();
        roomId = getIntent().getExtras().getString("roomid");
        if (TextUtils.isEmpty(roomId)) {
            String uid = getIntent().getExtras().getString("uid");
            RequestForm requestForm = new RequestForm(String.valueOf(uid), row++);
            mPresenter.searchRoomid(requestForm);
        } else {
            loadMoreMessage();
        }
        //聊天RecycleView布局变化的监听，数据项移动到底部，解决底部布局的遮盖
        rvChatting.addOnLayoutChangeListener((v, left, top, right, bottom, oldLeft, oldTop, oldRight, oldBottom) -> {

            if (oldBottom != -1 && oldBottom > bottom) {
                rvChatting.smoothScrollToPosition(chatItemBeanList.size() > 0 ? chatItemBeanList.size() - 1 : 0);
            }
        });

        //点击空白区域关闭键盘
        rvChatting.setOnTouchListener((view, motionEvent) -> {
            hideBottomLayout(false);
            hideSoftInput();
            etChat.clearFocus();
            ivEmoji.setImageResource(R.mipmap.chatting_emoticons);
            return false;
        });

    }

    @Override
    protected void onPause() {
        super.onPause();
        release();
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
        mImm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);  //软键盘
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
                switch (response.code) {
                    case 1006:
                        mSwipeRefresh.setEnabled(false);
                        if (1 != row)
                            ToastUtil.show(this, "无数据");
                        break;
                    case 1010:
                        ToastUtil.show(getContext(), "暂无好友关系");
                        finish();
                        break;
                    case 1000:
                        ToastUtil.show(getContext(), "Bad Server");
                        break;
                    case 1003:
                        ToastUtil.show(getContext(), "Invalid Parameter");
                        break;
                    default:
                        ToastUtil.show(getContext(), "未知错误1:" + throwable.getMessage());
                        break;
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            ToastUtil.show(this, "未知错误2:" + throwable.getMessage());
        }
        mSwipeRefresh.setRefreshing(false);
    }


    @Override
    public void showRecyclerView(List<ChatItemBean> messageListVos) {
        chatItemBeanList.addAll(0, messageListVos);
        mAdapter.notifyItemRangeInserted(0, messageListVos.size());
        if (isMoreLoad) {
            if (SPHelper.getBoolean(IS_EXCEED_SCEEN, false)) {
                manager.setStackFromEnd(true);
            }
            rvChatting.setLayoutManager(manager);
            isMoreLoad = false;
        } else
            ToastUtil.show(getContext(), R.string.loading_success);
        mSwipeRefresh.setRefreshing(false);
    }


    @SuppressLint("ClickableViewAccessibility")
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

        etChat.setOnTouchListener((v, event) -> {
            if (event.getAction() == MotionEvent.ACTION_UP && mBottomLayout.isShown()) {
                lockContentHeight();//显示软件盘时，锁定内容高度，防止跳闪。
                hideBottomLayout(true);//隐藏表情布局，显示软件盘
                ivEmoji.setImageResource(R.mipmap.chatting_emoticons);
                //软件盘显示后，释放内容高度
                etChat.postDelayed(this::unlockContentHeightDelayed, 200L);
            }
            return false;
        });
        etChat.setFocusable(true);
        etChat.setFocusableInTouchMode(true);
        etChat.requestFocus();
    }

    /**
     * 锁定内容高度，防止跳闪
     */
    private void lockContentHeight() {
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) mContentLayout.getLayoutParams();
        params.height = mContentLayout.getHeight();
        params.weight = 0.0F;
    }

    /**
     * 释放被锁定的内容高度
     */
    public void unlockContentHeightDelayed() {
        etChat.postDelayed(() -> ((LinearLayout.LayoutParams) mContentLayout.getLayoutParams()).weight = 1.0F, 200L);
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

    private void setCustomActionBar() {
        tvTitle.setText(Objects.requireNonNull(getIntent().getExtras()).getString("anotherName"));
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
        chatItem.setType("TEXT");
        if (null != filePath) {
            chatItem.setType("RECORD");
            chatItem.setFile_path(filePath);
        }
        chatItemBeanList.add(chatItem);
        mAdapter.notifyItemInserted(chatItemBeanList.size() - 1);
        rvChatting.smoothScrollToPosition(chatItemBeanList.size() > 0 ? chatItemBeanList.size() - 1 : 0);
    }

    private void initEmoji() {
        vpEmoji.setAdapter(new EmojiPagerAdapter(this, etChat));
        ivEmoji.setOnClickListener(v -> {
            if (!mEmojiLayout.isShown()) {      //判断表情布局是否已经显示
                if (mPlusLayout.isShown()) {
                    showEmotionLayout();
                    hideMoreLayout();
                    hideAudioButton();
                    return;
                }
            } else if (mEmojiLayout.isShown() && !mPlusLayout.isShown()) {
                ivEmoji.setImageResource(R.mipmap.chatting_emoticons);
                if (mBottomLayout.isShown()) {
                    lockContentHeight();//显示软件盘时，锁定内容高度，防止跳闪。
                    hideBottomLayout(true);//隐藏表情布局，显示软件盘
                    unlockContentHeightDelayed();//软件盘显示后，释放内容高度
                } else {
                    if (isSoftInputShown()) {//同上
                        lockContentHeight();
                        showBottomLayout();
                        unlockContentHeightDelayed();
                    } else {
                        showBottomLayout();//两者都没显示，直接显示表情布局
                    }
                }
                return;
            }
            showEmotionLayout();
            hideMoreLayout();
            hideAudioButton();
//                Log.e("ChatActivity" + "123456", "mBottomLayout.isShown():" + mBottomLayout.isShown());
            if (mBottomLayout.isShown()) {
                lockContentHeight();//显示软件盘时，锁定内容高度，防止跳闪。
                hideBottomLayout(true);//隐藏表情布局，显示软件盘
                unlockContentHeightDelayed();//软件盘显示后，释放内容高度
            } else {
                if (isSoftInputShown()) {//同上
                    lockContentHeight();
                    showBottomLayout();
                    unlockContentHeightDelayed();
                } else {
                    showBottomLayout();//两者都没显示，直接显示表情布局
                }
            }
        });
        indEmoji.setIndicatorCount(Objects.requireNonNull(vpEmoji.getAdapter()).getCount());
        indEmoji.setCurrentIndicator(vpEmoji.getCurrentItem());
        vpEmoji.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                indEmoji.setCurrentIndicator(position);
            }
        });
    }

    public boolean isSoftInputShown() {
        return getSupportSoftInputHeight() != 0;
    }

    private void showBottomLayout() {
        int softInputHeight = getSupportSoftInputHeight();
//        Log.e("ChatActivity" + "123456", "softInputHeight1:" + softInputHeight);
        if (softInputHeight == 0) {
            softInputHeight = SPHelper.getInt(SOFT_INPUT_HEIGHT, DensityUtil.dip2Px(200, getContext()));
        }
        hideSoftInput();
//        Log.e("ChatActivity" + "123456", "softInputHeight2:" + softInputHeight);
        mBottomLayout.getLayoutParams().height = softInputHeight;
        mBottomLayout.setVisibility(View.VISIBLE);
    }


    /**
     * 获取软件盘的高度
     */
    private int getSupportSoftInputHeight() {
        Rect r = new Rect();
        getWindow().getDecorView().getWindowVisibleDisplayFrame(r);
        int screenHeight = getWindow().getDecorView().getRootView().getHeight();          //获取屏幕的高度
        int softInputHeight = screenHeight - r.bottom;              //计算软件盘的高度
        /*
          某些Android版本下，没有显示软键盘时减出来的高度总是144，而不是零，
          这是因为高度是包括了虚拟按键栏的(例如华为系列)，所以在API Level高于20时，
          我们需要减去底部虚拟按键栏的高度（如果有的话）
         */
        if (Build.VERSION.SDK_INT >= 20) {
            softInputHeight = softInputHeight - getSoftButtonsBarHeight();
        }
        //存一份到本地
        if (softInputHeight > 0) {
            SPHelper.setInt(SOFT_INPUT_HEIGHT, softInputHeight);
        }
//        Log.e("ChatActivity" + "123456", "screenHeight:" + screenHeight);
//        Log.e("ChatActivity" + "123456", "r.bottom:" + r.bottom);

        return softInputHeight;
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    private int getSoftButtonsBarHeight() {
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        int usableHeight = metrics.heightPixels;
        getWindowManager().getDefaultDisplay().getRealMetrics(metrics);
        int realHeight = metrics.heightPixels;
        if (realHeight > usableHeight) {
            return realHeight - usableHeight;
        } else {
            return 0;
        }
    }


    private void showEmotionLayout() {
        mEmojiLayout.setVisibility(View.VISIBLE);
        ivEmoji.setImageResource(R.mipmap.chatting_softkeyboard);
    }

    //发送消息
    @OnClick(R.id.btnSend)
    public void sendMessage(View view) {
        String str = etChat.getText().toString().trim();
        if (TextUtils.isEmpty(str)) {
            Toast.makeText(ChatActivity.this, "请输入内容", Toast.LENGTH_SHORT).show();
            return;
        }
        addChatItem(str);
        mPresenter.insertContent(str, roomId, "", "TEXT", chatItemBeanList.size() - 1);
        etChat.setText("");
    }

    //初始化录音
    private void initRecord() {
        mRecordText.setOnFinishRecordListener((time, filePath) -> {
            addRecordItem(time + "\"", filePath);
            mPresenter.insertContent(time + "\"", roomId, filePath, "RECORD", chatItemBeanList.size() - 1);
        });

        ivRecord.setOnClickListener(view -> {
            //如果录音按钮显示
            if (mRecordText.isShown()) {
                hideAudioButton();
                etChat.requestFocus();
                showSoftInput();
            } else {
                etChat.clearFocus();
                showAudioButton();
                hideEmotionLayout();
                hideMoreLayout();
            }
        });

    }

    private void hideAudioButton() {
        mRecordText.setVisibility(View.GONE);
        etChat.setVisibility(View.VISIBLE);
        ivRecord.setImageResource(R.mipmap.chatting_voice);
    }

    private void showAudioButton() {
        mRecordText.setVisibility(View.VISIBLE);
        etChat.setVisibility(View.GONE);
        ivRecord.setImageResource(R.mipmap.chatting_softkeyboard);
//        Log.e("ChatActivity" + "123456", "mBottomLayout.isShown33():" + mBottomLayout.isShown());
        if (mBottomLayout.isShown()) {
            hideBottomLayout(false);
        } else {
            hideSoftInput();
        }
    }

    private void hideEmotionLayout() {
        mEmojiLayout.setVisibility(View.GONE);
        ivEmoji.setImageResource(R.mipmap.chatting_emoticons);
    }

    private void hideMoreLayout() {
        mPlusLayout.setVisibility(View.GONE);
    }


    public void hideSoftInput() {
        mImm.hideSoftInputFromWindow(etChat.getWindowToken(), 0);
    }


    public void showSoftInput() {
        etChat.requestFocus();
        etChat.post(() -> mImm.showSoftInput(etChat, 0));
    }

    private void initPlus() {
        //更多图标点击
        ivPlus.setOnClickListener(v -> {
            etChat.clearFocus();
            hideAudioButton();
            if (mBottomLayout.isShown()) {          //底部布局，是否显示
                if (mPlusLayout.isShown()) {        //底部布局的更多布局，是否显示
                    lockContentHeight();//显示软件盘时，锁定内容高度，防止跳闪。
                    hideBottomLayout(true);//隐藏表情布局，显示软件盘
                    unlockContentHeightDelayed();//软件盘显示后，释放内容高度
                } else {                              //底部布局的表情布局，是否显示
                    showMoreLayout();
                    hideEmotionLayout();
                }
            } else {
                if (isSoftInputShown()) {//同上
                    hideEmotionLayout();
                    showMoreLayout();
                    lockContentHeight();
                    showBottomLayout();
                    unlockContentHeightDelayed();
                } else {
                    showMoreLayout();
                    hideEmotionLayout();
                    showBottomLayout();//两者都没显示，直接显示表情布局
                }
            }
        });
        //照相机
        CameraLayout.setOnClickListener(v -> takePhoto());
        //相册
        AlbumLayout.setOnClickListener(this::pickPhoto);
        //定位
        PositionLayout.setOnClickListener(v -> {
            startActivityForResult(new Intent(ChatActivity.this, MapActivity.class), MAP_LOCATION);
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        });
        //名片
        CardLayout.setOnClickListener(v -> {
            Intent intent = new Intent(ChatActivity.this, AddressListActivity.class);
            Bundle bundle = new Bundle();
            bundle.putBoolean("chat", true);
            intent.putExtras(bundle);
            startActivityForResult(intent, USER_CARD);
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        });
        //文件
        FileLayout.setOnClickListener(v -> FunctionUtils.openFile(ChatActivity.this, PICK_FILE));
        //视频
        VedioLayout.setOnClickListener(v -> FunctionUtils.openGalleryAudio(ChatActivity.this, PICK_VEDIO));
    }

    private void showMoreLayout() {
        mPlusLayout.setVisibility(View.VISIBLE);
    }

    //隐藏底部布局
    public void hideBottomLayout(boolean showSoftInput) {
        if (mBottomLayout.isShown()) {
            mBottomLayout.setVisibility(View.GONE);
            if (showSoftInput) {
                showSoftInput();
            }
        }
    }

    //拍照
    private void takePhoto() {
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
                    mPresenter.insertContent("", roomId, filename[filename.length - 1], "PICTURE", chatItemBeanList.size() - 1);
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
                        mPresenter.insertContent("", roomId, content, "PICTURE", chatItemBeanList.size() - 1);
                        mPresenter.upPicture(uri, content, SPHelper.getString(SPHelper.USER_ID));
                    }
                }
                break;
            case MAP_LOCATION:      //定位回调
                if (RESULT_OK == resultCode) {
                    MapForm mapForm = data.getParcelableExtra("map");
                    stringBuilder.setLength(0);
                    addMapItem(mapForm);
                    mPresenter.insertContent(mapForm.address, roomId, stringBuilder.append(mapForm.latLng.latitude + "," + mapForm.latLng.longitude).toString(), "MAP", chatItemBeanList.size() - 1);
                }
                break;
            case USER_CARD:      //名片回调
                if (RESULT_OK == resultCode) {
                    String account = data.getStringExtra("ACCOUNT");
//                    Log.e("ChatActivity" + "123456", "account" + account);
                    mPresenter.insertCard(account, roomId);
                }
                break;
            case PICK_FILE:     //文件
                if (RESULT_OK == resultCode) {
                    String filePath = data.getStringExtra(FilePickerActivity.RESULT_FILE_PATH);     //路径
                    String fileName = FileUtils.getFileName(filePath);                              //文件名
                    String fileSize = FileUtils.getFileLength(FileUtils.getFileLength(filePath));   //文件大小
//                LogUtil.logE("ChatActivity" + "123456", "获取到的文件路径:" + filePath);
                    addFileItem(fileName, fileSize);
                    FunctionUtils.saveFileToSDK(filePath, fileName);
                    mPresenter.insertContent(fileName + "," + fileSize, roomId, fileName, "FILE", chatItemBeanList.size() - 1);
                }
                break;
            case PICK_VEDIO:   //视频
                // 视频选择结果回调
                List<LocalMedia> selectListVideo = PictureSelector.obtainMultipleResult(data);
                StringBuilder urlpath = new StringBuilder();
                StringBuilder vedioName = new StringBuilder();
                for (LocalMedia media : selectListVideo) {
//                    LogUtil.logE("ChatActivity" + "123456", "获取视频路径成功:" + media.getPath());
                    //数据重置
                    urlpath.setLength(0);
                    vedioName.setLength(0);
                    //文件名、缩略图名称
                    urlpath.append(getFileName());
                    vedioName.append(FileUtils.getFileName(media.getPath()));
                    //保存操作
                    FunctionUtils.createThumbnail(media.getPath(), urlpath.toString());   //添加缩略图
                    addVedioItem(vedioName.toString(), urlpath.toString());                 //添加视频项
                    FunctionUtils.saveVideoToSDK(media.getPath(), vedioName.toString());     //存储视频于本地
                    mPresenter.insertContent(urlpath.toString(), roomId, vedioName.toString(), "VEDIO", chatItemBeanList.size() - 1);        //添加视频信息于数据库
                }
                break;
            default:
                Log.e("ChatActivity" + "123456", "requestCode = " + requestCode);
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    //添加：图片项
    private void addPictureItem(String imageUri) {
        ChatItemBean chatItem = new ChatItemBean();
        chatItem.setFile_path(imageUri);
        chatItem.setType("PICTURE");
        chatItemBeanList.add(chatItem);
        mAdapter.notifyItemInserted(chatItemBeanList.size() - 1);
        rvChatting.smoothScrollToPosition(chatItemBeanList.size() > 0 ? chatItemBeanList.size() - 1 : 0);
    }

    //添加：名片项
    @Override
    public void addCardItem(UserVo userVo) {
        Message message = mHandler.obtainMessage();
        message.what = 3;
        message.obj = userVo;
        mHandler.sendMessage(message);

    }

    //数据库添加失败
    @Override
    public void saveFail(int position) {
        chatItemBeanList.get(position).setSentstatus("FAILED");
        mAdapter.notifyItemChanged(position);
    }

    //数据库添加成功
    @Override
    public void saveSuccess(int position) {
        chatItemBeanList.get(position).setSentstatus("SENT");
        mAdapter.notifyItemChanged(position);
    }

    //添加：文件项
    private void addFileItem(String filePath, String fileSize) {
        ChatItemBean chatItem = new ChatItemBean();
        chatItem.setType("FILE");
        chatItem.setFile_path(filePath);
        chatItem.setContent(FileUtils.getFileName(filePath) + "," + fileSize);
        chatItemBeanList.add(chatItem);
        mAdapter.notifyItemInserted(chatItemBeanList.size() - 1);
        rvChatting.smoothScrollToPosition(chatItemBeanList.size() > 0 ? chatItemBeanList.size() - 1 : 0);
    }

    //添加：视频项
    private void addVedioItem(String filePath, String imagePath) {
        ChatItemBean chatItem = new ChatItemBean();
        chatItem.setType("VEDIO");
        chatItem.setFile_path(filePath);
        chatItem.setContent(imagePath);
        chatItemBeanList.add(chatItem);
        mAdapter.notifyItemInserted(chatItemBeanList.size() - 1);
        rvChatting.smoothScrollToPosition(chatItemBeanList.size() > 0 ? chatItemBeanList.size() - 1 : 0);
    }


    //加载更多消息
    @Override
    public void loadMoreMessage() {
        RequestForm requestForm = new RequestForm(String.valueOf(roomId), row++);
        mPresenter.allChatByRoomId(requestForm);
    }

    @Override
    public void showToast(String message) {
        ToastUtil.show(getContext(), message);
    }

    @Override
    public void openFile(String path) {
        File file = new File(FILE_SDK_PATH + path);
        if (file.exists()) {
            FunctionUtils.openFile(getContext(), path, file);
        } else {
            ToastUtil.show(getContext(), "文件不存在");
        }
    }

    //添加：地图项
    public void addMapItem(MapForm mapForm) {
        ChatItemBean chatItem = new ChatItemBean();
        chatItem.setDirection(ChatStroke.DIR_RIGHT);
        chatItem.setContent(mapForm.address);
        stringBuilder.setLength(0);
        chatItem.setFile_path(stringBuilder.append(mapForm.latLng.latitude + "," + mapForm.latLng.longitude).toString());
        chatItem.setType("MAP");
        chatItemBeanList.add(chatItem);
        mAdapter.notifyItemInserted(chatItemBeanList.size() - 1);
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
                case 3:     //用户名片
                    UserVo userVo = (UserVo) msg.obj;
                    ChatItemBean chatItem = new ChatItemBean();
                    chatItem.setType("CARD");
                    chatItem.setSentstatus("SENT");
                    chatItem.setContent(userVo.avatar + "," + userVo.nickname + "," + userVo.account);
                    chatItemBeanList.add(chatItem);
                    mAdapter.notifyItemInserted(chatItemBeanList.size() - 1);
                    rvChatting.smoothScrollToPosition(chatItemBeanList.size() > 0 ? chatItemBeanList.size() - 1 : 0);
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
        FunctionUtils.callGaudMap(getContext(), address, loca[0], loca[1]);
    }


    @Override
    public void onRefresh() {
        loadMoreMessage();
    }
}
