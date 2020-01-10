package com.example.HealthyCampus.common.adapter;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.HealthyCampus.R;
import com.example.HealthyCampus.common.constants.ConstantValues;
import com.example.HealthyCampus.common.network.vo.FoodRecommendVo;
import com.example.HealthyCampus.common.widgets.pullrecycler.BaseViewHolder;
import com.example.HealthyCampus.module.Find.Recipes.Customization.activity.Detail.RecipesDetailActivity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecommendAdapter extends RecyclerView.Adapter<BaseViewHolder> {


    private Context context;
    private List<FoodRecommendVo> mData;
    private boolean isLoad = true;

    public void clear() {
        mData.clear();
        notifyDataSetChanged();
    }

    public boolean isLoad() {
        return isLoad;
    }

    public void setLoad(boolean load) {
        isLoad = load;
    }

    public RecommendAdapter(Context context, List<FoodRecommendVo> mData) {
        this.mData = mData;
        this.context = context;
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
        if (viewType == ConstantValues.RECIPES_RECOMMEDN) {
            view = LayoutInflater.from(context).inflate(R.layout.find_recipes_recommend_dish, parent, false);
            return new DishViewHolder(view);
        } else if (viewType == ConstantValues.RECIPES_TIPS) {
            view = LayoutInflater.from(context).inflate(R.layout.find_recipes_recommend_tips, parent, false);
            return new TipsViewHolder(view);
        } else {
            view = LayoutInflater.from(context).inflate(R.layout.widget_pull_to_refresh_footer, parent, false);
            return new FooterViewHolder(view);
        }
    }

    @Override
    public int getItemViewType(int position) {
        return position != mData.size() ? mData.get(position).getMold() : 3;    //可以选取任意数，不是0，1就可以
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder holder, int position) {
        holder.onBindViewHolder(position);
    }

    //展示的item
    class DishViewHolder extends BaseViewHolder {
        @BindView(R.id.dishLayout)
        CardView dishLayout;
        @BindView(R.id.ivRecommend)
        ImageView ivRecommend;
        @BindView(R.id.tvTitle)
        TextView tvTitle;

        DishViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

        @SuppressLint("SetTextI18n")
        @Override
        public void onBindViewHolder(int position) {
            Glide.with(context).load(mData.get(position).getPicture()).into(ivRecommend);
            tvTitle.setText(mData.get(position).getTitle());
            dishLayout.setOnClickListener(v -> {
                Intent intent = new Intent(context, RecipesDetailActivity.class);
                intent.putExtra("ID", mData.get(position).getId());
                context.startActivity(intent);
            });
        }


        @Override
        public void onItemClick(View view, int position) {

        }
    }

    class TipsViewHolder extends BaseViewHolder {

        @BindView(R.id.tipsLayout)
        CardView tipsLayout;
        @BindView(R.id.ivIcon)
        ImageView ivIcon;
        @BindView(R.id.tvTitle)
        TextView tvTitle;
        @BindView(R.id.tvDescription)
        TextView tvDescription;

        TipsViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

        @SuppressLint("SetTextI18n")
        @Override
        public void onBindViewHolder(int position) {
            Glide.with(context).load(mData.get(position).getPicture()).into(ivIcon);
            tvTitle.setText(mData.get(position).getTitle().substring(1,mData.get(position).getTitle().indexOf(')')));
            tvDescription.setText("\t\t\t\t" + mData.get(position).getDescription());
            tipsLayout.setOnClickListener(v -> {
                AlertDialog.Builder builder = new AlertDialog.Builder(context).setTitle("小知识")
                        .setMessage(mData.get(position).getDescription().replace("', '", "\n")).setIcon(R.drawable.find_recipes_recommend_dialog);
                AlertDialog dialog = builder.create();
                dialog.setCanceledOnTouchOutside(true);
                dialog.setCancelable(true);
                dialog.show();
            });

        }


        @Override
        public void onItemClick(View view, int position) {

        }
    }

    //展示的item
    class FooterViewHolder extends BaseViewHolder {

        @BindView(R.id.progressbar)
        ProgressBar progressbar;
        @BindView(R.id.footer)
        TextView footer;

        public FooterViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

        @Override
        public void onBindViewHolder(int position) {
        }

        @Override
        public void onItemClick(View view, int position) {

        }
    }
}
