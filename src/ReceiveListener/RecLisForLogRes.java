package ReceiveListener;

import handler.MessageHandler;

import java.util.List;

public class RecLisForLogRes implements MessageHandler.OnReceiveListener{

	int flag = 0;
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