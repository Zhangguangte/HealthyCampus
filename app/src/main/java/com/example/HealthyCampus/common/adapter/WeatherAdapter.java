package com.example.HealthyCampus.common.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.HealthyCampus.R;
import com.example.HealthyCampus.common.data.Bean.WeatherBean;
import com.example.HealthyCampus.common.utils.DateUtils;
import com.example.HealthyCampus.common.widgets.pullrecycler.BaseViewHolder;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class WeatherAdapter extends RecyclerView.Adapter<BaseViewHolder> {
    private Context context;
    private List<WeatherBean> mData;
    private int type = 0;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public void clear() {
        mData.clear();
        notifyDataSetChanged();
    }


    public WeatherAdapter(Context context, List<WeatherBean> mData) {
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

    public interface onItemClick {

    }


    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.mine_service_weather_item, parent, false);
        return new ItemViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder holder, int position) {
        holder.onBindViewHolder(position);
    }

    //展示的item
    class ItemViewHolder extends BaseViewHolder {

        @BindView(R.id.tvWeek)
        TextView tvWeek;
        @BindView(R.id.tvWeather)
        TextView tvWeather;
        @BindView(R.id.tvTemp1)
        TextView tvTemp1;
        @BindView(R.id.tvTemp2)
        TextView tvTemp2;

        public ItemViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

        @Override
        public void onBindViewHolder(int position) {
            Log.e("WeatherActivity" + "123456", "mData.get(position).getWeek()"+mData.get(position).getWeek());
            tvWeek.setText(DateUtils.weekStr[mData.get(position).getWeek()]);
            tvWeather.setText(mData.get(position).getWeather());
            String[] str = mData.get(position).getTemp().split("~");
            tvTemp1.setText(str[1]);
            tvTemp2.setText(str[0]);
        }


        @Override
        public void onItemClick(View view, int position) {

        }
    }
}

