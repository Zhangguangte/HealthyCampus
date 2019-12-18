package com.example.HealthyCampus.common.widgets.ViewHolder;

import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.HealthyCampus.R;
import com.example.HealthyCampus.common.widgets.pullrecycler.BaseViewHolder;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FooterViewHolder  extends BaseViewHolder {

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
