import java.security.PublicKey;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.UUID;


public class JDBCTest {


    //创建链接 连接数据库 返回连接状态
    public static Connection getconnection(){
        String driver = "com.mysql.cj.jdbc.Driver";
        String url = "jdbc:mysql://10.30.9.47:3306";
        String username = "idadmin";
        String password = "MFhx3gdVhgNeyLoA";
        Connection conn = null;
        try {
            //加载数据库驱动
            Class.forName(driver);
            //链接数据库
            conn = (Connection)DriverManager.getConnection(url,username,password);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }catch(SQLException e) {
            e.printStackTrace();
        }

        return conn;
    }
    //插入sql方法
    public static int insert(String uid,String uname,String upwd) {
        //获取链接
        Connection conn = getconnection();
        int i= 0;
        //数据库插入语句
        String sql = "insert into user(uuid,uname,upwd) values(?,?,?)";
        //预编译sql
        PreparedStatement pstmt;
        try {
            //将sql语句发送到数据库
            pstmt = (PreparedStatement) conn.prepareStatement(sql);
            pstmt.setString(1,uid);
            pstmt.setString(2, uname);
            pstmt.setString(3, upwd);
            //返回值是更新的条数
            i = pstmt.executeUpdate();
            pstmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        //返回受影响的行数
        return i;
    }

    public static int Delete() {
        //获取链接
        Connection conn = getconnection();
        int i= 0;
        //数据库语句
        String sql = "delete from user";
        //预编译sql
        PreparedStatement pstmt;
        try {
            pstmt = (PreparedStatement) conn.prepareStatement(sql);

            i = pstmt.executeUpdate();
            pstmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        //返回受影响的行数
        return i;
    }


}