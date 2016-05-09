package com.example.zhaimeng.douyuhelper.Fragment;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.zhaimeng.douyuhelper.Adapter.ShowDanMuAdapter;
import com.example.zhaimeng.douyuhelper.Adapter.ShowTop10DMAdapter;
import com.example.zhaimeng.douyuhelper.Bean.DanMu;
import com.example.zhaimeng.douyuhelper.Main.MainActivity;
import com.example.zhaimeng.douyuhelper.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

/**
 * Created by zhaimeng on 2016/5/7.
 */
public class TongJiFragment extends Fragment {

    private MainActivity mActivity;
    private View view;
    private ListView listView;
    private ShowTop10DMAdapter showDanMuAdapter;
    private ArrayList<DanMu> top10DanMuList;
    private LinkedHashMap<String, ArrayList<DanMu>> top10Item;
    private ArrayList<String> top10NickName;
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
        top10Item = findTop10Data4Adapter();//查找出前发言数量前10的弹幕信息，为Adapter准备数据
        showDanMuAdapter = new ShowTop10DMAdapter(mActivity, top10Item);
        listView.setAdapter(showDanMuAdapter);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onResume() {
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
        int count = 0;
        String sql = "select * from danmu where nickname = '" + NickName + "'";
        Cursor cursor = db.rawQuery(sql, null);
        while(count<=9 && cursor.moveToNext()){
            DanMu eachDanMu = new DanMu(cursor.getString(cursor.getColumnIndex("nickname")),
                    cursor.getString(cursor.getColumnIndex("content")),
                    cursor.getInt(cursor.getColumnIndex("level")),
                    cursor.getString(cursor.getColumnIndex("roomID")),
                    cursor.getString(cursor.getColumnIndex("danmuGroupID")));
            danMuListByNN.add(eachDanMu);
            count++;
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
        while(cursor.moveToNext() && count<=9){
            Log.i("TAG", cursor.getString(cursor.getColumnIndex("nickname")));
            arrayList.add(cursor.getString(cursor.getColumnIndex("nickname")));
            count++;
        }
        return arrayList;
    }
}
