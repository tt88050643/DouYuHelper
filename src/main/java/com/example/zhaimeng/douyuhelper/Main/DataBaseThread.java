package com.example.zhaimeng.douyuhelper.Main;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

import com.example.zhaimeng.douyuhelper.DataBase.DanMuDao;
import com.example.zhaimeng.douyuhelper.Bean.DanMu;


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
                        danMuDao.add(danMu);//入库
                    }
                    Log.i("TAG", "" + danMuDao.queryAll().size());
                }

            }
        };
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
