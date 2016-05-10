package com.example.zhaimeng.douyuhelper.ReceiveListener;

import android.support.v4.view.animation.FastOutLinearInInterpolator;

import com.example.zhaimeng.douyuhelper.Main.CrawlerThread;

import com.example.zhaimeng.douyuhelper.Handler.MessageHandler;

import java.util.List;

public class RecLisForLogRes implements MessageHandler.OnReceiveListener{

	int flag = 0;
	CrawlerThread crawlerThread;
	public RecLisForLogRes(CrawlerThread crawlerThread) {
		this.crawlerThread = crawlerThread;
	}



	@Override
	public void onReceive(List<String> responses) {
		for (String eachResponses : responses) {
			System.out.println(eachResponses);
			if(eachResponses.indexOf("loginres") != -1) flag = 1;
		}
	}
	@Override
	public boolean isFinished() {
		if(flag == 1) return true;
		else{
			flag = 0;
			return false;
		}
	}
	
}