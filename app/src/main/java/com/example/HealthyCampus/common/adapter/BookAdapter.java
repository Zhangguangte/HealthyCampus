package com.example.HealthyCampus.common.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.HealthyCampus.R;
import com.example.HealthyCampus.common.network.vo.BookVo;
import com.example.HealthyCampus.common.widgets.pullrecycler.BaseViewHolder;
import com.example.HealthyCampus.module.Mine.Service.Library.Detail.LibraryDetailActivity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class BookAdapter extends RecyclerView.Adapter<BaseViewHolder> {

    private Context context;
    private onItemClick onItemClick;
    private List<BookVo> mData;

    public void clear() {
        mData.clear();
        notifyDataSetChanged();
    }


    public BookAdapter(Context context, List<BookVo> mData) {
        this.context = context;
        this.mData = mData;
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


    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.mine_service_library_book_item, parent, false);
        return new ItemViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder holder, int position) {
        holder.onBindViewHolder(position);
    }

    //展示的item
    class ItemViewHolder extends BaseViewHolder {

        @BindView(R.id.tvBookName)
        TextView tvBookName;
        @BindView(R.id.tvAuthor)
        TextView tvAuthor;
        @BindView(R.id.tvPublish)
        TextView tvPublish;
        @BindView(R.id.tvClass)
        TextView tvClass;
        @BindView(R.id.tvState)
        TextView tvState;
        @BindView(R.id.ItemLayout)
        RelativeLayout ItemLayout;

        public ItemViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }


        @SuppressLint("SetTextI18n")
        @Override
        public void onBindViewHolder(int position) {
            tvBookName.setText(mData.get(position).getBookName());
            tvAuthor.setText("作者:" + mData.get(position).getAuthor());
            tvPublish.setText(mData.get(position).getPublish());
            tvClass.setText(mData.get(position).getClassify());
            tvState.setText(mData.get(position).getRest() > 0 ? "可借" : "暂无");
            tvState.getBackground().setLevel(mData.get(position).getRest() > 0 ? 1 : 2);
            ItemLayout.setOnClickListener(v -> {
                Intent intent = new Intent(context, LibraryDetailActivity.class);
                intent.putExtra("ID", mData.get(position).getId());
                context.startActivity(intent);
            });

        }


        @Override
        public void onItemClick(View view, int position) {

        }
    }
}

