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
public class ShowTop10DMAdapter extends RecyclerView.Adapter<ShowTop10DMAdapter.ViewHolder> implements View.OnClickListener {

    private Context context;
    private LinkedHashMap<String, ArrayList<DanMu>> linkedHashMap;

    private OnRecyclerViewItemClickListener mOnItemClickListener = null;

    public ShowTop10DMAdapter(Context context, LinkedHashMap<String, ArrayList<DanMu>> linkedHashMap){
        this.context = context;
        this.linkedHashMap = linkedHashMap;
    }
    //创建新View，被LayoutManager所调用
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_top10_danmu, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(view);
        view.setOnClickListener(this);
        return viewHolder;
    }

    //将数据与界面进行绑定的操作
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        viewHolder.tvT10Name.setText((String) getKeyByIndexInLHM(linkedHashMap, position));
        viewHolder.tvT10Count.setText(String.valueOf(linkedHashMap.get((String) getKeyByIndexInLHM(linkedHashMap, position)).size()));
        //get每个item设置一个Tag，便于通过Tag查找,这里设置NickName作为Tag
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
            mOnItemClickListener.onItemClick(v, linkedHashMap);
        }
    }

    //自定义的ViewHolder，持有每个Item的的所有界面元素
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tvT10Name;
        public TextView tvT10Count;
        public ViewHolder(View view) {
            super(view);
            tvT10Name = (TextView) view.findViewById(R.id.tv_t10DM_name);
            tvT10Count = (TextView) view.findViewById(R.id.tv_t10DM_content);
        }
    }
    //通过view结合tag来找到点击的哪个item
    public interface OnRecyclerViewItemClickListener {
        void onItemClick(View view, LinkedHashMap<String, ArrayList<DanMu>> linkedHashMap);
    }

    public void setOnItemClickListener(OnRecyclerViewItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }

}