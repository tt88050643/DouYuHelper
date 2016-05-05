package com.example.zhaimeng.douyuhelper.utils;

import java.io.IOException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.example.zhaimeng.douyuhelper.bean.DouYuServerInfo;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class MatchFromString {
	
	/**
	 * 通过html获得斗鱼服务器的信息，保存在List<DouYuServerInfo> douYuServerInfos 中
	 * @param html
	 * @param regex
	 * @return
	 * @throws IOException
	 */
	public static List<DouYuServerInfo> findDYServerFromHtml(String html, String regex) throws IOException {
		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(html);
		String serverAddr = null;
		while(m.find()){//正则匹配出斗鱼服务器信息
			serverAddr = m.group(1);
			serverAddr = java.net.URLDecoder.decode(serverAddr, "utf-8");
		}
		
		Gson gson = new Gson();
		List<DouYuServerInfo> douYuServerInfos = gson.fromJson(serverAddr, new TypeToken<List<DouYuServerInfo>>(){}.getType());
//		for (DouYuServerInfo danMuServerInfo : douYuServerInfos) {
//			System.out.println(danMuServerInfo.getIP() + "===" + danMuServerInfo.getPort());
//		}
		return douYuServerInfos;
	}
	
	/**
	 * 通过html获得roomId
	 * @param html
	 * @param regex
	 * @return roomId
	 * @throws IOException
	 */
	public static int findRIdFromHtml(String html, String regex) throws IOException {
		//获取roomId
		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(html);
		while(m.find()){
			String string = m.group(1);
			string = java.net.URLDecoder.decode(string, "utf-8");
			return Integer.parseInt(string);
		}
		return 0;
	}
	
	/**
	 * 通过html获得roomName
	 * @param html
	 * @param regex
	 * @return roomName
	 * @throws IOException
	 */
	public static String findRNameFromHtml(String html, String regex) throws IOException {
		//获取roomId
		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(html);
		while(m.find()){
			String roomName = m.group(1);
			roomName = java.net.URLDecoder.decode(roomName, "utf-8");
			return roomName;
		}
		return null;
	}
}
