package com.example.zhaimeng.douyuhelper;

import android.app.Application;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.test.ApplicationTestCase;
import android.util.Log;

import com.example.zhaimeng.douyuhelper.DataBase.DanMuDao;
import com.example.zhaimeng.douyuhelper.Bean.DanMu;

import java.util.ArrayList;
import java.util.List;

/**
 * <a href="http://d.android.com/tools/testing/testing_android.html">Testing Fundamentals</a>
 */
public class ApplicationTest extends ApplicationTestCase<Application> {
    public ApplicationTest() {
        super(Application.class);
    }

    public void testAddUser() {
        DanMuDao danMuDao = new DanMuDao(getContext());
        DanMu danMu = new DanMu("zhaimeng", "666666", 50, "123", "234");
        danMuDao.add(danMu);
    }

    public void queryAll() {
        DanMuDao danMuDao = new DanMuDao(getContext());
        List<DanMu> danMus = danMuDao.queryAll();
        for (DanMu danMu : danMus) {
            System.out.println(danMu.getNickname() + "   " + danMu.getContent());
            Log.i("TAG", "" + danMu.getNickname() + "   " + danMu.getContent());
        }
    }

    public void orderByLevel() {

        SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase(getContext().getFilesDir().toString() + "/danmu.db", null);
        Cursor cursor = db.rawQuery("select nickname from (select nickname, content, count(*) from (select nickname, content from danmu order by nickname) group by nickname having count(*) > 2) order by (count(*))", null);
        while(cursor.moveToNext()){
            Log.i("TAG", cursor.getString(cursor.getColumnIndex("nickname")));
        }
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

        for (String s : arrayList) {
            Log.i("TAG", s);
        }
        return arrayList;

    }
}