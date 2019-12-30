package com.example.HealthyCampus.common.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.HealthyCampus.R;
import com.example.HealthyCampus.common.constants.ConstantValues;
import com.example.HealthyCampus.common.utils.DateUtils;
import com.example.HealthyCampus.common.widgets.pullrecycler.BaseViewHolder;
import com.example.HealthyCampus.greendao.model.PatienInforBean;

import java.io.File;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ConsultPictureInfAdapter extends RecyclerView.Adapter<BaseViewHolder> {


    private Context context;
    private List<PatienInforBean> mData;

    private onItemClick onItemClick;

    public void clear() {
        mData.clear();
        notifyDataSetChanged();
    }

    public ConsultPictureInfAdapter(Context context, List<PatienInforBean> mData, onItemClick onItemClick) {
        this.mData = mData;
        this.context = context;
        this.onItemClick = onItemClick;
    }

    public ConsultPictureInfAdapter(Context context, List<PatienInforBean> mData) {
        this.mData = mData;
        this.context = context;
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
        View view = LayoutInflater.from(context).inflate(R.layout.health_consult_picture_infor, parent, false);
        return new ItemHolder(view);
    }

    @Override
    public int getItemViewType(int position) {
        return mData != null && mData.size() != position ? ConstantValues.HEALTH_PICTURE_ITEM : ConstantValues.HEALTH_PICTURE_DEFAULT;
    }

    public interface onItemClick {

        void removeInf(int position);
    }


    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder holder, int position) {
        holder.onBindViewHolder(position);
    }

    //展示的item
    class ItemHolder extends BaseViewHolder {

        @BindView(R.id.tvName)
        TextView tvName;
        @BindView(R.id.tvAge)
        TextView tvAge;
        @BindView(R.id.tvSex)
        TextView tvSex;
        @BindView(R.id.ivDelete)
        ImageView ivDelete;

        public ItemHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

        @SuppressLint("SetTextI18n")
        @Override
        public void onBindViewHolder(int position) {
            tvSex.setText(mData.get(position).getSex());
            try {
                tvAge.setText(DateUtils.getAge(mData.get(position).getBirthday()) + "岁");
            } catch (Exception e) {
                e.printStackTrace();
            }
            tvName.setText(mData.get(position).getName());
            ivDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClick.removeInf(position);
                }
            });
        }


        @Override
        public void onItemClick(View view, int position) {

        }
    }

}
