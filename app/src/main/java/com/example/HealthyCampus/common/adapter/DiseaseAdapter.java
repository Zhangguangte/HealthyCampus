package com.example.HealthyCampus.common.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.amap.api.services.core.PoiItem;
import com.example.HealthyCampus.R;
import com.example.HealthyCampus.common.constants.ConstantValues;
import com.example.HealthyCampus.common.network.vo.DiseaseSortVo;
import com.example.HealthyCampus.common.utils.GlideUtils;
import com.example.HealthyCampus.common.widgets.ViewHolder.FooterViewHolder;
import com.example.HealthyCampus.common.widgets.pullrecycler.BaseViewHolder;

import java.util.LinkedList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class DiseaseAdapter extends RecyclerView.Adapter<BaseViewHolder> {


    private onItemClick onItemClick;
    private Context context;
    private List<DiseaseSortVo> mData = new LinkedList<>();
    private int row;
    private boolean isLoad = true;

    public boolean isLoad() {
        return isLoad;
    }

    public void setLoad(boolean load) {
        isLoad = load;
    }

    public void setIsLoad(boolean isload) {
        this.isLoad = isload;
    }

    public void setRow(int row) {
        this.row = row;
    }

    private StringBuffer stringBuffer = new StringBuffer();

    public void clear() {
        mData.clear();
        notifyDataSetChanged();
    }


    public DiseaseAdapter(Context context, List<DiseaseSortVo> mData, onItemClick onItemClick) {
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

        void detailDisease(String id);

    }


    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        if (ConstantValues.CONTENT_REFRESH == viewType) {
            view = LayoutInflater.from(context).inflate(R.layout.find_self_diagnosis_search_item, parent, false);
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

        @BindView(R.id.cvDisease)
        CardView cvDisease;
        @BindView(R.id.ivIcon)
        ImageView ivIcon;
        @BindView(R.id.tvName)
        TextView tvName;
        @BindView(R.id.tvIntroduction)
        TextView tvIntroduction;

        public ItemViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

        @SuppressLint("SetTextI18n")
        @Override
        public void onBindViewHolder(int position) {
            GlideUtils.display(ivIcon, mData.get(position).getUrl());
            tvName.setText(mData.get(position).getTitle());
            tvIntroduction.setText(mData.get(position).getIntroduction());
            cvDisease.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClick.detailDisease(mData.get(position).getId());
                }
            });
        }


        @Override
        public void onItemClick(View view, int position) {

        }
    }


}

