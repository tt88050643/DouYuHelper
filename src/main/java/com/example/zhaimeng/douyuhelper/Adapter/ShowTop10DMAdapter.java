package com.example.zhaimeng.douyuhelper.Adapter;

import android.content.Context;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.zhaimeng.douyuhelper.Bean.DanMu;
import com.example.zhaimeng.douyuhelper.R;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Objects;

/**
 * Created by zhaimeng on 2016/5/7.
 * 这个Adapter是接收弹幕的ListView的Adapter
 */
public class ShowTop10DMAdapter extends BaseAdapter {
    private Context context;
    private LinkedHashMap<String, ArrayList<DanMu>> linkedHashMap;

    public ShowTop10DMAdapter(Context context, LinkedHashMap<String, ArrayList<DanMu>> linkedHashMap){
        this.context = context;
        this.linkedHashMap = linkedHashMap;
    }

    @Override
    public int getCount() {
        return linkedHashMap.size();
    }

    @Override
    public Object getItem(int position) {
        return (String)getKeyByIndexInLHM(linkedHashMap, position);
    }

    /**
     * 根据index找出index对应的key
     * @param LHM LinkedHashMap
     * @param index 要查找LHM中的哪个
     * @return key
     */
    private Object getKeyByIndexInLHM(LinkedHashMap LHM, int index){
        if(index > LHM.size()) {
            try {
                throw new Exception("index超过LHM的size");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        Object nickName = null;
        int pos = -1;
        for (Iterator<String> iterator = linkedHashMap.keySet().iterator(); iterator.hasNext()&&pos!=(index); pos++) {
            nickName = iterator.next();
        }
        return nickName;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    public final class ViewHolder {
        public TextView tvName;
        public TextView tvCount;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ShowTop10DMAdapter.ViewHolder holder = null;
        if(convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.danmu_item, null);
            holder = new ShowTop10DMAdapter.ViewHolder();
            holder.tvName = (TextView) convertView.findViewById(R.id.tv_danmu_name);
            holder.tvCount = (TextView) convertView.findViewById(R.id.tv_danmu_content);
            convertView.setTag(holder);
        } else {
            holder = (ShowTop10DMAdapter.ViewHolder) convertView.getTag();
        }
        holder.tvName.setText((String)getKeyByIndexInLHM(linkedHashMap, position));
        String s = (String)getKeyByIndexInLHM(linkedHashMap, position);
        holder.tvCount.setText(String.valueOf(linkedHashMap.get(getKeyByIndexInLHM(linkedHashMap, position)).size()));
        return convertView;
    }
}
