package com.example.zhaimeng.douyuhelper.ReceiveListener;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Parcelable;

import com.example.zhaimeng.douyuhelper.Main.CrawlerThread;
import com.example.zhaimeng.douyuhelper.Main.MainActivity;
import com.example.zhaimeng.douyuhelper.bean.DanMu;
import com.example.zhaimeng.douyuhelper.handler.MessageHandler;

import java.util.List;

public class RecLisForDM implements MessageHandler.OnReceiveListener{
	CrawlerThread crawlerThread;
	Handler handler;

	int flag = 0;
	public RecLisForDM(CrawlerThread crawlerThread, Handler handler) {
		this.crawlerThread = crawlerThread;
		this.handler = handler;
	}

	@Override
	public void onReceive(List<String> responses) {

		for (String eachResponses : responses) {
			eachResponses = MainActivity.deFilterStr(eachResponses);
			if(eachResponses.indexOf("chatmsg") != -1){ //判断消息类型
				DanMu danMu = new DanMu();
				for (String a : eachResponses.split("/")) {
					if(a.indexOf("nn@=") != -1){
						danMu.setNickname(a.substring(4));
					} else if(a.indexOf("txt@=") != -1){
						danMu.setContent(a.substring(5));
					} else if (a.indexOf("rid@=") != -1){
						danMu.setRoomID(a.substring(5));
					} else if (a.indexOf("uid@=") != -1){
						danMu.setDanmuGroupID(a.substring(5));
					} else if (a.indexOf("level@=") != -1){
						danMu.setLevel(Integer.parseInt(a.substring(7)));
					}
				}
				System.out.println("【线程名】: " + Thread.currentThread().getName() + "【用户】：" + danMu.getNickname() + "【弹幕】：" + danMu.getContent());

				Bundle bundle = new Bundle();
				bundle.putParcelable("DanMu", danMu);

				Message message = Message.obtain();
				message.setData(bundle);
				message.arg1 = 11;
				handler.sendMessage(message);
				//danMu.clear();
			}
		}

	}

	@Override
	public boolean isFinished() {
		return false;//返回false，一直循环接收弹幕tcp包
	}
	
}
