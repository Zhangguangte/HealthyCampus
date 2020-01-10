package com.example.HealthyCampus.common.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.HealthyCampus.R;
import com.example.HealthyCampus.common.constants.ConstantValues;
import com.example.HealthyCampus.common.network.vo.NoticeVo;
import com.example.HealthyCampus.common.utils.LogUtil;
import com.example.HealthyCampus.common.widgets.ViewHolder.FooterViewHolder;
import com.example.HealthyCampus.common.widgets.pullrecycler.BaseViewHolder;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class NoticeAdapter extends RecyclerView.Adapter<BaseViewHolder> {
    private Context context;
    private List<NoticeVo> mData;
    private onItemClick onItemClick;
    private boolean isLoad = true;

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public List<NoticeVo> getmData() {
        return mData;
    }

    public void setmData(List<NoticeVo> mData) {
        this.mData = mData;
    }

    public NoticeAdapter.onItemClick getOnItemClick() {
        return onItemClick;
    }

    public void setOnItemClick(NoticeAdapter.onItemClick onItemClick) {
        this.onItemClick = onItemClick;
    }

    public boolean isLoad() {
        return isLoad;
    }

    public void setLoad(boolean load) {
        isLoad = load;
    }

    public void clear() {
        mData.clear();
        notifyDataSetChanged();
    }


    public NoticeAdapter(Context context, List<NoticeVo> mData, onItemClick onItemClick) {
        this.mData = mData;
        this.context = context;
        this.onItemClick = onItemClick;
    }


    @Override
    public int getItemCount() {
        if (null != mData && mData.size() > 0) {
            if (isLoad)
                return mData.size() + 1;
            else
                return mData.size();
        }
        return 0;
    }

    public interface onItemClick {
        void deleteNotice(int position);

        void lookNotice(int position);
    }


    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        if (ConstantValues.CONTENT_REFRESH == viewType) {
            view = LayoutInflater.from(context).inflate(R.layout.message_notice_item, parent, false);
            return new ItemViewHolder(view);
        } else {
            view = LayoutInflater.from(context).inflate(R.layout.widget_pull_to_refresh_footer, parent, false);
            return new FooterViewHolder(view);
        }
    }

    @Override
    public int getItemViewType(int position) {
        return position != mData.size() ? ConstantValues.CONTENT_REFRESH : ConstantValues.FOOTER_REFRESH;
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder holder, int position) {
        holder.onBindViewHolder(position);

    }

    //展示的item
    class ItemViewHolder extends BaseViewHolder {

        @BindView(R.id.noticeLayout)
        LinearLayout noticeLayout;
        @BindView(R.id.tvContent)
        TextView tvContent;
        @BindView(R.id.tvTime)
        TextView tvTime;
        @BindView(R.id.sender)
        TextView sender;
        @BindView(R.id.tvRead)
        TextView tvRead;
        @BindView(R.id.ivLook)
        ImageView ivLook;

        public ItemViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

        @Override
        public void onBindViewHolder(int position) {
            LogUtil.logE("NoticeAdapter" + "1234567", "data.get(position)" + mData.get(position).toString());
            tvContent.setText(mData.get(position).getContent());
            tvTime.setText(mData.get(position).getCreate_time());
            if ("UNREAD".equals(mData.get(position).getStatus())) {
                tvRead.setVisibility(View.VISIBLE);
            }
            noticeLayout.setOnClickListener(v -> {
                Log.e("NoticeActivity" + "123456", "77777:");
                if (ivLook.getRotation() == -90.0f) {
                    tvContent.setMaxLines(Integer.MAX_VALUE);
                    ivLook.setRotation(90f);
                } else {
                    tvContent.setMaxLines(3);
                    ivLook.setRotation(-90f);
                }
                if ("UNREAD".equals(mData.get(position).getStatus())) {
                    mData.get(position).setStatus("READ");
                    tvRead.setVisibility(View.GONE);
                    if (!"ALL".equals(mData.get(position).getNoticeType())) {
                        onItemClick.lookNotice(position);
                    }
                }
            });
            noticeLayout.setOnLongClickListener(v -> {
                onItemClick.deleteNotice(position);
                return true;
            });
        }

        @Override
        public void onItemClick(View view, int position) {

        }
    }
}

