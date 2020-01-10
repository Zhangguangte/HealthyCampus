package com.example.HealthyCampus.common.adapter;

import android.content.Context;
import android.graphics.Typeface;
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
import com.example.HealthyCampus.common.widgets.pullrecycler.BaseViewHolder;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class TypeAdapter extends RecyclerView.Adapter<BaseViewHolder> {
    private Context context;
    private List<String> mData;
    private onItemClick onItemClick;
    private int posi = 0;
    private TextView typeNameView;
    private ImageView checkView;
    private boolean isdominate;


    public void setIsdominate(boolean isdominate) {
        this.isdominate = isdominate;
    }

    public void setPosi(int posi) {
        this.posi = posi;
    }

    public void clear() {
        mData.clear();
        notifyDataSetChanged();
    }


    public TypeAdapter(Context context, List<String> mData, onItemClick onItemClick) {
        this.mData = mData;
        this.context = context;
        this.onItemClick = onItemClick;
        this.isdominate = false;
    }


    @Override
    public int getItemCount() {
        if (null != mData && mData.size() > 0) {
            return mData.size();
        }
        return 0;
    }

    public interface onItemClick {
        void selectType(String type);

        void selectClassify(String classify);

        void backType();

    }


    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.find_drug_bank_type, parent, false);
        return new ItemViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder holder, int position) {
        holder.onBindViewHolder(position);
        holder.setIsRecyclable(false);
    }

    //展示的item
    class ItemViewHolder extends BaseViewHolder {

        @BindView(R.id.tv_medicine_type_name)
        TextView tvTypeName;
        @BindView(R.id.view_divider)
        ImageView ivCheck;
        @BindView(R.id.typeLayout)
        LinearLayout typeLayout;

        public ItemViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

        @Override
        public void onBindViewHolder(int position) {
            Log.e("TypeAdapter" + "123456", "mData.get(position)" + mData.get(position));
            if (position == posi) {
                //设置选中
                tvTypeName.setTextColor(context.getResources().getColor(R.color.colorPrimary));
                tvTypeName.setTextSize(15.0f);
                ivCheck.setVisibility(View.VISIBLE);
                //初始化数据
                posi = position;
                checkView = ivCheck;
                typeNameView = tvTypeName;
            }
            tvTypeName.setText(mData.get(position));
            typeLayout.setOnClickListener(v -> {
                if (!isdominate) {
                    if (position != posi) {
                        selectedView(position);
                        onItemClick.selectType(mData.get(position));
                    }
                } else {
                    if (position == 0) {
                        onItemClick.backType();
                    } else {
                        if (position != posi) {
                            selectedView(position);
                            onItemClick.selectClassify(mData.get(position));
                        }
                    }
                }
            });
        }

        private void selectedView(int position) {
            //设置选中
            tvTypeName.setTextColor(context.getResources().getColor(R.color.colorPrimary));
            tvTypeName.setTextSize(15.0f);
            tvTypeName .setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
            checkView.setVisibility(View.GONE);
            //取消之前的效果
            typeNameView.setTextColor(context.getResources().getColor(R.color.text_level_1));
            typeNameView.setTextSize(14.0f);
            tvTypeName .setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
            ivCheck.setVisibility(View.VISIBLE);
            //重置数据
            posi = position;
            checkView = ivCheck;
            typeNameView = tvTypeName;
        }

        @Override
        public void onItemClick(View view, int position) {

        }
    }
}

