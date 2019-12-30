package com.example.HealthyCampus.module.Find.News;

import com.example.HealthyCampus.common.constants.ConstantValues;
import com.example.HealthyCampus.common.data.Bean.ArticleDetailBean;
import com.example.HealthyCampus.common.data.Bean.HealthMessage;
import com.example.HealthyCampus.common.data.Bean.SearchBean;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

public class HealthNewsMessageManager {

    public static List<HealthMessage> getlist(Document document) {
        List<HealthMessage> healthBean = new ArrayList<>();
        Element element1 = document.select("div.w960").last();
        if (element1 != null) {
            Element element = element1.select("div.pleft").first();
            Element elements = element.select("div.listbox").first();
            Elements getlist = elements.select("ul.e2").select("li");
            for (Element list : getlist) {
                String imgurl = list.select("a").first().select("img").attr("src");
                String href = list.select("a").last().attr("href");
                String title = list.select("a").last().text();
                String date = list.select("span.info").html();
                String intro = list.select("p.intro").text();
                HealthMessage message = new HealthMessage();
                message.setDate(date);
                message.setImgurl(ConstantValues.BASE_URL_NEWS + imgurl);
                message.setHref(href);
                message.setTitle(title);
                message.setIntro(intro);
                healthBean.add(message);
            }
        }
        return healthBean;
    }

    public static List<SearchBean> getSearch(Document document) {
        List<SearchBean> searchBean = new ArrayList<>();
        Elements element = document.select("div.resultlist").select("div.item");
        if (element != null && element.size() > 0) {
            for (Element item : element) {
                String title = item.select("h3").first().select("a").html();
                String href = item.select("h3").first().select("a").attr("href");
                String intro = item.select("p.intro").first().html();
                String info = item.select("p.info").first().text();
                SearchBean search = new SearchBean();
                search.setTitle(title);
                search.setHref(href);
                search.setIntro(intro);
                search.setType(info);
                searchBean.add(search);
            }
        }
        return searchBean;
    }

    public static ArticleDetailBean getArticleData(Document document) {
        ArticleDetailBean message = new ArticleDetailBean();
        Element element = document.select("div#main").first();
        if (element == null) {
            return message;
        }
        Element element1 = element.select("div.left").first();
        if (element1 == null) {
            return message;
        }
        Element element2 = element1.select("div#content").first();
        if (element2 == null) {
            return message;
        }
        String introduce = null;
        String imageUrl = null;
        String txt = null;
        String intro = null;
        try {
            introduce = element.select("div.kw").first().text();
            imageUrl = element2.select("div").last().select("img").attr("src");
            txt = element1.select("p").html();
            intro = element1.select("div.intro").first().text();
        } catch (Exception ignored) {
        }
        message.setContent(txt);
        message.setIntro(intro);
        message.setImgeUrl(imageUrl);
        message.setIntroduce(introduce);

        return message;
    }


}
