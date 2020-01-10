package com.example.HealthyCampus.common.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.HealthyCampus.R;
import com.example.HealthyCampus.common.widgets.pullrecycler.BaseViewHolder;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class ClassifyAdapter extends RecyclerView.Adapter<BaseViewHolder> {
    private Context context;
    private List<String> mData;
    private onItemClick onItemClick;


    public void clear() {
        mData.clear();
        notifyDataSetChanged();
    }


    public ClassifyAdapter(Context context, List<String> mData, onItemClick onItemClick) {
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

    public interface onItemClick {
        void seletClassify(String classifyName );


    }


    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.find_frug_bank_classify, parent, false);
        return new ItemViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder holder, int position) {
        holder.onBindViewHolder(position);

    }

    //展示的item
    class ItemViewHolder extends BaseViewHolder {

        @BindView(R.id.classifyName)
        TextView classifyName;

        public ItemViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

        @Override
        public void onBindViewHolder(int position) {
            classifyName.setText(mData.get(position));
            classifyName.setOnClickListener(v -> onItemClick.seletClassify(mData.get(position)));
        }

        @Override
        public void onItemClick(View view, int position) {

        }
    }
}

