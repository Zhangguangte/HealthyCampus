package com.example.HealthyCampus.common.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.HealthyCampus.R;
import com.example.HealthyCampus.common.constants.ConstantValues;
import com.example.HealthyCampus.common.data.Bean.MedicineList;
import com.example.HealthyCampus.common.network.vo.MedicineListVo;
import com.example.HealthyCampus.common.utils.GlideUtils;
import com.example.HealthyCampus.common.utils.LogUtil;
import com.example.HealthyCampus.common.widgets.pullrecycler.BaseViewHolder;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class MedicineAdapter extends RecyclerView.Adapter<BaseViewHolder> {
    private Context context;
    private onItemClick onItemClick;
    private List<MedicineListVo> mData = new ArrayList<MedicineListVo>();
    private final int ITEM_TYPE = 0; //item布局类型
    private final int FOOTER_TYPPE = 1; //footer布局类型
    private boolean isLoad = true;

    public boolean isLoad() {
        return isLoad;
    }

    public void setLoad(boolean load) {
        isLoad = load;
    }

    public void clear() {
        mData.clear();
        notifyDataSetChanged();
    }


    public MedicineAdapter(Context context, List<MedicineListVo> mData, onItemClick onItemClick) {
        this.mData = mData;
        this.context = context;
        this.onItemClick = onItemClick;
    }

    public interface onItemClick {
        void selectedMedicine(String id);
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

    @Override
    public int getItemViewType(int position) {
        if (position + 1 == getItemCount() && isLoad) {  //最后一个为footer布局
            return FOOTER_TYPPE;
        }
        return ITEM_TYPE;

    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == ITEM_TYPE) {
            View view = LayoutInflater.from(context).inflate(R.layout.find_drug_bank_medicine_list, parent, false);
            return new ItemViewHolder(view);
        } else {
            View view = LayoutInflater.from(context).inflate(R.layout.widget_pull_to_refresh_footer, parent, false);
            return new FooterViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder holder, int position) {
        holder.onBindViewHolder(position);

    }

    //展示的item
    class ItemViewHolder extends BaseViewHolder {

        @BindView(R.id.tv_price)
        TextView tvPrice;
        @BindView(R.id.tv_type)
        TextView tvType;
        @BindView(R.id.tv_description)
        TextView tvDescription;
        @BindView(R.id.tv_goods_name)
        TextView tvGoods;
        @BindView(R.id.iv_img)
        ImageView ivIcon;
        @BindView(R.id.item_medicine_layout)
        LinearLayout medicineLayout;

        public ItemViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

        @Override
        public void onBindViewHolder(int position) {
            Log.e("MedicineAdapter" + "123456", "account" + mData.get(position).toString());
            tvPrice.setText(TextUtils.isEmpty(mData.get(position).getPrice()) ? context.getString(R.string.mine_drug_bank_price_unknown) : context.getString(R.string.mine_drug_bank_price) + mData.get(position).getPrice());
            tvGoods.setText(mData.get(position).getGoodName());
            tvDescription.setText(mData.get(position).getDescription());
            tvType.setText(mData.get(position).getIsOct());
            medicineLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClick.selectedMedicine(mData.get(position).getId());
                }
            });
            GlideUtils.displayMedicineImage(ivIcon,"http:"+mData.get(position).getImage());
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

