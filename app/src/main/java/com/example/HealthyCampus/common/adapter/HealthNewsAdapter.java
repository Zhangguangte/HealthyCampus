package com.example.HealthyCampus.common.adapter;

import android.content.Context;
import android.content.Intent;
import android.text.Html;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.HealthyCampus.R;
import com.example.HealthyCampus.common.data.Bean.HealthMessage;
import com.example.HealthyCampus.common.utils.GlideUtils;
import com.example.HealthyCampus.module.Find.News.list.Article.HealthNewsArticleActivity;

import java.util.ArrayList;
import java.util.List;


public class HealthNewsAdapter extends BaseQuickAdapter<HealthMessage, BaseViewHolder> {

    private Context context;
    private List<HealthMessage> mData = new ArrayList<HealthMessage>();

    public HealthNewsAdapter(Context context, List<HealthMessage> mData) {
        super(R.layout.find_health_news_item);
        this.context = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, HealthMessage item) {

        helper.setText(R.id.tvIntro, item.getIntro())
                .setText(R.id.tvNewsTitle, item.getTitle());
        TextView date = helper.getView(R.id.tvDate);
        date.setText(Html.fromHtml(item.getDate()));

        ImageView ivIcon = helper.getView(R.id.ivIcon);
        GlideUtils.display(ivIcon, item.getImgurl());

        helper.setOnClickListener(R.id.allLiner, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra("TITLE", item.getTitle());
                intent.putExtra("URL", item.getHref());
                intent.putExtra("PICTURE_URL", item.getImgurl());
                intent.setClass(context, HealthNewsArticleActivity.class);
                context.startActivity(intent);
            }
        });

    }
}

