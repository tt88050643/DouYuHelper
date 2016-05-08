package com.example.zhaimeng.douyuhelper.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.zhaimeng.douyuhelper.Adapter.MyAdapter;
import com.example.zhaimeng.douyuhelper.Main.MainActivity;
import com.example.zhaimeng.douyuhelper.R;
import com.example.zhaimeng.douyuhelper.bean.DanMu;

import java.util.ArrayList;

/**
 * Created by zhaimeng on 2016/5/7.
 */
public class TongJiFragment extends Fragment {

    private MainActivity mActivity;
    private View view;
    private ListView listView;
    private MyAdapter myAdapter;
    private ArrayList<DanMu> danMuList;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity = (MainActivity)getActivity();
    }

    @Nullable
    @Override
    //创建fragment对应的view
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = View.inflate(mActivity, R.layout.tongji_fragment, null);
        initView();
        return view;
    }

    private void initView() {
        listView = (ListView) view.findViewById(R.id.tongji_list);
        danMuList = new ArrayList();
        DanMu tmpDanMu = new DanMu("nickname", "content", 10, "roomID", "danmuGroupID");
        danMuList.add(tmpDanMu);
        myAdapter = new MyAdapter(mActivity, danMuList);
        listView.setAdapter(myAdapter);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

}
