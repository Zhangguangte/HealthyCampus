package com.example.HealthyCampus.common.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v4.util.ArrayMap;
import android.support.v4.view.PagerAdapter;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.GridView;

import com.example.HealthyCampus.common.utils.SpanStringUtils;
import com.example.HealthyCampus.common.utils.EmojiUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;


public class EmojiPagerAdapter extends PagerAdapter {
    private List<View> mViews;
    private EditText mEditText;
    private Context mContext;
    private int mEmojiType = EmojiUtils.EMOTION_CLASSIC_TYPE;


    public EmojiPagerAdapter(Context context, EditText editText) {
        mEditText = editText;
        mContext = context;
        mViews = getEmojiPagers();
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(mViews.get(position));
    }

    @Override
    public int getCount() {
        return mViews.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        container.addView(mViews.get(position));
        return mViews.get(position);
    }


    private List<View> getEmojiPagers() {

        WindowManager manager = ((Activity) mContext).getWindowManager();
        DisplayMetrics outMetrics = new DisplayMetrics();
        manager.getDefaultDisplay().getMetrics(outMetrics);
        int screenWidth = outMetrics.widthPixels;                       // 获取屏幕宽度
        int spacing = (int) (outMetrics.density * 15);                  // item的间距
        int itemWidth = (screenWidth - spacing * 8) / 8;                // 动态计算item的宽度和高度
        int gvHeight = itemWidth * 3 + spacing * 3;                                   //动态计算gridview的总高度
//        Log.e("EmojiPagerAdapter" + "123456", "screenWidth: " + screenWidth);
//        Log.e("EmojiPagerAdapter" + "123456", "spacing: " + spacing);
//        Log.e("EmojiPagerAdapter" + "123456", "itemWidth: " + itemWidth);
//        Log.e("EmojiPagerAdapter" + "123456", "gvHeight: " + gvHeight);

        List<View> list = new ArrayList<View>();
        ArrayMap<String, Integer> emojis = EmojiUtils.getEmojis(mEmojiType);
        Set<String> emojisAll = emojis.keySet();
        ArrayList<String> pager = new ArrayList<String>();

        for (String name : emojisAll) {
            pager.add(name);
            if (pager.size() == EmojiUtils.MAXNUM_ONEPAGER) {
                list.add(createEmojiGridView(pager, screenWidth, gvHeight, spacing, itemWidth));
                pager = new ArrayList<String>();
            }
        }

        if (pager.size() > 0) {
            list.add(createEmojiGridView(pager, screenWidth, gvHeight, spacing, itemWidth));
        }

        return list;
    }

    public void setText(int emojiType, CharSequence text) {
        // 获取当前光标位置,在指定位置上添加表情图片文本
        int curPosition = mEditText.getSelectionStart();
        StringBuilder sb = new StringBuilder(mEditText.getText().toString());
        sb.insert(curPosition, text);
        // 特殊文字处理,将表情等转换一下
        mEditText.setText(SpanStringUtils.getEmojiContent(emojiType,
                mContext, (int) mEditText.getTextSize() * 12 / 10, sb.toString()));
        // 将光标设置到新增完表情的右侧
        mEditText.setSelection(curPosition + text.length());
    }

    public void deleteText() {
        mEditText.dispatchKeyEvent(new KeyEvent(
                KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_DEL));
    }

    private GridView createEmojiGridView(List<String> emojis, int gvWidth, int gvHeight, int padding, int itemWidth) {

        //加入最后一个delete
        emojis.add(SpanStringUtils.DELETE);
        // 创建GridView
        GridView gv = new GridView(mContext);
        //设置点击背景透明
        gv.setSelector(android.R.color.transparent);
        //设置7列
        gv.setNumColumns(8);
        gv.setGravity(Gravity.CENTER_HORIZONTAL);
        gv.setPadding(padding, padding, padding, padding);
        gv.setHorizontalSpacing(padding);
        gv.setVerticalSpacing(padding);
        //设置GridView的宽高
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(gvWidth, gvHeight);
        gv.setLayoutParams(params);

        //设置emoji点击事件
        EmojiGridAdapter.OnClickEmojiListener listener = new EmojiGridAdapter.OnClickEmojiListener() {
            @Override
            public void onClick(CharSequence text) {
                setText(mEmojiType, text);
            }

            @Override
            public void onDelete() {
                deleteText();
            }
        };

        // 给GridView设置表情图片
        EmojiGridAdapter adapter = new EmojiGridAdapter(mContext, emojis, mEmojiType, itemWidth, listener);
        gv.setAdapter(adapter);
        return gv;
    }
}
