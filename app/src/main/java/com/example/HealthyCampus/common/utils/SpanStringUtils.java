package com.example.HealthyCampus.common.utils;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ImageSpan;

import com.example.HealthyCampus.R;
import com.example.HealthyCampus.common.widgets.chat.TopicClickableSpan;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class SpanStringUtils {

    private static final String EMOJI = "emoji";
    public final static String DELETE = "[edelete]";

    public static SpannableString getEmojiDelete(final Context context, int itemWidth) {
        SpannableString spannableString;
        Resources res = context.getResources();
        // 利用表情名字获取到对应的图片
        Integer imgRes = R.drawable.edelete;
        spannableString = new SpannableString(EMOJI);
        Bitmap bitmap = BitmapFactory.decodeResource(res, imgRes);
        Bitmap scaleBitmap = Bitmap.createScaledBitmap(bitmap, itemWidth, itemWidth, true);
        ImageSpan span = new ImageSpan(context, scaleBitmap);
        spannableString.setSpan(span, 0, EMOJI.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return spannableString;
    }

    public static SpannableString getEmojiContent(int emotion_map_type, final Context context, int itemWidth, String name) {
        SpannableString spannableString = new SpannableString(name);
        Resources res = context.getResources();
        String regexEmotion = "\\[(e1f){1}[0-9]+]";

//        String regexEmotion = "\\[([\u4e00-\u9fa5\\w])+\\]";
        Pattern patternEmotion = Pattern.compile(regexEmotion);
        Matcher matcherEmotion = patternEmotion.matcher(spannableString);
        while (matcherEmotion.find()) {
            // 获取匹配到的具体字符
            String key = matcherEmotion.group();
            // 匹配字符串的开始位置
            int start = matcherEmotion.start();
            // 利用表情名字获取到对应的图片
            Integer imgRes = EmojiUtils.getImgByName(emotion_map_type,key);
            // 压缩表情图片
            Bitmap bitmap = BitmapFactory.decodeResource(res, imgRes);
            Bitmap scaleBitmap = Bitmap.createScaledBitmap(bitmap, itemWidth, itemWidth, true);
            ImageSpan span = new ImageSpan(context, scaleBitmap);
            spannableString.setSpan(span, start, start + key.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        return spannableString;
    }

//    public static SpannableString getTopicContent(Context context, SpannableString spannableString) {
//        String rule = "#[\\S]*#{1}";
//        Pattern patternTopic = Pattern.compile(rule);
//        Matcher matcherTopic = patternTopic.matcher(spannableString);
//        while (matcherTopic.find()) {
//            String key = matcherTopic.group();
//            int start = matcherTopic.start();
//            TopicClickableSpan span = new TopicClickableSpan(key, context);
//            spannableString.setSpan(span, start, start + key.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
//        }
//        return spannableString;
//    }

}
