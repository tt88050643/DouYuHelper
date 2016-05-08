package com.example.zhaimeng.douyuhelper.Main;


import android.os.Handler;
import android.os.Message;

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import com.example.zhaimeng.douyuhelper.utils.MD5Util;
import com.example.zhaimeng.douyuhelper.utils.MatchFromString;
import com.example.zhaimeng.douyuhelper.ReceiveListener.RecLisForDM;
import com.example.zhaimeng.douyuhelper.ReceiveListener.RecLisForDMServer;
import com.example.zhaimeng.douyuhelper.ReceiveListener.RecLisForLogRes;
import com.example.zhaimeng.douyuhelper.bean.DanMuServerInfo;
import com.example.zhaimeng.douyuhelper.bean.DouYuServerInfo;
import com.example.zhaimeng.douyuhelper.bean.Request;
import com.example.zhaimeng.douyuhelper.handler.MessageHandler;

public class CrawlerThread implements Runnable{
	
	public String threadUrl;
	public int roomId;//房间Id
	public String roomName;//房间名
	public List<DouYuServerInfo> douYuServerInfos = new ArrayList<>();//保存斗鱼服务器的信息
	public List<DanMuServerInfo> danMuServerInfos = new ArrayList<>();//保存斗鱼弹幕服务器的信息
	public String rid;//房间号
	public String gid;//弹幕群组编号
	private Handler mainHandler;
	private Handler databaseHandler;
	public CrawlerThread(String threadUrl, Handler mainHandler, Handler databaseHandler){
		this.threadUrl = threadUrl;
		this.mainHandler = mainHandler;
		this.databaseHandler = databaseHandler;
	}
	@Override
	public void run(){
		try {
			//step1 : 获取html源码
			Document doc = (Document) Jsoup.connect(threadUrl).get();
			//step2 : 提取出斗鱼服务器地址,保存在douYuServerInfos list 中
			roomId = MatchFromString.findRIdFromHtml(doc.html(), "room_id\":(.*?),\"room_name\"");//获取roomId
			roomName = MatchFromString.findRNameFromHtml(doc.html(), "room_name\":\"(.*?)\",\"room_gg\"");//获取roomName
			douYuServerInfos = MatchFromString.findDYServerFromHtml(doc.html(), "\"server_config\":\"(.*?)\",\"def_disp_gg");//获取斗鱼服务器地址

			//step3 ： 选择一个斗鱼服务器，模拟与服务器进行tcp交互,获取弹幕服务器信息
			Socket socket = new Socket(douYuServerInfos.get(0).getIP(),  Integer.parseInt(douYuServerInfos.get(0).getPort()));//Socket连接服务器
			
			//发送登陆请求
			String timestamp = String.valueOf(System.currentTimeMillis() / 1000);//获取当前时间戳
			String uuid = UUID.randomUUID().toString().replace("-", "").toUpperCase();//构造uuid作为devid参数
			String vk = MD5Util.MD5(timestamp + "7oE9nPEG9xXV69phU31FYCLUagKeYtsF" + uuid);//vk参数
		    MessageHandler.send(socket, Request.gid(roomId, uuid, timestamp, vk));
		    
			
		    //等待接收
		    MessageHandler.receive(socket, new RecLisForDMServer(CrawlerThread.this));
			for (DanMuServerInfo danMuServerInfo : danMuServerInfos) {
				System.out.println("【弹幕服务器IP】：" + danMuServerInfo.getIp() + "\n" + "【弹幕服务器Port】：" + danMuServerInfo.getPort());
			}
			
			//step4 ： 选择一个弹幕服务器，发送登陆请求和加入群组的请求
			final Socket socketDanMu = new Socket(danMuServerInfos.get(0).getIp(),  Integer.parseInt(danMuServerInfos.get(0).getPort()));//Socket连接服务器
		    MessageHandler.send(socketDanMu, Request.danmakuLogin(roomId));
		    MessageHandler.receive(socketDanMu, new RecLisForLogRes(CrawlerThread.this));
		    MessageHandler.send(socketDanMu, Request.joinGroup(Integer.parseInt(rid), Integer.parseInt(gid)));
		    //发送心跳包
		    new Thread(new Runnable() {
				
				@Override
				public void run() {
					while(true){
						try {
							MessageHandler.send(socketDanMu, Request.keepLive(0));
						} catch (IOException e) {
							e.printStackTrace();
						}
						try {
							Thread.sleep(30000);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
				}
			}).start();
			
		    //step5 ： 接收弹幕信息
		    MessageHandler.receive(socketDanMu, new RecLisForDM(CrawlerThread.this, mainHandler, databaseHandler));
		    
		} catch (IOException e) {
			
		}		
		
	}
	
}
