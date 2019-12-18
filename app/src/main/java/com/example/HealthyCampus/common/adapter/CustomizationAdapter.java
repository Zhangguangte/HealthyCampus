package com.example.HealthyCampus.common.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.HealthyCampus.R;
import com.example.HealthyCampus.common.constants.ConstantValues;
import com.example.HealthyCampus.common.network.vo.FoodMenuVo;
import com.example.HealthyCampus.common.widgets.pullrecycler.BaseViewHolder;
import com.example.HealthyCampus.module.Find.Recipes.Customization.activity.CustomizationActivity;
import com.example.HealthyCampus.module.Find.Recipes.Customization.activity.Detail.RecipesDetailActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class CustomizationAdapter extends RecyclerView.Adapter<BaseViewHolder> {


    private Context context;
    private List<FoodMenuVo> mData;

    public void clear() {
        mData.clear();
        notifyDataSetChanged();
    }


    public CustomizationAdapter(Context context, List<FoodMenuVo> mData) {
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
        View view;
        if (viewType == ConstantValues.RECIPES_CONTENT) {
            view = LayoutInflater.from(context).inflate(R.layout.find_recipes_customization_activity_content, parent, false);
            return new ContentViewHolder(view);
        } else {
            view = LayoutInflater.from(context).inflate(R.layout.find_recipes_customization_activity_title, parent, false);
            return new TitleViewHolder(view);
        }
    }

    @Override
    public int getItemViewType(int position) {
        return mData.get(position).getMold();
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder holder, int position) {
        holder.onBindViewHolder(position);
        holder.setIsRecyclable(false);
    }

    //展示的item
    class ContentViewHolder extends BaseViewHolder {

        @BindView(R.id.ItemLayout)
        RelativeLayout ItemLayout;
        @BindView(R.id.ivIcon)
        ImageView ivIcon;
        @BindView(R.id.food_name)
        TextView tvfoodName;
        @BindView(R.id.arch)
        ImageView arch;
        @BindView(R.id.food_energy)
        TextView foodEnergy;

        public ContentViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

        @SuppressLint("SetTextI18n")
        @Override
        public void onBindViewHolder(int position) {
            Glide.with(context).load(mData.get(position).getPictureUrl()).into(ivIcon);
            tvfoodName.setText(mData.get(position).getDishName());
            foodEnergy.setText(mData.get(position).getCalorie());
            ItemLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, RecipesDetailActivity.class);
                    intent.putExtra("ID",mData.get(position).getId());
                    context.startActivity(intent);
                }
            });

        }


        @Override
        public void onItemClick(View view, int position) {

        }
    }

    class TitleViewHolder extends BaseViewHolder {

        @BindView(R.id.tvTitleName)
        TextView tvTitleName;
        @BindView(R.id.energy_text)
        TextView tvTnergy;

        public TitleViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

        @SuppressLint("SetTextI18n")
        @Override
        public void onBindViewHolder(int position) {
            tvTitleName.setText(mData.get(position).getDishName());
            tvTnergy.setText(mData.get(position).getCalorie());
        }


        @Override
        public void onItemClick(View view, int position) {

        }
    }

}

