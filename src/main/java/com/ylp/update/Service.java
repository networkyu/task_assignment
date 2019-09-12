package com.ylp.update;
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * 提供截取方法startRun();
 */
public class Service {
    // 首次访问页面url
    private String url;
    // 访问页面的父id（行政区划代码上级）
    private String parentId;
    // 用来保存数据的对象。
    private SaveData saveData = new SaveData();
    // 用来从url当中读取对方服务器返回的html字符串。
    private ReadResponseData readResponseData = new ReadResponseData();
    // 保存数据的表名。
    private String tableName;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getParentId() {
        return parentId;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    // 已经访问过的url
    public Set<String> visitedUrlSet = new HashSet<String>();
    // 待访问队列
    // <待访问的链接，父节点>
    public Map<String,String> visitQueue= new HashMap<String, String>();

    // 构造方法。设置首次访问的页面和页面的父区划代码。
    public Service(String url,String parentId,String tableName) {
        this.url = url;
        this.parentId = parentId;
        this.tableName = tableName;
    }
    // 开始抓取方法。
    public void startRun() throws SQLException, IOException {
        // 循环访问每一个待访问的url，相当于每一个页面
        while (visitQueue.size()>0){
            //：TODO 随机取出一个链接进行访问。
            Set<String> keys = this.visitQueue.keySet();
            // 当前访问的url
            String currentUrl = keys.iterator().next();
            // 获取父节点行政区划代码
            String parentId=this.visitQueue.get(currentUrl);
            System.out.println("当前访问的url:"+currentUrl);
            // 通过模拟浏览器访问url，并用content接收对方服务器返回的html字符串（String）
            String content = readResponseData.getResponseDataByWithHttpClient(currentUrl);
            // 正则表达式，包含数据格式的String，提取具有使用价值的相同格式的数据,格式上具有同一行的性质。
            String regularStr = "<tr(.){0,30}><td>(.*?)</td><td>(.*?)</td></tr>";
            // 获取到页面的所有有价值的String
            Set<String> stringSet = CustomRegular.interceptStringsByString(content,regularStr);
            //用来保存每一个页面的区域模型集合。
            Set<RegionModel> regionModels = new HashSet<RegionModel>();
            // 匹配一段数字和汉字和html，获取每个页面当中的每一条游泳的数据。
            for(String str:stringSet){
                /*
                :  TODO 提取数据并保存。
                */
                //正则表达式匹配12为行政区划代码
                String regularNumberStr = "\\d{12}";
                Set<String> numberStrs = CustomRegular.interceptStringsByString(str,regularNumberStr);
                //正则表达式匹配区划名称（全是中文）
                String regularChineseStr="[\\u4E00-\\u9FA5]+";
                Set<String> chineseSets = CustomRegular.interceptStringsByString(str,regularChineseStr);
                if (!numberStrs.isEmpty()||!chineseSets.isEmpty()){
                    /**
                     * 测试错误拦截，最后删除。
                     */
                    if (chineseSets.iterator().next().equals("名称")){
                        System.out.println("截取到的字符串集合为:"+stringSet);
                        System.out.println("当前的字符串为:"+str);
                    }
                    // 当前页面的每一行数据添加到区域模型当中。
                    RegionModel regionModel = new RegionModel(numberStrs.iterator().next(),chineseSets.iterator().next(),parentId);
                    regionModels.add(regionModel);
                }else {
                    System.out.println("提取每一条数据时有一处错误:"+str);
                }
                //: TODO 提取页面链接将其添加到未访问的队列中。
                //正则表达式匹配数据项中包含链接的字符串。
                String regularUrlStr = "[0-9]+/[0-9]+.html";
                Set<String> urlSets = CustomRegular.interceptStringsByString(str,regularUrlStr);
                //是否有链接
                if (!urlSets.isEmpty()){
                    // 把当前访问的url的最后的路径截掉，在把新查找到的链接加入到url并保存到下次访问的路径。
                    Set<String > endStrSets = CustomRegular.interceptStringsByString(currentUrl,"[0-9]+.html");
                    if (!endStrSets.isEmpty()){
                        String visitUrl = currentUrl.substring(0,currentUrl.length()-endStrSets.iterator().next().length())+urlSets.iterator().next();
//                        System.out.println("添加的Url是:"+visitUrl);
                        // 将新发现的链接添加到待访问链接（页面）队列
                        this.addVisitUrlAndParentId(visitUrl,numberStrs.iterator().next());
                    } else {
                        System.out.println("当截取字符串，将新Url加入到待访问队列当中出错");
                    }

                }

            }
            // 将每一个页面的数据插入数据库。
            saveData.insertDataByModelToTable(regionModels,tableName);
            // 将已访问的url从待访问列表中移除。
            this.removeVisitUrl(currentUrl);


        }
        return ;
    }

    /**
     * 添加一条新链接到待访问队列，如果该链接已访问则直接跳过，不在访问。
     * @param url 待访问url
     * @param parentId 访问url的父ID
     */
    public void addVisitUrlAndParentId(String url,String parentId){
        // 如果包含则不在添加到访问队列。
        if (visitedUrlSet.contains(url)){
            return;
        }
        this.visitQueue.put(url,parentId);
    }

    /**
     * 如果一个链接已经访问完成，那么将其从待访问队列中移除。
     * @param url 已访问的url
     */
    public void removeVisitUrl(String url){
        this.visitedUrlSet.add(url);
        this.visitQueue.remove(url);
    }
    /**
     * 查询总共有多少条数据
     */
    public Integer queryCount(String str){
        return  saveData.queryCount(str);
    }
}
