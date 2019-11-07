package com.example.HealthyCampus.common.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.HealthyCampus.R;
import com.example.HealthyCampus.common.network.vo.RequestFriendVo;
import com.example.HealthyCampus.common.widgets.pullrecycler.BaseViewHolder;

import org.raphets.roundimageview.RoundImageView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class MessageRecyclerAdapter extends RecyclerView.Adapter<BaseViewHolder> {
    private Context context;
    private List<RequestFriendVo> data = new ArrayList<RequestFriendVo>();
    private boolean val = false;
    private onItemClick onItemClick;

    public void addList(List<RequestFriendVo> newList) {
        if (null != newList && newList.size() > 0) {
            data.addAll(newList);
            notifyDataSetChanged();
        }
    }

    public void clear() {
        data.clear();
        notifyDataSetChanged();
    }

    public void agree(int position) {
        data.get(position).setStatus("AGREE");
        notifyDataSetChanged();
    }


    public MessageRecyclerAdapter(List<RequestFriendVo> data, Context context, boolean val, onItemClick onItemClick) {
        this.data = data;
        this.context = context;
        this.val = val;
        this.onItemClick = onItemClick;
    }


    public MessageRecyclerAdapter(List<RequestFriendVo> data, Context context, boolean val) {
        this.data = data;
        this.context = context;
        this.val = val;
    }


    public MessageRecyclerAdapter(List<RequestFriendVo> data, Context context, onItemClick onItemClick) {
        this.data = data;
        this.context = context;
        this.onItemClick = onItemClick;
    }


    public MessageRecyclerAdapter(List<RequestFriendVo> data, Context context) {
        this.data = data;
        this.context = context;
    }


    @Override
    public int getItemCount() {
        if (null != data && data.size() > 0) {
            if (val && data.size() > 3)
                return 3;
            else
                return data.size();
        }
        return 0;
    }


    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.user_new_friend_item, parent, false);
        return new ItemViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder holder, int position) {
        holder.onBindViewHolder(position);
    }

    public interface onItemClick {
        void onItemButtonClick(String userid, int position);

        void onItemViewClick(String userid, String nickname, String content, String status, int position);
    }

    //展示的item
    class ItemViewHolder extends BaseViewHolder {

        @BindView(R.id.tvNickname)
        TextView tvNickname;
        @BindView(R.id.tvContent)
        TextView tvContent;
        @BindView(R.id.btnOperation)
        Button btnOperation;
        @BindView(R.id.rivHead)
        RoundImageView rivHead;
        private boolean val = false;

        public ItemViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

        @Override
        public void onBindViewHolder(int position) {
            tvNickname.setText(data.get(position).getNickname());
            tvContent.setText(data.get(position).getContent().length() > 15 ? data.get(position).getContent().substring(0, 13) + "..." : data.get(position).getContent());
            switch (data.get(position).getStatus()) {
                case "AGREE":
                case "F_AGREE":
                    btnOperation.setText("已同意");
                    val = true;
                    break;
                case "F_REFUSE":
                    tvContent.setText(R.string.user_new_friend_refuse);
                    btnOperation.setVisibility(View.GONE);
                    break;
                case "REFUSE":
                    btnOperation.setText(R.string.user_new_friend_apply_alrea_refuse);
                    val = true;
                    break;
                case "F_REQUEST":
                    btnOperation.setText("等待验证");
                    tvContent.setText(R.string.user_add_friend_already_send);
                    val = true;
                    break;
                case "REQUEST":
                    btnOperation.setText("同意");
                    val = false;
                    break;
                default:
                    btnOperation.setVisibility(View.GONE);
                    break;
            }
            if (val) {
                btnOperation.setTextColor(context.getResources().getColor(R.color.hint_color));
                btnOperation.setBackground(null);
                btnOperation.setEnabled(false);
            } else {
                btnOperation.setBackground(context.getResources().getDrawable(R.drawable.shape_button_lines));
                btnOperation.setTextColor(context.getResources().getColor(R.color.white));
                btnOperation.setEnabled(true);
            }
            Log.e("MessageRecy" + "123456", "onItemClick" + onItemClick);
            btnOperation.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClick.onItemButtonClick(data.get(position).getUser_id(), position);
                }
            });
//            Picasso.with(context)
//                    .load(data.get(position).getHeadImg())
//                    .placeholder(R.mipmap.head_default)
//                    .into(rivHead);


        }

        @Override
        public void onItemClick(View view, int position) {
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClick.onItemViewClick(data.get(position).getRequest_user_id(), data.get(position).getNickname(), data.get(position).getContent(), data.get(position).getStatus(), position);
                }
            });
        }
    }
}

