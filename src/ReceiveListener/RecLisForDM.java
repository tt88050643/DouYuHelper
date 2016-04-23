package ReceiveListener;

import handler.MessageHandler;

import java.util.List;

import Main.CrawlerThread;

public class RecLisForDM implements MessageHandler.OnReceiveListener{
	CrawlerThread crawlerThread;
	int flag = 0;
	public RecLisForDM(CrawlerThread crawlerThread) {
		this.crawlerThread = crawlerThread;
	}

	@Override
	public void onReceive(List<String> responses) {
		for (String eachResponses : responses) {
			if(eachResponses.indexOf("chatmsg") != -1){
				System.out.println("【线程名】: " + Thread.currentThread().getName() + "【弹幕】：" + eachResponses);
			}
		}
	}

	@Override
	public boolean isFinished() {
		return false;//返回false，一直循环接收弹幕tcp包
	}
	
}

