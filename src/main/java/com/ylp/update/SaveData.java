package com.ylp.update;
import java.sql.*;
import java.util.Set;

/**
 * 将数据保存到数据库。
 */
public class SaveData{
    public Connection con;
    // 构造方法，数据库只连接一次。
    public SaveData(){
        this.con = null;
        //驱动程序名
        String driver = "com.mysql.cj.jdbc.Driver";
        //URL指向要访问的数据库名mydata
        String url = "jdbc:mysql://127.0.0.1:3306/community";
        //MySQL配置时的用户名
        String user = "root";
        //MySQL配置时的密码
        String password = "sqlrootpassword";
        //遍历查询结果集
        try {
            //加载驱动程序
            Class.forName(driver);
            //1.getConnection()方法，连接MySQL数据库！！
            con = DriverManager.getConnection(url,user,password);
            if(!con.isClosed())
                System.out.println("Succeeded connecting to the Database!");
        } catch(ClassNotFoundException e) {
            //数据库驱动类异常处理
            System.out.println("Sorry,can`t find the Driver!");
            e.printStackTrace();
        } catch(SQLException e) {
            //数据库连接失败异常处理
            e.printStackTrace();
        }catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }finally{
//            System.out.println("数据库数据成功获取！！");
        }
    }

    /**
     * 将区域模型集合插入到河北省的table中。
     * @param regionModels
     * @return
     */
    public boolean insertDataByModel(Set<RegionModel> regionModels){
        try {
            con.setAutoCommit(false);
            PreparedStatement pst = con.prepareStatement("INSERT INTO `community`.`hebei` (`ID`, `STREET_NAME`, `PARENT_ID`) VALUES (?,?,?)");
            for (RegionModel model:regionModels){
                pst.setString(1,model.getId());
                pst.setString(2,model.getStreet_name());
                pst.setString(3,model.getParent_id());
                pst.addBatch();
            }
            pst.executeBatch();
            con.commit();
            pst.clearBatch();
        } catch (SQLException e){
            System.out.println("批处理插入错误");
        }
        return true;
    }

    public boolean insertDataByModelToTable(Set<RegionModel> regionModels,String tableName){
        try {
            con.setAutoCommit(false);
            String pstStr = "INSERT INTO `community`.`"+tableName+"` (`ID`, `STREET_NAME`, `PARENT_ID`) VALUES (?,?,?)";
            PreparedStatement pst = con.prepareStatement(pstStr);
            for (RegionModel model:regionModels){
                pst.setString(1,model.getId());
                pst.setString(2,model.getStreet_name());
                pst.setString(3,model.getParent_id());
                pst.addBatch();
            }
            pst.executeBatch();
            con.commit();
            pst.clearBatch();
        } catch (SQLException e){
            System.out.println("批处理插入错误");
        }
        return true;
    }
    /**
     * 查询tableName总共插入了多少条数据。
     *
     */
    public Integer queryCount(String tableName){
        String sql = "select count(*) countResult from community."+tableName+";";
        Statement state= null;
        try {
            state = con.createStatement();
            ResultSet resultSet = state.executeQuery(sql);
            if(resultSet.next()) {
                return resultSet.getInt(1);
            } else {
                System.out.println("查询总数出错");
                return 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }
    /**
     * 数据库批插入优化实例.
     * @param conn
     */
    public void exec3(Connection conn){
        try {
            conn.setAutoCommit(false);
            Long beginTime = System.currentTimeMillis();
            //构造预处理statement
            PreparedStatement pst = conn.prepareStatement("insert into t1(id) values (?)");
            //1万次循环
            for(int i=1;i<=100000;i++){
                pst.setInt(1, i);
                pst.addBatch();
                //每1000次提交一次
                if(i%1000==0){//可以设置不同的大小；如50，100，500，1000等等
                    pst.executeBatch();
                    conn.commit();
                    pst.clearBatch();
                }
            }
            Long endTime = System.currentTimeMillis();
            System.out.println("pst+batch："+(endTime-beginTime)/1000+"秒");
            pst.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}