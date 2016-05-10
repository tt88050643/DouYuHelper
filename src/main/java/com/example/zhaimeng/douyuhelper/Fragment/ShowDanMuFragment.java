package com.example.zhaimeng.douyuhelper.Fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.zhaimeng.douyuhelper.Adapter.ShowDanMuAdapter;
import com.example.zhaimeng.douyuhelper.Main.CrawlerThread;
import com.example.zhaimeng.douyuhelper.Main.MainActivity;
import com.example.zhaimeng.douyuhelper.R;
import com.example.zhaimeng.douyuhelper.Bean.DanMu;

import java.util.ArrayList;

/**
 * Created by zhaimeng on 2016/5/7.
 */
public class ShowDanMuFragment extends Fragment {
    private MainActivity mActivity;
    private ListView listView;
    public Handler mainHandler;
    public ArrayList<DanMu> danMuList = new ArrayList();
    private ShowDanMuAdapter showDanMuAdapter;
    private View view;
    private boolean flagStop;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.i("Fragment", "SonCreate");
        super.onCreate(savedInstanceState);
        mActivity = (MainActivity)getActivity();
    }

    @Nullable
    @Override
    //创建fragment对应的view
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.i("Fragment", "SonCreateView");
        view = View.inflate(mActivity, R.layout.fragment_show_danmu, null);
        initView();
        initData();
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        Log.i("Fragment", "SonActivityCreated");
        super.onActivityCreated(savedInstanceState);
        new Thread(new CrawlerThread("http://www.douyu.com/weixiao", mainHandler, mActivity.dataBaseHandler), "Crawler-1").start();//开启接收弹幕线程
    }

    private void initView() {
        listView = (ListView) view.findViewById(R.id.lv_showDanMu);
        showDanMuAdapter = new ShowDanMuAdapter(mActivity, danMuList);
        listView.setAdapter(showDanMuAdapter);
    }

    private void initData() {
        //handler 不断的接收爬弹幕线程发送过来的弹幕对象
        mainHandler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                if(msg.arg1 == 11 && !flagStop){
                    DanMu danMu = msg.getData().getParcelable("DanMu");
                    danMuList.add(danMu);
                    if(danMu != null) {
                        showDanMuAdapter.notifyDataSetChanged();
                    }
                    listView.setSelection(listView.getCount() - 1);
                }
            }
        };
    }

    public void stopShowDM(boolean stop){
        flagStop = stop;
    }
}
