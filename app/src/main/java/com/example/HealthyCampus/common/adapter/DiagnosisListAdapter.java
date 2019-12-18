package com.example.HealthyCampus.common.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.HealthyCampus.R;
import com.example.HealthyCampus.common.network.vo.DiseaseSortListVo;
import com.example.HealthyCampus.common.widgets.pullrecycler.BaseViewHolder;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DiagnosisListAdapter extends RecyclerView.Adapter<BaseViewHolder> {

    private int type = 0;

    private Context context;
    private List<DiseaseSortListVo> mData;

    public void clear() {
        mData.clear();
        notifyDataSetChanged();
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public DiagnosisListAdapter(Context context, List<DiseaseSortListVo> mData) {
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
        View view = LayoutInflater.from(context).inflate(R.layout.find_self_diagnosis_list, parent, false);
        return new ListViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder holder, int position) {
        holder.onBindViewHolder(position);
    }

    //展示的item
    class ListViewHolder extends BaseViewHolder {
        @BindView(R.id.rvItem)
        RecyclerView rvItem;
        @BindView(R.id.tvTitle)
        TextView tvTitle;

        public ListViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

        @Override
        public void onBindViewHolder(int position) {
            tvTitle.setText(mData.get(position).getTitle());
            DiagnosisItemAdapter diagnosisItemAdapter = new DiagnosisItemAdapter(context, mData.get(position).getSubName(),type);
            rvItem.setLayoutManager(new GridLayoutManager(context, 3));
            rvItem.setHasFixedSize(true);
            rvItem.setAdapter(diagnosisItemAdapter);
        }


        @Override
        public void onItemClick(View view, int position) {

        }
    }

}
