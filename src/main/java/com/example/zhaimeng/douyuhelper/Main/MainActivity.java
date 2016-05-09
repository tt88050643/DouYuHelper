package com.example.zhaimeng.douyuhelper.Main;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.PagerTabStrip;
import android.support.v4.view.ViewPager;

import com.example.zhaimeng.douyuhelper.Adapter.MainViewPagerAdapter;
import com.example.zhaimeng.douyuhelper.Fragment.ShowDanMuFragment;
import com.example.zhaimeng.douyuhelper.Fragment.TongJiFragment;
import com.example.zhaimeng.douyuhelper.R;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends FragmentActivity implements ViewPager.OnPageChangeListener {
    private static final String SHOWDANMUFRAGMENT = "blank_fragment_container";

    private List<Fragment> fragList;//fragment list
    private List<String> titleList;//标题list
    private PagerTabStrip tab;//顶部标题部件
    private ViewPager viewPager;
    private MainViewPagerAdapter adapter;
    private ShowDanMuFragment showDanMuFragment;
    public Handler dataBaseHandler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
       // initFragment();
        setViewPager();

        DataBaseThread dataBaseThread = new DataBaseThread(MainActivity.this);
        dataBaseHandler = dataBaseThread.getHandler();
        //ShowDanMuFragment showDanMuFragment = (ShowDanMuFragment)getSupportFragmentManager().findFragmentByTag(SHOWDANMUFRAGMENT);
        new Thread(dataBaseThread).start();//弹幕存入数据库线程

       // new Thread(new CrawlerThread("http://www.douyu.com/pis", showDanMuFragment.mainHandler, dataBaseHandler), "Crawler-1").start();//开启接收弹幕线程
    }

//    /**
//     * 初始化fragment, 将fragment数据填充给布局文件
//     */
//    private void initFragment() {
//        FragmentManager fm = getSupportFragmentManager();
//        FragmentTransaction transaction = fm.beginTransaction();// 开启事务
//        showDanMuFragment = new ShowDanMuFragment();
//        transaction.replace(R.id.blank_fragment_container, showDanMuFragment, SHOWDANMUFRAGMENT);
//        transaction.commit();// 提交事务
//
//    }

    private void setViewPager() {
        titleList = new ArrayList<String>();
        tab = (PagerTabStrip) findViewById(R.id.tab);
        ShowDanMuFragment showDanMuFragment = new ShowDanMuFragment();
        TongJiFragment tongJiFragment = new TongJiFragment();
        //填充Fragment列表
        fragList = new ArrayList<Fragment>();
        fragList.add(showDanMuFragment);
        fragList.add(tongJiFragment);
        //为ViewPager页卡设置标题
        titleList.add("第一页");
        titleList.add("第二页");
        titleList.add("第三页");
        titleList.add("第四页");
        //为PagerTabStrip设置一些属性
        tab.setBackgroundColor(Color.WHITE);
        tab.setDrawFullUnderline(false);
        tab.setTabIndicatorColor(Color.GREEN);
        //设置viewpager
        viewPager = (ViewPager) findViewById(R.id.pager);
        adapter = new MainViewPagerAdapter(getSupportFragmentManager(), fragList, titleList);
        viewPager.setAdapter(adapter);
        viewPager.setOnPageChangeListener(this);
    }

    public static String deFilterStr(String str) {
        if (str == null) return null;
        return str.trim().replace("@A", "@").replace("@S", "/");
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}

