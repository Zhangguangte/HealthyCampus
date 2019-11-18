package com.example.HealthyCampus.common.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.amap.api.maps.model.LatLng;
import com.amap.api.services.core.PoiItem;
import com.example.HealthyCampus.R;
import com.example.HealthyCampus.common.widgets.pullrecycler.BaseViewHolder;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AmapListAdapter extends RecyclerView.Adapter<BaseViewHolder> {
    private Context context;
    private ArrayList<PoiItem> pois;
    private onItemClick onItemClick;
    private StringBuilder stringBuilder = new StringBuilder();

    public void clearAddAll(List<PoiItem> poiItemList) {
        pois.clear();
        pois.addAll(poiItemList);
        notify();
    }

    public void clearAll() {
        pois.clear();
        notifyDataSetChanged();
    }

    public void addAll(List<PoiItem> poiItemList) {
        pois.addAll(poiItemList);
        notifyDataSetChanged();
    }


    public AmapListAdapter(Context context, List<PoiItem> pois, onItemClick onItemClick) {
        this.context = context;
        this.onItemClick = onItemClick;
        this.pois = (ArrayList<PoiItem>) pois;
    }


    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.chats_map_address_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder holder, int position) {
        holder.onBindViewHolder(position);
    }

    public interface onItemClick {
        void relocation(String address, LatLng latLng);
    }

    @Override
    public int getItemCount() {
        if (null != pois && pois.size() > 0) {
            return pois.size();
        }
        return 0;
    }

    public void refresh() {
        notifyDataSetChanged();
    }

    class ViewHolder extends BaseViewHolder {
        @BindView(R.id.tv_current)
        TextView tvCurrent;
        @BindView(R.id.tv_big_address)
        TextView tvBigAddress;
        @BindView(R.id.tv_small_address)
        TextView tvSmallAddress;
        @BindView(R.id.addressItemLayout)
        LinearLayout addressItemLayout;

        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

        @Override
        public void onBindViewHolder(int position) {
            tvBigAddress.setText(pois.get(position).getTitle());
            tvSmallAddress.setText(pois.get(position).getSnippet());
        }

        @Override
        public void onItemClick(View view, int position) {
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.e("MapActivity123456:", "pois.get(position).toString()" + pois.get(position).toString());
                    Log.e("MapActivity123456:", "pois.get(position).toString()" + pois.get(position).toString());
                    stringBuilder.setLength(0);
                    stringBuilder.append(pois.get(position).getProvinceName());
                    stringBuilder.append(pois.get(position).getCityName());
                    stringBuilder.append(pois.get(position).getAdName());
                    stringBuilder.append(pois.get(position).getTitle());
                    stringBuilder.append(pois.get(position).getSnippet());
                    onItemClick.relocation(stringBuilder.toString(), new LatLng(pois.get(position).getLatLonPoint().getLatitude(), pois.get(position).getLatLonPoint().getLongitude()));
                }
            });
        }
    }

}
