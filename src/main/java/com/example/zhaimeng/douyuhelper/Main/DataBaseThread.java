package com.example.zhaimeng.douyuhelper.Main;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

import com.example.zhaimeng.douyuhelper.DataBase.DanMuDao;
import com.example.zhaimeng.douyuhelper.bean.DanMu;


/**
 * Created by zhaimeng on 2016/5/5.
 */
public class DataBaseThread implements Runnable {
    Context context;
    DanMuDao danMuDao;
    Handler handler;

    public DataBaseThread(Context context){
        this.context = context;
        this.danMuDao = new DanMuDao(context);
        this.handler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                if(msg.arg1 == 11){
                    DanMu danMu = msg.getData().getParcelable("DanMu");
                    if(danMu != null) {
                        danMuDao.add(danMu);
                    }
                    Log.i("TAG", "" + danMuDao.queryAll().size());
                    //orderByLevel();
                }

            }
        };
    }
    public void orderByLevel() {
        String dbPath = context.getDatabasePath("danmu.db").toString();
        SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase(dbPath, null);
        //查询：nickname，以发言次数排名（降序）
        Cursor cursor = db.rawQuery("select nickname from (select nickname, content, count(*) from (select nickname, content from danmu order by nickname) group by nickname having count(*) > 2) order by (count(*)) desc", null);
        while(cursor.moveToNext()){
            Log.i("TAG", cursor.getString(cursor.getColumnIndex("nickname")));
        }
    }

    public Handler getHandler(){
        return this.handler;
    }

    @Override
    public void run() {
        Looper.prepare();
        Looper.loop();
    }
}
