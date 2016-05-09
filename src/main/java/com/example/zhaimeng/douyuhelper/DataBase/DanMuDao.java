package com.example.zhaimeng.douyuhelper.DataBase;

import android.content.Context;

import com.example.zhaimeng.douyuhelper.Bean.DanMu;
import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.util.List;

public class DanMuDao
{
    private Context context;
    private Dao<DanMu, Integer> danMuDaoOpe;
    private DatabaseHelper helper;

    public DanMuDao(Context context) {
        this.context = context;
        try {
            helper = DatabaseHelper.getHelper(context);
            danMuDaoOpe = helper.getDao(DanMu.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 增加一个弹幕
     * @param danMu
     */
    public void add(DanMu danMu) {
        try {
            danMuDaoOpe.create(danMu);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
//    /**
//     * 删除一个弹幕
//     * @param danMu
//     */
//    public void delete(DanMu danMu) {
//        try {
//            danMuDaoOpe.deleteById(danMu.getId());
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }
//    /**
//     * 更新一个用户
//     * @param user
//     */
//    public void update(User user) {
//        try {
//            danMuDaoOpe.update(user);
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }

    /**
     * 查询所有
     * @return list
     */
    public List<DanMu> queryAll() {
        try {
            return danMuDaoOpe.queryForAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

//    /**
//     * 查询所有发言数量大于2的nickname
//     * @return list
//     */
//    public List<DanMu> orderBy() {
//        try {
//
//            //danMuDaoOpe.executeRawNoArgs("select nickname from (select nickname, content, count(*) from (select nickname, content from danmu order by nickname) group by nickname having count(*) > 2) order by (count(*))");
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return null;
//    }
}