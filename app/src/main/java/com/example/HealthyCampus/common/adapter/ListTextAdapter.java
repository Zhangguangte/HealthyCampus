package com.example.HealthyCampus.common.adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.HealthyCampus.R;
import com.example.HealthyCampus.common.widgets.pullrecycler.BaseViewHolder;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ListTextAdapter extends RecyclerView.Adapter<BaseViewHolder> {
    private Context context;
    private List<String> mData;
    private onItemClick onItemClick;

    private TextView typeNameView;
    private ImageView checkView;
    private int posi = 0;

    public void clear() {
        mData.clear();
        notifyDataSetChanged();
    }


    public ListTextAdapter(Context context, List<String> mData, onItemClick onItemClick) {
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

    public interface onItemClick {
        void selectType(String name);
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
                if (position != posi) {
                    selectedView(position);
                    onItemClick.selectType(mData.get(position));
                }
            });
        }

        private void selectedView(int position) {
            //设置选中
            tvTypeName.setTextColor(context.getResources().getColor(R.color.colorPrimary));
            tvTypeName.setTextSize(15.0f);
            tvTypeName.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
            ivCheck.setVisibility(View.VISIBLE);
            //取消之前的效果
            checkView.setVisibility(View.GONE);
            typeNameView.setTextColor(context.getResources().getColor(R.color.text_level_1));
            typeNameView.setTextSize(14.0f);
            tvTypeName.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));

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
