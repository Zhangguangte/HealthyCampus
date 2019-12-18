package com.example.HealthyCampus.module.Message.List;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.HealthyCampus.R;
import com.example.HealthyCampus.common.constants.ConstantValues;
import com.example.HealthyCampus.common.network.vo.MessageListVo;
import com.example.HealthyCampus.common.network.vo.DefaultResponseVo;
import com.example.HealthyCampus.common.utils.ActivityUtils;
import com.example.HealthyCampus.common.utils.DateUtils;
import com.example.HealthyCampus.common.utils.GlideUtils;
import com.example.HealthyCampus.common.utils.JsonUtil;
import com.example.HealthyCampus.common.utils.SpanStringUtils;
import com.example.HealthyCampus.common.utils.TimestampUtils;
import com.example.HealthyCampus.common.utils.ToastUtil;
import com.example.HealthyCampus.common.widgets.pullrecycler.BaseViewHolder;
import com.example.HealthyCampus.framework.BaseListFragment;
import com.example.HealthyCampus.module.Message.Chat.ChatActivity;
import com.example.HealthyCampus.module.Message.MessageFragment;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Response;
import retrofit2.adapter.rxjava.HttpException;

public class MessageListFragment extends BaseListFragment<MessageListContract.View, MessageListContract.Presenter, MessageListVo> implements MessageListContract.View {


    private ImageView mailList;

    public static MessageListFragment newInstance() {
        MessageListFragment fragment = new MessageListFragment();
        return fragment;
    }

    @Override
    protected void setUpData() {
        pullRecycler.setRefreshing();
//        new android.os.Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                pullRecycler.setRefreshing();
//            }
//        }, 200);
    }


    @Override
    protected BaseViewHolder getViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.message_item, parent, false);
        return new MessageListFragment.MessageItemHolder(itemView);
    }

    @Override
    public void onRefresh(int action) {
//        if (action == PullRecycler.ACTION_PULL_TO_REFRESH) {
//            mPresenter.getLatestNews();
//        } else {
//            mPresenter.getBeforeNews(currentDate);
//        }
        mPresenter.lastMessage();
    }

    @Override
    protected MessageListContract.Presenter setPresenter() {
        return new MessageListPresenter();
    }

    @Override
    protected int getItemType(int position) {
//        if (mDataList.get(position).getType().equals(ConstantValues.VIEW_MESSAGE_ITEM)) {
//            return ConstantValues.VIEW_MESSAGE_ITEM;
//        } else {
//            return ConstantValues.VIEW_MESSAGE_NO;
//        }
        return ConstantValues.VIEW_MESSAGE_ITEM;
    }

    @Override
    public void refreshList(List<MessageListVo> messageListBeans) {
        mDataList.clear();
        mDataList.addAll(messageListBeans);
        adapter.notifyDataSetChanged();
//        if (messageListBeans.size() == 0) {
//            MessageListVo noChat = new MessageListVo();
//            noChat.setType(""+ConstantValues.VIEW_MESSAGE_NO);
//            mDataList.add(noChat);
//            adapter.notifyDataSetChanged();
//        } else {
//            mDataList.addAll(messageListBeans);
//            adapter.notifyDataSetChanged();
//        }

    }

    @Override
    public void loadComplete() {
        pullRecycler.onRefreshCompleted();
    }

    @Override
    public void noChatItem(boolean value) {
        ((MessageFragment) (this.getParentFragment())).noChatItemVisible(value);
    }

    @Override
    public void MessageError(Throwable throwable) {
        mDataList.clear();
        adapter.notifyDataSetChanged();
        if (throwable instanceof HttpException) {
            Response httpException = ((HttpException) throwable).response();
            try {
                DefaultResponseVo response = JsonUtil.format(httpException.errorBody().string(), DefaultResponseVo.class);
                if (response.code == 1001) {
                    Log.e("MessageListFragm" + "123456", "response.toString:" + response.toString());
                    ToastUtil.show(mActivity, "用户名密码错误");
                } else {
                    ToastUtil.show(mActivity, "未知错误:" + throwable.getMessage());
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }

        } else {
            Toast.makeText(getContext(), "未知错误:" + throwable.getMessage(), Toast.LENGTH_SHORT).show();
            Log.e("MessageListFragm" + "123456", "throwable.getMessage()" + throwable.getMessage());
            Log.e("MessageListFragm" + "123456", "throwable.toString()" + throwable.toString());
        }

    }


    class MessageItemHolder extends BaseViewHolder {

        @BindView(R.id.headIcon)
        ImageView headIcon;
        @BindView(R.id.tvUsername)
        TextView tvUsername;
        @BindView(R.id.lastMessage)
        TextView lastMessage;
        @BindView(R.id.lastTime)
        TextView lastTime;
        @BindView(R.id.newTips)
        TextView newTips;

        public MessageItemHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @Override
        public void onBindViewHolder(int position) {
//            LogUtil.logE("HomePageListFragment" + "123456", "2");
            if (mDataList.get(position) != null) {
//                headIcon.setBackground(getResources().getDrawable(R.drawable.message_item_default_icon));
                tvUsername.setText(mDataList.get(position).getAnother_name());
                switch (mDataList.get(position).getType()) {
                    case "TEXT":
                        lastMessage.setText(SpanStringUtils.getEmojiContent(1, getContext(), (int) (lastMessage.getTextSize() * 12 / 10), mDataList.get(position).getContent()));
                        break;
                    case "RECORD":
                        lastMessage.setText("[语音]");
                        break;
                    case "PICTURE":
                        lastMessage.setText("[图片]");
                        break;
                    case "FILE":
                        lastMessage.setText("[文件]");
                        break;
                    case "MAP":
                        lastMessage.setText("[定位]");
                        break;
                    case "CARD":
                        lastMessage.setText("[推荐好友]");
                        break;
                    case "VEDIO":
                        lastMessage.setText("[视频]");
                        break;
                    default:
                        lastMessage.setText("你们已经成功加为好友");
                }
                if (mDataList.get(position).getUnread() > 0) {
                    newTips.setText(mDataList.get(position).getUnread() + "");
                    newTips.setVisibility(View.VISIBLE);
                } else {
                    newTips.setVisibility(View.GONE);
                }
                newTips.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ToastUtil.show(getContext(), mDataList.get(position).toString());
                    }
                });
                try {
                    lastTime.setText(TimestampUtils.getTimeStringAutoShort2(mDataList.get(position).getCreate_time(), false));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                GlideUtils.display(headIcon, null);
            }
        }

        @Override
        public void onItemClick(View view, int position) {
            Intent intent = new Intent(getContext(), ChatActivity.class);
            Bundle bundle = new Bundle();
            bundle.putString("roomid", mDataList.get(position).getRoom_id());
            bundle.putString("anotherName", mDataList.get(position).getAnother_name());
            intent.putExtras(bundle);
            startActivity(intent);
            mActivity.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        }
    }

}
