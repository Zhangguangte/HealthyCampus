package com.example.HealthyCampus.module.Message.List;

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
import com.example.HealthyCampus.common.utils.DateUtils;
import com.example.HealthyCampus.common.utils.GlideUtils;
import com.example.HealthyCampus.common.utils.JsonUtil;
import com.example.HealthyCampus.common.utils.ToastUtil;
import com.example.HealthyCampus.common.widgets.pullrecycler.BaseViewHolder;
import com.example.HealthyCampus.framework.BaseListFragment;
import com.example.HealthyCampus.module.Message.MessageFragment;

import java.io.IOException;
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
//        if (viewType == ConstantValues.VIEW_MESSAGE_ITEM) {
//            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.message_item, parent, false);
//            return new MessageListFragment.MessageItemHolder(itemView);
//        } else {
//            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.message_no_chat, parent, false);
//            return new MessageListFragment.MessageNoChatHolder(itemView);
//        }
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
                lastMessage.setText(mDataList.get(position).getContent().length() > 20 ? mDataList.get(position).getContent().substring(0, 20) + "..." : mDataList.get(position).getContent());
                if (mDataList.get(position).getUnread() > 0) {
                    newTips.setText(mDataList.get(position).getUnread() + "");
                    newTips.setVisibility(View.VISIBLE);
                }

                lastTime.setText(DateUtils.getTimeString(DateUtils.string2Date(mDataList.get(position).getCreate_time()).getTime()));
                GlideUtils.display(headIcon, null);
            }
        }

        @Override
        public void onItemClick(View view, int position) {
            ToastUtil.show(mActivity, "" + mDataList.get(position).getContent());

//            try {
//                String title = mDataList.get(position).getTitle();
//                String url = mDataList.get(position).getImages().get(0);
//                int id = mDataList.get(position).getId();
//
//                ActivityUtils.startActivity(mActivity, HomePageArticleFragment.newInstance(title, id, url));
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
        }
    }


}
