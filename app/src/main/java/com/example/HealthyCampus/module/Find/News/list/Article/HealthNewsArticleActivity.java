package com.example.HealthyCampus.module.Find.News.list.Article;

import android.content.Context;
import android.os.Bundle;
import android.text.Html;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.UnderlineSpan;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.HealthyCampus.R;
import com.example.HealthyCampus.common.constants.ConstantValues;
import com.example.HealthyCampus.common.data.Bean.ArticleDetailBean;
import com.example.HealthyCampus.common.network.NetworkManager;
import com.example.HealthyCampus.common.utils.DensityUtil;
import com.example.HealthyCampus.common.utils.GlideUtils;
import com.example.HealthyCampus.framework.BaseActivity;
import com.example.HealthyCampus.module.Find.News.HealthNewsMessageManager;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Request;

import static android.view.View.VISIBLE;


public class HealthNewsArticleActivity extends BaseActivity<HealthNewsArticleContract.View, HealthNewsArticleContract.Presenter> implements HealthNewsArticleContract.View {


    //自定义标题框
    @BindView(R.id.tvTitle)
    TextView tvTitle;
    @BindView(R.id.ivBack)
    ImageView ivBack;

    @BindView(R.id.tvIntroduction)
    TextView tvIntroduction;
    @BindView(R.id.tvAntistop)
    TextView tvAntistop;
    @BindView(R.id.ivDetailImg)
    ImageView ivDetailImg;
    @BindView(R.id.tvContent)
    TextView tvContent;

    private ArticleDetailBean articleDetailBean;

    @Override
    protected void setUpContentView() {
        setContentView(R.layout.find_health_news_article);
    }

    @Override
    protected HealthNewsArticleContract.Presenter createPresenter() {
        return new HealthNewsArticlePresenter();
    }

    @Override
    protected void initView() {
        setCustomActionBar();
    }

    private void setCustomActionBar() {
        tvTitle.setText(Html.fromHtml(getIntent().getStringExtra("TITLE").replace("color=\"red\"", "")));
        ivBack.setVisibility(VISIBLE);
    }


    @Override
    public void onBackPressed() {
        finish();
    }

    @OnClick(R.id.ivBack)
    public void ivBack(View view) {
        finish();
    }


    @Override
    protected void initData(Bundle savedInstanceState) {
        showProgressDialog(getString(R.string.loading_footer_tips));
        getArticleData();
    }


    @Override
    public Context getContext() {
        return this;
    }


    private void getArticleData() {
        NetworkManager.getAsync(ConstantValues.BASE_URL_NEWS + getIntent().getStringExtra("URL"), new NetworkManager.DataCallBack() {
            @Override
            public void requestFailure(Request request, Exception e) {
                dismissProgressDialog();
            }

            @Override
            public void requestSuccess(String result) {
                Document document = Jsoup.parse(result, ConstantValues.BASE_URL_NEWS);
                articleDetailBean = HealthNewsMessageManager.getArticleData(document);
                tvIntroduction.setText(Html.fromHtml(articleDetailBean.getIntro()));
                SpannableString sp = new SpannableString(articleDetailBean.getIntroduce());
                sp.setSpan(new UnderlineSpan(), 0, articleDetailBean.getIntroduce().length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                tvAntistop.setText(sp);
                tvContent.setText(Html.fromHtml(articleDetailBean.getContent()));
                String imgUrl = articleDetailBean.getImgeUrl();
                if (!TextUtils.isEmpty(getIntent().getStringExtra("PICTURE_URL")))
                    GlideUtils.display(ivDetailImg, getIntent().getStringExtra("PICTURE_URL"));
                else if (!TextUtils.isEmpty(imgUrl)) {
                    GlideUtils.display(ivDetailImg, ConstantValues.BASE_URL_NEWS + imgUrl);
                } else {
                    ivDetailImg.setImageResource(R.mipmap.find_news_article_default_picture);
                }
                dismissProgressDialog();
            }
        });
    }
}
