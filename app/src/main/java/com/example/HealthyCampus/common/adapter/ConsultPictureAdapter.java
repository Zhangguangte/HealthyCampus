package com.example.HealthyCampus.common.adapter;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.amap.api.services.core.PoiItem;
import com.bumptech.glide.Glide;
import com.example.HealthyCampus.R;
import com.example.HealthyCampus.common.constants.ConstantValues;
import com.example.HealthyCampus.common.network.vo.FoodRecommendVo;
import com.example.HealthyCampus.common.utils.GlideUtils;
import com.example.HealthyCampus.common.utils.PictureUtil;
import com.example.HealthyCampus.common.widgets.pullrecycler.BaseViewHolder;
import com.example.HealthyCampus.module.Find.Recipes.Customization.Fragment.CustomizationFragment;
import com.example.HealthyCampus.module.Find.Recipes.Customization.activity.Detail.RecipesDetailActivity;
import com.example.HealthyCampus.module.Find.Recipes.Functionality.FunctionalityFragment;
import com.example.HealthyCampus.module.Find.Recipes.Recommend.RecommendFragment;

import java.io.File;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ConsultPictureAdapter extends RecyclerView.Adapter<BaseViewHolder> {


    private Context context;
    private List<String> mData;

    private onItemClick onItemClick;

    public void clear() {
        mData.clear();
        notifyDataSetChanged();
    }

    public ConsultPictureAdapter(Context context, List<String> mData, onItemClick onItemClick) {
        this.mData = mData;
        this.context = context;
        this.onItemClick = onItemClick;
    }


    @Override
    public int getItemCount() {
        if (null != mData && mData.size() > 0) {
            if (mData.size() < 9)
                return mData.size() + 1;
            else
                return mData.size();
        }
        return 1;
    }

    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        if (viewType == ConstantValues.HEALTH_PICTURE_ITEM) {
            view = LayoutInflater.from(context).inflate(R.layout.health_consult_picture_item, parent, false);
            return new ItemHolder(view);
        } else {
            view = LayoutInflater.from(context).inflate(R.layout.health_consult_picture_default_item, parent, false);
            return new DefaultViewHolder(view);
        }
    }

    @Override
    public int getItemViewType(int position) {
        return mData != null && mData.size() != position ? ConstantValues.HEALTH_PICTURE_ITEM : ConstantValues.HEALTH_PICTURE_DEFAULT;
    }

    public interface onItemClick {
        void remove(int position);

        void pictureButton();
    }


    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder holder, int position) {
        holder.onBindViewHolder(position);
    }

    //展示的item
    class ItemHolder extends BaseViewHolder {

        @BindView(R.id.ivPicture)
        ImageView ivPicture;
        @BindView(R.id.ivDelete)
        ImageView ivDelete;

        public ItemHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

        @SuppressLint("SetTextI18n")
        @Override
        public void onBindViewHolder(int position) {
            Glide.with(context).load(Uri.fromFile(new File(mData.get(position).replace("file://", "")))).into(ivPicture);
//            ivPicture.setImageBitmap(PictureUtil.fileTobitmap(mData.get(position)));
            ivDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClick.remove(position);
                }
            });
        }


        @Override
        public void onItemClick(View view, int position) {

        }
    }

    class DefaultViewHolder extends BaseViewHolder {

        @BindView(R.id.pictureButton)
        LinearLayout pictureButton;

        public DefaultViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

        @Override
        public void onBindViewHolder(int position) {
            pictureButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClick.pictureButton();
                }
            });

        }


        @Override
        public void onItemClick(View view, int position) {

        }
    }
}
