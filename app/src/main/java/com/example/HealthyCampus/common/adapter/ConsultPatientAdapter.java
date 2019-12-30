package com.example.HealthyCampus.common.adapter;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.HealthyCampus.R;
import com.example.HealthyCampus.common.constants.ConstantValues;
import com.example.HealthyCampus.common.utils.DateUtils;
import com.example.HealthyCampus.common.utils.DialogUtil;
import com.example.HealthyCampus.common.utils.FunctionUtils;
import com.example.HealthyCampus.common.widgets.pullrecycler.BaseViewHolder;
import com.example.HealthyCampus.greendao.model.PatienInforBean;
import com.example.HealthyCampus.module.HomePage.Consultation.Picture.ConsultPictureActivity;
import com.example.HealthyCampus.module.Message.Chat.imageselect.ImageSelectorActivity;

import java.io.File;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.HealthyCampus.common.constants.ConstantValues.PICK_PHOTO;
import static com.example.HealthyCampus.common.constants.ConstantValues.PICK_VEDIO;

public class ConsultPatientAdapter extends RecyclerView.Adapter<BaseViewHolder> {


    private Context context;
    private List<PatienInforBean> mData;

    private onItemClick onItemClick;

    public void clear() {
        mData.clear();
        notifyDataSetChanged();
    }

    public ConsultPatientAdapter(Context context, List<PatienInforBean> mData, onItemClick onItemClick) {
        this.mData = mData;
        this.context = context;
        this.onItemClick = onItemClick;
    }


    @Override
    public int getItemCount() {
        if (null != mData && mData.size() > 0) {
            return mData.size() + 1;
        }
        return 1;
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        if (viewType == ConstantValues.HEALTH_PICTURE_INFOR_ITEM) {
            view = LayoutInflater.from(context).inflate(R.layout.health_consult_picture_infor_item, parent, false);
            return new ItemHolder(view);
        } else {
            view = LayoutInflater.from(context).inflate(R.layout.health_consult_picture_infor_default_item, parent, false);
            return new DefaultViewHolder(view);
        }
    }

    @Override
    public int getItemViewType(int position) {
        return mData != null && mData.size() != position ? ConstantValues.HEALTH_PICTURE_INFOR_ITEM : ConstantValues.HEALTH_PICTURE_INFOR_DEFAULT;
    }

    public interface onItemClick {

        void addLayout();

        void remove(int position);

        void update(int position);

        void selected(PatienInforBean patienInforBean, boolean val);
    }


    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder holder, int position) {
        holder.onBindViewHolder(position);
    }

    //展示的item
    class ItemHolder extends BaseViewHolder {

        @BindView(R.id.tvWeight)
        TextView tvWeight;
        @BindView(R.id.tvName)
        TextView tvName;
        @BindView(R.id.tvSex)
        TextView tvSex;
        @BindView(R.id.tvAge)
        TextView tvAge;
        @BindView(R.id.PatientLayout)
        LinearLayout PatientLayout;

        public ItemHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

        @SuppressLint("SetTextI18n")
        @Override
        public void onBindViewHolder(int position) {
            try {
                tvAge.setText(DateUtils.getAge(mData.get(position).getBirthday()) + "岁");
            } catch (Exception e) {
                e.printStackTrace();
            }
            tvSex.setText(mData.get(position).getSex());
            tvName.setText(mData.get(position).getName());
            tvWeight.setText(mData.get(position).getWeight() + "kg");
            PatientLayout.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setItems(R.array.operation, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            if (0 == which)
                                onItemClick.remove(position);
                            else
                                onItemClick.update(position);
                        }
                    });
                    builder.show();
                    return true;
                }
            });
            PatientLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClick.selected(mData.get(position), !PatientLayout.isSelected());
                    PatientLayout.setSelected(!PatientLayout.isSelected());
                }
            });
        }


        @Override
        public void onItemClick(View view, int position) {

        }
    }

    class DefaultViewHolder extends BaseViewHolder {

        @BindView(R.id.addLayout)
        LinearLayout addLayout;

        public DefaultViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

        @Override
        public void onBindViewHolder(int position) {
            addLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClick.addLayout();
                }
            });

        }


        @Override
        public void onItemClick(View view, int position) {

        }
    }
}
