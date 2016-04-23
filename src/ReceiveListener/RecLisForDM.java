package ReceiveListener;

import handler.MessageHandler;

import java.util.List;

import Main.Main;
import bean.DanMuServerInfo;

public class RecLisForDM implements MessageHandler.OnReceiveListener{

	int flag = 0;
	@Override
	public void onReceive(List<String> responses) {
		for (String eachResponses : responses) {
			if(eachResponses.indexOf("chatmsg") != -1){
				System.out.println("弹幕：" + eachResponses);
			}
		}
	}

	@Override
	public boolean isFinished() {
		return false;//返回false，一直循环接收弹幕tcp包
	}
	
}

