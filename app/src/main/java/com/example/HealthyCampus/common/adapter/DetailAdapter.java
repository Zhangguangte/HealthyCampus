package com.example.HealthyCampus.common.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.HealthyCampus.R;
import com.example.HealthyCampus.common.widgets.card.DailyCard;
import com.example.HealthyCampus.common.widgets.pullrecycler.BaseViewHolder;
import com.example.HealthyCampus.module.Find.Recipes.Customization.activity.CustomizationActivity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailAdapter extends RecyclerView.Adapter<BaseViewHolder> {


    private Context context;
    private List<String> mList;

    public DetailAdapter(Context context, List<String> mList) {
        this.context = context;
        this.mList = mList;
    }


    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.find_recipes_customization_activity_detail_material, parent, false);
        return new DetailViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder baseViewHolder, int position) {
        baseViewHolder.onBindViewHolder(position);
    }

    @Override
    public int getItemCount() {
        if (null != mList && mList.size() > 0) {
            return mList.size();
        }
        return 0;
    }

    //展示的item
    class DetailViewHolder extends BaseViewHolder {

        @BindView(R.id.make_step_text_view)
        TextView tvStep;

        public DetailViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

        @Override
        public void onBindViewHolder(int position) {
            Log.e("DetailAdapter" + "123456", "mList.get(position):" + mList.get(position));
            tvStep.setText(mList.get(position));
        }


        @Override
        public void onItemClick(View view, int position) {

        }
    }

}
