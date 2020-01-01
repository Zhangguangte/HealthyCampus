package com.example.HealthyCampus.common.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.HealthyCampus.R;
import com.example.HealthyCampus.common.constants.ConstantValues;
import com.example.HealthyCampus.common.network.vo.LectureVo;
import com.example.HealthyCampus.common.widgets.ViewHolder.FooterFinishViewHolder;
import com.example.HealthyCampus.common.widgets.ViewHolder.FooterViewHolder;
import com.example.HealthyCampus.common.widgets.pullrecycler.BaseViewHolder;
import com.example.HealthyCampus.module.Mine.Service.Lecture.Detail.LectureDetailActivity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class LectureAdapter extends RecyclerView.Adapter<BaseViewHolder> {
    private Context context;
    private List<LectureVo> mData;
    private boolean isLoad = true;

    public boolean isLoad() {
        return isLoad;
    }

    public void setLoad(boolean load) {
        isLoad = load;
    }

    public LectureAdapter(Context context, List<LectureVo> mData) {
        this.mData = mData;
        this.context = context;
        isLoad = true;
    }


    @Override
    public int getItemCount() {
        if (null != mData && mData.size() > 0) {
            if (!isLoad)
                return mData.size();
            else
                return mData.size() + 1;
        }
        return 0;
    }


    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        if (ConstantValues.CONTENT_REFRESH == viewType) {
            view = LayoutInflater.from(context).inflate(R.layout.mine_service_lecture_item, parent, false);
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

        @BindView(R.id.ItemLayout)
        RelativeLayout ItemLayout;
        @BindView(R.id.tvTitle)
        TextView tvTitle;
        @BindView(R.id.tvDate)
        TextView tvDate;

        public ItemViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

        @Override
        public void onBindViewHolder(int position) {
            tvTitle.setText(mData.get(position).getTitle());
            tvDate.setText(mData.get(position).getDate());
            ItemLayout.setOnClickListener(v -> LectureDetailActivity.actionShow(context, mData.get(position).getId()));
        }

        @Override
        public void onItemClick(View view, int position) {

        }


    }
}

