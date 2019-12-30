package com.example.HealthyCampus.common.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.HealthyCampus.R;
import com.example.HealthyCampus.common.data.Bean.WeatherBean;
import com.example.HealthyCampus.common.utils.DateUtils;
import com.example.HealthyCampus.common.widgets.pullrecycler.BaseViewHolder;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class CityAdapter extends RecyclerView.Adapter<BaseViewHolder> {
    private Context context;
    private onItemClick onItemClick;
    private List<String> mData;

    public void clear() {
        mData.clear();
        notifyDataSetChanged();
    }


    public CityAdapter(Context context, onItemClick onItemClick) {
        this.context = context;
        this.onItemClick = onItemClick;
        mData = Arrays.asList(context.getResources().getStringArray(R.array.weather_city));
    }

    @Override
    public int getItemCount() {
        if (null != mData && mData.size() > 0) {
            return mData.size();
        }
        return 0;
    }

    public interface onItemClick {
        void selectedCity(String cityName);
    }


    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.mine_service_weather_city_item, parent, false);
        return new ItemViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder holder, int position) {
        holder.onBindViewHolder(position);
    }

    //展示的item
    class ItemViewHolder extends BaseViewHolder {

        @BindView(R.id.btnCity)
        Button btnCity;

        public ItemViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

        @SuppressLint("SetTextI18n")
        @Override
        public void onBindViewHolder(int position) {
            btnCity.setText(mData.get(position)+"市");
            btnCity.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClick.selectedCity(mData.get(position));
                }
            });
        }


        @Override
        public void onItemClick(View view, int position) {

        }
    }
}

