package com.example.health.fragment.HomePage;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.health.Entity.Information;
import com.example.health.R;
import java.util.ArrayList;
import java.util.List;

public class HomePageAdapter extends BaseAdapter {

    private Context context;
    private List<Information> informationList;

    public HomePageAdapter(Context context,List<Information> informationList)
    {
        super();
        this.context = context;
        this.informationList = informationList;
    }

    @Override
    public int getCount() {
        return informationList.size();
    }

    @Override
    public Object getItem(int position) {
        return informationList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if(convertView == null )
        {
            convertView = LayoutInflater.from(context).inflate(R.layout.information_item,parent,false);
            holder = new ViewHolder();
            holder.information_head_title = convertView.findViewById(R.id.information_head_title);
           // holder.information_content = convertView.findViewById(R.id.information_content);
            holder.information_date = convertView.findViewById(R.id.information_date);
            holder.information_pic = convertView.findViewById(R.id.information_pic);
            convertView.setTag(holder);
        }
        else
        {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.information_head_title.setText(informationList.get(position).getTitle());
     //   holder.information_content.setText(informationList.get(position).getContent());
        holder.information_date.setText(informationList.get(position).getDate());
        holder.information_pic.setImageResource(R.drawable.defult);

//        holder.information_pic.setImageURI(Uri.parse(informationList.get(position).getPicture()));
        return convertView;
    }
    /**
     * 一次性设置一系列控件的可见性
     *
     * @param views
     *            ArrayList<View>类型，要设置可见性的控件封装
     * @param visivility
     *            boolean类型，true表示可见，false表示不可见
     */
    private void setViewsVisibility(ArrayList<View> views, boolean visivility) {
        for (View view : views) {
            view.setVisibility(visivility ? View.VISIBLE : View.GONE);
        }
    }

    private static class ViewHolder {
        private TextView information_head_title; //标题
        private TextView information_content;//内容
        private TextView information_date; //日期
        private ImageView information_pic; //资讯图片
    }
}
