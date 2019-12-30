package com.example.HealthyCampus.common.widgets.pullrecycler;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.text.ParseException;

/**
 * OK
 */

public abstract class BaseViewHolder extends RecyclerView.ViewHolder {
    public BaseViewHolder(View itemView) {
        super(itemView);
        itemView.setOnClickListener(v -> onItemClick(v, getAdapterPosition()));
    }

    public abstract void onBindViewHolder(int position);
    public abstract void onItemClick(View view, int position);
}
