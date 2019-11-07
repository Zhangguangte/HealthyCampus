package com.example.HealthyCampus.common.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.HealthyCampus.R;
import com.example.HealthyCampus.common.network.vo.RequestFriendVo;
import com.example.HealthyCampus.common.utils.LogUtil;
import com.example.HealthyCampus.common.utils.ToastUtil;
import com.example.HealthyCampus.common.widgets.pullrecycler.BaseViewHolder;
import com.example.HealthyCampus.greendao.model.SearchAdd;

import org.raphets.roundimageview.RoundImageView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class AddSearchAdapter extends RecyclerView.Adapter<BaseViewHolder> {
    private Context context;
    private List<SearchAdd> list = new ArrayList<SearchAdd>();
    private onItemOnClick onItemOnClick;

    public void clear() {
        list.clear();
        notifyDataSetChanged();
    }


    public AddSearchAdapter(Context context, List<SearchAdd> list, onItemOnClick onItemOnClick) {
        this.list = list;
        this.context = context;
        this.onItemOnClick = onItemOnClick;
    }


    @Override
    public int getItemCount() {
        if (null != list && list.size() > 0) {
            return list.size();
        }
        return 0;
    }

    public interface onItemOnClick {
        void onItemOnClick(String content);
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LogUtil.logE("MessageRecyclerAdapter" + "1234567", "viewType" + viewType);
        View view = LayoutInflater.from(context).inflate(R.layout.user_add_frend_history, parent, false);
        return new ItemViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder holder, int position) {
        holder.onBindViewHolder(position);
        LogUtil.logE("AddSearchAdapter" + "1234567", "data.get(position)" + list.get(position).toString());
    }

    //展示的item
    class ItemViewHolder extends BaseViewHolder {

        @BindView(R.id.tvHistory)
        TextView tvHistory;

        public ItemViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

        @Override
        public void onBindViewHolder(int position) {
            tvHistory.setText(list.get(position).getContent());
        }

        @Override
        public void onItemClick(View view, int position) {
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemOnClick.onItemOnClick(list.get(position).getContent());
                }
            });
        }
    }
}

