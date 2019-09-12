package com.ylp.update;
import java.io.IOException;
import java.sql.SQLException;

/**
 * 配置，启动类，
 */
public class Main {

    public static void main(String[] args) throws SQLException, IOException {
        // 首次抓取页面
        String url = "http://alm.ab.com/DevSuite/#home";
        // 抓取页面的父行政区划代码。
        String parentId = "130535203000";
        // 抓取页面将其保存到community的那个表下面。
        String tableName = "hebei";
        // 开始访问抓取
        Service service = new Service(url,parentId,tableName);
        service.addVisitUrlAndParentId(service.getUrl(),service.getParentId());
        service.startRun();
        // 最后打印出总共访问的页面数量。
        System.out.println("总共访问页面数："+service.visitedUrlSet.size());
        System.out.println(service.queryCount(tableName));
    }
}
