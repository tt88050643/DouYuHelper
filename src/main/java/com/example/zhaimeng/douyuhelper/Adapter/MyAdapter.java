package com.example.zhaimeng.douyuhelper.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.zhaimeng.douyuhelper.Bean.DanMu;
import com.example.zhaimeng.douyuhelper.R;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;

/**
 * Created by zhaimeng on 2016/5/9.
 */
public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> implements View.OnClickListener {

    private Context context;
    private LinkedHashMap<String, ArrayList<DanMu>> linkedHashMap;

    private OnRecyclerViewItemClickListener mOnItemClickListener = null;

    public MyAdapter(Context context, LinkedHashMap<String, ArrayList<DanMu>> linkedHashMap){
        this.context = context;
        this.linkedHashMap = linkedHashMap;
    }
    //创建新View，被LayoutManager所调用
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.danmu_item, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(view);
        view.setOnClickListener(this);
        return viewHolder;
    }

    //将数据与界面进行绑定的操作
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        viewHolder.tvName.setText((String) getKeyByIndexInLHM(linkedHashMap, position));
        viewHolder.tvCount.setText(String.valueOf(linkedHashMap.get((String) getKeyByIndexInLHM(linkedHashMap, position)).size()));
        //get每个item设置一个Tag，便于通过Tag查找
        viewHolder.itemView.setTag((String) getKeyByIndexInLHM(linkedHashMap, position));
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

    //获取数据的数量
    @Override
    public int getItemCount() {
        return linkedHashMap.size();
    }

    @Override
    public void onClick(View v) {
        if (mOnItemClickListener != null) {
            //注意这里使用getTag方法获取数据
            mOnItemClickListener.onItemClick(v,(String)v.getTag());
        }
    }

    //自定义的ViewHolder，持有每个Item的的所有界面元素
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tvName;
        public TextView tvCount;
        public ViewHolder(View view) {
            super(view);
            tvName = (TextView) view.findViewById(R.id.tv_danmu_name);
            tvCount = (TextView) view.findViewById(R.id.tv_danmu_content);
        }
    }

    public interface OnRecyclerViewItemClickListener {
        void onItemClick(View view, String data);
    }

    public void setOnItemClickListener(OnRecyclerViewItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }

}