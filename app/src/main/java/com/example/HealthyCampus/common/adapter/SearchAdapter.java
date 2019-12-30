package com.example.HealthyCampus.common.adapter;

import android.content.Context;
import android.content.Intent;
import android.text.Html;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.HealthyCampus.R;
import com.example.HealthyCampus.common.data.Bean.SearchBean;
import com.example.HealthyCampus.module.Find.News.list.Article.HealthNewsArticleActivity;

public class SearchAdapter extends BaseQuickAdapter<SearchBean, BaseViewHolder> {
    Context context;

    public SearchAdapter(Context context) {
        super(R.layout.find_hralth_news_search_item);
        this.context = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, final SearchBean item) {
        helper.setText(R.id.tvTitle, Html.fromHtml(item.getTitle()))
                .setText(R.id.tvIntro, Html.fromHtml(item.getIntro()))
                .setText(R.id.tvType, Html.fromHtml(item.getType()));
        helper.setOnClickListener(R.id.searchLayout, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra("TITLE", item.getTitle());
                intent.putExtra("URL", item.getHref());
                intent.setClass(context, HealthNewsArticleActivity.class);
                context.startActivity(intent);
            }
        });
    }
}