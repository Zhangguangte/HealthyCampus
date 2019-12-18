package com.example.HealthyCampus.common.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.HealthyCampus.R;
import com.example.HealthyCampus.common.constants.ConstantValues;
import com.example.HealthyCampus.common.data.Bean.CookDetailBean;
import com.example.HealthyCampus.common.network.vo.FoodMenuVo;
import com.example.HealthyCampus.common.network.vo.FoodVo;
import com.example.HealthyCampus.common.widgets.pullrecycler.BaseViewHolder;
import com.example.HealthyCampus.module.Find.Recipes.Customization.activity.Detail.RecipesDetailActivity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class MaterialAdapter extends RecyclerView.Adapter<BaseViewHolder> {


    private Context context;
    private List<CookDetailBean> mData;

    public void clear() {
        mData.clear();
        notifyDataSetChanged();
    }


    public MaterialAdapter(Context context, List<CookDetailBean> mData) {
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
        View view = LayoutInflater.from(context).inflate(R.layout.find_recipes_customization_activity_detail_content, parent, false);
        return new MaterialViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder holder, int position) {
        holder.onBindViewHolder(position);
    }

    //展示的item
    class MaterialViewHolder extends BaseViewHolder {

        @BindView(R.id.tvUnit)
        TextView tvUnit;
        @BindView(R.id.tvContent)
        TextView tvContent;

        public MaterialViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

        @Override
        public void onBindViewHolder(int position) {
            tvUnit.setText(mData.get(position).getQuantity());
            tvContent.setText(mData.get(position).getMaterial());
        }


        @Override
        public void onItemClick(View view, int position) {

        }
    }


}

