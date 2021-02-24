import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Try {
    public static void main(String[] arges) throws SQLException {
        JDBCTest test = new JDBCTest();
        Connection connection = test.getconnection();


        String sql = "select employee_id as eid ,employee_name as ename ,employee_organizationid as dqid,employee_type as lx from permission.employee where employee_parentid=10009";
        PreparedStatement pstmt;
        List<String[]> XiashuList = new ArrayList<String[]>();
        pstmt = connection.prepareStatement(sql);
        ResultSet a1 = pstmt.executeQuery(sql);


        while (a1.next()) {
            System.out.println(a1.getString(1) + "\t" +
                    a1.getString(2) + "\t" + a1.getString(3) + "\t" + a1.getString(4));
            String[] Xiashu = {a1.getString(1), a1.getString(2), a1.getString(3), a1.getString(4)};
            XiashuList.add(Xiashu);
        }
    }


    //输入查看人名,返回所有下属信息
    public static String Yid(String name) throws SQLException {

        JDBCTest test = new JDBCTest();
        Connection connection = test.getconnection();


        String sql = "select employee_id from permission.employee where employee_name='" + name + "'";


        //将sql语句发送到数据库
        PreparedStatement pstmt = connection.prepareStatement(sql);
        ResultSet a = pstmt.executeQuery(sql);

        String Yid = "";
        while (a.next()) {
            Yid = a.getString(1);

        }
     return Yid;
    }
}