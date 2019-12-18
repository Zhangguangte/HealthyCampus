package com.example.HealthyCampus.common.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.HealthyCampus.R;
import com.example.HealthyCampus.common.widgets.pullrecycler.BaseViewHolder;
import com.example.HealthyCampus.module.Find.Diagnosis.List.DiseaseSort.DiseaseSortActivity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DiagnosisItemAdapter extends RecyclerView.Adapter<BaseViewHolder> {
    private int type = 0;
    private Context context;
    private List<String> mData;

    public void clear() {
        mData.clear();
        notifyDataSetChanged();
    }


    public DiagnosisItemAdapter(Context context, List<String> mData, int type) {
        this.mData = mData;
        this.context = context;
        this.type = type;
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
        View view = LayoutInflater.from(context).inflate(R.layout.find_self_diagnosis_item, parent, false);
        return new ListViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder holder, int position) {
        holder.onBindViewHolder(position);
    }

    //展示的item
    class ListViewHolder extends BaseViewHolder {
        @BindView(R.id.tvSubName)
        TextView tvSubName;

        public ListViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

        @Override
        public void onBindViewHolder(int position) {
            tvSubName.setText(mData.get(position));
            tvSubName.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, DiseaseSortActivity.class);
                    intent.putExtra("title", mData.get(position));
                    intent.putExtra("type", type);
                    context.startActivity(intent);
                }
            });
        }


        @Override
        public void onItemClick(View view, int position) {

        }
    }

}
