package com.example.HealthyCampus.common.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.HealthyCampus.R;
import com.example.HealthyCampus.common.widgets.pullrecycler.BaseViewHolder;
import com.example.HealthyCampus.common.widgets.card.DailyCard;
import com.example.HealthyCampus.module.Find.Recipes.Customization.activity.CustomizationActivity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CardAdapter extends RecyclerView.Adapter<BaseViewHolder> {


    private Context context;
    private List<DailyCard> mList;

    public CardAdapter(Context context, List<DailyCard> mList) {
        this.context = context;
        this.mList = mList;
    }


    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.find_recipes_card_item, parent, false);
        return new CardViewHolder(view);
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

    class CardViewHolder extends BaseViewHolder {
        @BindView(R.id.CardLayout)
        CardView CardLayout;
        @BindView(R.id.iv_photo)
        ImageView ivPhoto;
        @BindView(R.id.tv_name)
        TextView tvName;
        @BindView(R.id.tv_sign)
        TextView tvSign;

        public CardViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @Override
        public void onBindViewHolder(int position) {
            tvName.setText(mList.get(position).getTitle());
            tvSign.setText(mList.get(position).getDescription());
            Glide.with(context).load(mList.get(position).getPictureId()).into(ivPhoto);
            CardLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, CustomizationActivity.class);
                    intent.putExtra("week_title", mList.get(position).getTitle());
                    context.startActivity(intent);
                }
            });
        }

        @Override
        public void onItemClick(View view, int position) {

        }
    }

}
