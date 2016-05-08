package com.example.zhaimeng.douyuhelper.Fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.zhaimeng.douyuhelper.Adapter.MyAdapter;
import com.example.zhaimeng.douyuhelper.Main.CrawlerThread;
import com.example.zhaimeng.douyuhelper.Main.MainActivity;
import com.example.zhaimeng.douyuhelper.R;
import com.example.zhaimeng.douyuhelper.bean.DanMu;

import java.util.ArrayList;

/**
 * Created by zhaimeng on 2016/5/7.
 */
public class ShowDanMuFragment extends Fragment {
    private MainActivity mActivity;
    private ListView listView;
    public Handler mainHandler;
    public ArrayList<DanMu> danMuList = new ArrayList();
    private MyAdapter myAdapter;
    private View view;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity = (MainActivity)getActivity();
    }

    @Nullable
    @Override
    //创建fragment对应的view
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = View.inflate(mActivity, R.layout.show_danmu_fragment, null);
        initView();
        initData();
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        new Thread(new CrawlerThread("http://www.douyu.com/xiaocang", mainHandler, mActivity.dataBaseHandler), "Crawler-1").start();//开启接收弹幕线程
    }


    private void initView() {
        listView = (ListView) view.findViewById(R.id.lv_list);
        myAdapter = new MyAdapter(mActivity, danMuList);
        listView.setAdapter(myAdapter);
    }

    private void initData() {
        //handler 不断的接收爬弹幕线程发送过来的弹幕对象
        mainHandler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                if(msg.arg1 == 11){
                    DanMu danMu = msg.getData().getParcelable("DanMu");
                    danMuList.add(danMu);
                    //ItemView itemView = new ItemView(danMu.getNickname(), danMu.getContent());
                    if(danMu != null) {
                        //listView.addFooterView(danmuView);
                        myAdapter.notifyDataSetChanged();
                    }
                    listView.setSelection(listView.getCount() - 1);
                }
            }
        };
    }

}
