package com.example.zhaimeng.douyuhelper.ReceiveListener;

import com.example.zhaimeng.douyuhelper.Main.CrawlerThread;

import com.example.zhaimeng.douyuhelper.handler.MessageHandler;

import java.util.List;

import com.example.zhaimeng.douyuhelper.Main.CrawlerThread;

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
		return flag == 1 ? true : false;
	}
	
}