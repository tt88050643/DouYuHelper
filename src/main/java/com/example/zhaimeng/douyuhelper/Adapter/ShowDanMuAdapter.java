package com.example.zhaimeng.douyuhelper.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.zhaimeng.douyuhelper.R;
import com.example.zhaimeng.douyuhelper.Bean.DanMu;

import java.util.ArrayList;

/**
 * Created by zhaimeng on 2016/5/7.
 * 这个Adapter是接收弹幕的ListView的Adapter
 */
public class ShowDanMuAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<DanMu> list;

    public ShowDanMuAdapter(Context context, ArrayList<DanMu> list){
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    public final class ViewHolder {
        public TextView tvName;
        public TextView tvContent;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ShowDanMuAdapter.ViewHolder holder = null;
        Log.i("position", "" + position);
        if(convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.danmu_item, null);
            holder = new ShowDanMuAdapter.ViewHolder();
            holder.tvName = (TextView) convertView.findViewById(R.id.tv_danmu_name);
            holder.tvContent = (TextView) convertView.findViewById(R.id.tv_danmu_content);
            convertView.setTag(holder);
        } else {
            holder = (ShowDanMuAdapter.ViewHolder) convertView.getTag();
        }
        holder.tvName.setText(list.get(position).getNickname());
        holder.tvContent.setText((CharSequence) list.get(position).getContent());
        return convertView;
    }
}
