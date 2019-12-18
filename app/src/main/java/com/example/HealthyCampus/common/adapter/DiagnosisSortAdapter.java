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
import android.widget.TextView;

import com.example.HealthyCampus.R;
import com.example.HealthyCampus.common.constants.ConstantValues;
import com.example.HealthyCampus.common.network.vo.DiseaseSortVo;
import com.example.HealthyCampus.common.utils.GlideUtils;
import com.example.HealthyCampus.common.widgets.ViewHolder.FooterFinishViewHolder;
import com.example.HealthyCampus.common.widgets.ViewHolder.FooterViewHolder;
import com.example.HealthyCampus.common.widgets.pullrecycler.BaseViewHolder;
import com.example.HealthyCampus.module.Find.Diagnosis.List.DiseaseSort.Detail.DiseaseDetailActivity;
import com.example.HealthyCampus.module.Find.Diagnosis.List.DiseaseSort.DiseaseSortActivity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DiagnosisSortAdapter extends RecyclerView.Adapter<BaseViewHolder> {


    private Context context;
    private List<DiseaseSortVo> mData;
    private boolean load = true;

    public boolean isLoad() {
        return load;
    }

    public void setLoad(boolean load) {
        this.load = load;
    }

    public void clear() {
        mData.clear();
        notifyDataSetChanged();
    }


    public DiagnosisSortAdapter(Context context, List<DiseaseSortVo> mData) {
        this.mData = mData;
        this.context = context;
    }


    @Override
    public int getItemCount() {
        if (null != mData && mData.size() > 0) {
            return mData.size() + 1;
        }
        return 0;
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        if (ConstantValues.CONTENT_REFRESH == viewType) {
            view = LayoutInflater.from(context).inflate(R.layout.find_self_diagnosis_sort_item, parent, false);
            return new ListViewHolder(view);
        } else if (isLoad()) {
            view = LayoutInflater.from(context).inflate(R.layout.widget_pull_to_refresh_footer, parent, false);
            return new FooterViewHolder(view);
        } else {
            view = LayoutInflater.from(context).inflate(R.layout.widget_pull_to_footer_finish, parent, false);
            return new FooterFinishViewHolder(view);
        }

    }

    @Override
    public int getItemViewType(int position) {
        return position != mData.size() ? ConstantValues.CONTENT_REFRESH : ConstantValues.FOOTER_REFRESH;
    }


    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder holder, int position) {
        holder.onBindViewHolder(position);
    }

    //展示的item
    class ListViewHolder extends BaseViewHolder {
        @BindView(R.id.contentLayout)
        CardView contentLayout;
        @BindView(R.id.ivIcon)
        ImageView ivIcon;
        @BindView(R.id.tvDiseaseName)
        TextView tvDiseaseName;
        @BindView(R.id.tvIntroduction)
        TextView tvIntroduction;

        public ListViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

        @Override
        public void onBindViewHolder(int position) {
            tvIntroduction.setText(mData.get(position).getIntroduction());
            tvDiseaseName.setText(mData.get(position).getTitle());
            GlideUtils.display(ivIcon, mData.get(position).getUrl(), true);
            contentLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, DiseaseDetailActivity.class);
                    intent.putExtra("ID", mData.get(position).getId());
                    context.startActivity(intent);
                }
            });
        }


        @Override
        public void onItemClick(View view, int position) {

        }
    }


}
