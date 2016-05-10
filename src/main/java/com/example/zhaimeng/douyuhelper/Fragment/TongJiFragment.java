package com.example.zhaimeng.douyuhelper.Fragment;

import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.example.zhaimeng.douyuhelper.Adapter.DividerItemDecoration;
import com.example.zhaimeng.douyuhelper.Adapter.ShowTop10DMAdapter;
import com.example.zhaimeng.douyuhelper.Bean.DanMu;
import com.example.zhaimeng.douyuhelper.Main.MainActivity;
import com.example.zhaimeng.douyuhelper.R;

import java.util.ArrayList;
import java.util.LinkedHashMap;

/**
 * Created by zhaimeng on 2016/5/7.
 */
public class TongJiFragment extends Fragment {
    private int topNumber = 10;//发言次数前topN名
    private MainActivity mActivity;
    private View view;
    private ShowTop10DMAdapter showTop10DMAdapter;
    private LinkedHashMap<String, ArrayList<DanMu>> top10Item;
    private ArrayList<String> top10NickName;
    private RecyclerView recyclerView;
    private LinearLayoutManager mLayoutManager;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity = (MainActivity)getActivity();
        Log.i("Fragment", "onCreate");
    }

    @Nullable
    @Override
    //创建fragment对应的view
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.i("Fragment", "onCreateView");
        view = View.inflate(mActivity, R.layout.fragment_show_tongji, null);
        initView4RecyView();
        return view;
    }

    private void initView4RecyView() {
        //查找出前发言数量前10的弹幕信息，为Adapter准备数据
        top10Item = findTop10Data4Adapter();
        recyclerView = (RecyclerView) view.findViewById(R.id.tongji_recyview);
        //创建默认的线性LayoutManager
        mLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(mLayoutManager);
        //如果可以确定每个item的高度是固定的，设置这个选项可以提高性能
        recyclerView.setHasFixedSize(true);
        showTop10DMAdapter = new ShowTop10DMAdapter(mActivity, top10Item);

        showTop10DMAdapter.setOnItemClickListener(new ShowTop10DMAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, LinkedHashMap<String, ArrayList<DanMu>> linkedHashMap) {
                Toast.makeText(getContext(), (String)view.getTag(), Toast.LENGTH_SHORT).show();
                String nickName = (String)view.getTag();
                showDialog(nickName, linkedHashMap);
            }
        });

        recyclerView.setAdapter(showTop10DMAdapter);
        //设置Item增加、移除动画
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL_LIST));
    }

    private void showDialog(String nickName, LinkedHashMap<String, ArrayList<DanMu>> linkedHashMap) {
        ArrayList<DanMu> arrlistDanMu = linkedHashMap.get(nickName);
        StringBuilder sb = new StringBuilder();
        sb.append("用户名：" + arrlistDanMu.get(0).getNickname() + "    " +"lv." + arrlistDanMu.get(0).getLevel() + "\n");
        int index = 1;
        for (DanMu danMu : arrlistDanMu) {
            sb.append(index++ + ":" + danMu.getContent() + "\n");
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setMessage(sb);
        builder.create().show();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        Log.i("Fragment", "onActivityCreated");
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onResume() {
        Log.i("Fragment", "onResume");
        super.onResume();
    }

    private LinkedHashMap<String, ArrayList<DanMu>> findTop10Data4Adapter() {
        LinkedHashMap<String, ArrayList<DanMu>> top10Item = new LinkedHashMap<>();
        top10NickName = findTop10NickName();//ArrayList<String> top10NickName
        for (String eachNN : top10NickName) {
            ArrayList<DanMu> al = findDanMuByNN(eachNN);
            top10Item.put(eachNN, al);
        }
        return top10Item;
    }

    /**
     * 根据NickName查找出其对应的发言弹幕
     * @param NickName
     * @return NickName的弹幕列表
     */
    private ArrayList<DanMu> findDanMuByNN(String NickName) {
        ArrayList<DanMu> danMuListByNN = new ArrayList<>();
        String dbPath = getContext().getDatabasePath("danmu.db").toString();
        SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase(dbPath, null);
        String sql = "select * from danmu where nickname = '" + NickName + "'";
        Cursor cursor = db.rawQuery(sql, null);
        while(cursor.moveToNext()){
            DanMu eachDanMu = new DanMu(cursor.getString(cursor.getColumnIndex("nickname")),
                    cursor.getString(cursor.getColumnIndex("content")),
                    cursor.getInt(cursor.getColumnIndex("level")),
                    cursor.getString(cursor.getColumnIndex("roomID")),
                    cursor.getString(cursor.getColumnIndex("danmuGroupID")));
            danMuListByNN.add(eachDanMu);
        }
        return danMuListByNN;
    }

    /**
     * 从数据库中查出发言前10的用户的NickName
     * @return
     */
    public ArrayList<String> findTop10NickName() {
        ArrayList<String> arrayList = new ArrayList<>();
        String dbPath = getContext().getDatabasePath("danmu.db").toString();
        SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase(dbPath, null);
        //查询：nickname，以发言次数排名（降序）
        Cursor cursor = db.rawQuery("select nickname from (select nickname, content, count(*) from (select nickname, content from danmu order by nickname) group by nickname having count(*) > 2) order by (count(*)) desc", null);
        int count = 0;
        //拿到发言数量前十的NickName
        while(cursor.moveToNext() && count<topNumber){
            Log.i("TAG", cursor.getString(cursor.getColumnIndex("nickname")));
            arrayList.add(cursor.getString(cursor.getColumnIndex("nickname")));
            count++;
        }
        return arrayList;
    }
}
