package com.example.HealthyCampus.common.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.HealthyCampus.R;
import com.example.HealthyCampus.common.widgets.pullrecycler.BaseViewHolder;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;


public class HostAdapter extends RecyclerView.Adapter<BaseViewHolder> {
    private Context context;
    private onItemClick onItemClick;
    private List<String> mData;
    private boolean isHost = true;

    public void clear() {
        mData.clear();
        notifyDataSetChanged();
    }


    public HostAdapter(Context context, onItemClick onItemClick) {
        this.context = context;
        this.onItemClick = onItemClick;
        mData = Arrays.asList(context.getResources().getStringArray(R.array.library_host));
        isHost = true;
    }

    public HostAdapter(Context context, List<String> mData, onItemClick onItemClick) {
        this.context = context;
        this.onItemClick = onItemClick;
        this.mData = mData;
        isHost = false;
    }

    @Override
    public int getItemCount() {
        if (null != mData && mData.size() > 0) {
            return mData.size();
        }
        return 0;
    }

    public interface onItemClick {
        void selectedName(String bookName);
    }


    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.mine_service_library_item, parent, false);
        return new ItemViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder holder, int position) {
        holder.onBindViewHolder(position);
    }

    //展示的item
    class ItemViewHolder extends BaseViewHolder {

        @BindView(R.id.tvName)
        TextView tvName;

        public ItemViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

        @Override
        public void onBindViewHolder(int position) {
            tvName.setText(mData.get(position));
            tvName.getBackground().setLevel(isHost ? MyRandom(1, 5) : 1);
            tvName.setOnClickListener(v -> onItemClick.selectedName(mData.get(position)));
        }


        @Override
        public void onItemClick(View view, int position) {

        }

        public int MyRandom(int min, int max) {
            Random random = new Random();
            int s = random.nextInt(max) % (max - min + 1) + min;
            return s;
        }

    }
}

