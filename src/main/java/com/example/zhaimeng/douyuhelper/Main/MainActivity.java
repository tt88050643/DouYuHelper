package com.example.zhaimeng.douyuhelper.Main;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.zhaimeng.douyuhelper.R;
import com.example.zhaimeng.douyuhelper.bean.DanMu;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {
    private MyAdapter myAdapter;
    private ListView listView;
    public static ArrayList<DanMu> arrList = new ArrayList();

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if(msg.arg1 == 11){
                DanMu danMu = msg.getData().getParcelable("DanMu");
//                View danmuView = null;
//                danmuView = LayoutInflater.from(MainActivity.this).inflate(R.layout.item_layout_type1, null);
//                TextView tvName = (TextView) danmuView.findViewById(R.id.tv_type1_name);
//                TextView tvContent = (TextView) danmuView.findViewById(R.id.tv_type1_content);
//                tvName.setText(danMu.getNickname());
//                tvContent.setText(danMu.getContent());
                arrList.add(danMu);
                //ItemView itemView = new ItemView(danMu.getNickname(), danMu.getContent());
                if(danMu != null) {
                    //listView.addFooterView(danmuView);
                    myAdapter.notifyDataSetChanged();
                }

                listView.setSelection(listView.getCount() - 1);
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        myAdapter = new MyAdapter(this, arrList);
        listView.setAdapter(myAdapter);
        new Thread(new CrawlerThread("http://www.douyu.com/491416", handler), "Crawler-1").start();
        //new Thread(new CrawlerThread("http://www.douyu.com/huanhuan520", handler), "Crawler-2").start();
    }

    private void initView() {
        listView = (ListView) findViewById(R.id.lv_list);
    }

    public static String deFilterStr(String str) {
        if (str == null) return null;
        return str.trim().replace("@A", "@").replace("@S", "/");
    }
}

class MyAdapter extends BaseAdapter{
    private Context context;
    private List list;

    public MyAdapter(Context context, List list){
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
        ViewHolder holder = null;
        Log.i("position", "" + position);
        if(convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.item_layout_type1, null);
            holder = new ViewHolder();
            holder.tvName = (TextView) convertView.findViewById(R.id.tv_type1_name);
            holder.tvContent = (TextView) convertView.findViewById(R.id.tv_type1_content);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.tvName.setText(MainActivity.arrList.get(position).getNickname());
        holder.tvContent.setText((CharSequence) MainActivity.arrList.get(position).getContent());
        return convertView;
    }
}
