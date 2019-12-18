package com.example.HealthyCampus.common.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.amap.api.services.route.WalkStep;
import com.bumptech.glide.Glide;
import com.example.HealthyCampus.R;
import com.example.HealthyCampus.common.data.Bean.RoadDetailBean;
import com.example.HealthyCampus.common.utils.GlideUtils;
import com.example.HealthyCampus.common.widgets.pullrecycler.BaseViewHolder;

import java.util.LinkedList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class RoadDetailAdapter extends RecyclerView.Adapter<BaseViewHolder> {
    private Context context;
    private List<RoadDetailBean> mData = new LinkedList<>();
    private int type = 0;
    private StringBuffer stringBuffer = new StringBuffer();

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


    public RoadDetailAdapter(Context context, List<RoadDetailBean> mData) {
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

//    @Override
//    public void onViewRecycled(@NonNull BaseViewHolder holder) {
//        if (holder != null) {
//            Glide.with(context).clear((ImageView) holder.itemView.findViewById(R.id.ivIcon));
//        }
//        super.onViewRecycled(holder);
//    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.find_nearby_map_detail_item, parent, false);
        return new ItemViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder holder, int position) {
        holder.onBindViewHolder(position);
    }

    //展示的item
    class ItemViewHolder extends BaseViewHolder {

        @BindView(R.id.tvRoad)
        TextView tvRoad;
        @BindView(R.id.ivIcon)
        ImageView ivIcon;

        public ItemViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

        @Override
        public void onBindViewHolder(int position) {
            Log.e("NearbyActivity:" + "123456", "11111111111111111111111111");
            int size = mData.get(position).getAction().length();
            if ("右转".equals(mData.get(position).getAction())) {
                loadImage(R.drawable.map_turn_right);
            } else if ("左转".equals(mData.get(position).getAction())) {
                loadImage(R.drawable.map_turn_left);
            } else if ("向左前方行走".equals(mData.get(position).getAction()) || "向左前方行驶".equals(mData.get(position).getAction())) {
                loadImage(R.drawable.map_up_left);
            } else if ("向右前方行走".equals(mData.get(position).getAction()) || "向右前方行驶".equals(mData.get(position).getAction())) {
                loadImage(R.drawable.map_up_right);
            } else if ("到达目的地".equals(mData.get(position).getAssistantAction())) {
                loadImage(R.drawable.map_end_position);
                size = 5;
            } else if ("向正东出发".equals(mData.get(position).getAction())) {
                loadImage(R.drawable.map_start_position);
            } else if (type != 0) {
                if ("左转调头".equals(mData.get(position).getAction())) {
                    loadImage(R.drawable.map_turn_around_left);
                } else if ("右转调头".equals(mData.get(position).getAction())) {
                    loadImage(R.drawable.map_turn_around_right);
                } else if ("向右后方行驶".equals(mData.get(position).getAction())) {
                    loadImage(R.drawable.map_down_right);
                } else if ("向左后方行驶".equals(mData.get(position).getAction())) {
                    loadImage(R.drawable.map_down_left);
                } else if ("进入环岛".equals(mData.get(position).getAction())) {
                    loadImage(R.drawable.map_in_round_about);
                } else if ("离开环岛".equals(mData.get(position).getAction())) {
                    loadImage(R.drawable.map_out_round_about);
                }
            } else {
                loadImage(R.drawable.map_up);
            }
            tvRoad.setText(boldAction(mData.get(position).getInstruction().substring(0, mData.get(position).getInstruction().length() - mData.get(position).getAction().length()) + "  " + mData.get(position).getAction(), size));
            Log.e("NearbyActivity:" + "123456", "2222222222222222222222222");
        }

        private SpannableString boldAction(String message, int size) {
            SpannableString spannableString = new SpannableString(message);
            spannableString.setSpan(new RelativeSizeSpan(1.1f), spannableString.length() - size, spannableString.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
            spannableString.setSpan(new ForegroundColorSpan(Color.parseColor("#000000")), spannableString.length() - size, spannableString.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            return spannableString;

        }

        private void loadImage(int res) {
//            Log.e("NearbyActivity:" + "123456", "888888888888");
//            Object tag = ivIcon.getTag();
//            Log.e("NearbyActivity:" + "123456", "tag" + tag);
//            if (tag != null) {
//                Log.e("NearbyActivity:" + "123456", "res" + res);
//                if (!tag.equals(res)) {
//                    Log.e("NearbyActivity:" + "123456", "777777777777777");
////                    Glide.with(context).clear(ivIcon);
//                }
//            }
            GlideUtils.displayMapImage(ivIcon, res);
//            ivIcon.setTag(res);          //给ImageView设置唯一标记。
            Log.e("NearbyActivity:" + "123456", "55555555555555555555");
        }


        @Override
        public void onItemClick(View view, int position) {

        }
    }
}

