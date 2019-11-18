package com.example.HealthyCampus.common.widgets.chat;

import android.content.Context;
import android.text.TextPaint;
import android.text.style.ClickableSpan;
import android.view.View;


public class TopicClickableSpan extends ClickableSpan {
    private int color = 0xff497eb0;
    private Context context;
    private String text;

    public TopicClickableSpan(String text, Context context) {
        super();
        this.text = text;
        this.context = context;
    }

    @Override
    public void updateDrawState(TextPaint ds) {
        ds.setColor(color);
    }

    @Override
    public void onClick(View widget) {
       /* Intent intent = new Intent(context, TopicActivity.class);
        intent.putExtra("topic", text.replaceAll("#",""));
        Log.d("DEBUG", "onClickonClickonClick");
        context.startActivity(intent);*/
    }
}
