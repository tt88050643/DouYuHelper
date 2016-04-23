package ReceiveListener;

import handler.MessageHandler;

import java.util.List;

import Main.CrawlerThread;
import Main.Main;
import bean.DanMuServerInfo;

public class RecLisForDMServer implements MessageHandler.OnReceiveListener{
	private CrawlerThread crawlerThread;
	public RecLisForDMServer(CrawlerThread crawlerThread) {
		this.crawlerThread = crawlerThread;
	}

	@Override
	public void onReceive(List<String> responses) {
		//获取弹幕服务器信息
		for (String eachString : responses) {
			eachString = Main.deFilterStr(eachString);
			if(eachString.indexOf("msgrepeaterlist") != -1){ //找到类型为msgrepeaterlist的字串
				DanMuServerInfo danMuServerInfo = new DanMuServerInfo();
				for (String a : eachString.split("/")) {
					if(a.indexOf("ip@A=") != -1){
						danMuServerInfo.setIp(a.substring(5));
					} else if(a.indexOf("port@A=") != -1){
						danMuServerInfo.setPort(a.substring(7));
					}
					if(danMuServerInfo.getIp() != null && danMuServerInfo.getPort() != null){
						crawlerThread.danMuServerInfos.add(danMuServerInfo);
						danMuServerInfo = new DanMuServerInfo();
					}
				}
			}
			if(eachString.indexOf("setmsggroup") != -1){ //找到类型为setmsggroup的字串
				for (String a : eachString.split("/")) {
					if(a.indexOf("rid@=") != -1){
						crawlerThread.rid = a.substring(5);
					} else if(a.indexOf("gid@=") != -1){
						crawlerThread.gid = a.substring(5);
					}
					
				}
			}
		}
		//System.out.println(responses);
	}
	
	@Override
	public boolean isFinished() {
		if(crawlerThread.rid != null && crawlerThread.gid != null){
			return true;
		}else return false;
	}
	
}
