package com.example.HealthyCampus.common.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
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
import com.example.HealthyCampus.common.utils.GlideUtils;
import com.example.HealthyCampus.common.widgets.pullrecycler.BaseViewHolder;

import java.util.LinkedList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class RoadListAdapter extends RecyclerView.Adapter<BaseViewHolder> {

    private onItemClick onItemClick;
    private Context context;
    private List<PoiItem> mData = new LinkedList<>();
    private int row;
    private boolean isLoad = true;


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


    public RoadListAdapter(Context context, List<PoiItem> mData, onItemClick onItemClick) {
        this.mData = mData;
        this.context = context;
        this.onItemClick = onItemClick;
    }

    @Override
    public int getItemCount() {
        if (null != mData && mData.size() > 0) {
            return mData.size() + 1;
        }
        return 0;
    }

    public interface onItemClick {
        void showDetailRoadCall(PoiItem poiItem);

        void nextPage(int row);

        void lastPage(int row);
    }


    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        if (ConstantValues.CONTENT_REFRESH == viewType) {
            view = LayoutInflater.from(context).inflate(R.layout.find_nearby_map_list_item, parent, false);
            return new ItemViewHolder(view);
        } else {
            view = LayoutInflater.from(context).inflate(R.layout.find_nearby_map_list_page_item, parent, false);
            return new PageViewHolder(view);
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

        @BindView(R.id.ivIcon)
        ImageView ivIcon;
        @BindView(R.id.tvTitle)
        TextView tvTitle;
        @BindView(R.id.tvAddress)
        TextView tvAddress;
        @BindView(R.id.tvPhone)
        TextView tvPhone;
        @BindView(R.id.hereLayout)
        LinearLayout hereLayout;

        public ItemViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

        @SuppressLint("SetTextI18n")
        @Override
        public void onBindViewHolder(int position) {
            if (null == mData.get(position).getPhotos() || mData.get(position).getPhotos().size() == 0)
                GlideUtils.displayMapImage(ivIcon, R.mipmap.picture_lose);
            else
                GlideUtils.display(ivIcon, mData.get(position).getPhotos().get(0).getUrl(),false);
            tvTitle.setText(((row - 1) * 15 + position + 1) + "." + mData.get(position).getTitle());
            tvAddress.setText(mData.get(position).getAdName() + mData.get(position).getSnippet());
            tvPhone.setText(TextUtils.isEmpty(mData.get(position).getTel()) ? "未知" : mData.get(position).getTel().replace(";", "\n"));
            hereLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClick.showDetailRoadCall(mData.get(position));
                }
            });
        }


        @Override
        public void onItemClick(View view, int position) {

        }
    }

    //展示的item
    class PageViewHolder extends BaseViewHolder {

        @BindView(R.id.btnNext)
        Button btnNext;
        @BindView(R.id.btnLast)
        Button btnLast;

        public PageViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

        @SuppressLint("SetTextI18n")
        @Override
        public void onBindViewHolder(int position) {
            if (!isLoad) {
                btnNext.setEnabled(false);
                btnNext.setTextColor(context.getResources().getColor(R.color.text_level_3));
            } else {
                btnNext.setEnabled(true);
                btnNext.setTextColor(context.getResources().getColor(R.color.black));
            }

            if (row == 1) {
                btnLast.setEnabled(false);
                btnLast.setTextColor(context.getResources().getColor(R.color.text_level_3));
            } else {
                btnLast.setEnabled(true);
                btnLast.setTextColor(context.getResources().getColor(R.color.black));
            }
        }

        @OnClick(R.id.btnNext)
        public void btnNext(View view) {
            onItemClick.nextPage(++row);
        }

        @OnClick(R.id.btnLast)
        public void btnLast(View view) {
            onItemClick.lastPage(--row);
        }


        @Override
        public void onItemClick(View view, int position) {

        }
    }


}

