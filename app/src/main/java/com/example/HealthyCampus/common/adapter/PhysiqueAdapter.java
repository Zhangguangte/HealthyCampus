package com.example.HealthyCampus.common.adapter;

import android.annotation.SuppressLint;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.HealthyCampus.R;
import com.example.HealthyCampus.common.utils.PhysiqueUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.HealthyCampus.common.utils.PhysiqueUtils.colors;

public class PhysiqueAdapter extends BaseAdapter {

    private onItemClick onItemClick;

    public PhysiqueAdapter(onItemClick onItemClick) {
        this.onItemClick = onItemClick;
    }

    @Override
    public int getCount() {
        return colors.length;
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @SuppressLint("ViewHolder")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ItemHolder itemHolder;

        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.mine_physique_item, parent, false);
            itemHolder = new ItemHolder(convertView);
            convertView.setTag(itemHolder);//讲ViewHolder存储在View中
        } else {
            itemHolder = (ItemHolder) convertView.getTag();
        }
        itemHolder.onBindViewHolder(position);
        convertView.findViewById(R.id.contentLayout).setBackgroundColor(colors[position]);

        return convertView;
    }


    public interface onItemClick {
        void btnClick(int position, int type);

    }


    //展示的item
    class ItemHolder {


        @BindView(R.id.tvTitle)
        TextView tvTitle;
        @BindView(R.id.btnFirst)
        Button btnFirst;
        @BindView(R.id.btnSecond)
        Button btnSecond;
        @BindView(R.id.btnThird)
        Button btnThird;
        @BindView(R.id.contentLayout)
        LinearLayout contentLayout;


        ItemHolder(View view) {
            ButterKnife.bind(this, view);
        }


        @SuppressLint("SetTextI18n")
        private void onBindViewHolder(int position) {
            contentLayout.setBackgroundColor(PhysiqueUtils.colors[position]);
            btnFirst.setBackgroundColor(PhysiqueUtils.colors[position] + 30);
            btnSecond.setBackgroundColor(PhysiqueUtils.colors[position] + 30);
            btnThird.setBackgroundColor(PhysiqueUtils.colors[position] + 30);
            tvTitle.setText((position + 1) + "." + PhysiqueUtils.questionList[position]);
            btnFirst.setText(PhysiqueUtils.answerList[position][0]);
            btnSecond.setVisibility(View.GONE);
            btnThird.setVisibility(View.GONE);
            if (!TextUtils.isEmpty(PhysiqueUtils.answerList[position][1])) {
                btnSecond.setText(PhysiqueUtils.answerList[position][1]);
                btnSecond.setVisibility(View.VISIBLE);
            }
            if (!TextUtils.isEmpty(PhysiqueUtils.answerList[position][2])) {
                btnThird.setText(PhysiqueUtils.answerList[position][2]);
                btnThird.setVisibility(View.VISIBLE);
            }

            btnFirst.setOnClickListener(v -> onItemClick.btnClick(position, 1));
            btnSecond.setOnClickListener(v -> onItemClick.btnClick(position, 2));
            btnThird.setOnClickListener(v -> onItemClick.btnClick(position, 3));
        }
    }


}
