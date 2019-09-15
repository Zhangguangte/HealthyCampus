package com.example.HealthyCampus.common.network.api;

import com.example.HealthyCampus.common.data.model.BeforeNewsBean;
import com.example.HealthyCampus.common.data.model.HomePageArticleBean;
import com.example.HealthyCampus.common.data.model.LatestNewsBean;

import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * OK
 */
public interface HomePageApi {

    /**
     * 获取健康资讯最新文章列表
     *
     * @return
     */
    @GET("news/latest")
    Observable<LatestNewsBean> getHomePageLatestNews();


    /**
     * 获取知乎指定日期文章列表
     *
     * @param date 日期
     * @return
     */
    @GET("news/before/{date}")
    Observable<BeforeNewsBean> getHomePageBeforeNews(@Path("date") String date);

    /**
     * 获取健康资讯指定日期文章列表
     *
     * @param articleId 文章Id
     * @return
     */
    @GET("news/{articleId}")
    Observable<HomePageArticleBean> getHomePageArticle(@Path("articleId") String articleId);

}
