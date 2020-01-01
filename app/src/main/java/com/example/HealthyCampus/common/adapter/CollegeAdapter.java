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
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class CollegeAdapter extends RecyclerView.Adapter<BaseViewHolder> {
    private Context context;
    private List<String> mData;
    private onItemClick onItemClick;

    public CollegeAdapter(Context context, onItemClick onItemClick) {
        this.mData = Arrays.asList(context.getResources().getStringArray(R.array.lecture_college));
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

    public interface onItemClick {
        void selectCollege(String college);
    }


    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.mine_service_lecture_college, parent, false);
        return new ItemViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder holder, int position) {
        holder.onBindViewHolder(position);
    }

    //展示的item
    class ItemViewHolder extends BaseViewHolder {

        @BindView(R.id.tvCollege)
        TextView tvCollege;

        public ItemViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

        @Override
        public void onBindViewHolder(int position) {
            tvCollege.setText(mData.get(position));
            tvCollege.setOnClickListener(v -> onItemClick.selectCollege(mData.get(position)));
        }

        @Override
        public void onItemClick(View view, int position) {

        }


    }
}

