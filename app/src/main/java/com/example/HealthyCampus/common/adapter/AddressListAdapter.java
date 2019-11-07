package com.example.HealthyCampus.common.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.SectionIndexer;
import android.widget.TextView;

import com.example.HealthyCampus.R;
import com.example.HealthyCampus.common.network.vo.AddressListVo;
import com.example.HealthyCampus.module.Mine.User.UserInformationActivity;

import org.raphets.roundimageview.RoundImageView;

import java.util.List;

public class AddressListAdapter extends BaseAdapter implements SectionIndexer {
    private List<AddressListVo> list = null;
    private Context mContext;
    private OnItemClickListener onItemClickListener;

    public AddressListAdapter(Context mContext, List<AddressListVo> list) {
        this.mContext = mContext;
        this.list = list;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    /**
     * 当ListView数据发生变化时,调用此方法来更新ListView
     *
     * @param list
     */
    public void updateListView(List<AddressListVo> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);

    }


    public int getCount() {
        return this.list.size();
    }

    public Object getItem(int position) {
        return list.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(final int position, View view, ViewGroup arg2) {
        /**得到通讯录联系人信息**/
        ViewHolder viewHolder;
        AddressListVo mContent=list.get(position);
        if (view == null) {
            viewHolder = new ViewHolder();
            view = LayoutInflater.from(mContext).inflate(R.layout.message_address_list_item, null);
//            viewHolder.headPortrait =  view.findViewById(R.id.headPortrait);
            viewHolder.nickName =  view.findViewById(R.id.nickName);
            viewHolder.catalogTitle = view.findViewById(R.id.catalogTitle);
            view.setTag(viewHolder);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, UserInformationActivity.class);
                    intent.putExtra("ACCOUNT", list.get(position).getAccount());
                    mContext.startActivity(intent);
                }
            });
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }

        //根据position获取分类的首字母的Char ascii值
        int section = getSectionForPosition(position);

        //如果当前位置等于该分类首字母的Char的位置 ，则认为是第一次出现
        if (position == getPositionForSection(section)) {
            viewHolder.catalogTitle.setVisibility(View.VISIBLE);
            viewHolder.catalogTitle.setText(mContent.getSortLetters());
        } else {
            viewHolder.catalogTitle.setVisibility(View.GONE);
        }

//        viewHolder.headPortrait.setBackgroundResource();
        viewHolder.nickName.setText(list.get(position).getNickname());

        return view;

    }


    final static class ViewHolder {
        RoundImageView headPortrait;
        TextView nickName;
        TextView catalogTitle;
    }


    /**
     * 根据ListView的当前位置获取分类的首字母的Char ascii值
     */
    public int getSectionForPosition(int position) {
        return list.get(position).getSortLetters().charAt(0);
    }

    /**
     * 根据分类的首字母的Char ascii值获取其第一次出现该首字母的位置
     */
    public int getPositionForSection(int section) {
        for (int i = 0; i < getCount(); i++) {
            String sortStr = list.get(i).getSortLetters();
            char firstChar = sortStr.toUpperCase().charAt(0);
            if (firstChar == section) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public Object[] getSections() {
        return null;
    }

    public void clear() {
        list.clear();
        notifyDataSetChanged();
    }


}
