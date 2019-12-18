package com.example.HealthyCampus.common.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.HealthyCampus.R;
import com.example.HealthyCampus.common.network.vo.NoticeVo;
import com.example.HealthyCampus.common.utils.LogUtil;
import com.example.HealthyCampus.common.widgets.pullrecycler.BaseViewHolder;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class NoticeAdapter extends RecyclerView.Adapter<BaseViewHolder> {
    private Context context;
    private List<NoticeVo> mData = new ArrayList<NoticeVo>();
    private AlertDialog deleteDialog;
    private onItemClick onItemClick;

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
            return mData.size();
        }
        return 0;
    }

    public interface onItemClick {
        void deleteNotice(int position);
        void lookNotice(int position);
    }


    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.message_notice_item, parent, false);
        return new ItemViewHolder(view);

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
            noticeLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.e("NoticeActivity" + "123456", "77777:");
                    if (ivLook.getRotation() == -90.0f) {
                        tvContent.setMaxLines(Integer.MAX_VALUE);
                        ivLook.setRotation(90f);
                    } else {
                        tvContent.setMaxLines(3);
                        ivLook.setRotation(-90f);
                    }
                    if("UNREAD".equals(mData.get(position).getStatus()))
                    {
                        mData.get(position).setStatus("READ");
                        tvRead.setVisibility(View.GONE);
                        if(!"ALL".equals(mData.get(position).getNoticeType()))
                        {
                            onItemClick.lookNotice(position);
                        }
                    }
                }
            });
            noticeLayout.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    onItemClick.deleteNotice(position);
                    return true;
                }
            });
        }

        @Override
        public void onItemClick(View view, int position) {
//            view.setOnClickListener();

//            view.setOnLongClickListener();

        }
    }
}

